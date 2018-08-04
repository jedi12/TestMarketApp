package ru.pioneersystem.testmarketapplication.mvp.models;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.pioneersystem.testmarketapplication.data.managers.DataManager;

@Module
public class ModelModule {
    @Provides
    @Singleton
    DataManager provideDataManager() {
        return new DataManager();
    }
}
