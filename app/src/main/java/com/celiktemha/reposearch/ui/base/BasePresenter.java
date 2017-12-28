package com.celiktemha.reposearch.ui.base;

/**
 * Created by celiktemha on 17/12/28.
 */

public abstract class BasePresenter<T extends BaseView> {
    protected T view;

    protected abstract void detach();

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        detach();
        view = null;
    }

    protected boolean isViewAttach() {
        return view != null;
    }
}
