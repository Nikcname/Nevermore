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

//    private String siteUrl = "https://manga-chan.me/manga/new";
    private String siteMainUriForRecycler = "https://manga-chan.me/manga/new?offset=15480";
    private RecyclerView recyclerViewForMangaList;
    private RecyclerView.Adapter adapterForMangaList;
    private RecyclerView.LayoutManager managerForMangaList;
    private List<MangaInstance> mangaInstancesOfList = new ArrayList<>();
    private List<MangaInstance> mangaInstancesTempList = new ArrayList<>();
    private PassmangaSelected passmangaSelected;

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

        ((MangaAdapter) adapterForMangaList).setOnClickMangaListener(mangaClicked ->
                passmangaSelected.passSelected(mangaClicked));

        return viewFragmentList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PageDownloader pageDownloader = new PageDownloader();
        pageDownloader.setOnResultListener(mangas -> {
            mangaInstancesTempList = mangas;
            downloadImages(mangas);});

        pageDownloader.execute(siteMainUriForRecycler);
    }

    public void downloadImages(List<MangaInstance> mangas){
        for (int i = 0; i < mangas.size(); i++){
            ImageDownloader imageDownloader = new ImageDownloader(i);
            imageDownloader.setNotifyListener(new ImageDownloader.NotifyChange() {
                @Override
                public void notifyAdapter(Bitmap bitmap, int i) {
                    notifyAdapterChange(bitmap, i);
                }
            });
//            imageDownloader.execute(urls.get(i));
            imageDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mangas.get(i)
                    .getImageUrl());
        }
    }

    public void notifyAdapterChange(Bitmap bitmap, int i){

        MangaInstance temp = mangaInstancesTempList.get(i);
        temp.setBitmap(bitmap);
        mangaInstancesOfList.add(temp);
        adapterForMangaList.notifyDataSetChanged();
    }

    public interface PassmangaSelected{
        void passSelected(MangaInstance manga);
    }

    public void setOnPassListener(PassmangaSelected passmangaSelected){
        this.passmangaSelected = passmangaSelected;
    }

}
