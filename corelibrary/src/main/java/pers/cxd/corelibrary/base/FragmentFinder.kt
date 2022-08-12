package pers.cxd.corelibrary.base

import androidx.fragment.app.Fragment

/**
 * A interface support to finding fragments in AMS cache
 *
 * @author pslilysm
 * @since 1.0.0
 */
interface FragmentFinder {
    fun <T : Fragment?> findFragment(fmtClass: Class<out Fragment?>, vararg args: Any?): T?
}