package ru.pioneersystem.testmarketapplication.mvp.presenters;

import ru.pioneersystem.testmarketapplication.mvp.models.AuthModel;
import ru.pioneersystem.testmarketapplication.mvp.views.IAuthView;
import ru.pioneersystem.testmarketapplication.ui.custom_views.AuthPanel;

public class AuthPresenter extends AbstractPresenter<IAuthView> implements IAuthPresenter {
    private static AuthPresenter ourInstance = new AuthPresenter();
    private AuthModel mAuthModel;
    private IAuthView mAuthView;

    public AuthPresenter() {
        mAuthModel = new AuthModel();
    }

    public static AuthPresenter getInstance() {
        return ourInstance;
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
}
