package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Result implements Serializable {
    @SerializedName("offerText")
    @Expose
    private OfferText offerText;
    @SerializedName("sliderData")
    @Expose
    private List<SliderDatum> sliderData = null;
    @SerializedName("afterSlider")
    @Expose
    private AfterSlider afterSlider;
    @SerializedName("topSellingData")
    @Expose
    private List<TopSellingDatum> topSellingData = null;
    @SerializedName("categoryData")
    @Expose
    private List<CategoryDatum> categoryData = null;
    @SerializedName("todayDeal")
    @Expose
    private TodayDeal todayDeal;
    @SerializedName("afterTodayDeal")
    @Expose
    private List<AfterBestOFDatum> afterTodayDeal = null;
    @SerializedName("videoData")
    @Expose
    private List<VideoDatum> videoData = null;
    @SerializedName("bestOf")
    @Expose
    private BestOf bestOf;
    @SerializedName("afterBestOfData")
    @Expose
    private List<AfterBestOFDatum> afterBestOfData = null;
    @SerializedName("recentlyViewData")
    @Expose
    private List<TopSellingDatum> recentlyViewData = null;
    @SerializedName("shopByBrandData")
    @Expose
    private List<ShopByBrandDatum> shopByBrandData = null;
    @SerializedName("afterShopByData")
    @Expose
    private List<AfterBestOFDatum> afterShopByData = null;
    @SerializedName("recommendedData")
    @Expose
    private List<TopSellingDatum> recommendedData = null;
    @SerializedName("footerBannerData")
    @Expose
    private List<AfterBestOFDatum> footerBannerData = null;
    @SerializedName("brandProductData")
    @Expose
    private List<BrandProductData> brandProductData = null;

    public OfferText getOfferText() {
        return offerText;
    }

    public void setOfferText(OfferText offerText) {
        this.offerText = offerText;
    }

    public List<SliderDatum> getSliderData() {
        return sliderData;
    }

    public void setSliderData(List<SliderDatum> sliderData) {
        this.sliderData = sliderData;
    }

    public AfterSlider getAfterSlider() {
        return afterSlider;
    }

    public void setAfterSlider(AfterSlider afterSlider) {
        this.afterSlider = afterSlider;
    }

    public List<TopSellingDatum> getTopSellingData() {
        return topSellingData;
    }

    public void setTopSellingData(List<TopSellingDatum> topSellingData) {
        this.topSellingData = topSellingData;
    }

    public List<CategoryDatum> getCategoryData() {
        return categoryData;
    }

    public void setCategoryData(List<CategoryDatum> categoryData) {
        this.categoryData = categoryData;
    }

    public TodayDeal getTodayDeal() {
        return todayDeal;
    }

    public void setTodayDeal(TodayDeal todayDeal) {
        this.todayDeal = todayDeal;
    }



    public List<VideoDatum> getVideoData() {
        return videoData;
    }

    public void setVideoData(List<VideoDatum> videoData) {
        this.videoData = videoData;
    }

    public BestOf getBestOf() {
        return bestOf;
    }

    public void setBestOf(BestOf bestOf) {
        this.bestOf = bestOf;
    }

    public List<AfterBestOFDatum> getAfterBestOfData() {
        return afterBestOfData;
    }

    public void setAfterBestOfData(List<AfterBestOFDatum> afterBestOfData) {
        this.afterBestOfData = afterBestOfData;
    }

    public List<TopSellingDatum> getRecentlyViewData() {
        return recentlyViewData;
    }

    public void setRecentlyViewData(List<TopSellingDatum> recentlyViewData) {
        this.recentlyViewData = recentlyViewData;
    }


    public List<ShopByBrandDatum> getShopByBrandData() {
        return shopByBrandData;
    }

    public void setShopByBrandData(List<ShopByBrandDatum> shopByBrandData) {
        this.shopByBrandData = shopByBrandData;
    }



    public List<TopSellingDatum> getRecommendedData() {
        return recommendedData;
    }

    public void setRecommendedData(List<TopSellingDatum> recommendedData) {
        this.recommendedData = recommendedData;
    }

    public List<AfterBestOFDatum> getAfterTodayDeal() {
        return afterTodayDeal;
    }

    public void setAfterTodayDeal(List<AfterBestOFDatum> afterTodayDeal) {
        this.afterTodayDeal = afterTodayDeal;
    }

    public List<AfterBestOFDatum> getAfterShopByData() {
        return afterShopByData;
    }

    public void setAfterShopByData(List<AfterBestOFDatum> afterShopByData) {
        this.afterShopByData = afterShopByData;
    }

    public List<AfterBestOFDatum> getFooterBannerData() {
        return footerBannerData;
    }

    public void setFooterBannerData(List<AfterBestOFDatum> footerBannerData) {
        this.footerBannerData = footerBannerData;
    }

    public List<BrandProductData> getBrandProductData() {
        return brandProductData;
    }

    public void setBrandProductData(List<BrandProductData> brandProductData) {
        this.brandProductData = brandProductData;
    }
    @SerializedName("totalPage")
    @Expose
    private Integer totalPage;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
