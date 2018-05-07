package Model;

import java.util.List;

import Bean.GankBean;

/**
 * Created by mac on 2018/5/2.
 */

public interface GankInterface {
    void loadGankList(GankInterface.loadCallback callback);
    void RefreshList(GankInterface.loadCallback callback);

    interface loadCallback{
        void Succeed(String response);
        void Failed(String response);
    }
}
