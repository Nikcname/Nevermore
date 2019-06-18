package com.forevermore.nikcname.nevermore;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.forevermore.nikcname.nevermore.containers.MangaInstance;
import com.forevermore.nikcname.nevermore.fragments.ListFragment;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ListFragment listFragment = new ListFragment();
        ((ListFragment)listFragment).setOnPassListener(new ListFragment.PassmangaSelected() {
            @Override
            public void passSelected(MangaInstance manga) {
                System.out.println(manga);
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_layout_main, listFragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
