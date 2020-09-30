package com.yc.verbaltalk.chat.adapter;

import com.yc.verbaltalk.skill.factory.LoveByStagesFactory;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Created by sunshey on 2019/5/5.
 */

public class LoveByStagesPagerAdapter extends FragmentPagerAdapter {
    private final List<Integer> mIdLists;
    private List<String> titleList;
    //    private List<String> titleJobTypeList;
    private final FragmentManager fm;

    public LoveByStagesPagerAdapter(FragmentManager fm, int behavior, List<String> titleList, List<Integer> idLists) {
        super(fm, behavior);
        this.fm = fm;
        this.titleList = titleList;
        this.mIdLists = idLists;
//        this.titleJobTypeList = titleJobTypeList;
    }

    @Override
    public Fragment getItem(int position) {
        return LoveByStagesFactory.createFragment(position, mIdLists.get(position));
    }

    @Override
    public int getCount() {
        return titleList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);//页卡标题
    }

//    @Override
//    public Fragment instantiateItem(ViewGroup container, int position) {
//        Fragment fragment = (Fragment) super.instantiateItem(container,
//                position);
//        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
////        fm.beginTransaction().show(fragment).commit();
//        fm.beginTransaction().show(fragment).commitAllowingStateLoss();
//        return fragment;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        Fragment fragment = LoveByStagesFactory.fragments.get(position);
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        if (fragment != null) {
//            fragmentTransaction.hide(fragment);
//        }
//        fragmentTransaction.commit();
//    }
}
