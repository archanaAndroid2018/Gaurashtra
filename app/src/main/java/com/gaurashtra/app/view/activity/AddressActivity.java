package com.gaurashtra.app.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.modelInteractor.StateModel;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.bean.AddAddressBean.AddAddressResponseDTO;
import com.gaurashtra.app.model.bean.addresslistbean.AddressResponseDTO;
import com.gaurashtra.app.model.bean.addresslistbean.UserAddressDatum;
import com.gaurashtra.app.model.bean.countrybean.CountryDatum;
import com.gaurashtra.app.model.bean.countrybean.CountryResponseDTO;
import com.gaurashtra.app.presentator.AddressPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.AddressPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.AddressActivityInteractor;
import com.gaurashtra.app.view.adapter.SelectAddressAdapter;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddressActivity extends AppCompatActivity implements AddressActivityInteractor, SelectAddressAdapter.DeleteEditAddressInterface, View.OnClickListener {
    private TextView textviewTitle;
    private RecyclerView addressAdd;
    String UserID;
    RestInterface restInterface;

    static Boolean isTouched = false;
    private PrefClass prefClass;
    private GlobalUtils globalUtils;
    List<CountryDatum> countryData = new ArrayList<>();
    List<String> stList;
    List<StateModel> zoneIdList;
    private SelectAddressAdapter addressAdapter;
    private AddressPresentarInteractor addressPresentarInteractor;
    private SharedPreference sharedPreference;
    List<String> countryList;
    List<String> countryIdList;
    List<UserAddressDatum> addressList;
    AddressResponseDTO addressResponseDTO;
    FloatingActionButton fab_address;
    TextView btnDeliverHere;
    int selectedAddressIndex = 0;
    FrameLayout frameNodata;
    String cartInfo, productQty, cartSessionId, shippingCountry="", shippingZone="",
            cIdIndex = "0", zIdIndex = "0", editedZoneId = "", selectedAddObj = "";
    Boolean isSetStateId = false, saveInPref = false, isAnyAddressSelected = false, isProceedToDelivery = false;
    ScrollView scrollView;
    Boolean isStateSelected = false, isCountrySelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("My Address");
        UserUpdate userUpdate= new UserUpdate(AddressActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(AddressActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(AddressActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        UserID = SharedPreference.getUserid(this);
        addressList = new ArrayList<>();

        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        prefClass = new PrefClass(this);
        addressPresentarInteractor = new AddressPresentar(this);
        if (GlobalUtils.isNetworkAvailable(this)) {
            addressPresentarInteractor.sendCountryRequest();
        } else {
            Toast.makeText(this, "Please check your network connectivity!", Toast.LENGTH_SHORT).show();
        }

        setUpContentView();

        globalUtils = new GlobalUtils();
    }

    public void setUpContentView() {
        frameNodata = findViewById(R.id.frame_no_address);
        addressAdd = findViewById(R.id.rvAddressAdd);
        scrollView = findViewById(R.id.scrollview);
        fab_address = findViewById(R.id.fab_new_address);
        btnDeliverHere = findViewById(R.id.ll_deliver);
        GlobalUtils.showdialog(this);
        addressAdd.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        addressAdd.setLayoutManager(manager);

        btnDeliverHere.setOnClickListener(this);
        fab_address.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        isAnyAddressSelected=false;
        super.onResume();
        isProceedToDelivery = true;
        if (getIntent().hasExtra(Constants.PreferenceConstants.AMOUNTTOPAY)) {

            cartInfo = getIntent().getStringExtra(Constants.PreferenceConstants.AMOUNTTOPAY);
            productQty = getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTQTY);
            cartSessionId = getIntent().getStringExtra(Constants.PreferenceConstants.CARTSESSIONID);
        }else {
            isProceedToDelivery = false;
            btnDeliverHere.setVisibility(View.INVISIBLE);
        }
        if (GlobalUtils.isNetworkAvailable(this)) {
            addressPresentarInteractor.sendAddressListRequest(SharedPreference.getUserid(this));
        } else {
            Toast.makeText(this, "Please check your network connectivity!", Toast.LENGTH_SHORT).show();
        }
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!isProceedToDelivery) {
            Intent intent = new Intent(AddressActivity.this, HomeActivity.class);
            intent.putExtra("BACKFROM", "ME");
            startActivity(intent);
        }else{
            finish();
        }
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void validateResponse(String msg) {
        GlobalUtils.showToast(this, msg);
    }

    @Override
    public void getListResponse(Object object) {
//       GlobalUtils.hidedialog();
        addressResponseDTO = (AddressResponseDTO) object;
        try {
            if (addressResponseDTO.getSuccess() == 1) {
                addressList= new ArrayList<>();
                addressList = addressResponseDTO.getResult().getUserAddressData();
                if(addressList.size()>0) {
                    frameNodata.setVisibility(View.GONE);
                    for (int i = 0; i < addressList.size(); i++) {
                        String AddressId = addressList.get(i).getAddressId();
                        String defAddressId = addressList.get(i).getDefaultAddressId();
                        String city = addressList.get(i).getCity();
                        String addr1 = addressList.get(i).getAddress1();
                        String addr2 = addressList.get(i).getAddress2();
                        String phone = addressList.get(i).getCustomField();
                        String countId = addressList.get(i).getCountryId();
                        String zoneId = addressList.get(i).getZoneId();
                        String zipCode = addressList.get(i).getPostcode();

                        if (AddressId.equalsIgnoreCase(defAddressId)) {
//                        setDefZoneCountry(countId, zoneId);
                            SharedPreference.setUserAddress1(this, addr1);
                            SharedPreference.setUserAddress2(this, addr2);
                            SharedPreference.setUserCity(this, city);
                            SharedPreference.setdefAddressPhone(this, phone);
                            SharedPreference.setUserZipcode(this, zipCode);
                            SharedPreference.setAddressId(this, AddressId);
                            SharedPreference.setDefAddressId(this, defAddressId);
                            String countryname = addressList.get(i).getCountryName();
                            String countryId = addressList.get(i).getCountryId();
                            String state = addressList.get(i).getZoneName();
                            String stateId = addressList.get(i).getZoneId();
                            SharedPreference.setUserCountry(this, countryname);
                            SharedPreference.setUserCountryId(this, countryId);
                            SharedPreference.setUserState(this, state);
                            SharedPreference.setUserStateId(this, stateId);

                        }
                    }
                    addressAdapter = new SelectAddressAdapter(AddressActivity.this, addressList, AddressActivity.this, isProceedToDelivery);
                    addressAdd.setAdapter(addressAdapter);
                }else{
                    frameNodata.setVisibility(View.VISIBLE);
                }
            } else {
                frameNodata.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAddressDeleteResponce(final Object object) {
        GlobalUtils.hidedialog();
        AddressResponseDTO addressDeleteResponceDTO = (AddressResponseDTO) object;
        addressPresentarInteractor.sendAddressListRequest(SharedPreference.getUserid(AddressActivity.this));
        Toast.makeText(AddressActivity.this, addressDeleteResponceDTO.getMessage(), Toast.LENGTH_SHORT).show();
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
                String countryId = countryData.get(i).getCountryId();
                if(SharedPreference.getDefAddressId(AddressActivity.this).equalsIgnoreCase(countryId)) {
                    SharedPreference.setcountryId(AddressActivity.this, countryId);
                }
            }
        } else {
            Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void getAddressResponse(Object object) {
        AddAddressResponseDTO addAddressResponseDTO = (AddAddressResponseDTO) object;
        String message = addAddressResponseDTO.getMessage();
//        globalUtils.showToast(this, message);
        addressPresentarInteractor.sendAddressListRequest(SharedPreference.getUserid(this));
    }

    public void getStateList(String id) {
        if (GlobalUtils.isNetworkAvailable(AddressActivity.this)) {
            new GetStateClass().execute(id);
        } else {
            Toast.makeText(this, R.string.no_internet_connectivity, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_new_address:
            Intent intent2= new Intent(AddressActivity.this, AddressFormActivity.class);
            startActivity(intent2);
            break;
            case R.id.ll_deliver:
                try {
                        if (addressResponseDTO.getSuccess() == 1) {
                            if (isAnyAddressSelected) {
                                if(shippingZone.isEmpty()) {
                                    if (Integer.parseInt(zIdIndex) > 0) {
                                        for (int j = 0; j < zoneIdList.size(); j++) {
                                            if (zoneIdList.get(j).getState_id().equalsIgnoreCase(zIdIndex)) {
                                                shippingZone = stList.get(j + 1);
                                                Log.e("shippingZone", ":" + shippingZone);
                                            }
                                        }
                                    }
                                }
                                if (selectedAddObj.isEmpty()) {
                                    selectedAddObj = new Gson().toJson(addressList.get(selectedAddressIndex));
                                }
                                Log.e("selectedAddObj", ":" + selectedAddObj);
                                Intent intent = new Intent(this, ConfirmOrderActivity.class);
                                intent.putExtra(Constants.PreferenceConstants.SELECTEDADDRESS, selectedAddObj);
                                intent.putExtra(Constants.PreferenceConstants.AMOUNTTOPAY, cartInfo);
                                intent.putExtra(Constants.PreferenceConstants.PRODUCTQTY, productQty);
                                intent.putExtra(Constants.PreferenceConstants.CARTSESSIONID, cartSessionId);
                                if (shippingCountry.isEmpty()) {
                                    shippingCountry = countryData.get(selectedAddressIndex).getName();
                                }
                                intent.putExtra(Constants.PreferenceConstants.COUNTRYNAME, shippingCountry);
                                Log.e("ZoneInIntent", ":" + shippingCountry + "\n" + cIdIndex + "\n" + shippingZone + "\n" + zIdIndex);
                                intent.putExtra(Constants.PreferenceConstants.ZONENAME, shippingZone);
                                isAnyAddressSelected=false;
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "Please select a delivery address to proceed!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Please add a delivery address to proceed!", Toast.LENGTH_SHORT).show();
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void selectedAddress(int position, HashMap<Integer, Boolean> map) {
        Log.e("ONSelected", "" + position);
//        clearFields();
        selectedAddressIndex = position;
        cIdIndex = addressList.get(position).getCountryId();
        zIdIndex = addressList.get(position).getZoneId();
        isAnyAddressSelected = true;
        for (int i = 0; i < countryData.size(); i++) {
            if (countryData.get(i).getCountryId().equalsIgnoreCase(cIdIndex)) {
//                selectedAddressIndex = i;
                Log.e("CountryName", ":" + countryData.get(i).getName());
                shippingCountry = countryData.get(i).getName();
                isSetStateId = true;
                getStateList(cIdIndex);
            }
        }

        try {
            if (map.size() > 0) {
                List<Integer> posList = new ArrayList<>(map.keySet());
                for (int a = 0; a < posList.size(); a++) {
                    if (map.get(posList.get(a))) {
                        isAnyAddressSelected = true;
                    }
                }
            }

            if (selectedAddressIndex >= 0 && isProceedToDelivery) {
                selectedAddObj = new Gson().toJson(addressList.get(selectedAddressIndex));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void DeleteAdd(String uid, final String addressid) {
//        clearFields();
        final Dialog removeItemDialog = new Dialog(this);
        removeItemDialog.setContentView(R.layout.dialog_logout_layout);
        removeItemDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        removeItemDialog.setCanceledOnTouchOutside(true);
        removeItemDialog.setCancelable(true);
        removeItemDialog.show();
        TextView tvTitle = removeItemDialog.findViewById(R.id.dialog_tv_title);
        tvTitle.setText("Removing Address");
        TextView tvMsg = removeItemDialog.findViewById(R.id.dialog_tv_msg);
        tvMsg.setText("Do you really want to remove this address?");
        TextView tvYes = removeItemDialog.findViewById(R.id.tv_logout_yes);
        TextView tvNo = removeItemDialog.findViewById(R.id.tv_logout_no);
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItemDialog.dismiss();
                GlobalUtils.showdialog(AddressActivity.this);
                addressPresentarInteractor.sendAddressDeleteRequest(SharedPreference.getUserid(AddressActivity.this), addressid);
            }
        });
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItemDialog.dismiss();
                GlobalUtils.hidedialog();
            }
        });
    }

    @Override
    public void EditAdd(int pos, UserAddressDatum datum){
        Intent intent = new Intent(AddressActivity.this, AddressFormActivity.class);
        intent.putExtra(Constants.PreferenceConstants.SELECTEDADDRESS, (Serializable)datum);
        startActivity(intent);
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

                            if (isSetStateId) {
                                if (stateId.equalsIgnoreCase(zIdIndex)) {
                                    shippingZone = adata;
                                    isSetStateId = false;
                                }
                            } else if (saveInPref) {
                                SharedPreference.setUserStateId(AddressActivity.this, stateId);
                                SharedPreference.setUserState(AddressActivity.this, adata);
                                saveInPref = false;
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
