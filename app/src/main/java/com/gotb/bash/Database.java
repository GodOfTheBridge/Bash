package com.gotb.bash;

import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;



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


    public static Cursor fetchResultCursor() {
        String tableName = Cache.getTableInfo(Database.class).getTableName();
        String resultRecords = new Select(tableName + ".*").from(Database.class).orderBy("post_id DESC").limit(150).toSql();
        return Cache.openDatabase().rawQuery(resultRecords, null);
    }

}
