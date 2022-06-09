package pers.cxd.corelibrary.util;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Miscellaneous compress file utility methods.
 *
 * @author cxd
 * @since 1.1.8
 * Created on 2022/6/9 10:43
 */
public class CompressFileUtil {

    /**
     * @see #concurrentDecompressZip(File, File)
     */
    public static void concurrentDecompressZip(String srcFilePath, String outputDirPath) throws IOException {
        concurrentDecompressZip(new File(srcFilePath), new File(outputDirPath));
    }

    /**
     * @see #concurrentDecompressZip(File, File, ExecutorService, int)
     */
    public static void concurrentDecompressZip(File srcFile, File outputDir) throws IOException {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
        try {
            concurrentDecompressZip(srcFile, outputDir, es, ((ThreadPoolExecutor) es).getCorePoolSize());
        } finally {
            es.shutdownNow();
        }
    }

    /**
     * @see #concurrentDecompressZip(File, File, ExecutorService, int)
     */
    public static void concurrentDecompressZip(String srcFilePath, String outputDirPath, ExecutorService es, int threads) throws IOException {
        concurrentDecompressZip(new File(srcFilePath), new File(outputDirPath), es, threads);
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
    public static void concurrentDecompressZip(File srcFile, File outputDir, ExecutorService es, int threads) throws IOException {
        if (srcFile.isDirectory()) {
            throw new IllegalArgumentException("please check if the srcFile is a file");
        }
        if (outputDir.isFile()) {
            throw new IllegalArgumentException("please check if the outputDir is a dir");
        }
        try (ZipFile zf = new ZipFile(srcFile)) {
            Enumeration<? extends ZipEntry> entries = zf.entries();
            ZipEntry entry;
            ConcurrentLinkedQueue<ZipEntry> entryFileQueue = new ConcurrentLinkedQueue<>();
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputDir, entry.getName()));
                } else {
                    entryFileQueue.add(entry);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(threads);
            final Thread cur = Thread.currentThread();
            for (int i = 0; i < threads; i++) {
                es.execute(() -> {
                    ZipEntry zipEntry;
                    while ((zipEntry = entryFileQueue.poll()) != null
                            && !Thread.currentThread().isInterrupted()) {
                        File f = new File(outputDir, zipEntry.getName());
                        try (InputStream is = zf.getInputStream(zipEntry);
                             FileOutputStream fos = new FileOutputStream(f)) {
                            IOUtils.copy(is, fos);
                        } catch (IOException e) {
                            cur.interrupt();
                            return;
                        }
                    }
                    countDownLatch.countDown();
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new IOException();
            }
        }
    }

    /**
     * @see #concurrentDecompress7z(File, File)
     */
    @Deprecated
    public static void concurrentDecompress7z(String srcFilePath, String outputDirPath) throws IOException {
        concurrentDecompress7z(new File(srcFilePath), new File(outputDirPath));
    }

    /**
     * @see #concurrentDecompress7z(File, File, ExecutorService, int)
     */
    @Deprecated
    public static void concurrentDecompress7z(File srcFile, File outputDir) throws IOException {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
        try {
            concurrentDecompress7z(srcFile, outputDir, es, ((ThreadPoolExecutor) es).getCorePoolSize());
        } finally {
            es.shutdownNow();
        }
    }

    /**
     * @see #concurrentDecompress7z(File, File, ExecutorService, int)
     */
    @Deprecated
    public static void concurrentDecompress7z(String srcFilePath, String outputDirPath, ExecutorService es, int threads) throws IOException {
        concurrentDecompress7z(new File(srcFilePath), new File(outputDirPath), es, threads);
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
    @Deprecated
    public static void concurrentDecompress7z(File srcFile, File outputDir, ExecutorService es, int threads) throws IOException {
        if (srcFile.isDirectory()) {
            throw new IllegalArgumentException("please check if the srcFile is a file");
        }
        if (outputDir.isFile()) {
            throw new IllegalArgumentException("please check if the outputDir is a dir");
        }
        try (SevenZFile sevenZFile = new SevenZFile(srcFile)) {
            ConcurrentLinkedQueue<SevenZArchiveEntry> entryFileQueue = new ConcurrentLinkedQueue<>();
            for (SevenZArchiveEntry entry : sevenZFile.getEntries()) {
                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(new File(outputDir, entry.getName()));
                } else {
                    entryFileQueue.offer(entry);
                }
            }
            CountDownLatch countDownLatch = new CountDownLatch(threads);
            final Thread curThread = Thread.currentThread();
            for (int i = 0; i < threads; i++) {
                es.execute(() -> {
                    SevenZArchiveEntry entry;
                    while ((entry = entryFileQueue.poll()) != null
                            && !Thread.currentThread().isInterrupted()) {
                        File f = new File(outputDir, entry.getName());
                        try (InputStream is = sevenZFile.getInputStream(entry);
                             FileOutputStream fos = new FileOutputStream(f)) {
                            IOUtils.copy(is, fos);
                        } catch (IOException e) {
                            curThread.interrupt();
                            return;
                        }
                    }
                    countDownLatch.countDown();
                });
            }
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                throw new IOException();
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
    public static void decompress7z(File srcFile, File outputDir) throws IOException {
        if (srcFile.isDirectory()) {
            throw new IllegalArgumentException("please check if the srcFile is a file");
        }
        if (outputDir.isFile()) {
            throw new IllegalArgumentException("please check if the outputDir is a dir");
        }
        try (SevenZFile sevenZFile = new SevenZFile(srcFile)) {
            SevenZArchiveEntry entry;
            while ((entry = sevenZFile.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    File f = new File(outputDir, entry.getName());
                    FileUtils.forceMkdirParent(f);
                    try (InputStream is = sevenZFile.getInputStream(entry);
                         FileOutputStream fos = new FileOutputStream(f)) {
                        IOUtils.copy(is, fos);
                    }
                }
            }
        }
    }

}
