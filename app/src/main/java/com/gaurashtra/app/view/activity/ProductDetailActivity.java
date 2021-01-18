package com.gaurashtra.app.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.bean.OptionListBean;
import com.gaurashtra.app.model.bean.ProductBasicDetailsResult;
import com.gaurashtra.app.model.bean.ProductDetails.ProductdetailsDTO;
import com.gaurashtra.app.model.bean.ProductRemainingResult;
import com.gaurashtra.app.view.adapter.CouponAdapter;
import com.gaurashtra.app.view.adapter.OfferDiscountAdapter;
import com.gaurashtra.app.view.adapter.OptionsAdapter;
import com.gaurashtra.app.view.fragment.CategoryFragment;
import com.gaurashtra.app.view.fragment.MeFragment;
import com.gaurashtra.app.view.fragment.SearchFragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
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
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.ProductDetails.Image;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDTO;
import com.gaurashtra.app.presentator.ProductDetailsPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.ProductDetailsPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ProductDetalisInteractor;
import com.gaurashtra.app.view.adapter.AskQuestionAdapter;
import com.gaurashtra.app.view.adapter.ImageAdapter;
import com.gaurashtra.app.view.adapter.ListAdapter;
import com.gaurashtra.app.view.adapter.RecentAdapter;
import com.gaurashtra.app.view.adapter.RelatedAdapter;
import com.gaurashtra.app.view.adapter.ReviewAdapter;

