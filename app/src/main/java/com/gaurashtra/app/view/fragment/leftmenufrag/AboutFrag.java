package com.gaurashtra.app.view.fragment.leftmenufrag;


import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.LeftMenuBean.LeftMenuContentBean;
import com.gaurashtra.app.view.activity.HomeActivity;

import org.apache.commons.codec.binary.StringUtils;
import org.jsoup.helper.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okio.Utf8;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFrag extends Fragment {
    public static final String TAG = AboutFrag.class.getSimpleName();
    WebView webView;
    View view;
    String content;
    GlobalUtils globalUtils;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_about, container, false);
        webView= view.findViewById(R.id.wv_about_us);
        globalUtils= new GlobalUtils();
        getAboutUsContent();
        Activity activity = getActivity();
        if(activity instanceof HomeActivity){
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.hideBottomNav();
        }
        return view;
    }

    private void getAboutUsContent() {
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<LeftMenuContentBean> call= restInterface.getAboutUs(globalUtils.getKey(), globalUtils.getapidate());
        call.enqueue(new Callback<LeftMenuContentBean>() {
            @Override
            public void onResponse(Call<LeftMenuContentBean> call, Response<LeftMenuContentBean> response) {
                if(response.isSuccessful()){
                    final WebSettings webSettings = webView.getSettings();
                    webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                    webSettings.setDomStorageEnabled(true);
                    content= response.body().getResult().getAboutUs().getDescription();
                    Log.e("contentBeforeFilter",":"+content);
                    content= content.replaceAll("\"r","");
                    content= content.replaceAll("\"n","");
                    content= content.replaceAll("\"","");
                    content= content.replace("http","https");
                    if(content.contains("100%;>")){
                        content= content.replaceAll("100%;>","100%;></img>");
                        content= content.replaceAll("<p><img>","<img>");
                        content= content.replace("</img>","</img></p>");
                    }
                    Log.e("contentAfterFilter",":"+content);
                    String str="<html><body><style>img{display: inline;height: auto;max-width: 100%;}</style>"+content+"</body></html>";
                    webView.loadData(str, "text/html", "UTF-8");
                }else{
                    Toast.makeText(getActivity(), "Something went wrong! please try again later!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LeftMenuContentBean> call, Throwable t) {
                Log.e("ExcAbout",""+t);
                Toast.makeText(getActivity(), "Something went wrong! please try again later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
