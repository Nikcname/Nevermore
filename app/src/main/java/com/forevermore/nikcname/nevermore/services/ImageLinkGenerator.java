package com.forevermore.nikcname.nevermore.services;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ImageLinkGenerator {

//    public static void main(String[] args) {
//        List<String> urs = new ArrayList<>();
////        urs.add("https://img4.manga-chan.me/manga/-9new/d/1561723755_do-tebya-i-posle-1-1/001.png");
////        urs.add("https://img4.manga-chan.me/manga/-9new/d/1561723755_do-tebya-i-posle-1-1/002.png");
//        urs.add("https://img4.manga-chan.me/manga/-9new/i/1561521462_img/1_370904_1416_2000.png");
//        urs.add("https://img4.manga-chan.me/manga/-9new/i/1561521462_img/2_210990_1416_2000.png");
//        new ImageLinkGenerator(8, urs).generateLinks();
//    }

    private int amount;
    private String linkOne;
    private String linkTwo;

    public ImageLinkGenerator(int amount, List<String> imageUrls) {
        this.amount = amount;
        this.linkOne = imageUrls.get(0);
        this.linkTwo = imageUrls.get(1);
    }

    public List<String> generateLinks(){

        List<String> imageSrc = new ArrayList<>();
        List<Integer> changingChars = new ArrayList<>();

        for (int i = 0; i < linkOne.length(); i++){
            if (linkOne.charAt(i) != linkTwo.charAt(i)){
                changingChars.add(i);
            }
        }

        String mainPart;
        if (changingChars.size() == 1)
            mainPart = linkOne;
        else
            mainPart = linkTwo;

        for (int i = 0, j = Integer.parseInt(""+linkOne.charAt(changingChars.get(0))); i < amount;j++, i++){

            StringBuilder sb = new StringBuilder();
            if (j<10){
                sb.append(mainPart.substring(0, changingChars.get(0)));
                sb.append(j);
                sb.append(mainPart.substring(changingChars.get(0)+1));
            } else if (j >= 10){
                sb.append(mainPart.substring(0, changingChars.get(0)-1));
                sb.append(j);
                sb.append(mainPart.substring(changingChars.get(0)+1));
            }

            imageSrc.add(sb.toString());
        }

        return imageSrc;
    }
}
