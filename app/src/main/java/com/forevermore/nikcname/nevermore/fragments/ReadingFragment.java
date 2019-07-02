package com.forevermore.nikcname.nevermore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forevermore.nikcname.nevermore.R;

public class ReadingFragment extends Fragment {

    private String link;
    public ReadingFragment() {}

    public static ReadingFragment newInstance(String link) {
        ReadingFragment fragment = new ReadingFragment();
        Bundle args = new Bundle();
        args.putString("link", link);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            link = getArguments().getString("link");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reading, container, false);

        Log.d("sdd", link);

        return v;
    }

}
