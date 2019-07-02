package com.forevermore.nikcname.nevermore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.adapters.PagerAdapterReading;

import java.util.List;

public class ReadingViewFragment extends Fragment {

    private static List<String> listUri;

    public ReadingViewFragment() {}

    public static ReadingViewFragment newInstance(List<String> list) {
        ReadingViewFragment fragment = new ReadingViewFragment();
        listUri = list;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reading_view, container, false);

        ViewPager vp = v.findViewById(R.id.view_pager_view);

        vp.setAdapter(new PagerAdapterReading(getFragmentManager(), listUri));
        vp.setOffscreenPageLimit(10);

        return v;
    }

}
