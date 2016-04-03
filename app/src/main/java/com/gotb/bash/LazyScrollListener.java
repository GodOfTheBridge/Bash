package com.gotb.bash;

import android.content.Context;
import android.database.Cursor;
import android.os.Parcelable;
import android.widget.AbsListView;
import android.widget.ListView;

public class LazyScrollListener implements AbsListView.OnScrollListener {

    ListView listView;
    Parcelable state;
    Context context;
    MySimpleCursorAdapter simpleCursorAdapter;
    Cursor cursorAllText;
    int countItems = 20, countItemsInDatabase = 0;

    public LazyScrollListener(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
        countItemsInDatabase = Database.getCount();
        newRequest(context, listView);
    }

    private void newRequest(Context context, ListView listView) {
        String[] from = new String[] { "text", "date"};
        int[] to = new int[] {R.id.tvForText, R.id.tvForDate };
        cursorAllText = Database.fetchResultCursor(countItems);
        simpleCursorAdapter = new MySimpleCursorAdapter(context, R.layout.list_item, cursorAllText, from, to, 0);
        listView.setAdapter(simpleCursorAdapter);
        if(state != null) {
            listView.onRestoreInstanceState(state);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0) {
            if (countItems + 20 < countItemsInDatabase) {
                countItems += 20;
                state = listView.onSaveInstanceState();
                newRequest(context, listView);
            } else if (countItems + 20 >= cursorAllText.getCount() && totalItemCount!=countItemsInDatabase){
                countItems += cursorAllText.getCount();
                state = listView.onSaveInstanceState();
                newRequest(context, listView);
            }
        }
    }
}