package com.yc.verbaltalk.base.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.yc.verbaltalk.base.YcApplication;
import com.yc.verbaltalk.base.engine.LoveEngine;
import com.yc.verbaltalk.chat.bean.ShareInfo;
import com.yc.verbaltalk.model.util.SPUtils;

import io.reactivex.observers.DisposableObserver;
import yc.com.rthttplibrary.bean.ResultInfo;
import yc.com.rthttplibrary.config.HttpConfig;

/**
 * Created by wanglin  on 2019/7/9 18:18.
 */
public class ShareInfoHelper {


    private static final String TAG = "ShareInfoHelper";
    private static ShareInfo mShareInfo;

    public static void setShareInfo(ShareInfo ShareInfo) {
        ShareInfoHelper.mShareInfo = ShareInfo;
        try {

            String str = JSON.toJSONString(ShareInfo);
            SPUtils.put(YcApplication.getInstance().getApplicationContext(), SPUtils.SHARE_INFO, str);

        } catch (Exception e) {
            Log.e(TAG, "setShareInfo: 保存json失败" + e.getMessage());
        }
    }

    public static ShareInfo getShareInfo() {
        if (mShareInfo != null) {
            return mShareInfo;
        }
        try {
            String str = (String) SPUtils.get(YcApplication.getInstance().getApplicationContext(), SPUtils.SHARE_INFO, "");

            mShareInfo = JSON.parseObject(str, ShareInfo.class);
        } catch (Exception e) {
            Log.e(TAG, "getShareInfo: 解析json失败" + e.getMessage());
        }

        return mShareInfo;
    }

    public static void getNetShareInfo(Context context) {
        LoveEngine loveEngin = new LoveEngine(context);

        loveEngin.getShareInfo().subscribe(new DisposableObserver<ResultInfo<ShareInfo>>() {
            @Override
            public void onComplete() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResultInfo<ShareInfo> shareInfoResultInfo) {
                if (shareInfoResultInfo != null && shareInfoResultInfo.code == HttpConfig.STATUS_OK && shareInfoResultInfo.data != null) {
                    ShareInfo shareInfo = shareInfoResultInfo.data;
                    setShareInfo(shareInfo);
                }
            }
        });
    }
}
