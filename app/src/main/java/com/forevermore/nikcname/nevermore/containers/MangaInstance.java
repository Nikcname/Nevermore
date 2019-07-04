package com.forevermore.nikcname.nevermore.containers;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class MangaInstance {

    private String previewName;
    private String previewDescription;
    private String previewImageUrl;
    private String previewAvailableChapters;
    private Bitmap previewBitmap;
    private String previewLink;
    private String fullDesc;
    private List<String> chapterDescs = new ArrayList<>();
    private List<String> chapterUris = new ArrayList<>();
    private String chapterOne;

    public String getPreviewName() {
        return previewName;
    }

    public void setPreviewName(String previewName) {
        this.previewName = previewName;
    }

    public String getPreviewDescription() {
        return previewDescription;
    }

    public void setPreviewDescription(String previewDescription) {
        this.previewDescription = previewDescription;
    }

    public String getPreviewImageUrl() {
        return previewImageUrl;
    }

    public void setPreviewImageUrl(String previewImageUrl) {
        this.previewImageUrl = previewImageUrl;
    }

    public String getPreviewAvailableChapters() {
        return previewAvailableChapters;
    }

    public void setPreviewAvailableChapters(String previewAvailableChapters) {
        this.previewAvailableChapters = previewAvailableChapters;
    }

    public Bitmap getPreviewBitmap() {
        return previewBitmap;
    }

    public void setPreviewBitmap(Bitmap previewBitmap) {
        this.previewBitmap = previewBitmap;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getFullDesc() {
        return fullDesc;
    }

    public void setFullDesc(String fullDesc) {
        this.fullDesc = fullDesc;
    }

    public List<String> getChapterDescs() {
        return chapterDescs;
    }

    public void setChapterDescs(List<String> chapterDescs) {
        this.chapterDescs = chapterDescs;
    }

    public List<String> getChapterUris() {
        return chapterUris;
    }

    public void setChapterUris(List<String> chapterUris) {
        this.chapterUris = chapterUris;
    }

    public String getChapterOne() {
        return chapterOne;
    }

    public void setChapterOne(String chapterOne) {
        this.chapterOne = chapterOne;
    }
}
