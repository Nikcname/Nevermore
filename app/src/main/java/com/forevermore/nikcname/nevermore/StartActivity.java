package com.forevermore.nikcname.nevermore;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.forevermore.nikcname.nevermore.containers.MangaInstance;
import com.forevermore.nikcname.nevermore.downloaders.IntPageDownloader;
import com.forevermore.nikcname.nevermore.fragments.EntryFragment;
import com.forevermore.nikcname.nevermore.fragments.ListFragment;
import com.forevermore.nikcname.nevermore.fragments.ReadingFragment;

import java.util.List;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ListFragment listFragment = new ListFragment();

        listFragment.setOnPassListener(new ListFragment.PassmangaSelected() {
            @Override
            public void passSelected(MangaInstance manga) {
                IntPageDownloader pageDownloader = new IntPageDownloader();
                pageDownloader.setOnDescDownloadedListener(new IntPageDownloader.PassListAndDesc() {
                    @Override
                    public void passLists(String descrAll, List<String> chapterDescs, List<String> chapterUris, String uriOfChapterOne) {
                        manga.setChapterDescs(chapterDescs);
                        manga.setChapterOne(uriOfChapterOne);
                        manga.setChapterUris(chapterUris);
                        manga.setFullDesc(descrAll);

                        EntryFragment entryFragment = EntryFragment.newInstance(manga);
                        entryFragment.setOnCallbackListener(new EntryFragment.InterfacePassSelectedLink() {
                            @Override
                            public void passLink(String link) {
                                startReading(link);
                            }
                        });

                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

                        fragmentTransaction.setCustomAnimations(
                                R.anim.enter_from_right,
                                R.anim.exit_to_left,
                                R.anim.enter_from_left,
                                R.anim.exit_to_right
                        );

                        fragmentTransaction.replace(R.id.frame_layout_for_fragments, entryFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                });
                pageDownloader.execute("https://manga-chan.me" + manga.getUrlOfManga());
            }
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_for_fragments, listFragment);
        fragmentTransaction.commit();

    }

    private void startReading(String link){
        ReadingFragment readingFragment = ReadingFragment.newInstance(link);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.setCustomAnimations(
                R.anim.enter_from_right,
                R.anim.exit_to_left,
                R.anim.enter_from_left,
                R.anim.exit_to_right
        );
        fragmentTransaction.replace(R.id.frame_layout_for_fragments, readingFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}
