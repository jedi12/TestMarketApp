package ru.pioneersystem.testmarketapplication.mvp.views;

import android.support.annotation.Nullable;

import ru.pioneersystem.testmarketapplication.mvp.presenters.IAuthPresenter;
import ru.pioneersystem.testmarketapplication.ui.custom_views.AuthPanel;

public interface IAuthView {
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();

    IAuthPresenter getPresenter();

    void showLoginBtn();
    void hideLoginBtn();

//    void showTestLoginDialog();

    @Nullable
    AuthPanel getAuthPanel();
}
