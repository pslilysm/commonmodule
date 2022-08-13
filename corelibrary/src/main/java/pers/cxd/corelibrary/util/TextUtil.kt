package pers.cxd.corelibrary.util

import android.text.TextUtils

/**
 * Miscellaneous text utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object TextUtil {
    @kotlin.jvm.JvmStatic
    fun cleanSpace(str: String): String {
        return str.replace("\\s+".toRegex(), "")
    }

    @kotlin.jvm.JvmStatic
    fun cleanWrap(str: String): String {
        return str.replace("[\\s*\t\n\r]".toRegex(), "")
    }

    @kotlin.jvm.JvmStatic
    fun nullIfEmpty(s1: String?): String? {
        return if (TextUtils.isEmpty(s1)) null else s1
    }
}