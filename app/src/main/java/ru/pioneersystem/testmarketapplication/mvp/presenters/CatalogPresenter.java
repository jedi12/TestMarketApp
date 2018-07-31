package ru.pioneersystem.testmarketapplication.mvp.presenters;

import java.util.List;

import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.mvp.models.CatalogModel;
import ru.pioneersystem.testmarketapplication.mvp.views.ICatalogView;

public class CatalogPresenter extends AbstractPresenter<ICatalogView> implements ICatalogPresenter {
    private static CatalogPresenter ourInstance = new CatalogPresenter();
    private CatalogModel mCatalogModel;
    private List<ProductDto> mProductDtoList;

    public static CatalogPresenter getInstance() {
        return ourInstance;
    }

    private CatalogPresenter() {
        mCatalogModel = new CatalogModel();
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
                getView().showAddToCartMessage(mProductDtoList.get(position));
            } else {
                getView().showAuthScreen();
            }
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mCatalogModel.isUserAurh();
    }
}
