package pers.cxd.corelibrary.util

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Miscellaneous [SimpleDateFormat] and [DecimalFormat] utility methods.
 *
 * @author pslilysm
 * @since 1.0.0
 */
object FormatterUtil {
    private val sDateFormatTLS: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("", Locale.getDefault())
            }
        }
    private const val syyyyMMddHHmmssPatter = "yyyy-MM-dd HH:mm:ss"
    private const val sMMddPattern = "MM-dd"
    private const val sHHmmssPattern = "HH:mm:ss"
    private val sDecimalFormatTLS: ThreadLocal<DecimalFormat> =
        object : ThreadLocal<DecimalFormat>() {
            override fun initialValue(): DecimalFormat {
                return DecimalFormat()
            }
        }

    /**
     * @return a Thread-Safely SimpleDateFormat
     */
    @kotlin.jvm.JvmStatic
    val simpleDateFormatter: SimpleDateFormat
        get() = sDateFormatTLS.get()!!

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"yyyy-MM-dd HH:mm:ss"}
     */
    @kotlin.jvm.JvmStatic
    fun getyyyyMMddHHmmssFormatter(): SimpleDateFormat {
        val sdf = sDateFormatTLS.get()
        sdf!!.applyPattern(syyyyMMddHHmmssPatter)
        return sdf
    }

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"MM-dd"}
     */
    @kotlin.jvm.JvmStatic
    val mMddFormatter: SimpleDateFormat
        get() {
            val sdf = sDateFormatTLS.get()
            sdf!!.applyPattern(sMMddPattern)
            return sdf
        }

    /**
     * @return a Thread-Safely SimpleDateFormat with pattern {"HH:mm:ss"}
     */
    @kotlin.jvm.JvmStatic
    val hHmmssFormatter: SimpleDateFormat
        get() {
            val sdf = sDateFormatTLS.get()
            sdf!!.applyPattern(sHHmmssPattern)
            return sdf
        }

    /**
     * @return a Thread-Safely DecimalFormat
     */
    @kotlin.jvm.JvmStatic
    val decimalFormat: DecimalFormat
        get() = sDecimalFormatTLS.get()!!

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.000"}
     */
    @kotlin.jvm.JvmStatic
    fun get_0dot000Formatter(): DecimalFormat {
        val decimalFormat = sDecimalFormatTLS.get()
        decimalFormat!!.applyPattern("0.000")
        return decimalFormat
    }

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.00"}
     */
    @kotlin.jvm.JvmStatic
    fun get_0dot00Formatter(): DecimalFormat {
        val decimalFormat = sDecimalFormatTLS.get()
        decimalFormat!!.applyPattern("0.00")
        return decimalFormat
    }

    /**
     * @return a Thread-Safely DecimalFormat with pattern {"#.0"}
     */
    @kotlin.jvm.JvmStatic
    fun get_0dot0Formatter(): DecimalFormat {
        val decimalFormat = sDecimalFormatTLS.get()
        decimalFormat!!.applyPattern("0.0")
        return decimalFormat
    }
}