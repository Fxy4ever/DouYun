package gank.model;

/**
 * Created by mac on 2018/6/14.
 */

public interface GankInterface {

    void loadGankList(GankInterface.loadCallback callback);
    void RefreshList(GankInterface.loadCallback callback);

    interface loadCallback{
        void Succeed(String response);
        void Failed(String response);
    }

}
