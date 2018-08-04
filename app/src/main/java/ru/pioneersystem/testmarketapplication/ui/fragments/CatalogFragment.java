package ru.pioneersystem.testmarketapplication.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Provides;
import ru.pioneersystem.testmarketapplication.R;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.scopes.CatalogScope;
import ru.pioneersystem.testmarketapplication.mvp.presenters.CatalogPresenter;
import ru.pioneersystem.testmarketapplication.mvp.views.ICatalogView;
import ru.pioneersystem.testmarketapplication.ui.activities.RootActivity;
import ru.pioneersystem.testmarketapplication.ui.fragments.adapters.CatalogAdapter;

public class CatalogFragment extends Fragment implements ICatalogView {
    private static final String TAG = "CatalogFragment";

    @BindView(R.id.add_to_cart_btn) Button addToCardBtn;
    @BindView(R.id.product_pager) ViewPager productPager;

    @Inject
    CatalogPresenter mPresenter;

    public CatalogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, view);

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);

        mPresenter.takeView(this);
        mPresenter.initView();
        return view;
    }

    @Override
    public void onDestroyView() {
        mPresenter.dropView();
        super.onDestroyView();
    }

    @Override
    public void showCatalogView(List<ProductDto> productsList) {
        CatalogAdapter adapter = new CatalogAdapter(getChildFragmentManager());
        for (ProductDto product : productsList) {
            adapter.addItem(product);
        }
        productPager.setAdapter(adapter);
    }

    @Override
    public void showAuthScreen() {

    }

    @Override
    public void updateProductCounter() {

    }

    private RootActivity getRootActivity() {
        return (RootActivity) getActivity();
    }

    @OnClick(R.id.add_to_cart_btn)
    public void onClickAddToCart(View v) {
        mPresenter.clickOnByButton(productPager.getCurrentItem());
    }

    @OnClick()
    public void onClick1(View v) {

    }

    private Component createDaggerComponent() {
        return DaggerCatalogFragment_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @CatalogScope
        CatalogPresenter provideCatalogPresenter() {
            return new CatalogPresenter();
        }
    }

    @dagger.Component(modules = CatalogFragment.Module.class)
    @CatalogScope
    public interface Component {
        void inject(CatalogFragment catalogFragment);
    }
}
