package com.forevermore.nikcname.nevermore.fragments;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.adapters.MangaAdapter;
import com.forevermore.nikcname.nevermore.containers.MangaInstance;
import com.forevermore.nikcname.nevermore.downloaders.ImageDownloader;
import com.forevermore.nikcname.nevermore.downloaders.PageDownloader;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private String siteMainUriForRecycler = "https://manga-chan.me/manga/new";
//    private String siteMainUriForRecycler = "https://manga-chan.me/manga/new?offset=15480";
    private RecyclerView recyclerViewForMangaList;
    private RecyclerView.Adapter adapterForMangaList;
    private RecyclerView.LayoutManager managerForMangaList;
    private List<MangaInstance> mangaInstancesOfList = new ArrayList<>();
    private CallbackListFragment callbackListFragment;

    public ListFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewFragmentList = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerViewForMangaList = viewFragmentList.findViewById(R.id.recycler_for_mangas_list);
        recyclerViewForMangaList.setHasFixedSize(true);
        managerForMangaList = new LinearLayoutManager(getContext());
        recyclerViewForMangaList.setLayoutManager(managerForMangaList);
        adapterForMangaList = new MangaAdapter(mangaInstancesOfList);
        recyclerViewForMangaList.setAdapter(adapterForMangaList);

        ((MangaAdapter)adapterForMangaList).setOnClickMangaListener(new MangaAdapter.OnClickedManga() {
            @Override
            public void mangaClicked(MangaInstance mangaClicked) {
                callbackListFragment.itemPressed(mangaClicked);
            }
        });

        return viewFragmentList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PageDownloader pageDownloader = new PageDownloader();
        pageDownloader.setListener(new PageDownloader.CallbackPageDownloader() {
            @Override
            public void mangaInstance(MangaInstance mangaInstance) {
                mangaInstancesOfList.add(mangaInstance);
                adapterForMangaList.notifyDataSetChanged();
            }
        });
        pageDownloader.execute(siteMainUriForRecycler);
    }

    public interface CallbackListFragment{
        void itemPressed(MangaInstance manga);
    }

    public void setListener(CallbackListFragment callbackListFragment){
        this.callbackListFragment = callbackListFragment;
    }

}
