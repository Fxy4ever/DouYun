package Presenter;

import android.util.Log;

import java.util.List;
import java.util.logging.Handler;

import Model.GankImp;
import Model.GankInterface;
import View.IGankfragment;

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
     * Presenter -> model
     */
    public List getList(){
        return gankInterface.ReturnList();
    }

    /**
     * Presenter -> view
     */
    public void requestGank(){
        Log.d("Fxy", "requestGank: ");


        new Thread(new Runnable() {
            @Override
            public void run() {
                gankInterface.loadGankList();
                if(iGankfragment!=null){
                    Log.d("Fxy", "run: "+getList().size());
                    iGankfragment.GetGankList(getList());
                    iGankfragment.InvalidateAdapter().notifyDataSetChanged();
                }
            }
        }).start();
    }
}
