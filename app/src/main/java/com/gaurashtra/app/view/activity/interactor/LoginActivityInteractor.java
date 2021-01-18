package com.gaurashtra.app.view.activity.interactor;

public interface LoginActivityInteractor {
   public void validateCredential(String msg);
   public void showProgress();
   public void hideProgress();
   public void getResponse(Object object);
}
