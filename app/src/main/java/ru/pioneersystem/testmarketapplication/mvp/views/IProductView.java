package ru.pioneersystem.testmarketapplication.mvp.views;

import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;

public interface IProductView extends IView {
    void showProductView(ProductDto product);
    void updateProductCountView(ProductDto product);
}
