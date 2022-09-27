package pers.cxd.corelibrary.util.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Task scheduling executor wrapper
 *
 * @author pslilysm
 * Created on 2022/9/27 15:56
 */
public class ScheduledThreadPoolExecutorWrapper implements ScheduledExecutorService {

    /**
     * The base task scheduling executor
     */
    private final ScheduledExecutorService mTaskSchedulingES;

    /**
     * The base task execution executor
     */
    private final ExecutorService mTaskExecutionES;

    public ScheduledThreadPoolExecutorWrapper(ExecutorService executorService) {
        this(executorService, Executors.defaultThreadFactory());
    }

    /**
     * @param executor The executor that actually executes task
     * @param threadFactory   For task scheduling executor
     */
    public ScheduledThreadPoolExecutorWrapper(ExecutorService executor, ThreadFactory threadFactory) {
        this.mTaskSchedulingES = new ScheduledThreadPoolExecutor(1, Objects.requireNonNull(threadFactory, "threadFactory can not be null"));
        this.mTaskExecutionES = Objects.requireNonNull(executor, "executor can not be null");
    }

    private Runnable wrapRunnable(Runnable command) {
        return () -> this.execute(command);
    }

    private <V> Callable<V> wrapCallable(Callable<V> callable) {
        return () -> this.submit(callable).get();
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit) {
        return mTaskSchedulingES.schedule(wrapRunnable(command), delay, unit);
    }

    @Override
    public <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        // warn!! if the callable will block, then task scheduling will block so.
        // invoke this method with caution!
        return mTaskSchedulingES.schedule(wrapCallable(callable), delay, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) {
        return mTaskSchedulingES.scheduleAtFixedRate(wrapRunnable(command), initialDelay, period, unit);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
        return mTaskSchedulingES.scheduleWithFixedDelay(wrapRunnable(command), initialDelay, delay, unit);
    }

    @Override
    public void shutdown() {
        mTaskSchedulingES.shutdown();
        mTaskExecutionES.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
//        List<Runnable> runnableList = mBase.shutdownNow();
//        runnableList.addAll(mWrapper.shutdownNow());
//        return runnableList;
        throw new UnsupportedOperationException("not impl yet");
    }

    @Override
    public boolean isShutdown() {
        return mTaskSchedulingES.isShutdown() && mTaskExecutionES.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return mTaskSchedulingES.isTerminated() && mTaskExecutionES.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
//        long timeoutNano = unit.toNanos(timeout);
//        long l = System.nanoTime();
//        boolean result;
//        boolean baseRet = mBase.awaitTermination(timeout, unit);
//        return mBase.isTerminated() && mWrapper.isTerminated();
        throw new UnsupportedOperationException("not impl yet");
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return mTaskExecutionES.submit(task);
    }

    @Override
    public <T> Future<T> submit(Runnable task, T result) {
        return mTaskExecutionES.submit(task, result);
    }

    @Override
    public Future<?> submit(Runnable task) {
        return mTaskExecutionES.submit(task);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return mTaskExecutionES.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws InterruptedException {
        return mTaskExecutionES.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws ExecutionException, InterruptedException {
        return mTaskExecutionES.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
        return mTaskExecutionES.invokeAny(tasks, timeout, unit);
    }

    @Override
    public void execute(Runnable command) {
        mTaskExecutionES.execute(command);
    }
}
