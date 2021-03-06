package com.forevermore.nikcname.nevermore.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class EntryFragment extends Fragment {

    private static MangaInstance mangaInstance;
    private RecyclerView recyclerViewForChapters;
    private RecyclerView.Adapter adapterForChapters;
    private RecyclerView.LayoutManager managerForChapters;
    private Button startReading;
    private View v;
    private int UNIT_COUNT;
    private WebView webViewOne;
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
        imageViewPicture.setImageBitmap(mangaInstance.getPreviewBitmap());

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        ((ChapterAdapter)adapterForChapters).setChapterClickListener(new ChapterAdapter.OnChapterSelected() {
            @Override
            public void passItemNum(int num) {

                String linkOfMangaForRead = "https://manga-chan.me" + mangaInstance.getChapterUris().get(num);
                downloadLinks(linkOfMangaForRead);
            }
        });

        startReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String linkOfMangaForRead = "https://manga-chan.me" + mangaInstance.getChapterOne();
                downloadLinks(linkOfMangaForRead);

            }
        });
    }

    private void parseJsoup(String html){

        String html1 = html.replace("\\u003C", "<");
        String html2 = html1.replace("\\&quot;", "");
        String html3 = html2.replace("\\\"", "\"");

        Document data = Jsoup.parse(html3, "", Parser.xmlParser());

        Elements pages = data.select(cssPathPages);

        UNIT_COUNT = (pages.size() -2)/2 - 1;

//        imageUrl = data.selectFirst("body div#wrap div#content div#image a img").attr("src");

    }

    private void webViewStopped(String link){
            interfacePassSelectedLink.passLink(UNIT_COUNT, link);
    }

    public interface InterfacePassSelectedLink{
        void passLink(int units, String link);
    }

    public void setOnCallbackListener(InterfacePassSelectedLink interfacePassSelectedLink){
        this.interfacePassSelectedLink = interfacePassSelectedLink;
    }

    private void downloadLinks(String linkOfMangaForRead){
        webViewOne = new WebView(getContext());
        webViewOne.getSettings().setJavaScriptEnabled(true);
        webViewOne.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                webViewOne.evaluateJavascript(
                        "(function() { return document.documentElement.innerHTML; })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String html) {
                                parseJsoup(html);
                                webViewStopped(linkOfMangaForRead);
                            }
                        });
            }
        });
        webViewOne.loadUrl(linkOfMangaForRead + "#page=1");

    }
}
