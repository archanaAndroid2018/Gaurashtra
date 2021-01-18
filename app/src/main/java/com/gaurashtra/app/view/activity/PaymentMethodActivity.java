package com.gaurashtra.app.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
//import com.razorpay.R;

import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.bean.CartInfoBean.CartInfoDTO;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.PaytmChecksumBean;
import com.gaurashtra.app.model.bean.RazorpayOrderResponse;
import com.gaurashtra.app.view.activity.PayUMoney.AppEnvironment;
import com.gaurashtra.app.view.adapter.PaymentMethodAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.PaymentData;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.OrderBean.OrderDetailsBean;
import com.gaurashtra.app.model.bean.addresslistbean.UserAddressDatum;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentMethodActivity extends AppCompatActivity implements  PaymentMethodAdapter.PaymentSelectorInterface , PaymentResultWithDataListener {
    //    PaymentResultListener,
    private static final String TAG = PaymentMethodActivity.class.getSimpleName();
    private TextView textviewTitle;
    Button btnPayNow;
    String userName, userPhone, userEmail, totalAmount="1.00", orderId = "", txnId="";
    PrefClass prefClass;
    GlobalUtils globalUtils;
    UserAddressDatum data;
    String currencySymbol="", selectedcurrency="INR";
    float currencyValue=0.00f;
    RecyclerView recyclerView;
    FrameLayout frameNoDelivery;
    LinearLayout amountLayout, clickToShowBill, breakUpLayout;
    Checkout co;
    Boolean isNoPaymentMethod= false, isBillShow= false, isPaymentComplete=false;
    String RAZORPAY="razorpay", PAYTM="paytm", COD="Cash on delivery",COD1="cod", PAYPAL="paypal", PAYUMONEY="payumoney";
    ArrayList<CartInfoDTO.Result.PaymentGateway> paymentMethodList = new ArrayList<>();
    ProgressDialog progressDialog;
    String userid, cartSessionId, paymentType,originalValue,rzpSignature,
            paymentFirstName, paymentLastName, paymentCompany, paymentAddress1, paymentAddress2, paymentCity, paymentPostcode, paymentCountry, paymentCountryId, paymentZone, paymentZoneId,
            shippingFirstName, shippingLastName,shippingPhone, shippingCompany, shippingAddress1, shippingAddress2, shippingCity, shippingPostcode, shippingCountry,
            shippingCountryId, shippingZone, shippingZoneId, intentExtraData, PaytmChecksum, PayUmoneyChecksum, paymentGatewayName="",razorPayOrderId="" ;

    TextView tvSubtotalTitle, tvCurrencySymbol1,tvSubtotalPrice,tvShippingLabel,tvCurrencySymbol2,tvShippingAmount,
            tvCouponLabel,tvCurrencySymbol3,tvCouponAmount,tvTax1Title,tvCurrencySymbol4,tvTaxAmount1,tvWalletLabel,
            tvCurrencySymbol8,tvWalletAmount,tvOtherTitle,tvCurrencySymbol9,tvOtherAmount,tvTax2Title, tvCurrencySymbol5,
            tvTaxAmount2, tvTax3Title,tvCurrencySymbol6,tvTaxAmount3, tvTax4Title, tvCurrencySymbol7,tvProductWeight,
            tvTaxAmount4,tvTotalAmountTitle,tvCurrencySymbol,tvTotalAmountPrice,tvUsername, tvFullAddress, tvPhoneNo;

    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;

    // note that these credentials will differ between live & sandbox
    // environments.
    private static final String CONFIG_CLIENT_ID = "AWalMqPbkQ1HV7P34a0W5QddlD8bnN2xJ9emQ3r4mtwSHt6WUvbH-Fon7XDZmS3fIsRqPIGv0kPEybsL";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            // The following are only used in PayPalFuturePaymentActivity.
            .merchantName("Gaurashtra")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));

    PayPalPayment thingToBuy;
    private ArrayList<NameValuePair> paramsListForPayumoneyHash;
    private String localServerHash;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    Dialog errorDialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Checkout.preload(getApplicationContext());
        prefClass = new PrefClass(this);
        globalUtils = new GlobalUtils();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        getIntentData();
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Payment");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        UserUpdate userUpdate= new UserUpdate(PaymentMethodActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(PaymentMethodActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(PaymentMethodActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        tvProductWeight = findViewById(R.id.tv_weight_info);
        btnPayNow= findViewById(R.id.btn_pay);
        breakUpLayout= findViewById(R.id.breakups_layout);
        tvUsername= findViewById(R.id.tv_username);
        tvFullAddress= findViewById(R.id.tv_fullAddress);
        tvPhoneNo= findViewById(R.id.tv_phoneNo);
        tvSubtotalTitle= findViewById(R.id.tv_subtotal_title);
        tvCurrencySymbol1= findViewById(R.id.tv_currencySymbol1);
        tvSubtotalPrice= findViewById(R.id.tv_subtotal_price);
        tvShippingLabel= findViewById(R.id.tv_shipping_label);
        tvCurrencySymbol2= findViewById(R.id.tv_currencySymbol2);
        tvShippingAmount= findViewById(R.id.tv_shipping_amount);
        tvCouponLabel= findViewById(R.id.tv_coupon_label);
        tvCurrencySymbol3= findViewById(R.id.tv_currencySymbol3);
        tvCouponAmount= findViewById(R.id.tv_coupon_amount);
        tvTax1Title= findViewById(R.id.tv_tax1_title);
        tvCurrencySymbol4= findViewById(R.id.tv_currencySymbol4);
        tvTaxAmount1= findViewById(R.id.tv_tax_amount1);
        tvWalletLabel= findViewById(R.id.tv_wallet_label);
        tvCurrencySymbol8= findViewById(R.id.tv_currencySymbol8);
        tvWalletAmount= findViewById(R.id.tv_wallet_amount);
        tvOtherTitle= findViewById(R.id.tv_other_title);
        tvCurrencySymbol9= findViewById(R.id.tv_currencySymbol9);
        tvOtherAmount= findViewById(R.id.tv_other_amount);
        tvTax2Title= findViewById(R.id.tv_tax2_title);
        tvCurrencySymbol5= findViewById(R.id.tv_currencySymbol5);
        tvTaxAmount2=findViewById(R.id.tv_tax_amount2);
        tvTax3Title=findViewById(R.id.tv_tax3_title);
        tvCurrencySymbol6=findViewById(R.id.tv_currencySymbol6);
        tvTaxAmount3=findViewById(R.id.tv_tax_amount3);
        tvTax4Title=findViewById(R.id.tv_tax4_title);
        tvCurrencySymbol7=findViewById(R.id.tv_currencySymbol7);
        tvTaxAmount4=findViewById(R.id.tv_tax_amount4);
        tvTotalAmountTitle= findViewById(R.id.tv_total_amount_tile);
        tvCurrencySymbol=findViewById(R.id.tv_currencySymbol);
        tvTotalAmountPrice= findViewById(R.id.tv_total_amount_price);

        amountLayout= findViewById(R.id.cart_info_layout);
        clickToShowBill= findViewById(R.id.total_layout);
        recyclerView= findViewById(R.id.rv_pay_method);
        frameNoDelivery= findViewById(R.id.frame_no_delivery);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
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
            }
        }
        String fullAddress= shippingAddress1+", "+shippingAddress2+", "+shippingCity+", "+shippingZone+", "+ shippingPostcode+", "+shippingCountry+".";
        tvUsername.setText(shippingFirstName+" "+shippingLastName);
        tvFullAddress.setText(fullAddress);
        tvPhoneNo.setText(shippingPhone);
        getPaymentMethods();
        co.preload(getApplicationContext());
        clickToShowBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBillLayout();
            }
        });
        btnPayNow.setOnClickListener(proceedToPay);
    }

    private void showBillLayout() {
        if(isBillShow){
            isBillShow=false;
            amountLayout.setVisibility(View.VISIBLE);
        }else{
            isBillShow=true;
            amountLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        if(isPaymentComplete){
            progressDialog = new ProgressDialog(PaymentMethodActivity.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Please wait...");
            progressDialog.setProgressStyle(R.style.CheckoutTheme);
            progressDialog.show();
        }else{
            if(errorDialog != null) {
                errorDialog.dismiss();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!isNoPaymentMethod) {
            openConfirmDialog();
        }else if(errorDialog==null){
            finish();
//        }else{
//            errorDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }
    //    @@@@@@@@@@@@@ Common Methods for all payment gateway integration  @@@@@@@@@@@@@@@@@


    private void getIntentData() {
        userid = SharedPreference.getUserid(this);
        if(!SharedPreference.getFirstName(this).isEmpty()) {
            paymentFirstName = SharedPreference.getFirstName(this);
        }

        paymentLastName = SharedPreference.getLastName(this);
        userName = paymentFirstName + " " + paymentLastName;
        userPhone = SharedPreference.getUserPhone(this);
        userEmail = prefClass.getUserMail();
        String extraData= getIntent().getStringExtra(Constants.PreferenceConstants.SELECTEDADDRESS);
        Type typeAddress=new TypeToken<UserAddressDatum>(){}.getType();
        data= (UserAddressDatum) (new Gson()).fromJson(extraData, typeAddress);
        try {
            if (getIntent().hasExtra(Constants.PreferenceConstants.AMOUNTTOPAY)) {
//                totalAmount="1.00";
           totalAmount = getIntent().getStringExtra(Constants.PreferenceConstants.AMOUNTTOPAY);
                Log.e("AmountToPay", "" + totalAmount);
            } else {
                Toast.makeText(this, "intentExtraData is missing!", Toast.LENGTH_SHORT).show();
            }
            if (getIntent().hasExtra(Constants.PreferenceConstants.CARTSESSIONID)) {
                cartSessionId = getIntent().getStringExtra(Constants.PreferenceConstants.CARTSESSIONID);
            } else {
                Toast.makeText(this, "cartSessionId is missing!", Toast.LENGTH_SHORT).show();
            }

            shippingCountry = data.getCountryName();
            shippingZone = data.getZoneName();
            shippingFirstName= data.getFirstname();
            shippingLastName= data.getLastname();
            paymentCompany = data.getCompany();

            if (!data.getAddress1().isEmpty()) {
                shippingAddress1 = data.getAddress1();
            } else {
                Toast.makeText(this, "Shipping Address1 is missing!", Toast.LENGTH_SHORT).show();
            }
            if (!data.getAddress2().isEmpty()) {
                shippingAddress2 = data.getAddress2();
            } else {
                shippingAddress2="";
            }
            if (!data.getCity().isEmpty()) {
                shippingCity = data.getCity();
            } else {
                Toast.makeText(this, "Shipping City is missing!", Toast.LENGTH_SHORT).show();
            }
            if (!data.getPostcode().isEmpty()) {
                shippingPostcode = data.getPostcode();
            } else {
                Toast.makeText(this, "Shipping Postcode is missing!", Toast.LENGTH_SHORT).show();
            }
            if (!data.getCountryId().isEmpty()) {
                shippingCountryId = data.getCountryId();
            } else {
                Toast.makeText(this, "Shipping CountryId is missing!", Toast.LENGTH_SHORT).show();
            }
            if (!data.getZoneId().isEmpty()) {
                shippingZoneId = data.getZoneId();
            } else {
                Toast.makeText(this, "Shipping ZoneId is missing!", Toast.LENGTH_SHORT).show();
            }
            shippingCompany = data.getCompany();
            if (!data.getCustomField().isEmpty()) {
                shippingPhone = data.getCustomField();
            } else {
                Toast.makeText(this, "Shipping Phone no. is missing!", Toast.LENGTH_SHORT).show();
            }
            paymentAddress1 = SharedPreference.getUserAddress1(this);
            paymentAddress2 = SharedPreference.getUserAddress2(this);
            paymentCity = SharedPreference.getUserCity(this);
            paymentPostcode = SharedPreference.getUserZipcode(this);
            paymentCountry = SharedPreference.getUserCountry(this);
            paymentCountryId = SharedPreference.getUserCountryId(this);
            paymentZone = SharedPreference.getUserState(this);
            paymentZoneId = SharedPreference.getUserStateId(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getPaymentMethods() {
        HashMap<String,String> map= new HashMap<>();
        map.put("userid",SharedPreference.getUserid(PaymentMethodActivity.this));
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
                Log.e("PaymentMethodResponse",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if(response.body().getSuccess()==1){
                    breakUpLayout.setVisibility(View.VISIBLE);
                    frameNoDelivery.setVisibility(View.GONE);
                    paymentMethodList = new ArrayList<>();
                    if(response.body().getResult().getPaymentGetway().size()>0) {
                        btnPayNow.setVisibility(View.VISIBLE);
                        CartInfoDTO.Result.CartInfo cartInfo= response.body().getResult().getCartInfo();
                        paymentMethodList = response.body().getResult().getPaymentGetway();
                        Log.e("CartInfo",":"+cartInfo.getSubTotal().getTitle());
                        tvSubtotalTitle.setText(cartInfo.getSubTotal().getTitle());
                        tvCurrencySymbol1.setText(currencySymbol);
                        tvSubtotalPrice.setText(new DecimalFormat("0.00").format(cartInfo.getSubTotal().getValue()));
                        tvShippingLabel.setText("Shipping charge");
                        String w = cartInfo.getShipping().getTitle();
                        String[] prodWeight= w.split(":");

                        if(prodWeight != null) {
                            tvProductWeight.setVisibility(View.VISIBLE);
                            tvProductWeight.setText("(Weight: "+prodWeight[1]);
                        }else{
                            tvProductWeight.setVisibility(View.GONE);
                        }
                        tvCurrencySymbol2.setText(currencySymbol);
                        tvShippingAmount.setText(new DecimalFormat("0.00").format(cartInfo.getShipping().getValue()));
                        if(cartInfo.getCoupon().getValue()>0) {
                            findViewById(R.id.coupon_amount_layout).setVisibility(View.VISIBLE);
                            tvCouponLabel.setText(cartInfo.getCoupon().getTitle()+"(-)");
                            tvCurrencySymbol3.setText(currencySymbol);
                            tvCouponAmount.setText(new DecimalFormat("0.00").format(cartInfo.getCoupon().getValue() * currencyValue));
                        }

                        if(cartInfo.getTax().size()>0) {
                            tvTax1Title.setText(cartInfo.getTax().get(0).getTitle());
                            tvCurrencySymbol4.setText(currencySymbol);
//                            Double tax1Percent= cartInfo.getTax().get(0).getValue();
                            tvTaxAmount1.setText(new DecimalFormat("0.00").format(cartInfo.getTax().get(0).getValue()));

                            if(cartInfo.getTax().size()>1) {
                                findViewById(R.id.tax2_layout).setVisibility(View.VISIBLE);
                                tvTax2Title.setText(cartInfo.getTax().get(1).getTitle());
                                tvCurrencySymbol5.setText(currencySymbol);
                                tvTaxAmount2.setText(new DecimalFormat("0.00").format(cartInfo.getTax().get(1).getValue()));
                            }
                            if(cartInfo.getTax().size()>2) {
                                findViewById(R.id.tax3_layout).setVisibility(View.VISIBLE);
                                tvTax3Title.setText(cartInfo.getTax().get(2).getTitle());
                                tvCurrencySymbol6.setText(currencySymbol);
                                tvTaxAmount3.setText(new DecimalFormat("0.00").format(cartInfo.getTax().get(2).getValue()));
                            }
                            if(cartInfo.getTax().size()>3) {
                                findViewById(R.id.tax4_layout).setVisibility(View.VISIBLE);
                                tvTax4Title.setText(cartInfo.getTax().get(3).getTitle());
                                tvCurrencySymbol7.setText(currencySymbol);
                                tvTaxAmount4.setText(new DecimalFormat("0.00").format(cartInfo.getTax().get(3).getValue()));
                            }
                        }
                        tvTotalAmountTitle.setText(cartInfo.getCartTotal().getTitle());
                        tvCurrencySymbol.setText(currencySymbol);
                        tvTotalAmountPrice.setText(new DecimalFormat("0.00").format(cartInfo.getCartTotal().getValue()));
                        if(cartInfo.getStoreCredit().getValue()>0) {
                            findViewById(R.id.wallet_layout).setVisibility(View.VISIBLE);
                            tvWalletLabel.setText("Wallet(-)");
                            tvCurrencySymbol8.setText(currencySymbol);
                            tvWalletAmount.setText(String.valueOf(cartInfo.getStoreCredit().getValue()));
                        }
                        Boolean isCOD=false;
                        if(response.body().getResult().getPaymentInfo().getCash().equalsIgnoreCase("Yes")) {
                            isCOD = true;
                        }
                        PaymentMethodAdapter adapter = new PaymentMethodAdapter(PaymentMethodActivity.this, paymentMethodList, PaymentMethodActivity.this,isCOD);
                        recyclerView.setAdapter(adapter);
                    }else{
                        btnPayNow.setVisibility(View.GONE);
                        isNoPaymentMethod=true;
                        frameNoDelivery.setVisibility(View.VISIBLE);
                        breakUpLayout.setVisibility(View.GONE);
                    }
                }else{
                    btnPayNow.setVisibility(View.GONE);
                    isNoPaymentMethod=true;
                    frameNoDelivery.setVisibility(View.VISIBLE);
                    breakUpLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CartInfoDTO> call, Throwable t) {
                Log.e("PaymentMethodExc",":"+t);
            }
        });
    }

    private void setConfirmOrder(final String paymentType) {
        progressDialog = new ProgressDialog(PaymentMethodActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Initiating payment...");
        progressDialog.setProgressStyle(R.style.CheckoutTheme);
        progressDialog.show();
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", userid);
        param.put("devicetype", "android");
        param.put("cartSessionId", cartSessionId);
        param.put("paymentType", paymentType);
        param.put("paymentFirstName", paymentFirstName);
        param.put("paymentLastName", paymentLastName);
        param.put("paymentTelephone", userPhone);
        param.put("paymentCompany", paymentCompany);
        param.put("paymentAddress1", paymentAddress1);
        param.put("paymentAddress2", paymentAddress2);
        param.put("paymentCity", paymentCity);
        param.put("paymentPostcode", paymentPostcode);
        param.put("paymentCountry", paymentCountry);
        param.put("paymentCountryId", paymentCountryId);
        param.put("paymentZone", paymentZone);
        param.put("paymentZoneId", paymentZoneId);
        param.put("shippingFirstName", shippingFirstName);
        param.put("shippingLastName", shippingLastName);
        param.put("shippingTelephone", shippingPhone);
        param.put("shippingCompany", shippingCompany);
        param.put("shippingAddress1", shippingAddress1);
        param.put("shippingAddress2", shippingAddress2);
        param.put("shippingCity", shippingCity);
        param.put("shippingPostcode", shippingPostcode);
        param.put("shippingCountry", shippingCountry);
        param.put("shippingCountryId", shippingCountryId);
        param.put("shippingZone", shippingZone);
        param.put("shippingZoneId", shippingZoneId);
        param.put("currencyCode", selectedcurrency);
        param.put("currencyValue", String.valueOf(currencyValue));

        Log.e("DataToSend",":"+param);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<OrderDetailsBean> call = restInterface.setConfirmOrder(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<OrderDetailsBean>() {
            @Override
            public void onResponse(Call<OrderDetailsBean> call, Response<OrderDetailsBean> response) {
                Log.e("ResponseOrder", ":" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                try {
                    if (response.body().success==1) {
                        if (response.body().getResult().getOrderId()!=0) {
                            orderId = String.valueOf(response.body().getResult().getOrderId());
//                            Double cartAmount= 1.00;
                            Double cartAmount = response.body().getResult().getCartInfo().cartTotal.value;
                            totalAmount = new DecimalFormat("0.00").format(cartAmount*currencyValue);
                            Log.e("TotalPayableAmount", "" + totalAmount);
                            if(paymentType.equalsIgnoreCase(RAZORPAY)) {
//                                getRazorpayOrderId();
                                new GetRazoprPayOrderId().execute();
                            }else if(paymentType.equalsIgnoreCase(PAYPAL)){
                                payPalMethod();
                            }else if(paymentType.equalsIgnoreCase(PAYUMONEY)){
                                launchPayUMoneyFlow();
                            }else if(paymentType.equalsIgnoreCase(PAYTM)){
                                getPaytmCheckSum();
                            }else if(paymentType.equalsIgnoreCase(COD) || (paymentType.equalsIgnoreCase(COD1))){
                                isPaymentComplete=true;
                                txnId="";
                                sendFinalPaymentDetail("cod");
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(PaymentMethodActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(PaymentMethodActivity.this, "something went wrong,\n couldn't redirect to payment gateway!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e("setConfError",""+e);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<OrderDetailsBean> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PaymentMethodActivity.this, "Something went wrong,\n couldn't redirect to payment gateway!", Toast.LENGTH_SHORT).show();
                Log.e("ResponseFailure", ":" + t);
            }
        });
    }


    @Override
    public void paymentMethodType(String type) {
        paymentGatewayName=type;
    }


    View.OnClickListener proceedToPay= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(paymentGatewayName.isEmpty()){
                Toast.makeText(PaymentMethodActivity.this, "Please select a payment method!", Toast.LENGTH_SHORT).show();
            }else {
                if (GlobalUtils.isNetworkAvailable(PaymentMethodActivity.this)) {
                    if (paymentGatewayName.equalsIgnoreCase(RAZORPAY)) {
                            paymentType = RAZORPAY;
                            setConfirmOrder(paymentType);
                    } else if (paymentGatewayName.equalsIgnoreCase(PAYPAL)) {
                            paymentType = PAYPAL;
                            setConfirmOrder(paymentType);
                    } else if (paymentGatewayName.equalsIgnoreCase(PAYTM)) {
                            paymentType = PAYTM;
                            setConfirmOrder(paymentType);
                    } else if (paymentGatewayName.equalsIgnoreCase(PAYUMONEY)) {
                        paymentType = PAYUMONEY;
                        setConfirmOrder(paymentType);
                    } else if (paymentGatewayName.equalsIgnoreCase(COD) || paymentGatewayName.equalsIgnoreCase(COD1)) {
                        try {
                            paymentType = COD1;
                            setConfirmOrder(paymentType);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(PaymentMethodActivity.this, "Please check your internet connectivity!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        progressDialog.dismiss();
        Log.e("onResult","requestcode: "+requestCode+"\n resultcode: "+resultCode+"\ndata: "+data);

        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null && confirm.getProofOfPayment() != null) {
                    try {
                        isPaymentComplete=true;
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject()
                                .toString(4));
                        Log.e("PayPalResponse",":"+confirm.getProofOfPayment());
                        txnId= confirm.getProofOfPayment().getPaymentId();
                        sendFinalPaymentDetail("paypal");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                System.out.println("The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                System.out
                        .println("An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(getApplicationContext(),
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }else if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {

            try {
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
                if(progressDialog!= null){
                    progressDialog.dismiss();
                }
                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    isPaymentComplete=true;
                    String payuResponse = transactionResponse.getPayuResponse();
                    Log.e("suresponse", payuResponse.toString());
                    JSONObject jsonObject = new JSONObject(payuResponse);
                    JSONObject jsonResult = jsonObject.getJSONObject("result");
                    String errorMessage = jsonResult.getString("error_Message");
                    txnId= jsonResult.optString("payuMoneyId");
                    try {
//                            JSONObject jsonObject2 = new JSONObject(payuResponse);
//                            JSONObject jsonResult2 = jsonObject2.getJSONObject("payuResponse");
//                            txnId = jsonResult2.optString("txnid");
                        Log.e("PayUmoneyResonse",":"+transactionResponse.getTransactionDetails());
                        Log.e("PayUmoneyTxnId",":"+txnId);
                        if (txnId != null) {

                            sendFinalPaymentDetail("payumoney");
                        }else {
                            Toast.makeText(PaymentMethodActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    SharedPreference.setPaymentStatusMessageFromServer(PaymentMethodActivity.this, errorMessage);
//                        if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {
//
//                            Log.e("PayUmoneyResonse",":"+transactionResponse.getTransactionDetails());
//                            sendFinalPaymentDetail("payumoney");
//                        } else {
//                            if(progressDialog!= null){
//                                progressDialog.dismiss();
//                            }
//                            Toast.makeText(PaymentMethodActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
//                            finish();
//                        }

                } else if (resultModel != null && resultModel.getError() != null) {
                    if(progressDialog!= null){
                        progressDialog.dismiss();
                    }
                    Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                } else {
                    if(progressDialog!= null){
                        progressDialog.dismiss();
                    }
                    Log.d(TAG, "Both objects are null!");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

//  =================   call this method at last to acknowledge successful transaction status

    private void sendFinalPaymentDetail(String payType) {
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        progressDialog = new ProgressDialog(PaymentMethodActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Initiating payment...Please wait");
        progressDialog.setProgressStyle(R.style.CheckoutTheme);
        progressDialog.show();
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", userid);
        param.put("orderId", orderId);
        param.put("txnId",txnId );
        param.put("paymentType", payType);
        param.put("cartSessionId", cartSessionId);
        param.put("currencyCode",selectedcurrency);
        param.put("currencyValue",originalValue);
        Log.e("SetPaymentReq",":"+param);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<OrderDetailsBean> call = restInterface.setPaymentSuccess(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<OrderDetailsBean>() {
            @Override
            public void onResponse(Call<OrderDetailsBean> call, Response<OrderDetailsBean> response) {
                Log.e("setPaymentRes",":"+response.body().getSuccess());
                Log.e("setPaymentResBody",":"+response.body());
                progressDialog.setCancelable(true);
                try {
                    Toast.makeText(PaymentMethodActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getSuccess()==1) {
                        progressDialog.dismiss();
                        Checkout.clearUserData(getApplicationContext());
                        Intent intent = new Intent(PaymentMethodActivity.this, ThankyouActivity.class);
                        intent.putExtra(Constants.PreferenceConstants.FIRSTNAME, tvUsername.getText());
                        intent.putExtra(Constants.PreferenceConstants.SELECTEDADDRESS, tvFullAddress.getText());
                        intent.putExtra(Constants.PreferenceConstants.DEFADDRESSPHONE, tvPhoneNo.getText());
                        intent.putExtra(Constants.PreferenceConstants.ORDERID, orderId);
                        intent.putExtra(Constants.PreferenceConstants.DELIVERY_DATE, "");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                    }
                }catch(Exception e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OrderDetailsBean> call, Throwable t) {
                progressDialog.setCancelable(false);
                progressDialog.dismiss();
                showErrorDialog(getResources().getString(R.string.pay_err_msg));
//                Toast.makeText(PaymentMethodActivity.this, "Transaction failed!", Toast.LENGTH_SHORT).show();
                Log.e("ErorInPaymnt",""+t);
            }
        });
    }

    private void showErrorDialog(String msg) {
        errorDialog = new Dialog(PaymentMethodActivity.this);
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errorDialog.setCancelable(false);
        errorDialog.setContentView(R.layout.payment_method_error);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            errorDialog.getWindow().setNavigationBarColor(PaymentMethodActivity.this.getResources().getColor(R.color.transparent));
        }
        TextView tvMsg = errorDialog.findViewById(R.id.tv_msg);
        tvMsg.setText(msg);
        Button btnOk = errorDialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorDialog.dismiss();
                finish();
            }
        });

        errorDialog.show();
    }

    private void openConfirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PaymentMethodActivity.this);
        builder.setMessage("Do you really want to leave this page?")
                .setTitle("Exit Payment");


        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
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


//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@   Razorpay Payment Integration  @@@@@@@@@@@@@@@@@

    private void getRazorpayOrderId() {
        HashMap<String, String> params= new HashMap<>();
        params.put("userid",userid);
        params.put("cartSessionId",cartSessionId);
        params.put("orderId",orderId);
        Log.e("RazOrderParam",":"+params);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<RazorpayOrderResponse> call = restInterface.getRazorPayOderId(globalUtils.getKey(), globalUtils.getapidate(), params);
        call.enqueue(new Callback<RazorpayOrderResponse>() {
            @Override
            public void onResponse(Call<RazorpayOrderResponse> call, Response<RazorpayOrderResponse> response) {
                Log.e("RazorpayOrderRes",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response));
                try{
                    if(response.body().getSuccess()==1){
                        razorPayOrderId = response.body().getResult().getRazorpayOrderId();
                        callRazorPayView();
                    }else{
                        showErrorDialog(getResources().getString(R.string.pay_err_msg));
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    e.printStackTrace();
                    showErrorDialog(getResources().getString(R.string.pay_err_msg));
                }
            }

            @Override
            public void onFailure(Call<RazorpayOrderResponse> call, Throwable t) {
                Log.e("RazorOderExc",":"+t);
                showErrorDialog(getResources().getString(R.string.pay_err_msg));
            }
        });
    }

    private void callRazorPayView() {
        progressDialog.setTitle("Redirecting to Razorpay ...");
        if(!razorPayOrderId. isEmpty()) {
            final Activity activity = this;
            co = new Checkout();
            co.setKeyID(getResources().getString(R.string.rzp_apikey));

            try {
                JSONObject options = new JSONObject();
                options.put("name", userName);
                options.put("description", "order id: " + orderId);
                options.put("image", "https://www.gaurashtra.com/image/catalog/Gaurashtra_New_Logo.png");
                options.put("currency", selectedcurrency);
                options.put("amount", Double.parseDouble(totalAmount) * 100);
                options.put("order_id", razorPayOrderId);

                JSONObject preFill = new JSONObject();
                preFill.put("email", userEmail);
                preFill.put("contact", userPhone);
                options.put("prefill", preFill);

                co.open(activity, options);
                if (!co.isInLayout()) {
                    progressDialog.dismiss();
//                rbRazPay.setChecked(false);
//                Toast.makeText(this, "backfromPayment gateway", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
//            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                e.printStackTrace();
                showErrorDialog(getResources().getString(R.string.pay_err_msg));
            }
        }else{
            Toast.makeText(PaymentMethodActivity.this, "Payment failed!", Toast.LENGTH_LONG).show();
        }
    }

    class GetRazoprPayOrderId extends AsyncTask<String, String, Order> {
        @Override
        protected Order doInBackground(String... strings) {
            Order order=null;
            try {
                RazorpayClient razorpay = new RazorpayClient("rzp_live_tNlHeOfmNqgtwO", "mhYVUT0aVZSQ7UFWs9rgB0u8");
                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", Double.parseDouble(totalAmount)*100); // amount in the smallest currency unit
                orderRequest.put("currency", selectedcurrency);
                orderRequest.put("receipt", orderId);
                orderRequest.put("payment_capture", true);
                order = razorpay.Orders.create(orderRequest);

            } catch (RazorpayException e) {  // Handle Exception
                System.out.println("RazorpayExc"+e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return order;
        }

        @Override
        protected void onPostExecute(Order order) {
            super.onPostExecute(order);
            Log.e("OrderResponse",":"+order);
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(String.valueOf(order));
                razorPayOrderId = jsonObject.getString("id");
                Log.e("razOrderId", ":" + razorPayOrderId);
                if(!razorPayOrderId.isEmpty()){
                    callRazorPayView();
                }else{
                    if(progressDialog!= null){
                        progressDialog.dismiss();
                    }
                    showErrorDialog(getResources().getString(R.string.pay_err_msg));
//                    GlobalUtils.showToast(PaymentMethodActivity.this, "Razorpay order id has not generated");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        Log.e("RazorResponse",":"+new GsonBuilder().setPrettyPrinting().create().toJson(paymentData));
        if(paymentData.getData() != null) {
            txnId = paymentData.getPaymentId();
            razorPayOrderId = paymentData.getOrderId();
            rzpSignature = paymentData.getSignature();
            new CheckIfPaymentCaptured().execute();
        }else{
            if(progressDialog != null){
                progressDialog.dismiss();
            }
            showErrorDialog(getResources().getString(R.string.pay_err_msg));
        }
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
        showErrorDialog(getResources().getString(R.string.pay_err_msg));
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    class CheckIfPaymentCaptured extends AsyncTask<String, String, Payment>{
        @Override
        protected Payment doInBackground(String... strings) {
            RazorpayClient razorpay = null;
            Payment payment= null;
            try {
                razorpay = new RazorpayClient("rzp_live_tNlHeOfmNqgtwO", "mhYVUT0aVZSQ7UFWs9rgB0u8");
                payment = razorpay.Payments.fetch(txnId);
            }catch (RazorpayException e) {
                // Handle Exception
                System.out.println(e.getMessage());
            }
            return payment;
        }

        @Override
        protected void onPostExecute(Payment payment) {
            super.onPostExecute(payment);
            Log.e("IsPaymentCapRes",":"+payment);
            if(payment.get("status").toString().equalsIgnoreCase("Failed")) {
                showErrorDialog(getResources().getString(R.string.pay_err_msg));
            } else if(payment.get("status").toString().equalsIgnoreCase("Captured")){
                sendFinalPaymentDetail("razorpay");
            }else{
                new CaptureRazorpayPayment().execute();
            }
        }
    }
    class CaptureRazorpayPayment extends AsyncTask<String, String, Payment>{

        @Override
        protected Payment doInBackground(String... strings) {
            Payment payment=null;
            try {
                RazorpayClient razorpay = new RazorpayClient(getResources().getString(R.string.rzp_apikey), (getResources().getString(R.string.rzp_secret)));
                JSONObject captureRequest = new JSONObject();
                captureRequest.put("currency", selectedcurrency);
                captureRequest.put("amount", Double.parseDouble(totalAmount)*100);

                payment = razorpay.Payments.capture(txnId, captureRequest);
            } catch (RazorpayException e) {
                // Handle Exception
                System.out.println(e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return payment;
        }

        @Override
        protected void onPostExecute(Payment payment) {
            super.onPostExecute(payment);
            Log.e("CaptureResponseRzp",":"+payment);
            if(payment != null) {
                isPaymentComplete=true;
                try {
                    JSONObject capObj = new JSONObject(String.valueOf(payment));
                    if(capObj.getString("status").equalsIgnoreCase("captured")) {
                        sendFinalPaymentDetail("razorpay");
                    }else if(capObj.getString("status").equalsIgnoreCase("failed")){
                        if(progressDialog != null){
                            progressDialog.dismiss();
                        }
                        showErrorDialog(getResources().getString(R.string.pay_err_msg));
                    }else{
                        new CheckIfPaymentCaptured().execute();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{
                if(progressDialog != null){
                    progressDialog.dismiss();
                }
                showErrorDialog(getResources().getString(R.string.pay_err_msg));
//                Toast.makeText(PaymentMethodActivity.this, "Payment not captured and will be refunded within 5 working days, please try again later!", Toast.LENGTH_LONG).show();
//                finish();
            }
        }
    }

//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PAYTM PAYMENT INTEGRATION  @@@@@@@@@@@@@@@@@@@@@@@

    private void getPaytmCheckSum() {
        if(progressDialog != null) {
            progressDialog.setTitle("Redirecting to Paytm...");
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("MID",Constants.PayTm.M_ID);
        map.put("CUST_ID", SharedPreference.getUserid(PaymentMethodActivity.this));
        map.put("ORDER_ID", orderId);
        map.put("INDUSTRY_TYPE_ID", Constants.PayTm.INDUSTRY_ID);
        map.put("CHANNEL_ID", Constants.PayTm.CHANNEL_ID);
        map.put("TXN_AMOUNT", totalAmount);
        map.put("WEBSITE", Constants.PayTm.WEBSITE);
        map.put("MOBILE_NO", userPhone);
        map.put("EMAIL", userEmail);
        map.put("CALLBACK_URL", Constants.PayTm.CALLBACKURL+orderId);
        map.put("MERCHANT_KEY", Constants.PayTm.MERCHANT_KEY);
        Log.e("RequestForCSPaytm",":"+map);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<PaytmChecksumBean> call = restInterface.getPaytmChecksum(globalUtils.getKey(), globalUtils.getapidate(), map);
        call.enqueue(new Callback<PaytmChecksumBean>() {
            @Override
            public void onResponse(Call<PaytmChecksumBean> call, Response<PaytmChecksumBean> response) {
                Log.e("GetCSPaytm",":"+response.body().getSuccess());

                if(response.body().getSuccess()==1){
                    PaytmChecksum= response.body().getResult().getHashKey();
                    Log.e("CheckSum",":"+PaytmChecksum);
                    callPaytmPaymentMethod();
                }else{
                    if(progressDialog!= null){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(PaymentMethodActivity.this, "Unable to proceed your payment, Try again later!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PaytmChecksumBean> call, Throwable t) {
                if(progressDialog!= null){
                    progressDialog.dismiss();
                }
                Toast.makeText(PaymentMethodActivity.this, "Exception Occurred: "+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callPaytmPaymentMethod() {
        progressDialog.dismiss();

        //getting paytm service
        PaytmPGService Service = PaytmPGService.getStagingService(Constants.PayTm.PAY_URL);
        //use this when using for production
//        PaytmPGService Service = PaytmPGService.getProductionService();

        //creating a hashmap and adding all the values required
        HashMap<String, String> paramMap = new HashMap<>();
//        paramMap.put("EMAIL", userEmail);
        paramMap.put("MID", Constants.PayTm.M_ID);
        paramMap.put("CUST_ID", SharedPreference.getUserid(PaymentMethodActivity.this));
        paramMap.put("CHANNEL_ID", Constants.PayTm.CHANNEL_ID);
        paramMap.put("ORDER_ID", orderId);
        paramMap.put("TXN_AMOUNT", totalAmount);
        paramMap.put("WEBSITE", Constants.PayTm.WEBSITE );
        paramMap.put("CALLBACK_URL", Constants.PayTm.CALLBACKURL+orderId);
        paramMap.put("CHECKSUMHASH", PaytmChecksum);
        paramMap.put("INDUSTRY_TYPE_ID", Constants.PayTm.INDUSTRY_ID);
        paramMap.put("MERCHANT_KEY", Constants.PayTm.MERCHANT_KEY);
        Log.e("paytmrequestData",":"+paramMap);
        //creating a paytm order object using the hashmap
        PaytmOrder order = new PaytmOrder(paramMap);

        //intializing the paytm service
        Service.initialize(order, null);

        //finally starting the payment transaction
        Service.startPaymentTransaction(PaymentMethodActivity.this, true, true, new PaytmPaymentTransactionCallback() {
            /*Call Backs*/
            public void someUIErrorOccurred(String inErrorMessage) {
                Log.e("PAytmError",":"+inErrorMessage);
            }
            public void onTransactionResponse(Bundle inResponse) {
                Log.e("PAytmResponse", ":" + inResponse);
                try {

                    isPaymentComplete=true;
                    String status=inResponse.getString("STATUS");
                    String TxnID=inResponse.getString("TXNID");
                    String resMeg=inResponse.getString("RESPMSG");
                    if (status.equalsIgnoreCase("TXN_SUCCESS")) {
                        if (GlobalUtils.isNetworkAvailable(PaymentMethodActivity.this)) {
                            txnId= TxnID;
                            sendFinalPaymentDetail("paytm");
                        } else {
                            Toast.makeText(PaymentMethodActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e("TranscFailed", ":" + inResponse);
                        Toast.makeText(PaymentMethodActivity.this, "Transaction failed:" +resMeg, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            public void networkNotAvailable() {}

            public void clientAuthenticationFailed(String inErrorMessage) {
                Toast.makeText(PaymentMethodActivity.this, "Trasaction failed:"+inErrorMessage, Toast.LENGTH_SHORT).show();
                Log.e("PAytmFailed",":"+inErrorMessage);
            }

            public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                Log.e("ErrorLoadingWebPage",":"+inErrorMessage);
            }

            public void onBackPressedCancelTransaction() {
                openConfirmDialog();
            }
            public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                Log.e("TransactionCancel",":"+inErrorMessage);
                openConfirmDialog();
            }
        });
    }

// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@   PAYPAL Payment Method    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private void payPalMethod() {
        progressDialog.setTitle("Redirecting to payPal...");
        progressDialog.setCancelable(false);

        thingToBuy = new PayPalPayment(new BigDecimal(String.valueOf(totalAmount)), "USD",
                "Order id: "+orderId, PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(PaymentMethodActivity.this,
                com.paypal.android.sdk.payments.PaymentActivity.class);

        intent.putExtra(com.paypal.android.sdk.payments.PaymentActivity.EXTRA_PAYMENT, thingToBuy);

        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    public void onFuturePaymentPressed(View pressed) {
        Intent intent = new Intent(PaymentMethodActivity.this,
                PayPalFuturePaymentActivity.class);
        startActivityForResult(intent, REQUEST_CODE_FUTURE_PAYMENT);
    }



    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    public void onFuturePaymentPurchasePressed(View pressed) {
        // Get the Application Correlation ID from the SDK
        String correlationId = PayPalConfiguration
                .getApplicationCorrelationId(this);

        Log.i("FuturePaymentExample", "Application Correlation ID: "
                + correlationId);


        // processing with
        // PayPal...
        Toast.makeText(getApplicationContext(),
                "App Correlation ID received from SDK", Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onDestroy() {
        // Stop service when done
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }


//    @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ PayUMoney Payment Integration  @@@@@@@@@@@@@@@@@@@@@@@@@

    private void launchPayUMoneyFlow() {
        if(progressDialog != null) {
            progressDialog.setTitle("Redirecting to PayUMoney...");
        }
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Payment");
        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Gaurashtra");
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
        String phone = SharedPreference.getUserPhone(PaymentMethodActivity.this);
        if(phone.isEmpty()){
            phone= "1212121212";
        }
        String productName = "Gaurashtra";
        String firstName = SharedPreference.getFirstName(PaymentMethodActivity.this);
        String email="";
        if(!SharedPreference.getUserEmail(this).isEmpty()) {
            email = SharedPreference.getUserEmail(PaymentMethodActivity.this);
            userEmail=email;
        }else if(!SharedPreference.getSocialEmail(this).isEmpty()){
            email= SharedPreference.getSocialEmail(this);
            userEmail=email;
        }
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";
        builder.setAmount(totalAmount+"")
                .setTxnId(orderId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(AppEnvironment.SANDBOX.surl())
                .setfUrl(AppEnvironment.SANDBOX.furl())
//                .setsUrl(AppEnvironment.PRODUCTION.surl())
//                .setfUrl(AppEnvironment.PRODUCTION.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(false)
                .setKey(AppEnvironment.SANDBOX.merchant_Key())
                .setMerchantId(AppEnvironment.SANDBOX.merchant_ID());
        try {
            mPaymentParams = builder.build();
            SharedPreference.setTxnIdFromServer(PaymentMethodActivity.this,orderId);
            SharedPreference.setAmountFromServer(PaymentMethodActivity.this, totalAmount+"");
            generateHashFromServer(mPaymentParams);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            // payNowButton.setEnabled(true);
        }
    }

    public void generateHashFromServer(PayUmoneySdkInitializer.PaymentParam paymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.
        HashMap<String, String> params = paymentParam.getParams();
        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.get(PayUmoneyConstants.KEY)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.get(PayUmoneyConstants.TXNID)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.PRODUCT_INFO, params.get(PayUmoneyConstants.PRODUCT_INFO)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.FIRSTNAME, params.get(PayUmoneyConstants.FIRSTNAME)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.get(PayUmoneyConstants.EMAIL)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF6, params.get(PayUmoneyConstants.UDF6)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF7, params.get(PayUmoneyConstants.UDF7)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF8, params.get(PayUmoneyConstants.UDF8)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF9, params.get(PayUmoneyConstants.UDF9)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF10, params.get(PayUmoneyConstants.UDF10)));

        paramsListForPayumoneyHash =new ArrayList<>();
        paramsListForPayumoneyHash.add(new BasicNameValuePair("payukey",params.get(PayUmoneyConstants.KEY)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("amount",params.get(PayUmoneyConstants.AMOUNT)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("txnid",params.get(PayUmoneyConstants.TXNID)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("email",params.get(PayUmoneyConstants.EMAIL)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("phone",params.get(PayUmoneyConstants.PHONE)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("productinfo",params.get(PayUmoneyConstants.PRODUCT_INFO)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("firstname",params.get(PayUmoneyConstants.FIRSTNAME)));

//        String hashSequence = "5jRdthRj|txn12347|9.0|OwnTv|Archana|archana.kumari@algosoft.co|udf1|udf2|udf3|udf4|udf5||||||d6aayomCTL";
        String hashSequence = params.get(PayUmoneyConstants.KEY)+"|"+params.get(PayUmoneyConstants.TXNID)+"|"+params.get(PayUmoneyConstants.AMOUNT)+"|"+params.get(PayUmoneyConstants.PRODUCT_INFO)+"|"+params.get(PayUmoneyConstants.FIRSTNAME)+"|"+params.get(PayUmoneyConstants.EMAIL)+"|||||||||||0cBffZXK";
        localServerHash= hashCal("SHA-512", hashSequence);

        Log.e("HASH SEQ",""+hashSequence+"\n Server Hash "+localServerHash);
        mPaymentParams.setMerchantHash(localServerHash);
//        PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentMethodActivity.this, R.style.PayumoneyAppTheme, true);

        if (globalUtils.isNetworkAvailable(PaymentMethodActivity.this))
        {
            getPayUMoneyChecksum(localServerHash);
        }else Toast.makeText(PaymentMethodActivity.this, "Please connect your Internet", Toast.LENGTH_SHORT).show();
    }

    private void getPayUMoneyChecksum(final String localServerHash) {

        HashMap<String, String> map = new HashMap<>();
        map.put("firstname",SharedPreference.getFirstName(PaymentMethodActivity.this));
        map.put("phone",userPhone);
        map.put("email",userEmail);
        map.put("txnid",orderId);
        map.put("key","uPmL4A");
        map.put("amount",totalAmount);
        map.put("productinfo","Gaurashtra");
        Log.e("RequestForCSPayUmoney",":"+map);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<PaytmChecksumBean> call = restInterface.getPayUMoneyChecksum(globalUtils.getKey(), globalUtils.getapidate(), map);
        call.enqueue(new Callback<PaytmChecksumBean>() {
            @Override
            public void onResponse(Call<PaytmChecksumBean> call, Response<PaytmChecksumBean> response) {
                Log.e("CS_PayUmoneyRes",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if(progressDialog!= null){
                    progressDialog.dismiss();
                }
                if(response.body().getSuccess()==1){
                    PayUmoneyChecksum= response.body().getResult().getHashKey();
                    Log.e("CheckSum",":"+PayUmoneyChecksum);
                    mPaymentParams.setMerchantHash(response.body().getResult().getHashKey());
                    mPaymentParams.setMerchantHash(localServerHash);
                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentMethodActivity.this, R.style.PayumoneyAppTheme, true);
                }else{
                    Toast.makeText(PaymentMethodActivity.this, "Unable to proceed your payment, Try again later!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PaytmChecksumBean> call, Throwable t) {
                if(progressDialog!= null){
                    progressDialog.dismiss();
                }
                Toast.makeText(PaymentMethodActivity.this, "Exception Occurred: "+t, Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

}
