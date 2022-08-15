package pers.cxd.corelibrary.util.function

/**
 * Represents a suspend function that produces a result.
 *
 * @param R return type of the function.
 * @author cxd
 * Created on 2022/8/11 16:00
 */
interface SuspendFunction<out R> {

    suspend fun invoke(): R

}