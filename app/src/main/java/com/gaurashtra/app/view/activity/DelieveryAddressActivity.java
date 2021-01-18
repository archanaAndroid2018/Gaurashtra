package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SwitchCompat;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.view.adapter.SelectAddressAdapter;

public class DelieveryAddressActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textviewTitle;
    private RecyclerView addressRv;
    private LinearLayout deliver, btnSaveAddress;
    private SelectAddressAdapter addressAdapter;
    RestInterface restInterface;
    private EditText firstName,lastName,mobile,address1,address2,city,pinCode,company;
    private SwitchCompat defaultAddress;
    private String UserID,fName="",lName="",adr1="", adr2="", cityText="", pinText="",cmpnyText="",mobileText="",dAddress = "Y",countryStr,stateStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delievery_address);
        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Selected Address");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        UserUpdate userUpdate= new UserUpdate(DelieveryAddressActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(DelieveryAddressActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(DelieveryAddressActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        setUpContentView();
        setOnClickListner();
    }

    public void setUpContentView(){
        firstName = findViewById(R.id.etFirstName);
        lastName  = findViewById(R.id.etLastName);
        mobile    = findViewById(R.id.etMobile);
        address1  = findViewById(R.id.etAddress1);
        address2  = findViewById(R.id.etAddress2);
        city      = findViewById(R.id.etCity);
        pinCode   = findViewById(R.id.etPinCode);
        company   = findViewById(R.id.etCompanyName);
        defaultAddress = findViewById(R.id.switch_item);

        addressRv = findViewById(R.id.rvAddress);
        deliver   = findViewById(R.id.lldeliver);
        btnSaveAddress= findViewById(R.id.llSaveButton);
        addressRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        addressRv.setLayoutManager(manager);
//        addressAdapter = new SelectAddressAdapter(this);
//        addressRv.setAdapter(addressAdapter);
    }

    public void setOnClickListner(){
        deliver.setOnClickListener(this);
        btnSaveAddress.setOnClickListener(this);
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lldeliver:
                Intent intent = new Intent(this,ConfirmOrderActivity.class);
//                intent.putExtra(Constants)
                startActivity(intent);
                break;

        case R.id.llSaveButton:

        break;
    }
    }
}
