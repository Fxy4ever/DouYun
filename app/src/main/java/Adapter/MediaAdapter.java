package Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.example.mac.douyun.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hundredSister.bean.MediaBean;
import util.MediaLoader.FMediaPlayer;
import config.Api;
import start.ImageLoader;
import util.JsonUtil;
import util.NetUtil;

/**
 * Created by mac on 2018/5/12.
 */

public class MediaAdapter<T> extends MultiLayoutBaseAdapter {
    private List<MediaBean> datalist;
    private OnButtonClickListener MyonButtonClickListener;

    public MediaAdapter(Context context, List<MediaBean> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.datalist = data;
    }
    int NORMAL = 0;
    int FOOTER = 1;
    @Override
    public int getItemType(int i) {
        if(i==datalist.size()-1){
            return FOOTER;
        }else{
            return NORMAL;
        }
    }

    @Override
    public void onBinds(final BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0:
                TextView name = baseHolder.getView(R.id.media_name);
                TextView title = baseHolder.getView(R.id.media_title);
                ImageView avatar = baseHolder.getView(R.id.media_avatar);
                ImageView media = baseHolder.getView(R.id.media_img);
                Button play = baseHolder.getView(R.id.media_play);
                name.setText(datalist.get(i).getName());
                String mtitle = datalist.get(i).getText();
                mtitle=mtitle.substring(9,mtitle.length()-6);
                title.setText(mtitle);
                ImageLoader
                        .with(getContext())
                        .load(datalist.get(i).getProfile_image())
                        .into(avatar)
                        .display();
                FMediaPlayer.setImageViewFace(getContext(),media,datalist.get(i).getVideo_uri());
                if(MyonButtonClickListener != null){
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = baseHolder.getLayoutPosition();
                            MyonButtonClickListener.OnClickButton(view,position);
                        }
                    });
                }
                break;
            case 1:
                loadMore();
                break;
        }
    }
    private static int n=2;
    private void loadMore(){
        Map<String,String> map = new HashMap<>();
        map.put("type","41");
        map.put("page",String.valueOf(n));
        map.put("showapi_sign","8b04f981ad524a7d84104e8360d1ae53");
        map.put("showapi_appid","38542");
        NetUtil.Get(Api.MediaUrl(), map, new NetUtil.Callback() {
            @Override
            public void onSucceed(String response) {
                JsonUtil.AddMedia(response,datalist);
                ((Activity)getContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailed(String response) {

            }
        });
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    public interface OnButtonClickListener {
        void OnClickButton(View view, int position);
    }
    public void onButtonClickListner(OnButtonClickListener onButtonClickListener){
        MyonButtonClickListener = onButtonClickListener;
    }

}
