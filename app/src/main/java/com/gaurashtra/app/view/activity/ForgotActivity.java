package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.forgotbean.ForgotResponseDTO;
import com.gaurashtra.app.presentator.ForgotPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.ForgotPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ForgotActivityInteractor;

public class ForgotActivity extends AppCompatActivity implements ForgotActivityInteractor, View.OnClickListener{
    private ForgotPresentarInteractor forgotPresentarInteractor;
    private EditText email;
    private LinearLayout submit;
    private GlobalUtils globalUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        forgotPresentarInteractor = new ForgotPresentar(this);
        globalUtils = new GlobalUtils();
        setUpContentView();
        setOnClickListner();
    }

    public void setUpContentView(){
        email = findViewById(R.id.etForgotEmail);
        submit = findViewById(R.id.llSubmit);
    }
    public void setOnClickListner(){
        submit.setOnClickListener(this);
    }

    @Override
    public void validateCredential(String msg) {
        globalUtils.showToast(this,msg);
    }

    @Override
    public void showProgress() {
        globalUtils.showdialog(this);
    }

    @Override
    public void hideProgress() {
        globalUtils.hidedialog();
    }

    @Override
    public void getResponse(Object object) {
        ForgotResponseDTO forgotResponseDTO = (ForgotResponseDTO)object;
        String mssage = forgotResponseDTO.getMessage();
        int success = forgotResponseDTO.getSuccess();
        if (success == 1){
            globalUtils.showToast(this,mssage);
            Intent intent = new Intent(this,ResetPasswordActivity.class);
            startActivity(intent);
        }else {
            globalUtils.showToast(this,mssage);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llSubmit:
                if (globalUtils.isNetworkAvailable(this)){
                    String useremail=email.getText().toString();
                    forgotPresentarInteractor.validateRequest(useremail);
                }else {
                    globalUtils.networkCheckerDialog(this);
                }

                break;
        }
    }
}
