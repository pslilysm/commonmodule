package pers.cxd.corelibrary.util

import pers.cxd.corelibrary.Singleton
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.atomic.AtomicInteger

/**
 * A global executors holder
 *
 * @author pslilysm
 * @since 1.0.0
 */
object GlobalExecutors {
    private val sIONum = AtomicInteger()
    private val sComputeNum = AtomicInteger()
    private val sGlobalIOExecutor: Singleton<ScheduledExecutorService> =
        object : Singleton<ScheduledExecutorService>() {
            override fun create(): ScheduledExecutorService {
                return Executors.newScheduledThreadPool(
                    Runtime.getRuntime().availableProcessors() * 5
                ) { r: Runnable? -> Thread(r, "g-io-" + sIONum.incrementAndGet() + "-thread") }
            }
        }
    private val sGlobalComputeExecutor: Singleton<ScheduledExecutorService> =
        object : Singleton<ScheduledExecutorService>() {
            override fun create(): ScheduledExecutorService {
                return Executors.newScheduledThreadPool(
                    Runtime.getRuntime().availableProcessors()
                ) { r: Runnable? ->
                    Thread(
                        r,
                        "g-compute-" + sComputeNum.incrementAndGet() + "-thread"
                    )
                }
            }
        }

    /**
     * @return a global io executor, the core pool size is `cpu cores * 5`
     */
    fun io(): ScheduledExecutorService? {
        return sGlobalIOExecutor.get()
    }

    /**
     * @return a global compute executor, the core pool size is cpu cores
     */
    fun compute(): ScheduledExecutorService? {
        return sGlobalComputeExecutor.get()
    }
}