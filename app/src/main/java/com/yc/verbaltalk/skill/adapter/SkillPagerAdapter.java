package com.yc.verbaltalk.skill.adapter;

import com.yc.verbaltalk.index.factory.MainT2Factory;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by sunshey on 2019/5/5.
 */

public class SkillPagerAdapter extends FragmentPagerAdapter {
    private List<String> titleList;


    public SkillPagerAdapter(FragmentManager fm, int behavior, List<String> titleList) {
        super(fm, behavior);
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return MainT2Factory.createFragment(position);
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);//页卡标题
    }

}
