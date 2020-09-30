package com.yc.verbaltalk.mine.ui.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.music.player.lib.util.ToastUtils;
import com.umeng.analytics.MobclickAgent;
import com.yc.verbaltalk.R;
import com.yc.verbaltalk.base.engine.LoveEngine;
import com.yc.verbaltalk.base.view.LoadDialog;
import com.yc.verbaltalk.base.view.OpenAkpDialog;
import com.yc.verbaltalk.chat.bean.LoveHealDetDetailsBean;
import com.yc.verbaltalk.chat.bean.OpenApkPkgInfo;
import com.yc.verbaltalk.mine.adapter.CollectLoveHealDetailAdapter;
import com.yc.verbaltalk.model.constant.ConstantKey;
import com.yc.verbaltalk.model.util.PackageUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.observers.DisposableObserver;
import yc.com.rthttplibrary.bean.ResultInfo;
import yc.com.rthttplibrary.config.HttpConfig;
import yc.com.rthttplibrary.util.ToastUtil;

/**
 * 收藏 话术
 */

public class CollectVerbalFragment extends BaseCollectFragment {

    private RecyclerView mRecyclerView;
    private LoveEngine mLoveEngin;
    private int PAGE_SIZE = 10;
    private int PAGE_NUM = 0;
    private LoadDialog mLoadingDialog;

    private CollectLoveHealDetailAdapter loveHealDetailsAdapter;
    private View emptyView;
    private Handler handler;

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
        handler = new Handler();

        mLoadingDialog = mCollectActivity.mLoadingDialog;
        initRecyclerView();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String tint) {  //无网状态

        if (TextUtils.equals("collect_cancel", tint)) {
            PAGE_NUM = 0;
            netData();
        }
    }


    private void initRecyclerView() {
        mRecyclerView = rootView.findViewById(R.id.fragment_collect_love_healing_rv);
        emptyView = rootView.findViewById(R.id.top_empty_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mCollectActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        loveHealDetailsAdapter = new CollectLoveHealDetailAdapter(null);

        mRecyclerView.setAdapter(loveHealDetailsAdapter);
        mRecyclerView.setHasFixedSize(true);
        initListener();

    }

    private void initListener() {
        loveHealDetailsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                netData();
            }
        }, mRecyclerView);
        loveHealDetailsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LoveHealDetDetailsBean item = loveHealDetailsAdapter.getItem(position);
                if (item != null) {
                    toCopy(item);
                }
            }
        });

    }

    @Override
    protected void lazyLoad() {
        netData();
    }


    private void netData() {


        mLoadingDialog.showLoadingDialog();
        mLoveEngin.getVerbalList(PAGE_NUM, PAGE_SIZE).subscribe(new DisposableObserver<ResultInfo<List<LoveHealDetDetailsBean>>>() {
            @Override
            public void onNext(ResultInfo<List<LoveHealDetDetailsBean>> listResultInfo) {
                if (listResultInfo != null && listResultInfo.code == HttpConfig.STATUS_OK) {
                    List<LoveHealDetDetailsBean> loveHealDetDetailsBeans = listResultInfo.data;
                    if (PAGE_NUM == 0) {
                        if (loveHealDetDetailsBeans == null || loveHealDetDetailsBeans.size() == 0) {
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            emptyView.setVisibility(View.GONE);
                        }
                    }
                    createData(loveHealDetDetailsBeans);
                } else {
                    if (PAGE_NUM == 0) emptyView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onError(Throwable e) {
                if (mLoadingDialog != null) mLoadingDialog.dismissLoadingDialog();
                if (PAGE_NUM == 0) emptyView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onComplete() {
                if (mLoadingDialog != null) mLoadingDialog.dismissLoadingDialog();
            }
        });


//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mLoveEngin.getCollectLoveHeals(PAGE_SIZE, PAGE_NUM).subscribe(new DisposableObserver<List<LoveHealDetDetailsBean>>() {
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<LoveHealDetDetailsBean> loveHealDetDetailsBeans) {
//
//                    }
//                });
//            }
//        }, 1500);


    }

    private void createData(List<LoveHealDetDetailsBean> loveHealDetDetailsBeans) {
        if (PAGE_NUM == 0) loveHealDetailsAdapter.setNewData(loveHealDetDetailsBeans);
        else loveHealDetailsAdapter.addData(loveHealDetDetailsBeans);
        if (loveHealDetDetailsBeans != null && loveHealDetDetailsBeans.size() == PAGE_SIZE) {
            PAGE_NUM++;
            loveHealDetailsAdapter.loadMoreComplete();
        } else {
            loveHealDetailsAdapter.loadMoreEnd();
        }
        if (mLoadingDialog != null) mLoadingDialog.dismissLoadingDialog();
    }

    private void toCopy(LoveHealDetDetailsBean content) {
        MobclickAgent.onEvent(getActivity(), ConstantKey.UM_COPY_DIALOGUE_HEAL);
        ClipboardManager myClipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", content.content);
        myClipboard.setPrimaryClip(myClip);
        ToastUtils.showCenterToast("复制成功，快去微信粘贴吧~");
//        showOpenAkpDialog(content);
    }

    private void showOpenAkpDialog(LoveHealDetDetailsBean content) {
        List<OpenApkPkgInfo> openApkPkgInfos = new ArrayList<>();
        OpenApkPkgInfo qq = new OpenApkPkgInfo(1, "", "QQ", getActivity().getResources().getDrawable(R.mipmap.icon_d_qq));
        OpenApkPkgInfo wx = new OpenApkPkgInfo(2, "", "微信", getActivity().getResources().getDrawable(R.mipmap.icon_d_wx));
        OpenApkPkgInfo mm = new OpenApkPkgInfo(3, "", "陌陌", getActivity().getResources().getDrawable(R.mipmap.icon_d_momo));
//        OpenApkPkgInfo tt = new OpenApkPkgInfo(4, "", "探探", getResources().getDrawable(R.mipmap.icon_d_tt));

        List<String> apkList = PackageUtils.getApkList(getActivity());
        for (int i = 0; i < apkList.size(); i++) {
            String apkPkgName = apkList.get(i);
            if ("com.tencent.mobileqq".equals(apkPkgName)) {
                qq.pkg = apkPkgName;
            } else if ("com.tencent.mm".equals(apkPkgName)) {
                wx.pkg = apkPkgName;
            } else if ("com.immomo.momo".equals(apkPkgName)) {
                mm.pkg = apkPkgName;
            }/* else if ("com.p1.mobile.putong".equals(apkPkgName)) {
                tt.pkg = apkPkgName;
            }*/
        }

        openApkPkgInfos.add(qq);
        openApkPkgInfos.add(wx);
        openApkPkgInfos.add(mm);
//        openApkPkgInfos.add(tt);
        OpenAkpDialog openAkpDialog = new OpenAkpDialog(getActivity(), openApkPkgInfos, content, true);

        openAkpDialog.show();
    }
}
