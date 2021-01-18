package com.gaurashtra.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.PaymentPayUmoneyActivity;
import com.gaurashtra.app.view.activity.SplashActivity;

public class SharedPreference {
    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static Boolean getIsRememberMe(Context context) {
        return getSharedPreferences(context).getBoolean(Constants.PreferenceConstants.REMEMBERME, false);
    }

    public static void setIsRememberMe(Context context, Boolean isRemember) {
        getSharedPreferences(context).edit().putBoolean(Constants.PreferenceConstants.REMEMBERME, isRemember).apply();
    }
    public static String getUserid(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.USERID, "");
    }

    public static void setUserId(Context context, String userId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.USERID, userId).apply();
    }

    public static String getUserEmail(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.USEREMAIL, "");
    }

    public static void setUserEmail(Context context, String userId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.USEREMAIL, userId).apply();
    }
    public static String getSocialEmail(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.SOCIALEMAIL, "");
    }

    public static void setSocialEmail(Context context, String socialEmail) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.SOCIALEMAIL, socialEmail).apply();
    }

    public static String getUserPassword(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.USERPASSWORD, "");
    }

    public static void setUserPassword(Context context, String userId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.USERPASSWORD, userId).apply();
    }

    public static String getFirstName(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.FIRSTNAME, "");
    }

    public static void setFirstName(Context context, String firstName) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.FIRSTNAME, firstName).apply();
    }

    public static String getLastName(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.LASTNAME, "");
    }

    public static void setLastName(Context context, String lastName) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.LASTNAME, lastName).apply();
    }

    public static String getcategoryId(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.CATEGORYID, "");
    }

    public static void setCategoryId(Context context, String catId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.CATEGORYID, catId).apply();
    }

    public static String getcountryId(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.COUNTRYID, "");
    }

    public static void setcountryId(Context context, String countryId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.COUNTRYID, countryId).apply();
    }

    public static String getAddressId(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.ADDRESSID, "");
    }

    public static void setAddressId(Context context, String addressId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.ADDRESSID, addressId).apply();
    }

    public static String getDefAddressId(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.DEFADDRESSID, "");
    }

    public static void setDefAddressId(Context context, String defAdd) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.DEFADDRESSID, defAdd).apply();
    }

    public static String getUserAddress1(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEEADDRESS1, "");
    }

    public static void setUserAddress1(Context context, String userAdd1) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEEADDRESS1, userAdd1).apply();
    }

    public static String getUserAddress2(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEEADDRESS2, "");
    }

    public static void setUserAddress2(Context context, String userAdd2) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEEADDRESS2, userAdd2).apply();
    }

    public static String getUserCity(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEECITY, "");
    }

    public static void setUserCity(Context context, String userCity) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEECITY, userCity).apply();
    }

    public static String getUserState(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEEZONE, "");
    }

    public static void setUserState(Context context, String userCity) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEEZONE, userCity).apply();
    }

    public static String getUserStateId(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEEZONEID, "");
    }

    public static void setUserStateId(Context context, String userStateId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEEZONEID, userStateId).apply();
    }

    public static String getUserCountryId(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEECOUNTRYID, "");
    }

    public static void setUserCountryId(Context context, String userCountryId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEECOUNTRYID, userCountryId).apply();
    }

    public static String getUserCountry(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEECOUNTRY, "");
    }

    public static void setUserCountry(Context context, String userCountry) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEECOUNTRY, userCountry).apply();
    }

    public static String getUserPhone(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEEPHONE, "");
    }

    public static void setUserPhone(Context context, String userPhone) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEEPHONE, userPhone).apply();
    }


    public static String getdefAddressPhone(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.DEFADDRESSPHONE, "");
    }

    public static void setdefAddressPhone(Context context, String userPhone) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.DEFADDRESSPHONE, userPhone).apply();
    }

    public static String getUserZipcode(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEEZIP, "");
    }

    public static void setUserZipcode(Context context, String userZipcode) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEEZIP, userZipcode).apply();
    }

    public static String getUserCompany(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.PAYEECOMPANY, "");
    }

    public static void setUserCompany(Context context, String userCmpny) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.PAYEECOMPANY, userCmpny).apply();
    }

    public static void setCurrency(Context context, String currency) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.CURRENCY, currency).apply();
    }

    public static String getCurrency(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.CURRENCY, "INR");
    }
    public static void setCartCount(Context context, String count) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.CARTCOUNT, count).apply();
    }

    public static String getCartCount(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.CARTCOUNT, "");
    }

    public static void setDeviceId(Context context, String count) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.DEVICEID, count).apply();
    }

    public static String getDeviceId(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.DEVICEID, "");
    }

//    =================PayUMoney==============

    public static void setTxnIdFromServer(Context context, String txnId) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.TXNID, txnId).apply();
    }

    public static String getTxnIdFromServer(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.TXNID, "");
    }

    public static void setAmountFromServer(Context context, String amount) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.AMOUNTPAYU, amount).apply();
    }

    public static String getAmountFromServer(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.AMOUNTPAYU, "");
    }

    public static void setPaymentStatusMessageFromServer(Context context, String errorMessage) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.ERRORMSG, errorMessage).apply();
    }
    public static String getPaymentStatusMessageFromServer(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.ERRORMSG, "");
    }

//=================================LOCALE=====================================

    public static void setLocale(Context context, String country) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.LOCALE, country).apply();
    }
    public static String getLocale(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.LOCALE, "");
    }

    public static void setIpAddress(Context context, String ip) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.IPADDRESS, ip).apply();
    }
    public static String getIpAddress(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.IPADDRESS, "");
    }

    public static void setHomeData(Context context, String list) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.HOME_DATA, list).apply();
    }
    public static String getHomeData(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.HOME_DATA, "");
    }

    public static void setHomeLastRefreshedDate(Context context, String list) {
        getSharedPreferences(context).edit().putString(Constants.PreferenceConstants.HOME_REF_DATE, list).apply();
    }
    public static String getsetHomeLastRefreshedDate(Context context) {
        return getSharedPreferences(context).getString(Constants.PreferenceConstants.HOME_REF_DATE, "");
    }
}
