package com.a_andries.fichedepaie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NDFList extends AppCompatActivity {

    @BindView(R.id.list)
    RecyclerView list;
    NDFAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndflist);
        getSupportActionBar().setTitle(R.string.ndf_list);
        ButterKnife.bind(this);
        adapter = new NDFAdapter(this);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.loadData();
    }
}
