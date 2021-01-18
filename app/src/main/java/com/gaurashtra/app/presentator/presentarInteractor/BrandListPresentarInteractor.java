package com.gaurashtra.app.presentator.presentarInteractor;

public interface BrandListPresentarInteractor {
    public void validateCategoryRequest(String uid, String cid, String pageNo, String perpageData);
    public void sendCategoryResponse(Object object);
    public void validateCartRequest(String uid, String pid, String qty);
    public void sendCartResponse(Object object);
}
