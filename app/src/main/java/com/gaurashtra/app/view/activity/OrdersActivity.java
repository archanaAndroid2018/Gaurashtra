package com.gaurashtra.app.view.activity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.OrderBean.OrderListBean;
import com.gaurashtra.app.model.bean.OrderBean.OrderedProductDetails;
import com.gaurashtra.app.view.adapter.OrderAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity implements OrderAdapter.ReportLayoutInterface {
    private TextView textviewTitle, btnShopping;
    private RecyclerView orderRv;
    private OrderAdapter adapter;
    GlobalUtils globalUtils;
    List<OrderListBean.Result.OrderDatum> orderList;
    String etReportText;
    private LinearLayout linear_image;
    Boolean googlePlay=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        globalUtils = new GlobalUtils();
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("My Order");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        UserUpdate userUpdate= new UserUpdate(OrdersActivity.this);
        if(getIntent().hasExtra(Constants.PreferenceConstants.BACKFROM)){
            rateApp();
        }
        if(!userUpdate.isUserAvailable){
            Toast.makeText(OrdersActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(OrdersActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }

        setUpContentView();

    }

    public void setUpContentView() {
        linear_image=(LinearLayout)findViewById(R.id.Linear_Image);
        orderRv = findViewById(R.id.rvOrder);
        btnShopping= findViewById(R.id.tv_btn_shopping);
        orderRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        orderRv.setLayoutManager(manager);
        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(OrdersActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        getOrderList();
    }

    private void rateApp(){
        ImageView star1, star2, star3, star4, star5;
        BottomSheetDialog appRatingDialog = new BottomSheetDialog(OrdersActivity.this, R.style.DialogStyle);
        Window window = appRatingDialog.getWindow();
        appRatingDialog.setContentView(R.layout.feedback_layout);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        window.setBackgroundDrawableResource(R.color.transparent);
        ImageView btnBack = appRatingDialog.findViewById(R.id.iv_close);
        TextView btnAskLater= appRatingDialog.findViewById(R.id.tv_btn_asklater);
        TextView btnSubmit = appRatingDialog.findViewById(R.id.tv_btn_submit);
        star1 = appRatingDialog.findViewById(R.id.star1);
        star2 = appRatingDialog.findViewById(R.id.star2);
        star3 = appRatingDialog.findViewById(R.id.star3);
        star4 = appRatingDialog.findViewById(R.id.star4);
        star5 = appRatingDialog.findViewById(R.id.star5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appRatingDialog.dismiss();
            }
        });

        btnAskLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appRatingDialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        appRatingDialog.setCancelable(false);
        appRatingDialog.show();
    }

    private void getOrderList() {
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", SharedPreference.getUserid(OrdersActivity.this));
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<OrderListBean> call = restInterface.getOrderList(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<OrderListBean>() {
            @Override
            public void onResponse(Call<OrderListBean> call, Response<OrderListBean> response) {
                try {
                if (response.body().getSuccess()==1) {
                    orderList = new ArrayList<>();
                        if(response.body().getResult() != null){
                            linear_image.setVisibility(View.GONE);
                            orderList = response.body().getResult().getOrderData();
                            adapter = new OrderAdapter(OrdersActivity.this, orderList, OrdersActivity.this);
                            orderRv.setAdapter(adapter);
                        }else {
                            linear_image.setVisibility(View.VISIBLE);
                        }

                } else {
                    linear_image.setVisibility(View.VISIBLE);
                   }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<OrderListBean> call, Throwable t) {

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
        Intent intent= new Intent(OrdersActivity.this,HomeActivity.class);
        intent.putExtra("BACKFROM","ME");
        startActivity(intent);
    }

    @Override
    public void openReportPopup(final String orderId) {
        final Dialog reportDialog = new Dialog(OrdersActivity.this, R.style.Theme_Design_NoActionBar);
        reportDialog.setContentView(R.layout.report_popup);
        LinearLayout btnDelete = (LinearLayout) reportDialog.findViewById(R.id.ll_close_btn);
        final EditText etReport = reportDialog.findViewById(R.id.et_report);
        TextView btnSubmit = reportDialog.findViewById(R.id.btn_report_submit);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportDialog.dismiss();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etReportText = etReport.getText().toString().trim();
                try {
                    if (etReportText.isEmpty()) {
                        Toast.makeText(OrdersActivity.this, "Please write something!", Toast.LENGTH_SHORT).show();
                    } else {
                        reportDialog.dismiss();
                        submitReport(orderId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        reportDialog.setCanceledOnTouchOutside(true);
        reportDialog.setCancelable(true);
        reportDialog.show();
    }

    private void submitReport(String orderId) {
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", SharedPreference.getUserid(this));
        param.put("orderId", orderId);
        param.put("comment", etReportText);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<OrderedProductDetails> call = restInterface.sendProductReport(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<OrderedProductDetails>() {
            @Override
            public void onResponse(Call<OrderedProductDetails> call, Response<OrderedProductDetails> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(OrdersActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(OrdersActivity.this, "" + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderedProductDetails> call, Throwable t) {

            }
        });
    }
}
