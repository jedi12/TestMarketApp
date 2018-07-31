package ru.pioneersystem.testmarketapplication.mvp.presenters;

import java.util.HashMap;
import java.util.Map;

import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;

public class ProductPresenterFactory {
    private static final Map<String, ProductPresenter> sPresenterMap = new HashMap<>();

    private static void registerPresenter(ProductDto productDto, ProductPresenter presenter) {
        sPresenterMap.put(String.valueOf(productDto.getId()), presenter);
    }

    public static ProductPresenter getInstance(ProductDto productDto) {
        ProductPresenter presenter = sPresenterMap.get(String.valueOf(productDto.getId()));
        if (presenter == null) {
            presenter = ProductPresenter.newInstance(productDto);
            registerPresenter(productDto, presenter);
        }
        return presenter;
    }
}
