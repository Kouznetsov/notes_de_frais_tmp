package com.a_andries.fichedepaie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NDFList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndflist);
        getSupportActionBar().setTitle(R.string.ndf_list);
    }
}
