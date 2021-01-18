package com.gaurashtra.app.view.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.view.adapter.IntermediateBannerSliderAdapter;
import com.gaurashtra.app.view.adapter.SliderAdapterExample;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.youtube.player.YouTubePlayer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.widget.NestedScrollView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Message;
import android.text.SpannableString;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.GetCartData.GetCartProduct;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.HomePagebean.AfterBestOFDatum;
import com.gaurashtra.app.model.bean.HomePagebean.CategoryDatum;
import com.gaurashtra.app.model.bean.HomePagebean.Datum;
import com.gaurashtra.app.model.bean.HomePagebean.HomepageDTO;
import com.gaurashtra.app.model.bean.HomePagebean.ShopByBrandDatum;
import com.gaurashtra.app.model.bean.HomePagebean.SliderDatum;
import com.gaurashtra.app.model.bean.HomePagebean.TodayProduct;
import com.gaurashtra.app.model.bean.HomePagebean.TopSellingDatum;
import com.gaurashtra.app.model.bean.HomePagebean.VideoDatum;
import com.gaurashtra.app.presentator.HomepagePresentar;
import com.gaurashtra.app.presentator.presentarInteractor.HomepagePresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.HomepageInteractor;
import com.gaurashtra.app.view.adapter.FirstHorizontalHomeAdapter;
import com.gaurashtra.app.view.adapter.FirstPagerAdapter;
import com.gaurashtra.app.view.adapter.SecondHorizontalHomeAdapter;
import com.gaurashtra.app.view.adapter.ShopAdapter;
import com.gaurashtra.app.view.fragment.CategoryFragment;
import com.gaurashtra.app.view.fragment.HomeFragment;
import com.gaurashtra.app.view.fragment.MeFragment;
import com.gaurashtra.app.view.fragment.SearchFragment;
import com.gaurashtra.app.view.fragment.leftmenufrag.AboutFrag;
import com.gaurashtra.app.view.fragment.leftmenufrag.ContactFrag;
import com.gaurashtra.app.view.fragment.leftmenufrag.FaqFrag;
import com.gaurashtra.app.view.fragment.leftmenufrag.OffersFrag;
import com.gaurashtra.app.view.fragment.leftmenufrag.PrivacyPolicyFrag;
import com.gaurashtra.app.view.fragment.leftmenufrag.RefundCancellationPolicyFrag;
import com.gaurashtra.app.view.fragment.leftmenufrag.Tcfrag;

//import io.branch.referral.Branch;
//import io.branch.referral.BranchError;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements HomepageInteractor, View.OnClickListener, FirstPagerAdapter.SliderClickInterface,
        IntermediateBannerSliderAdapter.BannerOnClickInterface {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private ViewPager firstPager, beforeVideoViewPager, afterBestViewPager, afterShopByViewPager, footerBannerViewPager;
    private LinearLayout sliderDots, topMsgLayout;
    private RelativeLayout mainLayout;
    private FirstPagerAdapter firstPagerAdapter;
    private RecyclerView rvTopSellingItems, rvCategory, rvBestOfHairCare, rvRecentlyViewed;
    private RecyclerView rvShopByBrand, rvRecommendedProduct;
    private FirstHorizontalHomeAdapter horizontalHomeAdapter;
    private SecondHorizontalHomeAdapter secondAdapter;
    private boolean doubleBackToExitPressedOnce = false;
    private ShopAdapter shopAdapter;
    private TextView more1, more2, Reco_more, Recent_more, Best_more;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggle;
    private TextView textviewTitle, textHome, tvHeaderUserName, textCartItemCount, tvTodayTimer, tvBestofLabel;
    private ImageView footerBanner;
    private VideoView videoView;
    private ImageView image1, image2, image3, image4, imageSummer1, imageSummer2, belowtoday;
    private LinearLayout todayMore, bestOfHairCareLayout;
    private ImageView imagebackground;
    IntermediateBannerSliderAdapter afterCatBannerAdapter;
    MediaController mediaControls;
    private YouTubePlayer YPlayer;
    private String yid;
    private TextView vidTitle, vidSubs, vidDesc, btnLogin, btnSignup;
    private BottomNavigationView navigation;
    private HomepagePresentarInteractor homepagePresentarInteractor;
    private GlobalUtils globalUtils;
    private final int Topselling = 1;
    private final int Todaydeal = 2;
    private final int HairCare = 3;
    private final int recentlyviewed = 4;
    private final int recommended = 5;
    private ImageView[] dots;
    InputMethodManager inputManager;
    String userid = " ";
    Long oldLong, NewLong, diff;
    List<Datum> imagedata = new ArrayList<>();
    List<SliderDatum> homeslider = new ArrayList<>();
    String linkType = " ", linkId = " ";
    SwipeRefreshLayout refreshLayout;
    FrameLayout mainContainer, bannerLayout;
    YouTubePlayerSupportFragment youTubePlayerFragment;
    LinearLayout scrollShopByBrands, firstTitleLayout;
    int mCartItemCount = 0;
    ShimmerFrameLayout shimmer1, shimmer2, shimmer3, shimmer4, shimmer5, shimmer6, shimmer7, shimmer8, shimmer9, shimmer10;
    FrameLayout shimmerFrameLayout;
    NestedScrollView nestedScrollView;
    Boolean isScroll = false, isUserLoggedIn = false;
    Profile profile;

    private LinearLayout recentlyViewedLayout, summerImagesLayout, DealBg, secondSectionLayout, thirdSectionLayout;
    public String strSelectedCurrency = "INR", currencyValue = "1", currencySymbol = "\u20B9",
            deviceId = "wedWfcyt2f86t38gcy2", strBestOfTitle;
    PrefClass prefClass;
    ProgressDialog progressDialog = null;
    private static final float END_SCALE = 0.7f;
    private RelativeLayout contentFrame, mainScreen;
    private Toolbar toolbar;
    GoogleSignInClient mGoogleSignInClient;
    Boolean isGoogleSignIn = false;
    NavigationView navigationView;
    final int BEFORE_VIDEO = 1, AFTER_BESTOF = 2, AFTER_SHOP = 3, FOOTER = 4;
    List<AfterBestOFDatum> banner_beforeVideo = new ArrayList<>();
    List<AfterBestOFDatum> banner_afterbestdata = new ArrayList<>();
    List<AfterBestOFDatum> banner_aftershopdata = new ArrayList<>();
    List<AfterBestOFDatum> banner_footerData = new ArrayList<>();
    Boolean pullRefresh = false;
    final int DELAY_MS = 6000;//delay in milliseconds before task is to be executed
    final int PERIOD_MS = 6000; // time in milliseconds between successive task executions.

    FrameLayout secondLoader;
    SliderView sliderView;
    private SliderAdapterExample adapter;
    List<SliderDatum> slider = new ArrayList<>();
    HomepageDTO homepageDTO;
    AppUpdateManager appUpdateManager;
    int REQ_CODE_VERSION_UPDATE = 111;
    InstallStateUpdatedListener installStateUpdatedListener;
    private boolean googlePlay= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Fade());
        }
        setContentView(R.layout.navdrawerpage);
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                deviceId = instanceIdResult.getToken();
                Log.e(TAG, "Refreshed token: " + deviceId);
                SharedPreference.setDeviceId(HomeActivity.this, deviceId);
            }
        });


        globalUtils = new GlobalUtils();
        profile = Profile.getCurrentProfile().getCurrentProfile();

        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        abar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        contentFrame = findViewById(R.id.contentLayout);
        prefClass = new PrefClass(HomeActivity.this);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
        getVersionName();
        View header = navigationView.getHeaderView(0);
        tvHeaderUserName = (TextView) header.findViewById(R.id.tvuname);
        btnLogin = header.findViewById(R.id.tv_nav_login);
        btnSignup = header.findViewById(R.id.tv_nav_signup);

        setupContentView();
        setOnClickListner();
