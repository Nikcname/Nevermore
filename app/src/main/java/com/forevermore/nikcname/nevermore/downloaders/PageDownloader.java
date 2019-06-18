package com.forevermore.nikcname.nevermore.downloaders;

import android.os.AsyncTask;
import com.forevermore.nikcname.nevermore.containers.MangaInstance;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageDownloader extends AsyncTask<String, Void, Document> {

    private String imgRegex = "(?i)<img[^>]+?src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";
    private String cssPath = "html body#wrap div.main_fon div#content div.content_row div.manga_images a img";
    private String cssPathName = "html body#wrap div.main_fon div#content div.content_row div.manga_row1 div h2 a";
    private String cssPathDesc = "html body#wrap div.main_fon div#content div.content_row div.tags";
    private String cssChapters = "html body#wrap div.main_fon div#content div.content_row div.manga_row3 div.row3_left div.item2 span b";
    private Pattern p = Pattern.compile(imgRegex);
    private List<String> urls = new ArrayList<>();
    private Matcher m;
    private ResultList resultList;

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

            Elements images = document.select(cssPath);
            Elements titles = document.select(cssPathName);
            Elements headings = document.select(cssPathDesc);
            Elements chapters = document.select(cssChapters);

            int count = headings.size();

            for (int i = 0; i < images.size(); i += 2) {
                m = p.matcher(images.get(i).toString());
                while (m.find()) {
                    urls.add(m.group(1));
                }
            }

//            parseTagInfo(titles, names);
//            parseTagInfo(headings, descs);
//            parseTagInfo(chapters, chpts);
//
            List<MangaInstance> mangaInstances;

            mangaInstances = parseTagInfo(titles, headings, chapters);
//            for (int i = 0; i < count; i++) {
//
//                String titleRef = titles.get(i).toString();
//
//                StringBuilder stringBuilder = new StringBuilder();
//
//                boolean flagStart = false;
//                for (int j = 0; j < titleRef.length(); j++) {
//
//                    if (titleRef.charAt(j) == '<') {
//                        flagStart = false;
//                    }
//
//                    if (flagStart) {
//                        stringBuilder.append(titleRef.charAt(j));
//                    }
//
//                    if (titleRef.charAt(j) == '>') {
//                        flagStart = true;
//                    }
//                }
//
//                names.add(stringBuilder.toString());
//            }

//            for (int i = 0; i < count; i++) {
//
//                String headingRef = headings.get(i).toString();
//
//                StringBuilder stringBuilder = new StringBuilder();
//
//                boolean flagStart = false;
//                for (int j = 0; j < headingRef.length(); j++) {
//
//                    if (headingRef.charAt(j) == '<') {
//                        flagStart = false;
//                    }
//
//                    if (flagStart) {
//                        stringBuilder.append(headingRef.charAt(j));
//                    }
//
//                    if (headingRef.charAt(j) == '>') {
//                        flagStart = true;
//                    }
//                }
//
//                descs.add(stringBuilder.toString());
//            }

//            for (int i = 0; i < count; i++) {
//
//                String chaptersRef = chapters.get(i).toString();
//
//                StringBuilder stringBuilder = new StringBuilder();
//
//                boolean flagStart = false;
//                for (int j = 0; j < chaptersRef.length(); j++) {
//
//                    if (chaptersRef.charAt(j) == '<') {
//                        flagStart = false;
//                    }
//
//                    if (flagStart) {
//                        stringBuilder.append(chaptersRef.charAt(j));
//                    }
//
//                    if (chaptersRef.charAt(j) == '>') {
//                        flagStart = true;
//                    }
//                }
//
//                chpts.add(stringBuilder.toString());
//            }
//            for (int i = 0; i < count; i++) {
//                mangaInstances.add(new MangaInstance(names.get(i), descs.get(i), urls.get(i), chpts.get(i)));
//            }
            for (MangaInstance s : mangaInstances)
                System.out.println(s);
            resultList.passMangaInstance(mangaInstances);
        }
    }

    private List<MangaInstance> parseTagInfo(Elements titles,
                              Elements headings,
                              Elements chapters){

        List<MangaInstance> mangaInstances = new ArrayList<>();
        int count = titles.size();

        for (int i = 0; i < count; i++) {

            String urlOfManga = patrseUrl(titles.get(i).toString());
            String resultTitle = parseCharacters(titles.get(i).toString());
            String resultHeading = parseCharacters(headings.get(i).toString());
            String resultChapter = parseCharacters(chapters.get(i).toString());

            mangaInstances.add(new MangaInstance(resultTitle, resultHeading, urls.get(i), resultChapter, urlOfManga));
        }

        return mangaInstances;
    }

    private String parseCharacters(String beforeParse){

        StringBuilder stringBuilder = new StringBuilder();

        boolean flagStart = false;
        for (int j = 0; j < beforeParse.length(); j++) {

            char processing = beforeParse.charAt(j);

            if (processing == '<') {
                flagStart = false;
            }

            if (flagStart) {
                stringBuilder.append(beforeParse.charAt(j));
            }

            if (processing == '>') {
                flagStart = true;
            }
        }

        return stringBuilder.toString();
    }

    private String patrseUrl(String string){

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < string.length(); i++){
            char temp = string.charAt(i);
            if (temp == '/'){
                while (temp != '"'){
                    stringBuilder.append(temp);
                    temp = string.charAt(++i);
                }
                break;
            }
        }
        return stringBuilder.toString();
    }

    public interface ResultList{
        void passMangaInstance(List<MangaInstance> urls);
    }

    public void setOnResultListener(ResultList resultList){
        this.resultList = resultList;
    }
}