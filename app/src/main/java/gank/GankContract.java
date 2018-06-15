package gank;

import android.content.Context;

import java.util.List;

import Adapter.GankAdapter;
import gank.bean.GankBean;
import baseMvp.baseMvpView;
import baseMvp.basePresenter;

/**
 * 协议接口 方便管理接口
 * Created by mac on 2018/6/14.
 */

public interface GankContract {

    interface IGankfragment extends baseMvpView{

        List<GankBean> GetGankList();

        GankAdapter GetAdapter();

        Context getContext();

    }

    interface IGankPresenter extends basePresenter{

    }

}
