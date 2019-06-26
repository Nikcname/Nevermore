package com.forevermore.nikcname.nevermore.downloaders;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IntPageDownloader extends AsyncTask<String, Void, Document>{

    private String cssPathDescription = "html body#wrap div.main_fon div#content div#dle-content div#description";
    private String cssPathChapterOne =   "html body#wrap div.main_fon div#content div#dle-content div#right div.extaraNavi p.extra_off a";
    private String cssPathListOdd =     "html body#wrap div.main_fon div#content div#dle-content div table#tc_1.table_cha tbody tr.no_zaliv td div.manga2 a";
    private String cssPathListEven =    "html body#wrap div.main_fon div#content div#dle-content div table#tc_1.table_cha tbody tr.zaliv td div.manga2 a";
    private PassListAndDesc passListAndDesc;

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

        if (document != null){

            List<String> chaptersList = new ArrayList<>();
            List<String> chapterListUrl = new ArrayList<>();
            Elements description = document.select(cssPathDescription);
//            for (Element desc : description){
////                String htmlStr = desc.toString();
////                StringBuilder stringBuilder = new StringBuilder();
////
////                boolean flagStart = false;
////                boolean flagPass = false;
////
////                for (int i = 0; i<htmlStr.length(); i++){
////
////                    char processing = htmlStr.charAt(i);
////
////                    if (processing == '<') {
////                        if ((htmlStr.charAt(++i) == 'd') && flagPass){
////                            break;
////                        }
////                        flagStart = false;
////                    }
////
////                    if (flagStart) {
////                        stringBuilder.append(htmlStr.charAt(i));
////                    }
////
////                    if (processing == '>') {
////                        flagStart = true;
////                        flagPass = true;
////                    }
////
////                }
//                descriptionStr = desc.text();
//            }
            Elements chapterOneUri = document.select(cssPathChapterOne);
            String uriOfChapterOne = chapterOneUri.get(chapterOneUri.size() - 1).attr("href");

            Elements chaptersOdd = document.select(cssPathListOdd);
            Elements chaptersEven = document.select(cssPathListEven);

            for (int i = 0; i < chaptersOdd.size(); i++){
                try {

                    chaptersList.add(chaptersOdd.get(i).text());
                    chaptersList.add(chaptersEven.get(i).text());
                    chapterListUrl.add(chaptersOdd.get(i).attributes().get("href"));
                    chapterListUrl.add(chaptersEven.get(i).attributes().get("href"));
                } catch (IndexOutOfBoundsException e){
//                    e.printStackTrace();
                }
            }
//
//            Elements chapters = document.select(cssPathListOdd);
//            for (Element element : chapters){
//                chaptersList.add(element.text());
//                chapterListUrl.add(element.attributes().get("href"));
//            }
            passListAndDesc.passLists(description.get(0).text(), chaptersList, chapterListUrl, uriOfChapterOne);
        }
    }

    public interface PassListAndDesc{
        void passLists(String descrAll, List<String> chapterDescs, List<String> chapterUris, String uriOfChapterOne);
    }

    public void setOnDescDownloadedListener(PassListAndDesc passListAndDesc){
        this.passListAndDesc = passListAndDesc;
    }

}
