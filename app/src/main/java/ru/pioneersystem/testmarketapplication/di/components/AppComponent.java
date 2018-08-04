package ru.pioneersystem.testmarketapplication.di.components;

import android.content.Context;

import dagger.Component;
import ru.pioneersystem.testmarketapplication.di.modules.AppModule;

@Component(modules = AppModule.class)
public interface AppComponent {
    Context getContext();
}
