package baseMvp;

/**
 * Created by mac on 2018/6/14.
 */

public interface basePresenter<T extends baseMvpView> {
    void start();
}
