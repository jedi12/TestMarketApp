package ru.pioneersystem.testmarketapplication.mvp.presenters;

import android.support.annotation.Nullable;

import ru.pioneersystem.testmarketapplication.mvp.views.IView;

public abstract class AbstractPresenter<T extends IView> {
    private T mView;

    public void takeView(T view) {
        mView = view;
    }

    public void dropView() {
        mView = null;
    }

    public abstract void initView();

    @Nullable
    public T getView() {
        return mView;
    }
}
