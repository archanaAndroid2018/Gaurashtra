package com.gaurashtra.app.view.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.ProductDetails.Datum;
import com.gaurashtra.app.model.bean.ProductRemainingResult;
import com.gaurashtra.app.view.adapter.ReviewAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AllReviewsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ProductRemainingResult.Result.Product.Review.Datum> reviewList;
    ReviewAdapter reviewAdapter;
    String strReviewList="";
    ImageButton btnBack;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reviews);
        btnBack = findViewById(R.id.ibtn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        UserUpdate userUpdate= new UserUpdate(AllReviewsActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(AllReviewsActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(AllReviewsActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        recyclerView= findViewById(R.id.rv_all_reviews);
        strReviewList= getIntent().getStringExtra(Constants.PreferenceConstants.ALLREVIEWS);
        Type type=new TypeToken<List<ProductRemainingResult.Result.Product.Review.Datum>>(){}.getType();
        reviewList= (new Gson()).fromJson(strReviewList,type);

        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        reviewAdapter = new ReviewAdapter(AllReviewsActivity.this, reviewList);
        recyclerView.setAdapter(reviewAdapter);
    }
}
