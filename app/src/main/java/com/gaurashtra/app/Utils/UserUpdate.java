package com.gaurashtra.app.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.gaurashtra.app.model.bean.UserUpdateModel;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserUpdate extends Activity {
    Context context;
    String userId="";
    GlobalUtils globalUtils;
    Profile profile;
    Boolean isGoogleSignIn = false;
    public Boolean isUserAvailable=true;
    GoogleSignInClient mGoogleSignInClient;


    public UserUpdate(Activity activity) {
        this.context= activity;
        globalUtils= new GlobalUtils();
        profile = Profile.getCurrentProfile().getCurrentProfile();
        if(!SharedPreference.getUserid(context).isEmpty()) {
            userId = SharedPreference.getUserid(context);
        }
//        if(GlobalUtils.isNetworkAvailable(UserUpdate.this)) {
            isUserAvailable = isUserRegistered();
//        }
    }
    public Boolean isUserRegistered(){
        try {
            if (!userId.isEmpty()) {
                RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
                Call<UserUpdateModel> call = restInterface.getUserStatus(globalUtils.getKey(), globalUtils.getapidate(), userId);
                call.enqueue(new Callback<UserUpdateModel>() {
                    @Override
                    public void onResponse(Call<UserUpdateModel> call, Response<UserUpdateModel> response) {
                        if (response != null) {
                            Log.e("USERSTATUS", "request:" + response.body().getMessage());
                            try {
                                if (response.body().getSuccess() == 1) {
                                    isUserAvailable = true;
                                } else if (response.body().getSuccess() == 10) {
                                    isUserAvailable = false;
//                        Toast.makeText(UserUpdate.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    if (profile != null) {
                                        LoginManager.getInstance().logOut();
                                    }
                                    if (signInSilently()) {
                                        mGoogleSignInClient.signOut();
                                    }
                                    SharedPreference.setUserId(context, null);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<UserUpdateModel> call, Throwable t) {
                        Log.e("UserStatusExc", ":" + t);
                    }
                });
            } else {
                isUserAvailable = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUserAvailable;
    }
    public boolean signInSilently() {

        mGoogleSignInClient = GoogleSignIn.getClient(context,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        mGoogleSignInClient.silentSignIn().addOnCompleteListener((Activity) context,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            try {
                                GoogleSignInAccount mAccount = task.getResult(ApiException.class);
                                if(mAccount != null){
                                    isGoogleSignIn=true;
                                }
                            } catch (ApiException e) {
                                Log.w("UserUpdate", "SignInResult::Failed code="
                                        + e.getStatusCode() + ", Message: "
                                        + e.getStatusMessage());
                            }
                        } else {
                            // Player will need to sign-in explicitly using via UI
                            Log.d("UserUpdate", "Silent::Login::Failed");
                        }
                    }
                });
        return isGoogleSignIn;
    }
}
