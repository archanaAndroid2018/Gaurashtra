package com.gaurashtra.app.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.Utils.UserUpdate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.GetCartData.GetCartProduct;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDTO;
import com.gaurashtra.app.view.adapter.CartAdapter;
import com.gaurashtra.app.view.adapter.CouponAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, CartAdapter.AddRemoveInterface {
    private TextView tvShoppingOffer, textviewTitle, tvSubtotalTitle, tvSubtotalAmount,tvCouponLabel, tvCouponAmount, tvShippingAmount,
            tvWalletAmount,tvProductWeight, tvCartTotalAmount, tvOtherAmount, tvTaxTitle1, tvTaxTitle2, tvTaxTitle3,
            tvTaxTitle4, tvTaxAmount1, tvTaxAmount2, tvTaxAmount3, tvTaxAmount4, tvOtherTitle, tvBtnContIfEmpty,
            tvCurrency1,tvCurrency2,tvCurrency3,tvCurrency4,tvCurrency5,tvCurrency6,tvCurrency7,tvCurrency8,tvCurrency9,tvCurrency10;
    String subTotalAmount, couponAmount, shippingAmount, taxAmount1, taxAmount2, taxAmount3, taxAmount4, walletAmount,
            cartTotalAmount, otherAmount, cartSessionId,couponTitle="";
    private RecyclerView cartRv;
    private CartAdapter adapter;
    private LinearLayout proceedPayment,tax1Layout, tax2Layout, tax3Layout, tax4Layout, couponAmountLayout,
            walletAmountLayout, continueShopLayout, emptyCartlayout, hiddenLayout1, hiddenLayout2,totalAmountLayout, expandLayout;
    RelativeLayout mainLayout, nonEmptyCartLayout, billLayout;
    RestInterface restInterface;
    GlobalUtils globalUtils;
    ArrayList<GetCartProduct.Result.CouponDatum> couponList;
    List<GetCartProduct.Result.CartDetails> cartDataList;
    GetCartProduct.Result.CartInfo cartInfoData;
    ImageView ivExpandCollapse;

    String UserId;
    Boolean hideToast= false, isBillShown=false;

    private String optionId="",optionValueId="";
    PrefClass prefClass;
    String currencySymbol="", selectedcurrency="INR", originalvalue="1";
    float currencyValue= (float) 1.000;
    Dialog couponDialog=null;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvShoppingOffer=findViewById(R.id.TextHome);
        tvShoppingOffer.setVisibility(View.GONE);
//        tvShoppingOffer.setText(SharedPreference.getShoppingOfferMsg(this));
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Cart");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        UserUpdate userUpdate= new UserUpdate(CartActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(CartActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(CartActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        globalUtils = new GlobalUtils();
        UserId = SharedPreference.getUserid(this);
        setUpContentView();
        setOnClickListner();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(GlobalUtils.isNetworkAvailable(CartActivity.this)) {
            if (SharedPreference.getLocale(CartActivity.this).equalsIgnoreCase("INDIA")) {
                selectedcurrency = "INR";
            } else {
                selectedcurrency = "USD";
            }
            getCartData();
        }else{
            GlobalUtils.showToast(CartActivity.this, getResources().getString(R.string.cb_no_internet));
        }
    }

    private void setSelectedCurrency() {
        prefClass= new PrefClass(this);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                selectedcurrency=prefClass.getSelectedCurrency();
                originalvalue= currencyData.getValue();
                String value= new DecimalFormat("0.000").format(Float.parseFloat(originalvalue));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
                setCurrencyInBill(currencySymbol);
            }
        }
    }

    private void setCurrencyInBill(String currencySymbol) {
        tvCurrency1.setText(currencySymbol);
        tvCurrency2.setText(currencySymbol);
        tvCurrency3.setText(currencySymbol);
        tvCurrency4.setText(currencySymbol);
        tvCurrency5.setText(currencySymbol);
        tvCurrency6.setText(currencySymbol);
        tvCurrency7.setText(currencySymbol);
        tvCurrency8.setText(currencySymbol);
        tvCurrency9.setText(currencySymbol);
        tvCurrency10.setText(currencySymbol);
    }

    private void getCartData() {
        if (GlobalUtils.isNetworkAvailable(this)) {
            setSelectedCurrency();
            GlobalUtils.showdialog(this);
            Map<String, String> param = new HashMap<>();
            param.put("userid", UserId);
            PrefClass prefClass=new PrefClass(CartActivity.this);
            Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
            List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
            for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
                if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode()));{
                    param.put("currencyCode", prefClass.getSelectedCurrency());
                    param.put("currencyValue", currencyData.getValue());
                }
            }
            restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
            Call<GetCartProduct> call = restInterface.getCartData(globalUtils.getKey(), globalUtils.getapidate(), param);
            call.enqueue(new Callback<GetCartProduct>() {
                @Override
                public void onResponse(Call<GetCartProduct> call, Response<GetCartProduct> response) {
                    try {
                        GlobalUtils.hidedialog();
                        mainLayout.setVisibility(View.VISIBLE);
                        if (response.body().getSuccess() != 0) {
                            nonEmptyCartLayout.setVisibility(View.VISIBLE);
                            emptyCartlayout.setVisibility(View.GONE);
                            Log.e("ResGetCart", "" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                            couponList = new ArrayList<>();
                            cartDataList = response.body().getResult().getCartData();
                            cartInfoData = response.body().getResult().getCartInfo();
                            cartSessionId = response.body().getResult().getCartSessionId();
                            GetCartProduct.Result.CartInfo cartInfo = response.body().getResult().getCartInfo();
                            GetCartProduct.Result.AppliedCouponData appliedCouponData = response.body().getResult().getAppliedCouponData();
                            couponList = (ArrayList<GetCartProduct.Result.CouponDatum>) response.body().getResult().getCouponData();
                            subTotalAmount = String.valueOf(cartInfo.getSubTotal().getValue());

                            tvSubtotalAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(subTotalAmount) * currencyValue));
                            couponAmount = String.valueOf(cartInfo.getCoupon().getValue());
                            couponTitle = cartInfo.getCoupon().getTitle();
                            if (cartInfo.getCoupon().getValue()==0) {
                                couponAmountLayout.setVisibility(View.GONE);
                            } else {
                                couponAmountLayout.setVisibility(View.VISIBLE);
                                if(cartInfo.getCoupon().getTitle().isEmpty()) {
                                    tvCouponLabel.setText("Coupon (-)");
                                }else{
                                    tvCouponLabel.setText(cartInfo.getCoupon().getTitle()+"(-)");
                                }
                                tvCouponAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(couponAmount) * currencyValue));
                            }

                            shippingAmount = String.valueOf(cartInfo.getShipping().getValue());
                            tvShippingAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(shippingAmount) * currencyValue));
                            for (int i = 0; i < cartInfo.getTax().size(); i++) {
                                if (i == 0) {
                                    taxAmount1 = String.valueOf(cartInfo.getTax().get(0).getValue());
                                    tax1Layout.setVisibility(View.VISIBLE);
                                    tvTaxTitle1.setText(String.valueOf(cartInfo.getTax().get(0).getTitle()));
                                    tvTaxAmount1.setText(new DecimalFormat("0.00").format(Float.parseFloat(taxAmount1) * currencyValue));
                                } else if (i == 1) {
                                    taxAmount2 = String.valueOf(cartInfo.getTax().get(1).getValue());
                                    tax2Layout.setVisibility(View.VISIBLE);
                                    tvTaxTitle2.setText(String.valueOf(cartInfo.getTax().get(1).getTitle()));
                                    tvTaxAmount2.setText(new DecimalFormat("0.00").format(Float.parseFloat(taxAmount2) * currencyValue));
                                } else if (i == 2) {
                                    taxAmount3 = String.valueOf(cartInfo.getTax().get(2).getValue());
                                    tax3Layout.setVisibility(View.VISIBLE);
                                    tvTaxTitle3.setText(String.valueOf(cartInfo.getTax().get(2).getTitle()));
                                    tvTaxAmount3.setText(new DecimalFormat("0.00").format(Float.parseFloat(taxAmount3) * currencyValue));
                                } else if (i == 3) {
                                    taxAmount4 = String.valueOf(cartInfo.getTax().get(3).getValue());
                                    tax4Layout.setVisibility(View.VISIBLE);
                                    tvTaxTitle4.setText(String.valueOf(cartInfo.getTax().get(3).getTitle()));
                                    tvTaxAmount4.setText(new DecimalFormat("0.00").format(Float.parseFloat(taxAmount4) * currencyValue));
                                }
                            }
                            if (cartInfo.getWallet() != null) {
                                walletAmount = String.valueOf(cartInfo.getWallet().getValue());
                                if (Float.parseFloat(walletAmount) == 0.0000) {
                                    walletAmountLayout.setVisibility(View.GONE);
                                } else {
                                    walletAmountLayout.setVisibility(View.VISIBLE);
                                    tvWalletAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(walletAmount) * currencyValue));
                                }
                            } else {
                                walletAmountLayout.setVisibility(View.GONE);
                            }
                            cartTotalAmount = String.valueOf(cartInfo.getCartTotal().getValue());
                            tvCartTotalAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(cartTotalAmount) * currencyValue));
                            otherAmount = String.valueOf(cartInfo.getOther().getTotalSaving() * currencyValue);
                            tvOtherTitle.setText("Total Saving");
                            tvOtherAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(otherAmount)));
                            String shippingInfo = cartInfo.getShipping().getTitle();
                                double w = cartInfo.getOther().getTotalWeight();
