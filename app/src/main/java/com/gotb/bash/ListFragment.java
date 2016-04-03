package com.gotb.bash;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;

import java.util.List;

public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ListView lvDatabase;
    SwipeRefreshLayout mSwipeRefreshLayout;
    MySimpleCursorAdapter mySimpleCursorAdapter;
    Cursor cursor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActiveAndroid.initialize(getContext());
        getContext().registerReceiver(receiver, new IntentFilter("receiveMessage"));

        View view = inflater.inflate(R.layout.list_view_database, container, false);

        lvDatabase = (ListView) view.findViewById(R.id.lvDatabase);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        if (mSwipeRefreshLayout != null) {mSwipeRefreshLayout.setOnRefreshListener(this);}

        serviceLaunch();
        return view;
    }

    @Override
    public void onRefresh() {
        serviceLaunch();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void buildListDB() {
        cursor = Database.fetchResultCursor();
        String[] from = new String[] {"text", "date"};
        int[] to = new int[] {R.id.tvForText, R.id.tvForDate };
        mySimpleCursorAdapter = new MySimpleCursorAdapter(getContext(), R.layout.list_item, cursor, from, to, 0);
        lvDatabase.setAdapter(mySimpleCursorAdapter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            buildListDB();
        }
    };

    private void serviceLaunch(){
        Intent intentAlarm = new Intent(getContext(), AlarmClock.class);
        getContext().sendBroadcast(intentAlarm);
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(receiver);
    }
}
