package ru.pioneersystem.testmarketapplication.mvp.presenters;

import android.util.Log;

import javax.inject.Inject;

import dagger.Provides;
import ru.pioneersystem.testmarketapplication.di.DaggerService;
import ru.pioneersystem.testmarketapplication.di.scopes.AuthScope;
import ru.pioneersystem.testmarketapplication.mvp.models.AuthModel;
import ru.pioneersystem.testmarketapplication.mvp.views.IAuthView;
import ru.pioneersystem.testmarketapplication.ui.custom_views.AuthPanel;

public class AuthPresenter extends AbstractPresenter<IAuthView> implements IAuthPresenter {
    private static final String TAG = "AuthPresenter";

    @Inject
    AuthModel mAuthModel;

    public AuthPresenter() {
        Component component = DaggerService.getComponent(Component.class);
        if (component == null) {
            component = createDaggerComponent();
            DaggerService.registerComponent(Component.class, component);
        }
        component.inject(this);
        Log.e(TAG, "AuthPresenter: inject complete");
    }

    @Override
    public void initView() {
        if (getView() != null) {
            if (checkUserAuth()) {
                getView().hideLoginBtn();
            } else {
                getView().showLoginBtn();
            }
        }
    }

    @Override
    public void clickOnLogin() {
        if (getView() != null && getView().getAuthPanel() != null) {
            if (getView().getAuthPanel().isIdle()) {
                getView().getAuthPanel().setCustomState(AuthPanel.LOGIN_STATE);
            } else {
                mAuthModel.loginUser(getView().getAuthPanel().getUserEmail(), getView().getAuthPanel().getUserPassword());
                getView().showMessage("request for user auth");
            }
        }
    }

    @Override
    public void clickOnFb() {
        if (getView() != null) {
            getView().showMessage("clickOnFb");
        }
    }

    @Override
    public void clickOnVk() {
        if (getView() != null) {
            getView().showMessage("clickOnVk");
        }
    }

    @Override
    public void clickOnTwitter() {
        if (getView() != null) {
            getView().showMessage("clickOnTwitter");
        }
    }

    @Override
    public void clickOnShowCatalog() {
        if (getView() != null) {
            getView().showMessage("Показать каталог");
            getView().showCatalogScreen();
        }
    }

    @Override
    public boolean checkUserAuth() {
        return mAuthModel.isAuthUser();
    }

    private Component createDaggerComponent() {
        return DaggerAuthPresenter_Component.builder()
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @AuthScope
        AuthModel provideAuthModel() {
            return new AuthModel();
        }
    }

    @dagger.Component(modules = AuthPresenter.Module.class)
    @AuthScope
    public interface Component {
        void inject(AuthPresenter presenter);
    }
}
