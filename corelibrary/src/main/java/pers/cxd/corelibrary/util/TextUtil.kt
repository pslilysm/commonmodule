package pers.cxd.corelibrary.util

import android.text.TextUtils

/**
 * Miscellaneous text utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object TextUtil {
    fun cleanSpace(str: String): String {
        return str.replace("\\s+".toRegex(), "")
    }

    fun cleanWrap(str: String): String {
        return str.replace("[\\s*\t\n\r]".toRegex(), "")
    }

    fun nullIfEmpty(s1: String?): String? {
        return if (TextUtils.isEmpty(s1)) null else s1
    }
}