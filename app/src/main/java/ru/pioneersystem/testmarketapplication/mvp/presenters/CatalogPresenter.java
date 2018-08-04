package ru.pioneersystem.testmarketapplication.mvp.presenters;

import java.util.List;

import javax.inject.Inject;

import dagger.Provides;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.scopes.CatalogScope;
import ru.pioneersystem.testmarketapplication.mvp.models.CatalogModel;
import ru.pioneersystem.testmarketapplication.mvp.views.ICatalogView;
import ru.pioneersystem.testmarketapplication.mvp.views.IRootView;
import ru.pioneersystem.testmarketapplication.ui.activities.RootActivity;

public class CatalogPresenter extends AbstractPresenter<ICatalogView> implements ICatalogPresenter {
    @Inject
    RootPresenter mRootPresenter;

    @Inject
    CatalogModel mCatalogModel;

    private List<ProductDto> mProductDtoList;

    public CatalogPresenter() {
        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(CatalogPresenter.Component.class, component);
        }
        component.inject(this);
    }

    @Override
    public void initView() {
        if (mProductDtoList == null) {
            mProductDtoList = mCatalogModel.getProductList();
        }

        if (getView() != null) {
            getView().showCatalogView(mProductDtoList);
        }
    }

    @Override
    public void clickOnByButton(int position) {
        if (getView() != null) {
            if (checkUserAuth()) {
                getRootView().showMessage("Товар " + mProductDtoList.get(position).getProductName() + " успешно добавлен в корзину");
            } else {
                getView().showAuthScreen();
            }
        }
    }

    private IRootView getRootView() {
        return mRootPresenter.getView();
    }

    @Override
    public boolean checkUserAuth() {
        return mCatalogModel.isUserAurh();
    }

    private Component createDaggerComponent() {
        return DaggerCatalogPresenter_Component.builder()
                .component(DaggerService.getComponent(RootActivity.Component.class))
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        CatalogModel provideCatalogModel() {
            return new CatalogModel();
        }
    }

    @dagger.Component(dependencies = RootActivity.Component.class, modules = CatalogPresenter.Module.class)
    @CatalogScope
    public interface Component {
        void inject(CatalogPresenter catalogPresenter);
    }
}
