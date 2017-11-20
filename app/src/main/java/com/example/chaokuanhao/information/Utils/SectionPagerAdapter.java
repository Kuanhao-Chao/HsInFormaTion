package com.example.chaokuanhao.information.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chaokuanhao on 21/11/2017.
 */


public class SectionPagerAdapter extends FragmentPagerAdapter {

    private static final String TAG = SectionPagerAdapter.class.getSimpleName();
    private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
    public SectionPagerAdapter(FragmentManager fragmentManager) { super(fragmentManager);}
    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
    }
}
