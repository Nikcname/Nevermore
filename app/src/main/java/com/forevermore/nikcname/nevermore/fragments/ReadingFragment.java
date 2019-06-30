package com.forevermore.nikcname.nevermore.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.forevermore.nikcname.nevermore.R;
import com.forevermore.nikcname.nevermore.services.ImageLinkGenerator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ReadingFragment extends Fragment {

    private String link;
    private WebView webViewOne;
    private WebView webViewTwo;
    private String cssPathPages = "body div#wrap div#content div.chapter div.page.postload select.drop option";
    private ProgressDialog dialog;
    private int flagDialogStop = 0;
    private List<String> IMAGE_URLS = new ArrayList<>();
    private int UNIT_COUNT;
    private List<String> imgLocation = new ArrayList<>();

    public ReadingFragment(){}

    public static ReadingFragment newInstance(String link) {

        Bundle args = new Bundle();
        args.putString("link", link);
        ReadingFragment fragment = new ReadingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        link = getArguments().getString("link");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reading, container, false);
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
                                webViewTwo.loadUrl(link + "#page=2");
                            }
                        });
            }
        });
        webViewOne.loadUrl(link + "#page=1");

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

        return v;
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

            for (String s : imgLocation)
                Log.d("sss", s);

        }
    }
}
