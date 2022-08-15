package pers.cxd.corelibrary.util.concurrent

import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.locks.LockSupport

/**
 * Default implementation of [ResourcePool]
 *
 * @param R the type of resource
 * @author pslilysm
 * @since 1.0.0
 */
class ResourcePoolImpl<R> : ResourcePool<R?> {
    val resourceQueue: Queue<R?> = ConcurrentLinkedQueue()
    val waitQueue: Queue<Thread> = ConcurrentLinkedQueue()
    override val resource: R?
        get() {
            var r = resourceQueue.poll()
            if (r == null) {
                val curTh = Thread.currentThread()
                waitQueue.offer(curTh)
                LockSupport.park(resourceQueue)
                if (curTh.isInterrupted) {
                    waitQueue.remove(curTh)
                    return null
                }
                r = resourceQueue.poll()
            }
            return r ?: resource
        }

    override fun storeResource(r: R?) {
        resourceQueue.offer(r)
        val waitTh = waitQueue.poll()
        if (waitTh != null) {
            LockSupport.unpark(waitTh)
        }
    }
}