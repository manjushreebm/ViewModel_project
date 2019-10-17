package com.example.opusapp;

import android.app.Application;
import android.content.Context;

import com.example.opusapp.services.OptusService;
import com.example.opusapp.services.OptusServiceImpl;

public class OptusApplication extends Application {

    private OptusService service;

    public OptusService getOptusService() {
        if (service == null) {
            service = OptusServiceImpl.getInstance(this);
        }
        return service;
    }

    public static OptusApplication getApplication(Context context) {
        return (OptusApplication) context.getApplicationContext();
    }

}
