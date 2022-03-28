package pers.cxd.corelibrary.util;

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
 * Miscellaneous {@code Zip File} utility methods.
 *
 * @author pslilysm
 * @since 1.1.4
 */
public class ZipUtil {

    /**
     * Multi threads unzip Zip file
     *
     * @param srcPath  the Zip file path
     * @param destPath the output dir path
     * @throws IOException if an I/O errors occurs
     * @see #unzip(File, File)
     */
    public static void unzip(String srcPath, String destPath) throws IOException {
        unzip(new File(srcPath), new File(destPath));
    }

    /**
     * Multi threads unzip Zip file
     *
     * @param src  the Zip file
     * @param dest the output dir
     * @throws IOException if an I/O errors occurs
     * @see #unzip(File, File, ExecutorService, int)
     */
    public static void unzip(File src, File dest) throws IOException {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);
        try {
            unzip(src, dest, es, ((ThreadPoolExecutor) es).getCorePoolSize());
        } finally {
            es.shutdownNow();
        }
    }

    /**
     * Multi threads unzip Zip file
     *
     * @param src     the Zip file
     * @param dest    the output dir
     * @param es      the ExecutorService to run unzip task
     * @param threads the num of threads to run unzip task
     * @throws IOException if an I/O errors occurs
     */
    public static void unzip(File src, File dest, ExecutorService es, int threads) throws IOException {
        ZipFile zf = new ZipFile(src);
        Enumeration<? extends ZipEntry> entries = zf.entries();
        ZipEntry entry;
        ConcurrentLinkedQueue<ZipEntry> entryFileList = new ConcurrentLinkedQueue<>();
        while (entries.hasMoreElements()) {
            entry = entries.nextElement();
            if (entry.isDirectory()) {
                FileUtils.forceMkdir(new File(dest, entry.getName()));
            } else {
                entryFileList.add(entry);
            }
        }
        CountDownLatch countDownLatch = new CountDownLatch(threads);
        final Thread cur = Thread.currentThread();
        for (int i = 0; i < threads; i++) {
            es.execute(() -> {
                ZipEntry zipEntry;
                while ((zipEntry = entryFileList.poll()) != null
                        && !Thread.currentThread().isInterrupted()) {
                    File f = new File(dest, zipEntry.getName());
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
