package com.gaurashtra.app.view.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.bean.OptionListBean;
import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.MultiplePulseRing;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.view.fragment.CategoryFragment;
import com.gaurashtra.app.view.fragment.MeFragment;
import com.gaurashtra.app.view.fragment.SearchFragment;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.Utils.ViewSwapper;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.BestOfBean.BestOfDTO;
import com.gaurashtra.app.model.bean.HomePagebean.HomepageDTO;
import com.gaurashtra.app.model.bean.HomePagebean.TodayProduct;
import com.gaurashtra.app.model.bean.HomePagebean.TopSellingDatum;
import com.gaurashtra.app.model.bean.TopSellingBean.TopSellingDTO;
import com.gaurashtra.app.presentator.ProductListActivityPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.ProductListActivityPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.ProductListActivityInteractor;
import com.gaurashtra.app.view.adapter.TodayAdapter;
import com.gaurashtra.app.view.adapter.ViewAdapter;
import com.gaurashtra.app.view.adapter.ViewMoreAdapter;
//import com.gaurashtra.app.view.fragment.catFragment.HairFragement;
import retrofit2.Call;
import retrofit2.Callback;

public class ProductListActivity extends AppCompatActivity implements ProductListActivityInteractor, ViewMoreAdapter.AddToCartInterface{
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;
    private ViewSwapper viewSwapper;
    private ViewAdapter viewSwapperAdapter;
    private ViewPager viewPager;
    //    private HairFragement hairFragement;
    public static TextView textviewProductTitle;
    private GlobalUtils globalUtils;
    RecyclerView recyclerView;
    private ViewMoreAdapter adapter;
    private TodayAdapter todayAdapter;
    SharedPreference sharedPreference;
    String userid, catid, optionId="",optionValueId="";
    private ProductListActivityPresentarInteractor productListActivityPresentarInteractor;
    RestInterface  restInterface;
    String title="",currency="INR",currencySymbol="",strBestOfTitle, deviceId="vwdhgch",pageNo="1", perpageData="10";
    double actualCurrValue=1.0,currencyValue=1.0;
    PrefClass prefClass;
    TextView textCartItemCount;
    ArrayList<OptionListBean.Result.ProductData.Option> productOptionList;
    private List<TopSellingDatum> commonCatList=new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems, currentPage=1, totalPages=1;
    FrameLayout progress;
    List<TopSellingDatum> catList;
    List<TopSellingDatum> bestdata;
    List<TopSellingDatum> recently;
    List<TopSellingDatum> recommend;
    List<TodayProduct> deals1;
    private static int firstVisiblePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(                                   //Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewProductTitle = viewActionBar.findViewById(R.id.actionbar_textview);

        if(!SharedPreference.getDeviceId(ProductListActivity.this).isEmpty()){
            deviceId = SharedPreference.getDeviceId(ProductListActivity.this);
        }
        if(getIntent().hasExtra(Constants.PreferenceConstants.TITLE)){
            title= getIntent().getStringExtra(Constants.PreferenceConstants.TITLE);
            Log.e("Title",":"+title);
        }
        textviewProductTitle.setText(title);
//        abar.setHomeAsUpIndicator(R.drawable.img_back_arrow);
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(true);
        abar.setDisplayHomeAsUpEnabled(true);


        UserUpdate userUpdate= new UserUpdate(ProductListActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(ProductListActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(ProductListActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }

        if(GlobalUtils.isNetworkAvailable(this)) {
            new UserUpdate(ProductListActivity.this);
        }else{
            Toast.makeText(userUpdate, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
        setUpContentView();
        if(!SharedPreference.getUserid(ProductListActivity.this).isEmpty() &&
                !SharedPreference.getCartCount(this).isEmpty()) {
            textCartItemCount.setVisibility(View.VISIBLE );
            textCartItemCount.setText(SharedPreference.getCartCount(this));
        }else{
            textCartItemCount.setVisibility(View.GONE);
        }
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        userid = SharedPreference.getUserid(this);
        catid = SharedPreference.getcategoryId(this);
        globalUtils = new GlobalUtils();
        productListActivityPresentarInteractor = new ProductListActivityPresentar(this);

        requestData();

        prefClass= new PrefClass(this);
        java.lang.reflect.Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                currency=prefClass.getSelectedCurrency();
                actualCurrValue= Float.parseFloat(currencyData.getValue());
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                currencySymbol= currencyData.getSymbol().trim().trim();
            }
        }
    }

    private void requestData() {
        Log.e("Page no",":"+pageNo+"\n title: "+title);
        if(GlobalUtils.isNetworkAvailable(this)) {
            if(title.equalsIgnoreCase(Constants.PreferenceConstants.TOP_SELLING_TITLE)) {
                productListActivityPresentarInteractor.sendRequest(userid, pageNo, perpageData );
            }else if(title.equalsIgnoreCase(Constants.PreferenceConstants.RECOMM_TITLE)){
                productListActivityPresentarInteractor.sendRecommendRequest(userid, pageNo , perpageData );
            }else if(title.equalsIgnoreCase(Constants.PreferenceConstants.RECENT_TITLE)){
                productListActivityPresentarInteractor.sendRecentlyRequest(userid, deviceId, pageNo , perpageData );
            }else if(title.equalsIgnoreCase(Constants.PreferenceConstants.TODAYDEAL_TITLE)){
                productListActivityPresentarInteractor.sendDealsRequest(userid, pageNo , perpageData );
            }else if(title.contains("Best of")){
                productListActivityPresentarInteractor.sendBestRequest(userid, pageNo , perpageData );
            }
        }else{
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
        }
    }

    public void setUpContentView() {

        bottomNavigationView = findViewById(R.id.view_bottom_navigation);
        View v = bottomNavigationView.findViewById(R.id.action_four);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
        View badge = LayoutInflater.from(this).inflate(R.layout.cart_icon_layout, itemView, true);
        textCartItemCount = badge.findViewById(R.id.cart_badge);

        progress = findViewById(R.id.list_loader_frame);
//        Sprite bounce = new MultiplePulseRing();
//        progress.setIndeterminateDrawable(bounce);
        recyclerView=findViewById(R.id.rvbrandList);
        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        firstVisiblePosition= linearLayoutManager.findFirstVisibleItemPosition();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollOutItems = linearLayoutManager.findFirstVisibleItemPosition();
                Log.e("onScroll","currentItems: "+currentItems+"\n totalItems: "+totalItems+
                        "\n scrollOutItems: "+scrollOutItems+"\ncurrentPage: "+currentPage+"\n totalPages: "+totalPages);
                if(isScrolling && (currentItems + scrollOutItems == totalItems) && currentPage< totalPages && totalPages>1) {
                    if (dy > 0) {
                        // scrolling up
                         pageNo = String.valueOf(currentPage + 1);
                         } else {
                        // scrolling down
//                            if(!pageNo.equalsIgnoreCase("1")) {
//                                pageNo = String.valueOf(currentPage - 1);
//                            }
                         }
                        isScrolling = false;
                        progress.setVisibility(View.VISIBLE);
                        requestData();
                }
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        bottomNavigationView = findViewById(R.id.view_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_SELECTED);
            switch (item.getItemId()) {
                case R.id.action_one:
                    textviewProductTitle.setText("Categories");
                    Intent intentHome= new Intent(ProductListActivity.this, HomeActivity.class);
                    startActivity(intentHome);
                    return true;
                case R.id.action_two:
                    textviewProductTitle.setText("Categories");
                    fragment = new CategoryFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.action_three:
                    if(!SharedPreference.getUserid(ProductListActivity.this).isEmpty()){
                    textviewProductTitle.setText("My Account");
                    fragment = new MeFragment();
                    loadFragment(fragment);
                    }else{
                        Intent intLogin= new Intent(ProductListActivity.this, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, ProductDetailActivity.class.getSimpleName());
                        startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                    return true;
                case R.id.action_four:
                    if(!SharedPreference.getUserid(ProductListActivity.this).isEmpty()) {
                        textviewProductTitle.setText("Cart");
                        Intent intent = new Intent(ProductListActivity.this, CartActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intLogin= new Intent(ProductListActivity.this, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, ProductDetailActivity.class.getSimpleName());
                        startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                    return true;
                case R.id.action_five:
                    textviewProductTitle.setText("Search");
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PreferenceConstants.BACKFROM,"LIST");
                    fragment = new SearchFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }

    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_allprod_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textviewProductTitle.setText(title);
        bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_UNLABELED);
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
    public void getTopSellingCategoryData(Object object) {
        progress.setVisibility(View.GONE);
        try {
            if(pageNo.equalsIgnoreCase("1")) {
                catList = new ArrayList<>();
            }
            TopSellingDTO categoryListResponseDTO = (TopSellingDTO) object;
            String message = categoryListResponseDTO.getMessage();
            currentPage = categoryListResponseDTO.getResult().getCurrentPage();
            totalPages = categoryListResponseDTO.getResult().getTotalPage();
//            GlobalUtils.showToast(this, message);
            List<TopSellingDatum> catData = categoryListResponseDTO.getResult().getTopSellingData();
            Log.e("TopDataSize", "" + catData.size());
            for (int i = 0; i < catData.size(); i++) {
                TopSellingDatum data = new TopSellingDatum();
                String prodName=catData.get(i).getProductName();
                if(prodName != null) {
                    data.setProductName(prodName);
                    data.setProductId(catData.get(i).getProductId());
                    data.setProductImage(catData.get(i).getProductImage());
                    data.setDiscountPrice(catData.get(i).getDiscountPrice());
                    data.setProductPrice(catData.get(i).getProductPrice());
                    data.setSpecialPrice(catData.get(i).getSpecialPrice());
                    data.setTaxRate(catData.get(i).getTaxRate());
                    data.setProductQuantity(catData.get(i).getProductQuantity());
                    data.setOptionId(catData.get(i).getOptionId());
                    data.setOptionValueId(catData.get(i).getOptionValueId());
                    data.setOptionType(catData.get(i).getOptionType());
                    data.setProductInStock(catData.get(i).getProductInStock());
                    catList.add(data);
                }
            }
            if(pageNo.equalsIgnoreCase("1")) {
                adapter = new ViewMoreAdapter(this, catList, ProductListActivity.this);
                recyclerView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void getBetOfResponse(Object object) {
        progress.setVisibility(View.GONE);
        try{
            if(pageNo.equalsIgnoreCase("1")) {
                bestdata = new ArrayList<>();
            }
            BestOfDTO bestOfDTO=(BestOfDTO)object;
            List<TopSellingDatum> BestData= bestOfDTO.getResult().getBestOf().getData();
            currentPage = bestOfDTO.getResult().getCurrentPage();
            totalPages = bestOfDTO.getResult().getTotalPage();
            for (int i=0;i<BestData.size();i++){
                TopSellingDatum best=new TopSellingDatum();
                best.setProductId(BestData.get(i).getProductId());
                best.setProductImage(BestData.get(i).getProductImage());
                String prodName=BestData.get(i).getProductName();
//            if(prodName.contains("&amp;")){
//                prodName.replace("amp;","");
//            }

                if(prodName != null) {
                    best.setProductName(prodName);
                    best.setProductPrice(BestData.get(i).getProductPrice());
                    best.setSpecialPrice(BestData.get(i).getSpecialPrice());
                    best.setDiscountPrice(BestData.get(i).getDiscountPrice());
                    best.setProductQuantity(BestData.get(i).getProductQuantity());
                    best.setOptionId(BestData.get(i).getOptionId());
                    best.setTaxRate(BestData.get(i).getTaxRate());
                    best.setOptionValueId(BestData.get(i).getOptionValueId());
                    best.setOptionType(BestData.get(i).getOptionType());
                    best.setProductInStock(BestData.get(i).getProductInStock());

                    bestdata.add(best);
                }
            }
            if(pageNo.equalsIgnoreCase("1")) {
                adapter = new ViewMoreAdapter(this, bestdata, ProductListActivity.this);
                recyclerView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getRecentlyResponse(Object object) {
        progress.setVisibility(View.GONE);
        try{
            if(pageNo.equalsIgnoreCase("1")) {
                recently = new ArrayList<>();
            }
            TopSellingDTO recentlydata=(TopSellingDTO)object;
            List<TopSellingDatum> RecentlyData=recentlydata.getResult().getRecentlyViewData();
            currentPage = recentlydata.getResult().getCurrentPage();
            totalPages = recentlydata.getResult().getTotalPage();
            for (int i=0;i<RecentlyData.size();i++){
                TopSellingDatum topSellingDatum=new TopSellingDatum();
                topSellingDatum.setProductId(RecentlyData.get(i).getProductId());
                topSellingDatum.setProductImage(RecentlyData.get(i).getProductImage());
                String prodName=RecentlyData.get(i).getProductName();
//            if(prodName.contains("&amp;")){
//                prodName.replace("amp;","");
//            }
                topSellingDatum.setProductName(prodName);
                topSellingDatum.setSpecialPrice(RecentlyData.get(i).getSpecialPrice());
                topSellingDatum.setDiscountPrice(RecentlyData.get(i).getDiscountPrice());
                topSellingDatum.setProductPrice(RecentlyData.get(i).getProductPrice());
                topSellingDatum.setProductQuantity(RecentlyData.get(i).getProductQuantity());
                topSellingDatum.setOptionId(RecentlyData.get(i).getOptionId());
                topSellingDatum.setTaxRate(RecentlyData.get(i).getTaxRate());
                topSellingDatum.setOptionValueId(RecentlyData.get(i).getOptionValueId());
                topSellingDatum.setOptionType(RecentlyData.get(i).getOptionType());
                topSellingDatum.setProductInStock(RecentlyData.get(i).getProductInStock());

                recently.add(topSellingDatum);
            }
            if(pageNo.equalsIgnoreCase("1")) {
                adapter = new ViewMoreAdapter(this, recently, ProductListActivity.this);
                recyclerView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getRecommendResponse(Object object) {
        progress.setVisibility(View.GONE);
        try{
            if(pageNo.equalsIgnoreCase("1")) {
               recommend = new ArrayList<>();
            }
            TopSellingDTO recommenddto=(TopSellingDTO) object;
            List<TopSellingDatum> recoData=recommenddto.getResult().getRecommendedData();
            currentPage = recommenddto.getResult().getCurrentPage();
            totalPages = recommenddto.getResult().getTotalPage();
            for(int i=0;i<recoData.size();i++){
                TopSellingDatum reData=new TopSellingDatum();
                reData.setProductId(recoData.get(i).getProductId());
                reData.setProductImage(recoData.get(i).getProductImage());
                String prodName=recoData.get(i).getProductName();
//            if(prodName.contains("&amp;")){
//                prodName.replace("amp;","");
//            }
                reData.setProductName(prodName);
                reData.setSpecialPrice(recoData.get(i).getSpecialPrice());
                reData.setProductPrice(recoData.get(i).getProductPrice());
                reData.setDiscountPrice(recoData.get(i).getDiscountPrice());
                reData.setProductQuantity(recoData.get(i).getProductQuantity());
                reData.setOptionId(recoData.get(i).getOptionId());
                reData.setTaxRate(recoData.get(i).getTaxRate());
                reData.setOptionValueId(recoData.get(i).getOptionValueId());
                reData.setOptionType(recoData.get(i).getOptionType());
                reData.setProductInStock(recoData.get(i).getProductInStock());
                recommend.add(reData);
            }
            if(pageNo.equalsIgnoreCase("1")) {
                adapter = new ViewMoreAdapter(this, recommend, ProductListActivity.this);
                recyclerView.setAdapter(adapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void getDealResponse(Object object) {
        progress.setVisibility(View.GONE);
        try{
            if(pageNo.equalsIgnoreCase("1")) {
               deals1 = new ArrayList<>();
            }
            HomepageDTO homepageDTO=(HomepageDTO) object;
            List<TodayProduct> dealDataList = homepageDTO.getResult().getTodayDeal().getData();
            currentPage = homepageDTO.getResult().getCurrentPage();
            totalPages = homepageDTO.getResult().getTotalPage();
            Log.e("TopData",""+dealDataList);
            for (int i = 0; i<dealDataList.size(); i++){
                TodayProduct todayProduct = new TodayProduct();
                todayProduct.setProduct_id(dealDataList.get(i).getProduct_id());
                todayProduct.setProduct_image(dealDataList.get(i).getProduct_image());
                String prodName=dealDataList.get(i).getProduct_name();
//            if(prodName.contains("&amp;")){
//                prodName.replace("amp;","");
//            }
                todayProduct.setSpecial_price(dealDataList.get(i).getSpecial_price());
                todayProduct.setProduct_price(dealDataList.get(i).getProduct_price());
                todayProduct.setDiscount_price(dealDataList.get(i).getDiscount_price());
                todayProduct.setProduct_quantity(dealDataList.get(i).getProduct_quantity());
                todayProduct.setOption_id(dealDataList.get(i).getOption_id());
                todayProduct.setTax_rate(dealDataList.get(i).getTax_rate());
                todayProduct.setOption_value_id(dealDataList.get(i).getOption_value_id());
                todayProduct.setOption_type(dealDataList.get(i).getOption_type());
                todayProduct.setProductInStock(dealDataList.get(i).getProductInStock());

                deals1.add(todayProduct);
                Log.e("desize-->",deals1.size()+"");
            }
            if(pageNo.equalsIgnoreCase("1")) {
                todayAdapter = new TodayAdapter(ProductListActivity.this, deals1);
                recyclerView.setAdapter(todayAdapter);
            }else{
                adapter.notifyDataSetChanged();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void addToCart(String productId, String qty, String action, String optId, String optValue) {
        optionId= optId;
        optionValueId= optValue;
        try{
            if (GlobalUtils.isNetworkAvailable(this)) {
                GlobalUtils.showdialog(this);
                HashMap<String, String> params = new HashMap<>();
                userid=SharedPreference.getUserid(ProductListActivity.this);
                params.put("userid", userid);
                params.put("productid", productId);
                params.put("quantity", qty);
                params.put("actiontype", action);
                params.put("currencyCode", currency);
                params.put("currencyValue", String.valueOf(currencyValue));
                params.put("optionid",optionId);
                params.put("optionvalueid",optionValueId);
                restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
                Call<AddCartResponseDTO> call = restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(), params);
                call.enqueue(new Callback<AddCartResponseDTO>() {
                    @Override
                    public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
                        GlobalUtils.hidedialog();
                        if (response.isSuccessful()) {
                            Log.e("addToCartProdList", ":" + response.body().getMessage());
                            Toast.makeText(ProductListActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            if(!SharedPreference.getUserid(ProductListActivity.this).isEmpty() &&
                                    !SharedPreference.getCartCount(ProductListActivity.this).isEmpty()) {
                                textCartItemCount.setVisibility(View.VISIBLE );
                                textCartItemCount.setText(SharedPreference.getCartCount(ProductListActivity.this));
                            }else{
                                textCartItemCount.setVisibility(View.GONE);
                            }
                        } else {
                            Log.e("addToCartProdList", ":" + response.message());
                            Toast.makeText(ProductListActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddCartResponseDTO> call, Throwable t) {
                        GlobalUtils.hidedialog();
                        Toast.makeText(ProductListActivity.this, "Please try again!!", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public void selectedOption(int selectedIdx, Boolean isMain) {
//        new ViewMoreAdapter(this,commonCatList, TopSellingid, ProductListActivity.this);
//    }
}
