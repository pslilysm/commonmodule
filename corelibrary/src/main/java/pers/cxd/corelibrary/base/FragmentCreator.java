package pers.cxd.corelibrary.base;

import androidx.fragment.app.Fragment;

/**
 * A Creator to create fragment by {@code fmtClazz}
 *
 * @param <T> Fragment type
 * @since 1.0.5
 * @author pslilysm
 */
public interface FragmentCreator<T extends Fragment> {

    T create(Class<T> fmtClazz);

}
