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

public class ImageDownloader extends AsyncTask<String, Bitmap, Bitmap> {

    private NotifyChange notifyChange;
    private int i;

    public ImageDownloader(int i){
        this.i = i;
    }
    public ImageDownloader(){}

    @Override
    protected Bitmap doInBackground(String... strings) {

        Bitmap bitmap = null;

        try {

            URL url = new URL(strings[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bitmap == null){
            try {
                URL url = new URL(strings[0].substring(0, strings[0].length() - 3) + "jpg");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap aVoid) {
        super.onPostExecute(aVoid);

        notifyChange.notifyAdapter(aVoid, i);

    }

    public interface NotifyChange{
        void notifyAdapter(Bitmap bitmap, int i);
    }

    public void setNotifyListener(NotifyChange notifyChange){
        this.notifyChange = notifyChange;
    }
}