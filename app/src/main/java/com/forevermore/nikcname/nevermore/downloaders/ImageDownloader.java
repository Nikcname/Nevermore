package com.forevermore.nikcname.nevermore.downloaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ImageDownloader extends AsyncTask<String, Bitmap, Void> {

    private NotifyChange notifyChange;
    private int i;

    public ImageDownloader(int i){
        this.i = i;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {

            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            InputStream inputStream = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            publishProgress(bitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Bitmap... values) {
        notifyChange.notifyAdapter(values[0], i);
    }

    public interface NotifyChange{
        void notifyAdapter(Bitmap bitmap, int i);
    }

    public void setNotifyListener(NotifyChange notifyChange){
        this.notifyChange = notifyChange;
    }
}