package com.gaurashtra.app.presentator;

import com.gaurashtra.app.model.bean.CategoryFragModel;
import com.gaurashtra.app.model.modelInteractor.CategoryFragModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.CategoryPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ProductListActivityInteractor;
import com.gaurashtra.app.view.fragment.fragementInteractor.categoryFragInteractor;

public class CategoryFragPresentar implements CategoryPresentarInteractor {
    private categoryFragInteractor categoryInteractor;
    private ProductListActivityInteractor productListActivityInteractor;
    private CategoryFragModelInteractor categoryFragModelInteractor;

    public CategoryFragPresentar(categoryFragInteractor categoryInteractor){
        this.categoryInteractor = categoryInteractor;
        categoryFragModelInteractor = new CategoryFragModel(this);
    }
    public CategoryFragPresentar(ProductListActivityInteractor productListActivityInteractor){
        this.productListActivityInteractor = productListActivityInteractor;
        categoryFragModelInteractor = new CategoryFragModel(this);
    }


    @Override
    public void sendRequest(String userid) {
        categoryInteractor.showProgress();
        categoryFragModelInteractor.callCategoryListAPI(userid);
    }

    @Override
    public void getResponse(Object object) {
        categoryInteractor.hideProgress();
        categoryInteractor.getResponse(object);
    }


}
