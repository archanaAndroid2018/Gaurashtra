package com.gaurashtra.app.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.CartInfoBean.CartInfoDTO;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.view.adapter.CouponAdapter;
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
import com.gaurashtra.app.model.bean.GetCartData.GetCartProduct;
import com.gaurashtra.app.model.bean.addresslistbean.UserAddressDatum;

public class ConfirmOrderActivity extends AppCompatActivity implements CouponAdapter.SelectedCouponInterface {
    private TextView textviewTitle, tvUsername, tvFullAddress, tvPhoneNo, btnChangeAddress, tvSubTotal,
                     tvCouponTitle,tvCouponUsed,tvShippingCharge, tvTotalAmount, tvTaxAmount, tvProductQty, tvCurrencySymbol1,
                      tvCurrencySymbol2, tvCurrencySymbol3,tvCurrencySymbol4,tvCurrencySymbol5, tvCurrencySymbol6,tvWalletAmount;
    private LinearLayout payment, couponLayout, walletAmountLayout,btnChooseCoupon;
    UserAddressDatum data;
    String cartSessionId, fname, lname, fullAddress, phoneNo, subTotal, countryName, zoneName, couponTitle,
            couponUsed,shippingCharge, totalAmount, taxAmount, productQty, extraData, cartData, CouponCode,
            shippingPostcode, shippingCountry, shippingCountryId,shippingZone, shippingZoneId, originalValue;
    LinearLayout couponOfferLayout,couponCodeLayout;
    ImageView paymentIcon;
    ArrayList<GetCartProduct.Result.CouponDatum> couponList= new ArrayList<>();
    GetCartProduct.Result.CartInfo cartInfoData;
    PrefClass prefClass;
    String currencySymbol="", selectedcurrency="INR", appliedCouponText="";
    float currencyValue= (float) 1.000;
    GlobalUtils globalUtils= new GlobalUtils();
    Dialog couponDialog=null;
    TextView tvBtnChooseCoupon, tvErrorCoupon, tvCouponCode;
    Boolean isUserTyped= false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        if(getIntent().hasExtra(Constants.PreferenceConstants.SELECTEDADDRESS))
        {
            extraData=getIntent().getStringExtra(Constants.PreferenceConstants.SELECTEDADDRESS);
            cartData= getIntent().getStringExtra(Constants.PreferenceConstants.AMOUNTTOPAY);
            cartSessionId= getIntent().getStringExtra(Constants.PreferenceConstants.CARTSESSIONID);
            Type typeAddress=new TypeToken<UserAddressDatum>(){}.getType();
            data= (UserAddressDatum) (new Gson()).fromJson(extraData, typeAddress);
            Type typeCartInfo= new TypeToken<GetCartProduct.Result.CartInfo>(){}.getType();
            cartInfoData= (GetCartProduct.Result.CartInfo) (new Gson()).fromJson(cartData,typeCartInfo);
            productQty= getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTQTY);
            fname = data.getFirstname();
            lname = data.getLastname();
            String address1 = data.getAddress1();
            String address2 = data.getAddress2();
            String city  = data.getCity();
            shippingZoneId = data.getZoneId();
            shippingCountryId = data.getCountryId();
            shippingPostcode   = data.getPostcode();
            shippingCountry = data.getCountryName();
            shippingZone = data.getZoneName();
            countryName= getIntent().getStringExtra(Constants.PreferenceConstants.COUNTRYNAME);
            zoneName= getIntent().getStringExtra(Constants.PreferenceConstants.ZONENAME);
            phoneNo= data.getCustomField();
            fullAddress = address1+", "+address2+", "+city+", "+shippingZone+", "+shippingCountry+", "+shippingPostcode;
            subTotal= String.valueOf(new DecimalFormat("0.00").format(cartInfoData.getSubTotal().getValue()));
            couponTitle = cartInfoData.getCoupon().getTitle();
            couponUsed= cartInfoData.getCoupon().getValue().toString();
            shippingCharge= cartInfoData.getShipping().getValue().toString();
            totalAmount= String.valueOf(new DecimalFormat("0.00").format(cartInfoData.getCartTotal().getValue()));
            taxAmount= String.valueOf(new DecimalFormat("0.00").format(cartInfoData.getOther().getTotalTax()));

