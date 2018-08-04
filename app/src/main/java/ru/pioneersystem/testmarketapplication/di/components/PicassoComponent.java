package ru.pioneersystem.testmarketapplication.di.components;

import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Component;
import ru.pioneersystem.testmarketapplication.di.modules.PicassoCacheModule;

@Component(dependencies = AppComponent.class, modules = PicassoCacheModule.class)
@Singleton
public interface PicassoComponent {
    Picasso getPicasso();
}
