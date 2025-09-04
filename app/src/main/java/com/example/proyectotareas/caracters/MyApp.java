package com.example.proyectotareas.caracters;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Inicializa Firebase Analytics una sola vez
        AnalyticsHelper.init(this);
    }
}

