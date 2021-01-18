package com.gaurashtra.app.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.addresslistbean.AddressResponseDTO;
import com.gaurashtra.app.model.bean.addresslistbean.UserAddressDatum;
import com.gaurashtra.app.model.bean.loginbean.LoginResponseDTO;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private TextView textviewTitle, tvAddress, btnUpdateProfile;
    EditText etFirstName, etLastName, etEmail, etPhone;
    TextView tvFnameError, tvLnameError, tvEmailError, tvPhoneError;
    String firstName, lastName, emailId, phoneNo, address;
    GlobalUtils globalUtils;
    PrefClass prefClass;
    LinearLayout noAddressLayout;
    TextView btnAddress;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        globalUtils = new GlobalUtils();
        prefClass = new PrefClass(this);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("My Profile");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        UserUpdate userUpdate= new UserUpdate(ProfileActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(ProfileActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(ProfileActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        tvFnameError = findViewById(R.id.tv_fname_error);
        tvLnameError = findViewById(R.id.tv_lname_error);
        tvEmailError = findViewById(R.id.tv_email_error);
        tvPhoneError = findViewById(R.id.tv_phone_error);

        etFirstName = findViewById(R.id.et_profile_fname);
        etFirstName.addTextChangedListener(textWatcher);

        etLastName = findViewById(R.id.et_profile_lname);
        etLastName.addTextChangedListener(textWatcher);

        etEmail = findViewById(R.id.et_profile_email);
        etEmail.addTextChangedListener(textWatcher);

        etPhone = findViewById(R.id.et_profile_phone);
        etPhone.addTextChangedListener(textWatcher);

        tvAddress = findViewById(R.id.tv_profile_address);
        btnAddress= findViewById(R.id.tv_btn_address);

        noAddressLayout= findViewById(R.id.ll_no_address);
        btnUpdateProfile = findViewById(R.id.tv_btn_profile_update);
        setTextInViews();
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ProfileActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setTextInViews() {
        if(!SharedPreference.getFirstName(this).isEmpty()) {
            firstName = SharedPreference.getFirstName(this);
        }else{
            tvFnameError.setVisibility(View.VISIBLE);
        }
        if(!SharedPreference.getLastName(this).isEmpty()) {
            lastName = SharedPreference.getLastName(this);
        }else{
            tvLnameError.setVisibility(View.VISIBLE);
        }
        if(!SharedPreference.getUserEmail(this).isEmpty()) {
            emailId = SharedPreference.getUserEmail(this);
        }else if(!SharedPreference.getSocialEmail(this).isEmpty()){
            emailId = SharedPreference.getSocialEmail(this);
        }else{
            tvEmailError.setVisibility(View.VISIBLE);
        }
        if(!SharedPreference.getUserPhone(this).isEmpty()) {
            phoneNo = SharedPreference.getUserPhone(this);
        }else{
            tvPhoneError.setVisibility(View.VISIBLE);
        }

        etFirstName.setText(firstName);
        etLastName.setText(lastName);
        etEmail.setText(emailId);
        etPhone.setText(phoneNo);

        getDefaultAddress();
    }

    private void getDefaultAddress() {
        HashMap<String,String> params= new HashMap<>();
        params.put("userid", SharedPreference.getUserid(ProfileActivity.this));
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<AddressResponseDTO> call = restInterface.callAddressListService(globalUtils.getKey(), globalUtils.getapidate(), params);
        call.enqueue(new Callback<AddressResponseDTO>() {
            @Override
            public void onResponse(Call<AddressResponseDTO> call, Response<AddressResponseDTO> response) {
                try{
                    Log.e("AddressResProfile",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if(response.body().getSuccess()==1) {
                        List<UserAddressDatum> addressList = new ArrayList<>();
                        addressList = response.body().getResult().getUserAddressData();
                        for(int i=0; i< addressList.size(); i++){
                             if(addressList.get(i).getAddressId().equalsIgnoreCase(addressList.get(i).getDefaultAddressId())){
                                 String add1 = addressList.get(i).getAddress1();
                                 String add2 = addressList.get(i).getAddress2();
                                 String city = addressList.get(i).getCity();
                                 String state = addressList.get(i).getZoneName();
                                 String zipcode = addressList.get(i).getPostcode();
                                 String country = addressList.get(i).getCountryName();

                                 SharedPreference.setUserAddress1(ProfileActivity.this, add1);
                                 SharedPreference.setUserAddress2(ProfileActivity.this, add2);
                                 SharedPreference.setUserCity(ProfileActivity.this, city);
                                 SharedPreference.setdefAddressPhone(ProfileActivity.this, addressList.get(i).getCustomField());
                                 SharedPreference.setUserZipcode(ProfileActivity.this, zipcode);
                                 SharedPreference.setAddressId(ProfileActivity.this, addressList.get(i).getAddressId());
                                 SharedPreference.setDefAddressId(ProfileActivity.this, addressList.get(i).getDefaultAddressId());
                                 SharedPreference.setUserCountry(ProfileActivity.this, country);
                                 SharedPreference.setUserCountryId(ProfileActivity.this, addressList.get(i).getCountryId());
                                 SharedPreference.setUserState(ProfileActivity.this, state);
                                 SharedPreference.setUserStateId(ProfileActivity.this, addressList.get(i).getZoneId());
                                 address = add1 + ", " + add2 + ", " + city + ", " + state + "- " + zipcode + ", " + country;
                                 if (add1.isEmpty() && add2.isEmpty() && city.isEmpty() && state.isEmpty() && zipcode.isEmpty() && country.isEmpty()) {
                                     noAddressLayout.setVisibility(View.VISIBLE);
                                     tvAddress.setVisibility(View.GONE);
                                 } else {
                                     noAddressLayout.setVisibility(View.GONE);
                                     tvAddress.setVisibility(View.VISIBLE);
                                     tvAddress.setText(address);
                                 }
                                 break;
                             }
                        }

                    }else{
                        noAddressLayout.setVisibility(View.GONE);
                        tvAddress.setVisibility(View.VISIBLE);
                        tvAddress.setText(address);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<AddressResponseDTO> call, Throwable t) {

            }
        });
    }


    private void validateData() {
        String newFname = etFirstName.getText().toString().trim();
        String newLname = etLastName.getText().toString().trim();
        String newEmail = etEmail.getText().toString().trim();
        String newPhone = etPhone.getText().toString().trim();
        if (newFname.equalsIgnoreCase(firstName) && newLname.equalsIgnoreCase(lastName) &&
                newEmail.equalsIgnoreCase(emailId) && newPhone.equalsIgnoreCase(phoneNo)) {
            Toast.makeText(this, "you haven't made any changes!", Toast.LENGTH_SHORT).show();
        } else {
            if(newFname.isEmpty()){
                Toast.makeText(this, "First name should not be empty!", Toast.LENGTH_SHORT).show();
            }else if(newLname.isEmpty()){
                Toast.makeText(this, "Last name should not be empty!", Toast.LENGTH_SHORT).show();
            }else if(newEmail.isEmpty()){
                Toast.makeText(this, "Email id should not be empty!", Toast.LENGTH_SHORT).show();
            }else if(!globalUtils.isEmailValidate(newEmail)) {
                Toast.makeText(this, "Please enter a valid email id!", Toast.LENGTH_SHORT).show();
            }else if(newPhone.isEmpty()){
                Toast.makeText(this, "Phone number should not be empty!", Toast.LENGTH_SHORT).show();
            }else if(newPhone.trim().length()<10){
                Toast.makeText(this, "Please enter a valid phone number!", Toast.LENGTH_SHORT).show();
            }else {
                progressDialog = new ProgressDialog(ProfileActivity.this);
                progressDialog.setTitle("Saving changes...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                updateProfile(newFname, newLname, newEmail, newPhone);
            }
        }
    }

    private void updateProfile(String newFname, String newLname, String newEmail, String newPhone) {
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", SharedPreference.getUserid(this));
        param.put("firstname", newFname);
        param.put("lastname", newLname);
        param.put("telephone", newPhone);
        param.put("email", newEmail);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<LoginResponseDTO> call = restInterface.updateUserProfile(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<LoginResponseDTO>() {
            @Override
            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    LoginResponseDTO loginResponseDTO = response.body();
                    String msg = loginResponseDTO.getMessage();
                    int success = loginResponseDTO.getSuccess();
                    if (success == 1) {
                        GlobalUtils.showToast(ProfileActivity.this, msg);
                        String userId = loginResponseDTO.getResult().getUserData().getCustomerId();
                        String userEmail = loginResponseDTO.getResult().getUserData().getEmail();
                        String password = loginResponseDTO.getResult().getUserData().getPassword();
                        String firstname = loginResponseDTO.getResult().getUserData().getFirstname();
                        String lastName = loginResponseDTO.getResult().getUserData().getLastname();
                        String defaultAddId = loginResponseDTO.getResult().getUserData().getAddressId();
                        String phoneNo = loginResponseDTO.getResult().getUserData().getTelephone();
                        prefClass.setCustomerId(userId);
                        prefClass.setUserEmail(userEmail);
                        SharedPreference.setUserId(ProfileActivity.this, userId);
                        SharedPreference.setUserEmail(ProfileActivity.this, userEmail);
                        SharedPreference.setUserPassword(ProfileActivity.this, password);
                        SharedPreference.setFirstName(ProfileActivity.this, firstname);
                        SharedPreference.setLastName(ProfileActivity.this, lastName);
                        SharedPreference.setAddressId(ProfileActivity.this, defaultAddId);
                        SharedPreference.setUserPhone(ProfileActivity.this, phoneNo);
                        Log.e("UserId", "" + prefClass.getCustomerId());
                        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        progressDialog.dismiss();
                        GlobalUtils.showToast(ProfileActivity.this, msg);
                    }
                } else {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {

            }
        });
    }

        TextWatcher textWatcher= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
             if(etFirstName.hasFocus()){
                 tvFnameError.setVisibility(View.GONE);
             }else if(etLastName.hasFocus()){
                 tvLnameError.setVisibility(View.GONE);
             }else if(etEmail.hasFocus()){
                 tvEmailError.setVisibility(View.GONE);
             }else if(etPhone.hasFocus()){
                 tvPhoneError.setVisibility(View.GONE);
             }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        String fn=etFirstName.getText().toString().trim();
        String ln=etLastName.getText().toString().trim();
        String em=etEmail.getText().toString().trim();
        String ph=etPhone.getText().toString().trim();

        if(fn.isEmpty() || ln.isEmpty() || em.isEmpty() || ph.isEmpty()) {
            if (fn.isEmpty()) {
                tvFnameError.setVisibility(View.VISIBLE);
            }
            if (ln.isEmpty()) {
                tvLnameError.setVisibility(View.VISIBLE);
            }
            if (em.isEmpty()) {
                tvEmailError.setVisibility(View.VISIBLE);
            }
            if (ph.isEmpty()) {
                tvPhoneError.setVisibility(View.VISIBLE);
            }
        }else {
            Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
            intent.putExtra("BACKFROM", "ME");
            startActivity(intent);
            super.onBackPressed();
        }
    }
}
