package com.yc.verbaltalk.index.factory;

import android.util.SparseArray;

import com.yc.verbaltalk.skill.ui.fragment.ExpressFragment;
import com.yc.verbaltalk.skill.ui.fragment.ChatSkillFragment;

import androidx.fragment.app.Fragment;

/**
 * Created by sunshey on 2019/5/5.
 */

public class MainT2Factory {

    private static final int FRAGMENT_0 = 0;
    private static final int FRAGMENT_1 = 1;
    public static SparseArray<Fragment> fragments = new SparseArray<>();

    public static Fragment createFragment(int position) {
        Fragment fragment = fragments.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case FRAGMENT_0:
                fragment = new ChatSkillFragment();
                fragments.put(FRAGMENT_0, fragment);
                break;
            case FRAGMENT_1:
//                fragment = new ChildMainT2T2Fragment();
                fragment = new ExpressFragment();
                fragments.put(FRAGMENT_1, fragment);
                break;
        }
        return fragment;
    }
}
