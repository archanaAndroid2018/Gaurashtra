package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.SearchProductResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AskQuestionActivity extends AppCompatActivity {
TextView textviewTitle, tvBtnAsk, tvProductname;
EditText etUsername, etPhone, etEmail, etMessage;
String username,email,phone, message, productId, productName;
GlobalUtils globalUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        globalUtils= new GlobalUtils();
        productId=getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTID);
        productName = getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTNAME);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(true);
        abar.setDisplayHomeAsUpEnabled(true);
        textviewTitle.setText("Ask a Question");
        textviewTitle.setTextSize(18);

        UserUpdate userUpdate= new UserUpdate(AskQuestionActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(AskQuestionActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(AskQuestionActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        tvProductname = findViewById(R.id.tv_product_name);
        tvProductname.setText(productName);
        etUsername= findViewById(R.id.et_username);
        etEmail= findViewById(R.id.et_email);
        etPhone= findViewById(R.id.et_phone);
        etMessage= findViewById(R.id.et_message);
        tvBtnAsk= findViewById(R.id.tv_btn_ask);
        tvBtnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    username = etUsername.getText().toString().trim();
                    email = etEmail.getText().toString().trim();
                    phone = etPhone.getText().toString().trim();
                    message = etMessage.getText().toString().trim();
                    if(username.isEmpty()){
                        Toast.makeText(AskQuestionActivity.this, "Name should not be empty!", Toast.LENGTH_SHORT).show();
                    }else if(email.isEmpty()){
                        Toast.makeText(AskQuestionActivity.this, "Email id should not be empty!", Toast.LENGTH_SHORT).show();
                    }else if (!globalUtils.isEmailValidate(email)){
                        Toast.makeText(AskQuestionActivity.this, "Please enter valid email", Toast.LENGTH_SHORT).show();
                    }else if(message.isEmpty()){
                        Toast.makeText(AskQuestionActivity.this, "Message should not be empty!", Toast.LENGTH_SHORT).show();
                    }else{
                        tvBtnAsk.setEnabled(false);
                        sendQuery();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void sendQuery() {
        HashMap<String, String> map= new HashMap<>();
        map.put("userid", SharedPreference.getUserid(this));
        map.put("username",username);
        map.put("email",email);
        map.put("query", message);
        map.put("phone",phone);
        map.put("productid",productId);
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<SearchProductResult> call= restInterface.askQuestion(globalUtils.getKey(),globalUtils.getapidate(),map);
         call.enqueue(new Callback<SearchProductResult>() {
             @Override
             public void onResponse(Call<SearchProductResult> call, Response<SearchProductResult> response) {
                 tvBtnAsk.setEnabled(true);
                 if(response.body().getSuccess()==1){
                     Toast.makeText(AskQuestionActivity.this, ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                     finish();
                 }
             }
             @Override
             public void onFailure(Call<SearchProductResult> call, Throwable t) {

             }
         });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
