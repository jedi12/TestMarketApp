package ru.pioneersystem.testmarketapplication.di.components;

import javax.inject.Singleton;

import dagger.Component;
import ru.pioneersystem.testmarketapplication.mvp.models.AbstractModel;
import ru.pioneersystem.testmarketapplication.mvp.models.ModelModule;

@Component(modules = ModelModule.class)
@Singleton
public interface ModelComponent {
    void inject(AbstractModel abstractModel);
}
