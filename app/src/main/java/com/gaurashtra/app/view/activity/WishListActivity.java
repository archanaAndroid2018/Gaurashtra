package com.gaurashtra.app.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDTO;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDatum;
import com.gaurashtra.app.presentator.WishlistPresenter;
import com.gaurashtra.app.presentator.presentarInteractor.WishListPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.WishListInteractor;
import com.gaurashtra.app.view.adapter.WishListAdapter;
import retrofit2.Call;
import retrofit2.Callback;

public class WishListActivity extends AppCompatActivity implements WishListInteractor, WishListAdapter.ListActionInterface {
    private TextView textviewTitle, btnExplore;
    private RecyclerView wishRv;
    private WishListAdapter adapter;
    private WishListPresentarInteractor wishListPresentarInteractor;
    private GlobalUtils globalUtils;
    SharedPreference sharedPreference;
    RestInterface restInterface;
    String UserId;
    private LinearLayout linearLayout;
    private LinearLayoutManager manager;
    String title="",currency="INR",currencySymbol="",optionId="",optionValueId="";
    double actualCurrValue=1.0,currencyValue=1.0;
    PrefClass prefClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("My WishList");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        setUpContentView();

        UserUpdate userUpdate= new UserUpdate(WishListActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(WishListActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(WishListActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }

        UserId= SharedPreference.getUserid(this);
        wishListPresentarInteractor=new WishlistPresenter(this);
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
        globalUtils=new GlobalUtils();
        if(GlobalUtils.isNetworkAvailable(this)){
            wishListPresentarInteractor.sendRequest(UserId);
        }else {
            Toast.makeText(this,"Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }

    public void setUpContentView(){
        linearLayout=findViewById(R.id.Linear_Image);
        wishRv = findViewById(R.id.rvWish);
        btnExplore= findViewById(R.id.tv_btn_home);
        wishRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        wishRv.setLayoutManager(manager);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(WishListActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        finish();
        Intent intent= new Intent(WishListActivity.this,HomeActivity.class);
        intent.putExtra("BACKFROM","ME");
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void getResponse(Object object) {
        List<WishlistDatum> WishlistData=new ArrayList<>();
        WishlistDTO wishlistDTO=(WishlistDTO)object;
        String message=wishlistDTO.getMessage();
//        GlobalUtils.showToast(this,message);

        List<WishlistDatum> wishlist=wishlistDTO.getResult().getWishlistData();
        for(int i=0;i<wishlist.size();i++){
            WishlistDatum wishlistDatum=new WishlistDatum();
            wishlistDatum.setBrandId(wishlist.get(i).getBrandId());
            wishlistDatum.setBrandName(wishlist.get(i).getBrandName());
            wishlistDatum.setDiscountPrice(wishlist.get(i).getDiscountPrice());
            wishlistDatum.setDiscountQuantity(wishlist.get(i).getDiscountQuantity());
            wishlistDatum.setOptionId(wishlist.get(i).getOptionId());
            wishlistDatum.setOptionValueId(wishlist.get(i).getOptionValueId());
            wishlistDatum.setOptionType(wishlist.get(i).getOptionType());
            wishlistDatum.setProductId(wishlist.get(i).getProductId());
            wishlistDatum.setProductImage(wishlist.get(i).getProductImage());
            wishlistDatum.setProductPrice(wishlist.get(i).getProductPrice());
            wishlistDatum.setProductName(wishlist.get(i).getProductName());
            wishlistDatum.setSpecialPrice(wishlist.get(i).getSpecialPrice());
            wishlistDatum.setTotalRating(wishlist.get(i).getTotalRating());
            wishlistDatum.setTaxRate(wishlist.get(i).getTaxRate());
            wishlistDatum.setTaxRate(wishlist.get(i).getProductQuantity());
            WishlistData.add(wishlistDatum);
        }
//Change by Nidhi
        if (wishlist.size()==0){
            linearLayout.setVisibility(View.VISIBLE);
        }else {
            linearLayout.setVisibility(View.GONE);
        }

        adapter = new WishListAdapter(this,WishlistData, WishListActivity.this);
        wishRv.setAdapter(adapter);
    }

    @Override
    public void deleteItemFromWishList(String prodName, final String prodId, String qty, final String action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WishListActivity.this);
        builder.setMessage("Do want to remove this item from Wishlist?")
                .setTitle("Remove Product");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                removeItemMethod(prodId,action);
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void removeItemMethod(String prodId, String action) {
        restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<WishlistDTO> call= restInterface.addRemoveWishList(globalUtils.getKey(), globalUtils.getapidate(),UserId,prodId,action);
        call.enqueue(new Callback<WishlistDTO>() {
            @Override
            public void onResponse(Call<WishlistDTO> call, retrofit2.Response<WishlistDTO> response) {
                if(response.isSuccessful()){
                    GlobalUtils.hidedialog();
                    Log.e("ResponseDeletedWishList",":"+response.body().getMessage());
                    wishListPresentarInteractor.sendRequest(UserId);
//                    Toast.makeText(WishListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<WishlistDTO> call, Throwable t) {
                GlobalUtils.hidedialog();
                Log.e("ErrorinDelWish",""+t);
            }
        });
    }

    @Override
    public void addToCart(final String prodId, final String qty, final String action, String optId, String optValue) {
        optionId= optId;
        optionValueId= optValue;
        if(GlobalUtils.isNetworkAvailable(this)){
            GlobalUtils.showdialog(this);
            HashMap<String, String> params = new HashMap<>();
            params.put("userid", UserId);
            params.put("productid", prodId);
            params.put("quantity", qty);
            params.put("actiontype", "add");
            params.put("currencyCode", currency);
            params.put("currencyValue", String.valueOf(currencyValue));
            params.put("optionid",optionId);
            params.put("optionvalueid",optionValueId);
            restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
            Call<AddCartResponseDTO> call= restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(),params);
            call.enqueue(new Callback<AddCartResponseDTO>() {
                @Override
                public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
                    if(response.isSuccessful()){
                        GlobalUtils.hidedialog();
                        removeItemMethod(prodId, "delete");
                        Log.e("ResponseDeletedWishList",":"+response.body().getMessage());
                        Toast.makeText(WishListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        GlobalUtils.hidedialog();
                        Toast.makeText(WishListActivity.this, "Something went wrong, please try again!!", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AddCartResponseDTO> call, Throwable t) {
                    GlobalUtils.hidedialog();
                    Log.e("ExcAddCartWish",""+t);
                }
            });
        }else {
            GlobalUtils.hidedialog();
            Toast.makeText(this,"Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }
}
