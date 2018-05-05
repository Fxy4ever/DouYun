package Util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Bean.GankBean;


/**
 * Created by mac on 2018/4/3.
 */

public class JsonUtil {
    public static String JsonToString(String response, String json){
        try {
            if(response!=null){
                JSONObject jsonObject = new JSONObject(response);
                String targetInfo = jsonObject.getString(json);
                return targetInfo;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void AddData(String response, List beanList){
        try {
            String results = JsonUtil.JsonToString(response,"results");
            JSONArray jsonArray = new JSONArray(results);
            for(int i = 0 ; i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                GankBean gankBean =
                        new GankBean.Builder()
                                .url(jsonObject.getString("url"))
                                .who(jsonObject.getString("who"))
                                .publishedAt(jsonObject.getString("publishedAt"))
                                .desc(jsonObject.getString("desc"))
                                .build();
                beanList.add(gankBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