//        requestData();
        if (SharedPreference.getUserid(this).isEmpty()) {
            tvHeaderUserName.setText("Gaurashtra");
            btnLogin.setVisibility(View.VISIBLE);
            btnSignup.setVisibility(View.VISIBLE);
            hideNavItems(navigationView);
        } else {
            if (!SharedPreference.getFirstName(this).isEmpty() || SharedPreference.getLastName(this).isEmpty()) {
                tvHeaderUserName.setText(SharedPreference.getFirstName(this) + " " + SharedPreference.getLastName(this));
            }
            btnLogin.setVisibility(View.GONE);
            btnSignup.setVisibility(View.GONE);
            showNavItems(navigationView);
        }
        navigationView.bringToFront();
        if (navigationView != null) {
            Menu menu = navigationView.getMenu();
            for (int i = 0; i < menu.size(); i++) {
                MenuItem menuItem = menu.getItem(i);
                applyFontToMenuItem(menuItem);
            }
            setupDrawerContent(navigationView);
        } else {
        }

        mtoggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                super.onDrawerSlide(drawerView, slideOffset);
                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentFrame.setScaleX(offsetScale);
                contentFrame.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentFrame.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentFrame.setTranslationX(xTranslation);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    contentFrame.setTranslationZ(xTranslation);
                    contentFrame.setTranslationX(xTranslation);
                }
            }
        };
        mDrawerLayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        String backFrom = "";
        if (getIntent().hasExtra(Constants.PreferenceConstants.BACKFROM)) {
            backFrom = getIntent().getStringExtra(Constants.PreferenceConstants.BACKFROM);
            Log.e("IntentData", ":" + backFrom);
            if (backFrom.equalsIgnoreCase("ME")) {
                setMeFragment();
            } else if (backFrom.equalsIgnoreCase("CAT")) {
                setCatFragment();
            } else if (backFrom.equalsIgnoreCase("CONTACT")) {
                openDrawers();
            } else if(backFrom.equalsIgnoreCase("ORDER_PLACED")){
                rateApp();
            }
        } else {
            textviewTitle.setText("Gaurashtra");
            navigation.setSelectedItemId(R.id.action_one);
        }
        progressDialog = new ProgressDialog(HomeActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Currency updating...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        if (SharedPreference.getLocale(HomeActivity.this).isEmpty()) {
            if (GlobalUtils.isNetworkAvailable(this)) {
                new GetIpAddress().execute();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }

        } else {
            if (SharedPreference.getLocale(HomeActivity.this).equalsIgnoreCase("INDIA")) {
                strSelectedCurrency = "INR";
            } else {
                strSelectedCurrency = "USD";
            }
        }
//        prefClass.setSelectedCurrency(strSelectedCurrency);
        getCurrencyList(strSelectedCurrency, progressDialog);
        checkForAppUpdate();

    }

    public void getVersionName() {
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            TextView tvVersion = findViewById(R.id.tv_version);
            tvVersion.setText("Version:" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void openDrawers() {
        navigationView.performClick();
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    private void showNavItems(NavigationView navigationView) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_logout).setVisible(true);
    }

    private void hideNavItems(NavigationView navigationView) {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
    }

    private void setupBadge() {
        if (GlobalUtils.isNetworkAvailable(this)) {
            Map<String, String> param = new HashMap<>();
            param.put("userid", userid);
            param.put("currencyCode", strSelectedCurrency);
            param.put("currencyValue", currencyValue);
            RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
            Call<GetCartProduct> call = restInterface.getCartData(globalUtils.getKey(), globalUtils.getapidate(), param);
            call.enqueue(new Callback<GetCartProduct>() {
                @Override
                public void onResponse(Call<GetCartProduct> call, Response<GetCartProduct> response) {
                    if (response.body().getSuccess() == 1) {
                        try {
                            if (response.body().getResult().getCartData().size() > 0) {
                                mCartItemCount = response.body().getResult().getCartData().size();
                                SharedPreference.setCartCount(HomeActivity.this, String.valueOf(mCartItemCount));
                                textCartItemCount.setText(String.valueOf(mCartItemCount));
                                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                                    textCartItemCount.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (textCartItemCount.getVisibility() != View.GONE) {
                                    textCartItemCount.setVisibility(View.GONE);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetCartProduct> call, Throwable t) {
                    Log.e("ErrorRes", "" + t);
                }
            });
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }

    private void requestData() {
        try {
            if (!SharedPreference.getUserid(HomeActivity.this).isEmpty()) {
                if (!prefClass.getCurrencyDataList().isEmpty()) {
                    Type type = new TypeToken<List<GetCurrencyList.Result.CurrencyData>>() {
                    }.getType();
                    List<GetCurrencyList.Result.CurrencyData> currencyDataList = (List<GetCurrencyList.Result.CurrencyData>) (new Gson()).fromJson(prefClass.getCurrencyDataList(), type);
                    for (GetCurrencyList.Result.CurrencyData currencyData : currencyDataList) {
                        if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())) {
                            strSelectedCurrency = prefClass.getSelectedCurrency();
                            if (strSelectedCurrency.equalsIgnoreCase(currencyData.getCode())) {
                                currencyValue = currencyData.getValue();
                                currencySymbol = currencyData.getSymbol().trim();
                                Log.e("currency details", "currCode: " + strSelectedCurrency + "\n currValue:" + currencyValue + "\n currSymbol: " + currencySymbol);
                            }
                        }
                    }
                }
            }


            if (GlobalUtils.isNetworkAvailable(this)) {
//                    homepagePresentarInteractor.sendHomeRequest(userid, deviceId);
                callHomePageData("1");

            } else {
                Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callHomePageData(final String type) {
        HashMap<String, String> param = new HashMap<>();
        param.put("userid", userid);
        param.put("deviceid", deviceId);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<HomepageDTO> call = null;
        if (type.equalsIgnoreCase("1")) {
            call = restInterface.callFirstHomePageData(globalUtils.getKey(), globalUtils.getapidate(), param);
        } else if (type.equalsIgnoreCase("2")) {
            Log.e("secondApi", ":" + type);
            call = restInterface.callSecondHomePageData(globalUtils.getKey(), globalUtils.getapidate(), param);
        } else if (type.equalsIgnoreCase("3")) {
            call = restInterface.callThirdHomePageData(globalUtils.getKey(), globalUtils.getapidate(), param);
        }
        call.enqueue(new Callback<HomepageDTO>() {
            @Override
            public void onResponse(Call<HomepageDTO> call, Response<HomepageDTO> response) {
                try {
                    Log.e("HomeResponse", ":" + type + ": " + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if (response != null) {
                        if (response.body().getSuccess() == 1) {
                            if (type.equalsIgnoreCase("1")) {
                                callHomePageData("2");
                            } else if (type.equalsIgnoreCase("2")) {
                                callHomePageData("3");
                            }
                            getResponseData(response.body(), type);
                        } else {
                            secondLoader.setVisibility(View.GONE);
                            GlobalUtils.showToast(HomeActivity.this, response.body().getMessage());
                        }
                    } else {
                        secondLoader.setVisibility(View.GONE);
                        GlobalUtils.showToast(HomeActivity.this, getResources().getString(R.string.label_something_went_wrong));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    secondLoader.setVisibility(View.GONE);
//                    nestedScrollView.setOnTouchListener(null);
                    refreshLayout.setRefreshing(false);
                    pullRefresh = false;
                }
            }

            @Override
            public void onFailure(Call<HomepageDTO> call, Throwable t) {
                Log.e("ExcHomeresp", ":" + t);
//                nestedScrollView.setOnTouchListener(null);
                refreshLayout.setRefreshing(false);
                secondLoader.setVisibility(View.GONE);
                pullRefresh = false;
                GlobalUtils.showToast(HomeActivity.this, getResources().getString(R.string.label_something_went_wrong));
            }
        });
    }

    public void setupContentView() {
        secondLoader = findViewById(R.id.list_loader_frame);
        secondSectionLayout = findViewById(R.id.second_container);
        thirdSectionLayout = findViewById(R.id.third_container);
        beforeVideoViewPager = findViewById(R.id.vp_below_todays_deals);
        afterBestViewPager = findViewById(R.id.vp_after_bestOf);
        afterShopByViewPager = findViewById(R.id.vp_after_shopByBrand);
        footerBannerViewPager = findViewById(R.id.vp_footer);
        firstTitleLayout = findViewById(R.id.first_title_layout);
        tvTodayTimer = findViewById(R.id.tv_today_timer);
        mainLayout = findViewById(R.id.contentLayout);
        imagebackground = findViewById(R.id.FramImage);
        imageSummer1 = findViewById(R.id.iv_image_summer_1);
        imageSummer2 = findViewById(R.id.iv_image_summer_2);
        image1 = findViewById(R.id.Image1);
        image2 = findViewById(R.id.Image2);
        image3 = findViewById(R.id.Image3);
        image4 = findViewById(R.id.Image4);
        bestOfHairCareLayout = findViewById(R.id.bestOfHair_layout);
        footerBanner = findViewById(R.id.iv_image_footer);
        topMsgLayout = findViewById(R.id.offer_txt_layout);
        textHome = findViewById(R.id.TextHome);
//        firstPager = findViewById(R.id.viewPager);
//        sliderDots = findViewById(R.id.SliderDots);
        rvTopSellingItems = findViewById(R.id.rv_top_selling_items);
        rvCategory = findViewById(R.id.rv_category);
        rvBestOfHairCare = findViewById(R.id.rv_best_of_hair_care);
        rvRecentlyViewed = findViewById(R.id.rv_recently_viewed);
        rvShopByBrand = findViewById(R.id.rv_shop_by_brand);
        rvRecommendedProduct = findViewById(R.id.rv_recommended_product);
        more1 = findViewById(R.id.tvMore1);
        Reco_more = findViewById(R.id.Recomm_viewMore);
        Recent_more = findViewById(R.id.Recently_viewmore);
        Best_more = findViewById(R.id.Best_ViewMore);
        more2 = findViewById(R.id.tvBrandView);
        vidTitle = findViewById(R.id.tvVidTitle);
        vidSubs = findViewById(R.id.tvVidSubscribe);
        vidDesc = findViewById(R.id.tvVidDesc);
        todayMore = findViewById(R.id.lltodayMore);
        mainScreen = findViewById(R.id.inner_content_layout);
        refreshLayout = findViewById(R.id.refresh_layout);
        nestedScrollView = findViewById(R.id.nestedScrollview);
//        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return true;
//            }
//        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!pullRefresh) {
                    pullRefresh = true;
                    requestData();
                }
            }
        });
//        bannerLayout = findViewById(R.id.banner_layout);
        scrollShopByBrands = findViewById(R.id.scroll_shopByBrands);
        DealBg = findViewById(R.id.llTodayDealBg);
        tvBestofLabel = findViewById(R.id.tv_bestof_label);
        shimmer1 = findViewById(R.id.sfl1);
        shimmer2 = findViewById(R.id.sfl2);
        shimmer3 = findViewById(R.id.sfl3);
        shimmer4 = findViewById(R.id.sfl4);
        shimmer5 = findViewById(R.id.sfl5);
        shimmer6 = findViewById(R.id.sfl6);
        shimmer7 = findViewById(R.id.sfl7);
        shimmer8 = findViewById(R.id.sfl8);
        shimmer9 = findViewById(R.id.sfl9);
        shimmer10 = findViewById(R.id.sfl10);

        recentlyViewedLayout = findViewById(R.id.recently_view_layout);
        summerImagesLayout = findViewById(R.id.summer_images_layout);


        shimmerFrameLayout = findViewById(R.id.parentShimmerLayout);
        mainScreen.setVisibility(View.GONE);
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmer1.startShimmer();
        shimmer2.startShimmer();
        shimmer3.startShimmer();
        shimmer4.startShimmer();
        shimmer5.startShimmer();
        shimmer6.startShimmer();
        shimmer7.startShimmer();
        shimmer8.startShimmer();
        shimmer9.startShimmer();
        shimmer10.startShimmer();
        sliderView = findViewById(R.id.imageSlider);


        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();


        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        View v = navigation.findViewById(R.id.action_four);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        View badge = LayoutInflater.from(this).inflate(R.layout.cart_icon_layout, itemView, true);
        textCartItemCount = badge.findViewById(R.id.cart_badge);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        rvTopSellingItems.setLayoutManager(manager);
        LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvCategory.setLayoutManager(manager1);
        LinearLayoutManager manager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvBestOfHairCare.setLayoutManager(manager2);
        LinearLayoutManager manager3 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        rvRecentlyViewed.setLayoutManager(manager3);

        LinearLayoutManager manager4 = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rvShopByBrand.setLayoutManager(manager4);

        LinearLayoutManager manager5 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvRecommendedProduct.setLayoutManager(manager5);

        ViewCompat.setNestedScrollingEnabled(rvTopSellingItems, false);
        ViewCompat.setNestedScrollingEnabled(rvCategory, false);
        ViewCompat.setNestedScrollingEnabled(rvBestOfHairCare, false);
        ViewCompat.setNestedScrollingEnabled(rvRecentlyViewed, false);
        ViewCompat.setNestedScrollingEnabled(rvShopByBrand, true);
        ViewCompat.setNestedScrollingEnabled(rvRecommendedProduct, false);
        nestedScrollView.setSmoothScrollingEnabled(true);
        if (!SharedPreference.getsetHomeLastRefreshedDate(HomeActivity.this).isEmpty() && !pullRefresh &&
                SharedPreference.getsetHomeLastRefreshedDate(HomeActivity.this).equalsIgnoreCase(globalUtils.getapidate())) {
            String strObj = SharedPreference.getHomeData(HomeActivity.this);
            Object object = new Gson().fromJson(strObj, HomepageDTO.class);
            getResponseData(object, "1");
        }
    }


    public void addNewItem(View view) {
        SliderDatum sliderItem = new SliderDatum();
//        sliderItem.setDescription("Slider Item Added Manually");
//        sliderItem.setImageUrl("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }

    public void setMeFragment() {
        textviewTitle.setText("My Account");
        Fragment fragment = new MeFragment();
        loadFragment(fragment, CategoryFragment.class.getSimpleName());
        navigation.setSelectedItemId(R.id.action_three);
    }

    public void setCatFragment() {
        textviewTitle.setText("Categories");
        Fragment fragment = new CategoryFragment();
        loadFragment(fragment, CategoryFragment.class.getSimpleName());
    }

    public void setOnClickListner() {
        more1.setOnClickListener(this);
        Reco_more.setOnClickListener(this);
        Recent_more.setOnClickListener(this);
        Best_more.setOnClickListener(this);
        more2.setOnClickListener(this);
        todayMore.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        imageSummer1.setOnClickListener(this);
        imageSummer2.setOnClickListener(this);
        footerBanner.setOnClickListener(this);
    }

    public void hideBottomNav() {
        navigation.setVisibility(View.GONE);
    }

    public void showBottomNav() {
        navigation.setVisibility(View.VISIBLE);
    }

    public void hideMenuItem() {
    }

    @Override
    protected void onResume() {
        closeKeyboard();
        requestData();
        try {
            if (!SharedPreference.getUserid(HomeActivity.this).isEmpty()) {
                if (!prefClass.getCurrencyDataList().isEmpty()) {
                    Type type = new TypeToken<List<GetCurrencyList.Result.CurrencyData>>() {
                    }.getType();
                    List<GetCurrencyList.Result.CurrencyData> currencyDataList = (List<GetCurrencyList.Result.CurrencyData>) (new Gson()).fromJson(prefClass.getCurrencyDataList(), type);
                    for (GetCurrencyList.Result.CurrencyData currencyData : currencyDataList) {
                        if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())) {
                            strSelectedCurrency = prefClass.getSelectedCurrency();
                            if (strSelectedCurrency.equalsIgnoreCase(currencyData.getCode())) {
                                currencyValue = currencyData.getValue();
                                currencySymbol = currencyData.getSymbol().trim();
                                Log.e("currency details", "currCode: " + strSelectedCurrency + "\n currValue:" + currencyValue + "\n currSymbol: " + currencySymbol);
                            }
                        }
                    }
                }
            }
            if (!SharedPreference.getUserid(HomeActivity.this).isEmpty()) {
                UserUpdate userUpdate = new UserUpdate(HomeActivity.this);
                if (!userUpdate.isUserAvailable) {
                    Toast.makeText(HomeActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
                }

                isUserLoggedIn = true;
                userid = SharedPreference.getUserid(HomeActivity.this);
                Log.e("UserId", ":" + SharedPreference.getUserid(this));
                if (!SharedPreference.getFirstName(this).isEmpty() || SharedPreference.getLastName(this).isEmpty()) {
                    tvHeaderUserName.setText(SharedPreference.getFirstName(this) + " " + SharedPreference.getLastName(this));
                }
                btnLogin.setVisibility(View.GONE);
                btnSignup.setVisibility(View.GONE);
                showNavItems(navigationView);
            } else {
                tvHeaderUserName.setText("Gaurashtra");
                btnLogin.setVisibility(View.VISIBLE);
                btnSignup.setVisibility(View.VISIBLE);
                hideNavItems(navigationView);
            }
            showBottomNav();
            textCartItemCount.setVisibility(View.GONE);
            navigation.setSelectedItemId(R.id.action_one);
            setupBadge();
            super.onResume();
        } catch (Exception e) {
            e.printStackTrace();
        }

        checkNewAppVersionState();
    }

    private void closeKeyboard() {
        if (inputManager != null) {
            try {
                inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("OnActHome", ":" + requestCode + "\n data:" + data + "\n resultcode: " + resultCode);
        if (requestCode == Constants.PreferenceConstants.BACKFROMPRODDETAILS
                || resultCode == Constants.PreferenceConstants.BACKFROMPRODDETAILS) {
            requestData();
            isUserLoggedIn = true;
            if (SharedPreference.getUserid(this).isEmpty()) {
                tvHeaderUserName.setText("Gaurashtra");
                btnLogin.setVisibility(View.VISIBLE);
                btnSignup.setVisibility(View.VISIBLE);
                hideNavItems(navigationView);
            } else {
                if (!SharedPreference.getFirstName(this).isEmpty() || SharedPreference.getLastName(this).isEmpty()) {
                    tvHeaderUserName.setText(SharedPreference.getFirstName(this) + " " + SharedPreference.getLastName(this));
                }
                btnLogin.setVisibility(View.GONE);
                btnSignup.setVisibility(View.GONE);
                showNavItems(navigationView);
            }
        } else if (requestCode == REQ_CODE_VERSION_UPDATE) {
            if (resultCode != RESULT_OK) { //RESULT_OK / RE""SULT_CANCELED / RESULT_IN_APP_UPDATE_FAILED
                Log.e("Update_failed!", " Result code :" + resultCode);
                // If the update is cancelled or fails,
                // you can request to start the update again.
                unregisterInstallStateUpdListener();
            }
        }

        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                Log.e("GGG", "onIndicatorClicked: " + sliderView.getCurrentPagePosition());
            }
        });
    }

    @Override
    public void onClick(View view) {
        textviewTitle.setText(" ");
        switch (view.getId()) {
            case R.id.tvMore1:
                Intent intent = new Intent(this, ProductListActivity.class);
                intent.putExtra(Constants.PreferenceConstants.TITLE, Constants.PreferenceConstants.TOP_SELLING_TITLE);
                startActivity(intent);
                break;
            case R.id.Recomm_viewMore:
                Intent intent1 = new Intent(this, ProductListActivity.class);
                intent1.putExtra(Constants.PreferenceConstants.TITLE, Constants.PreferenceConstants.RECOMM_TITLE);
                startActivity(intent1);
                break;
            case R.id.Best_ViewMore:
                Intent intent2 = new Intent(this, ProductListActivity.class);
                intent2.putExtra(Constants.PreferenceConstants.TITLE, "Best of " + strBestOfTitle);
                startActivity(intent2);
                break;
            case R.id.Recently_viewmore:
                Intent intent3 = new Intent(this, ProductListActivity.class);
                intent3.putExtra(Constants.PreferenceConstants.TITLE, Constants.PreferenceConstants.RECENT_TITLE);
                startActivity(intent3);
                break;
            case R.id.tvBrandView:
                Intent intentB = new Intent(this, BrandActivity.class);
                startActivity(intentB);
                break;
            case R.id.Image1:
//                Toast.makeText(this, "Click1", Toast.LENGTH_SHORT).show();
                try {
                    if (imagedata.size() > 0) {
                        if (imagedata.get(0).getLinkType().equalsIgnoreCase("Product")) {
                            linkType = "Product";
                        } else if (imagedata.get(0).getLinkType().equalsIgnoreCase("Category")) {
                            linkType = "Category";
                        }
                        linkId = imagedata.get(0).getLinkId();
                        openBrandProduct(linkType, linkId, 0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.Image2:
                if (imagedata.size() > 1) {
                    if (imagedata.get(1).getLinkType().equalsIgnoreCase("Product")) {
                        linkType = "Product";
                    } else if (imagedata.get(1).getLinkType().equalsIgnoreCase("Category")) {
                        linkType = "Category";
                    }
                    linkId = imagedata.get(1).getLinkId();
                    openBrandProduct(linkType, linkId, 0);
                }
                break;
            case R.id.Image3:
                try {
                    if (imagedata.size() > 2) {
                        if (imagedata.get(2).getLinkType().equalsIgnoreCase("Product")) {
                            linkType = "Product";
                        } else if (imagedata.get(2).getLinkType().equalsIgnoreCase("Category")) {
                            linkType = "Category";
                        }
                        linkId = imagedata.get(2).getLinkId();
                        openBrandProduct(linkType, linkId, 0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.Image4:
                try {
                    if (imagedata.size() > 3) {
                        if (imagedata.get(3).getLinkType().equalsIgnoreCase("Product")) {
                            linkType = "Product";
                        } else if (imagedata.get(3).getLinkType().equalsIgnoreCase("Category")) {
                            linkType = "Category";
                        }
                        linkId = imagedata.get(3).getLinkId();
                        openBrandProduct(linkType, linkId, 0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_nav_login:
                Intent intent5 = new Intent(HomeActivity.this, LoginActivity.class);
                intent5.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
                startActivityForResult(intent5, Constants.PreferenceConstants.BACKFROMHOME);
                break;
            case R.id.tv_nav_signup:
                Intent intent6 = new Intent(HomeActivity.this, RegisterActivity.class);
                intent6.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
                startActivityForResult(intent6, Constants.PreferenceConstants.BACKFROMHOME);
                break;
            case R.id.lltodayMore:
                Intent intent7 = new Intent(this, ProductListActivity.class);
                intent7.putExtra(Constants.PreferenceConstants.TITLE, Constants.PreferenceConstants.TODAYDEAL_TITLE);
                startActivity(intent7);
                break;

//            case R.id.iv_below_todays_deals:
//                try {
//                    if (banner_beforeVideo.size() > 0) {
//                        if (banner_beforeVideo.get(0).getLinkType().equalsIgnoreCase("Product")) {
//                            linkType = "Product";
//                        } else if (banner_beforeVideo.get(0).getLinkType().equalsIgnoreCase("Category")) {
//                            linkType = "Category";
//                        }
//                        linkId = banner_beforeVideo.get(0).getLinkId();
//                        openBrandProduct(linkType, linkId, 0);
//                    }
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                break;
            case R.id.iv_image_summer_1:
                try {
                    if (banner_aftershopdata.size() > 0) {
                        if (banner_aftershopdata.get(0).getLinkType().equalsIgnoreCase("Product")) {
                            linkType = "Product";
                        } else if (banner_aftershopdata.get(0).getLinkType().equalsIgnoreCase("Category")) {
                            linkType = "Category";
                        }
                        linkId = banner_aftershopdata.get(0).getLinkId();
                        openBrandProduct(linkType, linkId, 0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_image_summer_2:
                try {
                    if (banner_aftershopdata.size() > 1) {
                        if (banner_aftershopdata.get(1).getLinkType().equalsIgnoreCase("Product")) {
                            linkType = "Product";
                        } else if (banner_aftershopdata.get(1).getLinkType().equalsIgnoreCase("Category")) {
                            linkType = "Category";
                        }
                        linkId = banner_aftershopdata.get(1).getLinkId();
                        openBrandProduct(linkType, linkId, 1);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_image_footer:
                try {
                    if (banner_footerData.size() > 0) {
                        if (banner_footerData.get(0).getLinkType().equalsIgnoreCase("Product")) {
                            linkType = "Product";
                        } else if (banner_footerData.get(0).getLinkType().equalsIgnoreCase("Category")) {
                            linkType = "Category";
                        }
                        linkId = banner_footerData.get(0).getLinkId();
                        openBrandProduct(linkType, linkId, 0);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void openBrandProduct(String linkType, String linkId, int imagePos) {
//        Toast.makeText(this, "Click2", Toast.LENGTH_SHORT).show();
        if (linkType.equalsIgnoreCase("Product")) {
            Intent intentPro = new Intent(this, ProductDetailActivity.class);
            intentPro.putExtra(Constants.PreferenceConstants.PRODUCTID, linkId);
            intentPro.putExtra(Constants.PreferenceConstants.PRODUCTNAME, "");
            startActivity(intentPro);
        } else if (linkType.equalsIgnoreCase("Category")) {
            Intent intent = new Intent(HomeActivity.this, BrandListActivity.class);
            intent.putExtra(Constants.PreferenceConstants.CATEGORYID, linkId);
            intent.putExtra(Constants.PreferenceConstants.CATEGORYNAME, " ");
            startActivity(intent);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.action_one:
//                    setItemChecked(item)
                    item.setChecked(true);
                    textviewTitle.setText("Gaurashtra");
                    fragment = new HomeFragment();
                    loadFragment(fragment, HomeFragment.class.getSimpleName());
                    return true;
                case R.id.action_two:
                    textviewTitle.setText("Categories");
                    fragment = new CategoryFragment();
                    loadFragment(fragment, CategoryFragment.class.getSimpleName());
                    return true;
                case R.id.action_three:
                    if (!SharedPreference.getUserid(HomeActivity.this).isEmpty()) {
                        textviewTitle.setText("My Account");
                        fragment = new MeFragment();
                        loadFragment(fragment, MeFragment.class.getSimpleName());
                    } else {
                        Intent intLogin = new Intent(HomeActivity.this, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
                        startActivityForResult(intLogin, Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                    return true;
                case R.id.action_four:
                    textviewTitle.setText("Cart");
                    if (!SharedPreference.getUserid(HomeActivity.this).isEmpty()) {
                        Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intLogin = new Intent(HomeActivity.this, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
                        startActivityForResult(intLogin, Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                    return true;
                case R.id.action_five:
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    textviewTitle.setText("Search");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PreferenceConstants.BACKFROM, "HOME");
                    fragment = new SearchFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment, SearchFragment.class.getSimpleName());
                    return true;
            }
            return false;
        }

    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mtoggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.cart:
                Intent intent = new Intent(this, CartActivity.class);
                startActivity(intent);
                break;
            case R.id.notification:
                Intent i = new Intent(this, NotificationActivity.class);
                startActivity(i);
                break;
            case R.id.currency_action:
                displayCurrencyChangeOptionDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void displayCurrencyChangeOptionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.currency_change_option_dialog_layout);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();
        RadioButton rbInr = dialog.findViewById(R.id.rb_inr);
        RadioButton rbUsd = dialog.findViewById(R.id.rb_usd);
        rbInr.setSelected(false);
        rbUsd.setSelected(false);
        RadioGroup radioGroup = dialog.findViewById(R.id.rgCurrencyChange);
        if (strSelectedCurrency.equalsIgnoreCase("INR")) {
            radioGroup.check(R.id.rb_inr);
        } else if (strSelectedCurrency.equalsIgnoreCase("USD")) {
            radioGroup.check(R.id.rb_usd);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_inr) {
                    strSelectedCurrency = "INR";
                } else if (i == R.id.rb_usd) {
                    strSelectedCurrency = "USD";
                }
                dialog.dismiss();
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                getCurrencyList(strSelectedCurrency, progressDialog);
            }
        });

    }

    public void displayProductImage(List<SliderDatum> slist) {
        //  itemname.setText(productDetailsBean.getProductName());
        try {
            if (sliderDots.getChildCount() > 0) {
                sliderDots.removeAllViews();
            }
            dots = new ImageView[slist.size()];

            for (int i = 0; i < slist.size(); i++) {
                dots[i] = new ImageView(this);
                dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_outline));

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(25, 25);
                params.setMargins(8, 0, 8, 0);
                sliderDots.addView(dots[i], params);
            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_circle_filled));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFragment(Fragment fragment, String tag) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//            Fragment existingFragment = fragmentManager.findFragmentByTag(tag);
//            if (existingFragment != null) {
//                Fragment currentFragment = fragmentManager.findFragmentById(R.id.frame_container);
//                fragmentTransaction.hide(currentFragment);
//                fragmentTransaction.show(existingFragment);
//            }
//            else {
//                fragmentTransaction.add(R.id.frame_container, fragment, tag);
//            }
//            fragmentTransaction.commit();
    }

    private void applyFontToMenuItem(MenuItem mi) {
        //  Typeface font = Typeface.createFromAsset(getAssets(), "ds_digi_b.TTF");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
//        System.out.println("lmenu--->" + mNewTitle);
//        mNewTitle.setSpan(new CustomTypefaceSpan("" , typeface), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        mi.setTitle(mNewTitle);
    }


    private void setupDrawerContent(final NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);

                        switch (menuItem.getItemId()) {
                            case R.id.nav_logout:
                                openLogoutDialog();
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.Home:
                                textviewTitle.setText("Gaurashtra");
                                HomeFragment fragment = new HomeFragment();
                                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
                                mDrawerLayout.closeDrawers();
                                showBottomNav();
                                findViewById(R.id.notification).setVisibility(View.VISIBLE);
                                findViewById(R.id.currency_action).setVisibility(View.VISIBLE);
                                break;
                            case R.id.nav_about:
                                textviewTitle.setText("About Us");
                                AboutFrag aboutFrag = new AboutFrag();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_container,
                                                aboutFrag, aboutFrag.getClass().getSimpleName())
                                        .addToBackStack(AboutFrag.TAG).commit();
                                mDrawerLayout.closeDrawers();
                                findViewById(R.id.notification).setVisibility(View.GONE);
                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                break;
                            case R.id.nav_faq:
                                textviewTitle.setText("Faq");
                                FaqFrag faqFrag = new FaqFrag();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_container,
                                                faqFrag, faqFrag.getClass().getSimpleName())
                                        .addToBackStack(FaqFrag.TAG).commit();
                                findViewById(R.id.notification).setVisibility(View.GONE);
                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_notification:
                                Intent intent = new Intent(HomeActivity.this, NotificationActivity.class);
                                startActivity(intent);
                                findViewById(R.id.notification).setVisibility(View.GONE);
                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_contact:
//                                textviewTitle.setText("Contact Us");
//                                ContactFrag contactFrag = new ContactFrag();
//                                getSupportFragmentManager().beginTransaction()
//                                        .replace(R.id.frame_container,
//                                                contactFrag, contactFrag.getClass().getSimpleName())
//                                        .addToBackStack(ContactFrag.TAG).commit();
//                                findViewById(R.id.notification).setVisibility(View.GONE);
//                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                Intent intent1 = new Intent(HomeActivity.this, ContactUsActivity.class);
                                mDrawerLayout.closeDrawers();
                                startActivity(intent1);
                                break;
                            case R.id.nav_share:
                                shareApp();
                                break;
                            case R.id.nav_tc:
                                textviewTitle.setText("Terms & Conditions");
                                Tcfrag tcfrag = new Tcfrag();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_container,
                                                tcfrag, tcfrag.getClass().getSimpleName())
                                        .addToBackStack(Tcfrag.TAG).commit();
                                findViewById(R.id.notification).setVisibility(View.GONE);
                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_refund_cancellation:
                                textviewTitle.setText(getResources().getString(R.string.refund_cancellation));
                                RefundCancellationPolicyFrag refundCancellationPolicyFrag = new RefundCancellationPolicyFrag();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_container,
                                                refundCancellationPolicyFrag, refundCancellationPolicyFrag.getClass().getSimpleName())
                                        .addToBackStack(RefundCancellationPolicyFrag.TAG).commit();
                                findViewById(R.id.notification).setVisibility(View.GONE);
                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_offers:
                                textviewTitle.setText("Offers");
                                OffersFrag offerFrag = new OffersFrag();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_container,
                                                offerFrag, offerFrag.getClass().getSimpleName())
                                        .addToBackStack(OffersFrag.TAG).commit();
                                findViewById(R.id.notification).setVisibility(View.GONE);
                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.nav_privacy_policy:
                                textviewTitle.setText("Privacy Policy");
                                PrivacyPolicyFrag privacyPolicyFrag = new PrivacyPolicyFrag();
                                getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.frame_container,
                                                privacyPolicyFrag, privacyPolicyFrag.getClass().getSimpleName())
                                        .addToBackStack(PrivacyPolicyFrag.TAG).commit();
                                findViewById(R.id.notification).setVisibility(View.GONE);
                                findViewById(R.id.currency_action).setVisibility(View.GONE);
                                mDrawerLayout.closeDrawers();
                                break;
                        }
                        return false;
                    }
                });
    }

    private void openLogoutDialog() {
        final Dialog logoutDialog = new Dialog(this);
        logoutDialog.setContentView(R.layout.dialog_logout_layout);
        logoutDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        logoutDialog.setCanceledOnTouchOutside(true);
        logoutDialog.setCancelable(true);
        logoutDialog.show();
        TextView tvYes = logoutDialog.findViewById(R.id.tv_logout_yes);
        TextView tvNo = logoutDialog.findViewById(R.id.tv_logout_no);
        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    logoutDialog.dismiss();
                    if (profile != null) {
                        LoginManager.getInstance().logOut();
                    }
                    if (signInSilently()) {
                        mGoogleSignInClient.signOut();
                    }
                    clearPreferences();
                    finish();
                    startActivity(getIntent());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutDialog.dismiss();
            }
        });
    }

    private void rateApp(){
        ImageView star1, star2, star3, star4, star5;
        BottomSheetDialog appRatingDialog = new BottomSheetDialog(HomeActivity.this, R.style.DialogStyle);
        Window window = appRatingDialog.getWindow();
        appRatingDialog.setContentView(R.layout.feedback_layout);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE );
        window.setBackgroundDrawableResource(R.color.transparent);
        ImageView btnBack = appRatingDialog.findViewById(R.id.iv_close);
        TextView btnAskLater= appRatingDialog.findViewById(R.id.tv_btn_asklater);
        TextView btnSubmit = appRatingDialog.findViewById(R.id.tv_btn_submit);
        star1 = appRatingDialog.findViewById(R.id.star1);
        star2 = appRatingDialog.findViewById(R.id.star2);
        star3 = appRatingDialog.findViewById(R.id.star3);
        star4 = appRatingDialog.findViewById(R.id.star4);
        star5 = appRatingDialog.findViewById(R.id.star5);

        star1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });
        star5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSubmit.performClick();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appRatingDialog.dismiss();
            }
        });

        btnAskLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appRatingDialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        appRatingDialog.setCancelable(false);
        appRatingDialog.show();
    }
    private void shareApp() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hey I just found an interesting mobile app for all the authentic Panchagavya Cow Products.To download this mobile app for android , please click on the link below:"
                +"\n"+"https://play.google.com/store/apps/details?id=com.gaurashtra.app";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Gaurashtra");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private void clearPreferences() {

        SharedPreference.setHomeLastRefreshedDate(HomeActivity.this, null);
        SharedPreference.setHomeData(HomeActivity.this, null);
        SharedPreference.setUserId(HomeActivity.this, null);
        SharedPreference.setUserCountryId(HomeActivity.this, null);
        SharedPreference.setUserCity(HomeActivity.this, null);
        SharedPreference.setUserCountry(HomeActivity.this, null);
        SharedPreference.setUserStateId(HomeActivity.this, null);
        SharedPreference.setUserState(HomeActivity.this, null);
        SharedPreference.setUserZipcode(HomeActivity.this, null);
        SharedPreference.setUserPhone(HomeActivity.this, null);
        SharedPreference.setLastName(HomeActivity.this, null);
        SharedPreference.setFirstName(HomeActivity.this, null);
        SharedPreference.setdefAddressPhone(HomeActivity.this, null);
        SharedPreference.setAddressId(HomeActivity.this, null);
        SharedPreference.setAmountFromServer(HomeActivity.this, null);
        if (!SharedPreference.getIsRememberMe(HomeActivity.this)) {
            SharedPreference.setUserEmail(HomeActivity.this, null);
        }
        SharedPreference.setSocialEmail(HomeActivity.this, null);
        SharedPreference.setUserAddress1(HomeActivity.this, null);
        SharedPreference.setUserAddress2(HomeActivity.this, null);
    }

    public boolean signInSilently() {

        mGoogleSignInClient = GoogleSignIn.getClient(HomeActivity.this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

        mGoogleSignInClient.silentSignIn().addOnCompleteListener(HomeActivity.this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            // The signed in account is stored in the task's result.
                            try {
                                GoogleSignInAccount mAccount = task.getResult(ApiException.class);
                                if (mAccount != null) {
                                    isGoogleSignIn = true;
                                }
                            } catch (ApiException e) {
                                Log.w(TAG, "SignInResult::Failed code="
                                        + e.getStatusCode() + ", Message: "
                                        + e.getStatusMessage());
                            }
                        } else {
                            // Player will need to sign-in explicitly using via UI
                            Log.d(TAG, "Silent::Login::Failed");
                        }
                    }
                });
        return isGoogleSignIn;
    }

    @Override
    public void onBackPressed() {
//        Log.e("BackStack", "" + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
//        if (getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()).getName().equalsIgnoreCase(HomeFragment.class.getSimpleName())) {
            if (doubleBackToExitPressedOnce) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                HomeActivity.this.finish();
                super.onBackPressed();
                return;
            }
            if (navigation.getSelectedItemId() == R.id.action_one) {
                this.doubleBackToExitPressedOnce = true;
                Snackbar.make(findViewById(android.R.id.content), "Please click back again to exit", Snackbar.LENGTH_LONG).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 2000);
            } else {
                navigation.setSelectedItemId(R.id.action_one);
            }
        } else {
            try {
//            getSupportFragmentManager().popBackStack();
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                textviewTitle.setText("Gaurashtra");
                findViewById(R.id.notification).setVisibility(View.VISIBLE);
                navigation.setSelectedItemId(R.id.action_one);
                showBottomNav();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
//        this method is fetching data from unsplited home api and currently not in use
        Log.e("HomeResponse", ":" + new GsonBuilder().setPrettyPrinting().create().toJson(object));
//        getResponseData(object, "3");

    }

    private void getResponseData(Object object, String type) {
        try {


            if (object != null) {
                pullRefresh = false;
                homepageDTO = (HomepageDTO) object;
            }
            if (type.equalsIgnoreCase("1")) {
                List<AfterBestOFDatum> afterbest = new ArrayList<>();
                List<CategoryDatum> category = new ArrayList<>();
                imagedata = new ArrayList<>();

                String imageframe = homepageDTO.getResult().getAfterSlider().getBackground().getImage();
                List<Datum> imagelist = homepageDTO.getResult().getAfterSlider().getData();

                for (int i = 0; i < imagelist.size(); i++) {
                    Datum frameimage = new Datum();
                    frameimage.setBannerImage(imagelist.get(i).getBannerImage());
                    frameimage.setLinkType(imagelist.get(i).getLinkType());
                    frameimage.setBannerId(imagelist.get(i).getBannerId());
                    frameimage.setLinkId(imagelist.get(i).getLinkId());
                    imagedata.add(frameimage);
                    if (i == 0) {
                        String image1url = imagelist.get(i).getBannerImage();
                        Glide.with(this).load(image1url)
                                .error(R.drawable.img_icon)
                                .placeholder(R.drawable.img_icon).fitCenter().into(image1);
                    } else if (i == 1) {
                        String image1url = imagelist.get(i).getBannerImage();
                        Glide.with(this).load(image1url).error(R.drawable.img_icon)
                                .placeholder(R.drawable.img_icon).fitCenter().into(image2);
                    } else if (i == 2) {
                        String image1url = imagelist.get(i).getBannerImage();
                        Glide.with(this).load(image1url).error(R.drawable.img_icon)
                                .placeholder(R.drawable.img_icon).fitCenter().into(image3);
                    } else if (i == 3) {
                        String image1url = imagelist.get(i).getBannerImage();
                        Glide.with(this).load(image1url).error(R.drawable.img_icon)
                                .placeholder(R.drawable.img_icon).fitCenter().into(image4);
                    }
                }
                Picasso.with(this).load(imageframe).into(imagebackground);


                homeslider = homepageDTO.getResult().getSliderData();
                slider = new ArrayList<>();
                for (int i = 0; i < homeslider.size(); i++) {
                    SliderDatum sliderDatum = new SliderDatum();
                    sliderDatum.setSliderImage(homeslider.get(i).getSliderImage());
                    sliderDatum.setSliderId(homeslider.get(i).getSliderId());
                    sliderDatum.setLinkType(homeslider.get(i).getLinkType());
                    sliderDatum.setLinkId(homeslider.get(i).getLinkId());
                    slider.add(sliderDatum);
                }
                adapter.renewItems(slider);

                List<TopSellingDatum> Topsellingdata = homepageDTO.getResult().getTopSellingData();
                if (Topsellingdata.size() <= 3) {
                    more1.setVisibility(View.GONE);
                }
                horizontalHomeAdapter = new FirstHorizontalHomeAdapter(this, Topsellingdata, Topselling);
                rvTopSellingItems.setAdapter(horizontalHomeAdapter);

                List<CategoryDatum> categorydata = homepageDTO.getResult().getCategoryData();
                for (int i = 0; i < categorydata.size(); i++) {
                    CategoryDatum categoryDatum = new CategoryDatum();
                    categoryDatum.setCategoryId(categorydata.get(i).getCategoryId());
                    categoryDatum.setCatImage(categorydata.get(i).getCatImage());
                    categoryDatum.setName(categorydata.get(i).getName());
                    categoryDatum.setParentId(categorydata.get(i).getParentId());
                    category.add(categoryDatum);
                }
                secondAdapter = new SecondHorizontalHomeAdapter(this, category);
                rvCategory.setAdapter(secondAdapter);


                String strObject = new Gson().toJson(homepageDTO).toString();
                SharedPreference.setHomeData(HomeActivity.this, strObject);
                SharedPreference.setHomeLastRefreshedDate(HomeActivity.this, globalUtils.getapidate());

                if (!homepageDTO.getResult().getOfferText().getContent().isEmpty()) {
                    topMsgLayout.setVisibility(View.VISIBLE);
                    String messageHome = homepageDTO.getResult().getOfferText().getContent();
                    textHome.setText(messageHome);
                } else {
                    topMsgLayout.setVisibility(View.GONE);
                }
            }
//@@@@@@@@@@@@@@@@@@@@@@@@@@ Starting of 2nd part of  api from Today's deal @@@@@@@@@@@@@@@@@@@@@@@@@@@@
            if (type.equalsIgnoreCase("2")) {
                secondLoader.setVisibility(View.GONE);
                secondSectionLayout.setVisibility(View.VISIBLE);
//                nestedScrollView.setOnTouchListener(null);
                refreshLayout.setRefreshing(false);
                pullRefresh = false;
                List<VideoDatum> video = new ArrayList<>();
                List<AfterBestOFDatum> ATDeal = new ArrayList<>();
                ImageView todayimg1 = findViewById(R.id.ivimg);
                TextView todayimgName1 = findViewById(R.id.tvimgName1);
                TextView todayprice1 = findViewById(R.id.tvprice1);

                ImageView todayimg2 = findViewById(R.id.ivimg2);
                TextView todayImgName2 = findViewById(R.id.tvImgName2);
                TextView todayPrice2 = findViewById(R.id.tvPrice2);

                ImageView todayimg3 = findViewById(R.id.ivimg3);
                TextView todayImgName3 = findViewById(R.id.tvimgName3);
                TextView todayPrice3 = findViewById(R.id.tvPrice3);

                ImageView todayimg4 = findViewById(R.id.ivimg4);
                TextView todayImgName4 = findViewById(R.id.tvimgName4);
                TextView todayPrice4 = findViewById(R.id.tvPrice4);
                if (homepageDTO.getResult().getTodayDeal().getAvailable().equalsIgnoreCase("1")) {
                    DealBg.setVisibility(View.VISIBLE);

                    String TodayDealTime = homepageDTO.getResult().getTodayDeal().getDealStartTime();
                    String TodayDealEndTime = homepageDTO.getResult().getTodayDeal().getDealEndTime();
                    String TodayDealDate = homepageDTO.getResult().getTodayDeal().getDealDate();
                    String DealBackground = homepageDTO.getResult().getTodayDeal().getBackground().getImage();
                    int timeInMins = ((Integer.parseInt(TodayDealEndTime) - Integer.parseInt(TodayDealTime)) + 1) * 60;
                    String time = globalUtils.getTime();
                    String currentTime[] = time.split(":");
                    int leftTimeinMin = timeInMins - ((Integer.parseInt(currentTime[0]) * 60) + Integer.parseInt(currentTime[1]));
                    startTimer(leftTimeinMin);

                    final List<TodayProduct> ToDealList = homepageDTO.getResult().getTodayDeal().getData();
//                    Log.e("mtodaysize-->", ToDealList.size() + "");
                    if (ToDealList.size() > 2) {
                        todayMore.setVisibility(View.VISIBLE);
                    } else {
                        todayMore.setVisibility(View.GONE);
                    }

                    for (int i = 0; i < ToDealList.size(); i++) {
                        TodayProduct todayProduct = new TodayProduct();
                        todayProduct.setProduct_id(ToDealList.get(i).getProduct_id());
                        todayProduct.setBrand_id(ToDealList.get(i).getBrand_id());
                        todayProduct.setBrand_name(ToDealList.get(i).getBrand_name());
                        todayProduct.setDiscount_price(ToDealList.get(i).getDiscount_price());
                        todayProduct.setOption_id(ToDealList.get(i).getOption_id());
                        todayProduct.setOption_type(ToDealList.get(i).getOption_type());
                        todayProduct.setOption_value_id(ToDealList.get(i).getOption_value_id());
                        todayProduct.setProduct_model(ToDealList.get(i).getProduct_model());
                        todayProduct.setProduct_name(ToDealList.get(i).getProduct_name());
                        todayProduct.setProduct_price(ToDealList.get(i).getProduct_price());
                        todayProduct.setProduct_quantity(ToDealList.get(i).getProduct_quantity());
                        todayProduct.setSpecial_price(ToDealList.get(i).getSpecial_price());
                        todayProduct.setTax_rate(ToDealList.get(i).getTax_rate());

                        String pname1 = ToDealList.get(0).getProduct_name();
                        String pname2 = ToDealList.get(1).getProduct_name();
                        String pname3 = ToDealList.get(2).getProduct_name();
                        String pname4 = ToDealList.get(3).getProduct_name();

                        String price1 = ToDealList.get(0).getProduct_price();
                        String price2 = ToDealList.get(1).getProduct_price();
                        String price3 = ToDealList.get(2).getProduct_price();
                        String price4 = ToDealList.get(3).getProduct_price();

                        todayimgName1.setText(pname1);
                        todayprice1.setText(currencySymbol + new DecimalFormat("0.00").format(Double.parseDouble(price1) * Double.parseDouble(currencyValue)));

                        todayImgName2.setText(pname2);
                        todayPrice2.setText(currencySymbol + new DecimalFormat("0.00").format(Double.parseDouble(price2) * Double.parseDouble(currencyValue)));

                        todayImgName3.setText(pname3);
                        todayPrice3.setText(currencySymbol + new DecimalFormat("0.00").format(Double.parseDouble(price3) * Double.parseDouble(currencyValue)));

                        todayImgName4.setText(pname4);
                        todayPrice4.setText(currencySymbol + new DecimalFormat("0.00").format(Double.parseDouble(price4) * Double.parseDouble(currencyValue)));

                        if (i == 0) {
                            String imageUrl0 = globalUtils.urlWithSuffix(ToDealList.get(0).getProduct_image());
                            Glide.with(this)
                                    .load(globalUtils.IMAGE_BASE_URL + imageUrl0)
                                    .error(R.drawable.img_icon)
                                    .placeholder(R.drawable.img_icon).fitCenter()
                                    .dontAnimate()
                                    .into(todayimg1);
                            todayimg1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, ToDealList.get(0).getProduct_id());
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, ToDealList.get(0).getProduct_name());
                                    startActivity(intent);
                                }
                            });
                        }
                        if (i == 1) {
                            String imageUrl1 = globalUtils.urlWithSuffix(ToDealList.get(1).getProduct_image());
                            Glide.with(this)
                                    .load(globalUtils.IMAGE_BASE_URL + imageUrl1).error(R.drawable.img_icon)
                                    .placeholder(R.drawable.img_icon).fitCenter().dontAnimate()
                                    .into(todayimg2);

                            todayimg2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, ToDealList.get(1).getProduct_id());
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, ToDealList.get(1).getProduct_name());
                                    startActivity(intent);
                                }
                            });
                        }

                        if (i == 2) {
                            String imageUrl2 = globalUtils.urlWithSuffix(ToDealList.get(2).getProduct_image());
                            Glide.with(this)
                                    .load(globalUtils.IMAGE_BASE_URL + imageUrl2)
                                    .error(R.drawable.img_icon)
                                    .placeholder(R.drawable.img_icon).fitCenter().dontAnimate()
                                    .into(todayimg3);

                            todayimg3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, ToDealList.get(2).getProduct_id());
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, ToDealList.get(2).getProduct_name());
                                    startActivity(intent);
                                }
                            });
                        }

                        if (i == 3) {
                            String imageUrl3 = globalUtils.urlWithSuffix(ToDealList.get(3).getProduct_image());
                            Glide.with(this)
                                    .load(globalUtils.IMAGE_BASE_URL + imageUrl3)
                                    .error(R.drawable.img_icon)
                                    .placeholder(R.drawable.img_icon).fitCenter().dontAnimate()
                                    .into(todayimg4);

                            todayimg4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(HomeActivity.this, ProductDetailActivity.class);
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, ToDealList.get(3).getProduct_id());
                                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, ToDealList.get(3).getProduct_name());
                                    startActivity(intent);
                                }
                            });
                        }

                        // todayProduct.setProduct_image(imageUrl);
                    }

                    if (DealBackground.length() != 0) {

                        Picasso.with(this).load(DealBackground).into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                DealBg.setBackground(new BitmapDrawable(getResources(), bitmap));
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                Log.d("TAG", "FAILED");
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                                Log.d("TAG", "Prepare Load");
                            }
                        });
                    }
                } else {
                    DealBg.setVisibility(View.GONE);
                }
