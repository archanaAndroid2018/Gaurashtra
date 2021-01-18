package com.gaurashtra.app.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.WalletResponseModel;
import com.gaurashtra.app.model.bean.WalletResponseModel.Result;
import com.gaurashtra.app.model.bean.WalletResponseModel.Result.WalletDatum;
import com.gaurashtra.app.view.adapter.WalletAdapter;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {

    TextView tvNoAmount, textviewTitle;
    RecyclerView recyclerView;
    WalletAdapter adapter;
    GlobalUtils globalUtils = new GlobalUtils();
    ArrayList<WalletResponseModel.Result.WalletDatum> walletList = new ArrayList<>();


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(                                   //Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("My Wallet");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        tvNoAmount = findViewById(R.id.tv_no_amount);
        recyclerView = findViewById(R.id.rv_wallet);
        if(GlobalUtils.isNetworkAvailable(this)){
            GlobalUtils.showdialog(WalletActivity.this);
            getWalletList();
        }else{
            GlobalUtils.hidedialog();
            GlobalUtils.showToast(this, getResources().getString(R.string.no_internet_connection_try_later));
        }
    }

    private void getWalletList() {
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<WalletResponseModel> call =restInterface.getWalletData(globalUtils.getKey(),globalUtils.getapidate(), SharedPreference.getUserid(WalletActivity.this));
        call.enqueue(new Callback<WalletResponseModel>() {
            @Override
            public void onResponse(Call<WalletResponseModel> call, Response<WalletResponseModel> response) {
                Log.e("WalletResponse",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                GlobalUtils.hidedialog();
                try{
                    if(response.body().getSuccess()==1){
                        recyclerView.setVisibility(View.VISIBLE);
                        tvNoAmount.setVisibility(View.GONE);
                        walletList = new ArrayList<>();
                        walletList = response.body().getResult().getWalletData();
                       if(walletList.size()>0) {

                           LinearLayoutManager layoutManager = new LinearLayoutManager(WalletActivity.this);
                           recyclerView.setLayoutManager(layoutManager);
                           adapter = new WalletAdapter(WalletActivity.this, walletList);
                           recyclerView.setAdapter(adapter);
                       }else{
                           recyclerView.setVisibility(View.GONE);
                           tvNoAmount.setVisibility(View.VISIBLE);
                       }
                    }else{
                        recyclerView.setVisibility(View.GONE);
                        tvNoAmount.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<WalletResponseModel> call, Throwable t) {
                Log.e("ExcWallet",":"+t);
                GlobalUtils.hidedialog();
                GlobalUtils.showToast(WalletActivity.this, getResources().getString(R.string.label_something_went_wrong));
            }
        });
    }
}