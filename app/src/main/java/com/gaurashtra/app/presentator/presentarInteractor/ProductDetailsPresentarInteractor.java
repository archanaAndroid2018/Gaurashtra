package com.gaurashtra.app.presentator.presentarInteractor;

public interface ProductDetailsPresentarInteractor {
    public void sendProductDetailsRequest(String userid, String Pid, String optionId);
    public void getProductDetailsResponse(Object object);

}
