package com.gaurashtra.app.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;

public class ThankyouActivity extends AppCompatActivity {

    LinearLayout btnOrderHistory, deliveryDateLayout;
    Button btnHome;
    TextView tvUsername,tvPhone,  tvAddress, tvOrderId, tvOrderDate,textviewTitle;
    String username, address, phone, orderId, deliveryDate;

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ThankyouActivity.this, HomeActivity.class);
        intent.putExtra(Constants.PreferenceConstants.BACKFROM, "ORDER_PLACED");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(                                   //Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Payment Successful");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        username = getIntent().getStringExtra(Constants.PreferenceConstants.FIRSTNAME);
        address = getIntent().getStringExtra(Constants.PreferenceConstants.SELECTEDADDRESS);
        phone = getIntent().getStringExtra(Constants.PreferenceConstants.DEFADDRESSPHONE);
        orderId = getIntent().getStringExtra(Constants.PreferenceConstants.ORDERID);
        deliveryDate = getIntent().getStringExtra(Constants.PreferenceConstants.DELIVERY_DATE);

        btnOrderHistory= findViewById(R.id.ll_order_history);
        btnHome = findViewById(R.id.btn_home);

        tvUsername = findViewById(R.id.tv_username);
        tvPhone = findViewById(R.id.tv_phno);
        tvAddress = findViewById(R.id.tv_address);
        tvOrderId = findViewById(R.id.tv_order_id);
        tvOrderDate = findViewById(R.id.tv_delivery_date);
        deliveryDateLayout = findViewById(R.id.ll_del_date);

        tvUsername.setText(username);
        tvPhone.setText(phone);
        tvAddress.setText(address);
        tvOrderId.setText(orderId);
        if(!deliveryDate.isEmpty()) {
            deliveryDateLayout.setVisibility(View.VISIBLE);
            tvOrderDate.setText(deliveryDate);
        }else{
            deliveryDateLayout.setVisibility(View.GONE);
        }
        btnOrderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ThankyouActivity.this, OrdersActivity.class);
                intent.putExtra(Constants.PreferenceConstants.BACKFROM, "ORDER_PLACED");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ThankyouActivity.this, HomeActivity.class);
                intent.putExtra(Constants.PreferenceConstants.BACKFROM, "ORDER_PLACED");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ThankyouActivity.this, HomeActivity.class);
        intent.putExtra(Constants.PreferenceConstants.BACKFROM,"ORDER_PLACED");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}