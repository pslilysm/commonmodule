package pers.cxd.corelibrary.util

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry
import org.apache.commons.compress.archivers.sevenz.SevenZFile
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.concurrent.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

/**
 * Miscellaneous compress file utility methods.
 *
 * @author pslilysm
 * @since 1.1.8
 * Created on 2022/6/9 10:43
 */
object CompressFileUtil {

    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun concurrentDecompressZip(srcFilePath: String, outputDirPath: String) {
        concurrentDecompressZip(File(srcFilePath), File(outputDirPath))
    }

    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun concurrentDecompressZip(srcFile: File, outputDir: File) {
        val es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5)
        try {
            concurrentDecompressZip(srcFile, outputDir, es, (es as ThreadPoolExecutor).corePoolSize)
        } finally {
            es.shutdownNow()
        }
    }

    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun concurrentDecompressZip(
        srcFilePath: String,
        outputDirPath: String,
        es: ExecutorService,
        threads: Int
    ) {
        concurrentDecompressZip(File(srcFilePath), File(outputDirPath), es, threads)
    }

    /**
     * Multi threads decompress Zip file
     *
     * @param srcFile   the Zip file
     * @param outputDir the output dir
     * @param es        the ExecutorService to run unzip task
     * @param threads   the num of threads to run unzip task
     * @throws IOException if an I/O errors occurs
     */
    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun concurrentDecompressZip(srcFile: File, outputDir: File, es: ExecutorService, threads: Int) {
        require(!srcFile.isDirectory) { "please check if the srcFile is a file" }
        require(!outputDir.isFile) { "please check if the outputDir is a dir" }
        ZipFile(srcFile).use { zf ->
            val entries = zf.entries()
            var entry: ZipEntry
            val entryFileQueue = ConcurrentLinkedQueue<ZipEntry>()
            while (entries.hasMoreElements()) {
                entry = entries.nextElement()
                if (entry.isDirectory) {
                    FileUtils.forceMkdir(File(outputDir, entry.name))
                } else {
                    entryFileQueue.add(entry)
                }
            }
            val countDownLatch = CountDownLatch(threads)
            val cur = Thread.currentThread()
            for (i in 0 until threads) {
                es.execute {
                    var zipEntry: ZipEntry?
                    while (entryFileQueue.poll().also { zipEntry = it } != null
                        && !Thread.currentThread().isInterrupted) {
                        val f = File(outputDir, zipEntry!!.name)
                        try {
                            zf.getInputStream(zipEntry).use { `is` ->
                                FileOutputStream(f).use { fos ->
                                    IOUtils.copy(
                                        `is`,
                                        fos
                                    )
                                }
                            }
                        } catch (e: IOException) {
                            cur.interrupt()
                            return@execute
                        }
                    }
                    countDownLatch.countDown()
                }
            }
            try {
                countDownLatch.await()
            } catch (e: InterruptedException) {
                throw IOException()
            }
        }
    }

    @Deprecated("")
    @Throws(IOException::class)
    fun concurrentDecompress7z(srcFilePath: String, outputDirPath: String) {
        concurrentDecompress7z(File(srcFilePath), File(outputDirPath))
    }

    @Deprecated("not impl the concurrent method")
    @Throws(IOException::class)
    fun concurrentDecompress7z(srcFile: File, outputDir: File) {
        val es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5)
        try {
            concurrentDecompress7z(srcFile, outputDir, es, (es as ThreadPoolExecutor).corePoolSize)
        } finally {
            es.shutdownNow()
        }
    }

    @Deprecated("")
    @Throws(IOException::class)
    fun concurrentDecompress7z(
        srcFilePath: String,
        outputDirPath: String,
        es: ExecutorService,
        threads: Int
    ) {
        concurrentDecompress7z(File(srcFilePath), File(outputDirPath), es, threads)
    }

    /**
     * Multi threads decompress 7z file
     *
     * @param srcFile   the 7z file
     * @param outputDir the output dir
     * @param es        the ExecutorService to run unzip task
     * @param threads   the num of threads to run unzip task
     * @throws IOException if an I/O errors occurs
     */
    @Deprecated("not impl the concurrent method")
    @Throws(IOException::class)
    fun concurrentDecompress7z(srcFile: File, outputDir: File, es: ExecutorService, threads: Int) {
        require(!srcFile.isDirectory) { "please check if the srcFile is a file" }
        require(!outputDir.isFile) { "please check if the outputDir is a dir" }
        SevenZFile(srcFile).use { sevenZFile ->
            val entryFileQueue = ConcurrentLinkedQueue<SevenZArchiveEntry>()
            for (entry in sevenZFile.entries) {
                if (entry.isDirectory) {
                    FileUtils.forceMkdir(File(outputDir, entry.name))
                } else {
                    entryFileQueue.offer(entry)
                }
            }
            val countDownLatch = CountDownLatch(threads)
            val curThread = Thread.currentThread()
            for (i in 0 until threads) {
                es.execute {
                    var entry: SevenZArchiveEntry?
                    while (entryFileQueue.poll().also { entry = it } != null
                        && !Thread.currentThread().isInterrupted) {
                        val f = File(outputDir, entry!!.name)
                        try {
                            sevenZFile.getInputStream(entry).use { `is` ->
                                FileOutputStream(f).use { fos ->
                                    IOUtils.copy(
                                        `is`,
                                        fos
                                    )
                                }
                            }
                        } catch (e: IOException) {
                            curThread.interrupt()
                            return@execute
                        }
                    }
                    countDownLatch.countDown()
                }
            }
            try {
                countDownLatch.await()
            } catch (e: InterruptedException) {
                throw IOException()
            }
        }
    }

    /**
     * Sync decompress 7z file
     *
     * @param srcFile   the 7z file
     * @param outputDir the output dir
     * @throws IOException if an I/O errors occurs
     */
    @kotlin.jvm.JvmStatic
    @Throws(IOException::class)
    fun decompress7z(srcFile: File, outputDir: File) {
        require(!srcFile.isDirectory) { "please check if the srcFile is a file" }
        require(!outputDir.isFile) { "please check if the outputDir is a dir" }
        SevenZFile(srcFile).use { sevenZFile ->
            var entry: SevenZArchiveEntry
            while (sevenZFile.nextEntry.also { entry = it } != null) {
                if (!entry.isDirectory) {
                    val f = File(outputDir, entry.name)
                    FileUtils.forceMkdirParent(f)
                    sevenZFile.getInputStream(entry)
                        .use { `is` -> FileOutputStream(f).use { fos -> IOUtils.copy(`is`, fos) } }
                }
            }
        }
    }
}