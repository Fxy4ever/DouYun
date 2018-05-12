package view.frag_interface;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import Adapter.MediaAdapter;

/**
 * Created by mac on 2018/5/12.
 */

public interface IMediaFragment {

    MediaAdapter getAdapter();

    Context getIContext();

    SwipeRefreshLayout getSwipe();
}
