package pers.cxd.corelibrary.util.concurrent;

/**
 * The ResourcePool obtains resources or store resources
 *
 * @param <R> the type of resource
 * @author pslilysm
 * @since 1.0.0
 */
public interface ResourcePool<R> {

    /**
     * Get a resource of the poll, this method will blocking if poll is empty until a resource was returned or
     * current thread is interrupted
     *
     * @return null if current thread is interrupted
     * @throws InterruptedException if current thread interrupted
     */
    R getResource() throws InterruptedException;

    /**
     * store a resource to pool, this method will notify a waiting thread after we store it.
     *
     * @param r the resource we want to store
     */
    void storeResource(R r);

}
