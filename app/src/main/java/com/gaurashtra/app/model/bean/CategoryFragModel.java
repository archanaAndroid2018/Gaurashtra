package com.gaurashtra.app.model.bean;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.WebServiceHandler;
import com.gaurashtra.app.model.api.WebServiceResponse;
import com.gaurashtra.app.model.modelInteractor.CategoryFragModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.CategoryPresentarInteractor;

public class CategoryFragModel implements CategoryFragModelInteractor,WebServiceResponse {
    private CategoryPresentarInteractor categoryPresentarInteractor;
    private GlobalUtils globalUtils;
    private PrefClass prefClass;
    private SharedPreference sharedPreference;
    String UserId;

    public CategoryFragModel(CategoryPresentarInteractor categoryPresentarInteractor){
        this.categoryPresentarInteractor = categoryPresentarInteractor;
        globalUtils = new GlobalUtils();
        //UserId=prefClass.getCustomerId();
        Log.e("USERId",""+UserId);
    }
    @Override
    public void callCategoryListAPI(String uid) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",uid);
        new WebServiceHandler().callCategoryListAPI(this,globalUtils.getKey(),globalUtils.getapidate(),map);

    }



    @Override
    public void OnSuccess(Object object) {
        categoryPresentarInteractor.getResponse(object);

    }

    @Override
    public void OnFailure() {
        globalUtils.hidedialog();

    }


}
