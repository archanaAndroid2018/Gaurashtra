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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tcfrag extends Fragment {
    public static final String TAG = Tcfrag.class.getSimpleName() ;
    WebView webView;
    View view;
    String content;
    GlobalUtils globalUtils;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_tcfrag, container, false);
        webView= view.findViewById(R.id.wv_tc);
        globalUtils= new GlobalUtils();
        getTCContent();
        Activity activity = getActivity();
        if(activity instanceof HomeActivity){
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.hideBottomNav();
        }
        return view;
    }

    private void getTCContent() {
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<LeftMenuContentBean> call= restInterface.getTermsCondition(globalUtils.getKey(), globalUtils.getapidate());
        call.enqueue(new Callback<LeftMenuContentBean>() {
            @Override
            public void onResponse(Call<LeftMenuContentBean> call, Response<LeftMenuContentBean> response) {
                if(response.isSuccessful()){
                    final WebSettings webSettings = webView.getSettings();
//                    webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                    content= response.body().getResult().getTermsConditions().getDescription();
                    webView.loadDataWithBaseURL(null,   content, "text/html", "UTF-8", null);
                }else{
                    Toast.makeText(getActivity(), "Some error occured, please try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LeftMenuContentBean> call, Throwable t) {
                Log.e("ExcTerms",""+t);
                Toast.makeText(getActivity(), "Some error occured, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
