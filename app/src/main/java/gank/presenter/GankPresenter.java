package gank.presenter;

import android.app.Activity;
import android.util.Log;

import baseMvp.baseMvpView;
import gank.GankContract;
import gank.model.GankImp;
import gank.model.GankInterface;
import util.JsonUtil;
/**
 * Created by mac on 2018/5/2.
 */

public class GankPresenter implements GankContract.IGankPresenter {
    private GankContract.IGankfragment iGankfragment;
    private GankInterface gankInterface;

    public GankPresenter(GankContract.IGankfragment iGankfragment) {
        this.iGankfragment = iGankfragment;
        gankInterface = new GankImp();
        iGankfragment.setPresenter(this);
    }


    /**
     * Presenter -> view
     */
    public void requestGank(){
        Log.d("Fxy", "requestGank: ");
        gankInterface.loadGankList(new GankInterface.loadCallback() {
            @Override
            public void Succeed(String response) {
                if(iGankfragment!=null){
                    JsonUtil.AddData(response,iGankfragment.GetGankList());
                    ((Activity)(iGankfragment.getContext())).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iGankfragment.GetAdapter().notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void Failed(String response) {

            }
        });
    }

    public void RefreshData(){
        gankInterface.RefreshList(new GankInterface.loadCallback() {
            @Override
            public void Succeed(String response) {
                if(iGankfragment!=null){
                    iGankfragment.GetGankList().clear();
                    JsonUtil.AddData(response,iGankfragment.GetGankList());
                    ((Activity)(iGankfragment.getContext())).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iGankfragment.GetAdapter().notifyDataSetChanged();
                        }
                    });
                }
            }

            @Override
            public void Failed(String response) {

            }
        });
    }

    @Override
    public void start() {
        requestGank();
    }
}
