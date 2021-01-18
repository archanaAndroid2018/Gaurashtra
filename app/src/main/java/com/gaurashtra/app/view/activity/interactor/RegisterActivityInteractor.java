package com.gaurashtra.app.view.activity.interactor;

public interface RegisterActivityInteractor {
    public void showProgress();
    public void hideProgress();
    public void validateCredentials(String msg);
    public void getResponse(Object object);
}
