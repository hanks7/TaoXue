package com.taoxue.ui.module.search.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.taoxue.base.BaseFragment;
import com.taoxue.ui.module.search.fragment.SupplierAllResourcesFragment;
import com.taoxue.ui.module.search.fragment.SupplierHomeFragment;
import com.taoxue.ui.module.search.fragment.SupplierKUFragment;

public class ViewpageAdapter extends FragmentPagerAdapter {

    private final String[] mTitles;

    public ViewpageAdapter(FragmentManager fm, String[] strings) {
        super(fm);
        mTitles = strings;
    }

    @Override
    public Fragment getItem(int position) {

        BaseFragment fragment = null;
        if (position == 0) {
            fragment = new SupplierHomeFragment();
        } else if (position == 1) {
            fragment = new SupplierKUFragment();
        } else if (position == 2) {
            fragment = new SupplierAllResourcesFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}