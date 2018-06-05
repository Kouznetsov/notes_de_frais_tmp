package com.a_andries.fichedepaie;

import android.app.Application;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;

import java.io.IOException;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            Reservoir.init(this, 16384); //in bytes
        } catch (IOException e) {
            Log.e("App", "Could not init Reservoir");
        }
    }
}
