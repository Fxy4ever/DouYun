package Util;

import android.accounts.NetworkErrorException;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 这是一个利用HttpURLConnection实现的工具类
 * 网络权限：<uses-permission android:name="android.permission.INTERNET"/>
 */

public class NetUtil{

    private static final int CPU_CORE_COUNT = Runtime.getRuntime().availableProcessors();//cpu核心数量
    private static final int MAX_POOL_SIZE = CPU_CORE_COUNT * 2 + 1;//线程池的最大线程数量
    private static final int CORE_POOL_SIZE = CPU_CORE_COUNT + 1;//线程池的核心线程数量
    private static final long WAITE_TIME = 10L;//线程等待时间

    //创建线程池的工厂
    private static final ThreadFactory threadFactory = new ThreadFactory() {
        private final AtomicInteger count = new AtomicInteger(1);//线程安全的加减操作
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,"NetUtil"+count.getAndIncrement());//名字按照AtomicInteger来命名
        }
    };
    private static final Executor THREAD_POOL_EXECUTOR =
            new ThreadPoolExecutor
                            (CORE_POOL_SIZE,
                            MAX_POOL_SIZE,
                            WAITE_TIME,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>(),
                            threadFactory);

    public static void Post(final String url, final Map<String,String> params, final Callback callback){
        Runnable post = new Runnable() {
            @Override
            public void run() {
                final String response = NetUtil.post(url,params);
                new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            int code = jsonObject.getInt("code");
                            if(code==200){
                                callback.onSucceed(response);
                            }else{
                                callback.onFailed(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }
        };
        THREAD_POOL_EXECUTOR.execute(post);
    }

    public static void Get(final String url, final Map<String,String> params, final Callback callback){
        Runnable get = new Runnable() {
            @Override
            public void run() {
                final String response = NetUtil.get(url,params);
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    int code = jsonObject.getInt("code");
                    if(true){
                        callback.onSucceed(response);
                    }else{
                        callback.onFailed(response);
                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        };
        THREAD_POOL_EXECUTOR.execute(get);
    }


    private static String post(String url,Map<String,String> params){

        BufferedReader reader = null;
        HttpURLConnection conn = null;
        try{
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            //post不用拼接
            String data = configuraParam(params);
            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(data);
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            if(responseCode==200) {
                InputStream inputStream = conn.getInputStream();
                String line;
                StringBuilder response = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while((line=reader.readLine())!=null){
                    response.append(line);
                }
                return response.toString();
            }else{
                throw new NetworkErrorException("网络连接错误"+responseCode);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NetworkErrorException e) {
            e.printStackTrace();
        }finally {
            if(reader!=null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return null;
    }

    private static String get(String path,Map<String,String> params){
        HttpURLConnection conn = null;
        try {
            URL url = configuraGetUrl(path,params);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(10000);
            if(conn.getResponseCode()==200){
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] data = new byte[1024];
                int len = -1;
                while ((len=is.read(data))!=-1){
                    baos.write(data,0,len);
                }
                is.close();
                String response = baos.toString();
                Log.d("fxy", "get: "+response);
                baos.close();
                return response;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(conn!=null) conn.disconnect();
        }
        return null;
    }

    /**
     * 生成字符串
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String configuraParam(Map<String, String> params) throws UnsupportedEncodingException {
        String str = "";
        if(params.size()==0){
            return str;
        }
        for (Map.Entry<String,String> entry:params.entrySet()){
            str = entry.getKey()+"="+entry.getValue()+"&";
        }
        String subStr = str.substring(str.length()-1);
        return URLEncoder.encode(subStr,"UTF-8");
    }

    /**
     * 拼接GET参数
     * @param URL
     * @param params
     * @return
     */
    private static URL configuraGetUrl(String URL, Map<String, String> params) throws UnsupportedEncodingException, MalformedURLException {
        String url=URL;
        if (params.size()!=0){
            url= URL+"?";
            url += configuraParam(params);
        }
        return new URL(url);
    }


    public interface  Callback{
        void onSucceed(String response);
        void onFailed(String response);
    }

}
