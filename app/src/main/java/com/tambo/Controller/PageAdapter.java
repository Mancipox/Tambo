package com.tambo.Controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
    private int numberOfTabs;

    public PageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
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
    public int getCount() {
        return numberOfTabs;
    }
}
