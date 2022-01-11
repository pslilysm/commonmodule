package pers.cxd.corelibrary.base;

import androidx.fragment.app.Fragment;

public interface FragmentCreator<T extends Fragment> {

    T create(Class<T> fmtClazz);

}
