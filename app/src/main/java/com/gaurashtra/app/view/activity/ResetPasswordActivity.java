package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.resetbean.ResetResponseDTO;
import com.gaurashtra.app.presentator.ResetPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.ResetPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ResetActivityInteractor;

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener, ResetActivityInteractor {
    private EditText otp,newPassword,confirmPassword;
    private LinearLayout submit;
    private ResetPresentarInteractor resetPresentarInteractor;
    private GlobalUtils globalUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        resetPresentarInteractor = new ResetPresentar(this);
        globalUtils = new GlobalUtils();
        setUpContentView();
        setOnClickListner();
    }

    public void setUpContentView(){
        otp = findViewById(R.id.etOTP);
        newPassword = findViewById(R.id.etNewPassword);
        confirmPassword = findViewById(R.id.etConfirmPassword);
        submit  = findViewById(R.id.llSubmit);
    }
    public void setOnClickListner(){
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llSubmit:
                if (globalUtils.isNetworkAvailable(this)){
                    resetPresentarInteractor.validateRequest(otp.getText().toString(),newPassword.getText().toString(),confirmPassword.getText().toString());
                }else {
                    globalUtils.networkCheckerDialog(this);
                }
                 break;
        }
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
    public void validationResponse(String msg) {
        globalUtils.showToast(this,msg);
    }

    @Override
    public void getResponse(Object object) {
        globalUtils.hidedialog();
        ResetResponseDTO resetResponseDTO = (ResetResponseDTO) object;
        String massage = resetResponseDTO.getMessage();
        globalUtils.showToast(this,massage);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
