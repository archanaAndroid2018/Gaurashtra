package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.resetbean.ResetResponseDTO;
import com.gaurashtra.app.presentator.ChangePasswordPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.ChangePasswordInteractor;
import com.gaurashtra.app.view.activity.interactor.ChangePasswordActivityInteractor;

public class ChangePassActivity extends AppCompatActivity implements ChangePasswordActivityInteractor, View.OnClickListener{
    private TextView textviewTitle;
    private EditText oldPassword,newPassword,confPassword;
    private LinearLayout changePassword;
    private GlobalUtils globalUtils;
    private PrefClass prefClass;
    private ChangePasswordInteractor changePasswordInteractor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Change Password");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        changePasswordInteractor = new ChangePasswordPresentar(this);
        globalUtils = new GlobalUtils();
        prefClass = new PrefClass(this);
        setUpContentView();
        setOnClickListner();
    }

    public void setUpContentView(){
        oldPassword = findViewById(R.id.etOldPassword);
        newPassword = findViewById(R.id.etChangePassword);
        confPassword = findViewById(R.id.etConfChangePass);
        changePassword = findViewById(R.id.llChangePass);
    }
    public void setOnClickListner(){
        changePassword.setOnClickListener(this);
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
        ResetResponseDTO resetResponseDTO = (ResetResponseDTO)object;
        String message = resetResponseDTO.getMessage();
        GlobalUtils.showToast(this,message);
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.llChangePass:
                 if (globalUtils.isNetworkAvailable(this)){
                      changePasswordInteractor.validateRequest(prefClass.getCustomerId(),oldPassword.getText().toString(),newPassword.getText().toString(),confPassword.getText().toString());
                 }else {
                     globalUtils.networkCheckerDialog(this);
                 }
                break;
        }
    }
}
