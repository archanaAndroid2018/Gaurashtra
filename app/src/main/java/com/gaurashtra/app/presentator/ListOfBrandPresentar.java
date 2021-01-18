package com.gaurashtra.app.presentator;

import com.gaurashtra.app.model.bean.ListOfBrandModel;
import com.gaurashtra.app.model.modelInteractor.ListofbrandInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.ListofBrandPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ListofBrandActivityInteractor;

public class ListOfBrandPresentar implements ListofBrandPresentarInteractor {
    private ListofbrandInteractor listofbrandInteractor;
    private ListofBrandActivityInteractor listofBrandActivityInteractor;

    public ListOfBrandPresentar(ListofBrandActivityInteractor listofBrandActivityInteractor){
        this.listofBrandActivityInteractor=listofBrandActivityInteractor;
        this.listofbrandInteractor=new ListOfBrandModel(this);

    }


    @Override
    public void sendRequest(String userid) {
        listofBrandActivityInteractor.showProgress();
        listofbrandInteractor.callBrandListAPI(userid);

    }

    @Override
    public void getResponse(Object object) {
        listofBrandActivityInteractor.hideProgress();
        listofBrandActivityInteractor.getResponse(object);

    }
}
