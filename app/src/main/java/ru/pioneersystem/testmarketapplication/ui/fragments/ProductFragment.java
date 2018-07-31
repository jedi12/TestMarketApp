package ru.pioneersystem.testmarketapplication.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.pioneersystem.testmarketapplication.R;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.mvp.presenters.ProductPresenter;
import ru.pioneersystem.testmarketapplication.mvp.presenters.ProductPresenterFactory;
import ru.pioneersystem.testmarketapplication.mvp.views.IProductView;
import ru.pioneersystem.testmarketapplication.ui.activities.RootActivity;

public class ProductFragment extends Fragment implements IProductView {
    private static final String TAG = "ProductFragment";
    private static final String PRODUCT_DTO = "PRODUCT_DTO";

    @BindView(R.id.product_name_txt) TextView productNameTxt;
    @BindView(R.id.product_description_txt) TextView productDesctriptionTxt;
    @BindView(R.id.product_image) ImageView productImage;
    @BindView(R.id.product_count_txt) TextView productCountTxt;
    @BindView(R.id.product_price_txt) TextView productPriceTxt;
    @BindView(R.id.plus_btn) ImageButton plusBtn;
    @BindView(R.id.minus_btn) ImageButton minusBtn;

    // TODO: 30.07.2018 Загружать через Picasso
    @BindDrawable(R.drawable.radio_image) Drawable productDraw;

    private ProductPresenter mPresenter;

    public ProductFragment() {

    }

    public static ProductFragment newInstance(ProductDto product) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(PRODUCT_DTO, product);
        ProductFragment fragment = new ProductFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            ProductDto product = bundle.getParcelable(PRODUCT_DTO);
            mPresenter = ProductPresenterFactory.getInstance(product);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        readBundle(getArguments());
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
    public void showProductView(ProductDto product) {
        productNameTxt.setText(product.getProductName());
        productDesctriptionTxt.setText(product.getDescription());
        productCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            productPriceTxt.setText(String.valueOf(product.getCount() * product.getPrice() + ".-"));
        } else {
            productPriceTxt.setText(String.valueOf(product.getPrice() + ".-"));
        }

        // TODO: 30.07.2018 Загружать через Picasso из url
        productImage.setImageDrawable(productDraw);
    }

    @Override
    public void updateProductCountView(ProductDto product) {
        productCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            productPriceTxt.setText(String.valueOf(product.getCount() * product.getPrice() + ".-"));
        }
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

    @OnClick(R.id.plus_btn)
    public void onClickPlus(View v) {
        mPresenter.clickOnPlus();
    }

    @OnClick(R.id.minus_btn)
    public void onClickMinus(View v) {
        mPresenter.clickOnMinus();
    }
}
