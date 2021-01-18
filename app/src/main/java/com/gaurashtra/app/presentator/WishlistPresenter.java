package com.gaurashtra.app.presentator;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.WishListModel;
import com.gaurashtra.app.model.modelInteractor.WishListModelinteractor;
import com.gaurashtra.app.presentator.presentarInteractor.WishListPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.WishListInteractor;

public class WishlistPresenter implements WishListPresentarInteractor {
    private WishListInteractor wishListInteractor;
    private WishListModelinteractor wishListModelinteractor;
    private GlobalUtils globalUtils;


    public WishlistPresenter(WishListInteractor wishListInteractor){
        this.wishListInteractor=wishListInteractor;
        this.wishListModelinteractor=new WishListModel(this);
        globalUtils=new GlobalUtils();


    }
    @Override
    public void sendRequest(String userid) {
        wishListInteractor.showProgress();
        wishListModelinteractor.callWishListAPI(userid);

    }

    @Override
    public void getResponse(Object object) {
        wishListInteractor.hideProgress();
        wishListInteractor.getResponse(object);

    }
}
