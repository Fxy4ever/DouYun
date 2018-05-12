package model.lmp;

import java.util.HashMap;
import java.util.Map;

import config.Api;
import model.model_Interface.IMediaInterface;
import util.NetUtil;

/**
 * Created by mac on 2018/5/12.
 */

public class MediaImp implements IMediaInterface {
    private String showapi_appid = "38542";
    private String showapi_sign = "8b04f981ad524a7d84104e8360d1ae53";
    private String page = "1";
    private String type = "41";
    private int i = 1;
    @Override
    public void loadList(final LoadCallback callback) {

        Map<String,String> map = new HashMap<>();
        map.put("type","41");
        map.put("page",String.valueOf(i));
        map.put("showapi_sign","8b04f981ad524a7d84104e8360d1ae53");
        map.put("showapi_appid","38542");
        NetUtil.Get(Api.MediaUrl(), map, new NetUtil.Callback() {
            @Override
            public void onSucceed(String response) {
                callback.succeed(response);
            }

            @Override
            public void onFailed(String response) {
                callback.failed(response);
            }
        });
    }

    @Override
    public void RefreshList(final LoadCallback callback) {
        Map<String,String> map = new HashMap<>();
        map.put("type","41");
        map.put("page","5");
        map.put("showapi_sign","8b04f981ad524a7d84104e8360d1ae53");
        map.put("showapi_appid","38542");
        NetUtil.Get(Api.MediaUrl(), map, new NetUtil.Callback() {
            @Override
            public void onSucceed(String response) {
                callback.succeed(response);
            }

            @Override
            public void onFailed(String response) {

            }
        });
    }
}
