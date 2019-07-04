package com.forevermore.nikcname.nevermore.downloaders;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import com.forevermore.nikcname.nevermore.containers.MangaInstance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;

public class PageDownloader extends AsyncTask<String, Void, Document> {

    private String cssPath = "html body#wrap div.main_fon div#content div.content_row div";
    private String imageLink = ".manga_images a img";
    private String mangaName = ".manga_row1 div h2 a";
    private String mangaDescription = ".tags";
    private String availableChapters = ".manga_row3 div.row3_left div.item2 span b";
    private CallbackPageDownloader callbackPageDownloader;

    @Override
    protected Document doInBackground(String... strings) {

        try {
            return Jsoup.connect(strings[0]).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);

        if (document != null) {

            Elements el_ImageLinks = document.select(cssPath + imageLink);
            Elements el_MangaNames = document.select(cssPath + mangaName);
            Elements el_MangaDescriptions = document.select(cssPath + mangaDescription);
            Elements el_AvailableChapters = document.select(cssPath + availableChapters);

            int count = el_MangaNames.size();

            for (int i = 0; i < count; i++) {

                String previewMangaLink = el_MangaNames.get(i).attr("href");
                String previewMangaName = el_MangaNames.get(i).text();
                String previewMangaDescription = el_MangaDescriptions.get(i).text();
                String previewMangaAvailableChapters = el_AvailableChapters.get(i).text();
                String previewImageLink = el_ImageLinks.get(i*2).attr("src");

                ImageDownloader imageDownloader = new ImageDownloader();
                imageDownloader.setListener(new ImageDownloader.CallbackImageDownloader() {
                    @Override
                    public void previewImageBitmap(Bitmap bitmap) {

                        MangaInstance mangaInstance = new MangaInstance();
                        mangaInstance.setPreviewName(previewMangaName);
                        mangaInstance.setPreviewAvailableChapters(previewMangaAvailableChapters);
                        mangaInstance.setPreviewBitmap(bitmap);
                        mangaInstance.setPreviewImageUrl(previewImageLink);
                        mangaInstance.setPreviewDescription(previewMangaDescription);
                        mangaInstance.setPreviewLink(previewMangaLink);

                        callbackPageDownloader.mangaInstance(mangaInstance);
                    }
                });
                imageDownloader.execute(previewImageLink);
            }
        }
    }

    public interface CallbackPageDownloader{
        void mangaInstance(MangaInstance mangaInstance);
    }

    public void setListener(CallbackPageDownloader callbackPageDownloader){
        this.callbackPageDownloader = callbackPageDownloader;
    }
}