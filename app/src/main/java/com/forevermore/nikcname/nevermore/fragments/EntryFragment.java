package com.forevermore.nikcname.nevermore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.containers.MangaInstance;

public class EntryFragment extends Fragment {

    private static MangaInstance mangaInstance;

    public EntryFragment() {

    }

    public static EntryFragment newInstance(MangaInstance manga) {
        EntryFragment fragment = new EntryFragment();
        mangaInstance = manga;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry, container, false);

        TextView textViewDescAll = v.findViewById(R.id.text_view_add_desc);

        textViewDescAll.setText(mangaInstance.getFullDesc());

        ImageView imageViewPicture = v.findViewById(R.id.image_view_picture);

        imageViewPicture.setImageBitmap(mangaInstance.getBitmap());

        return v;
    }
}
