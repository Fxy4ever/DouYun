package View;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.mac.douyun.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.GankAdapter;
import Presenter.GankPresenter;

/**
 * Created by mac on 2018/5/2.
 */

@SuppressLint("ValidFragment")
public class GankFragment extends Fragment implements IGankfragment {
    private Context context;
    private RecyclerView recyclerView;
    private GankAdapter gankAdapter;
    private List datalist;
    private List datalist2;
    private int layoutId[]= {R.layout.gank_normal_item};
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefresh=false;
    private View view;
    GankPresenter gankPresenter;

    @SuppressLint("ValidFragment")
    public GankFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Fxy", "onCreateView: ");
        view=inflater.inflate(R.layout.gank_fragment,container,false);
        gankPresenter = new GankPresenter(this);
        gankPresenter.requestGank();
        initView();
        return view;
    }

    private void initView(){
        recyclerView = view.findViewById(R.id.gank_recyclerview);
        gankAdapter = new GankAdapter(context,datalist,layoutId);
//        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setAdapter(gankAdapter);
        recyclerView.setLayoutManager(manager);
        swipeRefreshLayout = ((Activity)context).findViewById(R.id.gank_swipe);
    }


    @Override
    public void GetGankList(List gankList) {
        datalist = gankList;
    }

    @Override
    public void GankRefresh(final List ganklist) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isRefresh){
                    isRefresh = true;
                    datalist.clear();
                    gankAdapter.Refresh(ganklist);
                    swipeRefreshLayout.setRefreshing(false);
                    isRefresh = false;
                }

            }
        });
    }

    @Override
    public GankAdapter InvalidateAdapter() {
        return gankAdapter;
    }


}
