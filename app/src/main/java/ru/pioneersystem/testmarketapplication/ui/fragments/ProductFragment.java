package ru.pioneersystem.testmarketapplication.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Provides;
import ru.pioneersystem.testmarketapplication.App;
import ru.pioneersystem.testmarketapplication.R;
import ru.pioneersystem.testmarketapplication.data.storage.dto.ProductDto;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.components.DaggerPicassoComponent;
import ru.pioneersystem.testmarketapplication.di.components.PicassoComponent;
import ru.pioneersystem.testmarketapplication.di.modules.PicassoCacheModule;
import ru.pioneersystem.testmarketapplication.di.scopes.ProductScope;
import ru.pioneersystem.testmarketapplication.mvp.presenters.ProductPresenter;
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

    @Inject
    Picasso mPicasso;

    @Inject
    ProductPresenter mPresenter;

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
            Component component = createDaggerComponent(product);
            component.inject(this);
            // TODO: 03.08.2018 fix recreate component
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

        mPicasso.load(product.getImageUrl())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .fit()
                .centerCrop()
                .into(productImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "onSuccess: load from cache");
                    }

                    @Override
                    public void onError(Exception e) {
                        mPicasso.load(product.getImageUrl())
                                .fit()
                                .centerCrop()
                                .into(productImage);
                    }
                });
    }

    @Override
    public void updateProductCountView(ProductDto product) {
        productCountTxt.setText(String.valueOf(product.getCount()));
        if (product.getCount() > 0) {
            productPriceTxt.setText(String.valueOf(product.getCount() * product.getPrice() + ".-"));
        }
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

    private Component createDaggerComponent(ProductDto product) {
        PicassoComponent picassoComponent = DaggerService.getComponent(PicassoComponent.class);
        if (picassoComponent == null) {
            picassoComponent = DaggerPicassoComponent.builder()
                    .appComponent(App.getAppComponent())
                    .picassoCacheModule(new PicassoCacheModule())
                    .build();
            DaggerService.registerComponent(PicassoComponent.class, picassoComponent);
        }
        return DaggerProductFragment_Component.builder()
                .picassoComponent(picassoComponent)
                .module(new Module(product))
                .build();
    }

    @dagger.Module
    public class Module {
        ProductDto mProductDto;

        public Module(ProductDto productDto) {
            mProductDto = productDto;
        }

        @Provides
        @ProductScope
        ProductPresenter provideProductPresenter() {
            return new ProductPresenter(mProductDto);
        }
    }

    @dagger.Component(dependencies = PicassoComponent.class, modules = ProductFragment.Module.class)
    @ProductScope
    public interface Component {
        void inject(ProductFragment fragment);
    }
}
