package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frecyclerview.BaseHolder;
import com.example.frecyclerview.MultiLayoutBaseAdapter;
import com.example.mac.douyun.R;

import java.util.List;

import Bean.GankBean;
import start.ImageLoader;

/**
 * Created by mac on 2018/5/2.
 */

public class GankAdapter extends MultiLayoutBaseAdapter {
    public GankAdapter(Context context, List data, int[] layoutIds) {
        super(context, data, layoutIds);
    }

    private int NORMAL = 0;



    @Override
    public int getItemType(int i) {
        Log.d("Fxy", "getItemCount: "+getItemCount());
        return NORMAL;
    }

    @Override
    public void onBinds(BaseHolder baseHolder, Object o, int i, int i1) {
        switch (i1){
            case 0:
                ImageView imageView = baseHolder.getView(R.id.gank_img);
                ImageLoader.with(getContext()).load(((GankBean)getData().get(i))
                        .getUrl())
                        .into(imageView)
                        .display();
                if(i==getData().size()-1){
                    loadMore();
                }
                break;
        }
    }

    private void loadMore(){

    }

    public void Refresh(List datalist){
        getData().addAll(datalist);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
