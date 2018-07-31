package ru.pioneersystem.testmarketapplication.mvp.models;

import java.util.List;

import ru.pioneersystem.testmarketapplication.data.DataManager;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;

public class CatalogModel {
    DataManager mDataManager = DataManager.getInstance();

    public CatalogModel() {

    }

    public List<ProductDto> getProductList() {
        return mDataManager.getProductList();
    }

    public boolean isUserAurh() {
        return mDataManager.isAuthUser();
    }

//    public ProductDto getProductById(int productId) {
//        // TODO: 29.10.2016 get product from datamanager
//        return mDataManager.getProductById(productId);
//    }
//
//    public void updateProduct(ProductDto product) {
//        mDataManager.updateProduct(product);
//    }
}
