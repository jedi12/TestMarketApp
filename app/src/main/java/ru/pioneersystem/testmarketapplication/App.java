package ru.pioneersystem.testmarketapplication;

import android.app.Application;

import ru.pioneersystem.testmarketapplication.di.components.AppComponent;
import ru.pioneersystem.testmarketapplication.di.components.DaggerAppComponent;
import ru.pioneersystem.testmarketapplication.di.modules.AppModule;

public class App extends Application {
    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        createComponent();
    }

    private void createComponent() {
        sAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext()))
                .build();
    }
}
