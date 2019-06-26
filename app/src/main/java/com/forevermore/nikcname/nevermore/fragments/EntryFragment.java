package com.forevermore.nikcname.nevermore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.adapters.ChapterAdapter;
import com.forevermore.nikcname.nevermore.containers.MangaInstance;

public class EntryFragment extends Fragment {

    private static MangaInstance mangaInstance;
    private RecyclerView recyclerViewForChapters;
    private RecyclerView.Adapter adapterForChapters;
    private RecyclerView.LayoutManager managerForChapters;
    private Button startReading;
    private InterfacePassSelectedLink interfacePassSelectedLink;

    public EntryFragment() {}

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

        recyclerViewForChapters = v.findViewById(R.id.recycler_for_mangas_entry);
        managerForChapters = new LinearLayoutManager(getContext());
        recyclerViewForChapters.setLayoutManager(managerForChapters);
        adapterForChapters = new ChapterAdapter(mangaInstance.getChapterDescs());
        recyclerViewForChapters.setAdapter(adapterForChapters);
        startReading = v.findViewById(R.id.button_start_reading_entry);

        TextView textViewFullDesc = v.findViewById(R.id.text_view_manga_desc_entry);
        ImageView imageViewPicture = v.findViewById(R.id.image_view_picture_entry);
        textViewFullDesc.setMovementMethod(new ScrollingMovementMethod());
        textViewFullDesc.setText(mangaInstance.getFullDesc());
        imageViewPicture.setImageBitmap(mangaInstance.getBitmap());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        startReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String linkOfMangaForRead = "https://manga-chan.me" + mangaInstance.getChapterOne();
                interfacePassSelectedLink.passLink(linkOfMangaForRead);

            }
        });
    }

    public interface InterfacePassSelectedLink{
        void passLink(String link);
    }

    public void setOnCallbackListener(InterfacePassSelectedLink interfacePassSelectedLink){
        this.interfacePassSelectedLink = interfacePassSelectedLink;
    }
}
