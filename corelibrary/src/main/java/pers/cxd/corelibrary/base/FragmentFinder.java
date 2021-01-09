package pers.cxd.corelibrary.base;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public interface FragmentFinder {

    @Nullable
    <T extends Fragment> T findFragment(FragmentManager manager, Class<T> clazz, int position);

}
