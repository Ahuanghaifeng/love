package com.yc.verbaltalk.mine.ui.activity;

import android.graphics.Color;
import android.os.Bundle;

import com.yc.verbaltalk.R;
import com.yc.verbaltalk.mine.adapter.UsingHelpPagerAdapter;
import com.yc.verbaltalk.base.activity.BaseSameActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;

import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class UsingHelpActivity extends BaseSameActivity {

    private ViewPager viewPager;
//    private LinearLayout llVpIndicate;

    private int[] imageResId = new int[]{R.mipmap.using_help_01, R.mipmap.using_help_02, R.mipmap.using_help_03, R.mipmap.using_help_04, R.mipmap.using_help_05};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_using_help);
//        invadeStatusBar(); //侵入状态栏

        initViews();
    }

    boolean isTransparency = false;

    private void initViews() {
//        RelativeLayout rlCon = findViewById(R.love_id.using_help_rl_con);



        viewPager = findViewById(R.id.using_help_viewpager);
//        llVpIndicate = findViewById(R.love_id.using_help_ll_vp_indicate);

        initViewPager();
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = findViewById(R.id.using_help_magic_indicator);
        CircleNavigator circleNavigator = new CircleNavigator(this);
        circleNavigator.setFollowTouch(false);
        circleNavigator.setCircleCount(imageResId.length);
        circleNavigator.setCircleColor(Color.RED);
        circleNavigator.setCircleClickListener(index -> viewPager.setCurrentItem(index));
        magicIndicator.setNavigator(circleNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    private void initViewPager() {
        UsingHelpPagerAdapter usingHelpPagerAdapter = new UsingHelpPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, imageResId);
        viewPager.setAdapter(usingHelpPagerAdapter);
    }


    @Override
    protected String offerActivityTitle() {
        if (isTransparency) {
            return "";
        }
        return "使用帮助";
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }
}
