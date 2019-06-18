package com.forevermore.nikcname.nevermore.containers;

import android.graphics.Bitmap;

public class MangaInstance {

    private String name;
    private String description;
    private String imageUrl;
    private String chapters;
    private Bitmap bitmap;
    private String urlOfManga;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public MangaInstance(String name, String description, String imageUrl, String chapters, String urlOfManga) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.chapters = chapters;
        this.urlOfManga = urlOfManga;
    }

    @Override
    public String toString() {
        return "MangaInstance{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", chapters='" + chapters + '\'' +
                ", bitmap=" + bitmap +
                ", urlOfManga='" + urlOfManga + '\'' +
                '}';
    }

    public String getUrlOfManga() {
        return urlOfManga;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getChapters() {
        return chapters;
    }
}
