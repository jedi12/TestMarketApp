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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.pioneersystem.testmarketapplication.R;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.mvp.presenters.CatalogPresenter;
import ru.pioneersystem.testmarketapplication.mvp.views.ICatalogView;
import ru.pioneersystem.testmarketapplication.ui.activities.RootActivity;
import ru.pioneersystem.testmarketapplication.ui.fragments.adapters.CatalogAdapter;

public class CatalogFragment extends Fragment implements ICatalogView {
    private static final String TAG = "CatalogFragment";
    private CatalogPresenter mPresenter = CatalogPresenter.getInstance();

    @BindView(R.id.add_to_cart_btn) Button addToCardBtn;
    @BindView(R.id.product_pager) ViewPager productPager;

    public CatalogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalog, container, false);
        ButterKnife.bind(this, view);

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
    public void showAddToCartMessage(ProductDto productDto) {
        showMessage("Товар " + productDto.getProductName() + " успешно добавлен в корзину");
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

    @Override
    public void showMessage(String message) {
        getRootActivity().showMessage(message);
    }

    @Override
    public void showError(Throwable e) {
        getRootActivity().showError(e);
    }

    @Override
    public void showLoad() {
        getRootActivity().showLoad();
    }

    @Override
    public void hideLoad() {
        getRootActivity().hideLoad();
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
}
