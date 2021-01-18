package com.gaurashtra.app.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.loginbean.LoginResponseDTO;
import com.gaurashtra.app.model.bean.loginbean.SocialLoginResponseDTO;
import com.gaurashtra.app.presentator.LoginPresentator;
import com.gaurashtra.app.presentator.presentarInteractor.LoginPresentatorInteractor;
import com.gaurashtra.app.view.activity.interactor.LoginActivityInteractor;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginActivityInteractor, View.OnClickListener {
    private static final int RC_SIGN_IN = 100;
    private TextView signUp, forgot;
    private LinearLayout signBtn;
    private GlobalUtils globalUtils;
    private PrefClass prefClass;
    private EditText userId, password, etPhone;
    private String deviceId = "";
    private SharedPreference sharedPreference;
    CallbackManager callbackManager;
    private LoginPresentatorInteractor loginPresentatorInteractor;
    LoginButton loginButton;
    Profile profile;
    String id, name, email;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    SignInButton signInButton;
    String firstName, lastName = " ", strPhone, BackFromClass = "";
    TextView tvError;
    Boolean loginFromActivity = false;
    CheckBox cbRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresentatorInteractor = new LoginPresentator(this);
        globalUtils = new GlobalUtils();
        prefClass = new PrefClass(this);
        BackFromClass = getIntent().getStringExtra(Constants.PreferenceConstants.BACKFROM);

        setUpContentView();
        setOnClickListner();

        try {
            if (!SharedPreference.getDeviceId(LoginActivity.this).isEmpty()) {
                deviceId = SharedPreference.getDeviceId(LoginActivity.this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//@@@@@@@@@@        Google signIn
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
        Log.e("SignInData", ":" + account);
//@@@@@@@@@       FaceBook Callback

        callbackManager = CallbackManager.Factory.create();
        Log.e("Profile", ":" + profile);
        try {
            if (!BackFromClass.isEmpty()) {
                loginFromActivity = true;
            } else {
                loginFromActivity = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpContentView() {
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setHeight(40);
        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signUp = findViewById(R.id.tvreg);
        forgot = findViewById(R.id.tvforgot);
        signBtn = findViewById(R.id.llsignbtn);
        userId = findViewById(R.id.etUserId);
        password = findViewById(R.id.etUserPass);
        cbRememberMe= findViewById(R.id.cb_remember_me);
        if(SharedPreference.getIsRememberMe(LoginActivity.this)) {
            if (prefClass.getUserMail() != "") {
                userId.setText(prefClass.getUserMail());
                cbRememberMe.setChecked(true);
            }
        }
    }

    private void setOnClickListner() {
        signUp.setOnClickListener(this);
        forgot.setOnClickListener(this);
        signBtn.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        signInButton.setOnClickListener(this);
        cbRememberMe.setOnCheckedChangeListener(checkListner);
    }

    CompoundButton.OnCheckedChangeListener checkListner= new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            Log.e("IsRememberMe",": "+b);
            if(b){
                SharedPreference.setIsRememberMe(LoginActivity.this,b);
            }else{
                SharedPreference.setIsRememberMe(LoginActivity.this,b);
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent data) {
        callbackManager.onActivityResult(requestCode, responseCode, data);
        super.onActivityResult(requestCode, responseCode, data);
        if (requestCode == RC_SIGN_IN) {
            Log.e("OnActGoogle", "" + data + "\n reqcode:" + requestCode + "\n resCode:" + responseCode);
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else if (data.hasExtra(Constants.PreferenceConstants.BACKFROM)) {
            Log.e("OnActBAck", "" + data + "\n reqcode:" + requestCode + "\n resCode:" + responseCode);
            loginFromActivity = true;
            BackFromClass = data.getStringExtra(Constants.PreferenceConstants.BACKFROM);
        } else {
            Log.e("OnActFb", "" + data + "\n reqcode:" + requestCode + "\n resCode:" + responseCode);
//            if (responseCode == -1) {
//                if (GlobalUtils.isNetworkAvailable(LoginActivity.this)) {
//                    socialLoginMethod();
//                } else {
//                    Toast.makeText(LoginActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//                }
//            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
//@@@@@@@@@@@@@@ Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.e("Google Account", ":" + account.getEmail() + "\n name:" + account.getGivenName()+"\n lname: "+account.getFamilyName());
            String fullname[] = account.getDisplayName().split("\\s");
            if (fullname.length > 0) {
                firstName = fullname[0];
            } else if (fullname.length > 1) {
                lastName = fullname[fullname.length - 1];
            }
            firstName= account.getGivenName();
            lastName= account.getFamilyName();
//            if(lastName.isEmpty()){
//                lastName= account.getFamilyName();
//            }
            name= firstName+" "+lastName;
            email = account.getEmail();
            if (GlobalUtils.isNetworkAvailable(LoginActivity.this)) {
                socialLoginMethod();
            } else {
                Toast.makeText(LoginActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LoginExc", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void openContactAddPopup() {
        try {
            final Dialog dialog = new Dialog(LoginActivity.this);
            dialog.setContentView(R.layout.conatct_no_layout);
            dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
            TextView btnOk = dialog.findViewById(R.id.btn_ok);
            tvError = dialog.findViewById(R.id.tv_error_phone);
            etPhone = dialog.findViewById(R.id.et_phone);
            etPhone.addTextChangedListener(textWatcher);
            strPhone = etPhone.getText().toString().trim();
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etPhone.getText().toString().trim().isEmpty()) {
                        tvError.setText("Please enter a valid phone number!");
                    } else if (SharedPreference.getLocale(LoginActivity.this).equalsIgnoreCase("INDIA")
                            && etPhone.getText().toString().trim().length() > 10) {
                        tvError.setText("Phone number should not be more than 10 digits!");
                    } else if (SharedPreference.getLocale(LoginActivity.this).equalsIgnoreCase("INDIA")
                            && etPhone.getText().toString().trim().length() < 10) {
                        tvError.setText("Phone number should not be less than 10 digits!");
                    } else if (SharedPreference.getLocale(LoginActivity.this).equalsIgnoreCase("OTHER")
                            && etPhone.getText().toString().trim().length() < 6) {
                        tvError.setText("Please eneter a valid phone number!");
                    } else {
                        dialog.dismiss();
                        GlobalUtils.showdialog(LoginActivity.this);
                        if (GlobalUtils.isNetworkAvailable(LoginActivity.this)) {
                            addUserPhone();
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addUserPhone() {
        HashMap<String, String> map = new HashMap<>();
        map.put("userid", SharedPreference.getUserid(LoginActivity.this));
        map.put("telephone", etPhone.getText().toString().trim());
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<SocialLoginResponseDTO> call = restInterface.addPhoneNumber(globalUtils.getKey(), globalUtils.getapidate(), map);
        call.enqueue(new Callback<SocialLoginResponseDTO>() {
            @Override
            public void onResponse(Call<SocialLoginResponseDTO> call, Response<SocialLoginResponseDTO> response) {
                try {
                    GlobalUtils.hidedialog();
                    if (response.body().getSuccess() == 1) {
                        GlobalUtils.showToast(LoginActivity.this, response.body().getMessage() + "..");
                        String userId = response.body().getResult().getUserData().getCustomerId();
                        String userEmail = response.body().getResult().getUserData().getEmail();
                        String password = response.body().getResult().getUserData().getPassword();
                        String firstname = response.body().getResult().getUserData().getFirstname();
                        String lastName = response.body().getResult().getUserData().getLastname();
                        String defaultAddId = response.body().getResult().getUserData().getAddressId();
                        String phone = response.body().getResult().getUserData().getTelephone();
                        SharedPreference.setUserId(LoginActivity.this, userId);
                        SharedPreference.setSocialEmail(LoginActivity.this, userEmail);
                        SharedPreference.setUserPassword(LoginActivity.this, password);
                        SharedPreference.setFirstName(LoginActivity.this, firstname);
                        SharedPreference.setLastName(LoginActivity.this, lastName);
                        SharedPreference.setAddressId(LoginActivity.this, defaultAddId);
                        SharedPreference.setUserPhone(LoginActivity.this, phone);
                        Log.e("UserId", "" + prefClass.getCustomerId());
                        if (firstname.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || SharedPreference.getUserPhone(LoginActivity.this).isEmpty()) {
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } else {
                            if (!loginFromActivity) {
                                ;
                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
//                        finish();
                                Intent intent5 = new Intent(LoginActivity.this, HomeActivity.class);
                                intent5.putExtra(Constants.PreferenceConstants.BACKFROM, LoginActivity.class.getSimpleName());
                                startActivityForResult(intent5, Constants.PreferenceConstants.BACKFROMHOME);
                            }
                        }
                    } else {
                        GlobalUtils.showToast(LoginActivity.this, response.body().getMessage());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<SocialLoginResponseDTO> call, Throwable t) {
                GlobalUtils.hidedialog();
                Log.e("PhoneVerifExc", ":" + t);
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            tvError.setText("");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvreg:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tvforgot:
                startActivity(new Intent(this, ForgotActivity.class));
                break;
            case R.id.llsignbtn:
                if (globalUtils.isNetworkAvailable(this)) {
                    if (deviceId.length() != 0) {
                        loginPresentatorInteractor.validateRequest(userId.getText().toString(), password.getText().toString(), deviceId);
                    } else {
                        globalUtils.showToast(this, "Your internet connection is slow!");
                    }
                } else {
                    globalUtils.networkCheckerDialog(this);
                }

                break;
            case R.id.login_button:
                Log.e("Login", "FbLogin");
                if (profile != null) {
                    // user has logged in
                    LoginManager.getInstance().logOut();
                } else {
                    // user has not logged in
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,
                            Arrays.asList("public_profile", "email"));
                    initFacebookLogin();
                }
                break;

            case R.id.sign_in_button:
                Log.e("Login", "GPlus");
                signIn();
                break;
        }
    }

    private void initFacebookLogin() {
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        System.out.println("onSuccess");

                        String accessToken = loginResult.getAccessToken()
                                .getToken();
                        Log.e("accessToken", accessToken);
                        profile = Profile.getCurrentProfile().getCurrentProfile();

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,
                                                            GraphResponse response) {

                                        Log.e("FbResponse", response.toString()+"\n fbObject: "+object);
                                        try {
                                            name = object.getString("name");
                                            String fname[]= name.split("\\s");
                                            email = response.getJSONObject().getString("email");
//                                            firstName = response.getJSONObject().getString("first_name");
//                                            lastName = response.getJSONObject().getString("last_name");
//                                            if(firstName ==null || lastName == null){
                                                firstName= fname[0];
                                                lastName= fname[fname.length-1];
//                                            }

                                            String gender = response.getJSONObject().getString("gender");

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        if (GlobalUtils.isNetworkAvailable(LoginActivity.this)) {
                                            socialLoginMethod();
                                        } else {
                                            Toast.makeText(LoginActivity.this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        System.out.println("onCancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("onError");
                        Log.e("LoginActivity", exception.getCause().toString());
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void validateCredential(String msg) {
        globalUtils.showToast(this, msg);
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
        globalUtils.hidedialog();
        LoginResponseDTO loginResponseDTO = (LoginResponseDTO) object;
        String msg = loginResponseDTO.getMessage();
        int success = loginResponseDTO.getSuccess();
        if (success == 1) {
            GlobalUtils.showToast(this, msg);
            String userId = loginResponseDTO.getResult().getUserData().getCustomerId();
            String userEmail = loginResponseDTO.getResult().getUserData().getEmail();
            String password = loginResponseDTO.getResult().getUserData().getPassword();
            String firstname = loginResponseDTO.getResult().getUserData().getFirstname();
            String lastName = loginResponseDTO.getResult().getUserData().getLastname();
            String defaultAddId = loginResponseDTO.getResult().getUserData().getAddressId();
            String phone = loginResponseDTO.getResult().getUserData().getTelephone();
            prefClass.setCustomerId(userId);
            prefClass.setUserEmail(userEmail);
            SharedPreference.setUserId(this, userId);
            SharedPreference.setUserEmail(LoginActivity.this, userEmail);
            SharedPreference.setUserPassword(LoginActivity.this, password);
            SharedPreference.setFirstName(LoginActivity.this, firstname);
            SharedPreference.setLastName(LoginActivity.this, lastName);
            SharedPreference.setAddressId(LoginActivity.this, defaultAddId);
            SharedPreference.setUserPhone(LoginActivity.this, phone);
            Log.e("UserId", "" + prefClass.getCustomerId());
            if (firstname.isEmpty() || lastName.isEmpty() || userEmail.isEmpty() || phone.isEmpty() || SharedPreference.getUserPhone(LoginActivity.this).isEmpty()) {
                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                startActivity(intent);
            } else{
            if (!loginFromActivity) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
//             finishActivity(Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                finish();
            }
        }
        } else {
            GlobalUtils.showToast(this, msg);
        }

    }



    public void socialLoginMethod() {
        HashMap<String, String> map = new HashMap<>();
        map.put("firstname", firstName);
        map.put("lastname", lastName);
        map.put("email", email);
        map.put("ipaddress", SharedPreference.getIpAddress(LoginActivity.this));
        map.put("devicetype", "Android");
        map.put("deviceid", deviceId);
        Log.e("SocialMap",":"+map);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<SocialLoginResponseDTO> call = restInterface.addSocialLogin(globalUtils.getKey(), globalUtils.getapidate(), map);
        call.enqueue(new Callback<SocialLoginResponseDTO>() {
            @Override
            public void onResponse(Call<SocialLoginResponseDTO> call, Response<SocialLoginResponseDTO> response) {
                Log.e("SocialResp",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response.body().getSuccess() == 1) {
                    GlobalUtils.showToast(LoginActivity.this, response.body().getMessage());
                    String userId = response.body().getResult().getUserData().getCustomerId();
                    String userEmail = response.body().getResult().getUserData().getEmail();
                    String password = response.body().getResult().getUserData().getPassword();
                    String firstname = response.body().getResult().getUserData().getFirstname();
                    String lastName = response.body().getResult().getUserData().getLastname();
                    String defaultAddId = response.body().getResult().getUserData().getAddressId();
                    String phone = response.body().getResult().getUserData().getTelephone();
//                    String phone="";
                        prefClass.setCustomerId(userId);
                        prefClass.setUserEmail(userEmail);
                    SharedPreference.setUserId(LoginActivity.this, userId);
                    SharedPreference.setSocialEmail(LoginActivity.this, userEmail);
                    SharedPreference.setUserPassword(LoginActivity.this, password);
                    SharedPreference.setFirstName(LoginActivity.this, firstname);
                    SharedPreference.setLastName(LoginActivity.this, lastName);
                    SharedPreference.setAddressId(LoginActivity.this, defaultAddId);
                    SharedPreference.setUserPhone(LoginActivity.this, phone);
                    Log.e("UserId", "" + prefClass.getCustomerId()+"\n Phone:"+phone);
                    if(firstname.isEmpty() || lastName.isEmpty() || userEmail.isEmpty() || phone.isEmpty()|| SharedPreference.getUserPhone(LoginActivity.this).isEmpty()){
                        Intent intent= new Intent(LoginActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    }else {
                        if (!loginFromActivity) {
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
//                            Toast.makeText(LoginActivity.this, "Back To ProductDetails!", Toast.LENGTH_SHORT).show();
//                            finishActivity(Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                            finish();
                        }
                    }
                } else if (response.body().getSuccess() == 2) {
                    String userId = response.body().getResult().getUserData().getCustomerId();
                    SharedPreference.setUserId(LoginActivity.this, userId);
                    openContactAddPopup();
                } else {
                    GlobalUtils.showToast(LoginActivity.this, response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<SocialLoginResponseDTO> call, Throwable t) {
                Log.e("SocialExc", ":" + t);
            }
        });
    }
}