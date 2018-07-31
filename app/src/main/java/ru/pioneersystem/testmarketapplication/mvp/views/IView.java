package ru.pioneersystem.testmarketapplication.mvp.views;

public interface IView {
    void showMessage(String message);
    void showError(Throwable e);

    void showLoad();
    void hideLoad();
}
