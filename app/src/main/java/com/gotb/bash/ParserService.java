package com.gotb.bash;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.activeandroid.ActiveAndroid;

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
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        Log.i("myLog", "Start Service");
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

                    Boolean check = Database.checkDuplicate(postIdToDatabase);
                    if (!check){
                        new NotificationMaker().showNotification(this);
                        Database saveInDatabase = new Database(textToDatabase, postIdToDatabase);
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
