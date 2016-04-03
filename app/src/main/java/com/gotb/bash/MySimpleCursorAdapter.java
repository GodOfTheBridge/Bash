package com.gotb.bash;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TextView;

import java.util.Date;

public class MySimpleCursorAdapter extends SimpleCursorAdapter {
    public MySimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);

        TextView tvText = (TextView) view.findViewById(R.id.tvForText);
        TextView tvDate = (TextView) view.findViewById(R.id.tvForDate);
        tvDate.setVisibility(View.VISIBLE);

        String textString = cursor.getString(cursor.getColumnIndex("text"));
        String dateString;

        if (cursor.getString(cursor.getColumnIndex("date"))!=null){
            String dateTemp = cursor.getString(cursor.getColumnIndex("date"));
            long dateLong = Long.parseLong(dateTemp);
            dateString = DateFormat.format("dd.MM.yy hh:mm", new Date(dateLong)).toString();
        }else{
            dateString = "";
            tvDate.setVisibility(View.GONE);
        }

        tvText.setText(textString);
        tvDate.setText(dateString);
    }
}
