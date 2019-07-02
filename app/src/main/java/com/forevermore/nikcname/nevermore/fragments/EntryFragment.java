package com.forevermore.nikcname.nevermore.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.adapters.ChapterAdapter;
import com.forevermore.nikcname.nevermore.containers.MangaInstance;
import com.forevermore.nikcname.nevermore.services.ImageLinkGenerator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class EntryFragment extends Fragment {

    private static MangaInstance mangaInstance;
    private RecyclerView recyclerViewForChapters;
    private RecyclerView.Adapter adapterForChapters;
    private RecyclerView.LayoutManager managerForChapters;
    private Button startReading;
    private View v;
    private int flagDialogStop = 0;
    private List<String> IMAGE_URLS = new ArrayList<>();
    private int UNIT_COUNT;
    private List<String> imgLocation = new ArrayList<>();
    private WebView webViewOne;
    private WebView webViewTwo;
    private ProgressDialog dialog;
    private InterfacePassSelectedLink interfacePassSelectedLink;
    private String cssPathPages = "body div#wrap div#content div.chapter div.page.postload select.drop option";

    public EntryFragment() {}

    public static EntryFragment newInstance(MangaInstance manga) {
        EntryFragment fragment = new EntryFragment();
        mangaInstance = manga;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_entry, container, false);

        recyclerViewForChapters = v.findViewById(R.id.recycler_for_mangas_entry);
        managerForChapters = new LinearLayoutManager(getContext());
        recyclerViewForChapters.setLayoutManager(managerForChapters);
        adapterForChapters = new ChapterAdapter(mangaInstance.getChapterDescs());
        recyclerViewForChapters.setAdapter(adapterForChapters);
        startReading = v.findViewById(R.id.button_start_reading_entry);

        TextView textViewFullDesc = v.findViewById(R.id.text_view_manga_desc_entry);
        ImageView imageViewPicture = v.findViewById(R.id.image_view_picture_entry);
        textViewFullDesc.setMovementMethod(new ScrollingMovementMethod());
        textViewFullDesc.setText(mangaInstance.getFullDesc());
        imageViewPicture.setImageBitmap(mangaInstance.getBitmap());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        startReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String linkOfMangaForRead = "https://manga-chan.me" + mangaInstance.getChapterOne();

                webViewOne = new WebView(getContext());
                webViewOne.getSettings().setJavaScriptEnabled(true);
                dialog = new ProgressDialog(getContext());
                webViewOne.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        webViewOne.evaluateJavascript(
                                "(function() { return document.documentElement.innerHTML; })();",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String html) {
                                        parseJsoup(html);
                                        webViewStopped();
                                        webViewTwo.loadUrl(linkOfMangaForRead + "#page=2");
                                    }
                                });
                    }
                });
                webViewOne.loadUrl(linkOfMangaForRead + "#page=1");

                webViewTwo = new WebView(getContext());
                webViewTwo.getSettings().setJavaScriptEnabled(true);
                dialog = new ProgressDialog(getContext());
                webViewTwo.setWebViewClient(new WebViewClient(){
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        webViewTwo.evaluateJavascript(
                                "(function() { return document.documentElement.innerHTML; })();",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String html) {
                                        parseJsoup(html);
                                        webViewStopped();
                                    }
                                });
                    }
                });
                dialog.setMessage("Loading..Please wait.");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
    }

    private void parseJsoup(String html){

        String html1 = html.replace("\\u003C", "<");
        String html2 = html1.replace("\\&quot;", "");
        String html3 = html2.replace("\\\"", "\"");

        Document data = Jsoup.parse(html3, "", Parser.xmlParser());

        Elements pages = data.select(cssPathPages);

        UNIT_COUNT = (pages.size() -2)/2;

        String imageUrl = data.selectFirst("body div#wrap div#content div#image a img").attr("src");

        IMAGE_URLS.add(imageUrl);
    }

    private void webViewStopped(){
        flagDialogStop++;
        if (flagDialogStop == 2){
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            imgLocation = new ImageLinkGenerator(UNIT_COUNT, IMAGE_URLS).generateLinks();
            interfacePassSelectedLink.passLink(imgLocation);
        }
    }

    public interface InterfacePassSelectedLink{
        void passLink(List<String> link);
    }

    public void setOnCallbackListener(InterfacePassSelectedLink interfacePassSelectedLink){
        this.interfacePassSelectedLink = interfacePassSelectedLink;
    }
}
