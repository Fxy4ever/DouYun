package View;


import android.content.Context;

import java.util.List;

import Adapter.GankAdapter;
import Bean.GankBean;

public interface IGankfragment {
    List<GankBean> GetGankList();
    GankAdapter GetAdapter();
    Context getContext();
}
