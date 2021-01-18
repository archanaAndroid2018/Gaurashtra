package com.gaurashtra.app.view.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.SearchProductResult;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.ProductDetailActivity;
import com.gaurashtra.app.view.adapter.SearchProductAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements SearchProductAdapter.SearchProductInterface {

    public static final String TAG=SearchFragment.class.getSimpleName();

    RecyclerView recyclerView;
    EditText etSearchBox;
    TextView tvNoDataFound;
    LinearLayout btnClearText;
    GlobalUtils globalUtils;
    String searchText, backFrom;
    RelativeLayout mainContainer;
    List<SearchProductResult.Result.ProductDatum> searchProductList;
    SearchProductAdapter adapter;
    TextView textviewTitle;
    LinearLayout progressBar;
    Boolean isKeyboardShow=false;
    View view;
    public SearchFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        backFrom = getArguments().getString(Constants.PreferenceConstants.BACKFROM);
        bindView(view);
        return view;
    }

    public void bindView(View view){

        mainContainer= view.findViewById(R.id.rl_main_layout);
        recyclerView= view.findViewById(R.id.rv_search_result_list);
        globalUtils= new GlobalUtils();
        searchProductList= new ArrayList<>();
        etSearchBox= view.findViewById(R.id.et_search_text);
        etSearchBox.requestFocus();
        showInputMethod();
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        assert imm != null && etSearchBox != null;
//        imm.showSoftInput(etSearchBox, InputMethodManager.SHOW_IMPLICIT);

        etSearchBox.requestFocus();
        etSearchBox.addTextChangedListener(filterTextWatcher);
        progressBar= view.findViewById(R.id.progressBar_layout);
        btnClearText= view.findViewById(R.id.ll_clear_data);
        tvNoDataFound= view.findViewById(R.id.tv_no_data_found);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        etSearchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    searchText= etSearchBox.getText().toString().trim();
                    if(GlobalUtils.isNetworkAvailable(getActivity())) {
                        if (!searchText.isEmpty()) {
                            searchProduct();
                        } else {
                            Toast.makeText(getActivity(), "Please search with product or brand name!", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getActivity(), "Please check your internet connectivity!", Toast.LENGTH_SHORT).show();
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

        mainContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideShowKeyboard(v);
                return false;
            }
        });
    }

    private void hideShowKeyboard(final View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        if(!isKeyboardShow){
            isKeyboardShow=true;

            etSearchBox.requestFocus();
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }else{
            isKeyboardShow=false;
//            if(!etSearchBox.hasFocus()) {
//                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow((IBinder) view, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
//        }
    }

    private void searchProduct() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        GlobalUtils.showdialog(getActivity());
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        HashMap<String, String> params= new HashMap<>();
        params.put("searchtext",searchText);
        Call<SearchProductResult> call= restInterface.searchProductList(globalUtils.getKey(), globalUtils.getapidate(), params);
        call.enqueue(new Callback<SearchProductResult>() {
            @Override
            public void onResponse(Call<SearchProductResult> call, Response<SearchProductResult> response) {
                Log.e("SearchResponseF",""+response);
                GlobalUtils.hidedialog();
                isKeyboardShow=true;
//                hideShowKeyboard();
                if(response.body().getSuccess()==1) {
                    tvNoDataFound.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    searchProductList = response.body().getResult().getProductData();
                    adapter = new SearchProductAdapter(getActivity(), searchProductList, SearchFragment.this);
                    recyclerView.setAdapter(adapter);
                }else{
                    tvNoDataFound.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<SearchProductResult> call, Throwable t) {
                Log.e("searchProdFailed",""+t);
                GlobalUtils.hidedialog();
                isKeyboardShow=true;
//                hideShowKeyboard();
            }
        });
    }

    public void showInputMethod() {
        isKeyboardShow=true;
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()==3 || s.length()==6 || s.length()==9 || s.length()==12) {
//             searchProduct();
            }else{
//             progressBar.setVisibility(View.GONE);
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

}
    };


    @Override
    public void onClickProduct(String prodId, String prodName, String prodImage) {
        if(backFrom.equalsIgnoreCase("PROD")) {
            getActivity().finish();
        }
        Intent intent= new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(Constants.PreferenceConstants.PRODUCTID,prodId);
        intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, prodName);
        intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, prodName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        getFragmentManager().popBackStack();
    }
}
