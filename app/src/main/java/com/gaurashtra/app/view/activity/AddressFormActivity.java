package com.gaurashtra.app.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddAddressBean.AddAddressResponseDTO;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.addresslistbean.AddressResponseDTO;
import com.gaurashtra.app.model.bean.addresslistbean.UserAddressDatum;
import com.gaurashtra.app.model.bean.countrybean.CountryDatum;
import com.gaurashtra.app.model.bean.countrybean.CountryResponseDTO;
import com.gaurashtra.app.model.modelInteractor.StateModel;
import com.gaurashtra.app.presentator.AddressPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.AddressPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.AddressActivityInteractor;
import com.gaurashtra.app.view.adapter.SelectAddressAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AddressFormActivity extends AppCompatActivity implements View.OnClickListener, AddressActivityInteractor {
    private LinearLayout saveButton, addAddressTitle, editAddressTitle;
    private Spinner countrySpin, stateSpin;
    private EditText firstName, lastName, mobile, address1, address2, city, pinCode, etCompany;
    private SwitchCompat defaultAddress;
    ArrayList<String> countryList= new ArrayList<>();
    Boolean isStateSelected = false, isCountrySelected = false, isEditModeOn = false,isTouched = false, saveInPref=false;
    private String fName = "", lName = "", adr1 = "", adr2 = "", cityText = "", pinText = "", cmpnyText = "",UserID="",
            mobileText = "", dAddress = "Y", countryStr = "", stateStr = "", address_id = "", selectedCountryId, selectedStateId, selectedcurrency="";
    private PrefClass prefClass;
    private GlobalUtils globalUtils;
    List<CountryDatum> countryData = new ArrayList<>();
    List<String> stList;
    List<StateModel> zoneIdList;
    private SelectAddressAdapter addressAdapter;
    private AddressPresentarInteractor addressPresentarInteractor;
    private SharedPreference sharedPreference;
    List<String> countryIdList;
    List<UserAddressDatum> addressList;
    AddressResponseDTO addressResponseDTO;
    UserAddressDatum addressData;
//    AddressPresentarInteractor addressPresentarInteractor;

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_form);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("New Address");
        UserUpdate userUpdate= new UserUpdate(AddressFormActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(AddressFormActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(AddressFormActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        globalUtils= new GlobalUtils();
        UserID = SharedPreference.getUserid(this);
        prefClass= new PrefClass(this);
        selectedcurrency = prefClass.getSelectedCurrency();
        if(getIntent().hasExtra(Constants.PreferenceConstants.SELECTEDADDRESS)){
            isEditModeOn= true;
            textviewTitle.setText("Update Address");
            Bundle bundle = getIntent().getExtras();
            addressData= (UserAddressDatum)bundle.get(Constants.PreferenceConstants.SELECTEDADDRESS);
            Log.e("SelectedAddress"," Data: "+addressData);
            if(addressData != null) {
                fName = addressData.getFirstname();
                lName = addressData.getLastname();
                adr1 = addressData.getAddress1();
                adr2 = addressData.getAddress2();
                cityText = addressData.getCity();
                pinText = addressData.getPostcode();
                cmpnyText = addressData.getCompany();
                mobileText = addressData.getCustomField();
                address_id = addressData.getAddressId();
                selectedCountryId = addressData.getCountryId();
                selectedStateId = addressData.getZoneId();
            }
        }
        firstName = findViewById(R.id.etFirstName);
        lastName = findViewById(R.id.etLastName);
        mobile = findViewById(R.id.etMobile);
        address1 = findViewById(R.id.etAddress1);
        address2 = findViewById(R.id.etAddress2);
        city = findViewById(R.id.etCity);
        pinCode = findViewById(R.id.etPinCode);
        etCompany = findViewById(R.id.etCompanyName);
        saveButton = findViewById(R.id.llSaveButton);
        defaultAddress = findViewById(R.id.switch_item);
        countrySpin = findViewById(R.id.spinCountry);
        stateSpin = findViewById(R.id.spinState);
        addAddressTitle = findViewById(R.id.add_title_layout);
        editAddressTitle = findViewById(R.id.update_title_layout);
        setOnClickListner();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, countryList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpin.setAdapter(dataAdapter);

        ArrayList<String> stateList = new ArrayList<>();
        stateList.add("Select State");
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(AddressFormActivity.this, android.R.layout.simple_spinner_item, stateList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpin.setAdapter(dataAdapter1);
        prefClass = new PrefClass(this);
        addressPresentarInteractor = new AddressPresentar(this);
        if (GlobalUtils.isNetworkAvailable(this)) {
            addressPresentarInteractor.sendCountryRequest();
        } else {
            Toast.makeText(this, "Please check your network connectivity!", Toast.LENGTH_SHORT).show();
        }

        defaultAddress.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                isTouched = true;
                return false;
            }
        });

        defaultAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isTouched) {
                    isTouched = false;
                    if (isChecked) {
                        dAddress = "Y";
                        Log.e("defaultChecked-->", dAddress);
                    } else {
                        dAddress = "N";
                        Log.e("defaultUnChecked-->", dAddress);
                    }
                }
            }
        });
        countrySpin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                return false;
            }
        });

        countrySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {
                    countryStr = countryList.get(position);
                    selectedCountryId = countryData.get(position - 1).getCountryId();
                    getStateList(selectedCountryId);
                    Log.e("mCountry-->", countryStr);
                } else {
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        stateSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    stateStr = stList.get(position);
                    selectedStateId = zoneIdList.get(position - 1).getState_id();
                    Log.e("mState-->", stateStr);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(isEditModeOn) {
            setFormFields();
        }
//            Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
//            List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
//            for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
//                if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
//
//                }
//            }
    }

    private void setFormFields() {
        firstName.setText(fName);
        lastName.setText(lName);
        mobile.setText(mobileText);
        address1.setText(adr1);
        address2.setText(adr2);
        city.setText(cityText);
        pinCode.setText(pinText);
        etCompany.setText(cmpnyText);

        addAddressTitle.setVisibility(View.GONE);
        editAddressTitle.setVisibility(View.VISIBLE);
    }

    private void setOnClickListner() {
        saveButton.setOnClickListener(AddressFormActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llSaveButton:
                if(GlobalUtils.isNetworkAvailable(AddressFormActivity.this)){
                    GlobalUtils.showdialog(this);
                    validateAddress();

//                    addressPresentarInteractor.validateAddressData(address_id, UserID, firstName.getText().toString(),
//                            lastName.getText().toString(), address1.getText().toString(), address2.getText().toString(),
//                            city.getText().toString(), pinCode.getText().toString(), selectedCountryId, selectedStateId, isStateSelected, isCountrySelected, dAddress,
//                            etCompany.getText().toString(), mobile.getText().toString());
                    String requestData = String.format("1.%s\n2.%s\n3.%s\n4.%s\n5.%s\n6.%s\n7.%s\n8.%s\n9.%s\n10.%s\n11.%s\n12.%s\n13.%s\n14.%s",
                            address_id, UserID, firstName.getText().toString(), lastName.getText().toString(), address1.getText().toString(), address2.getText().toString(), city.getText().toString(),
                            pinCode.getText().toString(), selectedCountryId, selectedStateId, isStateSelected, isCountrySelected, dAddress,
                            etCompany.getText().toString(), mobile.getText().toString());
                    Log.e("AddressAdd", "" + requestData);
                }else{
                    Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void validateAddress() {
        isCountrySelected = false;
        isStateSelected=false;
        hideProgress();

        if(firstName.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "First name should not be empty!", Toast.LENGTH_SHORT).show();
        }else if(lastName.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Last name should not be empty!", Toast.LENGTH_SHORT).show();

        }else if(address1.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Address should not be empty!", Toast.LENGTH_SHORT).show();
//        }else if(address2.getText().toString().trim().isEmpty()){
//            Toast.makeText(this, "Alternate address should not be empty!", Toast.LENGTH_SHORT).show();

        }else if(city.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "City should not be empty!", Toast.LENGTH_SHORT).show();

        }else if(pinCode.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Pincode should not be empty!", Toast.LENGTH_SHORT).show();

        }else if(mobile.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Mobile number should not be empty!", Toast.LENGTH_SHORT).show();
        }else if(mobile.getText().toString().trim().length()<10){
            Toast.makeText(this, "Please enter a valid mobile number!!", Toast.LENGTH_SHORT).show();
        }else if(countrySpin.getSelectedItemPosition()==0) {
            isCountrySelected = true;
            Toast.makeText(this, "Please select a country!", Toast.LENGTH_SHORT).show();
        }else if(stateSpin.getSelectedItemPosition()==0){
            isStateSelected=true;
            Toast.makeText(this, "Please select a state!", Toast.LENGTH_SHORT).show();
        }else{
            ProgressDialog pd= new ProgressDialog(this);
            pd.setCanceledOnTouchOutside(false);
            pd.setIndeterminate(true);
            pd.setMessage("Address submitting...");
            pd.show();
            addEditAddress(address_id,firstName.getText().toString(),
                    lastName.getText().toString(), address1.getText().toString(), address2.getText().toString(),
                    city.getText().toString(), pinCode.getText().toString(), selectedCountryId, selectedStateId, dAddress,
                    etCompany.getText().toString(), mobile.getText().toString(), pd);
        }
    }

    private void addEditAddress(String address_id, String fName, String lName, String address1,
                                String address2, String city, String pinCode,
                                String selectedCountryId, String selectedStateId,
                                String defAddress, String cmpny, String mobile, final ProgressDialog pd) {
        Map<String, String> map = new HashMap<>();
        map.put("userid",UserID);
        map.put("firstname",fName);
        map.put("lastname",lName);
        map.put("address1",address1);
        map.put("address2",address2);
        map.put("city",city);
        map.put("postcode",pinCode);
        map.put("country",selectedCountryId);
        map.put("state",selectedStateId);
        map.put("def_address",defAddress);
        map.put("address_id",address_id);
        map.put("company",cmpny);
        map.put("telephone",mobile);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<AddAddressResponseDTO> call= restInterface.callAddToAddressService(globalUtils.getKey(),globalUtils.getapidate(), map);
        call.enqueue(new Callback<AddAddressResponseDTO>() {
            @Override
            public void onResponse(Call<AddAddressResponseDTO> call, retrofit2.Response<AddAddressResponseDTO> response) {
             try {
                 if (response != null) {
                     hideProgress();
                     Toast.makeText(AddressFormActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                     if (response.body().getSuccess() == 1) {
                         pd.dismiss();
                         finish();
                     }else{
                         pd.dismiss();
                     }
                 }else{
                     pd.dismiss();
                 }
             }catch (Exception e){
                 pd.dismiss();
                 e.printStackTrace();
             }
            }

            @Override
            public void onFailure(Call<AddAddressResponseDTO> call, Throwable t) {
                pd.dismiss();
                Log.e("AddressAddExc",":"+t);
                Toast.makeText(AddressFormActivity.this, "Something went wrong, please try again later!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void getStateList(String id) {
        if (GlobalUtils.isNetworkAvailable(AddressFormActivity.this)) {
            new GetStateClass().execute(id);
        } else {
            Toast.makeText(this, R.string.no_internet_connectivity, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {
        GlobalUtils.hidedialog();
    }

    @Override
    public void validateResponse(String msg) {
        hideProgress();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getListResponse(Object object) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(AddressFormActivity.this, AddressActivity.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public void getResponse(Object object) {
        GlobalUtils.hidedialog();
        countryList = new ArrayList<>();
        countryIdList = new ArrayList<>();
        countryList.add("Select Country");
        CountryResponseDTO countryResponseDTO = (CountryResponseDTO) object;
        String msg = countryResponseDTO.getMessage();
//        GlobalUtils.showToast(this, msg);
        if (countryResponseDTO.getSuccess() == 1) {
//            btnDeliverHere.setEnabled(true);
            countryData = countryResponseDTO.getResult().getCountryData();
            for (int i = 0; i < countryData.size(); i++) {
                countryIdList.add(countryData.get(i).getCountryId());
                countryList.add(countryData.get(i).getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, countryList);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            countrySpin.setAdapter(dataAdapter);
            if(isEditModeOn) {
                for (int i = 0; i < countryData.size(); i++) {
                    if (selectedCountryId.equalsIgnoreCase(countryData.get(i).getCountryId())) {
                        countrySpin.setSelection(i + 1);
                    }
                }
            }
            if(selectedcurrency.equalsIgnoreCase("INR")){
                for (int i = 0; i < countryData.size(); i++) {
                    if (countryData.get(i).getName().equalsIgnoreCase("INDIA")) {
                        countrySpin.setSelection(i + 1);
                    }
                }
            }
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getAddressResponse(Object object) {

    }

    @Override
    public void getAddressDeleteResponce(Object object) {

    }

    private class GetStateClass extends AsyncTask<String, String, String> {
        String result;

        @Override
        protected String doInBackground(String... strings) {
            try {
                String countId = strings[0];
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("countryid", countId)
                        .build();

                Request request = new Request.Builder()
                        .url(globalUtils.STATE_LIST_URL)
                        .post(requestBody)
                        .addHeader("Apikey", globalUtils.getKey())
                        .addHeader("Apidate", globalUtils.getapidate())
                        .build();

                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();


                Response response = client.newCall(request).execute();
                result = response.body().string();
                System.out.println("StateResponse--->" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.length() != 0) {
                try {
                    JSONObject data = new JSONObject(s);
                    zoneIdList = new ArrayList<>();
                    int status = data.optInt("success");
                    String msg = data.optString("message");
                    stList = new ArrayList<>();
                    if (status == 1) {
                        JSONObject inData = data.optJSONObject("result");
                        stList.add("Select State");
                        JSONArray stateList = inData.optJSONArray("zoneData");
                        for (int i = 0; i < stateList.length(); i++) {
                            JSONObject lsObj = stateList.optJSONObject(i);
                            String adata = lsObj.optString("name").replace("&amp;", "&");
                            String stateId = lsObj.optString("zone_id");
                            stList.add(adata);
                            zoneIdList.add(new StateModel(stateId, adata));
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddressFormActivity.this, android.R.layout.simple_spinner_item, stList);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        stateSpin.setAdapter(dataAdapter);
                        if (isEditModeOn) {
                            if (countrySpin.getSelectedItemPosition() > 0) {
//                            Toast.makeText(AddressActivity.this, "ZoneID:"+editedZoneId, Toast.LENGTH_SHORT).show();

                                for (int j = 0; j < zoneIdList.size(); j++) {
                                    if (zoneIdList.get(j).getState_id().equalsIgnoreCase(selectedStateId)) {
                                        stateSpin.setSelection(j + 1);
                                    }
                                }
                            }else {
                            Toast.makeText(AddressFormActivity.this, "Country not selected!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
