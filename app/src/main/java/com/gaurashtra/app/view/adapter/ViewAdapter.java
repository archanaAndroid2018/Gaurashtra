package com.gaurashtra.app.view.adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.gaurashtra.app.R;
import com.gaurashtra.app.view.fragment.CategoryFragment;
import com.gaurashtra.app.view.fragment.HomeFragment;
import com.gaurashtra.app.view.fragment.MeFragment;


public class ViewAdapter extends FragmentStateAdapter {

    private static final int INDEX_BUFFER = 0;
    private static final int INDEX_VALUES1 = 1;
    private static final int INDEX_RETREAT = 3;
    private static final int INDEX_VALUES = 2;

    Context context;

    public ViewAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case INDEX_BUFFER:
                return HomeFragment.newInstance(R.drawable.listhome);
            case INDEX_VALUES:
                return CategoryFragment.newInstance(R.drawable.listcat);
            case INDEX_VALUES1:
                return MeFragment.newInstance(R.drawable.listme);
            case INDEX_RETREAT:
//                return SearchFragment.newInstance(R.drawable.listsearch);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

}
