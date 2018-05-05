package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mac on 2018/5/2.
 */

public class mFragmentPagerAdapter extends FragmentPagerAdapter {
    List fragmentList;
    List titleList;

    public  mFragmentPagerAdapter(FragmentManager fm, List fragmentList, List titleList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.titleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment) fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return (CharSequence) titleList.get(position);
    }
}
