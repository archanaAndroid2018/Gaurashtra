package com.gaurashtra.app.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.Utils.UserUpdate;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.bean.categoryDetailListbean.CategoryDetailListResponseDTO;
import com.gaurashtra.app.model.bean.categoryDetailListbean.ProductDatum;
import com.gaurashtra.app.model.bean.categoryDetailListbean.SubCatBannerDatum;
import com.gaurashtra.app.model.bean.categoryDetailListbean.SubCatDatum;
import com.gaurashtra.app.presentator.BrandListPresentar;
import com.gaurashtra.app.presentator.presentarInteractor.BrandListPresentarInteractor;
import com.gaurashtra.app.view.activity.interactor.BrandListActivityIntegractor;
import com.gaurashtra.app.view.adapter.BrandListAdapter;
import com.gaurashtra.app.view.adapter.ViewAdapter;
import com.google.gson.Gson;

public class BrandListActivity extends AppCompatActivity implements BrandListActivityIntegractor, TabLayout.OnTabSelectedListener, BrandListAdapter.ClickListener {
    private BrandListPresentarInteractor brandListPresentarInteractor;
    private TextView textviewTitle;
    private RecyclerView brandList;
    private BrandListAdapter adapter;
    private String name,id;
    private TabLayout tabLayout;
    ArrayList<ProductDatum> productList = new ArrayList<>();
    private GlobalUtils globalUtils;
    private PrefClass prefClass;
    private ViewAdapter viewSwapperAdapter;
    private ViewPager viewPager;
    private SharedPreference sharedPreference;
    LinearLayout listLayout, categoryLayout;
    String UserId,CatId,pageNo="1", perpageData="10";
    List<SubCatDatum> subcatlist=new ArrayList<>();
    List<SubCatBannerDatum> bannerList= new ArrayList<>();
    CategoryDetailListResponseDTO categoryDetailListResponseDTO;
    ProgressDialog progressDialog;
    int selectedIdx;
    Boolean isButtonLocked=false;
//    PagerAdapter pagerAdapter;
    String bannerPos=" ";
    LinearLayoutManager linearLayoutManager;
    Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems, currentPage=1, totalPages=1;
//    FrameLayout progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_list);
        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        name = getIntent().getStringExtra(Constants.PreferenceConstants.CATEGORYNAME);
        id   = getIntent().getStringExtra(Constants.PreferenceConstants.CATEGORYID);
      //  System.out.println("mID--->"+id);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        categoryLayout = findViewById(R.id.llmain);
//        if (name.length() != 0){
            textviewTitle.setText(name);
//        }
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        UserUpdate userUpdate= new UserUpdate(BrandListActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(BrandListActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(BrandListActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        globalUtils = new GlobalUtils();
        UserId=SharedPreference.getUserid(this);
        prefClass = new PrefClass(this);
        brandListPresentarInteractor = new BrandListPresentar(this);
        Log.e("BrandListParam","\n user id: "+UserId+"\n product id: "+id);
        setUpContentView();
        if(GlobalUtils.isNetworkAvailable(BrandListActivity.this)) {
            isButtonLocked=true;
            CatId = id;
            brandListPresentarInteractor.validateCategoryRequest(UserId, id,pageNo , perpageData);
        }else{
            isButtonLocked=false;
            Toast.makeText(BrandListActivity.this, "", Toast.LENGTH_SHORT).show();
        }
//        callProductByCat(UserId, id);
    }

    public void setUpContentView(){
//        progress = findViewById(R.id.list_loader_frame);
        viewPager = (ViewPager) findViewById(R.id.pager);
        listLayout = findViewById(R.id.list_layout);
        brandList = findViewById(R.id.rvbrandList);
        tabLayout=findViewById(R.id.tabLayout);
        brandList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        brandList.setLayoutManager(linearLayoutManager);
        brandList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                Log.e("onScroll2","currentItems: "+currentItems+"\n totalItems: "+totalItems+"\n scrollOutItems: "+scrollOutItems);
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
//                    progress.setVisibility(View.VISIBLE);
                    brandListPresentarInteractor.validateCategoryRequest(UserId, CatId,pageNo,perpageData);

                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if(!isButtonLocked) {
                    isButtonLocked=true;
                    Log.e("Scrolled", ":Yes " + i + "\n index1: " + i1);
                    viewPager.setCurrentItem(i);
                    viewPager.setRotationY(180);
                    brandList.setVisibility(View.GONE);
                    productList.clear();
                    Log.e("BrandListParam", "user id: " + UserId + "\nproduct id: " + subcatlist.get(i).getCategoryId());
                    if (GlobalUtils.isNetworkAvailable(BrandListActivity.this)) {
//                    callProductByCat(UserId, subcatlist.get(i).getCategoryId());
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                        CatId = subcatlist.get(i).getCategoryId();
                        brandListPresentarInteractor.validateCategoryRequest(UserId, CatId, pageNo,perpageData );
                    } else {
                        Toast.makeText(BrandListActivity.this, "No product available for this category!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onPageSelected(int i) {
                Log.e("SelectedPage",":Yes "+i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("ScrollStateChanged",":Yes "+i);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        try {
            if(progressDialog != null){
                progressDialog.dismiss();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(BrandListActivity.this,HomeActivity.class);
        intent.putExtra(Constants.PreferenceConstants.BACKFROM,"CAT");
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void showProgress() {
        for(int i = 0; i < tabLayout.getChildCount(); i++) {
            tabLayout.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        try {
            progressDialog= new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(android.R.style.DeviceDefault_Light_ButtonBar_AlertDialog);
            progressDialog.setCancelable(true);
            progressDialog.show();
        }catch (Exception e){
            progressDialog.dismiss();
            e.printStackTrace();
        }

//        GlobalUtils.showdialog(this);
    }

    @Override
    public void hideProgress() {
        try {
            if(progressDialog != null){
                progressDialog.dismiss();
            }
            for(int i = 0; i < tabLayout.getChildCount(); i++) {
                tabLayout.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void validationResponse(String msg) {

//        globalUtils.showToast(this,msg);
    }

    @Override
    public void getCategoryResponse(Object object) {
//        progress.setVisibility(View.GONE);
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
        isButtonLocked=false;
        hideProgress();
        categoryDetailListResponseDTO = (CategoryDetailListResponseDTO) object;
        Log.e("CategoryRes",":"+new Gson().toJson(object));
//        List<MainCat> maincat = new ArrayList<>();

        currentPage = categoryDetailListResponseDTO.getResult().getCurrentPage();
        totalPages = categoryDetailListResponseDTO.getResult().getTotalPage();
        String MainCat = categoryDetailListResponseDTO.getResult().getCateProductList().getMainCat().getName();
        String id = categoryDetailListResponseDTO.getResult().getCateProductList().getMainCat().getId();

        textviewTitle.setText(MainCat);
        List<SubCatDatum> subCatdata = categoryDetailListResponseDTO.getResult().getCateProductList().getSubCatData();
        List<SubCatBannerDatum> bannerdata = categoryDetailListResponseDTO.getResult().getCateProductList().getSubCatBannerData();
        List<ProductDatum> productData=categoryDetailListResponseDTO.getResult().getCateProductList().getProductData();

        subcatlist.clear();
        if(subCatdata.size()>0) {
            categoryLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < subCatdata.size(); i++) {
                SubCatDatum subCatDatum = new SubCatDatum();
                subCatDatum.setCategoryId(subCatdata.get(i).getCategoryId());
                subCatDatum.setName(subCatdata.get(i).getName());
                subCatDatum.setSelected(subCatdata.get(i).getSelected());
                subcatlist.add(subCatDatum);
            }
        }else{
            categoryLayout.setVisibility(View.GONE);
        }
        for (int j = 0; j < subcatlist.size(); j++) {
            if (tabLayout.getTabCount() < subcatlist.size()) {
                tabLayout.addTab(tabLayout.newTab().setText(subcatlist.get(j).getName().replace("&amp;","&")));
            }
        }
        try {
            if(currentPage==1) {
                bannerList = new ArrayList<>();
            }
            if (bannerdata.size() > 0) {
                for (int k = 0; k < bannerdata.size(); k++) {
                    SubCatBannerDatum bannerDatum = new SubCatBannerDatum();
                    bannerDatum.setBannerId(bannerdata.get(k).getBannerId());
                    bannerDatum.setBannerAlt(bannerdata.get(k).getBannerAlt());
                    bannerDatum.setBannerImage(bannerdata.get(k).getBannerImage());
                    bannerDatum.setBannerPosition(bannerdata.get(k).getBannerPosition());
                    bannerPos = bannerdata.get(k).getBannerPosition();
                    if(productData.size()>0 && Integer.parseInt(bannerPos)>productData.size()-1){
                        bannerDatum.setBannerPosition(String.valueOf(productData.size()-1));
                        bannerPos=String.valueOf(productData.size()-1);
                    }
                    bannerDatum.setLinkId(bannerdata.get(k).getLinkId());
                    bannerList.add(bannerDatum);
                }
            } else {
                bannerPos = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int u = 0; u < tabLayout.getTabCount(); u++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(u);
            ViewGroup.MarginLayoutParams mp = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            mp.setMargins(10, 10, 10, 10);
            if (u < subcatlist.size()) {
                tab.requestLayout();
            }
            if (subcatlist.get(u).getSelected().equalsIgnoreCase("Y")) {
                selectedIdx=u;
                tabLayout.getTabAt(u).select();
            }
        }
        tabLayout.postDelayed(new Runnable() {
            public void run() {
                tabLayout.setScrollPosition(selectedIdx,0f,true);
            }
        }, 100L);
        tabLayout.setOnTabSelectedListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            listLayout.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    Log.w("Scroll Value",":"+scrollX+"\n "+scrollY+"\n "+oldScrollX+" \n "+oldScrollY);
                }
            });
        }
        if(currentPage==1) {
            productList = new ArrayList<>();
        }
        for(int i=0; i<productData.size(); i++) {
            try {
                if (productData.get(i).getProductId() != null) {
                    ProductDatum datum= new ProductDatum();
                    datum.setProductId(productData.get(i).getProductId());
                    datum.setProductImage(productData.get(i).getProductImage());
                    datum.setProductQuantity(productData.get(i).getProductQuantity());
                    datum.setProductPrice(productData.get(i).getProductPrice());
                    datum.setProductName(productData.get(i).getProductName());
                    datum.setSpecialPrice(productData.get(i).getSpecialPrice());
                    datum.setDiscountQuantity(productData.get(i).getDiscountQuantity());
                    datum.setDiscountPrice(productData.get(i).getDiscountPrice());
                    datum.setBrandId(productData.get(i).getBrandId());
                    Log.e("BrandId",":"+productData.get(i).getBrandId());
                    datum.setBrandName(productData.get(i).getBrandName());
                    datum.setTaxRate(productData.get(i).getTaxRate());
                    datum.setOptionId(productData.get(i).getOptionId());
                    datum.setOptionValueId(productData.get(i).getOptionValueId());
                    datum.setOptionValueId(productData.get(i).getOptionType());
                    datum.setProductInStock(productData.get(i).getProductInStock());
                    productList.add(datum);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
            }
        }
        if(currentPage==1) {
            if (productList.size() > 0) {
                brandList.setVisibility(View.VISIBLE);
            }
            adapter = new BrandListAdapter(this, (ArrayList<ProductDatum>) productList, name, bannerList, bannerPos, BrandListActivity.this);
            brandList.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (!isButtonLocked) {
            currentPage=1;
            totalPages=1;
            pageNo="1";
            isButtonLocked=true;
            brandList.setVisibility(View.GONE);
            Log.e("TabSel", ":" + tab.getText());
            String selectedTab = tab.getText().toString();
            for (int i = 0; i < subcatlist.size(); i++) {
                if (subcatlist.get(i).getName().replace("&amp;", "&").equalsIgnoreCase(selectedTab)) {
                    viewPager.setCurrentItem(i);
                    viewPager.setRotationY(180);
                    productList.clear();
                    Log.e("BrandListParam", "user id: " + UserId + "\nproduct id: " + subcatlist.get(i).getCategoryId());
                    if (GlobalUtils.isNetworkAvailable(BrandListActivity.this)) {
//                callProductByCat(UserId, subcatlist.get(i).getCategoryId());
                        CatId = subcatlist.get(i).getCategoryId();
                        brandListPresentarInteractor.validateCategoryRequest(UserId,CatId,pageNo, perpageData );
                    } else {
                        Toast.makeText(BrandListActivity.this, "No product available for this category!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void ListClickListener(String prodId, String prodName, String prodPrice, String imgUrl) {
        Intent intent = new Intent(BrandListActivity.this, ProductDetailActivity.class);
        intent.putExtra(Constants.PreferenceConstants.PRODUCTID, prodId);
        intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME,prodName);
        intent.putExtra(Constants.PreferenceConstants.PRODNETPRICE, prodPrice);
        intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, imgUrl);
        startActivity(intent);
    }
}
