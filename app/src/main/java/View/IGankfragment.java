package View;


import java.util.List;

import Adapter.GankAdapter;

public interface IGankfragment {
    void GetGankList(List gankList);
    void GankRefresh(List ganklist);
    GankAdapter InvalidateAdapter();
}
