package com.gotb.bash;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;


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
}
