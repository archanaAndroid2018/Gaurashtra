package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.bean.BrandListBean.BrandListDTO;
import com.gaurashtra.app.model.bean.HomePagebean.ShopByBrandDatum;
import com.gaurashtra.app.presentator.ListOfBrandPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.ListofBrandPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ListofBrandActivityInteractor;
import com.gaurashtra.app.view.adapter.BrandAdapter;

public class BrandActivity extends AppCompatActivity implements ListofBrandActivityInteractor {
    private TextView textviewTitle;
    private RecyclerView brand;
    private BrandAdapter adapter;
    private ListofBrandPresentarInteractor listofBrandPresentarInteractor;
    private GlobalUtils globalUtils;
    private SharedPreference sharedPreference;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Brands");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        setUpContentView();
        UserUpdate userUpdate= new UserUpdate(BrandActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(BrandActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(BrandActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        listofBrandPresentarInteractor=new ListOfBrandPresentar(this);
        UserId=SharedPreference.getUserid(this);

        if(GlobalUtils.isNetworkAvailable(this)){
            listofBrandPresentarInteractor.sendRequest(UserId);
        }else {
            Toast.makeText(this,"Please check your network connection", Toast.LENGTH_LONG).show();
        }
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

    public void setUpContentView() {
        brand = findViewById(R.id.rvBrand);
        brand.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        brand.setLayoutManager(manager);

    }

    @Override
    public void showProgress() {
        GlobalUtils.showdialog(this);
    }

    @Override
    public void hideProgress() {
        GlobalUtils.hidedialog();
    }

    @Override
    public void getResponse(Object object) {
        List<ShopByBrandDatum> brandlist=new ArrayList<>();
        BrandListDTO brandListDTO=(BrandListDTO)object;
        String message=brandListDTO.getMessage();
//        GlobalUtils.showToast(this,message);

        List<ShopByBrandDatum> listofbrand=brandListDTO.getResult().getShopByBrandData();
        for (int i=0;i<listofbrand.size();i++){
            ShopByBrandDatum shopByBrandDatum=new ShopByBrandDatum();
            shopByBrandDatum.setBrandName(listofbrand.get(i).getBrandName());
            shopByBrandDatum.setBrandId(listofbrand.get(i).getBrandId());
            brandlist.add(shopByBrandDatum);
        }
        adapter = new BrandAdapter(this,brandlist);
        brand.setAdapter(adapter);
    }
}
