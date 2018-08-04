package ru.pioneersystem.testmarketapplication.mvp.views;

public interface IRootView extends IView {
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();
}
