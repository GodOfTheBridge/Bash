package com.gotb.bash;

import android.app.IntentService;
import android.content.Intent;

import com.activeandroid.ActiveAndroid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserService extends IntentService {

    public final String URL_BASH = "http://bash.im/";

    public ParserService() {
        super("parseBashService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
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

                    SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm");
                    String tempDate = element.getElementsByClass("date").text();
                    Date date = format.parse(tempDate);
                    long dateToDatabase = date.getTime();

                    Boolean check = Database.checkDuplicate(postIdToDatabase);
                    if (!check){
                        new NotificationMaker().showNotification(this);
                        Database saveInDatabase = new Database(textToDatabase, postIdToDatabase, dateToDatabase);
                        saveInDatabase.save();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendMessage();
    }

    private void sendMessage() {
        Intent intent = new Intent("receiveMessage");
        sendBroadcast(intent);
    }
}
