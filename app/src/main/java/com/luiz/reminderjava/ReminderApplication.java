package com.luiz.reminderjava;

import android.app.Application;
import android.content.Context;

import com.mlykotom.valifi.ValiFi;

public class ReminderApplication extends Application {
    /**
     * Instance
     */
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        /* Set context */
        context = getApplicationContext();

        ValiFi.install(this);
    }

    public static Context getInstance() {
        return context;
    }
}
