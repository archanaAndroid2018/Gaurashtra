package com.gaurashtra.app.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.bean.CartInfoBean.CartInfoDTO;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.OrderBean.OrderedProductDetails;
import com.gaurashtra.app.model.bean.ReviewResult;
import com.gaurashtra.app.view.adapter.OrderDetailAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity implements OrderDetailAdapter.ReviewInterface {
    private TextView textviewTitle;
    private RecyclerView DetailRv;
    private OrderDetailAdapter adapter;
    TextView tvOrderId, tvOrderDate, tvAmount, tvOrderedOn, tvDeliveredOn, tvShipUsername,tvShipAddress, tvShipPhone,
    tvBillUsername, tvBillAddress, tvBillPhone, tvDeliverTitle, tvDeliveredStatus, tvTerrible, tvBad, tvOkay, tvGood, tvGreat;

    LinearLayout tax1Layout, tax2Layout, tax3Layout, tax4Layout, walletLayout, couponLayout;

    TextView tvTaxLabel1, tvTaxAmount1,tvTaxLabel2, tvTaxAmount2,tvTaxLabel3, tvTaxAmount3,
            tvTaxLabel4, tvTaxAmount4, tvWalletLabel, tvWalletAmount, tvSubTotalLabel, tvProductWeight,
            tvSubTotalAmount, tvShippingLabel, tvShippingAmount, tvCouponLabel, tvCouponAmount;
    String strTaxLabel1, strTaxAmount1,strTaxLabel2, strTaxAmount2,strTaxLabel3, strTaxAmount3,
            strTaxLabel4, strTaxAmount4, strWalletLabel, strWalletAmount, strSubTotalLabel,
            strSubTotalAmount, strShippingLabel, strShippingAmount, strCouponLabel, strCouponAmount="", trackingUrl;
    String orderId, userId;
    GlobalUtils globalUtils= new GlobalUtils();
    ArrayList<OrderedProductDetails.Result.OrderData.Product> productList;
    ImageView iconTerrible, iconBad, iconOkay, iconGood,iconGreat;
    EditText etReview;
    Button btnSubmitReview;
    RelativeLayout trackLayout;
    LinearLayout layoutTerrible, layoutBad,layoutOkay, layoutGood, layoutGreat, layoutCollapsible, layoutOrderInfo;
    String reviewText, ratingValue;
    PrefClass prefClass;
    String currencySymbol="", selectedcurrency="INR";
    float currencyValue= (float) 1.000;
    Boolean isOpen=false;
    LinkedHashMap<String, String> orderStatusMap= new LinkedHashMap<>();
    NestedScrollView nestedScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        orderId= getIntent().getStringExtra(Constants.PreferenceConstants.ORDERID);
        userId= SharedPreference.getUserid(this);
        setStatusMap();
        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Orders Detail");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        UserUpdate userUpdate= new UserUpdate(OrderDetailActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(OrderDetailActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(OrderDetailActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        prefClass= new PrefClass(this);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                selectedcurrency=prefClass.getSelectedCurrency();
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
            }
        }
        setUpContentView();
        if(GlobalUtils.isNetworkAvailable(this)) {
            GlobalUtils.showdialog(this);
            getOrderDetails();
        }else{
            GlobalUtils.showToast(this, getResources().getString(R.string.no_internet_connection));
        }
    }

    private void setStatusMap() {
        orderStatusMap.put("1","Pending");
        orderStatusMap.put("2","Processing");
        orderStatusMap.put("3","In Transit");
        orderStatusMap.put("4","Payment Failed");
        orderStatusMap.put("5","Delivered");
        orderStatusMap.put("6","Partially Refunded");
        orderStatusMap.put("7","Payment Awaited");
        orderStatusMap.put("8","Returned & Refunded");
        orderStatusMap.put("9","Damaged & Refunded");
        orderStatusMap.put("10","Voided");
        orderStatusMap.put("11","On Hold");
        orderStatusMap.put("12","Cancelled Reversal");
        orderStatusMap.put("13","Awaiting Response");
        orderStatusMap.put("14","Out For Delivery");
        orderStatusMap.put("15","Shipment Returned");
    }

    private void getOrderDetails() {
        HashMap<String, String> param= new HashMap<>();
        param.put("userid", userId);
        param.put("orderId", orderId);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<OrderedProductDetails> call= restInterface.getOrderProductDetails(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<OrderedProductDetails>() {
            @Override
            public void onResponse(Call<OrderedProductDetails> call, Response<OrderedProductDetails> response) {
                if(response != null) {
                    if (response.body().getSuccess() == 1) {
                        GlobalUtils.hidedialog();
                        nestedScrollView.setVisibility(View.VISIBLE);
                        productList = new ArrayList<>();
                        tvOrderId.setText(orderId);
                        tvOrderDate.setText(response.body().getResult().getOrderData().getGeneral().getOrderDate());
                        Double amount = Double.parseDouble(response.body().getResult().getOrderData().getGeneral().getAmount());
                        tvAmount.setText(currencySymbol + new DecimalFormat("0.00").format(amount * currencyValue));
                        tvOrderedOn.setText(response.body().getResult().getOrderData().getGeneral().getOrderDate());
                        ArrayList<OrderedProductDetails.Result.OrderData.Info> orderInfoList = new ArrayList<>();
                        orderInfoList = (ArrayList<OrderedProductDetails.Result.OrderData.Info>) response.body().getResult().getOrderData().getInfo();
                        String orderStatus= response.body().getResult().getOrderData().getGeneral().getOrderStatus();

                            tvDeliverTitle.setVisibility(View.VISIBLE);
                            tvDeliverTitle.setText(orderStatusMap.get(orderStatus));
                            tvDeliveredOn.setVisibility(View.GONE);
                            tvDeliveredStatus.setVisibility(View.GONE);
                        if(orderStatus.equalsIgnoreCase("5")){
                            tvDeliveredStatus.setVisibility(View.VISIBLE);
                            tvDeliveredOn.setText(response.body().getResult().getOrderData().getGeneral().getDelivered());
                        }
                        if(response.body().getResult().getOrderData().getGeneral().getTrackingUrl().isEmpty()){
                            trackLayout.setVisibility(View.GONE);
                        }else{
                            trackLayout.setVisibility(View.VISIBLE);
                            trackingUrl = response.body().getResult().getOrderData().getGeneral().getTrackingUrl();
                        }
                        tvShipUsername.setText(response.body().getResult().getOrderData().getGeneral().getShippingName());
                        tvShipAddress.setText(response.body().getResult().getOrderData().getGeneral().getShippingAddress1() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getShippingAddress2() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getShippingCity() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getShippingZone() + " - " +
                                response.body().getResult().getOrderData().getGeneral().getShippingPostcode() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getShippingCountry());
                        tvShipPhone.setText(response.body().getResult().getOrderData().getGeneral().getShippingPhone());
                        tvBillUsername.setText(response.body().getResult().getOrderData().getGeneral().getCustomerName());
                        tvBillAddress.setText(response.body().getResult().getOrderData().getGeneral().getPaymentAddress1() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getPaymentAddress2() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getPaymentCity() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getPaymentZone() + " - " +
                                response.body().getResult().getOrderData().getGeneral().getPaymentPostcode() + ", " +
                                response.body().getResult().getOrderData().getGeneral().getPaymentCountry());
                        tvBillPhone.setText(response.body().getResult().getOrderData().getGeneral().getCustomerPhone());
                        for (int j = 0; j < orderInfoList.size(); j++) {
                            if (orderInfoList.get(j).getCode().equalsIgnoreCase("sub_total")) {
                                strSubTotalLabel = orderInfoList.get(j).getTitle();
                                strSubTotalAmount = orderInfoList.get(j).getValue();
                            }
                            if (orderInfoList.get(j).getCode().equalsIgnoreCase("shipping")) {
                                strShippingLabel = orderInfoList.get(j).getTitle();
                                strShippingAmount = orderInfoList.get(j).getValue();
                                String[] prodWeight= strShippingLabel.split(":");

                                if(prodWeight != null) {
                                    tvProductWeight.setVisibility(View.VISIBLE);
                                    tvProductWeight.setText("(Weight: "+prodWeight[1]);
                                }else{
                                    tvProductWeight.setVisibility(View.GONE);
                                }
                            }
                            if (orderInfoList.get(j).getCode().equalsIgnoreCase("tax")) {
                                strTaxLabel1 = orderInfoList.get(j).getTitle();
                                strTaxAmount1 = orderInfoList.get(j).getValue();
                            }
                            if (orderInfoList.get(j).getCode().equalsIgnoreCase("total")) {
                                Double netAmount = Double.parseDouble(orderInfoList.get(j).getValue());
                                tvAmount.setText(currencySymbol + new DecimalFormat("0.00").format(netAmount * currencyValue));
                            }

                            Log.e("CartInfo", "Title: " + orderInfoList.get(j).getTitle() + "\n value:" + orderInfoList.get(j).getValue());

                        }
                        productList = (ArrayList<OrderedProductDetails.Result.OrderData.Product>) response.body().getResult().getOrderData().getProduct();
                        adapter = new OrderDetailAdapter(OrderDetailActivity.this, productList, OrderDetailActivity.this);
                        DetailRv.setAdapter(adapter);
                    } else {
                        GlobalUtils.hidedialog();
                        Toast.makeText(OrderDetailActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<OrderedProductDetails> call, Throwable t) {
              Log.e("ExcOrderDetails",""+t);
                GlobalUtils.hidedialog();
                nestedScrollView.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setUpContentView(){
        nestedScrollView = findViewById(R.id.nestedScrollview);
        trackLayout = findViewById(R.id.track_order_layout);
        tvProductWeight = findViewById(R.id.tv_weight_info);
        DetailRv  = findViewById(R.id.rvDetail);
        tvOrderId = findViewById(R.id.tv_ord_details_orderid);
        tvOrderDate = findViewById(R.id.tv_ord_details_orderdate);
        tvAmount = findViewById(R.id.tv_ord_details_amount);
        tvOrderedOn = findViewById(R.id.tv_ord_details_OrderedOn);
        tvDeliveredOn = findViewById(R.id.tv_ord_details_deliveredOn);
        tvShipUsername = findViewById(R.id.tv_ship_username);
        tvShipAddress = findViewById(R.id.tv_ship_address);
        tvShipPhone = findViewById(R.id.tv_ship_phone);
        tvBillUsername = findViewById(R.id.tv_billing_username);
        tvBillAddress = findViewById(R.id.tv_billing_address);
        tvBillPhone = findViewById(R.id.tv_billing_phone);
        tvDeliverTitle= findViewById(R.id.tv_deliver_title);
        tvDeliveredStatus= findViewById(R.id.tv_deliver_status);
        layoutCollapsible= findViewById(R.id.ll_collapsible);
        layoutOrderInfo= findViewById(R.id.order_info_layout);

        tvSubTotalLabel= findViewById(R.id.tv_subtotal_title);
        tvSubTotalAmount= findViewById(R.id.tv_subtotal_price);
        tvShippingLabel= findViewById(R.id.tv_shipping_label);
        tvShippingAmount= findViewById(R.id.tv_shipping_amount);
        couponLayout= findViewById(R.id.coupon_amount_layout);
        tvCouponLabel= findViewById(R.id.tv_coupon_label);
        tvCouponAmount= findViewById(R.id.tv_coupon_amount);
        tax1Layout= findViewById(R.id.tax1_layout);
        tax2Layout = findViewById(R.id.tax2_layout);
        tax3Layout = findViewById(R.id.tax3_layout);
        tax4Layout = findViewById(R.id.tax4_layout);
        walletLayout = findViewById(R.id.wallet_layout);
        tvTaxLabel1 = findViewById(R.id.tv_tax1_title);
        tvTaxAmount1 = findViewById(R.id.tv_tax_amount1);
        tvTaxLabel2 = findViewById(R.id.tv_tax2_title);
        tvTaxAmount2 = findViewById(R.id.tv_tax_amount2);
        tvTaxLabel3 = findViewById(R.id.tv_tax3_title);
        tvTaxAmount3 = findViewById(R.id.tv_tax_amount3);
        tvTaxLabel4 = findViewById(R.id.tv_tax4_title);
        tvTaxAmount4 = findViewById(R.id.tv_tax_amount4);
        tvWalletLabel = findViewById(R.id.tv_wallet_label);
        tvWalletAmount = findViewById(R.id.tv_wallet_amount);
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        DetailRv.setLayoutManager(manager);
        DetailRv.setNestedScrollingEnabled(false);
        layoutCollapsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOpen){
                    isOpen=true;
                    layoutOrderInfo.setVisibility(View.VISIBLE);
                    setBreakUpsLayout();
                }else{
                    isOpen=false;
                    layoutOrderInfo.setVisibility(View.GONE);
                }
            }
        });

        trackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openTrackOrderDialog();
            }
        });

   }

    private void openTrackOrderDialog() {
//        trackingUrl= "https://gaurashtra.shiprocket.co/tracking/1904127305223";// for testing purpose only

        final Dialog dialog = new Dialog(OrderDetailActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.track_order_layout);
        WebView webView = dialog.findViewById(R.id.wv_track_order);
        ImageView btnClose = dialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.clearCache(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl(trackingUrl);

        dialog.show();
    }

    private void setBreakUpsLayout() {
        try {
            tvSubTotalLabel.setText(strSubTotalLabel);
            Double netAmount= Double.parseDouble(strSubTotalAmount);
            tvSubTotalAmount.setText(currencySymbol+new DecimalFormat("0.00").format(netAmount*currencyValue));
            tvShippingLabel.setText("Shipping charge");
            tvShippingAmount.setText(currencySymbol+new DecimalFormat("0.00").format(Double.parseDouble(strShippingAmount)*currencyValue));


            if (!strTaxLabel1.isEmpty()) {
                tax1Layout.setVisibility(View.VISIBLE);
                tvTaxLabel1.setText(strTaxLabel1);
                tvTaxAmount1.setText(currencySymbol+new DecimalFormat("0.00").format(Double.parseDouble(strTaxAmount1)*currencyValue));
            }
            if (!strTaxLabel2.isEmpty()) {
                tax2Layout.setVisibility(View.VISIBLE);
                tvTaxLabel2.setText(strTaxLabel2);
                tvTaxAmount2.setText(strTaxAmount2);
            }

            //not in  use
 /*           if (!strTaxLabel3.isEmpty()) {
                tax3Layout.setVisibility(View.VISIBLE);
                tvTaxLabel3.setText(strTaxLabel3);
                tvTaxAmount3.setText(strTaxAmount3);
            }
            if (!strTaxLabel4.isEmpty()) {
                tax4Layout.setVisibility(View.VISIBLE);
                tvTaxLabel4.setText(strTaxLabel4);
                tvTaxAmount4.setText(strTaxAmount4);
            }

  */
            if (!strWalletLabel.isEmpty()) {
                walletLayout.setVisibility(View.VISIBLE);
                tvWalletLabel.setText(strWalletLabel);
                tvWalletAmount.setText(strWalletAmount);
            }

            if (!strCouponAmount.isEmpty()) {
                couponLayout.setVisibility(View.VISIBLE);
                tvCouponLabel.setText(strCouponLabel+"(-)");
                tvCouponAmount.setText(new DecimalFormat("0.00").format(Float.parseFloat(strCouponAmount) * currencyValue));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void openWriteReview(int adapterPosition, final String productId) {
        final Dialog dialog = new Dialog(OrderDetailActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.write_review_layout);
        ImageView btnDelete = (ImageView) dialog.findViewById(R.id.img_rev_btn_delete);
        etReview= dialog.findViewById(R.id.et_review);
        btnSubmitReview= dialog.findViewById(R.id.btn_submit_review);

        tvTerrible=dialog.findViewById(R.id.tv_terrible_text);
        tvBad= dialog.findViewById(R.id.tv_bad_text);
        tvOkay= dialog.findViewById(R.id.tv_okay_text);
        tvGood= dialog.findViewById(R.id.tv_good_text);
        tvGreat= dialog.findViewById(R.id.tv_great_text);

        iconTerrible= dialog.findViewById(R.id.iv_terrible_icon);
        iconBad= dialog.findViewById(R.id.iv_bad_icon);
        iconOkay= dialog.findViewById(R.id.iv_okay_icon);
        iconGood= dialog.findViewById(R.id.iv_good_icon);
        iconGreat= dialog.findViewById(R.id.iv_great_icon);

        layoutTerrible = dialog.findViewById(R.id.ll_terrible_icon);
        layoutBad= dialog.findViewById(R.id.ll_bad_icon);
        layoutOkay= dialog.findViewById(R.id.ll_okay_icon);
        layoutGood= dialog.findViewById(R.id.ll_good_icon);
        layoutGreat= dialog.findViewById(R.id.ll_great_icon);

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewText= etReview.getText().toString().trim();
                try{
                    if(reviewText.isEmpty()){
                        Toast.makeText(OrderDetailActivity.this, "Please write review!", Toast.LENGTH_SHORT).show();
                    }else if(ratingValue.equalsIgnoreCase("0")){
                        Toast.makeText(OrderDetailActivity.this, "Please rate the product!", Toast.LENGTH_SHORT).show();
                    }else{
                        dialog.dismiss();
                        submitReview(productId);
                    }
                }catch (Exception e){
                    dialog.dismiss();
                    e.printStackTrace();}
            }

        });

        layoutTerrible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="1";
                setRatingIcon(iconTerrible, iconBad,iconOkay,iconGood,iconGreat);
                setIconText(tvTerrible, tvBad, tvOkay, tvGood, tvGreat);
            }
        });
        layoutBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="2";
                setRatingIcon(iconBad, iconTerrible,iconOkay,iconGood,iconGreat);
                setIconText(tvBad, tvTerrible, tvOkay, tvGood, tvGreat);
            }
        });
        layoutOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="3";
                setRatingIcon(iconOkay,iconBad,iconTerrible,iconGood,iconGreat);
                setIconText(tvOkay,tvBad,tvTerrible, tvGood, tvGreat);
            }
        });
        layoutGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="4";
                setRatingIcon(iconGood,iconBad, iconOkay, iconTerrible,iconGreat);
                setIconText(tvGood,tvTerrible,tvBad, tvOkay, tvGreat);
            }
        });
        layoutGreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="5";
                setRatingIcon(iconGreat,iconOkay, iconGood,iconTerrible, iconBad);
                setIconText(tvGreat,tvTerrible,tvBad, tvOkay, tvGood);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    private void submitReview(String productId) {
        HashMap<String, String> param= new HashMap<>();
        param.put("userid", SharedPreference.getUserid(OrderDetailActivity.this));
        param.put("productId", productId);
        param.put("review", reviewText);
        param.put("rating", ratingValue);
        param.put("reviewType","add");
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<ReviewResult> call= restInterface.sendProductReviewRating(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, retrofit2.Response<ReviewResult> response) {
                if(response.isSuccessful()){
                    Toast.makeText(OrderDetailActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(OrderDetailActivity.this, "Something went wrong, please resubmit review!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {

            }
        });
    }

    private void setIconText(TextView selected, TextView unSelected1, TextView unSelected2, TextView unSelected3, TextView unSelected4) {
        selected.setTextColor(getResources().getColor(R.color.light_red));
        unSelected1.setTextColor(getResources().getColor(R.color.gray));
        unSelected2.setTextColor(getResources().getColor(R.color.gray));
        unSelected3.setTextColor(getResources().getColor(R.color.gray));
        unSelected4.setTextColor(getResources().getColor(R.color.gray));
    }

    private void setRatingIcon(ImageView selected, ImageView unSelected1, ImageView unSelected2, ImageView unSelected3, ImageView unSelected4) {
//         selected.setImageDrawable(getResources().getDrawable(R.drawable.ic ));
        DrawableCompat.setTint(
                DrawableCompat.wrap(selected.getDrawable()),
                ContextCompat.getColor(this, R.color.light_red)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected1.getDrawable()),
                ContextCompat.getColor(this, R.color.gray)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected2.getDrawable()),
                ContextCompat.getColor(this, R.color.gray)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected3.getDrawable()),
                ContextCompat.getColor(this, R.color.gray)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected4.getDrawable()),
                ContextCompat.getColor(this, R.color.gray)
        );
    }

}
