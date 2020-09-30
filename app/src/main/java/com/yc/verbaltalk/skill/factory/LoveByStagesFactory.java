package com.yc.verbaltalk.skill.factory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.yc.verbaltalk.chat.ui.fragment.LoveByStagesFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunshey on 2019/5/5.
 */

public class LoveByStagesFactory {


    public static Map<Integer, Fragment> fragments = new HashMap<>();

    public static Fragment createFragment(int position, int id) {
//        Fragment fragment = fragments.get(position);
        Fragment fragment = fragments.get(id);
        if (fragment != null) {
            return fragment;
        }
        fragment = createJobTypePervasiveFragment(position, id);
//        fragments.put(position, fragment);
        fragments.put(id, fragment);
        return fragment;
    }

    private static Fragment createJobTypePervasiveFragment(int position, int id) {
        Fragment fragment = new LoveByStagesFragment();
        Bundle args = new Bundle();
        args.putInt("category_id", id);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }
}
