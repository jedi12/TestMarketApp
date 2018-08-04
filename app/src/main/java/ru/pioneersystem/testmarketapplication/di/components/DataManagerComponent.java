package ru.pioneersystem.testmarketapplication.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.pioneersystem.testmarketapplication.data.managers.DataManager;
import ru.pioneersystem.testmarketapplication.di.modules.LocalModule;
import ru.pioneersystem.testmarketapplication.di.modules.NetworkModule;

@Component(dependencies = AppComponent.class, modules = {NetworkModule.class, LocalModule.class})
@Singleton
public interface DataManagerComponent {
    void inject(DataManager dataManager);
}
