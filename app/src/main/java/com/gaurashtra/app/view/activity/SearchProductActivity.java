package com.gaurashtra.app.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.SearchProductResult;
import com.gaurashtra.app.view.adapter.SearchProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText etSearchBox;
    TextView tvNoDataFound;
    LinearLayout btnClearText;
    GlobalUtils globalUtils;
    String searchText;
    RelativeLayout mainContainer;
    List<SearchProductResult.Result.ProductDatum> searchProductList;
    SearchProductAdapter adapter;
    TextView textviewTitle;
    LinearLayout progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Search");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);
        abar.setHomeButtonEnabled(true);

        UserUpdate userUpdate= new UserUpdate(SearchProductActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(SearchProductActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(SearchProductActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }

        recyclerView= findViewById(R.id.rv_search_result_list);
        globalUtils= new GlobalUtils();
        searchProductList= new ArrayList<>();
        etSearchBox= findViewById(R.id.et_search_text);
//        etSearchBox.addTextChangedListener(filterTextWatcher);
        progressBar= findViewById(R.id.progressBar_layout);
        btnClearText= findViewById(R.id.ll_clear_data);
        tvNoDataFound= findViewById(R.id.tv_no_data_found);
        LinearLayoutManager layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        etSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                searchText= etSearchBox.getText().toString().trim();
                if(GlobalUtils.isNetworkAvailable(SearchProductActivity.this)) {
                    if (!searchText.isEmpty()) {
                        searchProduct();
                    } else {
                        Toast.makeText(SearchProductActivity.this, "Please search with product or brand name!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(SearchProductActivity.this, "Please check your internet connectivity!", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
                // Return true if you have consumed the action, else false.
                return false;
            }
        });

        btnClearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                etSearchBox.setText("");
                recyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    private void searchProduct() {
        GlobalUtils.showdialog(SearchProductActivity.this);
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        HashMap<String, String> params= new HashMap<>();
        params.put("searchtext",searchText);
        Call<SearchProductResult> call= restInterface.searchProductList(globalUtils.getKey(), globalUtils.getapidate(), params);
        call.enqueue(new Callback<SearchProductResult>() {
            @Override
            public void onResponse(Call<SearchProductResult> call, Response<SearchProductResult> response) {
                Log.e("SearchResponse",""+response);
                GlobalUtils.hidedialog();
                if(response.body().getSuccess()==1) {
                    tvNoDataFound.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    searchProductList = response.body().getResult().getProductData();
//                    adapter = new SearchProductAdapter(SearchProductActivity.this, searchProductList);
//                    recyclerView.setAdapter(adapter);
                }else{
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SearchProductResult> call, Throwable t) {
                Log.e("searchProdFailed",""+t);
            }
        });
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
                hideKeyboard(SearchProductActivity.this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
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
}
