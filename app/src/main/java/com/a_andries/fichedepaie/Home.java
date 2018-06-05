package com.a_andries.fichedepaie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import butterknife.OnClick;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @OnClick(R.id.edit)
    void onEditClick() {
        Intent intent = new Intent(this, NDFList.class);

    }

    @OnClick(R.id.add)
    void onAddClick() {
        startActivity(new Intent(this, NDFView.class));
    }

    @OnClick(R.id.share)
    void onShareClick() {
        Toast.makeText(this, "To implement", Toast.LENGTH_SHORT).show();
    }
}
