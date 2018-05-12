package view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mac.douyun.R;

import Adapter.MediaAdapter;
import model.IMediaInterface;
import presenter.MediaPresenter;

/**
 * Created by mac on 2018/5/12.
 */

public class MediaFragment extends Fragment implements IMediaFragment{
    private View view;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MediaAdapter mediaAdapter;
    private MediaPresenter presenter;
    private Button play;
    private CallBackValue callBackValue;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue = (CallBackValue) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.media_fragment,container,false);
        presenter = new MediaPresenter(this);
        presenter.loadData();
        init();
        return view;
    }
    private void init(){
        recyclerView = view.findViewById(R.id.media_recyclerview);
        swipeRefreshLayout = view.findViewById(R.id.media_swipe);
        play = view.findViewById(R.id.media_play);
        mediaAdapter = presenter.initAdatper();
        recyclerView.setAdapter(mediaAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mediaAdapter.onButtonClickListner(new MediaAdapter.OnButtonClickListener() {
            @Override
            public void OnClickButton(View view, int position) {
                String url = presenter.getBeanList().get(position).getVideo_uri();
                callBackValue.SendMediaValue(url);
            }
        });
        presenter.Refresh();//设置监听
    }

    @Override
    public MediaAdapter getAdapter() {
        return mediaAdapter;
    }

    @Override
    public Context getIContext() {
        return getContext();
    }

    @Override
    public SwipeRefreshLayout getSwipe() {
        return swipeRefreshLayout;
    }

    public interface CallBackValue{
        void SendMediaValue(String Value);
    }

}