//@@@@@@@@@@@@@@@@@@@@@@@@@@  End of Today's deal   |  Starting of AfterToday's Deal  @@@@@@@@@@@@@@@@@@@@@@@@@@@@

                List<TopSellingDatum> bestRC = homepageDTO.getResult().getBestOf().getData();
                if (bestRC.size() > 0) {
                    bestOfHairCareLayout.setVisibility(View.VISIBLE);
                    strBestOfTitle = homepageDTO.getResult().getBestOf().getName();
                    tvBestofLabel.setText("BEST OF " + strBestOfTitle.toUpperCase());
                    if (bestRC.size() <= 3) {
                        Best_more.setVisibility(View.GONE);
                    }
                    horizontalHomeAdapter = new FirstHorizontalHomeAdapter(this, bestRC, HairCare);
                    rvBestOfHairCare.setAdapter(horizontalHomeAdapter);
                } else {
                    bestOfHairCareLayout.setVisibility(View.GONE);
                }

                banner_beforeVideo = homepageDTO.getResult().getAfterTodayDeal();
                if (banner_beforeVideo.size() > 0) {
                    afterCatBannerAdapter = new IntermediateBannerSliderAdapter(this, banner_beforeVideo, HomeActivity.this, BEFORE_VIDEO);
                    beforeVideoViewPager.setAdapter(afterCatBannerAdapter);
                    if (banner_beforeVideo.size() > 1) {
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            int cpage = 0;

                            public void run() {
                                if (cpage == banner_beforeVideo.size()) {
                                    cpage = 0;
                                }
                                beforeVideoViewPager.setCurrentItem(cpage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                    }
                }

                banner_afterbestdata = homepageDTO.getResult().getAfterBestOfData();
                if (banner_afterbestdata.size() > 0) {
                    afterCatBannerAdapter = new IntermediateBannerSliderAdapter(this, banner_afterbestdata, HomeActivity.this, AFTER_BESTOF);
                    afterBestViewPager.setAdapter(afterCatBannerAdapter);
                    if (banner_afterbestdata.size() > 1) {
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            int cpage = 0;

                            public void run() {
                                if (cpage == banner_afterbestdata.size()) {
                                    cpage = 0;
                                }
                                afterBestViewPager.setCurrentItem(cpage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                    }
                }


                String videoURl = "";
                List<VideoDatum> videoData = homepageDTO.getResult().getVideoData();
                for (int i = 0; i < videoData.size(); i++) {
                    VideoDatum videoDatum = new VideoDatum();
                    videoDatum.setViceoId(videoData.get(i).getViceoId());
                    videoDatum.setVideoDescription(videoData.get(i).getVideoDescription());
                    videoDatum.setVideoTitle(videoData.get(i).getVideoTitle());
                    videoDatum.setVideoUrl(videoData.get(i).getVideoUrl());
                    video.add(videoDatum);

                    videoURl = videoData.get(i).getVideoUrl();
                    String title = videoData.get(i).getVideoTitle();
                    String desc = videoData.get(i).getVideoDescription();
                    vidTitle.setText(title);
                    vidDesc.setText(desc);
                    try {
                        yid = extractYoutubeId(videoURl);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                final String strVideo = videoData.get(0).getVideoUrl();
                YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
                getLifecycle().addObserver(youTubePlayerView);

                youTubePlayerView.initialize(new YouTubePlayerInitListener() {
                    @Override
                    public void onInitSuccess(@NonNull final com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer youTubePlayer) {
                        youTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                            @Override
                            public void onReady() {
                                super.onReady();
                                String videoId[] = strVideo.split("=");
//                                 Log.e("VideoUrlSplited",":"+videoId[0]+"\n yid"+yid);
                                youTubePlayer.cueVideo(videoId[1], 0);
                            }
                        });
                    }
                }, true);

//                List<AfterBestOFDatum> aftertodayD = homepageDTO.getResult().getAfterTodayDeal();
//                for (int i = 0; i < aftertodayD.size(); i++) {
//                    AfterBestOFDatum afterTodayDeal = new AfterBestOFDatum();
//                    afterTodayDeal.setBannerAlt(aftertodayD.get(i).getBannerAlt());
//                    afterTodayDeal.setBannerId(aftertodayD.get(i).getBannerId());
//                    afterTodayDeal.setBannerImage(aftertodayD.get(i).getBannerImage());
//                    afterTodayDeal.setLinkId(aftertodayD.get(i).getLinkId());
//                    afterTodayDeal.setLinkType(aftertodayD.get(i).getLinkType());
//
//                    ATDeal.add(afterTodayDeal);
//
//                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            } else if (type.equalsIgnoreCase("3")) {
                secondLoader.setVisibility(View.GONE);
                thirdSectionLayout.setVisibility(View.VISIBLE);

                LinearLayout recentLayout = findViewById(R.id.recently_view_layout);

                List<AfterBestOFDatum> aftershop = new ArrayList<>();
                List<AfterBestOFDatum> footer = new ArrayList<>();
                List<ShopByBrandDatum> shopByBrandList = new ArrayList<>();


                List<ShopByBrandDatum> shopBrand = homepageDTO.getResult().getShopByBrandData();
                if (shopBrand.size() <= 3) {
                    more2.setVisibility(View.GONE);
                }

                for (int i = 0; i < shopBrand.size(); i++) {
                    ShopByBrandDatum shopByBrandDatum = new ShopByBrandDatum();
                    shopByBrandDatum.setBrandId(shopBrand.get(i).getBrandId());
                    shopByBrandDatum.setBrandName(shopBrand.get(i).getBrandName());
                    shopByBrandList.add(shopByBrandDatum);
                }
                shopAdapter = new ShopAdapter(this, shopByBrandList);
                rvShopByBrand.setAdapter(shopAdapter);

                List<TopSellingDatum> Recently = homepageDTO.getResult().getRecentlyViewData();
                if (Recently.size() <= 3) {
                    Recent_more.setVisibility(View.GONE);
                } else if (Recently.size() == 0) {
                    recentLayout.setVisibility(View.GONE);
                }
                horizontalHomeAdapter = new FirstHorizontalHomeAdapter(this, Recently, recentlyviewed);
                rvRecentlyViewed.setAdapter(horizontalHomeAdapter);

                if (Recently.size() < 1) {
                    recentlyViewedLayout.setVisibility(View.GONE);
                }
                List<TopSellingDatum> recommenddata = homepageDTO.getResult().getRecommendedData();
                if (recommenddata.size() <= 3) {
                    Reco_more.setVisibility(View.GONE);
                }
                horizontalHomeAdapter = new FirstHorizontalHomeAdapter(this, recommenddata, recommended);
                rvRecommendedProduct.setAdapter(horizontalHomeAdapter);

                banner_aftershopdata = homepageDTO.getResult().getAfterShopByData();
                if (banner_aftershopdata.size() > 0) {
                    afterCatBannerAdapter = new IntermediateBannerSliderAdapter(this, banner_aftershopdata, HomeActivity.this, AFTER_SHOP);
                    afterShopByViewPager.setAdapter(afterCatBannerAdapter);
                    if (banner_aftershopdata.size() > 1) {
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            int cpage = 0;

                            public void run() {
                                if (cpage == banner_aftershopdata.size()) {
                                    cpage = 0;
                                }
                                afterShopByViewPager.setCurrentItem(cpage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                    }
                }
                banner_footerData = homepageDTO.getResult().getFooterBannerData();
                if (banner_footerData.size() > 0) {
                    afterCatBannerAdapter = new IntermediateBannerSliderAdapter(this, banner_footerData, HomeActivity.this, FOOTER);
                    footerBannerViewPager.setAdapter(afterCatBannerAdapter);
                    if (banner_footerData.size() > 1) {
                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {
                            int cpage = 0;

                            public void run() {
                                if (cpage == banner_footerData.size()) {
                                    cpage = 0;
                                }
                                footerBannerViewPager.setCurrentItem(cpage++, true);
                            }
                        };
                        Timer swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        shimmer1.stopShimmer();
        shimmer2.stopShimmer();
        shimmer3.stopShimmer();
        shimmer4.stopShimmer();
        shimmer5.stopShimmer();
        shimmer6.stopShimmer();
        mainScreen.setVisibility(View.VISIBLE);
        shimmerFrameLayout.setVisibility(View.GONE);

    }

    //Start Countodwn method
    private void startTimer(final int noOfMinutes) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        String oldTime = null;
        oldTime = formatter.format(date);
        String[] dates = oldTime.split(",");
        String[] times = dates[1].split(":");
        int leftHour = noOfMinutes / 60;
        int leftMins = noOfMinutes % 60;
//        Log.e("CurrentDateTime","Hour: "+leftHour+"Mins: "+leftMins);
        int newHours = Integer.parseInt(times[0].trim()) + leftHour;
        int newMins = Integer.parseInt(times[1].trim()) + leftMins;
        String NewTime = dates[0] + ", " + String.valueOf(newHours) + ":" + String.valueOf(newMins);//Timer date 2
        Date oldDate, newDate;
        try {
            oldDate = formatter.parse(oldTime);
            newDate = formatter.parse(NewTime);
//            Log.e("ChangedDateTime","oldDate: "+oldDate+" newDate: "+newDate);
            oldLong = oldDate.getTime();
            NewLong = newDate.getTime();
            diff = NewLong - oldLong;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        MyCount counter = new MyCount(diff, 1000);
        counter.start();
    }

    public String checkDigit(long number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    @Override
    public void bannerClick(int pos) {

        if (homeslider.get(pos).getLinkType().equalsIgnoreCase("Product")) {
            Intent intentPro = new Intent(HomeActivity.this, ProductDetailActivity.class);
            intentPro.putExtra(Constants.PreferenceConstants.PRODUCTID, homeslider.get(pos).getLinkId());
            intentPro.putExtra(Constants.PreferenceConstants.PRODUCTNAME, " ");
            startActivity(intentPro);
        } else if (homeslider.get(pos).getLinkType().equalsIgnoreCase("Category")) {
            Intent intent = new Intent(HomeActivity.this, BrandListActivity.class);
            intent.putExtra(Constants.PreferenceConstants.CATEGORYID, homeslider.get(pos).getLinkId());
            intent.putExtra(Constants.PreferenceConstants.CATEGORYNAME, " ");
            startActivity(intent);
        }
    }


    //methods

    protected static String extractYoutubeId(String url) throws MalformedURLException {
        String id = null;
        try {
            String query = new URL(url).getQuery();
            if (query != null) {
                String[] param = query.split("&");
                for (String row : param) {
                    String[] param1 = row.split("=");
                    if (param1[0].equals("v")) {
                        id = param1[1];
                    }
                }
            } else {
                if (url.contains("embed")) {
                    id = url.substring(url.lastIndexOf("/") + 1);
                }
            }
        } catch (Exception ex) {
            Log.e("Exception", ex.toString());
        }
        return id;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        View v = getCurrentFocus();
        boolean ret = super.dispatchTouchEvent(event);
        if (this instanceof HomeActivity) {
            return false;
        }
        if (v instanceof EditText) {
            View w = getCurrentFocus();
            int scrCoords[] = new int[2];
            w.getLocationOnScreen(scrCoords);
            float x = event.getRawX() + w.getLeft() - scrCoords[0];
            float y = event.getRawY() + w.getTop() - scrCoords[1];
            if (event.getAction() == MotionEvent.ACTION_UP && (x < w.getLeft() || x >= w.getRight() || y < w.getTop() || y > w.getBottom())) {
                InputMethodManager imm = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
        return ret;
    }

    private void getCurrencyList(final String currency, final ProgressDialog progressDialog) {
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<GetCurrencyList> call = restInterface.getCurrencyList(globalUtils.getKey(), globalUtils.getapidate());
        call.enqueue(new Callback<GetCurrencyList>() {
            @Override
            public void onResponse(Call<GetCurrencyList> call, Response<GetCurrencyList> response) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Log.e("GetCurrencyList", "" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                try {
                    if (response.body().getSuccess() == 1) {
//                        ArrayList<GetCurrencyList.>
                        prefClass.setCurrencyDataList((new Gson()).toJson(response.body().getResult().getCurrencyDataList()));
                        prefClass.setSelectedCurrency(currency);
                        requestData();
                    } else {
                        Toast.makeText(HomeActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetCurrencyList> call, Throwable t) {
                Log.e("ExcOffers", "" + t);
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                Toast.makeText(HomeActivity.this, "Some error occurred, please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void afterCatBannerClick(int pos, int type) {
        if (type == BEFORE_VIDEO) {
            if (banner_beforeVideo.get(pos).getLinkType().equalsIgnoreCase("Product")) {
                linkType = "Product";
            } else if (banner_beforeVideo.get(pos).getLinkType().equalsIgnoreCase("Category")) {
                linkType = "Category";
            }
            linkId = banner_beforeVideo.get(pos).getLinkId();
            openBrandProduct(linkType, linkId, pos);
        } else if (type == AFTER_BESTOF) {
            if (banner_afterbestdata.get(pos).getLinkType().equalsIgnoreCase("Product")) {
                linkType = "Product";
            } else if (banner_afterbestdata.get(pos).getLinkType().equalsIgnoreCase("Category")) {
                linkType = "Category";
            }
            linkId = banner_afterbestdata.get(pos).getLinkId();
            openBrandProduct(linkType, linkId, pos);
        } else if (type == AFTER_SHOP) {

            if (banner_aftershopdata.get(pos).getLinkType().equalsIgnoreCase("Product")) {
                linkType = "Product";
            } else if (banner_aftershopdata.get(pos).getLinkType().equalsIgnoreCase("Category")) {
                linkType = "Category";
            }
            linkId = banner_aftershopdata.get(pos).getLinkId();
            openBrandProduct(linkType, linkId, pos);

        } else if (type == FOOTER) {
            if (banner_footerData.get(pos).getLinkType().equalsIgnoreCase("Product")) {
                linkType = "Product";
            } else if (banner_footerData.get(pos).getLinkType().equalsIgnoreCase("Category")) {
                linkType = "Category";
            }
            linkId = banner_footerData.get(pos).getLinkId();
            openBrandProduct(linkType, linkId, pos);
        }
    }


    private class GetIpAddress extends AsyncTask<String, String, JSONObject> {
        JSONObject result = null;

        @Override
        protected JSONObject doInBackground(String... strings) {
            try {
                Request request = new Request.Builder()
                        .url("https://api.ipify.org?format=json")
                        .get()
                        .build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(30, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();
                okhttp3.Response response = client.newCall(request).execute();
                result = new JSONObject(response.body().string());
                Log.e("ResultIp", ":" + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            Log.e("IpResponse", ":" + jsonObject);

        }
    }

    public class MyCount extends CountDownTimer {
        MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            tvTodayTimer.setText("00:00:00");
            tvTodayTimer.setTextColor(getResources().getColor(R.color.red));
            DealBg.setVisibility(View.GONE);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long millis = millisUntilFinished;
//            (TimeUnit.MILLISECONDS.toDays(millis)) + "Day "+

            int hourOfDay = (int) (TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(millis)));
            int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)));
            int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            String h = String.valueOf(hourOfDay);
            String m = String.valueOf(minutes);
            String s = String.valueOf(seconds);
            if (hourOfDay < 10) {
                h = "0" + hourOfDay;
            }
            if (minutes < 10) {
                m = "0" + minutes;
            }

            String hms = h + " h " + m + " m " + " remaining";
            int timeInMin = (int) minutes * 60 + (int) seconds;
            String timeTaken = String.valueOf(timeInMin);
            tvTodayTimer.setText(hms);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        this.setIntent(intent);
        Log.e("DeepLink", ":" + intent);
    }
//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  Checking for app updates

    private void checkForAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(HomeActivity.this);

        // Returns an intent object that you use to check for an update.
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

        // Create a listener to track request state updates.
        installStateUpdatedListener = new InstallStateUpdatedListener() {
            @Override
            public void onStateUpdate(InstallState installState) {
                // Show module progress, log state, or install the update.
                if (installState.installStatus() == InstallStatus.DOWNLOADED)
                    // After the update is downloaded, show a notification
                    // and request user confirmation to restart the app.
                    popupSnackbarForCompleteUpdateAndUnregister();
            }
        };

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                    // Before starting an update, register a listener for updates.
                    appUpdateManager.registerListener(installStateUpdatedListener);
                    // Start an update.
                    startAppUpdateFlexible(appUpdateInfo);
                } else if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    // Start an update.
                    startAppUpdateImmediate(appUpdateInfo);
                }
            }
        });
    }

    private void startAppUpdateImmediate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    private void startAppUpdateFlexible(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    appUpdateInfo,
                    AppUpdateType.FLEXIBLE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    REQ_CODE_VERSION_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
            unregisterInstallStateUpdListener();
        }
    }

    /**
     * Displays the snackbar notification and call to action.
     * Needed only for Flexible app update
     */
    private void popupSnackbarForCompleteUpdateAndUnregister() {
        Snackbar snackbar =
                Snackbar.make(findViewById(android.R.id.content), getString(R.string.update_downloaded), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.restart, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.primary_blue));
        snackbar.show();

        unregisterInstallStateUpdListener();
    }

    /**
     * Checks that the update is not stalled during 'onResume()'.
     * However, you should execute this check at all app entry points.
     */
    private void checkNewAppVersionState() {
        appUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {
                            //FLEXIBLE:
                            // If the update is downloaded but not installed,
                            // notify the user to complete the update.
                            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                                popupSnackbarForCompleteUpdateAndUnregister();
                            }

                            //IMMEDIATE:
                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                startAppUpdateImmediate(appUpdateInfo);
                            }
                        });

    }

    /**
     * Needed only for FLEXIBLE update
     */
    private void unregisterInstallStateUpdListener() {
        if (appUpdateManager != null && installStateUpdatedListener != null)
            appUpdateManager.unregisterListener(installStateUpdatedListener);
    }
}
