package model;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import util.NetUtil;
import config.Api;

/**
 * Created by mac on 2018/5/2.
 */

public class GankImp implements GankInterface {

    @Override
    public void loadGankList(final GankInterface.loadCallback callback) {
        Log.d("Fxy", "loadGankList: start");
        Map<String,String> map = new HashMap<>();//没东西。。啥也不加
        NetUtil.Get(Api.GankUrl()+"1", map, new NetUtil.Callback() {
            @Override
            public void onSucceed(String response) {
                callback.Succeed(response);
            }

            @Override
            public void onFailed(String response) {

            }
        });
    }
    @Override
    public void RefreshList(final loadCallback callback) {
        Map<String,String> map = new HashMap<>();//没东西。。啥也不加
        NetUtil.Get(Api.GankUrl()+"10", map, new NetUtil.Callback() {
            @Override
            public void onSucceed(String response) {
                callback.Succeed(response);
            }

            @Override
            public void onFailed(String response) {

            }
        });
    }
}
