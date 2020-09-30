package com.yc.verbaltalk.community.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yc.verbaltalk.R;
import com.yc.verbaltalk.base.activity.BaseSameActivity;
import com.yc.verbaltalk.base.utils.ItemDecorationHelper;
import com.yc.verbaltalk.base.utils.UserInfoHelper;
import com.yc.verbaltalk.base.view.LoadDialog;
import com.yc.verbaltalk.chat.bean.CommunityInfo;
import com.yc.verbaltalk.chat.bean.CommunityInfoWrapper;
import com.yc.verbaltalk.community.adapter.CommunityAdapter;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.observers.DisposableObserver;
import yc.com.rthttplibrary.bean.ResultInfo;
import yc.com.rthttplibrary.config.HttpConfig;

/**
 * Created by suns  on 2019/8/28 09:17.
 */
public class CommunityTagListActivity extends BaseSameActivity {

    private RecyclerView recyclerView;


    private CommunityAdapter communityAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private int page = 1;
    private final int PAGE_SIZE = 10;
    private LoadDialog loadingDialog;
    private String title;
    private String catId;
    private View emptyView;


    public static void StartActivity(Context context, String title, String catId) {
        Intent intent = new Intent(context, CommunityTagListActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("cat_id", catId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_community);
        initViews();

    }


    protected void initViews() {
        if (getIntent() != null) {
            title = getIntent().getStringExtra("title");
            catId = getIntent().getStringExtra("cat_id");
        }

        recyclerView = findViewById(R.id.rv_community);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        emptyView = findViewById(R.id.top_empty_view);
        setBarTitle(title);

        initRecyclerView();
        getData();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        communityAdapter = new CommunityAdapter(null);
        recyclerView.setAdapter(communityAdapter);
        recyclerView.addItemDecoration(new ItemDecorationHelper(this, 10));
        initListener();
    }

    private void initListener() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.red_crimson));
        swipeRefreshLayout.setOnRefreshListener(() -> {
            page = 1;
            getData();
        });


        communityAdapter.setOnItemClickListener((adapter, view, position) -> {
            CommunityInfo communityInfo = communityAdapter.getItem(position);
            if (UserInfoHelper.isLogin(this))
                if (null != communityInfo)
                    CommunityDetailActivity.StartActivity(this, getString(R.string.community_detail), communityInfo.topicId);
        });


        communityAdapter.setOnLoadMoreListener(this::getData, recyclerView);

        communityAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            CommunityInfo communityInfo = communityAdapter.getItem(position);
            if (communityInfo != null) {

                switch (view.getId()) {
                    case R.id.iv_like:
                    case R.id.ll_like:

                        if (UserInfoHelper.isLogin(this))
                            if (communityInfo.is_dig == 0) {//未点赞

                                like(communityInfo, position);
                            }

                        break;
                }
            }
        });


    }

    public void getData() {

        if (page == 1) {
            loadingDialog = new LoadDialog(this);
            loadingDialog.showLoadingDialog();
        }
        mLoveEngine.getCommunityTagListInfo(UserInfoHelper.getUid(), catId, page, PAGE_SIZE).subscribe(new DisposableObserver<ResultInfo<CommunityInfoWrapper>>() {
            @Override
            public void onComplete() {
                if (loadingDialog != null) loadingDialog.dismissLoadingDialog();
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                if (loadingDialog != null) loadingDialog.dismissLoadingDialog();
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onNext(ResultInfo<CommunityInfoWrapper> communityInfoWrapperResultInfo) {
                if (loadingDialog != null) loadingDialog.dismissLoadingDialog();
                if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
                if (communityInfoWrapperResultInfo != null && communityInfoWrapperResultInfo.code == HttpConfig.STATUS_OK) {
                    if (communityInfoWrapperResultInfo.data != null && communityInfoWrapperResultInfo.data.list != null &&
                            communityInfoWrapperResultInfo.data.list.size() > 0) {
                        List<CommunityInfo> list = communityInfoWrapperResultInfo.data.list;
                        createNewData(list);
                    } else {
                        if (page == 1) {
                            emptyView.setVisibility(View.VISIBLE);
                        }
                    }

                }
            }
        });


    }

    private void createNewData(List<CommunityInfo> list) {

        if (page == 1) {
            communityAdapter.setNewData(list);
        } else {
            communityAdapter.addData(list);

        }

        if (list != null && list.size() == PAGE_SIZE) {
            communityAdapter.loadMoreComplete();
            page++;
        } else {
            communityAdapter.loadMoreEnd();
        }
    }


    private void like(CommunityInfo communityInfo, int position) {

        mLoveEngine.likeTopic(UserInfoHelper.getUid(), communityInfo.topicId).subscribe(new DisposableObserver<ResultInfo<String>>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<String> stringResultInfo) {
                if (stringResultInfo != null && stringResultInfo.code == HttpConfig.STATUS_OK) {
                    communityInfo.is_dig = 1;
                    int likeNum = communityInfo.like_num + 1;
                    communityInfo.like_num = likeNum;
                    communityAdapter.notifyItemChanged(position);
//                    imageView.setImageResource(R.mipmap.community_like_selected);
//
//                    textView.setText(String.valueOf(likeNum));
                }
            }
        });
    }

    @Override
    protected String offerActivityTitle() {
        return null;
    }

}
