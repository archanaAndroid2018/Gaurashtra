package com.gaurashtra.app.view.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.MyReviewListBean.ReviewData;
import com.gaurashtra.app.model.bean.MyReviewListBean.ReviewResponceDTO;
import com.gaurashtra.app.model.bean.ReviewResult;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.adapter.MyReviewsAdapter;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.gaurashtra.app.R;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ToBeReviewFragment extends Fragment implements MyReviewsAdapter.MyReviewInterface {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyReviewsAdapter adapter;
    private GlobalUtils globalUtils;
    private ProgressBar progressBar;
    ArrayList<ReviewData> reviewList = new ArrayList<>();
    TextView tvTerrible, tvBad, tvOkay, tvGood, tvGreat, btnShopping;
    String orderId, userId;
    ImageView iconTerrible, iconBad, iconOkay, iconGood,iconGreat;
    EditText etReview;
    Button btnSubmitReview;
    LinearLayout layoutTerrible, layoutBad,layoutOkay, layoutGood, layoutGreat;
    String reviewText, ratingValue;
    FrameLayout frameNoList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_to_be_review, container, false);
        globalUtils= new GlobalUtils();
        userId = SharedPreference.getUserid(getActivity());
        progressBar = view.findViewById(R.id.pb_to_be_review);
        recyclerView = view.findViewById(R.id.rv_to_be_review);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        frameNoList= view.findViewById(R.id.frame_no_review);
        btnShopping= view.findViewById(R.id.tv_btn_shopping);
        btnShopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        if(GlobalUtils.isNetworkAvailable(getActivity())) {
            getToBeReviewList(userId,progressBar);
        }else{
            Toast.makeText(getActivity(),R.string.no_internet_connectivity, Toast.LENGTH_SHORT).show();
        }
        GlobalUtils.isNetworkAvailable(getActivity());
    }

    private void getToBeReviewList(String userId, final ProgressBar progressBar) {
        HashMap<String,String> review = new HashMap<>();
        review.put("userid", userId);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<ReviewResponceDTO> call= restInterface.myReviewList(globalUtils.getKey(), globalUtils.getapidate(),review);
        call.enqueue(new Callback<ReviewResponceDTO>() {
            @Override
            public void onResponse(Call<ReviewResponceDTO> call, Response<ReviewResponceDTO> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body().getSuccess()==1){
                    frameNoList.setVisibility(View.GONE);
                    Log.e("MyReviewList",""+response.body().getResult().getOrderData());
                    reviewList = response.body().getResult().getOrderData().getToBeReviewed();
                    if(reviewList.size()>0) {
                        recyclerView.setVisibility(View.VISIBLE);
                        adapter = new MyReviewsAdapter(getActivity(), reviewList, ToBeReviewFragment.this);
                        recyclerView.setAdapter(adapter);
                    }else{
                        frameNoList.setVisibility(View.VISIBLE);
                    }
                }else{
//                    if(response.body().getResult() == null){
                        frameNoList.setVisibility(View.VISIBLE);
//                    }
                }
            }
            @Override
            public void onFailure(Call<ReviewResponceDTO> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e("MyReviewListExc",""+t);
            }
        });
    }

    @Override
    public void openWriteReview(int adapterPosition, final String productId, String action) {
        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.write_review_layout);
        ImageView btnDelete = (ImageView) dialog.findViewById(R.id.img_rev_btn_delete);
        etReview= dialog.findViewById(R.id.et_review);
        btnSubmitReview= dialog.findViewById(R.id.btn_submit_review);

        tvTerrible=dialog.findViewById(R.id.tv_terrible_text);
        tvBad= dialog.findViewById(R.id.tv_bad_text);
        tvOkay= dialog.findViewById(R.id.tv_okay_text);
        tvGood= dialog.findViewById(R.id.tv_good_text);
        tvGreat= dialog.findViewById(R.id.tv_great_text);

        iconTerrible= dialog.findViewById(R.id.iv_terrible_icon);
        iconBad= dialog.findViewById(R.id.iv_bad_icon);
        iconOkay= dialog.findViewById(R.id.iv_okay_icon);
        iconGood= dialog.findViewById(R.id.iv_good_icon);
        iconGreat= dialog.findViewById(R.id.iv_great_icon);

        layoutTerrible = dialog.findViewById(R.id.ll_terrible_icon);
        layoutBad= dialog.findViewById(R.id.ll_bad_icon);
        layoutOkay= dialog.findViewById(R.id.ll_okay_icon);
        layoutGood= dialog.findViewById(R.id.ll_good_icon);
        layoutGreat= dialog.findViewById(R.id.ll_great_icon);

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewText= etReview.getText().toString().trim();
                try{
                    if(reviewText.isEmpty()){
                        Toast.makeText(getActivity(), "Please write review!", Toast.LENGTH_SHORT).show();
                    }else if(ratingValue.equalsIgnoreCase("0")){
                        Toast.makeText(getActivity(), "Please rate the product!", Toast.LENGTH_SHORT).show();
                    }else{
                        dialog.dismiss();
                        submitReview(productId,"add");
                    }
                }catch (Exception e){
                    dialog.dismiss();
                    e.printStackTrace();}
            }

        });

        layoutTerrible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="1";
                setRatingIcon(iconTerrible, iconBad,iconOkay,iconGood,iconGreat);
                setIconText(tvTerrible, tvBad, tvOkay, tvGood, tvGreat);
            }
        });
        layoutBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="2";
                setRatingIcon(iconBad, iconTerrible,iconOkay,iconGood,iconGreat);
                setIconText(tvBad, tvTerrible, tvOkay, tvGood, tvGreat);
            }
        });
        layoutOkay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="3";
                setRatingIcon(iconOkay,iconBad,iconTerrible,iconGood,iconGreat);
                setIconText(tvOkay,tvBad,tvTerrible, tvGood, tvGreat);
            }
        });
        layoutGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="4";
                setRatingIcon(iconGood,iconBad, iconOkay, iconTerrible,iconGreat);
                setIconText(tvGood,tvTerrible,tvBad, tvOkay, tvGreat);
            }
        });
        layoutGreat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingValue="5";
                setRatingIcon(iconGreat,iconOkay, iconGood,iconTerrible, iconBad);
                setIconText(tvGreat,tvTerrible,tvBad, tvOkay, tvGood);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    private void submitReview(String productId, String action) {
        HashMap<String, String> param= new HashMap<>();
        param.put("userid", SharedPreference.getUserid(getActivity()));
        param.put("productId", productId);
        param.put("review", reviewText);
        param.put("rating", ratingValue);
        param.put("reviewType",action);
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<ReviewResult> call= restInterface.sendProductReviewRating(globalUtils.getKey(), globalUtils.getapidate(), param);
        call.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, retrofit2.Response<ReviewResult> response) {
                if(response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                    getToBeReviewList(userId, progressBar);
                }else{
                    Toast.makeText(getActivity(), "Something went wrong, please resubmit review!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                Log.e("SubmitReviewExc",":"+t);
            }
        });
    }

    private void setIconText(TextView selected, TextView unSelected1, TextView unSelected2, TextView unSelected3, TextView unSelected4) {
        selected.setTextColor(getResources().getColor(R.color.light_red));
        unSelected1.setTextColor(getResources().getColor(R.color.gray));
        unSelected2.setTextColor(getResources().getColor(R.color.gray));
        unSelected3.setTextColor(getResources().getColor(R.color.gray));
        unSelected4.setTextColor(getResources().getColor(R.color.gray));
    }

    private void setRatingIcon(ImageView selected, ImageView unSelected1, ImageView unSelected2, ImageView unSelected3, ImageView unSelected4) {
//         selected.setImageDrawable(getResources().getDrawable(R.drawable.ic ));
        DrawableCompat.setTint(
                DrawableCompat.wrap(selected.getDrawable()),
                ContextCompat.getColor(getActivity(), R.color.light_red)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected1.getDrawable()),
                ContextCompat.getColor(getActivity(), R.color.gray)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected2.getDrawable()),
                ContextCompat.getColor(getActivity(), R.color.gray)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected3.getDrawable()),
                ContextCompat.getColor(getActivity(), R.color.gray)
        );
        DrawableCompat.setTint(
                DrawableCompat.wrap(unSelected4.getDrawable()),
                ContextCompat.getColor(getActivity(), R.color.gray)
        );
    }
}
