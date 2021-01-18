package com.gaurashtra.app.model.bean.NotificationBean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class NotificationDTO {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private Result result;

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }
    public class Result{
        @SerializedName("notificationData")
        @Expose
        private ArrayList<NotificationDatum> notificationData = null;

        public ArrayList<NotificationDatum> getNotificationData() {
            return notificationData;
        }

        public void setNotificationData(ArrayList<NotificationDatum> notificationData) {
            this.notificationData = notificationData;
        }

        public class NotificationDatum {
            @SerializedName("notification_id")
            @Expose
            private String notificationId;
            @SerializedName("notification_title")
            @Expose
            private String notificationTitle;
            @SerializedName("notification_content")
            @Expose
            private String notificationContent;
            @SerializedName("notification_date")
            @Expose
            private String notificationDate;
            @SerializedName("notification_type")
            @Expose
            private String notificationType;

            public String getNotificationId() {
                return notificationId;
            }

            public void setNotificationId(String notificationId) {
                this.notificationId = notificationId;
            }

            public String getNotificationTitle() {
                return notificationTitle;
            }

            public void setNotificationTitle(String notificationTitle) {
                this.notificationTitle = notificationTitle;
            }

            public String getNotificationContent() {
                return notificationContent;
            }

            public void setNotificationContent(String notificationContent) {
                this.notificationContent = notificationContent;
            }

            public String getNotificationDate() {
                return notificationDate;
            }

            public void setNotificationDate(String notificationDate) {
                this.notificationDate = notificationDate;
            }

            public String getNotificationType() {
                return notificationType;
            }

            public void setNotificationType(String notificationType) {
                this.notificationType = notificationType;
            }
        }
    }
}
