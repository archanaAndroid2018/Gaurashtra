package com.gaurashtra.app.view.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.GetCurrencyList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.model.bean.OptionListBean;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.LoginActivity;
import com.gaurashtra.app.view.activity.OptionsActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.HomePagebean.TopSellingDatum;
import com.gaurashtra.app.view.activity.ProductDetailActivity;
import com.gaurashtra.app.view.activity.ProductListActivity;

import org.json.JSONObject;

public class ViewMoreAdapter extends RecyclerView.Adapter<ViewMoreAdapter.MyViewHolder> {
    private GlobalUtils globalUtils;
    private List<TopSellingDatum> topselling=new ArrayList<>();
    private ArrayList<OptionListBean.Result.ProductData.Option> productOptionList;
    private Context context;
    AddToCartInterface addToCartInterface;
    String imagebaseurl= GlobalUtils.IMAGE_BASE_URL;
    int Type=0;
    PrefClass prefClass;
    String currency="", currencySymbol="\u20B9";
    float currencyValue= (float) 1.000;
    Float actualCurrValue;
    String selectedQty="1";
    int totalQty=1;
    LinkedHashMap<Integer, String> priceList= new LinkedHashMap<>();
    HashMap<Integer, String> qtyMap= new HashMap<>();

    public ViewMoreAdapter(ProductListActivity productListActivity, List<TopSellingDatum> catList, ProductListActivity listActivity) {
        this.context=productListActivity;
        this.topselling=catList;
        this.addToCartInterface= listActivity;
        globalUtils= new GlobalUtils();
    }

    @Override
    public ViewMoreAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comonproductitem,viewGroup,false);
        MyViewHolder vh=new MyViewHolder(view);
        return vh;
    }

//    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewMoreAdapter.MyViewHolder myViewHolder, int i) {
        prefClass= new PrefClass(context);
        myViewHolder.tvAddtoCart.setId(i);
        qtyMap.put(i, "1");
        try {
            if (!SharedPreference.getUserid(context).isEmpty()) {
                java.lang.reflect.Type type = new TypeToken<List<GetCurrencyList.Result.CurrencyData>>() {
                }.getType();
                List<GetCurrencyList.Result.CurrencyData> currencyDataList = (List<GetCurrencyList.Result.CurrencyData>) (new Gson()).fromJson(prefClass.getCurrencyDataList(), type);
                for (GetCurrencyList.Result.CurrencyData currencyData : currencyDataList) {
                    if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())) {
                        currency = prefClass.getSelectedCurrency();
                        actualCurrValue = Float.parseFloat(currencyData.getValue());
                        String value = new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                        currencyValue = Float.parseFloat(value);
                        Log.e("currencyValue", ":" + currencyValue);
                        currencySymbol = currencyData.getSymbol().trim();
                    }
                }
            }
            String imagebaseurl = GlobalUtils.IMAGE_BASE_URL;
            ImageView view = myViewHolder.imageView;
            String imageURL = topselling.get(i).getProductImage();
            if (imageURL != null) {
                if (imageURL.contains(".jpg")) {
                    String addimage = imageURL.replace(".jpg", "-300x400.jpg");
                    String image = imagebaseurl + addimage;
                    Picasso.with(context).load(image).into(view);
                }
            }
