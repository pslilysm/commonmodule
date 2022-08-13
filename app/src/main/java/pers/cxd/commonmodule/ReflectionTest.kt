package pers.cxd.commonmodule

import android.util.Log

/**
 * @author pslilysm
 * Created on 2022/8/13 16:20
 */
class ReflectionTest {
    private var mTest: String? = null
    fun setmTest(mTest: String?) {
        this.mTest = mTest
    }

    fun printmTest() {
        Log.i(TAG, "printmTest: mTest -> $mTest")
    }

    companion object {
        private const val TAG = "CXD-Test"
        @kotlin.jvm.JvmStatic
        private var sTest: String? = null
        @kotlin.jvm.JvmStatic
        fun setsTest(sTest: String?) {
            Companion.sTest = sTest
        }

        @kotlin.jvm.JvmStatic
        fun printsTest() {
            Log.i(TAG, "printsTest: sTest -> " + sTest)
        }
    }
}