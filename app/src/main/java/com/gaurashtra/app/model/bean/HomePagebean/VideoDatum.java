package com.gaurashtra.app.model.bean.HomePagebean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoDatum {
    @SerializedName("viceoId")
    @Expose
    private String viceoId;
    @SerializedName("videoTitle")
    @Expose
    private String videoTitle;
    @SerializedName("videoDescription")
    @Expose
    private String videoDescription;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;

    public String getViceoId() {
        return viceoId;
    }

    public void setViceoId(String viceoId) {
        this.viceoId = viceoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
