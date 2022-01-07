package pers.cxd.corelibrary.base;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * A interface support to find Fragment by {@link FragmentManager}、 {@link Class}、 {@code position}
 *
 * @author pslilysm
 * @since 1.0.0
 */
public interface FragmentFinder {

    @Nullable
    <T extends Fragment> T findFragment(Class<? extends Fragment> fmtClass, Object... args);

}
