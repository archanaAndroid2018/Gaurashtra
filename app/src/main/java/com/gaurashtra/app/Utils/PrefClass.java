package com.gaurashtra.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.gaurashtra.app.R;

public class PrefClass {

    public SharedPreferences sharedPreferences;
    private Context context;

    public PrefClass(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.pref_file), Context.MODE_PRIVATE);
    }

    public void setUserEmail(String email){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.userMail),email);
        editor.commit();
    }

    public String getUserMail(){
        return sharedPreferences.getString(context.getString(R.string.userMail),"");
    }
    public void setUserMobile(String mobile){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.user_mobile),mobile);
        editor.commit();
    }

    public String getUserMobile(){
        return sharedPreferences.getString(context.getString(R.string.user_mobile),"");
    }

    public void setCustomerId(String cid){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.customerId),cid);
        editor.commit();
    }
    public String getCustomerId(){
        return sharedPreferences.getString(context.getString(R.string.customerId),"");
    }

    public void setCurrencyDataList(String currencyDataList){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currencyDataList",currencyDataList);
        editor.commit();
    }

    public String getCurrencyDataList(){
        return sharedPreferences.getString("currencyDataList","");
    }

    public void setSelectedCurrency(String currency){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("SelectedCurrency",currency);
        editor.commit();
    }

    public String getSelectedCurrency(){
        return sharedPreferences.getString("SelectedCurrency","INR");
    }
    public void setHomeDetails(String homeDetails){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("homeDetails",homeDetails);
        editor.commit();
    }

    public String getHomeDetails(){
        return sharedPreferences.getString("homeDetails","");
    }
}
