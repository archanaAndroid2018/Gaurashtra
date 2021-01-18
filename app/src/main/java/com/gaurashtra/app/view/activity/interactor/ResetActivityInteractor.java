package com.gaurashtra.app.view.activity.interactor;

public interface ResetActivityInteractor {
    public void showProgress();
    public void hideProgress();
    public void validationResponse(String msg);
    public void getResponse(Object object);
}
