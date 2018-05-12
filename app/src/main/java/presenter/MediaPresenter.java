package presenter;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.example.mac.douyun.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.MediaAdapter;
import Bean.MediaBean;
import model.IMediaInterface;
import model.MediaImp;
import util.JsonUtil;
import view.IMediaFragment;

/**
 * Created by mac on 2018/5/12.
 */

public class MediaPresenter {
    private IMediaFragment fragment;
    private IMediaInterface mediaInterface;
    private List<MediaBean> beanList = new ArrayList<>();
    private int[] layoutId={R.layout.media_item,R.layout.gank_load_item};
    private boolean isRefresh = false;

    public MediaPresenter(IMediaFragment fragment) {
        this.fragment = fragment;
        mediaInterface = new MediaImp();
    }

    public MediaAdapter initAdatper(){
        MediaAdapter mediaAdapter = new MediaAdapter(fragment.getIContext(),beanList,layoutId);
        return mediaAdapter;
    }

    public List<MediaBean> getBeanList(){
        return beanList;
    }

    public void loadData(){
        mediaInterface.loadList(new IMediaInterface.LoadCallback() {
            @Override
            public void succeed(String response) {
                JsonUtil.AddMedia(response,beanList);
                ((Activity)(fragment.getIContext())).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragment.getAdapter().notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void failed(String response) {
                Log.d("Fxy", "failed: "+response);
            }
        });
    }

    public void Refresh(){
        final SwipeRefreshLayout swipeRefreshLayout = fragment.getSwipe();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isRefresh){
                    isRefresh = true;
                    swipeRefreshLayout.setRefreshing(false);
                    RefreshData();
                    isRefresh = false;
                }

            }
        });
    }
    public void RefreshData(){
        mediaInterface.RefreshList(new IMediaInterface.LoadCallback() {
            @Override
            public void succeed(String response) {
                beanList.clear();
                JsonUtil.AddMedia(response,beanList);
                ((Activity)(fragment.getIContext())).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fragment.getAdapter().notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void failed(String response) {

            }
        });
    }
}
