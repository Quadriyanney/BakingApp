package com.quadriyanney.bakingapp.ui.base;

public interface BasePresenter<BaseView> {
    void attachView(BaseView view);

    void detachView();
}
