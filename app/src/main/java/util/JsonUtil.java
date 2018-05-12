package util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Bean.GankBean;
import Bean.MediaBean;


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
    public static void AddData(String response, List<GankBean> beanList){
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
    public static void AddMedia(String response,List<MediaBean> beanList) {
        String showapi_res_body  = JsonUtil.JsonToString(response,"showapi_res_body");
        String pagebean = JsonUtil.JsonToString(showapi_res_body,"pagebean");
        String contentlist = JsonUtil.JsonToString(pagebean,"contentlist");
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(contentlist);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                MediaBean mediaBean = new MediaBean
                        .Builder()
                        .text(jsonObject.getString("text"))
                        .profile_image(jsonObject.getString("profile_image"))
                        .name(jsonObject.getString("name"))
                        .video_uri(jsonObject.getString("video_uri"))
                        .build();
                beanList.add(mediaBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