//                                double prodWeight=0;
//                                if(w >=1000.00){
//                                    double z= 1000.00/w;
//
//                                }
                                String weight = String.valueOf(w);
                                if (!weight.isEmpty()) {
                                    tvProductWeight.setVisibility(View.VISIBLE);
                                    weight = "(Weight: " + weight+" gm )";
                                } else {
                                    tvProductWeight.setVisibility(View.GONE);
                                }

                                tvProductWeight.setText(weight);
                            cartTotalAmount = String.valueOf(cartInfo.getCartTotal().getValue());
                            tvCartTotalAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(cartTotalAmount) * currencyValue));

                            LinearLayoutManager manager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false);
                            cartRv.setLayoutManager(manager);
                            adapter = new CartAdapter(CartActivity.this, cartDataList, CartActivity.this);
                            cartRv.setAdapter(adapter);
                        } else {
                            nonEmptyCartLayout.setVisibility(View.GONE);
                            emptyCartlayout.setVisibility(View.VISIBLE);
//                        Toast.makeText(CartActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<GetCartProduct> call, Throwable t) {
                    Log.e("ErrorRes", "" + t);
                }
            });
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }

    public void setUpContentView() {
        ivExpandCollapse = findViewById(R.id.iv_expand_collapse);
        tvProductWeight = findViewById(R.id.tv_weight_info);
        cartRv = findViewById(R.id.rvCartitem);
        proceedPayment = findViewById(R.id.llPayment);
        tvSubtotalTitle = findViewById(R.id.tv_subtotal_title);
        tvSubtotalAmount = findViewById(R.id.tv_subtotal_price);
        tvShippingAmount = findViewById(R.id.tv_shipping_amount);
        tvCouponAmount = findViewById(R.id.tv_coupon_amount);
        tvTaxAmount1 = findViewById(R.id.tv_tax_amount1);
        tvTaxAmount2 = findViewById(R.id.tv_tax_amount2);
        tvTaxAmount3 = findViewById(R.id.tv_tax_amount3);
        tvTaxAmount4 = findViewById(R.id.tv_tax_amount4);
        tvTaxTitle1 = findViewById(R.id.tv_tax1_title);
        tvTaxTitle2 = findViewById(R.id.tv_tax2_title);
        tvTaxTitle3 = findViewById(R.id.tv_tax3_title);
        tvTaxTitle4 = findViewById(R.id.tv_tax4_title);
        tax1Layout = findViewById(R.id.tax1_layout);
        tax2Layout = findViewById(R.id.tax2_layout);
        tax3Layout = findViewById(R.id.tax3_layout);
        tax4Layout = findViewById(R.id.tax4_layout);
        mainLayout = findViewById(R.id.cart_main_layout);
        billLayout = findViewById(R.id.bill_layout);
        emptyCartlayout = findViewById(R.id.empty_cart_layout);
        nonEmptyCartLayout = findViewById(R.id.nonEmpty_cart_layout);
        mainLayout.setVisibility(View.GONE);
        continueShopLayout = findViewById(R.id.llContinue);
        tvCouponLabel = findViewById(R.id.tv_coupon_title);
        couponAmountLayout = findViewById(R.id.coupon_amount_layout);
        walletAmountLayout = findViewById(R.id.wallet_layout);
        couponAmountLayout.setVisibility(View.GONE);
        tvWalletAmount = findViewById(R.id.tv_wallet_amount);
        tvCartTotalAmount = findViewById(R.id.tv_cart_total_amount);
        tvOtherAmount = findViewById(R.id.tv_other_amount);
        tvOtherTitle = findViewById(R.id.tv_other_title);
        tvBtnContIfEmpty = findViewById(R.id.tv_btn_cont_empty);
        cartRv.setHasFixedSize(true);
        hiddenLayout1= findViewById(R.id.cart_info_layout);
        hiddenLayout2= findViewById(R.id.before_coupon_layout);
        totalAmountLayout= findViewById(R.id.layout_total);
        expandLayout= findViewById(R.id.expand_layout);
        tvCurrency1= findViewById(R.id.tv_currencySymbol1);
        tvCurrency2= findViewById(R.id.tv_currencySymbol2);
        tvCurrency3= findViewById(R.id.tv_currencySymbol3);
        tvCurrency4= findViewById(R.id.tv_currencySymbol4);
        tvCurrency5= findViewById(R.id.tv_currencySymbol5);
        tvCurrency6= findViewById(R.id.tv_currencySymbol6);
        tvCurrency7= findViewById(R.id.tv_currencySymbol7);
        tvCurrency8= findViewById(R.id.tv_currencySymbol8);
        tvCurrency9= findViewById(R.id.tv_currencySymbol9);
        tvCurrency10= findViewById(R.id.tv_currencySymbol10);
        hideBillLayout();
    }

    public void setOnClickListner() {
        proceedPayment.setOnClickListener(this);
        continueShopLayout.setOnClickListener(this);
        tvBtnContIfEmpty.setOnClickListener(this);
        totalAmountLayout.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        menu.findItem(R.id.notification).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.currency_action) {
            ProgressDialog progressDialog = new ProgressDialog(CartActivity.this);
            progressDialog.setMax(100);
            progressDialog.setMessage("Currency updating...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            displayCurrencyChangeOptionDialog(progressDialog);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llPayment:
                Intent intent = new Intent(this, AddressActivity.class);
                intent.putExtra(Constants.PreferenceConstants.AMOUNTTOPAY, (new Gson().toJson(cartInfoData)));
                intent.putExtra(Constants.PreferenceConstants.PRODUCTQTY, String.valueOf(cartDataList.size()));
                intent.putExtra(Constants.PreferenceConstants.CARTSESSIONID, cartSessionId);
                startActivity(intent);
                break;
            case R.id.llContinue:
                Intent intentHome = new Intent(CartActivity.this, HomeActivity.class);
                intentHome.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentHome);
                finish();
                break;
            case R.id.tv_btn_cont_empty:
                Intent intentCont = new Intent(CartActivity.this, HomeActivity.class);
                startActivity(intentCont);
                break;
            case R.id.layout_total:
                if(isBillShown){
                    hideBillLayout();
                }else {
                    displayBillLayout();
                }
                break;
        }
    }
    public void displayCurrencyChangeOptionDialog(final ProgressDialog progressDialog) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.currency_change_option_dialog_layout);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        RadioButton rbInr = dialog.findViewById(R.id.rb_inr);
        RadioButton rbUsd = dialog.findViewById(R.id.rb_usd);
        rbInr.setSelected(false);
        rbUsd.setSelected(false);
        RadioGroup radioGroup = dialog.findViewById(R.id.rgCurrencyChange);
        if (selectedcurrency.equalsIgnoreCase("INR")) {
            radioGroup.check(R.id.rb_inr);
        } else if (selectedcurrency.equalsIgnoreCase("USD")) {
            radioGroup.check(R.id.rb_usd);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_inr) {
                    selectedcurrency = "INR";
                    SharedPreference.setLocale(CartActivity.this, "INDIA");
                    dialog.dismiss();
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    getCurrencyList(selectedcurrency, progressDialog);

                } else if (i == R.id.rb_usd) {
                    selectedcurrency = "USD";
                    SharedPreference.setLocale(CartActivity.this, "OTHER");
                    dialog.dismiss();
                    progressDialog.show();
                    progressDialog.setIndeterminate(true);
                    getCurrencyList(selectedcurrency, progressDialog);
                }
            }
        });
    }
    private void getCurrencyList(final String currency, final ProgressDialog progressDialog) {
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<GetCurrencyList> call = restInterface.getCurrencyList(globalUtils.getKey(), globalUtils.getapidate());
        call.enqueue(new Callback<GetCurrencyList>() {
            @Override
            public void onResponse(Call<GetCurrencyList> call, Response<GetCurrencyList> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    Log.e("GetCurrencyList", response.body().getResult().getCurrencyDataList().size() + "");
                    prefClass.setCurrencyDataList((new Gson()).toJson(response.body().getResult().getCurrencyDataList()));
                    prefClass.setSelectedCurrency(currency);
                    getCartData();

                } else {
                    Toast.makeText(CartActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetCurrencyList> call, Throwable t) {
                Log.e("ExcOffers", "" + t);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Toast.makeText(CartActivity.this, "Some error occurred, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayBillLayout() {
        isBillShown = true;
        hiddenLayout1.setVisibility(View.VISIBLE);
        hiddenLayout2.setVisibility(View.VISIBLE);
        totalAmountLayout.setVisibility(View.VISIBLE);
        expandLayout.setVisibility(View.INVISIBLE);
        totalAmountLayout.setBackgroundResource(R.color.white);
        ivExpandCollapse.setImageDrawable(getResources().getDrawable(R.drawable.ic_collapse));
    }

    private void hideBillLayout() {
        isBillShown=false;
        hiddenLayout1.setVisibility(View.GONE);
        hiddenLayout2.setVisibility(View.GONE);
        totalAmountLayout.setVisibility(View.VISIBLE);
        expandLayout.setVisibility(View.VISIBLE);
        totalAmountLayout.setBackgroundResource(R.drawable.total_amount_bg);
        ivExpandCollapse.setImageDrawable(getResources().getDrawable(R.mipmap.ic_expand_info));
    }

    private void updateCart(String pId, String qty, String action,GetCartProduct.Result.CartDetails cartDetailsData) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", UserId);
        params.put("productid", pId);
        params.put("quantity", qty);
        params.put("actiontype", action);
        params.put("optionid",optionId);
        params.put("optionvalueid",optionValueId);

        PrefClass prefClass=new PrefClass(CartActivity.this);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode()));{
                params.put("currencyCode", prefClass.getSelectedCurrency());
                params.put("currencyValue", currencyData.getValue());
            }
        }
        if (!cartDetailsData.getOptionId().isEmpty())
            params.put("optionid", cartDetailsData.getOptionId());

        if (!cartDetailsData.getOptionValueId().isEmpty())
            params.put("optionvalueid", cartDetailsData.getOptionValueId());

        Log.e("CartReqData",": "+params);
        restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<AddCartResponseDTO> call = restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(), params);
        call.enqueue(new Callback<AddCartResponseDTO>() {
            @Override
            public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
                GlobalUtils.hidedialog();
                billLayout.setVisibility(View.VISIBLE);
                mainLayout.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    getCartData();
                    Log.e("AddWishToCart", ":" + response.body().getMessage());
                    if (!hideToast){
                        Toast.makeText(CartActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("AddWishToCart", ":" + response.message());
                    Toast.makeText(CartActivity.this, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCartResponseDTO> call, Throwable t) {
                GlobalUtils.hidedialog();
                Toast.makeText(CartActivity.this, "Please try again!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void updateQty(String pId, String qty,GetCartProduct.Result.CartDetails cartDetailsData) {
        if (GlobalUtils.isNetworkAvailable(this)) {
            GlobalUtils.showdialog(this);
            billLayout.setVisibility(View.GONE);
            updateCart(pId, qty, "update",cartDetailsData);
        } else {
            GlobalUtils.hidedialog();
            billLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Please check your network connection!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void removeProduct(String productName, final String pId, final String qty, final GetCartProduct.Result.CartDetails cartDetailsData) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        builder.setMessage("Do you want to remove "+productName+" from cart?").setTitle("Remove Product");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                updateCart(pId, qty, "delete", cartDetailsData);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    public void moveToWishList(final String cartProductId, String qty, final String action,final GetCartProduct.Result.CartDetails cartDetailsData) {
        hideToast= false;
        if (GlobalUtils.isNetworkAvailable(this)) {
            GlobalUtils.showdialog(this);
            restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
            Call<WishlistDTO> call = restInterface.addRemoveWishList(globalUtils.getKey(), globalUtils.getapidate(), UserId, cartProductId, action);
            call.enqueue(new Callback<WishlistDTO>() {
                @Override
                public void onResponse(Call<WishlistDTO> call, retrofit2.Response<WishlistDTO> response) {
                    if (response.body().getSuccess()==1) {
                        Log.e("ResponseMovedWishList", ":" + response.body().getMessage());
                        hideToast= true;
                        updateCart(cartProductId,"1","delete",cartDetailsData);
                        Toast.makeText(CartActivity.this, "Product Saved for later in wishlist!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<WishlistDTO> call, Throwable t) {
                  Log.e("MoveToWLExc",":"+t);
                }
            });
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }
}
