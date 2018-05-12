package view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mac.douyun.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.GankAdapter;
import Bean.GankBean;
import presenter.GankPresenter;
import view.frag_interface.IGankfragment;

/**
 * Created by mac on 2018/5/2.
 */

@SuppressLint("ValidFragment")
public class GankFragment extends Fragment implements IGankfragment {
    private Context context;
    private RecyclerView recyclerView;
    private GankAdapter gankAdapter;
    private List<GankBean> datalist;
    private int layoutId[]= {R.layout.gank_normal_item,R.layout.gank_load_item};
    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean isRefresh=false;
    private View view;
    GankPresenter gankPresenter;

    CallBackValue callBackValue;
    public interface CallBackValue{
        void SendValue(String Value);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue = (CallBackValue) getActivity();
    }

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
        datalist = new ArrayList<>();
        gankAdapter = new GankAdapter(context,datalist,layoutId);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setAdapter(gankAdapter);
        recyclerView.setLayoutManager(manager);
        swipeRefreshLayout = view.findViewById(R.id.gank_swipe);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isRefresh){
                    isRefresh = true;
                    gankPresenter.RefreshData();
                    swipeRefreshLayout.setRefreshing(false);
                    isRefresh = false;
                }

            }
        });
        gankAdapter.onItemClickListner(new GankAdapter.OnItemClickListener() {
            @Override
            public void OnClickItem(View view, int position) {
                String url = datalist.get(position).getUrl();
                callBackValue.SendValue(url);
            }
        });
    }


    @Override
    public List<GankBean> GetGankList() {
        return datalist;
    }

    @Override
    public GankAdapter GetAdapter() {
        return gankAdapter;
    }

    @Nullable
    @Override
    public Context getContext() {
        return context;
    }
}