            Log.e("DataFromIntent",""+data.getAddress1());
        }
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Confirm Order");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        UserUpdate userUpdate= new UserUpdate(ConfirmOrderActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(ConfirmOrderActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(ConfirmOrderActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }

        prefClass= new PrefClass(this);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                selectedcurrency=prefClass.getSelectedCurrency();
                originalValue = new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(originalValue);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
            }
        }

        setUpContentView();
        setCurrencyInBill(currencySymbol);
        if(GlobalUtils.isNetworkAvailable(ConfirmOrderActivity.this)) {
            getCartInfo();
            getCartData();
        }else{
            Toast.makeText(userUpdate, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void setSelectedCurrency() {
        prefClass= new PrefClass(this);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                selectedcurrency=prefClass.getSelectedCurrency();
                originalValue= currencyData.getValue();
                String value= new DecimalFormat("0.000").format(Float.parseFloat(originalValue));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
                setCurrencyInBill(currencySymbol);
            }
        }
    }
    private void getCartData() {
        if (GlobalUtils.isNetworkAvailable(this)) {
            setSelectedCurrency();
            GlobalUtils.showdialog(this);
            Map<String, String> param = new HashMap<>();
            param.put("userid", SharedPreference.getUserid(ConfirmOrderActivity.this));
            PrefClass prefClass=new PrefClass(ConfirmOrderActivity.this);
            Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
            List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
            for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
                if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode()));{
                    param.put("currencyCode", prefClass.getSelectedCurrency());
                    param.put("currencyValue", currencyData.getValue());
                }
            }
            RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
            Call<GetCartProduct> call = restInterface.getCartData(globalUtils.getKey(), globalUtils.getapidate(), param);
            call.enqueue(new Callback<GetCartProduct>() {
                @Override
                public void onResponse(Call<GetCartProduct> call, Response<GetCartProduct> response) {
                    try {
                        GlobalUtils.hidedialog();
                        if (response.body().getSuccess() ==1) {
                            Log.e("ResGetCartInConfirm", "" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                            couponList = new ArrayList<>();
                            couponList = (ArrayList<GetCartProduct.Result.CouponDatum>) response.body().getResult().getCouponData();
                            GetCartProduct.Result.AppliedCouponData appliedCouponData = response.body().getResult().getAppliedCouponData();

                            if (couponList.size() > 0) {

                            } else {
                            }
//                            if (cartInfo.getCoupon().getValue() == 0) {
//                                couponAmountLayout.setVisibility(View.GONE);
//                            } else {
//                                couponAmountLayout.setVisibility(View.VISIBLE);
//                                if (cartInfo.getCoupon().getTitle().isEmpty()) {
//                                    tvCouponLabel.setText("Coupon (-)");
//                                } else {
//                                    tvCouponLabel.setText(cartInfo.getCoupon().getTitle() + "(-)");
//                                }
//                                tvCouponAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(couponAmount) * currencyValue));
//                            }

                            tvCouponCode.setEnabled(true);
                            if (!appliedCouponData.getCouponCode().isEmpty()) {
                                appliedCouponText=appliedCouponData.getCouponCode();
                                tvCouponCode.setText(appliedCouponText);
//                                etCouponCode.setKeyListener(null);
//                                etCouponCode.setEnabled(false);

                                for (int i = 0; i < couponList.size(); i++) {
                                    if (couponList.get(i).getCouponCode().equalsIgnoreCase(appliedCouponData.getCouponCode())) {
                                        if (!appliedCouponData.getCouponMessage().equalsIgnoreCase(couponList.get(i).getCouponContent())) {
                                            tvErrorCoupon.setVisibility(View.VISIBLE);
                                            tvErrorCoupon.setText(appliedCouponData.getCouponMessage());
                                        } else {
                                            tvErrorCoupon.setVisibility(View.GONE);
                                        }
                                        break;
                                    }else
                                        if(response.body().getResult().getAppliedCouponData().getCouponMessage().equalsIgnoreCase("Yes")){
                                        tvErrorCoupon.setVisibility(View.VISIBLE);
                                        tvErrorCoupon.setText(response.body().getResult().getAppliedCouponData().getCouponMessage());
                                    }
                                }
                            }
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
    private void getCartInfo() {
        HashMap<String,String> map= new HashMap<>();
        map.put("userid", SharedPreference.getUserid(ConfirmOrderActivity.this));
        map.put("cartSessionId",cartSessionId);
        map.put("shippingPostcode",shippingPostcode);
        map.put("shippingCountryId",shippingCountryId);
        map.put("shippingZoneId",shippingZoneId);
        map.put("currencyCode",selectedcurrency);
        map.put("currencyValue",originalValue);
        Log.e("RequestMap",":"+map);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<CartInfoDTO> call = restInterface.getPaymentMethodsInfo(globalUtils.getKey(), globalUtils.getapidate(), map);
        call.enqueue(new Callback<CartInfoDTO>() {
            @Override
            public void onResponse(Call<CartInfoDTO> call, Response<CartInfoDTO> response) {
                Log.e("PaymentMethodResponse",":"+response.body().getMessage()+"\n request data:"+response.raw());
                if(response.body().getSuccess()==1){
                    if(response.body().getResult().getCartInfo() !=  null) {
                        CartInfoDTO.Result.CartInfo cartInfo = response.body().getResult().getCartInfo();
//                        paymentMethodList = response.body().getResult().getPaymentGetway();
                        Log.e("CartInfo", ":" + cartInfo.getSubTotal().getTitle());
                        tvCurrencySymbol1.setText(currencySymbol);
                        tvSubTotal.setText(new DecimalFormat("0.00").format(cartInfo.getSubTotal().getValue()));
                        tvCurrencySymbol2.setText(currencySymbol);
                        tvShippingCharge.setText(new DecimalFormat("0.00").format(cartInfo.getShipping().getValue()));
                        if(cartInfo.getCoupon().getValue()>0){
                            couponLayout.setVisibility(View.VISIBLE);
                            tvCouponTitle.setText(couponTitle + "(-)");
                        }
                        if (!String.valueOf(cartInfo.getCoupon().getValue()).equalsIgnoreCase("0")) {
                            tvCouponUsed.setText(new DecimalFormat("0.00").format(cartInfo.getCoupon().getValue()*currencyValue));
                        }
                        tvShippingCharge.setText(new DecimalFormat("0.00").format(cartInfo.getShipping().getValue()*currencyValue));

                        Double totalTax=0.00, totalAmount=0.00;
                        totalAmount = cartInfo.getCartTotal().getValue()*currencyValue;
                        totalTax = cartInfo.getOther().getTotalTax()*currencyValue;
                        tvTaxAmount.setText(new DecimalFormat("0.00").format(totalTax));
                        tvTotalAmount.setText(new DecimalFormat("0.00").format(totalAmount));
                        if(cartInfo.getStoreCredit().getValue()>00.0){
                            walletAmountLayout.setVisibility(View.VISIBLE);
                            tvCurrencySymbol6.setText(currencySymbol);
                            tvWalletAmount.setText(String.valueOf(cartInfo.getStoreCredit().getValue() * currencyValue));
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CartInfoDTO> call, Throwable t) {
                Log.e("PaymentMethodExc",":"+t);
            }
        });
    }
    private void setCurrencyInBill(String currencySymbol) {
        tvCurrencySymbol1.setText(currencySymbol);
        tvCurrencySymbol2.setText(currencySymbol);
        tvCurrencySymbol3.setText(currencySymbol);
        tvCurrencySymbol4.setText(currencySymbol);
        tvCurrencySymbol5.setText(currencySymbol);
    }

    public void setUpContentView(){
        couponOfferLayout = findViewById(R.id.coupon_available_layout);
        couponCodeLayout = findViewById(R.id.coupon_code_layout);
        btnChooseCoupon = findViewById(R.id.choose_coupon_layout);
        tvCouponCode = findViewById(R.id.tv_coupon_code);
//        etCouponCode.addTextChangedListener(textWatcher);
        tvBtnChooseCoupon= findViewById(R.id.tv_btn_choose_coupon);
        tvErrorCoupon = findViewById(R.id.tv_error_coupon);

        tvUsername= findViewById(R.id.tv_username);
        tvFullAddress= findViewById(R.id.tv_fullAddress);
        tvPhoneNo= findViewById(R.id.tv_phoneNo);
        btnChangeAddress= findViewById(R.id.tv_btn_changeAddress);
        payment = findViewById(R.id.llPayment);
        paymentIcon= findViewById(R.id.payment_icon);
        tvSubTotal= findViewById(R.id.tv_sub_total_amount);
        couponLayout = findViewById(R.id.coupon_layout);
        tvCouponTitle = findViewById(R.id.tv_coupon_title);
        tvCouponUsed= findViewById(R.id.tv_coupon_used_amount);
        tvShippingCharge= findViewById(R.id.tv_shipping_amount);
        tvTotalAmount= findViewById(R.id.tv_total_amount);
        tvTaxAmount= findViewById(R.id.tv_tax_amount);
        tvProductQty= findViewById(R.id.tv_product_quantity);
        tvCurrencySymbol6= findViewById(R.id.tv_currencySymbol8);
        walletAmountLayout = findViewById(R.id.wallet_layout);
        tvWalletAmount = findViewById(R.id.tv_wallet_amount);
        tvUsername.setText(fname+" "+lname);
        tvFullAddress.setText(fullAddress);
        tvPhoneNo.setText(phoneNo);

        tvSubTotal.setText(new DecimalFormat("0.00").format(Double.parseDouble(subTotal)*currencyValue));
        tvProductQty.setText(" ("+productQty+" items)");

        tvCouponTitle.setText(couponTitle+"(-)");
        tvCouponUsed.setText(new DecimalFormat("0.00").format(Float.parseFloat(couponUsed)*currencyValue));
        tvShippingCharge.setText(new DecimalFormat("0.00").format(Float.parseFloat(shippingCharge)*currencyValue));
        tvTotalAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(totalAmount)*currencyValue));
        tvTaxAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(taxAmount)*currencyValue));

        tvCurrencySymbol1= findViewById(R.id.tv_currencySymbol1);
        tvCurrencySymbol2= findViewById(R.id.tv_currencySymbol2);
        tvCurrencySymbol3= findViewById(R.id.tv_currencySymbol3);
        tvCurrencySymbol4= findViewById(R.id.tv_currencySymbol4);
        tvCurrencySymbol5= findViewById(R.id.tv_currencySymbol5);
        tvBtnChooseCoupon.setOnClickListener(couponButtonClickListener);
        btnChooseCoupon.setOnClickListener(couponButtonClickListener);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfirmOrderActivity.this, PaymentMethodActivity.class);
                String amount=new DecimalFormat("0.00").format(Float.parseFloat(totalAmount)*currencyValue);
                intent.putExtra(Constants.PreferenceConstants.AMOUNTTOPAY, amount );
                intent.putExtra(Constants.PreferenceConstants.SELECTEDADDRESS, extraData);
                intent.putExtra(Constants.PreferenceConstants.CARTSESSIONID, cartSessionId);
                startActivity(intent);
            }
        });
        btnChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    View.OnClickListener couponButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openCouponPopUp();
        }
    };
    private void openCouponPopUp() {
        couponDialog = new Dialog(ConfirmOrderActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                // Tap anywhere to close dialog.
                this.dismiss();
                return true;
            }
        };
        couponDialog.setContentView(R.layout.popup_coupon_layout);
        couponDialog.getWindow().setBackgroundDrawableResource(R.color.white_40);
        RecyclerView rvCoupons = couponDialog.findViewById(R.id.rv_coupons);
        EditText etUserTypedCoupon = couponDialog.findViewById(R.id.et_user_coupon);
        TextView btnApply = couponDialog.findViewById(R.id.tv_btn_apply);
        rvCoupons.hasFixedSize();
        LinearLayoutManager manager = new LinearLayoutManager(ConfirmOrderActivity.this, RecyclerView.VERTICAL, false);
        rvCoupons.setLayoutManager(manager);
        if(couponList.size()>0) {
            CouponAdapter couponAdapter = new CouponAdapter(ConfirmOrderActivity.this, couponList, ConfirmOrderActivity.this);
            rvCoupons.setAdapter(couponAdapter);
        }
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CouponCode= etUserTypedCoupon.getText().toString().trim();
                    if (CouponCode.length() == 0) {
                        Toast.makeText(couponDialog.getContext(), "Please enter a coupon code to proceed!", Toast.LENGTH_SHORT).show();
                    } else {
                        GlobalUtils.showdialog(ConfirmOrderActivity.this);
                        tvCouponCode.setText(CouponCode);
                        isUserTyped=true;
                        submitCouponCode(couponDialog);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        couponDialog.setCancelable(true);
        couponDialog.setCanceledOnTouchOutside(true);
        couponDialog.show();
    }

    private void submitCouponCode(final Dialog dialog) {
        CouponCode = tvCouponCode.getText().toString().trim();
        HashMap<String, String> param= new HashMap<>();
        param.put("userid",SharedPreference.getUserid(this));
        param.put("couponCode", CouponCode);
        param.put("currencyCode",selectedcurrency);
        param.put("currencyValue",originalValue);
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<GetCartProduct>call= restInterface.applyCoupon(globalUtils.getKey(),globalUtils.getapidate(),param);
        call.enqueue(new Callback<GetCartProduct>() {
            @Override
            public void onResponse(Call<GetCartProduct> call, Response<GetCartProduct> response) {
                Log.e("SubmitCouponRes",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                GlobalUtils.hidedialog();
                if(response.isSuccessful()){
                    try {
                        if (!response.body().getResult().getAppliedCouponData().getCouponMessage().isEmpty()) {
                            if(dialog != null) {
                                dialog.dismiss();
                            }
                            tvErrorCoupon.setVisibility(View.VISIBLE);
                            tvErrorCoupon.setText(response.body().getResult().getAppliedCouponData().getCouponMessage());
                        } else {
                            if(dialog != null) {
                                dialog.dismiss();
                            }
                            getCartInfo();
                            tvErrorCoupon.setVisibility(View.GONE);
                            tvCouponCode.setText(CouponCode);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    if(dialog != null) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetCartProduct> call, Throwable t) {
                if(dialog != null) {
                    dialog.dismiss();
                }
                GlobalUtils.hidedialog();
                Log.e("ErrorInCoupon",""+t);

            }
        });

    }

    @Override
    public void selectedCoupons(int position) {
        if(couponDialog != null) {
            GlobalUtils.showdialog(ConfirmOrderActivity.this);
            CouponCode = couponList.get(position).getCouponCode();
            tvCouponCode.setText(CouponCode);
            isUserTyped= false;
            submitCouponCode(couponDialog);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        paymentIcon.setBackgroundResource(R.drawable.paymnet);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
