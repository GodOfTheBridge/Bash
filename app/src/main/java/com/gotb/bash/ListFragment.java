package com.gotb.bash;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    ListView lvDatabase;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.list_view_database, container, false);

        lvDatabase = (ListView) view.findViewById(R.id.lvDatabase);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh);
        if (mSwipeRefreshLayout != null) {mSwipeRefreshLayout.setOnRefreshListener(this);}

        return view;
    }

    @Override
    public void onRefresh() {

    }
}
