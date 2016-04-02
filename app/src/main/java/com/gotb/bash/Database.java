package com.gotb.bash;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;


@Table(name = "BashIm", id = "_id")
public class Database extends Model {
    @Column(name = "text")
    public String text;

    @Column(name = "post_id")
    public int postId;

    public Database() {
        super();
    }

    public Database(String text, int postId) {
        super();
        this.text = text;
        this.postId = postId;
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
        return text;
    }
}
