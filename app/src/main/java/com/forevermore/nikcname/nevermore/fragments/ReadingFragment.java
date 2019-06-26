package com.forevermore.nikcname.nevermore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.downloaders.ReadingPageDownloader;

public class ReadingFragment extends Fragment {

    private String link;

    public ReadingFragment(){}

    public static ReadingFragment newInstance(String link) {

        Bundle args = new Bundle();
        args.putString("link", link);
        ReadingFragment fragment = new ReadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        link = getArguments().getString("link");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reading, container, false);

        new ReadingPageDownloader().execute(link);

        return v;
    }

}
