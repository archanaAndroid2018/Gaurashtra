package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.HomePagebean.BrandProductData;
import com.gaurashtra.app.view.adapter.ProductBrandAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductByBrand extends AppCompatActivity implements ProductBrandAdapter.AddToCartInterface {
    RecyclerView recyclerView;
    ProductBrandAdapter adapter;
    List<BrandProductData.Result.BrandProductDatum> productList;
    String brandId, brandName,pageNo="1", perpageData="10";
    public static TextView textviewProductTitle, tvNodataMsg;
    GlobalUtils globalUtils= new GlobalUtils();
    String title="",currency="INR",currencySymbol="", optionId="",optionValueId="";
    double actualCurrValue=1.0,currencyValue=1.0;
    PrefClass prefClass;
    LinearLayoutManager linearLayoutManager;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems, currentPage=1, totalPages=1;
    FrameLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_by_brand);
        brandId= getIntent().getStringExtra(Constants.PreferenceConstants.BRANDID);
        brandName= getIntent().getStringExtra(Constants.PreferenceConstants.BRANDNAME);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewProductTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewProductTitle.setText(brandName);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        UserUpdate userUpdate= new UserUpdate(ProductByBrand.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(ProductByBrand.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(ProductByBrand.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        recyclerView= findViewById(R.id.rv_prod_by_brand);
        tvNodataMsg= findViewById(R.id.tv_no_data_msg);
        progress = findViewById(R.id.list_loader_frame);

        linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                if(isScrolling && (currentItems + scrollOutItems == totalItems) && currentPage< totalPages && totalPages>1) {
                    Log.e("onScroll","currentItems: "+currentItems+"\n totalItems: "+totalItems+
                            "\n scrollOutItems: "+scrollOutItems+"\ncurrentPage: "+currentPage+"\n totalPages: "+totalPages);
                    if (dy > 0) {
                        // scrolling up
                        pageNo = String.valueOf(currentPage + 1);
//                    } else {
                        // scrolling down
//                            if(!pageNo.equalsIgnoreCase("1")) {
//                                pageNo = String.valueOf(currentPage - 1);
//                            }
                    }
                    isScrolling = false;
                    progress.setVisibility(View.VISIBLE);

                    if(GlobalUtils.isNetworkAvailable(ProductByBrand.this)){
                        getProductListBybrand();
                    }else{
                        Toast.makeText(ProductByBrand.this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        prefClass= new PrefClass(this);
        java.lang.reflect.Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                currency=prefClass.getSelectedCurrency();
                actualCurrValue= Float.parseFloat(currencyData.getValue());
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
            }
        }
        if(GlobalUtils.isNetworkAvailable(this)){
            GlobalUtils.showdialog(this);
           getProductListBybrand();
        }else{
            GlobalUtils.hidedialog();
            Toast.makeText(this, "Please check your internet connection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void getProductListBybrand() {
        Map<String, String> param = new HashMap<>();
        param.put("userid", SharedPreference.getUserid(this));
        param.put("brandid", brandId);
        param.put("pageNo",pageNo);
        param.put("perpageData",perpageData);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<BrandProductData> call= restInterface.productByBrand(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<BrandProductData>() {
            @Override
            public void onResponse(Call<BrandProductData> call, Response<BrandProductData> response) {
                GlobalUtils.hidedialog();
                progress.setVisibility(View.GONE);
                    Log.e("ResponseBrand",""+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    try {
                        if(response.body().getSuccess()==1) {
                        if (response.body().getResult().getBrandProductData() != null) {
                            tvNodataMsg.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            currentPage = response.body().getResult().getCurrentPage();
                            totalPages = response.body().getResult().getTotalPage();
                            Log.e("Pages","current: "+currentPage+" \ntotal: "+totalPages);
                            if(currentPage==1){
                                productList = new ArrayList<>();
                                productList = response.body().getResult().getBrandProductData();
                                adapter = new ProductBrandAdapter(ProductByBrand.this, productList, ProductByBrand.this);
                                recyclerView.setAdapter(adapter);
                            }else{
                                productList.addAll(response.body().getResult().getBrandProductData());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            tvNodataMsg.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
//                            Toast.makeText(ProductByBrand.this, "No Data Available!", Toast.LENGTH_SHORT).show();
                        }
                        }else{
                            tvNodataMsg.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.GONE);
                            Toast.makeText(ProductByBrand.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
//                        tvNodataMsg.setVisibility(View.VISIBLE);
//                        recyclerView.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
               }

            @Override
            public void onFailure(Call<BrandProductData> call, Throwable t) {
                Log.e("ExcProdByBrand",""+t);
                progress.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void addToCart(String productId, String selectedQty, String action, String optId, String optValue) {
        optionId=optId;
        optionValueId= optValue;
        if(GlobalUtils.isNetworkAvailable(this)){
            GlobalUtils.showdialog(this);
            HashMap<String, String> params = new HashMap<>();
            params.put("userid",SharedPreference.getUserid(this));
            params.put("productid", productId);
            params.put("quantity", selectedQty);
            params.put("actiontype", action);
            params.put("currencyCode", currency);
            params.put("currencyValue", String.valueOf(currencyValue));
            params.put("optionid",optionId);
            params.put("optionvalueid",optionValueId);
            RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
            Call<AddCartResponseDTO> call= restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(),params);
            call.enqueue(new Callback<AddCartResponseDTO>() {
                @Override
                public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
                    if(response.isSuccessful()){
                        GlobalUtils.hidedialog();
                        Log.e("ResponseDeletedWishList",":"+response.body().getMessage());
                        Toast.makeText(ProductByBrand.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        GlobalUtils.hidedialog();
                        Toast.makeText(ProductByBrand.this, "Something went wrong, please try again!!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AddCartResponseDTO> call, Throwable t) {
                    GlobalUtils.hidedialog();
                    Log.e("ExcAddCartProdBrand",""+t);
                }
            });
        }else {
            GlobalUtils.hidedialog();
            Toast.makeText(this,"Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }
}
