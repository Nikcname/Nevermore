package com.forevermore.nikcname.nevermore.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
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
    private String siteUrl = "https://manga-chan.me/manga/new?offset=15480";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private List<MangaInstance> logoManga = new ArrayList<>();
    private List<MangaInstance> mangaInstancesMain = new ArrayList<>();
    private PassmangaSelected passmangaSelected;

    public ListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = v.findViewById(R.id.recycler_manga_list);
        recyclerView.setHasFixedSize(true);
        manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new MangaAdapter(logoManga);
        recyclerView.setAdapter(adapter);

        ((MangaAdapter)adapter).setOnClickMangaListener(new MangaAdapter.OnClickedManga() {
            @Override
            public void mangaClicked(MangaInstance mangaClicked) {
                passmangaSelected.passSelected(mangaClicked);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PageDownloader pageDownloader = new PageDownloader();
        pageDownloader.setOnResultListener(new PageDownloader.ResultList() {
            @Override
            public void passMangaInstance(List<MangaInstance> mangas) {

                mangaInstancesMain = mangas;
                downloadImages(mangas);

            }

        });

        pageDownloader.execute(siteUrl);
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
            imageDownloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mangas.get(i).getImageUrl());
        }
    }

    public void notifyAdapterChange(Bitmap bitmap, int i){

        MangaInstance temp = mangaInstancesMain.get(i);
        temp.setBitmap(bitmap);
        logoManga.add(temp);
        adapter.notifyDataSetChanged();
    }

    public interface PassmangaSelected{
        void passSelected(MangaInstance manga);
    }

    public void setOnPassListener(PassmangaSelected passmangaSelected){
        this.passmangaSelected = passmangaSelected;
    }

}
