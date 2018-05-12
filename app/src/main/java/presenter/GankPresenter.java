package presenter;

import android.app.Activity;
import android.util.Log;

import model.lmp.GankImp;
import model.model_Interface.GankInterface;
import util.JsonUtil;
import view.frag_interface.IGankfragment;

/**
 * Created by mac on 2018/5/2.
 */

public class GankPresenter {
    IGankfragment iGankfragment;
    GankInterface gankInterface;

    public GankPresenter(IGankfragment iGankfragment) {
        this.iGankfragment = iGankfragment;
        gankInterface = new GankImp();
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


}
