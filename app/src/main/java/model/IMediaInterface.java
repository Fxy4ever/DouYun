package model;

/**
 * Created by mac on 2018/5/12.
 */

public interface IMediaInterface {
    void loadList(LoadCallback callback);
    void RefreshList(LoadCallback callback);
    interface LoadCallback{
        void succeed(String response);
        void failed(String response);
    }
}
