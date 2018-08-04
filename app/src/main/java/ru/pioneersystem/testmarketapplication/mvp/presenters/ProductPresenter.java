package ru.pioneersystem.testmarketapplication.mvp.presenters;

import javax.inject.Inject;

import dagger.Provides;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.scopes.ProductScope;
import ru.pioneersystem.testmarketapplication.mvp.models.ProductModel;
import ru.pioneersystem.testmarketapplication.mvp.views.IProductView;

public class ProductPresenter extends AbstractPresenter<IProductView> implements IProductPresenter {
    private static final String TAG = "ProductPresenter";

    @Inject
    ProductModel mProductModel;

    private ProductDto mProduct;

    public ProductPresenter(ProductDto product) {
        mProduct = product;

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(ProductPresenter.Component.class, component);
        }
        component.inject(this);
    }

    @Override
    public void initView() {
        if (getView() != null) {
            getView().showProductView(mProduct);
        }
    }

    @Override
    public void clickOnPlus() {
        mProduct.addProduct();
        mProductModel.updateProduct(mProduct);
        if (getView() != null) {
            getView().updateProductCountView(mProduct);
        }
    }

    @Override
    public void clickOnMinus() {
        if (mProduct.getCount() > 0) {
            mProduct.deleteProduct();
            mProductModel.updateProduct(mProduct);
            if (getView() != null) {
                getView().updateProductCountView(mProduct);
            }
        }
    }

    private Component createDaggerComponent() {
        return DaggerProductPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @ProductScope
        ProductModel provideProductModel() {
            return new ProductModel();
        }
    }

    @dagger.Component(modules = ProductPresenter.Module.class)
    @ProductScope
    public interface Component {
        void inject(ProductPresenter productPresenter);
    }
}
