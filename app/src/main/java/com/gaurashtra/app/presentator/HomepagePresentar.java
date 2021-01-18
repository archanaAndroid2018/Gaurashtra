package com.gaurashtra.app.presentator;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.HomePageModel;
import com.gaurashtra.app.model.modelInteractor.HomepageModelInteractor;
import com.gaurashtra.app.presentator.presentarInteractor.HomepagePresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.HomepageInteractor;

public class HomepagePresentar implements HomepagePresentarInteractor {
    private HomepageInteractor homepageInteractor;
    private HomepageModelInteractor homepageModelInteractor;
    private GlobalUtils globalUtils;


    public HomepagePresentar(HomepageInteractor homepageInteractor){
        this.homepageInteractor=homepageInteractor;
        homepageModelInteractor=new HomePageModel(this);
        globalUtils=new GlobalUtils();
    }

    @Override
    public void sendHomeRequest(String userid, String deviceId) {
        homepageModelInteractor.callHomePageAPI(userid, deviceId);

    }

    @Override
    public void getHomeResponse(Object object) {
        homepageInteractor.getResponse(object);

    }
}
