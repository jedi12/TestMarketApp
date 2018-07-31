package ru.pioneersystem.testmarketapplication.data;

import java.util.ArrayList;
import java.util.List;

import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;

public class DataManager {
    private static DataManager ourInstance = new DataManager();
    private List<ProductDto> mMockProductList;

    public static DataManager getInstance() {
        return ourInstance;
    }

    private DataManager() {
        generateMockData();
    }

    public ProductDto getProductById(int productId) {
        // TODO: 30.07.2018 Заменить на загрузку из БД
        return mMockProductList.get(productId - 1);
    }

    public void updateProduct(ProductDto product) {
        // TODO: 30.07.2018 Обновление продукта
    }

    public List<ProductDto> getProductList() {
        // TODO: 30.07.2018 Получить из БД
        return mMockProductList;
    }

    private void generateMockData() {
        mMockProductList = new ArrayList<>();
        mMockProductList.add(new ProductDto(1, "test 1", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 100, 1));
        mMockProductList.add(new ProductDto(2, "test 2", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 200, 1));
        mMockProductList.add(new ProductDto(3, "test 3", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 300, 1));
        mMockProductList.add(new ProductDto(4, "test 4", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 400, 1));
        mMockProductList.add(new ProductDto(5, "test 5", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 500, 1));
        mMockProductList.add(new ProductDto(6, "test 6", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 600, 1));
        mMockProductList.add(new ProductDto(7, "test 7", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 700, 1));
        mMockProductList.add(new ProductDto(8, "test 8", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 800, 1));
        mMockProductList.add(new ProductDto(9, "test 9", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 900, 1));
        mMockProductList.add(new ProductDto(10, "test 10", "imageUrl", "description 1 description 1 description 1 description 1 description 1", 1000, 1));
    }

    public boolean isAuthUser() {
        return false;
    }
}
