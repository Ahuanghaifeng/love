package com.yc.verbaltalk.mine.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.yc.verbaltalk.R;
import com.yc.verbaltalk.base.engine.LoveEngine;
import com.yc.verbaltalk.base.utils.UserInfoHelper;
import com.yc.verbaltalk.base.view.LoadDialog;
import com.yc.verbaltalk.chat.bean.LoveHealingBean;
import com.yc.verbaltalk.mine.adapter.LoveHealingItemAdapter;
import com.yc.verbaltalk.skill.ui.activity.LoveUpDownPhotoActivity;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.observers.DisposableObserver;
import yc.com.rthttplibrary.bean.ResultInfo;
import yc.com.rthttplibrary.config.HttpConfig;

/**
 * 收藏 情话
 * Created by sunshey on 2019/5/5.
 */

public class CollectLoveHealingFragment extends BaseCollectFragment {

    private RecyclerView mRecyclerView;
    private LoveEngine mLoveEngin;
    private int PAGE_SIZE = 10;
    private int PAGE_NUM = 1;
    private LoadDialog mLoadingDialog;

    private LoveHealingItemAdapter mAdapter;

    private View emptyView;


    @Override
    protected void initBundle() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int position = arguments.getInt("position");
//            mCategoryId = arguments.getInt("category_id", -1);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_collect_love_healing;
    }

    @Override
    protected void initViews() {
        mLoveEngin = new LoveEngine(mCollectActivity);
//        LoadingDialog loadingDialog=mCollectActivity.mLoadingDialog;
        mLoadingDialog = mCollectActivity.mLoadingDialog;
        emptyView = rootView.findViewById(R.id.top_empty_view);
        initRecyclerView();
        initListener();
    }

    private void initListener() {
        mAdapter.setOnLoadMoreListener(this::netData, mRecyclerView);
        mAdapter.setOnItemClickListener((adapter, view, position) -> LoveUpDownPhotoActivity.startLoveUpDownPhotoActivity(mCollectActivity, position, "Lovewords/collect_list"));
    }


    private void initRecyclerView() {
        mRecyclerView = rootView.findViewById(R.id.fragment_collect_love_healing_rv);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mCollectActivity);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new LoveHealingItemAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void lazyLoad() {
        netData();
    }


    private void netData() {

        if (PAGE_NUM == 1)
            mLoadingDialog.showLoadingDialog();
        mLoveEngin.listsCollectLovewords(UserInfoHelper.getUid(), String.valueOf(PAGE_NUM), String.valueOf(PAGE_SIZE))
                .subscribe(new DisposableObserver<ResultInfo<List<LoveHealingBean>>>() {

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (PAGE_NUM == 1) mLoadingDialog.dismissLoadingDialog();
                    }

                    @Override
                    public void onNext(ResultInfo<List<LoveHealingBean>> listAResultInfo) {
                        if (PAGE_NUM == 1) mLoadingDialog.dismissLoadingDialog();
                        if (listAResultInfo != null && listAResultInfo.code == HttpConfig.STATUS_OK) {
                            List<LoveHealingBean> mLoveHealingBeans = listAResultInfo.data;
                            if (mLoveHealingBeans != null && mLoveHealingBeans.size() > 0)

                                initRecyclerData(mLoveHealingBeans);
                            else {
                                if (PAGE_NUM == 1) {
                                    emptyView.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                    }


                });
    }

    private void initRecyclerData(List<LoveHealingBean> mLoveHealingBeans) {
        if (PAGE_NUM == 1) {
            mAdapter.setNewData(mLoveHealingBeans);
        } else {
            mAdapter.addData(mLoveHealingBeans);
        }
        if (mLoveHealingBeans != null && mLoveHealingBeans.size() == PAGE_SIZE) {
            mAdapter.loadMoreComplete();
            PAGE_NUM++;
        } else {
            mAdapter.loadMoreEnd();
        }

    }

}