//import io.branch.referral.Branch;
//import io.branch.referral.BranchError;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductDetailActivity extends AppCompatActivity implements ProductDetalisInteractor, View.OnClickListener,
        RelatedAdapter.ProductDetailsInterface, RecentAdapter.RecentprodDetailsInterface,
        AdapterView.OnItemSelectedListener, OptionsAdapter.SelectOptionInterface {
//    tvShoppingOffer
    private TextView tvOfferTitle1, tvOfferTitle2, tvOfferText1, tvOfferText2,tvMoreOffer, textviewTitle, ProductName, spl_price, prod_price, descTabTitle, reviewTabTitle,
            askTabTitle, btnWriteReview, tvPercentOffer, tvReviewCount, tvBrandName, tvAvailability, tvCheckPincode,
            tvDeliveryPincodeInfo, tvNoLocFound,  tvWishlistText, tabAddTocart, tabViewCart,fixedAddTocart,
            fixedViewCart,textCartItemCount, tvPackTextFloat, tvPackTextFixed;
    WebView wvProductDesc;
    private RecyclerView relatedRv, recentRv, recyclerList, recyclerReview, recyclerAskQuestion, recyclerOptions;
    private RelatedAdapter relatedAdapter;
    private RecentAdapter recentAdapter;
    private ListAdapter listAdapter;
    private ReviewAdapter reviewAdapter;
    private AskQuestionAdapter askQuestionAdapter;
    View skeletalRecent,skeletalRelated;
    private CardView cardDropLine;
    private LinearLayout topMsgLayout, demo1, demo2, demo3, tabDescription, tabReviews, tabAskQuestion, reviewContainer, askContainer,buttonLayout,fixedButtonLayout,
            linearTabs, layoutChange, pincodeContainer,checkZipLayout,offerPercentLayout, amountCrossLayout,layout_btnCheckPincode, tabWishlist, tabShareProduct,
            quantityLayout, fixedQuantityLayout, btnAllReviews, packLayoutFixed,packLayoutFloat, productOptionsLayout,
            optionAvailableLayout, brandLayout, relatedLayout,recentlyLayout,offerLayoutContainer;
    RelativeLayout offerLayout1,offerLayout2;
    private ImageView pView, product_img1, product_img2, product_img3, addedHeart_img, notAddedHeart_img;
    private Spinner qtySpin, fixedQtySpin;
    private ProductDetailsPresentarInteractor productDetailsPresentarInteractor;
    private GlobalUtils globalUtils;
    private EditText etPincode, etQuantity;
    BottomSheetDialog pincodeDialog, optionDialog;
    String tempImage,image1, image2, image3, imageurl1, imageurl2, imageurl3, UserId, ProName,p_spclprice="0.00",
            ratingValue= "0", productId, reviewText, strReviewList="", discountQty="0", packPrice="",P_price="0.00",
            optionType="",productDescText="";
    ScrollView scrollView;
    ViewPager viewPager;
    ImageAdapter adapterView;
    ArrayList<Image> sliderImageList;
    public CountDownTimer timer;
    int timeout = 900;
    List<ProductRemainingResult.Result.Product.Review.Datum> reviewList;
    List<ProductRemainingResult.Result.Product.AskQuestion> askQuestionList;
    ProductBasicDetailsResult productdetailsDTO;
    ProductRemainingResult productRemainingDetails;
    RelativeLayout mainLayout, reviews;
    ImageView dot1, dot2, dot3, dot4;
    ViewTreeObserver vto;
    Button btnSubmitReview;
    RestInterface restInterface;
    Boolean isPreSetData=true, isFloatingSpin=false, isFixedSpin= false,
            isPackAvailable=false, isUserLoggedIn= false, isOptionAvailable=false;
    FrameLayout progressFrame, mainProgressFrame;
    private Dialog notifyDialog;
    PrefClass prefClass;
    Double dPackPrice,netPrice, dProductPrice, dProductTax,taxPercent=0.00, packOfPrice;
    private String optionId="0",optionValueId="", brandId="", brandName="";
    private String strSelectedCurrency="INR", currencySymbol="//u20B9", seoUrl="", productName
            ;
    int totalProductQty=1, selectedQty=1, reviewListSize=0,limit=0;
    double currencyValue=1.000;
    BottomNavigationView bottNavigationView;
    ProgressBar pbOnBtn;
    TextView tvNoReview;
    int lastSelectedPos=0;
    OptionsAdapter optionsAdapter;
    List<ProductBasicDetailsResult.Result.StaticContentDatum> staticData = new ArrayList<>();
    ArrayList<OptionListBean.Result.ProductData.Option> productOptionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        topMsgLayout = findViewById(R.id.offer_txt_layout);
//        tvShoppingOffer=findViewById(R.id.TextHome);
//        getSupportFragmentManager().popBackStack();

        // Initialize the Branch object
//        Branch.getAutoInstance(this);

        Log.e("prodIntent",":"+getIntent());
        Intent intent = getIntent ();
        Uri data = intent.getData ();

        UserUpdate userUpdate= new UserUpdate(ProductDetailActivity.this);

        if(!userUpdate.isUserAvailable){
            Toast.makeText(ProductDetailActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(ProductDetailActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        invalidateOptionsMenu();
        Log.e("DataFromDeeplink",":"+data);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(                                   //Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText(" ");// to clear last junk name
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        setUpContentView();
        if(!SharedPreference.getUserid(this).isEmpty()) {
            UserId = SharedPreference.getUserid(this);
            isUserLoggedIn=true;
        }else{
            UserId=" ";
        }
        productId = getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTID);
        if( getIntent().hasExtra(Constants.PreferenceConstants.PRODUCTNAME)) {
            ProName = getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTNAME);
        }
        if(getIntent().hasExtra(Constants.PreferenceConstants.OPTIONID)) {
            optionId = getIntent().getStringExtra(Constants.PreferenceConstants.OPTIONID);
        }else{
            optionId="";
        }

        try {
            if (getIntent().hasExtra(Constants.PreferenceConstants.PRODUCTIMG)) {
                isPreSetData=true;
                tempImage = getIntent().getStringExtra(Constants.PreferenceConstants.PRODUCTIMG);
                String proPrice=getIntent().getStringExtra(Constants.PreferenceConstants.PRODNETPRICE);
                setInitialScreen(tempImage, ProName, proPrice);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("ProductId", "" + productId);
        textviewTitle.setText(ProName);
        textviewTitle.setTextSize(16);
        prefClass= new PrefClass(this);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                strSelectedCurrency=prefClass.getSelectedCurrency();
                String value= new DecimalFormat("0.000").format(Double.parseDouble(currencyData.getValue()));
                currencyValue= Double.parseDouble(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
            }
        }

        setOnClickListner();
        getContentFromApi(UserId, productId, optionId);
    }

    private void setInitialScreen(String tempImage, String proName, String prodPrice) {
        scrollView.setVisibility(View.VISIBLE);
        mainProgressFrame.setVisibility(View.GONE);
        progressFrame.setVisibility(View.GONE);
        resetVariable();
        textviewTitle.setText(" ");
        ProgressBar progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite wave = new Wave();
        progressBar.setIndeterminateDrawable(wave);
        Glide.with(this).load(tempImage).diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.img_icon).into(pView);
        relatedLayout.setVisibility(View.VISIBLE);
        recentlyLayout.setVisibility(View.VISIBLE);
        recentRv.setVisibility(View.GONE);
        relatedRv.setVisibility(View.GONE);
        skeletalRecent.setVisibility(View.VISIBLE);
        skeletalRelated.setVisibility(View.VISIBLE);
        optionAvailableLayout.setVisibility(View.GONE);
        btnAllReviews.setVisibility(View.GONE);
        reviewList= new ArrayList<>();
        askQuestionList = new ArrayList<>();
        reviewTabTitle.setText("Review(0)");
        setRatingStar((float)0.00);
        tvAvailability.setText("");
        tabAddTocart.setText("");
        fixedAddTocart.setText("");
        askQuestionList= new ArrayList<>();
        recyclerAskQuestion.setVisibility(View.GONE);
        recyclerReview.setVisibility(View.GONE);
        pbOnBtn.setVisibility(View.VISIBLE);
        ProductName.setText(proName);
        prod_price.setVisibility(View.VISIBLE);
        prod_price.setText(prodPrice);
        prod_price.setTextColor(getResources().getColor(R.color.light_black));
        fixedQtySpin.setSelection(0);
        qtySpin.setSelection(0);
        amountCrossLayout.setVisibility(View.GONE);
        offerPercentLayout.setVisibility(View.GONE);
        spl_price.setVisibility(View.GONE);
        fixedQuantityLayout.setWeightSum(2);
        quantityLayout.setWeightSum(2);
        packLayoutFloat.setVisibility(View.GONE);
        packLayoutFixed.setVisibility(View.GONE);
        wvProductDesc.loadData(productDescText,"text/html","UTF-8");
        tabDescription.performClick();
    }

    private void resetVariable() {
        productDescText="";
        dPackPrice=0.00;
        netPrice=0.00;
        dProductPrice=0.00;
        dProductTax=0.00;
        taxPercent=0.00;
        isPreSetData=true;
        isFloatingSpin=false;
        isFixedSpin= false;
        isPackAvailable=false;
        p_spclprice="0.00";
        ratingValue= "0";
        reviewText="";
        strReviewList="";
        discountQty="0";
        packPrice="";
        P_price="0.00";
        totalProductQty=1;
        selectedQty=1;
        reviewListSize=0;
        limit=0;
    }

    private void getContentFromApi(final String userId, final String pId, String optionId) {
        productDetailsPresentarInteractor = new ProductDetailsPresentar(this);
        globalUtils = new GlobalUtils();
        scrollView.setVisibility(View.GONE);
        mainProgressFrame.setVisibility(View.VISIBLE);
        fixedButtonLayout.setVisibility(View.GONE);
        textviewTitle.setText("");
        if (GlobalUtils.isNetworkAvailable(this)) {
            productDetailsPresentarInteractor.sendProductDetailsRequest(userId, pId, optionId);
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }

    private void addToRecent(String uId,String pId) {
        HashMap<String, String> reqMap= new HashMap<>();
        reqMap.put("userid",uId);
        reqMap.put("deviceid",SharedPreference.getDeviceId(ProductDetailActivity.this));
        reqMap.put("productid",pId);

        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<JSONObject> call= restInterface. addToRecentList(globalUtils.getKey(), globalUtils.getapidate(), reqMap);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, retrofit2.Response<JSONObject> response) {
                Log.e("AddToRecentRES",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.e("AddToRecentEXC",":"+t);
            }
        });
    }

    public void setUpContentView() {
        mainProgressFrame = findViewById(R.id.frame_fullwidth_pbar);
        tvNoReview = findViewById(R.id.tv_no_review);
        relatedLayout= findViewById(R.id.layout_related);
        recentlyLayout= findViewById(R.id.layout_recent);
        skeletalRecent= findViewById(R.id.skel_rv_recent);
        skeletalRelated= findViewById(R.id.skel_rv_related);
        brandLayout= findViewById(R.id.ll_brand_tab);
        optionAvailableLayout= findViewById(R.id.available_option_layout);
        productOptionsLayout= findViewById(R.id.ll_options);
        pbOnBtn= findViewById(R.id.pb_cartBtn);
        packLayoutFixed= findViewById(R.id.ll_pack_fixed);
        packLayoutFloat= findViewById(R.id.ll_pack_float);
        tvPackTextFixed= findViewById(R.id.tv_pack_text_fixed);
        tvPackTextFloat= findViewById(R.id.tv_pack_text_float);
        progressFrame= findViewById(R.id.progress_frame);
        pView = findViewById(R.id.ivProductView);
        spl_price = findViewById(R.id.Real_price);
        prod_price = findViewById(R.id.tvoldAmount);
        ProductName = findViewById(R.id.ProductText);
        product_img1 = findViewById(R.id.ImageP1);
        product_img2 = findViewById(R.id.ImageP2);
        product_img3 = findViewById(R.id.ImageP3);
//        recyclerList = findViewById(R.id.Recycler_List);
        relatedRv = findViewById(R.id.rvRelated);
        recentRv = findViewById(R.id.rvRecently);
        recyclerReview = findViewById(R.id.rv_reviews);
        recyclerAskQuestion = findViewById(R.id.rv_ask_question);
        cardDropLine = findViewById(R.id.dropLine_layout);
        demo1 = findViewById(R.id.llpDemo1);
        demo2 = findViewById(R.id.llpDemo2);
        demo3 = findViewById(R.id.llpDemo3);
        tabDescription = findViewById(R.id.ll_description_tab);
        tabReviews = findViewById(R.id.ll_reviews_tab);
        tabAskQuestion = findViewById(R.id.ll_ask_tab);
        tabWishlist = findViewById(R.id.layout_tab_wishlist);
        buttonLayout = findViewById(R.id.layout_buttons);
        fixedButtonLayout = findViewById(R.id.layout_buttons_fixed);
        fixedButtonLayout.setVisibility(View.VISIBLE);
        buttonLayout.setVisibility(View.GONE);
        tabAddTocart= findViewById(R.id.tv_btn_addToCart);
        tabViewCart= findViewById(R.id.tv_btn_viewCart);
        fixedAddTocart= findViewById(R.id.tv_btn_addToCart_fixed);
        fixedViewCart= findViewById(R.id.tv_btn_viewCart_fixed);
        quantityLayout= findViewById(R.id.quantity_layout);
        tabShareProduct = findViewById(R.id.layout_tab_shareProduct);
        linearTabs = findViewById(R.id.ll_tabs);
        descTabTitle = findViewById(R.id.tab_text_desc);
        reviewTabTitle = findViewById(R.id.tab_text_review);
        askTabTitle = findViewById(R.id.tab_text_ask);
        wvProductDesc = findViewById(R.id.tv_product_desc);

        offerLayoutContainer = findViewById(R.id.offer_txt_layout);
        offerLayout1 = findViewById(R.id.offer_layout1);
        offerLayout2 = findViewById(R.id.offer_layout2);
        tvOfferTitle1 = findViewById(R.id.tv_offer_title1);
        tvOfferTitle2 = findViewById(R.id.tv_offer_title2);
        tvOfferText1 = findViewById(R.id.tv_offer_text1);
        tvOfferText2 = findViewById(R.id.tv_offer_text2);
        tvMoreOffer = findViewById(R.id.tv_more_offer);

        wvProductDesc.getSettings().setDomStorageEnabled(true);
        wvProductDesc.getSettings().setJavaScriptEnabled(true);
        wvProductDesc.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvProductDesc.getSettings().setPluginState(WebSettings.PluginState.ON);
        wvProductDesc.getSettings().setMediaPlaybackRequiresUserGesture(false);
        wvProductDesc.setWebViewClient(new WebViewClient());

        tvAvailability = findViewById(R.id.tv_availability);
        tvCheckPincode= findViewById(R.id.tv_check_pincode);
        notAddedHeart_img = findViewById(R.id.iv_not_added_heart);
        addedHeart_img = findViewById(R.id.iv_added_heart);
        tvWishlistText= findViewById(R.id.tv_wishlist_text);
        tvDeliveryPincodeInfo = findViewById(R.id.tv_delivery_info);
        tvBrandName = findViewById(R.id.tv_brand_name);
        reviewContainer = findViewById(R.id.reviews_layout);
        askContainer = findViewById(R.id.ask_question_layout);
        btnWriteReview = findViewById(R.id.tv_btn_write_review);
        offerPercentLayout = findViewById(R.id.product_offer_layout);
        amountCrossLayout = findViewById(R.id.amount_cross_line);
        tvPercentOffer = findViewById(R.id.tv_prod_offer_percent);
        tvReviewCount = findViewById(R.id.tv_review_count);
        pView = findViewById(R.id.ivProductView);
        pincodeContainer= findViewById(R.id.pincode_container);
        layoutChange = findViewById(R.id.layout_change_from);
        scrollView = findViewById(R.id.prod_details_scroll);
        mainLayout = findViewById(R.id.prod_details_main_layout);
        fixedQuantityLayout = findViewById(R.id.quantity_layout_float);
        fixedQtySpin= findViewById(R.id.spinner1_float);
        bottNavigationView= findViewById(R.id.bott_nav_prod);
        btnAllReviews= findViewById(R.id.ll_btn_all_review);
        checkZipLayout= findViewById(R.id.pincode_layout);
        View v = bottNavigationView.findViewById(R.id.action_four);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        View badge = LayoutInflater.from(this).inflate(R.layout.cart_icon_layout, itemView, true);
        textCartItemCount = badge.findViewById(R.id.cart_badge);
        if(!SharedPreference.getUserid(ProductDetailActivity.this).isEmpty() &&
                !SharedPreference.getCartCount(this).isEmpty()) {
            textCartItemCount.setVisibility(View.VISIBLE );
//            textCartItemCount.setTextColor(getResources().getColor(R.color.white));
            textCartItemCount.setText(SharedPreference.getCartCount(this));
        }else{
            textCartItemCount.setVisibility(View.GONE);
        }
        bottNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        relatedRv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        relatedRv.setLayoutManager(manager);

        recentRv.setHasFixedSize(true);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recentRv.setLayoutManager(manager1);
        addDataSpinner1();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UserId=SharedPreference.getUserid(ProductDetailActivity.this);
        if(requestCode==Constants.PreferenceConstants.BACKFROMPRODDETAILS) {
            isUserLoggedIn = true;
        }
    }

    public void setOnClickListner() {
        brandLayout.setOnClickListener(this);
        pView.setOnClickListener(this);
        demo1.setOnClickListener(this);
        demo2.setOnClickListener(this);
        demo3.setOnClickListener(this);
        btnAllReviews.setOnClickListener(this);
        tabDescription.setOnClickListener(this);
        tvReviewCount.setOnClickListener(this);
        tabReviews.setOnClickListener(this);
        tabAskQuestion.setOnClickListener(this);
        cardDropLine.setOnClickListener(this);
        btnWriteReview.setOnClickListener(this);
        tabWishlist.setOnClickListener(this);
        tabShareProduct.setOnClickListener(this);
        tabAddTocart.setOnClickListener(this);
        tabViewCart.setOnClickListener(this);
        fixedAddTocart.setOnClickListener(this);
        fixedViewCart.setOnClickListener(this);
        checkZipLayout.setOnClickListener(this);
        qtySpin.setOnItemSelectedListener(this);
        fixedQtySpin.setOnItemSelectedListener(this);
        productOptionsLayout.setOnClickListener(this);
        tvMoreOffer.setOnClickListener(this);
        qtySpin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isFixedSpin = true;
                ((TextView)qtySpin.getChildAt(0)).setText(selectedQty + "");
                ((TextView)fixedQtySpin.getChildAt(0)).setText(selectedQty + "");
                if(qtySpin.getSelectedItem().toString().equalsIgnoreCase("More")) {
                    openQuantityDialog();
                }

                return false;
            }
        });
        fixedQtySpin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isFloatingSpin = true;
                ((TextView)qtySpin.getChildAt(0)).setText(selectedQty + "");
                ((TextView)fixedQtySpin.getChildAt(0)).setText(selectedQty + "");

                if(fixedQtySpin.getSelectedItem().toString().equalsIgnoreCase("More")) {
                    openQuantityDialog();
                }
                return false;
            }
        });
        bottNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            getSupportFragmentManager().popBackStack();
            Fragment fragment;
            bottNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
            switch (item.getItemId()) {
                case R.id.action_one:
                    Intent intentHome= new Intent(ProductDetailActivity.this, HomeActivity.class);
                    intentHome.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentHome);
                    finish();
                    return true;
                case R.id.action_two:
                    textviewTitle.setText("Categories");
                    fragment = new CategoryFragment();
                    loadFragment(fragment, CategoryFragment.class.getSimpleName());
                    return true;
                case R.id.action_three:
                    if(isUserLoggedIn) {
                        textviewTitle.setText("My Account");
                        fragment = new MeFragment();
                        loadFragment(fragment, MeFragment.class.getSimpleName());
                     }else{
                Intent intLogin= new Intent(ProductDetailActivity.this, LoginActivity.class);
                intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, ProductDetailActivity.class.getSimpleName());
                startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
            }
                    return true;
                case R.id.action_four:
                    if(isUserLoggedIn) {
                        textviewTitle.setText("Cart");
                        Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intent);
                        finish();
                    }else{
                        Intent intLogin= new Intent(ProductDetailActivity.this, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, ProductDetailActivity.class.getSimpleName());
                        startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                    return true;
                case R.id.action_five:
                    textviewTitle.setText("Search");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PreferenceConstants.BACKFROM,"PROD");
                    fragment = new SearchFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment,SearchFragment.class.getSimpleName());
                    return true;
            }
            return false;
        }

    };
    private void loadFragment(Fragment fragment, String fragName) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_prod_container, fragment);
        transaction.addToBackStack(fragName);
        transaction.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }
        finish();
    }

    public void addDataSpinner1() {
        qtySpin = findViewById(R.id.spinner1);
        ArrayList<String> qtyList = new ArrayList<>();
        qtyList.add("1");
        qtyList.add("2");
        qtyList.add("3");
        qtyList.add("4");
        qtyList.add("5");
        qtyList.add("6");
        qtyList.add("7");
        qtyList.add("8");
        qtyList.add("9");
        qtyList.add("More");
        final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, qtyList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtySpin.setAdapter(dataAdapter);
        final ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, qtyList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fixedQtySpin.setAdapter(dataAdapter2);
    }


    @Override
    public void onItemSelected(final AdapterView<?> adapterView, View view, int i, long l) {
        Log.e("selPos",":"+i);
//        ((TextView) adapterView.getChildAt(0)).setGravity(Gravity.CENTER);
        ((TextView)qtySpin.getChildAt(0)).setText(selectedQty + "");
        ((TextView)fixedQtySpin.getChildAt(0)).setText(selectedQty + "");

        if (isFixedSpin || isFloatingSpin) {
            if (i == 9) {
               openQuantityDialog();

            } else {
                int qty1= Integer.parseInt(qtySpin.getSelectedItem().toString());
                int qty2= Integer.parseInt(fixedQtySpin.getSelectedItem().toString());
                    if(qty1 >totalProductQty || qty2 >totalProductQty) {
                        qtySpin.setSelection(0);
                        fixedQtySpin.setSelection(0);
                        if(totalProductQty==0){
                            Toast.makeText(ProductDetailActivity.this, "Product not in stock!", Toast.LENGTH_LONG).show();
                        }else if(totalProductQty==1){
                            Toast.makeText(ProductDetailActivity.this, "Only 1 item left in the stock!", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ProductDetailActivity.this, "Only " + totalProductQty + " left in the stock. ", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        selectedQty = i + 1;
                        qtySpin.setSelection(i);
                        fixedQtySpin.setSelection(i);
                    calculatePriceQty(selectedQty);
                    }
//                addToCart(true);
            }
//                qtySpin.setAdapter(dataAdapter);
        }
    }

    private void openQuantityDialog() {
        ((TextView)qtySpin.getChildAt(0)).setText(selectedQty + "");
        ((TextView)fixedQtySpin.getChildAt(0)).setText(selectedQty + "");
        if(selectedQty<10) {
            qtySpin.setSelection(selectedQty-1, false);
            fixedQtySpin.setSelection(selectedQty-1, false);
        }else{
            qtySpin.setSelection(0, false);
            fixedQtySpin.setSelection(0, false);
        }
        final Dialog dialog = new Dialog(ProductDetailActivity.this);
        dialog.setContentView(R.layout.enter_quantity_layout);
        dialog.show();
        etQuantity = dialog.findViewById(R.id.et_quantity);
        TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        TextView tvApply = dialog.findViewById(R.id.tv_apply);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etQuantity.getText().toString().isEmpty() && Integer.parseInt(etQuantity.getText().toString()) > 0) {
                    if (Integer.parseInt(etQuantity.getText().toString()) <= totalProductQty) {
                        selectedQty = Integer.parseInt(etQuantity.getText().toString());
                        calculatePriceQty(selectedQty);
                        ((TextView)qtySpin.getChildAt(0)).setText(selectedQty + "");
                        ((TextView)fixedQtySpin.getChildAt(0)).setText(selectedQty + "");
                        dialog.dismiss();
                    } else {
                        qtySpin.setSelection(0);
                        fixedQtySpin.setSelection(0);
                        if(totalProductQty==0){
                            Toast.makeText(ProductDetailActivity.this, "Product not in stock!", Toast.LENGTH_LONG).show();
                        }else if(totalProductQty==1){
                            Toast.makeText(ProductDetailActivity.this, "Only 1 item left in the stock!", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(ProductDetailActivity.this, "Only " + totalProductQty + " left in the stock. ", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(ProductDetailActivity.this, "Quantity should be at least 1", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void calculatePriceQty(int selectedQty) {
        int totalQty=0;
        Double newNetPrice=0.00,price=0.00, taxAmount=0.00,discountpercent=0.00, newPrice=0.00;
        totalQty= Integer.parseInt(discountQty);
        newNetPrice= netPrice * selectedQty*currencyValue;
        prod_price.setText(currencySymbol+ new DecimalFormat("0.00").format(newNetPrice));
        if (Double.parseDouble(p_spclprice) == 0.00) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                prod_price.setTextColor(getResources().getColor(R.color.light_black));
            }
            amountCrossLayout.setVisibility(View.GONE);
            offerPercentLayout.setVisibility(View.GONE);
            spl_price.setVisibility(View.GONE);

            if(isPackAvailable && selectedQty >=totalQty){
                price= selectedQty *dPackPrice;
                Double priceWithtax= price + (taxPercent*0.01);
                prod_price.setText(currencySymbol+  new DecimalFormat("0.00").format(priceWithtax));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                prod_price.setTextColor(getResources().getColor(R.color.gray));
            }
            spl_price.setVisibility(View.VISIBLE);
            amountCrossLayout.setVisibility(View.VISIBLE);
            offerPercentLayout.setVisibility(View.VISIBLE);
            taxAmount= Double.parseDouble(p_spclprice)*(taxPercent/100);
            discountpercent = (Double.parseDouble(P_price) - Double.parseDouble(p_spclprice)) / Double.parseDouble(P_price) * 100;
//            String spcl_price = currencySymbol + " " + new DecimalFormat("0").format(discountpercent*currencyValue);
            String spcl_price = new DecimalFormat("0").format(discountpercent*currencyValue);

            tvPercentOffer.setText(spcl_price + "%");
            double oldprice= selectedQty *netPrice;

            if(isPackAvailable && selectedQty >=totalQty){
                price= selectedQty *packOfPrice;

                spl_price.setText(currencySymbol+ new DecimalFormat("0.00").format(price));
                prod_price.setText(currencySymbol +  new DecimalFormat("0.00").format(oldprice));
            }else{
//                double newPrice= netPrice*selectedQty;
                newPrice=(Double.parseDouble(p_spclprice)+taxAmount)*currencyValue*selectedQty;
                spl_price.setText(currencySymbol + new DecimalFormat("0.00").format(newPrice));
                prod_price.setText(currencySymbol + new DecimalFormat("0.00").format(oldprice));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    private boolean isViewVisible(View view) {
        Rect scrollBounds = new Rect();
        scrollView.getDrawingRect(scrollBounds);

        float top = view.getY()+1000;
        float bottom = view.getHeight();
//        Log.e("ScrollBounds","\n top: "+scrollBounds.top+
//                " & viewTop:"+top+"\n bottom:"+scrollBounds.bottom+" & viewBottom:"+bottom);

        if (scrollBounds.top <= top && scrollBounds.bottom >= bottom) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }


        bottNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        scrollView.setFocusableInTouchMode(true);
        scrollView.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//                    scrollView.scrollTo(0, (int)fixedAddTocart.getY());
                    int height= v.getHeight();

                    Boolean isvisible= isViewVisible(quantityLayout);
                    if(!isvisible){
                        buttonLayout.setVisibility(View.VISIBLE);
                        fixedButtonLayout.setVisibility(View.GONE);
                        bottNavigationView.setVisibility(View.VISIBLE);
                    }else{
                        buttonLayout.setVisibility(View.GONE);
                        fixedButtonLayout.setVisibility(View.VISIBLE);
                        bottNavigationView.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
    //@@@@@@@@@@@@@@@@@@@@@@@@ getProductBasicsDetailsResponse
    @Override
    public void getProductDataResponse(Object object) {
        Log.e("1stApi",":"+new GsonBuilder().setPrettyPrinting().create().toJson(object));
        productdetailsDTO = (ProductBasicDetailsResult) object;
        productId = productdetailsDTO.getResult().getProduct().getData().getProductId();
        optionId = productdetailsDTO.getResult().getProduct().getData().getOptionId();
        if(GlobalUtils.isNetworkAvailable(ProductDetailActivity.this)) {
            getRemainingDetails();
        }else{
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
        progressFrame.setVisibility(View.GONE);
        GlobalUtils.hidedialog();
        scrollView.setVisibility(View.VISIBLE);
        mainProgressFrame.setVisibility(View.GONE);
        fixedButtonLayout.setVisibility(View.VISIBLE);
        scrollView.scrollTo(0, pView.getTop());
        String message = productdetailsDTO.getMessage();

        if(!productdetailsDTO.getResult().getOfferText().getContent().isEmpty()) {
            topMsgLayout.setVisibility(View.VISIBLE);
            String offerText = productdetailsDTO.getResult().getOfferText().getContent();
//            tvShoppingOffer.setText(offerText);
        }else{
//            topMsgLayout.setVisibility(View.GONE);
        }
        sliderImageList = new ArrayList<>();
        ArrayList<ProductBasicDetailsResult.Result.Product.Image> listimage = productdetailsDTO.getResult().getProduct().getImage();
        Image imageData = new Image();
        imageData.setImageId(productdetailsDTO.getResult().getProduct().getData().getProductId());
        imageData.setImageUrl(productdetailsDTO.getResult().getProduct().getData().getProductImage());
        imageData.setImageOrder("top");
        sliderImageList.add(0, imageData);
        isPreSetData=false;
        for (int i = 0; i < listimage.size(); i++) {
            Image image = new Image();
            image.setImageId(listimage.get(i).getImageId());
            image.setImageUrl(listimage.get(i).getImageUrl());
            image.setImageOrder(listimage.get(i).getImageOrder());
            sliderImageList.add(i + 1, image);
            if (i == 0) {
                demo1.setVisibility(View.VISIBLE);
                image1 = listimage.get(i).getImageUrl();
                String baseUrl = "https://www.gaurashtra.com/image/cache/";
                String addimage = image1.replace(".jpg", "-60x80.jpg");
                imageurl1 = baseUrl + addimage;
                if (!ProductDetailActivity.this.isFinishing()) {
                    Glide.with(ProductDetailActivity.this).load(imageurl1).skipMemoryCache(true).placeholder(R.drawable.img_icon).into(product_img1);
                }
            } else if (i == 1) {
                demo2.setVisibility(View.VISIBLE);
                image2 = listimage.get(i).getImageUrl();
                String baseUrl = "https://www.gaurashtra.com/image/cache/";
                String addimage = image2.replace(".jpg", "-60x80.jpg");
                String imageurl2 = baseUrl + addimage;
                if (!ProductDetailActivity.this.isFinishing()) {
                    Glide.with(ProductDetailActivity.this).asBitmap().load(imageurl2).placeholder(R.drawable.img_icon).into(product_img2);
                }
            } else if (i == 2) {
                demo2.setVisibility(View.VISIBLE);
                image3 = listimage.get(i).getImageUrl();
                String baseUrl = "https://www.gaurashtra.com/image/cache/";
                String addimage = image3.replace(".jpg", "-60x80.jpg");
                String imageurl3 = baseUrl + addimage;
                if (!ProductDetailActivity.this.isFinishing()) {
                    Glide.with(ProductDetailActivity.this).asBitmap().load(imageurl3).placeholder(R.drawable.img_icon).into(product_img3);
                }
            }
        }
        if(productdetailsDTO.getResult().getProduct().getData().getAddedWishlist().equalsIgnoreCase("Y")){
            notAddedHeart_img.setVisibility(View.GONE);
            addedHeart_img.setVisibility(View.VISIBLE);
            tvWishlistText.setText("Added to Wishlist");
        }else{
            notAddedHeart_img.setVisibility(View.VISIBLE);
            addedHeart_img.setVisibility(View.GONE);
            tvWishlistText.setText("Add to Wishlist");
        }

        productDescText = Jsoup.parse("<html>" + productdetailsDTO.getResult().getProduct().getData().getProductDescription() + "</html>").text();
        String productImgUrl = productdetailsDTO.getResult().getProduct().getData().getProductImage();
        String baseUrl = "https://www.gaurashtra.com/image/cache/";
        String addimage = productImgUrl.replace(".jpg", "-300x400.jpg");
        String TopImageList = baseUrl + addimage;
        if(!isPreSetData) {
            try {
                Glide.with(getApplicationContext()).load(TopImageList).diskCacheStrategy(DiskCacheStrategy.RESOURCE).placeholder(R.drawable.img_icon).into(pView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        productId = productdetailsDTO.getResult().getProduct().getData().getProductId();
        P_price = productdetailsDTO.getResult().getProduct().getData().getProductPrice();
        productName = productdetailsDTO.getResult().getProduct().getData().getProductName();
        if(productName.contains("amp;")){
            productName = productName.replaceAll("amp;","");
        }
        p_spclprice = productdetailsDTO.getResult().getProduct().getData().getSpecialPrice();
        discountQty= productdetailsDTO.getResult().getProduct().getData().getDiscountQuantity();



        ProductName.setText(productName);
        textviewTitle.setText(productName);
        int reviewsCount = Integer.parseInt(productdetailsDTO.getResult().getProduct().getReview().getCount());
        if(reviewsCount==0){
            tvReviewCount.setText("0 Review");
            reviewTabTitle.setText("Review(0)");
        }else if (reviewsCount <= 1) {
            tvReviewCount.setText(productdetailsDTO.getResult().getProduct().getReview().getCount() + " Review");
            reviewTabTitle.setText("Review(" + productdetailsDTO.getResult().getProduct().getReview().getCount() + ")");
            Float averageRating = Float.parseFloat(productdetailsDTO.getResult().getProduct().getReview().getAverage());
            setRatingStar(averageRating);
        } else {
            reviewTabTitle.setText("Reviews(" + productdetailsDTO.getResult().getProduct().getReview().getCount() + ")");
            tvReviewCount.setText(productdetailsDTO.getResult().getProduct().getReview().getCount() + " Reviews");
            Float averageRating = Float.parseFloat(productdetailsDTO.getResult().getProduct().getReview().getAverage());
            setRatingStar(averageRating);
        }

        String between = "", videoStr = "", url = "", newIframe = "", newDescText="";
        try {
            if (productDescText.contains("<iframe")) {
                between = StringUtils.substringBetween(productDescText, "<iframe", "</iframe>");
                if (!between.isEmpty()) {
                    Log.e("between", ":" + between);
                    String searchUrl[] = between.split("\\s+");
                    for (int i=0; i<searchUrl.length;i++) {
                        if (searchUrl[i].contains("www.youtube.com")) {
                            videoStr = searchUrl[i];
                            Log.e("splited", ":"+"\n"+i+"-->" + videoStr);
                            break;
                        }
                    }
                    if(!videoStr.isEmpty()) {
                        if (videoStr.contains("http:")) {
                            url = videoStr.replaceAll("http:", "https");
                        } else {
                             url = videoStr.replace("src=\"//www.youtube.com","src=\"https://www.youtube.com");

                        }
                        newIframe = "<iframe " + url + " width=\"auto\" height=\"240\"  style=\"border:none;\">";
                        String oldIframe= "<iframe"+between;
                        Log.e("newIframe", ":" + newIframe+"\n oldIframe: "+oldIframe);
                        if(productDescText.contains(oldIframe)) {
                            newDescText = productDescText.replace(oldIframe, newIframe);
                            Log.e("DescText", ":" + newDescText);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if(newDescText.isEmpty()){
            newDescText= productDescText;
        }
        wvProductDesc.loadData(newDescText,"text/html","UTF-8");
        brandName= productdetailsDTO.getResult().getProduct().getData().getBrandName();
        brandId= productdetailsDTO.getResult().getProduct().getData().getBrandId();
        tvBrandName.setText(brandName);
        displayProductDesc();
        seoUrl = productdetailsDTO.getResult().getProduct().getData().getSeoUrl();
        optionId=productdetailsDTO.getResult().getProduct().getData().getOptionId();
        optionValueId=productdetailsDTO.getResult().getProduct().getData().getOptionValueId();
        optionType= productdetailsDTO.getResult().getProduct().getData().getOptionType();
        if(Integer.parseInt(optionId)>0){
            getOptionList();
            isOptionAvailable=true;
            optionAvailableLayout.setVisibility(View.VISIBLE);
        }else{
            isOptionAvailable=false;
            optionAvailableLayout.setVisibility(View.GONE);
        }
        optionValueId=productdetailsDTO.getResult().getProduct().getData().getOptionValueId();
        totalProductQty=Integer.parseInt(productdetailsDTO.getResult().getProduct().getData().getProductQuantity());
        tabAddTocart.setVisibility(View.VISIBLE);
        fixedAddTocart.setVisibility(View.VISIBLE);
        pbOnBtn.setVisibility(View.GONE);
        if (totalProductQty > 0) {
            tvAvailability.setText("In Stock");
            tabAddTocart.setText("Add to cart");
            fixedAddTocart.setText("Add to cart");
        } else {
            tvAvailability.setText("Out of stock");
            tabAddTocart.setText("Notify Me");
            fixedAddTocart.setText("Notify Me");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvAvailability.setTextColor(getResources().getColor(R.color.gray));
            }
        }

        DecimalFormat df = new DecimalFormat("0.00");
        Double tax=0.00;
        try {
            if (!productdetailsDTO.getResult().getProduct().getData().getTaxRate().isEmpty()) {
                taxPercent = Double.parseDouble(productdetailsDTO.getResult().getProduct().getData().getTaxRate());
                tax= taxPercent /100 *Double.parseDouble(P_price);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        int discQty=Integer.parseInt(discountQty);
        if(discQty>0) {
            packPrice = productdetailsDTO.getResult().getProduct().getData().getDiscountPrice();
            dPackPrice= Double.parseDouble(packPrice);
            isPackAvailable=true;
            setPackLayout();
        }
        netPrice = (Double.parseDouble(P_price) * (taxPercent / 100) + Double.parseDouble(P_price))*currencyValue;
        prod_price.setText(currencySymbol+ df.format(netPrice));
        calculatePriceQty(1);
        if (Double.parseDouble(p_spclprice) == 0.00) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                prod_price.setTextColor(getResources().getColor(R.color.light_black));
            }
            amountCrossLayout.setVisibility(View.GONE);
            offerPercentLayout.setVisibility(View.GONE);
            spl_price.setVisibility(View.GONE);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                prod_price.setTextColor(getResources().getColor(R.color.gray));
            }
            spl_price.setVisibility(View.VISIBLE);
            amountCrossLayout.setVisibility(View.VISIBLE);
            offerPercentLayout.setVisibility(View.VISIBLE);
            double taxAmount= Double.parseDouble(p_spclprice)*(taxPercent/100);
            Double discountpercent = (Double.parseDouble(P_price) - Double.parseDouble(p_spclprice)) / Double.parseDouble(P_price) * 100;
//            String spcl_price = currencySymbol + " " + new DecimalFormat("0").format(discountpercent*currencyValue);
            String spcl_price = new DecimalFormat("0").format(discountpercent*currencyValue);

            tvPercentOffer.setText(spcl_price + "%");
            spl_price.setText(currencySymbol+ df.format((Double.parseDouble(p_spclprice)+taxAmount)*currencyValue));
        }
        staticData = new ArrayList<>();
        TextView tvOffersLabel = findViewById(R.id.tv_available_offer_label);
        staticData = productdetailsDTO.getResult().getStaticContentData();
        Log.e("staticListSize",":"+staticData.size());
        if(staticData.size()>0) {
            offerLayoutContainer.setVisibility(View.VISIBLE);
            tvOfferTitle1.setText(staticData.get(0).getTitle());
            tvOfferText1.setText(staticData.get(0).getContent());
        }
        if(staticData.size()>1){
            tvOffersLabel.setText("Available offers");
            offerLayoutContainer.setVisibility(View.VISIBLE);
            tvOfferTitle1.setText(staticData.get(1).getTitle());
            tvOfferText1.setText(staticData.get(1).getContent());
        }
        if(staticData.size()>2){
            int count = staticData.size()-2;
            tvMoreOffer.setVisibility(View.VISIBLE);
            tvMoreOffer.setText("+"+String.valueOf(count)+" more");
        }
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@ getProductRemainingDetailsResponse
    private void getRemainingDetails() {
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        HashMap<String, String> requestMap= new HashMap<>();
        requestMap.put("userid",UserId);
        requestMap.put("productid",productId);
        requestMap.put("optionvalueid", optionId);
        Log.e("RequestParam",":"+requestMap);
        Call<ProductRemainingResult> call = restInterface.callRemainingDetails(globalUtils.getKey(), globalUtils.getapidate(), requestMap);
        call.enqueue(new Callback<ProductRemainingResult>() {
            @Override
            public void onResponse(Call<ProductRemainingResult> call, retrofit2.Response<ProductRemainingResult> response) {
                Log.e("ResProductDetails2",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                if (response != null) {
                    if(response.body().getSuccess()==1){
                        productRemainingDetails = response.body();
                        reviewList = new ArrayList<>();
                        reviewList = response.body().getResult().getProduct().getReview().getData();
                        if(reviewList.size()>0) {
                            tabReviews.setOnClickListener(ProductDetailActivity.this);
                            reviewListSize = reviewList.size();
                            if (reviewListSize > 4) {
                                limit = 5;
                                strReviewList = (new Gson()).toJson(reviewList);
                                btnAllReviews.setVisibility(View.VISIBLE);
                            } else {
                                limit = reviewListSize;
                                Log.e("CountRev", ":" + limit);
                                btnAllReviews.setVisibility(View.GONE);
                            }

                            LinearLayoutManager manager = new LinearLayoutManager(ProductDetailActivity.this, LinearLayoutManager.VERTICAL, false) {
                                @Override
                                public boolean canScrollVertically() {
                                    return false;
                                }
                            };
                            recyclerReview.setLayoutManager(manager);
                            tvNoReview.setVisibility(View.GONE);
                            recyclerReview.setVisibility(View.VISIBLE);
                            reviewAdapter = new ReviewAdapter(ProductDetailActivity.this, reviewList, limit);
                            recyclerReview.setAdapter(reviewAdapter);

                            int revCount = Integer.parseInt(productdetailsDTO.getResult().getProduct().getReview().getCount());
                            if (revCount <= 1) {
                                reviewTabTitle.setText("Review(" + productdetailsDTO.getResult().getProduct().getReview().getCount() + ")");
                            } else {
                                reviewTabTitle.setText("Reviews(" + productdetailsDTO.getResult().getProduct().getReview().getCount() + ")");
                            }
                        }else{
                            recyclerReview.setVisibility(View.GONE);
                            tvNoReview.setVisibility(View.VISIBLE);
                        }
                        askQuestionList = new ArrayList<>();
                        askQuestionList = response.body().getResult().getProduct().getAskQuestion();

                        List<ProductRemainingResult.Result.RelatedProductDatum> relateddata = new ArrayList<>();

                        relateddata = response.body().getResult().getRelatedProductData();
                        if (relateddata.size() > 0) {
                            skeletalRelated.setVisibility(View.GONE);
                            relatedRv.setVisibility(View.VISIBLE);
                            relatedAdapter = new RelatedAdapter(ProductDetailActivity.this, relateddata, ProductDetailActivity.this);
                            relatedRv.setAdapter(relatedAdapter);
                        }else{
                            relatedLayout.setVisibility(View.VISIBLE);
                        }
                        List<ProductRemainingResult.Result.RecentlyPurchasedDatum> recentlydata = new ArrayList<>();
                        recentlydata = response.body().getResult().getRecentlyPurchasedData();
                        Log.e("recentListSize",":"+recentlydata.size());
                        if(recentlydata.size()>0) {
                            skeletalRecent.setVisibility(View.GONE);
                            recentRv.setVisibility(View.VISIBLE);
                            recentAdapter = new RecentAdapter(ProductDetailActivity.this, recentlydata, ProductDetailActivity.this);
                            recentRv.setAdapter(recentAdapter);
                        }else{
                            recentlyLayout.setVisibility(View.GONE);
                        }
                        addToRecent(UserId, productId);
                    }else{
                        Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ProductRemainingResult> call, Throwable t) {

            }
        });
    }

    private void setPackLayout() {
        packLayoutFloat.setVisibility(View.VISIBLE);
        packLayoutFixed.setVisibility(View.VISIBLE);
        quantityLayout.setWeightSum(3);
        fixedQuantityLayout.setWeightSum(3);
//        netPrice = (Double.parseDouble(P_price) * (taxPercent / 100) + Double.parseDouble(P_price))*currencyValue;

        packOfPrice =(dPackPrice * (taxPercent / 100) + dPackPrice)*currencyValue;
        tvPackTextFloat.setText(discountQty+" or more "+currencySymbol+new DecimalFormat("0.00").format(packOfPrice));
        tvPackTextFixed.setText(discountQty+" or more "+currencySymbol+new DecimalFormat("0.00").format(packOfPrice));
    }

    private void setRatingStar(Float count) {
        ImageView star1 = findViewById(R.id.prod_details_star1);
        ImageView star2 = findViewById(R.id.prod_details_star2);
        ImageView star3 = findViewById(R.id.prod_details_star3);
        ImageView star4 = findViewById(R.id.prod_details_star4);
        ImageView star5 = findViewById(R.id.prod_details_star5);
        if(count ==0.00){
            star1.setVisibility(View.GONE);
            star2.setVisibility(View.GONE);
            star3.setVisibility(View.GONE);
            star4.setVisibility(View.GONE);
            star5.setVisibility(View.GONE);
        }else if (count == 1.00) {
            star1.setVisibility(View.VISIBLE);
        } else if (count == 2.00) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
        } else if (count == 3.00) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
        } else if (count == 4.00) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star4.setVisibility(View.VISIBLE);
        } else if (count == 5.00) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star4.setVisibility(View.VISIBLE);
            star5.setVisibility(View.VISIBLE);
        } else if (count > 0.00 && count < 1.00) {
            star1.setVisibility(View.VISIBLE);
            star1.setImageResource(R.drawable.star);
        } else if (count > 1.00 && count < 2.00) {
            star1.setVisibility(View.VISIBLE);
            star1.setImageResource(R.drawable.star);
            star2.setVisibility(View.VISIBLE);
            star2.setImageResource(R.drawable.halfstar);
        } else if (count > 2.00 && count < 3.00) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star2.setImageResource(R.drawable.star);
            star3.setVisibility(View.VISIBLE);
            star3.setImageResource(R.drawable.halfstar);
        } else if (count > 3.00 && count < 4.00) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star3.setImageResource(R.drawable.star);
            star4.setVisibility(View.VISIBLE);
            star4.setImageResource(R.drawable.halfstar);
        } else if (count > 4.00 && count < 5.00) {
            star1.setVisibility(View.VISIBLE);
            star2.setVisibility(View.VISIBLE);
            star3.setVisibility(View.VISIBLE);
            star4.setVisibility(View.VISIBLE);
            star4.setImageResource(R.drawable.star);
            star5.setVisibility(View.VISIBLE);
            star5.setImageResource(R.drawable.halfstar);
        }
    }

//    View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivProductView:
                if(!isPreSetData) {
                    imagePopup(0);
                }
                break;
            case R.id.llpDemo1:
                if(!isPreSetData) {
                    imagePopup(1);
                }
                break;
            case R.id.llpDemo2:
                if(!isPreSetData) {
                    imagePopup(2);
                }
                break;
            case R.id.llpDemo3:
                if(!isPreSetData) {
                    imagePopup(3);
                }
                break;
            case R.id.ll_description_tab:
                if(!isPreSetData) {
                    displayProductDesc();
                }
                break;
            case R.id.tv_review_count:
                if(!isPreSetData) {
                    displayReviews();
                    scrollView.scrollTo(0, 1500);
                }
                break;
            case R.id.ll_reviews_tab:
                if(!isPreSetData) {
                    if(reviewList.size()==0) {
                        recyclerReview.setVisibility(View.GONE);
                        tvNoReview.setVisibility(View.VISIBLE);
                    }
                    displayReviews();
                }
                break;
            case R.id.ll_ask_tab:
                if(!isPreSetData) {
                    displayAskQuestion();
                }
                break;
            case R.id.dropLine_layout:
                if(!isPreSetData) {
                    Intent intent = new Intent(ProductDetailActivity.this, AskQuestionActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, productId);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, productName);
                    startActivity(intent);
                }
                break;
            case R.id.tv_btn_write_review:
//                openWriteReviewPopUp();
                break;
            case R.id.pincode_layout:
                if(!isPreSetData) {
                    openPincodeCheckingPopup();
                }
                break;
            case R.id.layout_btn_pincode:
                if(!isPreSetData) {
                    checkDeliveryAvailability(etPincode.getText().toString());
                }
                break;
            case R.id.layout_tab_wishlist:
                if(!isPreSetData) {
                    if(isUserLoggedIn) {
                        String action = "";
                        if (tvWishlistText.getText().toString().equalsIgnoreCase("Add to Wishlist")) {
                            action = "add";
                            addProductToWishlist(action);
                        } else if (tvWishlistText.getText().toString().equalsIgnoreCase("Added to Wishlist")) {
                            action = "delete";
                            addProductToWishlist(action);
                        }
                    }else{
                        Intent intLogin= new Intent(ProductDetailActivity.this, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, ProductDetailActivity.class.getSimpleName());
                        startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                }
                break;
            case R.id.layout_tab_shareProduct:
                if(!isPreSetData) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, productdetailsDTO.getResult().getProduct().getData().getProductName());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, seoUrl);
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }
                break;
            case R.id.tv_btn_addToCart:
            case R.id.tv_btn_addToCart_fixed:
                if(!isPreSetData) {
                    if(isUserLoggedIn) {
                        if(isOptionAvailable){
                            Intent intent = new Intent(ProductDetailActivity.this, OptionsActivity.class);
                            intent.putExtra(Constants.PreferenceConstants.TITLE, textviewTitle.getText());
                            intent.putExtra(Constants.PreferenceConstants.PRODUCTID, productId);
                            startActivity(intent);
//                            finish();
                        }else {
                            addToCart();
                        }
                    }else{
                        Intent intLogin= new Intent(ProductDetailActivity.this, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, ProductDetailActivity.class.getSimpleName());
                        startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                }
                break;
            case R.id.tv_btn_viewCart:
            case R.id.tv_btn_viewCart_fixed:
                if(!isPreSetData) {
                    if(isUserLoggedIn) {
                        Intent intentCart = new Intent(ProductDetailActivity.this, CartActivity.class);
                        intentCart.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intentCart.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        startActivity(intentCart);
                        finish();
                    }else{
                        Intent intLogin= new Intent(ProductDetailActivity.this, LoginActivity.class);
                        startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                }
                break;
            case R.id.ll_btn_all_review:
                if(!isPreSetData){
                    Intent intentAllReviews= new Intent(ProductDetailActivity.this,AllReviewsActivity.class );
                    intentAllReviews.putExtra(Constants.PreferenceConstants.ALLREVIEWS,strReviewList);
                    startActivity(intentAllReviews);
                }
                break;
            case R.id.ll_options:
                if(!isPreSetData){
                    if(isOptionAvailable) {
                        Intent intent = new Intent(ProductDetailActivity.this, OptionsActivity.class);
                        intent.putExtra(Constants.PreferenceConstants.TITLE, textviewTitle.getText());
                        intent.putExtra(Constants.PreferenceConstants.PRODUCTID, productId);
                        startActivity(intent);
//                        finish();
                    }
                }
                break;
            case R.id.ll_brand_tab:
                Intent intent= new Intent(ProductDetailActivity.this, ProductByBrand.class);
                intent.putExtra(Constants.PreferenceConstants.BRANDID,brandId);
                intent.putExtra(Constants.PreferenceConstants.BRANDNAME,brandName);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_more_offer:
                openOfferList();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }

    private void openOfferList() {
        Dialog offerDialog = new Dialog(ProductDetailActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                // Tap anywhere to close dialog.
                this.dismiss();
                return true;
            }
        };
        ImageView btnClose = offerDialog.findViewById(R.id.iv_close);
        offerDialog.setContentView(R.layout.offer_layout_popup);
        offerDialog.getWindow().setBackgroundDrawableResource(R.color.white_40);
        RecyclerView rvOffers = offerDialog.findViewById(R.id.rv_offers);
        rvOffers.hasFixedSize();
        LinearLayoutManager manager = new LinearLayoutManager(ProductDetailActivity.this, RecyclerView.VERTICAL, false);
        rvOffers.setLayoutManager(manager);
        if(staticData.size()>0) {
            OfferDiscountAdapter offerAdapter = new OfferDiscountAdapter(this, (ArrayList<ProductBasicDetailsResult.Result.StaticContentDatum>) staticData);
            rvOffers.setAdapter(offerAdapter);
        }else{
            rvOffers.setVisibility(View.GONE);
        }
        offerDialog.setCancelable(true);
        offerDialog.setCanceledOnTouchOutside(true);
        offerDialog.show();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerDialog.dismiss();
            }
        });

    }

    private void checkDeliveryAvailability(String pincode) {
        restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<ProductdetailsDTO> call = restInterface.checkPincode(globalUtils.getKey(), globalUtils.getapidate(), SharedPreference.getUserid(ProductDetailActivity.this),pincode);
        call.enqueue(new Callback<ProductdetailsDTO>() {
            @Override
            public void onResponse(Call<ProductdetailsDTO> call, retrofit2.Response<ProductdetailsDTO> response) {
                try {
                    if (response != null) {
                        Log.e("PincodeRes",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                        if (response.body().getSuccess()==1) {
                            tvNoLocFound.setVisibility(View.GONE);
                            pincodeDialog.dismiss();
                            String msg = response.body().getResult().getAvailabilityMsg();
                            tvDeliveryPincodeInfo.setVisibility(View.GONE);
                            tvCheckPincode .setText(msg);
                        } else {
                            tvNoLocFound.setVisibility(View.VISIBLE);
                            tvNoLocFound.setText("Please enter a valid pincode!");
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ProductdetailsDTO> call, Throwable t) {
                pincodeDialog.dismiss();
                Log.e("ExcPincode",":"+t);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        Branch branch = Branch.getInstance();

        // Branch init
//        branch.initSession(new Branch.BranchReferralInitListener() {
//            @Override
//            public void onInitFinished(JSONObject referringParams, BranchError error) {
//                if (error == null) {
//                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
//                    // params will be empty if no data found
//                    // ... insert custom logic here ...
//                    Log.e("BRANCH SDK","splash: "+ referringParams.toString());
//                } else {
//                    Log.e("BRANCH SDK", "splash_error: "+error.getMessage());
//                }
//            }
//        }, this.getIntent().getData(), this);
//        Log.e("DeepLink",":"+branch.getDeeplinkDebugParams());
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
        Log.e("newintent",":"+intent);
    }

    private void getOptionList() {
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<OptionListBean> call= restInterface.getOptionList(globalUtils.getKey(), globalUtils.getapidate(),UserId, productId);
        call.enqueue(new Callback<OptionListBean>() {
            @Override
            public void onResponse(Call<OptionListBean> call, retrofit2.Response<OptionListBean> response) {
                Log.e("OptionRes",":"+new GsonBuilder().create().toJson(response.body()));
                if(response.body().getSuccess()==1){
                    productOptionList= new ArrayList<>();
                    productOptionList= response.body().getResult().getProductData().getOption();
                }else{
                    Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OptionListBean> call, Throwable t) {
                Log.e("OptionListExc",":"+t);
            }
        });
    }

    private void openPincodeCheckingPopup() {
        pincodeDialog= new BottomSheetDialog(ProductDetailActivity.this, R.style.DialogStyle);
        Window window = pincodeDialog.getWindow();
        pincodeDialog.setContentView(R.layout.pincode_check_layout);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        window.setBackgroundDrawableResource(R.color.transparent);
        etPincode= pincodeDialog.findViewById(R.id.et_pincode);
        etPincode.addTextChangedListener(filterTextWatcher);
        layout_btnCheckPincode = pincodeDialog.findViewById(R.id.layout_btn_pincode);
        layout_btnCheckPincode.setOnClickListener(this);
        tvNoLocFound = pincodeDialog.findViewById(R.id.tv_not_found_loc);
        pincodeDialog.show();
        pincodeDialog.setCancelable(true);
    }

    private void addToCart() {
        if (fixedAddTocart.getText().toString().equalsIgnoreCase("Notify Me")
                || tabAddTocart.getText().toString().equalsIgnoreCase("Notify Me")){
            displayNotifyMeDialog();
        }else {
            manageCart();
        }
    }

    private void manageCart(){
        if (GlobalUtils.isNetworkAvailable(this)) {
            GlobalUtils.showdialog(this);
            HashMap<String, String> params = new HashMap<>();
            UserId=SharedPreference.getUserid(ProductDetailActivity.this);
            params.put("userid", UserId);
            params.put("productid", productId);
            params.put("quantity", String.valueOf(selectedQty));
            params.put("actiontype", "add");
            params.put("currencyCode", strSelectedCurrency);
            params.put("currencyValue", String.valueOf(currencyValue));
            params.put("optionid",optionId);
            params.put("optionvalueid",optionValueId);
            Log.e("CartReqData",": "+params);
            PrefClass prefClass=new PrefClass(ProductDetailActivity.this);
            Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
            List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
            for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
                if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode()));{
                    params.put("currencyCode", prefClass.getSelectedCurrency());
                    params.put("currencyValue", currencyData.getValue());
                }
            }

            restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
            Call<AddCartResponseDTO> call = restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(), params);
            call.enqueue(new Callback<AddCartResponseDTO>() {
                @Override
                public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
                    GlobalUtils.hidedialog();
                    Log.e("AddWishToCart", ":" +new GsonBuilder().create().toJson(response.body()));
                    if (response.body().getSuccess()==1) {
                        Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(ProductDetailActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<AddCartResponseDTO> call, Throwable t) {
                    GlobalUtils.hidedialog();
                    Toast.makeText(ProductDetailActivity.this, "Please try again!!", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }

    private void addProductToWishlist(final String action) {
        if (GlobalUtils.isNetworkAvailable(this)) {
            GlobalUtils.showdialog(this);
            UserId=SharedPreference.getUserid(ProductDetailActivity.this);
            restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
            Call<WishlistDTO> call = restInterface.addRemoveWishList(globalUtils.getKey(), globalUtils.getapidate(), UserId, productId, action);
            call.enqueue(new Callback<WishlistDTO>() {
                @Override
                public void onResponse(Call<WishlistDTO> call, retrofit2.Response<WishlistDTO> response) {
                    if (response.isSuccessful()) {
                        GlobalUtils.hidedialog();
                        if(action=="add") {
                            notAddedHeart_img.setVisibility(View.GONE);
                            addedHeart_img.setVisibility(View.VISIBLE);
                            tvWishlistText.setText("Added to Wishlist");
                        }else{
                            addedHeart_img.setVisibility(View.GONE);
                            notAddedHeart_img.setVisibility(View.VISIBLE);
                            tvWishlistText.setText("Add to Wishlist");
                        }
                        Log.e("ResponseDeletedWishList", ":" + response.body().getMessage());
                        Toast.makeText(ProductDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        if(action=="add") {
                            notAddedHeart_img.setVisibility(View.VISIBLE);
                            addedHeart_img.setVisibility(View.GONE);
                            tvWishlistText.setText("Add to Wishlist");
                        }else{
                            addedHeart_img.setVisibility(View.VISIBLE);
                            notAddedHeart_img.setVisibility(View.GONE);
                            tvWishlistText.setText("Added to Wishlist");
                        }
                    }
                }

                @Override
                public void onFailure(Call<WishlistDTO> call, Throwable t) {
                    Log.e("ManageCartExc",": "+t);
                }
            });
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }


    private void displayProductDesc() {

        linearTabs.setBackgroundResource(R.drawable.detailtabbag);
        wvProductDesc.setVisibility(View.VISIBLE);
        reviewContainer.setVisibility(View.GONE);
        askContainer.setVisibility(View.GONE);
        tabDescription.setBackgroundResource(R.color.colorPrimary);
        tabReviews.setBackgroundResource(R.color.white);
        tabAskQuestion.setBackgroundResource(R.color.white);
        descTabTitle.setTextColor(Color.WHITE);
        reviewTabTitle.setTextColor(Color.LTGRAY);
        askTabTitle.setTextColor(Color.LTGRAY);
    }

    private void displayReviews() {
        linearTabs.setBackgroundResource(R.drawable.detailtabbag);
        reviewContainer.setVisibility(View.VISIBLE);
        wvProductDesc.setVisibility(View.GONE);
        askContainer.setVisibility(View.GONE);
        tabReviews.setBackgroundResource(R.color.colorPrimary);
        tabDescription.setBackgroundResource(R.color.white);
        tabAskQuestion.setBackgroundResource(R.color.white);
        reviewTabTitle.setTextColor(Color.WHITE);
        descTabTitle.setTextColor(Color.LTGRAY);
        askTabTitle.setTextColor(Color.LTGRAY);
        tvNoReview.setText("Loading...");
        timer = new CountDownTimer(timeout * 1000, 20000) {
            public void onTick(long millisUntilFinished) {
                if(reviewList.size()==0){
                    tabReviews .performClick();
                    timer.cancel();
                }
            }

            public void onFinish() {
                timer.cancel();
            }
        };

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerReview.setLayoutManager(manager);
        if(reviewList.size()>0) {
            if (reviewListSize > 4) {
                btnAllReviews.setVisibility(View.VISIBLE);
            } else {
                btnAllReviews.setVisibility(View.GONE);
            }
            tvNoReview.setVisibility(View.GONE);
            recyclerReview.setVisibility(View.VISIBLE);
            reviewAdapter = new ReviewAdapter(ProductDetailActivity.this, reviewList, limit);
            recyclerReview.setAdapter(reviewAdapter);
        }else{
            recyclerReview.setVisibility(View.GONE);
            tvNoReview.setVisibility(View.VISIBLE);
        }
    }

    private void displayAskQuestion() {
        linearTabs.setBackgroundResource(R.drawable.detailtabbag);
        askContainer.setVisibility(View.VISIBLE);
        wvProductDesc.setVisibility(View.GONE);
        reviewContainer.setVisibility(View.GONE);
        tabAskQuestion.setBackgroundResource(R.color.colorPrimary);
        tabDescription.setBackgroundResource(R.color.white);
        tabReviews.setBackgroundResource(R.color.white);
        askTabTitle.setTextColor(Color.WHITE);
        descTabTitle.setTextColor(Color.LTGRAY);
        reviewTabTitle.setTextColor(Color.LTGRAY);

        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        if(askQuestionList.size()>0) {
            recyclerAskQuestion.setVisibility(View.VISIBLE);
            recyclerAskQuestion.setLayoutManager(manager);
            askQuestionAdapter = new AskQuestionAdapter(ProductDetailActivity.this, askQuestionList);
            recyclerAskQuestion.setAdapter(askQuestionAdapter);
        }else{
            recyclerAskQuestion.setVisibility(View.GONE);
        }
    }

    public TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(tvNoLocFound.getVisibility()== View.VISIBLE){
                tvNoLocFound.setVisibility(View.GONE);
            }
            if (s.length() == 6 || s.length() ==7) {
                hideKeyboard2(pincodeDialog);
            }
        }
    };

    private void imagePopup(int i) {
        final Dialog dialog = new Dialog(ProductDetailActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.product_image_slider_layout);
        ImageButton btnDelete = (ImageButton) dialog.findViewById(R.id.img_btn_delete);
        viewPager = (ViewPager) dialog.findViewById(R.id.pager);
        dot1 = dialog.findViewById(R.id.img_dot1);
        dot2 = dialog.findViewById(R.id.img_dot2);
        dot3 = dialog.findViewById(R.id.img_dot3);
        dot4 = dialog.findViewById(R.id.img_dot4);
        if(sliderImageList.size()== 2){
            dot2.setVisibility(View.VISIBLE);
        }else if (sliderImageList.size() == 3) {
            dot2.setVisibility(View.VISIBLE);
            dot3.setVisibility(View.VISIBLE);
        } else if (sliderImageList.size() == 4) {
            dot2.setVisibility(View.VISIBLE);
            dot3.setVisibility(View.VISIBLE);
            dot4.setVisibility(View.VISIBLE);
        }
        setCurrentDot(i);
        adapterView = new ImageAdapter(ProductDetailActivity.this, sliderImageList, i);
        viewPager.setAdapter(adapterView);
        viewPager.setCurrentItem(i);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                setCurrentDot(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void setCurrentDot(int i) {
        if (i == 0) {
            dot1.setImageResource(R.drawable.active_dot);
            dot2.setImageResource(R.drawable.non_active);
            dot3.setImageResource(R.drawable.non_active);
            dot4.setImageResource(R.drawable.non_active);
        } else if (i == 1) {
            dot2.setImageResource(R.drawable.active_dot);
            dot1.setImageResource(R.drawable.non_active);
            dot3.setImageResource(R.drawable.non_active);
            dot4.setImageResource(R.drawable.non_active);
        } else if (i == 2) {
            dot3.setImageResource(R.drawable.active_dot);
            dot1.setImageResource(R.drawable.non_active);
            dot2.setImageResource(R.drawable.non_active);
            dot4.setImageResource(R.drawable.non_active);
        } else if (i == 3) {
            dot4.setImageResource(R.drawable.active_dot);
            dot1.setImageResource(R.drawable.non_active);
            dot2.setImageResource(R.drawable.non_active);
            dot3.setImageResource(R.drawable.non_active);
        }
    }

    @Override
    public void displayDetails(String user_id, String prod_id, String product_name, String product_image, String product_price) {
        scrollView.setVisibility(View.GONE);
        mainProgressFrame.setVisibility(View.VISIBLE);
        textviewTitle.setText("");
        if(GlobalUtils.isNetworkAvailable(ProductDetailActivity.this)) {
            refreshImages();
            ViewGroup vg = findViewById(R.id.prod_details_main_layout);
            vg.setVisibility(View.INVISIBLE);
//        pView.invalidate();
            demo1.setVisibility(View.GONE);
            demo2.setVisibility(View.GONE);
            demo3.setVisibility(View.GONE);
            vg.setVisibility(View.VISIBLE);
            scrollView.scrollTo(0, pView.getTop());
            setInitialScreen(product_image, product_name, product_price);
            getContentFromApi(user_id, prod_id, optionId);
        }else{
            Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void recentProdDetails(String productId, String productName, String productImage, String productPrice) {
        scrollView.setVisibility(View.GONE);
        mainProgressFrame.setVisibility(View.VISIBLE);
        textviewTitle.setText("");
        if(GlobalUtils.isNetworkAvailable(ProductDetailActivity.this)) {
            refreshImages();
            ViewGroup vg = findViewById(R.id.prod_details_main_layout);
            vg.invalidate();
//        pView.invalidate();
            demo1.setVisibility(View.GONE);
            demo2.setVisibility(View.GONE);
            demo3.setVisibility(View.GONE);
            scrollView.scrollTo(0, pView.getTop());
            setInitialScreen(productImage, productName, productPrice);
            getContentFromApi(UserId, productId, optionId);
        }else{
        Toast.makeText(this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
    }
    }

    private void refreshImages() {
        pView.setImageBitmap(null);
        product_img1.setImageBitmap(null);
        product_img2.setImageBitmap(null);
        product_img3.setImageBitmap(null);
    }

    @Override
    public void selectedOption(int selectedIdx, Boolean isMain, String operator) {
        String availableQty= productOptionList.get(selectedIdx).getOptionQuantity();
        if(availableQty.equalsIgnoreCase("0")){
            fixedAddTocart.setText("Notify Me");
            tabAddTocart.setText("Notify Me");
        }else{
            fixedAddTocart.setText("Add to cart");
            tabAddTocart.setText("Add to cart");
        }
        if(fixedQtySpin.getSelectedItemPosition()>0) {
            selectedQty = fixedQtySpin.getSelectedItemPosition()+1;
        }
        if(qtySpin.getSelectedItemPosition()>0){
            selectedQty = fixedQtySpin.getSelectedItemPosition()+1;
        }
        if(!isMain) {
            optionId= productOptionList.get(selectedIdx).getOptionId();
            optionValueId= productOptionList.get(selectedIdx).getOptionValueId();
            String optionPrice= productOptionList.get(selectedIdx).getOptionPrice();
            Double dNetPrice=Double.parseDouble(P_price)+Double.parseDouble(optionPrice);
            netPrice = dNetPrice * (taxPercent / 100) + dNetPrice*currencyValue*selectedQty;
            String dprice= new DecimalFormat("0.00").format(netPrice);
            Log.e("OptionPrice",":"+netPrice);
            prod_price.setText(currencySymbol+dprice);
        }else{
            if(operator.equalsIgnoreCase("+")) {
                netPrice = (Double.parseDouble(P_price) * (taxPercent / 100) + Double.parseDouble(P_price)) * currencyValue * selectedQty;
            }else if(operator.equalsIgnoreCase("-")) {
                netPrice = (Double.parseDouble(P_price) * (taxPercent / 100) - Double.parseDouble(P_price)) * currencyValue * selectedQty;
            }
            String dprice= new DecimalFormat("0.00").format(netPrice);
            Log.e("OptionPriceMain",":"+netPrice);
            prod_price.setText(currencySymbol+dprice);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(ProductDetailActivity.this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public static void hideKeyboard2(Dialog dialog) {
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void displayNotifyMeDialog(){
        notifyDialog=new Dialog(ProductDetailActivity.this);
        notifyDialog.setContentView(R.layout.notify_me_dialog_layout);
        notifyDialog.show();
        final EditText etName=notifyDialog.findViewById(R.id.et_name);
        final EditText etEmail=notifyDialog.findViewById(R.id.et_email);
        final EditText etRemark=notifyDialog.findViewById(R.id.et_remark);
        Button btnNotify=notifyDialog.findViewById(R.id.btn_notify);
        notifyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        String name = "";
        if (!SharedPreference.getFirstName(this).isEmpty()) {
            name = SharedPreference.getFirstName(this);
        }
        if (!SharedPreference.getLastName(this).isEmpty()) {
            name = name + " " + SharedPreference.getLastName(this);
        }
        etName.setText(name);
        if (!SharedPreference.getUserEmail(this).isEmpty()){
            etEmail.setText(SharedPreference.getUserEmail(this));
        }else{
            etEmail.setText(SharedPreference.getSocialEmail(this));
        }
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName=etName.getText().toString();
                String strEmail=etEmail.getText().toString();
                String strRemark=etRemark.getText().toString();
                if (!strName.isEmpty()&&!strEmail.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        if (GlobalUtils.isNetworkAvailable(ProductDetailActivity.this)) {
                            new NotifyMeServiceClass().execute(strName, strEmail, strRemark);
                        } else
                            Toast.makeText(ProductDetailActivity.this, "Please check your internet connection...", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(ProductDetailActivity.this, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    }
                }else if (strName.isEmpty()){
                    Toast.makeText(ProductDetailActivity.this, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else if (strEmail.isEmpty()){
                    Toast.makeText(ProductDetailActivity.this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public class NotifyMeServiceClass extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            GlobalUtils.showdialog(ProductDetailActivity.this);
        }

        @Override
        protected String doInBackground(String... strings) {
            String strResult="";
            try {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("productid", productId)
                        .addFormDataPart("username", strings[0])
                        .addFormDataPart("email", strings[1])
                        .addFormDataPart("query", strings[2])
                        .build();

                Request request = new Request.Builder()
                        .url("https://app.gaurashtra.com/api/notifyMe")
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
                strResult = response.body().string();
                System.out.println("NotifyMeResponse->" + strResult);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return strResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                GlobalUtils.hidedialog();
                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(ProductDetailActivity.this, jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                if (jsonObject.getInt("success") == 1){
                    notifyDialog.dismiss();
                }
            }catch (JSONException je){
                je.printStackTrace();
            }
            //{"success":1,"message":"We have received your request. We will contact shortly.","result":{"username":"Laukendra","email":"archna.kumari@algosoft.co"}}
        }
    }

}
