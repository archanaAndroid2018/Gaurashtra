package com.gaurashtra.app.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
//import io.branch.referral.Branch;
//import io.branch.referral.BranchError;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.FirebaseMessagingService;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.HomePagebean.HomepageDTO;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG ="SplashActivity" ;
    String useremail, userid="", deviceId="";
    String country="";
    GlobalUtils globalUtils = new GlobalUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Uri data = getIntent().getData ();
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e(TAG, "Refreshed token: " + newToken);
                deviceId = newToken;
                SharedPreference.setDeviceId(SplashActivity.this, newToken);
//                String locale = getResources().getConfiguration().locale.getCountry();

            }
        });
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String countryCode = tm.getSimCountryIso();
        if(!countryCode.isEmpty()) {
            if (countryCode.equalsIgnoreCase("in")) {
                country = "INDIA";
            } else {
                country = "OTHER";
            }
        }
        {
            if(GlobalUtils.isNetworkAvailable(this)) {
                new GetIpAddress().execute();
            }else{
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        }
        SharedPreference.setLocale(SplashActivity.this, country);
        Log.e(TAG,"country = "+countryCode);
        Log.e("DataFromDeeplink1",":"+data);
        if(!SharedPreference.getUserid(this).isEmpty()) {
            useremail = SharedPreference.getUserid(this);
            userid = SharedPreference.getUserid(SplashActivity.this);
        }
        if(GlobalUtils.isNetworkAvailable(SplashActivity.this)){
            callHomePageData();
        }else{
            GlobalUtils.showToast(SplashActivity.this, getResources().getString(R.string.no_internet_connection));
        }

        printHashKey();
    }
    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(SplashActivity.this.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.e(TAG, "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "printHashKey()", e);
        } catch (Exception e) {
            Log.e(TAG, "printHashKey()", e);
        }
    }

    private void callHomePageData() {
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", userid);
        param.put("deviceid", deviceId);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<HomepageDTO> call = call = restInterface.callFirstHomePageData(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<HomepageDTO>() {
            @Override
            public void onResponse(Call<HomepageDTO> call, Response<HomepageDTO> response) {
                try {
                    Log.e("SplashHomeResponse", ": " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response != null) {
                        if (response.body().getSuccess() == 1) {
                            String strObject = new Gson().toJson(response).toString();
                            SharedPreference.setHomeData(SplashActivity.this, strObject);
                            SharedPreference.setHomeLastRefreshedDate(SplashActivity.this, globalUtils.getapidate());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<HomepageDTO> call, Throwable t) {
                Log.e("ExcSplashHomeresp", ":" + t);
                GlobalUtils.showToast(SplashActivity.this, getResources().getString(R.string.label_something_went_wrong));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Thread splash = new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally {
                    try {
                        Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                        intent.putExtra(Constants.PreferenceConstants.LOAD, Constants.PreferenceConstants.FIRSTLOAD);
                        startActivity(intent);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        };
        splash.start();
    }
    private class GetIpAddress extends AsyncTask<String,String, JSONObject> {
        JSONObject result= null;
        @Override
        protected JSONObject doInBackground(String... strings) {
            try{
                Request request = new Request.Builder()
                        .url("https://api.ipify.org?format=json")
                        .get()
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                result = new JSONObject(response.body().string());
                Log.e("ResultIp",":"+result);
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                Log.e("IpResponse", ":" + jsonObject);
                String ip = jsonObject.optString("ip");
                SharedPreference.setIpAddress(SplashActivity.this,ip);
                if (!ip.isEmpty()) {
                    new GetLocale().execute(ip);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private class GetLocale extends AsyncTask<String,String, String> {
        String result= null;
        @Override
        protected String doInBackground(String... strings) {
            try{
                Request request = new Request.Builder()
                        .url("https://ipinfo.io/"+strings[0]+"/country")
                        .get()
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                result= response.body().string();
            }catch (Exception e){
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("LocaleResponse",":"+result);
            if (result.equalsIgnoreCase("IN") || result.contains("IN") || result.contains("in")) {
                country = "INDIA";
            } else {
                country = "OTHER";
            }
            SharedPreference.setLocale(SplashActivity.this, country);
        }
    }
}
