package com.forevermore.nikcname.nevermore.downloaders;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ReadingPageDownloader extends AsyncTask<String, Void, Document> {
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


    }
}
