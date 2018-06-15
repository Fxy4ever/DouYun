package Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.example.mac.douyun.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gank.bean.GankBean;
import util.JsonUtil;
import util.NetUtil;
import config.Api;
import config.RequestOptions;

/**
 * Created by mac on 2018/5/2.
 */

public class GankAdapter<T> extends MultiLayoutBaseAdapter {
    private List<GankBean> datalist;


    public GankAdapter(Context context, List<GankBean> data, int[] layoutIds) {
        super(context, data, layoutIds);
        this.datalist = data;
    }

    private int NORMAL = 0;
    private int FOOTER = 1;

    public interface OnItemClickListener {
        void OnClickItem(View view, int position);
    }
    private OnItemClickListener MyonItemClickListener;
    public void onItemClickListner(OnItemClickListener onItemClickListener){
        MyonItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemType(int i) {
        if(i==datalist.size()-1){
            return FOOTER;
        }else{
            return NORMAL;
        }
    }

    private final RequestOptions options = new RequestOptions()
            .setPreloadPic(R.mipmap.ic_launcher_round)
            .setErrorPic(R.mipmap.ic_launcher);

    @Override
    public void onBinds(final BaseHolder baseHolder, Object o, final int i, int i1) {
        switch (i1){
            case 0:
                Log.d("Fxy", "onBinds: case 0");
                ImageView imageView = baseHolder.getView(R.id.gank_img);
//                ImageLoader.with(getContext())
//                        .load(datalist.get(i).getUrl())
//                        .into(imageView)
//                        .apply(options)
//                        .display();
                    Glide
                        .with(getContext())
                        .load(datalist.get(i).getUrl())
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher_round)
                        .into(imageView);
                if(MyonItemClickListener != null){
                    baseHolder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int position = baseHolder.getLayoutPosition();
                            MyonItemClickListener.OnClickItem(view,position);
                        }
                    });
                }
                break;
            case 1:
                Log.d("Fxy", "onBinds: case 1");
                loadMore();
                break;
        }
    }


    private static int n=2;
    private void loadMore(){
        Log.d("Fxy", "loadMore: ");
            Map<String,String> map = new HashMap<>();//没东西。。啥也不加
            n++;
            NetUtil.Get(Api.GankUrl()+n, map, new NetUtil.Callback() {
                @Override
                public void onSucceed(String response) {
                    JsonUtil.AddData(response,datalist);
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
}
