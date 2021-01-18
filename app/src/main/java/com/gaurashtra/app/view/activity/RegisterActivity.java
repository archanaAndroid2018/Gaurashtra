package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.ArrowKeyMovementMethod;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.bean.Registerbean.RegistrationResponseDTO;
import com.gaurashtra.app.presentator.RegisterPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.RegisterPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.RegisterActivityInteractor;
import com.gaurashtra.app.view.fragment.leftmenufrag.PrivacyPolicyFrag;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, RegisterActivityInteractor {
    private RegisterPresentarInteractor registerPresentarInteractor;
    private TextView signIn, tvTermsCond;
    String strTermsCond;
    private EditText name, phone, email, pass, confPass,lastName;
    private LinearLayout signUp,bottomLayout;
    private GlobalUtils globalUtils;
    String deviceId = "adfjldffj", deviceType="Android", ipAddress="";
    Boolean shouldHilightWord=false, shouldHilightWord2=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        try {
            if (!SharedPreference.getDeviceId(RegisterActivity.this).isEmpty()) {
                deviceId= SharedPreference.getDeviceId(RegisterActivity.this);
            }
            if(!SharedPreference.getIpAddress(RegisterActivity.this).isEmpty()){
                ipAddress= SharedPreference.getIpAddress(RegisterActivity.this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        registerPresentarInteractor = new RegisterPresentar(this);
        globalUtils = new GlobalUtils();
        setUpContentView();
        setOnClickListner();
        final View activityRootView = findViewById(R.id.main_layout);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                //r will be populated with the coordinates of your view that area still visible.
                activityRootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                  bottomLayout.setVisibility(View.GONE);
                }else{
                    bottomLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        strTermsCond= "By signing up, you agree to our "+"\n"+"Terms of Service and Privacy Policy.";

        SpannableString spanText1= new SpannableString(strTermsCond);
        final ClickableSpan clickableSpan1= new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                loadFragment(new PrivacyPolicyFrag());
//                Toast.makeText(RegisterActivity.this, "Terms", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.bgColor = Color.WHITE;
                ds.setARGB(255, 255, 255, 255);
                ds.setColor(getResources().getColor(R.color.blue));
                ds.setUnderlineText(false);
            }
        };

        ClickableSpan clickableSpan2= new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                loadFragment(new PrivacyPolicyFrag());
//                Toast.makeText(RegisterActivity.this, "Privacy", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(ds.linkColor);
                ds.bgColor = Color.WHITE;
                ds.setARGB(255, 255, 255, 255);
                ds.setColor(getResources().getColor(R.color.blue));
                ds.setUnderlineText(false);
            }
        };

        spanText1.setSpan(clickableSpan1,33,49, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanText1.setSpan(clickableSpan2,53,68, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvTermsCond.setText(spanText1);
        tvTermsCond.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_frag_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void setUpContentView() {
        signIn = findViewById(R.id.tvlogin);
        signUp = findViewById(R.id.llSignUp);
        name = findViewById(R.id.etName);
        lastName = findViewById(R.id.etLastName);
        phone = findViewById(R.id.etphone);
        email = findViewById(R.id.etEmail);
        pass = findViewById(R.id.etPass);
        confPass = findViewById(R.id.etConfirmPass);
        tvTermsCond= findViewById(R.id.tv_terms_cond);
        bottomLayout= findViewById(R.id.llbottom);
    }

    private void setOnClickListner() {
        signIn.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvlogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.llSignUp:
                if (globalUtils.isNetworkAvailable(this)){
                    registerPresentarInteractor.sendRequest(name.getText().toString(),lastName.getText().toString(),phone.getText().toString(),email.getText().toString(),pass.getText().toString(),confPass.getText().toString(), deviceType ,deviceId,ipAddress);
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
    public void validateCredentials(String msg) {
        globalUtils.showToast(this,msg);
    }

    @Override
    public void getResponse(Object object) {
        RegistrationResponseDTO registrationResponseDTO = (RegistrationResponseDTO)object;
        String msg = registrationResponseDTO.getMessage();
        globalUtils.showToast(this,msg);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
