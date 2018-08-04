package ru.pioneersystem.testmarketapplication.data.managers;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import ru.pioneersystem.testmarketapplication.App;
import ru.pioneersystem.testmarketapplication.data.network.RestService;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.components.DaggerDataManagerComponent;
import ru.pioneersystem.testmarketapplication.di.components.DataManagerComponent;
import ru.pioneersystem.testmarketapplication.di.modules.LocalModule;
import ru.pioneersystem.testmarketapplication.di.modules.NetworkModule;

public class DataManager {
    @Inject
    PreferencesManager mPreferencesManager;

    @Inject
    RestService mRestService;

    private List<ProductDto> mMockProductList;

    public DataManager() {
        DataManagerComponent component = DaggerService.getComponent(DataManagerComponent.class);
        if (component == null) {
            component = DaggerDataManagerComponent.builder()
                    .appComponent(App.getAppComponent())
                    .localModule(new LocalModule())
                    .networkModule(new NetworkModule())
                    .build();
            DaggerService.registerComponent(DataManagerComponent.class, component);
        }
        component.inject(this);

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
        mMockProductList.add(new ProductDto(1, "test 1", "https://vignette.wikia.nocookie.net/sword-art-online/images/2/20/Kirito.png/revision/latest?cb=20121017120406&path-prefix=ru", "description 1 description 1 description 1 description 1 description 1", 100, 1));
        mMockProductList.add(new ProductDto(2, "test 2", "https://img0.liveinternet.ru/images/attach/c/3/122/87/122087070_16.png", "description 1 description 1 description 1 description 1 description 1", 200, 1));
        mMockProductList.add(new ProductDto(3, "test 3", "http://xaxa-net.ru/uploads/posts/2013-01/1358260748_foarxhpxua4.jpg", "description 1 description 1 description 1 description 1 description 1", 300, 1));
        mMockProductList.add(new ProductDto(4, "test 4", "http://xaxa-net.ru/uploads/posts/2013-01/1358260732_ge-s26ehdaw.jpg", "description 1 description 1 description 1 description 1 description 1", 400, 1));
        mMockProductList.add(new ProductDto(5, "test 5", "http://xaxa-net.ru/uploads/posts/2013-01/1358260741_ieh8god_u58.jpg", "description 1 description 1 description 1 description 1 description 1", 500, 1));
        mMockProductList.add(new ProductDto(6, "test 6", "http://xaxa-net.ru/uploads/posts/2013-01/1358260769_j9u6qkb4v7y.jpg", "description 1 description 1 description 1 description 1 description 1", 600, 1));
        mMockProductList.add(new ProductDto(7, "test 7", "http://xaxa-net.ru/uploads/posts/2013-01/1358260781_n1qcbiaryao.jpg", "description 1 description 1 description 1 description 1 description 1", 700, 1));
        mMockProductList.add(new ProductDto(8, "test 8", "http://xaxa-net.ru/uploads/posts/2013-01/1358260777_nc-losxfixk.jpg", "description 1 description 1 description 1 description 1 description 1", 800, 1));
        mMockProductList.add(new ProductDto(9, "test 9", "http://xaxa-net.ru/uploads/posts/2013-01/1358260821_nibsaz8j8k8.jpg", "description 1 description 1 description 1 description 1 description 1", 900, 1));
        mMockProductList.add(new ProductDto(10, "test 10", "http://xaxa-net.ru/uploads/posts/2013-01/1358260781_nmmju9tdovi.jpg", "description 1 description 1 description 1 description 1 description 1", 1000, 1));
    }

    public boolean isAuthUser() {
        return true;
    }
}
