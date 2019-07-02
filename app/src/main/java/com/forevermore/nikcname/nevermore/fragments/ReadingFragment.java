package com.forevermore.nikcname.nevermore.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.downloaders.ImageDownloader;

public class ReadingFragment extends Fragment {

    private String link;
    private ImageView imageView;
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
        imageView = v.findViewById(R.id.image_view_reading);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        ImageDownloader imageDownloader = new ImageDownloader();
        imageDownloader.setNotifyListener(new ImageDownloader.NotifyChange() {
            @Override
            public void notifyAdapter(Bitmap bitmap, int i) {
                if (bitmap != null)
                    imageView.setImageBitmap(bitmap);
            }
        });
        imageDownloader.execute(link);
    }
}
