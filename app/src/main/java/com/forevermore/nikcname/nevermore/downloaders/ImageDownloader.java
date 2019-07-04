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

    private CallbackImageDownloader callbackImageDownloader;

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

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        callbackImageDownloader.previewImageBitmap(bitmap);
    }

    public interface CallbackImageDownloader{
        void previewImageBitmap(Bitmap bitmap);
    }

    public void setListener(CallbackImageDownloader callbackImageDownloader){
        this.callbackImageDownloader = callbackImageDownloader;
    }
}