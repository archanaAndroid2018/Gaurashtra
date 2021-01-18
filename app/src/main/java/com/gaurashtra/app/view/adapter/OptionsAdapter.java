package com.gaurashtra.app.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.OptionListBean;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDTO;
import com.gaurashtra.app.view.activity.CartActivity;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.LoginActivity;
import com.gaurashtra.app.view.activity.OptionsActivity;
import com.gaurashtra.app.view.activity.ProductDetailActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OptionsAdapter  extends RecyclerView.Adapter<OptionsAdapter.OptionViewHolder> {
    Context context;
    ArrayList<OptionListBean.Result.ProductData.Option> optionList;
    RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
    PrefClass prefClass;
    String currency="", currencySymbol="\\u20B9", productPrice;
    double currencyValue=1.000;
    SelectOptionInterface selectOptionInterface;
    Boolean isMainProduct= false;
    final static int horView=1;
    final static int verView=2;
    String prodId, prodName, prodImage,prodTax, spclPrice, prodQty, discountPrice,discountQty, priceTosend;
    GlobalUtils globalUtils= new GlobalUtils();
    OptionListBean.Result.ProductData.Data optionData;
    String selectedQty="1", btnText="";
    HashMap<Integer, String> priceMap= new HashMap<>();
    HashMap<Integer, String> qtyMap= new HashMap<>();

    public OptionsAdapter(OptionsActivity optionsActivity, ArrayList<OptionListBean.Result.ProductData.Option> optionList, int type, OptionListBean.Result.ProductData.Data data, String btnText) {
        this.context= optionsActivity;
        this.optionList= optionList;
        this.optionData= data;
        this.btnText= btnText;
    }


    @Override
    public int getItemViewType(int position) {
        Log.e("Type",": "+position);
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.productlistitem, null, false);
        OptionViewHolder vh= new OptionViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OptionViewHolder holder, final int position) {
        // for VerOptionList
        double curValue=0.00;
        //
        qtyMap.put(position, "1");
        prefClass= new PrefClass(context);
        try {
            if (!prefClass.getCurrencyDataList().isEmpty()) {
                Type type = new TypeToken<List<GetCurrencyList.Result.CurrencyData>>() {
                }.getType();
                List<GetCurrencyList.Result.CurrencyData> currencyDataList = (List<GetCurrencyList.Result.CurrencyData>) (new Gson()).fromJson(prefClass.getCurrencyDataList(), type);
                for (GetCurrencyList.Result.CurrencyData currencyData : currencyDataList) {
                    if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())) {
                        currency = prefClass.getSelectedCurrency();
                        curValue= Double.parseDouble(currencyData.getValue());
                        String value = new DecimalFormat("0.00").format(curValue);
                        currencyValue = Float.parseFloat(value);
                        Log.e("currencyValue", ":" + currencyValue);
                        currencySymbol = currencyData.getSymbol().trim();
                    }
                }
            }
                prodImage=GlobalUtils.IMAGE_BASE_URL+optionData.getProductImage().replace(".jpg", "-300x400.jpg");
                prodId= optionData.getProductId();
                prodName= optionData.getProductName();
                if(prodName.contains("&amp;")){
                    prodName.replace("&amp;"," & ");
                }
                productPrice= optionData.getProductPrice();
                spclPrice= optionData.getSpecialPrice();
                discountPrice=optionData.getDiscountPrice();
                discountQty= optionData.getDiscountQuantity();
                prodQty= optionData.getProductQuantity();
                prodTax= optionData.getTaxRate();
                DecimalFormat df=new DecimalFormat("0.00");

                Log.e("ProdId",": "+prodId);
                Picasso.with(context).load(prodImage).into(holder.productImage);
                holder.cartAddButton.setId(position);
                String optionProdPrice= optionList.get(position).getOptionPrice();
