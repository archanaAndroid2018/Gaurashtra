package com.gaurashtra.app.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.OptionListBean;
import com.gaurashtra.app.view.adapter.OptionsAdapter;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class OptionsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    GlobalUtils globalUtils;
    String btnText="";
    ArrayList<OptionListBean.Result.ProductData.Option> optionList;

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        String prodTitle= getIntent().getStringExtra(Constants.PreferenceConstants.TITLE);
        String productId= getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTID);
        if(getIntent().hasExtra(Constants.PreferenceConstants.BTNACTION)){
            btnText= getIntent().getStringExtra(Constants.PreferenceConstants.BTNACTION);
        }
        String userId= SharedPreference.getUserid(this);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(                                   //Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView tvProductTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        globalUtils= new GlobalUtils();
        tvProductTitle.setText(prodTitle);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        UserUpdate userUpdate= new UserUpdate(OptionsActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(OptionsActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(OptionsActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }

        recyclerView= findViewById(R.id.rv_options);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getOptionList(userId, productId);
    }

    private void getOptionList(String userId, String productId) {
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<OptionListBean> call= restInterface.getOptionList(globalUtils.getKey(), globalUtils.getapidate(),userId, productId);
        call.enqueue(new Callback<OptionListBean>() {
            @Override
            public void onResponse(Call<OptionListBean> call, Response<OptionListBean> response) {
                Log.e("OPTIONRES",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if(response.body().getSuccess()==1){
                    optionList= new ArrayList<>();
                    optionList= response.body().getResult().getProductData().getOption();
                    OptionsAdapter adapter= new OptionsAdapter(OptionsActivity.this,optionList,2, response.body().getResult().getProductData().getData(),btnText);
                    recyclerView.setAdapter(adapter);
                }else{
                    Toast.makeText(OptionsActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OptionListBean> call, Throwable t) {
                Log.e("OptionExc",":"+t);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }
}
