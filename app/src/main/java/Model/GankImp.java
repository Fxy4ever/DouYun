package Model;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Bean.GankBean;
import Util.JsonUtil;
import Util.NetUtil;

/**
 * Created by mac on 2018/5/2.
 */

public class GankImp implements GankInterface {
    private List Ganklist = new ArrayList();

    @Override
    public void loadGankList() {
        Log.d("Fxy", "loadGankList: start");
        Map<String,String> map = new HashMap<>();//没东西。。啥也不加
        NetUtil.Get("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1", map, new NetUtil.Callback() {
            @Override
            public void onSucceed(String response) {
                JsonUtil.AddData(response,Ganklist);
            }

            @Override
            public void onFailed(String response) {

            }
        });
    }

    @Override
    public List ReturnList() {
        Log.d("Fxy", "ReturnList: "+Ganklist.size());
        return Ganklist;
    }
}
