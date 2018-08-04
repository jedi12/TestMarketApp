package ru.pioneersystem.testmarketapplication.ui.activities;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.Provides;
import ru.pioneersystem.testmarketapplication.BuildConfig;
import ru.pioneersystem.testmarketapplication.R;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.scopes.AuthScope;
import ru.pioneersystem.testmarketapplication.mvp.presenters.AuthPresenter;
import ru.pioneersystem.testmarketapplication.mvp.presenters.IAuthPresenter;
import ru.pioneersystem.testmarketapplication.mvp.views.IAuthView;
import ru.pioneersystem.testmarketapplication.ui.custom_views.AuthPanel;

public class SplashActivity extends AppCompatActivity implements IAuthView {

    @Inject
    AuthPresenter mPresenter;

    @BindView(R.id.coordinator_container) CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.show_catalog_btn) Button mShowCatalogBtn;
    @BindView(R.id.login_btn) Button mShowLoginBtn;
    @BindView(R.id.auth_wrapper) AuthPanel mAuthPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);

        mPresenter.takeView(this);
        mPresenter.initView();
    }

    @Override
    protected void onDestroy() {
        mPresenter.dropView();
        if (isFinishing()) {
            DaggerService.unregisterScope(AuthScope.class);
        }
        super.onDestroy();
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mCoordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(Throwable e) {
        if (BuildConfig.DEBUG) {
            showMessage(e.getMessage());
            e.printStackTrace();
        } else {
            showMessage("Что-то пошло не так");
        }
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void hideLoad() {

    }

    @Override
    public IAuthPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void showLoginBtn() {
        mShowLoginBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoginBtn() {
        mShowLoginBtn.setVisibility(View.GONE);
    }

//    @Override
//    public void showTestLoginDialog() {
//        mCardView.setVisibility(View.VISIBLE);
//    }

    @Override
    public AuthPanel getAuthPanel() {
        return mAuthPanel;
    }

    @Override
    public void showCatalogScreen() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!mAuthPanel.isIdle()) {
            mAuthPanel.setCustomState(AuthPanel.IDLE_STATE);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.show_catalog_btn)
    public void onClickCatalog() {
        mPresenter.clickOnShowCatalog();
    }

    @OnClick(R.id.login_btn)
    public void onClickLogin() {
        mPresenter.clickOnLogin();
    }

    private Component createDaggerComponent() {
        return DaggerSplashActivity_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @AuthScope
        AuthPresenter provideAuthPresenter() {
            return new AuthPresenter();
        }
    }

    @dagger.Component(modules = SplashActivity.Module.class)
    @AuthScope
    interface Component {
        void inject(SplashActivity activity);
    }
}
