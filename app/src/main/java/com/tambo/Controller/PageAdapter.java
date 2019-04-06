package com.tambo.Controller;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tambo.R;

/**
 * Adapter to set Fragments acording to Tablayout
 */
public class PageAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;
    private Context context;

    public PageAdapter(FragmentManager fm, int numberOfTabs, Context context) {
        super(fm);
        this.context = context;
        this.numberOfTabs=numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0: return new StudentFragment();
            case 1: return new ProfessorFragment();
            default: return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.text_learn);
            case 1:
                return context.getString(R.string.text_teach);
            default: return null;
        }
    }



    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
