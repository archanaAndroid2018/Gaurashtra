package com.gaurashtra.app.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.PaytmChecksumBean;
import com.gaurashtra.app.view.activity.PayUMoney.AppEnvironment;
import com.google.gson.Gson;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class PaymentPayUmoneyActivity extends AppCompatActivity {
    private String TAG="PayUmoneyPaymentActivity";
    private String txnId,orderData;
    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    private String totalAmount="1.00", orderId="1234";
    private ArrayList<NameValuePair> paramsListForPayumoneyHash;
    private String localServerHash;
    GlobalUtils globalUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_pay_umoney);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
       TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Payment Gateway");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        Double TotalAmount = Double.parseDouble(getIntent().getStringExtra("Amount"));
        UserUpdate userUpdate= new UserUpdate(PaymentPayUmoneyActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(PaymentPayUmoneyActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(PaymentPayUmoneyActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        globalUtils= new GlobalUtils();
        totalAmount= String.valueOf(TotalAmount);
        orderData = getIntent().getStringExtra("Orderdata");
//        package_packs= getIntent().getStringExtra("Packs");
//        package_id= getIntent().getStringExtra("packageId")
        launchPayUMoneyFlow();

    }

//    private void getPayUmoneyChecksum() {
//        HashMap<String, String> map = new HashMap<>();
//        map.put("MID", Constants.PayTm.M_ID);
//        map.put("CUST_ID", SharedPreference.getUserid(PaymentPayUmoneyActivity.this));
//        map.put("ORDER_ID",orderId);
//        map.put("INDUSTRY_TYPE_ID",Constants.PayTm.INDUSTRY_ID);
//        map.put("CHANNEL_ID",Constants.PayTm.CHANNEL_ID);
//        map.put("TXN_AMOUNT",totalAmount);
//        //non mandatory field
//        map.put("MOBILE_NO","1234567890");
//        map.put("EMAIL","user@gmail.com");
//
//        map.put("WEBSITE",Constants.PayTm.WEBSITE);
//        map.put("CALLBACK_URL",Constants.PayTm.CALLBACKURL+orderId);
//        map.put("MERCHANT_KEY",Constants.PayTm.MERCHANT_KEY);
//        Log.e("RequestForCSPaytm",":"+map);
//        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
//        Call<PaytmChecksumBean> call = restInterface.getPaytmChecksum(globalUtils.getKey(), globalUtils.getapidate(), map);
//        call.enqueue(new Callback<PaytmChecksumBean>() {
//            @Override
//            public void onResponse(Call<PaytmChecksumBean> call, Response<PaytmChecksumBean> response) {
//                Log.e("PayU_CS_Res",":"+response.body().getSuccess());
//                if(response.body().getSuccess()==1){
//
//                }else{
//                    Toast.makeText(PaymentPayUmoneyActivity.this, "Unable to proceed your payment, Try again later!", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<PaytmChecksumBean> call, Throwable t) {
//
//                Toast.makeText(PaymentPayUmoneyActivity.this, "Exception Occured: "+t, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//    }

    private void launchPayUMoneyFlow() {
        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();
        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Payment");
        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Gaurashtra");
        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();
//        txnId = System.currentTimeMillis() + "";
        txnId = orderData;
        String phone = "7501688164";
        String productName = "Gaurashtra";
        String firstName = SharedPreference.getFirstName(PaymentPayUmoneyActivity.this);
        String email="";
        if(!SharedPreference.getUserEmail(this).isEmpty()) {
            email = SharedPreference.getUserEmail(PaymentPayUmoneyActivity.this);
        }
        if(!SharedPreference.getSocialEmail(this).isEmpty()){
            email= SharedPreference.getSocialEmail(this);
        }
        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";
        builder.setAmount(totalAmount+"")
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(AppEnvironment.PRODUCTION.surl())
                .setfUrl(AppEnvironment.PRODUCTION.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(false)
                .setKey(AppEnvironment.PRODUCTION.merchant_Key()).setMerchantId(AppEnvironment.PRODUCTION.merchant_ID());
        try {
            mPaymentParams = builder.build();
            SharedPreference.setTxnIdFromServer(PaymentPayUmoneyActivity.this,txnId);
            SharedPreference.setAmountFromServer(PaymentPayUmoneyActivity.this, totalAmount+"");
            generateHashFromServer(mPaymentParams);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            // payNowButton.setEnabled(true);
        }
    }

    public void generateHashFromServer(PayUmoneySdkInitializer.PaymentParam paymentParam) {
        //nextButton.setEnabled(false); // lets not allow the user to click the button again and again.
        HashMap<String, String> params = paymentParam.getParams();
        // lets create the post params
        StringBuffer postParamsBuffer = new StringBuffer();
        postParamsBuffer.append(concatParams(PayUmoneyConstants.KEY, params.get(PayUmoneyConstants.KEY)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.TXNID, params.get(PayUmoneyConstants.TXNID)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.AMOUNT, params.get(PayUmoneyConstants.AMOUNT)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.PRODUCT_INFO, params.get(PayUmoneyConstants.PRODUCT_INFO)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.FIRSTNAME, params.get(PayUmoneyConstants.FIRSTNAME)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.EMAIL, params.get(PayUmoneyConstants.EMAIL)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF1, params.get(PayUmoneyConstants.UDF1)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF2, params.get(PayUmoneyConstants.UDF2)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF3, params.get(PayUmoneyConstants.UDF3)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF4, params.get(PayUmoneyConstants.UDF4)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF5, params.get(PayUmoneyConstants.UDF5)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF6, params.get(PayUmoneyConstants.UDF6)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF7, params.get(PayUmoneyConstants.UDF7)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF8, params.get(PayUmoneyConstants.UDF8)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF9, params.get(PayUmoneyConstants.UDF9)));
        postParamsBuffer.append(concatParams(PayUmoneyConstants.UDF10, params.get(PayUmoneyConstants.UDF10)));

        paramsListForPayumoneyHash =new ArrayList<>();
        paramsListForPayumoneyHash.add(new BasicNameValuePair("payukey",params.get(PayUmoneyConstants.KEY)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("amount",params.get(PayUmoneyConstants.AMOUNT)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("txnid",params.get(PayUmoneyConstants.TXNID)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("email",params.get(PayUmoneyConstants.EMAIL)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("phone",params.get(PayUmoneyConstants.PHONE)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("productinfo",params.get(PayUmoneyConstants.PRODUCT_INFO)));
        paramsListForPayumoneyHash.add(new BasicNameValuePair("firstname",params.get(PayUmoneyConstants.FIRSTNAME)));

//        String hashSequence = "5jRdthRj|txn12347|9.0|OwnTv|Archana|archana.kumari@algosoft.co|udf1|udf2|udf3|udf4|udf5||||||d6aayomCTL";
        String hashSequence = params.get(PayUmoneyConstants.KEY)+"|"+params.get(PayUmoneyConstants.TXNID)+"|"+params.get(PayUmoneyConstants.AMOUNT)+"|"+params.get(PayUmoneyConstants.PRODUCT_INFO)+"|"+params.get(PayUmoneyConstants.FIRSTNAME)+"|"+params.get(PayUmoneyConstants.EMAIL)+"|||||||||||0cBffZXK";
        localServerHash= hashCal("SHA-512", hashSequence);

        Log.e("HASH SEQ",""+hashSequence+"\n Server Hash "+localServerHash);
        mPaymentParams.setMerchantHash(localServerHash);
        PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentPayUmoneyActivity.this, R.style.PayumoneyAppTheme, true);

//        if (NetworkStatus.isNetworkAvailable(PaymentPayUmoneyActivity.this))
//        {
//            new GetMerchantHash().execute();
//        }else Toast.makeText(PaymentPayUmoneyActivity.this, "Please connect your Internet", Toast.LENGTH_SHORT).show();


//        progressDialog = new ProgressDialog(PaymentPayActivity.this);
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//        progressDialog.setContentView(R.layout.progressbar);


    }

    public static String hashCal(String type, String hashString) {
        StringBuilder hash = new StringBuilder();
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(type);
            messageDigest.update(hashString.getBytes());
            byte[] mdbytes = messageDigest.digest();
            for (byte hashByte : mdbytes) {
                hash.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // Result Code is -1 send from Payumoney activity
            Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
            if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
                TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE);
                ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);
                // Check which object is non-null
                if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {
                    String payuResponse = transactionResponse.getPayuResponse();
                    Log.e("suresponse",payuResponse.toString());
                    JSONObject jsonObject=new JSONObject(payuResponse);
                    JSONObject jsonResult=jsonObject.getJSONObject("result");
                    String errorMessage=jsonResult.getString("error_Message");
                    SharedPreference.setPaymentStatusMessageFromServer(PaymentPayUmoneyActivity.this,errorMessage);
                    if (transactionResponse.getTransactionStatus().equals(TransactionResponse.TransactionStatus.SUCCESSFUL)) {

//                        new UpdateOrderStatus().execute(SharedPreference.getUserid(PaymentPayUmoneyActivity.this),package_packs,package_id,txnId,jsonResult.getInt("paymentId")+"");
//                        Intent intent = new Intent(PaymentPayUmoneyActivity.this, PaymentSuccessActivity.class);
//                        intent.putExtra("sessionId", sessionId);
//                        intent.putExtra("txnId", txnId);
//                        intent.putExtra("status", "success");
//                        intent.putExtra("amount", TotalAmount+"");
//                        startActivity(intent);
                    } else {
                        Toast.makeText(PaymentPayUmoneyActivity.this, "Payment failed", Toast.LENGTH_SHORT).show();
                        finish();
//                        Intent intent = new Intent(PaymentPayUmoneyActivity.this, PaymentFailureActivity.class);
//                        intent.putExtra("sessionId", sessionId);
//                        intent.putExtra("txnId", txnId);
//                        intent.putExtra("status", "cancel");
//                        intent.putExtra("amount", TotalAmount+"");
//                        startActivity(intent);
                    }

                } else if (resultModel != null && resultModel.getError() != null) {
                    Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
                } else {
                    Log.d(TAG, "Both objects are null!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected String concatParams(String key, String value) {
        return key + "=" + value + "&";
    }



//    public class UpdateOrderStatus extends AsyncTask<String,String,JSONObject>
//    {
//
//        @Override
//        protected JSONObject doInBackground(String... strings) {
//            WebServicesURL webServicesURL=new WebServicesURL();
//            ArrayList<NameValuePair> params=new ArrayList<>();
//            params.add(new BasicNameValuePair("uid",strings[0]));
//            params.add(new BasicNameValuePair("package_packs",strings[1]));
//            params.add(new BasicNameValuePair("package_id",strings[2]));
//            params.add(new BasicNameValuePair("order_id",strings[3]));
//            params.add(new BasicNameValuePair("txnid",strings[4]));
//            JSONObject jsonObject=  webServicesURL.placeOrder(params);
//            return webServicesURL.updateOrderStatusMethod(params);
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            super.onPostExecute(jsonObject);
//            try{
//                Log.e("UpdateOrderStatus",jsonObject.toString());
//                Gson gson=new Gson();
//                ResponseClass responseClass=(ResponseClass)gson.fromJson(jsonObject.toString(),ResponseClass.class);
//                if (responseClass.getSuccess()==1) {
//                    Toast.makeText(PaymentPayUmoneyActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
//                    finish();
//                }else {
//                    Toast.makeText(PaymentPayUmoneyActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }

//    public class GetMerchantHash extends AsyncTask<String,String,JSONObject>
//    {
//
//        @Override
//        protected JSONObject doInBackground(String... strings) {
//            WebServicesURL webServiceClass=new WebServicesURL();
//            Log.e("PayUMoneyHash",""+paramsListForPayumoneyHash);
//            return webServiceClass.getPayUmoneyHashMethod(paramsListForPayumoneyHash);
//        }
//
//        @Override
//        protected void onPostExecute(JSONObject jsonObject) {
//            super.onPostExecute(jsonObject);
//            try{
//                Log.e("GetMerchantHashRes",jsonObject.toString());
//                Gson gson=new Gson();
//                ResponseClass responseClass=(ResponseClass)gson.fromJson(jsonObject.toString(),ResponseClass.class);
//                if (responseClass.getSuccess()==1)
//                {
////                    mPaymentParams.setMerchantHash(responseClass.getResult().getChecksumData());
//                    mPaymentParams.setMerchantHash(localServerHash);
//                    PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, PaymentPayUmoneyActivity.this, R.style.PayumoneyAppTheme, true);
//                }else {
//
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        finish();
        return super.onKeyDown(keyCode, event);

    }
}