//                String price = new DecimalFormat("0.00").format(Double.parseDouble(optionProdPrice) * currencyValue);
                holder.productName.setText(prodName.replace("&amp;","&"));
                holder.tvOptionTitle.setText(optionData.getOptionType().replace("&amp;","&"));
                holder.tvOptionName.setText(optionList.get(position).getOptionName().replace("&amp;","&"));
                if (optionProdPrice.equalsIgnoreCase("0.0000")) {
//                    priceTosend= new DecimalFormat("0.00").format(Double.parseDouble(productPrice)* curValue);
//                    holder.tvPrice.setText(currencySymbol+priceTosend);
                    double netPrice=0.00;
                    netPrice = Double.parseDouble(productPrice);
                    double taxAmount= netPrice*(Double.parseDouble(prodTax)/100);
                    double amountToShow= (netPrice+taxAmount)* curValue;
                    priceTosend=String.valueOf(amountToShow);
                    holder.tvPrice.setText(currencySymbol+ df.format(amountToShow));
                }else{
                    double netPrice=0.00;
                    if(optionList.get(position).getOptionPricePrefix().equalsIgnoreCase("+")) {
                      netPrice = Double.parseDouble(productPrice) + Double.parseDouble(optionProdPrice);
                    }else if(optionList.get(position).getOptionPricePrefix().equalsIgnoreCase("-")){
                        netPrice =Double.parseDouble(productPrice) - Double.parseDouble(optionProdPrice);
                    }
                    double taxAmount= netPrice*(Double.parseDouble(prodTax)/100);
                    double amountToShow= (netPrice+taxAmount)* curValue;
                    priceTosend=String.valueOf(amountToShow);
                    holder.tvPrice.setText(currencySymbol+ df.format(amountToShow));
                }
                if (optionList.get(position).getOptionQuantity().equalsIgnoreCase("0")) {
                    holder.cartAddButton.setBackgroundResource(R.drawable.reviewbox);
                    holder.tvInStock.setTextColor(Color.GRAY);
                    holder.tvInStock.setText("Notify Me");
                }else {
                    holder.cartAddButton.setBackgroundResource(R.drawable.custom_button);
                    holder.tvInStock.setTextColor(Color.WHITE);
                    if (!btnText.isEmpty()) {
                        holder.tvInStock.setText(btnText);
                    } else {
                        holder.tvInStock.setText("Add to cart");
                    }

                }
            priceMap.put(position, holder.tvPrice.getText().toString());
                holder.cartAddButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!SharedPreference.getUserid(context).isEmpty()) {
                            if (GlobalUtils.isNetworkAvailable((Activity) context)) {
                                if (optionList.get(position).getOptionQuantity().equalsIgnoreCase("0")) {
                                    String optId= optionList.get(position).getOptionId();
                                    String optvalue= optionList.get(position).getOptionValueId();
                                    displayNotifyMeDialog(optId, optvalue);
                                }else{
                                    callAddtoCartApi(prodId, qtyMap.get(position), optionList.get(position).getOptionId(), optionList.get(position).getOptionValueId());
                                }
                            } else {
                                Toast.makeText(context, R.string.no_internet_connectivity, Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Intent intLogin= new Intent(context, LoginActivity.class);
                            intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
                            ((Activity)context).startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                        }
                    }
                });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvSize, tvPrice, tvInStock;// for horView
