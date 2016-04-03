package com.gotb.bash;

import android.text.format.DateFormat;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;


@Table(name = "BashIm", id = "_id")
public class Database extends Model {
    @Column(name = "text")
    public String text;

    @Column(name = "post_id")
    public int postId;

    @Column(name = "date")
    public long date;

    public Database() {
        super();
    }

    public Database(String text, int postId, long date) {
        super();
        this.text = text;
        this.postId = postId;
        this.date = date;
    }

    public static boolean checkDuplicate(int postId) {
        return new Select()
                .from(Database.class)
                .where("post_id = ?", postId)
                .exists();
    }

    public static List<Database> getText() {
        return new Select()
                .from(Database.class)
                .orderBy("post_id DESC")
                .limit(150)
                .execute();
    }

    @Override
    public String toString() {
        String str = DateFormat.format("dd.MM.yy hh:mm", new Date(date)).toString();
        if (str.equals("01.01.70 12:00")) {
            str = "";

        } else {
            str = "Дата публикации: " + str + "\n\n";
        }
        return str  + text;
    }
}
