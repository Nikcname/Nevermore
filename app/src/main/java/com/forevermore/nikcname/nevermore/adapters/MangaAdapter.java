package com.forevermore.nikcname.nevermore.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
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
    private OnClickedManga onClickedManga;

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

            imageViewLogo = itemView.findViewById(R.id.image_view_manga_picture_item);
            textViewTitle = itemView.findViewById(R.id.text_view_manga_title_item);
            textViewChapters = itemView.findViewById(R.id.text_view_chapters_amnt_item);
            textViewDesc = itemView.findViewById(R.id.text_view_manga_desc_item);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onClickedManga.mangaClicked(logoManga.get(getAdapterPosition()));
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

        mangaViewHolder.imageViewLogo.setImageBitmap(manga.getPreviewBitmap());
        mangaViewHolder.textViewDesc.setText(manga.getPreviewDescription());
        mangaViewHolder.textViewChapters.setText(manga.getPreviewAvailableChapters());
        mangaViewHolder.textViewTitle.setText(manga.getPreviewName());

    }

    @Override
    public int getItemCount() {
        return logoManga.size();
    }

    public interface OnClickedManga{
        void mangaClicked(MangaInstance mangaClicked);
    }

    public void setOnClickMangaListener(OnClickedManga onClickedManga){
        this.onClickedManga = onClickedManga;
    }
}