//
            String productName=topselling.get(i).getProductName();
            myViewHolder.textproduct.setText(productName);
            if(productName.contains("&amp;")){
                productName =topselling.get(i).getProductName().replaceAll("&amp;","&");
                myViewHolder.textproduct.setText(productName);
            }
            String sprice = topselling.get(i).getSpecialPrice() + "";
            String pprice = topselling.get(i).getProductPrice() + "";
            double dprice = 0.00, dsprice = 0.00;
            if (!pprice.isEmpty() && !pprice.contains("null")) {
                dprice = Double.parseDouble(pprice);
            }
            if (!sprice.isEmpty() && !sprice.contains("null")) {
                dsprice = Double.parseDouble(sprice);
            }
            double taxPercent = 0.00;
            try {
                if (!topselling.get(i).getTaxRate().isEmpty()) {
                    taxPercent = Double.parseDouble(topselling.get(i).getTaxRate());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dsprice == 0.00) {
                double taxAmount = dprice * (taxPercent / 100);
                myViewHolder.productprice.setText(currencySymbol+ (new DecimalFormat("##.##").format((dprice + taxAmount) * currencyValue)));
                myViewHolder.behind.setVisibility(View.INVISIBLE);
                myViewHolder.discountPercentage.setVisibility(View.INVISIBLE);
            } else if (dsprice > 0.00) {
                double taxAmount = dprice * (taxPercent / 100);
                double new_taxAmount = dsprice * (taxPercent / 100);
                double forice = dprice - dsprice;
                double fprice = (forice / dprice) * 100;
                myViewHolder.prod_discount.setText(new DecimalFormat("##.##").format(dprice + taxAmount));
                myViewHolder.productprice.setText(currencySymbol+ new DecimalFormat("##.##").format((dsprice + new_taxAmount) * currencyValue));
                myViewHolder.disPer.setText(new DecimalFormat("##.##").format(fprice * currencyValue) + "% OFF");
            }
            if(Integer.parseInt(topselling.get(i).getOptionId())>0){
                myViewHolder.tvOptionAvailable.setVisibility(View.VISIBLE);
            }else{
                myViewHolder.tvOptionAvailable.setVisibility(View.GONE);
            }
            if (topselling.get(i).getProductQuantity() != null) {
                totalQty = Integer.parseInt(topselling.get(i).getProductQuantity());
                   if(topselling.get(i).getOptionId().equalsIgnoreCase("0")){
                       if(totalQty==0){
                           myViewHolder.cartAddButton.setBackgroundResource(R.drawable.reviewbox);
                           myViewHolder.tvAddtoCart.setTextColor(Color.GRAY);
                           myViewHolder.tvAddtoCart.setText("Notify Me");
                       }else {
                           myViewHolder.cartAddButton.setBackgroundResource(R.drawable.custom_button);
                           myViewHolder.tvAddtoCart.setTextColor(Color.WHITE);
                           myViewHolder.tvAddtoCart.setText("Add to cart");
                       }
                   }else{
                       if(topselling.get(i).getProductInStock().equalsIgnoreCase("0")) {
                           myViewHolder.cartAddButton.setBackgroundResource(R.drawable.reviewbox);
                           myViewHolder.tvAddtoCart.setTextColor(Color.GRAY);
                           myViewHolder.tvAddtoCart.setText("Notify Me");
                       }else{
                           myViewHolder.cartAddButton.setBackgroundResource(R.drawable.custom_button);
                           myViewHolder.tvAddtoCart.setTextColor(Color.WHITE);
                           myViewHolder.tvAddtoCart.setText("Add to cart");
                       }
                }
            }
            priceList.put(i,myViewHolder.productprice.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        }

    @Override
    public int getItemCount() {
        return topselling.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        FrameLayout frameLayout;
        Spinner spList;
        ImageView ivStar1, ivStar2, ivStar3, ivStar4, ivStar5, imgShine;
        TextView textproduct,productprice,prod_discount,disPer, tvQuantity, tvReview, tvAddtoCart, tvOptionAvailable;
        private LinearLayout cartAddButton,discountPercentage,behind,bottomlayout, mainLayout;

        private AutoCompleteTextView actvQuantity;
        public MyViewHolder( View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.fldata);
            imageView = (ImageView) itemView.findViewById(R.id.ivProductImage);
            textproduct = (TextView) itemView.findViewById(R.id.tvProductName);
            productprice = (TextView) itemView.findViewById(R.id.Product_price);
            cartAddButton = (LinearLayout) itemView.findViewById(R.id.lladdtocart);
            tvAddtoCart= itemView.findViewById(R.id.tv_addToCart);
            ivStar1= itemView.findViewById(R.id.iv_star1);
            ivStar2= itemView.findViewById(R.id.iv_star2);
            ivStar3= itemView.findViewById(R.id.iv_star3);
            ivStar4= itemView.findViewById(R.id.iv_star4);
            ivStar5= itemView.findViewById(R.id.iv_star5);
            tvReview= itemView.findViewById(R.id.tv_prod_review);
            cartAddButton.setOnClickListener(this);
            prod_discount = (TextView) itemView.findViewById(R.id.tvoldAmount);
            discountPercentage = itemView.findViewById(R.id.lldiscount);
            bottomlayout = itemView.findViewById(R.id.llbottom);
            tvOptionAvailable= itemView.findViewById(R.id.tv_option_avail);
            disPer = itemView.findViewById(R.id.tvdis);
            behind = itemView.findViewById(R.id.llBehindLine);
            mainLayout= itemView.findViewById(R.id.llproduct);
            actvQuantity = itemView.findViewById(R.id.actv_quantity);
            imgShine= itemView.findViewById(R.id.shineover_btn_img);

            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            final ArrayList<String> qtykList = new ArrayList<>();
            qtykList.add("1");
            qtykList.add("2");
            qtykList.add("3");
            qtykList.add("4");
            qtykList.add("5");
            qtykList.add("6");
            qtykList.add("7");
            qtykList.add("8");
            qtykList.add("9");
            qtykList.add("more");
            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, qtykList);
            actvQuantity.setAdapter(dataAdapter);
            tvOptionAvailable.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String imageURL = topselling.get(pos).getProductImage();
                    String addimage = imageURL.replace(".jpg", "-300x300.jpg");
                    String image = imagebaseurl + addimage;
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, topselling.get(pos).getProductId());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, topselling.get(pos).getProductName());
                    if(topselling.get(pos).getProductName().contains("&amp;")){
                        intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, topselling.get(pos).getProductName().replace("&amp;","&"));
                    }
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, image);
                    intent.putExtra(Constants.PreferenceConstants.PRODNETPRICE,priceList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });

            actvQuantity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    actvQuantity.showDropDown();
                    if(i== 9){
                        final Dialog dialog=new Dialog(context);
                        dialog.setContentView(R.layout.enter_quantity_layout);
                        dialog.show();
                        final EditText etQuantity=dialog.findViewById(R.id.et_quantity);
                        TextView tvCancel=dialog.findViewById(R.id.tv_cancel);
                        TextView tvApply=dialog.findViewById(R.id.tv_apply);

                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });

                        tvApply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!etQuantity.getText().toString().isEmpty() && Integer.parseInt(etQuantity.getText().toString())>0){
                                    selectedQty= etQuantity.getText().toString();
                                    qtyMap.put(getAdapterPosition(),selectedQty);
                                    int limit= Integer.parseInt(topselling.get(getAdapterPosition()).getProductQuantity());
                                    if (Integer.parseInt(selectedQty)<=limit) {
                                        tvQuantity.setText(selectedQty);
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(context, "Only "+limit+" left in the stock. ", Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(context, "Quantity should be at least 1", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else{
                        int qty = Integer.parseInt(qtykList.get(i));
                        int limit = Integer.parseInt(topselling.get(getAdapterPosition()).getProductQuantity());
                        if(qty > limit){
                            selectedQty = "1";
                            tvQuantity.setText("1");
                            qtyMap.put(getAdapterPosition(),selectedQty);
                            if(limit==0){
                                Toast.makeText(context, "Product not available!", Toast.LENGTH_SHORT).show();
                            }else if(limit ==1){
                                Toast.makeText(context, "Only 1 item left in the stock!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Only " + limit + " left in the stock. ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            selectedQty = qtykList.get(i);
                            tvQuantity.setText(qtykList.get(i) + "");
                            qtyMap.put(getAdapterPosition(),selectedQty);
                        }
                    }
                }
            });

            actvQuantity.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    actvQuantity.setAdapter(dataAdapter);
                    actvQuantity.showDropDown();
                    return true;
                }
            });
        }
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.tv_option_avail:
                        Intent intOption= new Intent(context, OptionsActivity.class);
                        intOption.putExtra(Constants.PreferenceConstants.PRODUCTID,topselling.get(getAdapterPosition()).getProductId());
                        intOption.putExtra(Constants.PreferenceConstants.TITLE, topselling.get(getAdapterPosition()).getProductName());
                        if(topselling.get(getAdapterPosition()).getProductName().contains("&amp;")){
                        intOption.putExtra(Constants.PreferenceConstants.TITLE, topselling.get(getAdapterPosition()).getProductName().replace("&amp;"," "));
                    }
                        context.startActivity(intOption);
                        break;
                    case R.id.lladdtocart:
                        String optId = topselling.get(getAdapterPosition()).getOptionId();
                        String optValue= topselling.get(getAdapterPosition()).getOptionValueId();

                        totalQty = Integer.parseInt(topselling.get(getAdapterPosition()).getProductQuantity());
                        if(!SharedPreference.getUserid(context).isEmpty()) {
                            if(GlobalUtils.isNetworkAvailable((Activity)context)) {
                                if (totalQty == 0 && topselling.get(getAdapterPosition()).getOptionId().equalsIgnoreCase("0")) {
                                    displayNotifyMeDialog(topselling.get(getAdapterPosition()).getProductId(),optId,optValue);
                                } else if (totalQty >= 0 && !topselling.get(getAdapterPosition()).getOptionId().equalsIgnoreCase("0")) {
                                    Intent intOption2 = new Intent(context, OptionsActivity.class);
                                    intOption2.putExtra(Constants.PreferenceConstants.PRODUCTID, topselling.get(getAdapterPosition()).getProductId());
                                    intOption2.putExtra(Constants.PreferenceConstants.TITLE, topselling.get(getAdapterPosition()).getProductName());
                                    context.startActivity(intOption2);
                                } else if (totalQty > 0 && topselling.get(getAdapterPosition()).getOptionId().equalsIgnoreCase("0")) {
                                   addToCartInterface.addToCart(topselling.get(getAdapterPosition()).getProductId(), qtyMap.get(getAdapterPosition()), "add",optId,optValue);
                                }
                            }else{
                                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Intent intLogin= new Intent(context, LoginActivity.class);
                            intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
                            ((Activity)context).startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                        }
                        break;
                }
            }
        }
        public  interface AddToCartInterface{
            void addToCart(String productId, String selectedItemPosition, String add, String optionId, String optionValue);
        }

    public void displayNotifyMeDialog(final String productId, final String optId, final String optValue){
        final Dialog notifyDialog=new Dialog(context);
        notifyDialog.setContentView(R.layout.notify_me_dialog_layout);
        notifyDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        notifyDialog.show();
        final EditText etName=notifyDialog.findViewById(R.id.et_name);
        final EditText etEmail=notifyDialog.findViewById(R.id.et_email);
        final EditText etRemark=notifyDialog.findViewById(R.id.et_remark);
        Button btnNotify=notifyDialog.findViewById(R.id.btn_notify);
        String name = "";
        if (!SharedPreference.getFirstName(context).isEmpty()) {
            name = SharedPreference.getFirstName(context);
        }
        if (!SharedPreference.getLastName(context).isEmpty()) {
            name = name + " " + SharedPreference.getLastName(context);
        }
        etName.setText(name);
        if (!SharedPreference.getUserEmail(context).isEmpty()){
            etEmail.setText(SharedPreference.getUserEmail(context));
        }else{
            etEmail.setText(SharedPreference.getSocialEmail(context));
        }
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strName=etName.getText().toString();
                String strEmail=etEmail.getText().toString();
                String strRemark=etRemark.getText().toString();
                if (!strName.isEmpty()&&!strEmail.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        if (GlobalUtils.isNetworkAvailable((Activity) context)) {
                            GlobalUtils.showdialog(context);
                            callNotifySend(productId,strName, strEmail, strRemark,optId,optValue, notifyDialog);
                        } else
                            Toast.makeText(context, "Please check your internet connection...", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(context, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    }
                }else if (strName.isEmpty()){
                    Toast.makeText(context, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                }else if (strEmail.isEmpty()){
                    Toast.makeText(context, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void callNotifySend(String productId, String strName, String strEmail, String strRemark,String optId, String optValue, final Dialog notifyDialog) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", strName);
        params.put("productid", productId);
        params.put("email", strEmail);
        params.put("query", strRemark);
        params.put("optionid",optId);
        params.put("optionvalueid",optValue);
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<JSONObject> call= restInterface.setNotifyMe(globalUtils.getKey(), globalUtils.getapidate(),params);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                GlobalUtils.hidedialog();
                Log.e("NotifyResp",":"+response);
                if(response != null){
                    notifyDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                GlobalUtils.hidedialog();
                Log.e("NotifyExc",":"+t);
            }
        });
    }
}