//             for verView
        private LinearLayout productItem, optionNameLayout;
        public FrameLayout cartAddButton;
        AutoCompleteTextView actvQuantity;
        TextView tvQuantity, tvNetPrice;
        private ImageView productImage, shine;
        private TextView productName,productOldAmount,tvOptions, tvOptionTitle, tvOptionName;
        ArrayList<String> qtyList = new ArrayList<>();

        public OptionViewHolder(@NonNull View itemView) {
            super(itemView);
            verItemView(itemView);
        }

        private void verItemView(View itemView) {
            productItem = itemView.findViewById(R.id.llproduct);
            productImage = itemView.findViewById(R.id.ivProductImage);
            productName  = itemView.findViewById(R.id.tvProductName);
            productOldAmount = itemView.findViewById(R.id.tvoldAmount);
            tvPrice=itemView.findViewById(R.id.tv_net_price);
            productOldAmount.setVisibility(View.GONE);
            cartAddButton  = (FrameLayout) itemView.findViewById(R.id.lladdtocart);
            actvQuantity = itemView.findViewById(R.id.actv_quantity);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            shine= itemView.findViewById(R.id.shineover_btn_img);
            tvOptions= itemView.findViewById(R.id.tv_option_avail);
            tvInStock= itemView.findViewById(R.id.tv_addtocart);
            optionNameLayout= itemView.findViewById(R.id.option_name_layout);
            optionNameLayout.setVisibility(View.VISIBLE);
            tvOptionTitle= itemView.findViewById(R.id.tv_option_title);
            tvOptionName= itemView.findViewById(R.id.tv_option_name);

            tvOptions.setVisibility(View.GONE);
            productItem.setOnClickListener(this);
            cartAddButton.setOnClickListener(this);

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
            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, qtyList);
            actvQuantity.setAdapter(dataAdapter);

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
                                if (!etQuantity.getText().toString().isEmpty()&& Integer.parseInt(etQuantity.getText().toString())>0){
                                    selectedQty= etQuantity.getText().toString();
                                    qtyMap.put(getAdapterPosition(), selectedQty);
                                    int limit= Integer.parseInt(optionList.get(getAdapterPosition()).getOptionQuantity());
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
                        int qty = Integer.parseInt(qtyList.get(i));
                        int limit = Integer.parseInt(optionList.get(getAdapterPosition()).getOptionQuantity());
                        if(qty > limit){
                            selectedQty = "1";
                            tvQuantity.setText("1");
                            qtyMap.put(getAdapterPosition(), selectedQty);
                            if(limit==0){
                                Toast.makeText(context, "Product not available!", Toast.LENGTH_SHORT).show();
                            }else if(limit ==1){
                                Toast.makeText(context, "Only 1 item left in the stock!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Only " + limit + " left in the stock. ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            selectedQty= qtyList.get(i);
                            tvQuantity.setText(qtyList.get(i)+"");
                            qtyMap.put(getAdapterPosition(), selectedQty);
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
            int pos = getAdapterPosition();
            switch(view.getId()){
                case R.id.llproduct:

                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, prodId);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, prodName);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, prodImage);
                    intent.putExtra(Constants.PreferenceConstants.OPTIONID, optionList.get(getAdapterPosition()).getOptionValueId());
                    intent.putExtra(Constants.PreferenceConstants.PRODNETPRICE, priceMap.get(getAdapterPosition()));
                    context.startActivity(intent);
                    break;
                case R.id.lladdtocart:
//                    Toast.makeText(context, "notify me!", Toast.LENGTH_SHORT).show();
//                    if(!SharedPreference.getUserid(context).isEmpty()) {
//                        if (GlobalUtils.isNetworkAvailable((Activity) context)) {
//                            if (optionList.get(getAdapterPosition()).getOptionQuantity().equalsIgnoreCase("0")) {
//                                displayNotifyMeDialog();
//                            }else{
//                                callAddtoCartApi(prodId, selectedQty, optionList.get(pos).getOptionId(), optionList.get(pos).getOptionValueId());
//                            }
//                        } else {
//                            Toast.makeText(context, R.string.no_internet_connectivity, Toast.LENGTH_SHORT).show();
//                        }
//                    }else{
//                        Intent intLogin= new Intent(context, LoginActivity.class);
//                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
//                        ((Activity)context).startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
//                    }
                    break;
                case R.id.tv_option_avail:
                    Intent intOption= new Intent(context, OptionsActivity.class);
                    intOption.putExtra(Constants.PreferenceConstants.PRODUCTID, prodId);
                    intOption.putExtra(Constants.PreferenceConstants.TITLE, prodName);
                    context.startActivity(intOption);
                    break;
            }
        }
    }

    private void callWishListApi(final String prodId, String action, final int pos) {
        restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<WishlistDTO> call = restInterface.addRemoveWishList(globalUtils.getKey(), globalUtils.getapidate(), SharedPreference.getUserid(context), prodId, action);
        call.enqueue(new Callback<WishlistDTO>() {
            @Override
            public void onResponse(Call<WishlistDTO> call, retrofit2.Response<WishlistDTO> response) {
                if (response.body().getSuccess()==1) {
                    Log.e("ResponseMovedWishList", ":" + response.body().getMessage());
                    Toast.makeText(context, "Moved to wishList!", Toast.LENGTH_SHORT).show();
                    removeProduct(prodId,"remove",optionList.get(pos).getOptionId(), optionList.get(pos).getOptionValueId());
                }
            }

            @Override
            public void onFailure(Call<WishlistDTO> call, Throwable t) {
                Log.e("MoveToWLExc",":"+t);
            }
        });
    }

    private void removeProduct(String prodId, String action, String optionId, String optionValueId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", SharedPreference.getUserid(context));
        params.put("productid", prodId);
        params.put("quantity", selectedQty);
        params.put("actiontype", action);
        params.put("optionid",optionId);
        params.put("optionvalueid",optionValueId);
        params.put("currencyCode", currency);
        params.put("currencyValue", String.valueOf(currencyValue));
        restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<AddCartResponseDTO> call = restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(), params);
        call.enqueue(new Callback<AddCartResponseDTO>() {
            @Override
            public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
                GlobalUtils.hidedialog();
                if (response.isSuccessful()) {
                    Log.e("AddWishToCart", ":" + response.body().getMessage());
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("AddWishToCart", ":" + response.message());
                    Toast.makeText(context, "Something went wrong, try again!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddCartResponseDTO> call, Throwable t) {
                GlobalUtils.hidedialog();
                Toast.makeText(context, "Please try again!!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public interface SelectOptionInterface{
        void selectedOption(int selectedIdx, Boolean isMain, String operator);
    }


    private void callAddtoCartApi(String prodId, String selectedQty, String optionId, String optionValueId) {
        GlobalUtils.showdialog(context);
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", SharedPreference.getUserid(context));
        params.put("productid", prodId);
        params.put("quantity", selectedQty);
        params.put("actiontype", "add");
        params.put("currencyCode", currency);
        params.put("currencyValue", String.valueOf(currencyValue));
        params.put("optionid",optionId);
        params.put("optionvalueid",optionValueId);
        Call<AddCartResponseDTO> call= restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(),params);
        call.enqueue(new Callback<AddCartResponseDTO>() {
            @Override
            public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
                Log.e("AddToCartFromOption",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response));
                if(response.isSuccessful()){
                    GlobalUtils.hidedialog();
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    GlobalUtils.hidedialog();
                    Toast.makeText(context, "Something went wrong, please try again!!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<AddCartResponseDTO> call, Throwable t) {
                GlobalUtils.hidedialog();
                Log.e("ExcAddCartOpt",""+t);
            }
        });
    }


    public void displayNotifyMeDialog(final String optId, final String optValue){
        final Dialog notifyDialog=new Dialog(context);
        notifyDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        notifyDialog.setContentView(R.layout.notify_me_dialog_layout);
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
                            callNotifySend(strName, strEmail, strRemark,optId, optValue, notifyDialog);
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
    private void callNotifySend(String strName, String strEmail, String strRemark,String optId, String optValue, final Dialog notifyDialog) {
        HashMap<String, String> params = new HashMap<>();
        params.put("username", strName);
        params.put("productid", prodId);
        params.put("email", strEmail);
        params.put("query", strRemark);
        params.put("optionid",optId);
        params.put("optionvalueid",optValue);
        Call<JSONObject> call= restInterface.setNotifyMe(globalUtils.getKey(), globalUtils.getapidate(),params);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                GlobalUtils.hidedialog();
                Log.e("NotifyResp",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response));
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
