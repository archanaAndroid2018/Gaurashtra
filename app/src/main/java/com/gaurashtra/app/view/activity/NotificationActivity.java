package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.GetCartData.GetCartProduct;
import com.gaurashtra.app.model.bean.NotificationBean.NotificationDTO;
import com.gaurashtra.app.view.adapter.NotificationAdapter;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity implements NotificationAdapter.NotificationInterface {
    private TextView textviewTitle;
    GlobalUtils globalUtils;
    RecyclerView recyclerView;
    ArrayList<NotificationDTO.Result.NotificationDatum> notificationList;
    NotificationAdapter adapter;
    String notifId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        globalUtils= new GlobalUtils();
        recyclerView= findViewById(R.id.rv_notification);
        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Notifications");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        UserUpdate userUpdate= new UserUpdate(NotificationActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(NotificationActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(NotificationActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(GlobalUtils.isNetworkAvailable(this)) {
            GlobalUtils.showdialog(NotificationActivity.this);
            getNotificationList("GET");
        }else{
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    private void getNotificationList(final String type) {
        Map<String, String> param = new HashMap<>();
        param.put("userid", SharedPreference.getUserid(this));
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<NotificationDTO> call =null;
        if(type.equalsIgnoreCase("GET")) {
            call = restInterface.getNotificationList(globalUtils.getKey(), globalUtils.getapidate(), param);
        }else{
            param.put("notification_id",notifId);
            call = restInterface.callDeleteNotification(globalUtils.getKey(), globalUtils.getapidate(), param);
        }
        call.enqueue(new Callback<NotificationDTO>() {
            @Override
            public void onResponse(Call<NotificationDTO> call, Response<NotificationDTO> response) {
                GlobalUtils.hidedialog();
                Log.e("NotificationRes",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response != null) {
                try {
                        if (type.equalsIgnoreCase("DELETE")) {
                            GlobalUtils.showToast(NotificationActivity.this, response.body().getMessage());
                        }
                        if (response.body().getSuccess() == 1) {
                            notificationList = new ArrayList<>();
                            notificationList = response.body().getResult().getNotificationData();
                            adapter = new NotificationAdapter(NotificationActivity.this, notificationList, NotificationActivity.this);
                            recyclerView.setAdapter(adapter);
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        GlobalUtils.showToast(NotificationActivity.this, getResources().getString(R.string.label_something_went_wrong));
                    }
                }else{
                    GlobalUtils.showToast(NotificationActivity.this, getResources().getString(R.string.label_something_went_wrong));
                }
            }

            @Override
            public void onFailure(Call<NotificationDTO> call, Throwable t) {
                GlobalUtils.hidedialog();
                Log.e("NotifExc",":"+t);
                GlobalUtils.showToast(NotificationActivity.this, getResources().getString(R.string.label_something_went_wrong));
            }
        });
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
    public void deleteNofication(String Id) {
        notifId = Id;
        if(GlobalUtils.isNetworkAvailable(this)) {
            GlobalUtils.showdialog(NotificationActivity.this);
            getNotificationList("DELETE");
        }else{
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }
}
