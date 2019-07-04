package com.forevermore.nikcname.nevermore.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forevermore.nikcname.nevermore.R;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChViewHolder> {

    private List<String> mangaChapters;
    private OnChapterSelected onChapterSelected;

    public ChapterAdapter(List<String> mangaChapters){
        this.mangaChapters = mangaChapters;
    }

    @NonNull
    @Override
    public ChViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chapter_item, viewGroup, false);

        return new ChViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChViewHolder chViewHolder, int i) {

        chViewHolder.textViewChapter.setText(mangaChapters.get(i));

    }

    @Override
    public int getItemCount() {
        return mangaChapters.size();
    }

    public class ChViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewChapter;
        public ChViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewChapter = itemView.findViewById(R.id.text_view_chapter_entry);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onChapterSelected.passItemNum(getAdapterPosition());
        }
    }

    public interface OnChapterSelected{
        void passItemNum(int num);
    }

    public void setChapterClickListener(OnChapterSelected onChapterSelected){
        this.onChapterSelected = onChapterSelected;
    }
}
