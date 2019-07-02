package com.forevermore.nikcname.nevermore.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.forevermore.nikcname.nevermore.fragments.ReadingFragment;

import java.util.List;

public class PagerAdapterReading extends SmartFragmentStatePagerAdapter {

    private List<String> links;

    public PagerAdapterReading(FragmentManager fragmentManager, List<String> links) {
        super(fragmentManager);
        this.links = links;
    }

    @Override
    public Fragment getItem(int i) {

        ReadingFragment rf = ReadingFragment.newInstance(links.get(i));

        return rf;
    }

    @Override
    public int getCount() {
        return links.size();
    }
}
