package com.gotb.bash;

import android.app.IntentService;
import android.content.Intent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParserService extends IntentService {

    public final String URL_BASH = "http://bash.im/";

    public ParserService() {
        super("parseBashService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            Document document = Jsoup.connect(URL_BASH).get();
            document.select("br").append("\\n");
            Elements elements = document.select("div.quote");
            for (Element element : elements) {
                if (!element.getElementsByClass("id").text().equals("")) {
                    String textToDatabase = element.getElementsByClass("text").text().replaceAll("\\\\n", "\n");
                    String postId = element.getElementsByClass("id").text().replaceAll("#", "");
                    int postIdToDatabase = Integer.parseInt(postId);

                    System.out.println(textToDatabase);
                    System.out.println(postIdToDatabase);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
