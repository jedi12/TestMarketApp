package ru.pioneersystem.testmarketapplication.mvp.models;

import javax.inject.Inject;

import ru.pioneersystem.testmarketapplication.data.managers.DataManager;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.components.DaggerModelComponent;
import ru.pioneersystem.testmarketapplication.di.components.ModelComponent;

public abstract class AbstractModel {

    @Inject
    DataManager mDataManager;

    public AbstractModel() {
        ModelComponent component = DaggerService.getComponent(ModelComponent.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(ModelComponent.class, component);
        }
        component.inject(this);
    }

    private ModelComponent createDaggerComponent() {
        return DaggerModelComponent.builder()
                .modelModule(new ModelModule())
                .build();
    }
}
