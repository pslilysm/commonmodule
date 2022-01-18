package pers.cxd.corelibrary.base;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * A interface support to finding fragments in AMS cache
 *
 * @author pslilysm
 * @since 1.0.0
 */
public interface FragmentFinder {

    @Nullable
    <T extends Fragment> T findFragment(Class<? extends Fragment> fmtClass, Object... args);

}
