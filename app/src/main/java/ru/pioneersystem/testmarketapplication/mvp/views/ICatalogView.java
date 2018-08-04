package ru.pioneersystem.testmarketapplication.mvp.views;

import java.util.List;

import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;

public interface ICatalogView extends IView {
//    void showAddToCartMessage(ProductDto productDto);
    void showCatalogView(List<ProductDto> productsList);
    void showAuthScreen();
    void updateProductCounter();
}
