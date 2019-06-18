package com.forevermore.nikcname.nevermore.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.containers.MangaInstance;

import java.util.List;

public class MangaAdapter extends RecyclerView.Adapter<MangaAdapter.MangaViewHolder> {

    private List<MangaInstance> logoManga;

    public MangaAdapter(List<MangaInstance> logoManga){
        this.logoManga = logoManga;
    }

    public class MangaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageViewLogo;
        public TextView textViewTitle;
        public TextView textViewChapters;
        public TextView textViewDesc;

        public MangaViewHolder(@NonNull View itemView){
            super(itemView);

            imageViewLogo = itemView.findViewById(R.id.image_view_logo);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewChapters = itemView.findViewById(R.id.text_view_chapters);
            textViewDesc = itemView.findViewById(R.id.text_view_desc);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

        }
    }

    @NonNull
    @Override
    public MangaViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.manga_item,
                viewGroup, false);

        return new MangaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MangaViewHolder mangaViewHolder, int i) {

        MangaInstance manga = logoManga.get(i);

        mangaViewHolder.imageViewLogo.setImageBitmap(manga.getBitmap());
        mangaViewHolder.textViewDesc.setText(manga.getDescription());
        mangaViewHolder.textViewChapters.setText(manga.getChapters());
        mangaViewHolder.textViewTitle.setText(manga.getName());

    }

    @Override
    public int getItemCount() {
        return logoManga.size();
    }
}