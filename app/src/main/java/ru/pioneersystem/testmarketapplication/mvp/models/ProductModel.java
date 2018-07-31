package ru.pioneersystem.testmarketapplication.mvp.models;

import ru.pioneersystem.testmarketapplication.data.DataManager;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;

public class ProductModel {
    private DataManager mDataManager = DataManager.getInstance();

    public ProductDto getProductById(int productId) {
        return mDataManager.getProductById(productId);
    }

    public void updateProduct(ProductDto product) {
        mDataManager.updateProduct(product);
    }
}