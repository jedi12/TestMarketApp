package ru.pioneersystem.testmarketapplication.mvp.models;

import java.util.List;

import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;

public class CatalogModel extends AbstractModel {

    public CatalogModel() {

    }

    public List<ProductDto> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAurh() {
        return mDataManager.isAuthUser();
    }
}
