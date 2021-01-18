package com.gaurashtra.app.model.bean.ProductDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AskQuestion {
    @SerializedName("questioner_name")
    @Expose
    private String questionerName;
    @SerializedName("questioner_content")
    @Expose
    private String questionerContent;
    @SerializedName("questioner_date")
    @Expose
    private String questionerDate;
    @SerializedName("replayer_name")
    @Expose
    private String replayerName;
    @SerializedName("replayer_content")
    @Expose
    private String replayerContent;
    @SerializedName("replayer_date")
    @Expose
    private String replayerDate;

    public String getQuestionerName() {
        return questionerName;
    }

    public void setQuestionerName(String questionerName) {
        this.questionerName = questionerName;
    }

    public String getQuestionerContent() {
        return questionerContent;
    }

    public void setQuestionerContent(String questionerContent) {
        this.questionerContent = questionerContent;
    }

    public String getQuestionerDate() {
        return questionerDate;
    }

    public void setQuestionerDate(String questionerDate) {
        this.questionerDate = questionerDate;
    }

    public String getReplayerName() {
        return replayerName;
    }

    public void setReplayerName(String replayerName) {
        this.replayerName = replayerName;
    }

    public String getReplayerContent() {
        return replayerContent;
    }

    public void setReplayerContent(String replayerContent) {
        this.replayerContent = replayerContent;
    }

    public String getReplayerDate() {
        return replayerDate;
    }

    public void setReplayerDate(String replayerDate) {
        this.replayerDate = replayerDate;
    }
}
