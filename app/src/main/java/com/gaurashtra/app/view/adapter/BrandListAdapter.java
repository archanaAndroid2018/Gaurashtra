package com.gaurashtra.app.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.AddCartBean.AddCartResponseDTO;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

import com.bumptech.glide.Glide;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.LoginActivity;
import com.gaurashtra.app.view.activity.OptionsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.categoryDetailListbean.ProductDatum;
import com.gaurashtra.app.model.bean.categoryDetailListbean.SubCatBannerDatum;
import com.gaurashtra.app.view.activity.BrandListActivity;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

import org.json.JSONObject;

public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.ListViewHolder> {
    private Context context;
    private GlobalUtils globalUtils;
    private List<ProductDatum> productList = new ArrayList<>();
    private List<SubCatBannerDatum> bannerList = new ArrayList<>();
    private String string=null;
    private String bannerPos=" ",optionId="",optionValueId="";
    PrefClass prefClass;
    String currency="INR", currencySymbol="\u20B9";
    float currencyValue= (float) 1.000;
    Float actualCurrValue= (float) 1.000;
    String selectedQty="1";
    ArrayList<TextView> buttonList= new ArrayList<>();
    LinkedHashMap<Integer, String> priceList= new LinkedHashMap<>();
    LinkedHashMap<Integer, String> bannerLinkIdMap = new LinkedHashMap<>();
    HashMap<Integer, String> qtyMap= new HashMap<>();

    ClickListener clickListener;

    public BrandListAdapter(BrandListActivity brandListActivity, ArrayList<ProductDatum> productList, String name, List<SubCatBannerDatum> bannerList, String bannerPos, BrandListActivity listActivity) {
        this.productList = productList;
        this.context=brandListActivity;
        this.string=name;
        this.bannerList=bannerList;
        this.bannerPos= bannerPos;
        this.clickListener= listActivity;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comonproductitem,parent,false);
        ListViewHolder viewHolder = new ListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, final int position) {
        holder.cartAddButton.setId(position);
        prefClass = new PrefClass(context);
        globalUtils = new GlobalUtils();
        qtyMap.put(position,"1");
        if (!SharedPreference.getUserid(context).isEmpty()) {
            Type type = new TypeToken<List<GetCurrencyList.Result.CurrencyData>>() {
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
        double tax = 0.00;
        final ProductDatum datum = productList.get(position);
        holder.productName.setText(datum.getProductName());
        if(datum.getProductName().contains("&amp;")) {
            holder.productName.setText(datum.getProductName().replace("&amp;", "&"));
        }
        DecimalFormat df = new DecimalFormat("0.00");
        if (datum.getTaxRate() != null) {
            if (!datum.getTaxRate().isEmpty() && !datum.getTaxRate().contains("null")) {
                tax = Double.parseDouble(datum.getTaxRate());
            }
        }
        if (Integer.parseInt(datum.getOptionId()) > 0) {
            holder.tvOption.setVisibility(View.VISIBLE);
        } else {
            holder.tvOption.setVisibility(View.GONE);
        }
        if (Float.parseFloat(datum.getSpecialPrice()) == 0.0000) {
            holder.oldAmountLayout.setVisibility(View.INVISIBLE);
            holder.discountLayout.setVisibility(View.INVISIBLE);
            if (datum.getProductPrice() != null) {
                double prodPrice = Double.parseDouble(datum.getProductPrice());
                double taxAmount = tax * (prodPrice / 100);
                holder.productNewAmount.setText(currencySymbol+ df.format((prodPrice + taxAmount) * currencyValue));
            } else {
                holder.productNewAmount.setText(currencySymbol + "0.00");
            }
        } else {
            holder.oldAmountLayout.setVisibility(View.VISIBLE);
            holder.discountLayout.setVisibility(View.VISIBLE);
            if (datum.getProductPrice() != null) {
                Double price = Double.parseDouble(datum.getProductPrice());
                Double newPrice = Double.parseDouble(datum.getSpecialPrice());
                double oldTaxAmount = tax * (price / 100);
                double taxAmount = tax * (newPrice / 100);
                String offer = new DecimalFormat("0").format(((price - newPrice) * 100 / price) * actualCurrValue);
                if (Float.parseFloat(offer) == 0) {
//                    holder.discountPercent.setText(offer + "% OFF");
                    holder.discountPercent.setVisibility(View.GONE);
                } else {
                    double offerAmount = (price - newPrice) * actualCurrValue;
                    if (offerAmount > 0.00) {
                        holder.discountPercent.setVisibility(View.VISIBLE);
                        holder.discountPercent.setText(currencySymbol+ new DecimalFormat("0.00").format(offerAmount) + " OFF");
                    }
                }
                holder.productOldAmount.setText(currencySymbol+ df.format((price + oldTaxAmount) * currencyValue));
                holder.productNewAmount.setText(currencySymbol+ df.format((newPrice + taxAmount) * currencyValue));
            } else {
                holder.discountPercent.setVisibility(View.GONE);
//                holder.discountPercent.setText("0% OFF");
                holder.productOldAmount.setText(currencySymbol + "0.00");
                holder.productNewAmount.setText(currencySymbol + "0.00");
            }
        }
        if (datum.getProductImage() != null) {
            if (datum.getProductImage().length() != 0) {
                Glide.with(context)
                        .load(globalUtils.IMAGE_BASE_URL + datum.getProductImage().replace(".jpg", "-300x400.jpg"))
                        .placeholder(R.drawable.img_icon)
                        .into(holder.productImage);
//                Log.e("BannerListSize", ":" + bannerList.size() + "\n image url:  " + globalUtils.IMAGE_BASE_URL + datum.getProductImage().replace(".jpg", "-300x400.jpg"));
            }
        }
        if (!bannerPos.isEmpty()) {
            int bannerIdx = Integer.parseInt(bannerPos);
            if (bannerIdx == position) {
                String imgUrl="";
                if(bannerLinkIdMap.size()>0){
                    imgUrl= bannerList.get(bannerLinkIdMap.size()).getBannerImage();
                    bannerLinkIdMap.put(bannerIdx,bannerList.get(bannerLinkIdMap.size()).getLinkId());
                }else{
                    imgUrl= bannerList.get(0).getBannerImage();
                    bannerLinkIdMap.put(bannerIdx,bannerList.get(0).getLinkId());
                }
                holder.bannerLayout.setVisibility(View.VISIBLE);
                Picasso.with(context)
                        .load(imgUrl)
                        .into(holder.bannerImage);
            } else {
                holder.bannerLayout.setVisibility(View.GONE);
            }
         }
        priceList.put(position, holder.productNewAmount.getText().toString());


        Log.e("prodIdBrandList",":"+datum.getProductQuantity());
        if (datum.getOptionId().equalsIgnoreCase("0")){
            if(datum.getProductQuantity().equalsIgnoreCase("0")){
                holder.cartAddButton.setBackgroundResource(R.drawable.reviewbox);
                holder.tvAddCart.setTextColor(Color.GRAY);
                holder.tvAddCart.setText("Notify Me");
            }else {
                holder.cartAddButton.setBackgroundResource(R.drawable.custom_button);
                holder.tvAddCart.setTextColor(Color.WHITE);
                holder.tvAddCart.setText("Add to Cart");
            }
        }else {
            if (datum.getProductInStock().equalsIgnoreCase("0")) {
                holder.cartAddButton.setBackgroundResource(R.drawable.reviewbox);
                holder.tvAddCart.setTextColor(Color.GRAY);
                holder.tvAddCart.setText("Notify Me");
            }else{
                holder.cartAddButton.setBackgroundResource(R.drawable.custom_button);  // redirect to optionPage
                holder.tvAddCart.setTextColor(Color.WHITE);
                holder.tvAddCart.setText("Add to Cart");
            }
        }
        holder.tvAddCart.setId(position);
        buttonList.add(holder.tvAddCart);
        holder.cartAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!SharedPreference.getUserid(context).isEmpty()) {
                    Log.e("Clicked",":LoggedIn");
                    int totalQty= Integer.parseInt(productList.get(position).getProductQuantity());
                    if(GlobalUtils.isNetworkAvailable((Activity)context)) {
                        if (totalQty == 0 && productList.get(position).getOptionId().equalsIgnoreCase("0")) {
                            displayNotifyMeDialog(productList.get(position).getProductId(),productList.get(position).getOptionId(), productList.get(position).getOptionValueId());
                        } else if (totalQty >= 0  && !productList.get(position).getOptionId().equalsIgnoreCase("0")) {
                            Intent intOption2= new Intent(context, OptionsActivity.class);
                            intOption2.putExtra(Constants.PreferenceConstants.PRODUCTID,productList.get(position).getProductId());
                            intOption2.putExtra(Constants.PreferenceConstants.TITLE, productList.get(position).getProductName());
                            context.startActivity(intOption2);
                        } else if (totalQty > 0 && productList.get(position).getOptionId().equalsIgnoreCase("0")) {
                            optionId = productList.get(position).getOptionId();
                            optionValueId = productList.get(position).getOptionValueId();
                            callAddtoCartApi(productList.get(position).getProductId(), qtyMap.get(position));
                        }
                    }else{
                    }
                }else{
                    Log.e("Clicked",": LoggedOut");
                    Intent intLogin= new Intent(context, LoginActivity.class);
                    intLogin.putExtra(Constants.PreferenceConstants.BACKFROM,HomeActivity.class.getSimpleName());
                    ((Activity)context).startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout item,cartAddButton, oldAmountLayout,discountLayout;
        private ImageView productImage, bannerImage;
        AutoCompleteTextView actvQuantity;
        private TextView productName,productOldAmount, productNewAmount, discountPercent, tvQuantity, tvOption, tvAddCart;
        FrameLayout bannerLayout;
        ArrayList<String> qtyList= new ArrayList<>();
        public ListViewHolder(View itemView){
            super(itemView);
            item = itemView.findViewById(R.id.llproduct);
            productImage = itemView.findViewById(R.id.ivProductImage);
            productName = itemView.findViewById(R.id.tvProductName);
            productOldAmount = itemView.findViewById(R.id.tvoldAmount);
            cartAddButton = itemView.findViewById(R.id.lladdtocart);
            bannerLayout = itemView.findViewById(R.id.banner_frame);
            bannerImage = itemView.findViewById(R.id.iv_banner_img);
            productNewAmount = itemView.findViewById(R.id.Product_price);
            oldAmountLayout = itemView.findViewById(R.id.llBehindLine);
            discountLayout = itemView.findViewById(R.id.lldiscount);
            actvQuantity = itemView.findViewById(R.id.actv_quantity);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvOption = itemView.findViewById(R.id.tv_option_avail);
            discountPercent = itemView.findViewById(R.id.tvdis);
            tvAddCart = itemView.findViewById(R.id.tv_addToCart);
            item.setOnClickListener(this);
            bannerLayout.setOnClickListener(this);

            cartAddButton.setOnClickListener(this);
            tvOption.setOnClickListener(this);
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
//                    actvQuantity.showDropDown();
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
                                qtyMap.put(getAdapterPosition(),selectedQty);
                                int limit= Integer.parseInt(productList.get(getAdapterPosition()).getProductQuantity());
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
                        int qty = i+1;
                        int limit = Integer.parseInt(productList.get(getAdapterPosition()).getProductQuantity());
                        Log.e("qty & limit",":"+qty+" and "+limit);
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
                            selectedQty = actvQuantity.getText().toString();
                            tvQuantity.setText(selectedQty + "");
                            qtyMap.put(getAdapterPosition(),selectedQty);
                        }
                    }
                }
            });

            actvQuantity.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    try {
                        if (!productList.get(getAdapterPosition()).getProductQuantity().isEmpty()) {
                            int limit = Integer.parseInt(productList.get(getAdapterPosition()).getProductQuantity());
                            if (limit <= 10) {
                                qtyList = new ArrayList<>();
                                for (int a = 0; a < limit; a++) {
                                    qtyList.add(String.valueOf(a + 1));
                                }
                                final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, qtyList);
                                actvQuantity.setAdapter(dataAdapter);
                            }
                        }
                        actvQuantity.setAdapter(dataAdapter);
                        if (!actvQuantity.isPopupShowing()) {
                            actvQuantity.showDropDown();
//                    }else{
//                        actvQuantity.dismissDropDown();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return true;
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.banner_frame:
                    String linkId= bannerLinkIdMap.get(getAdapterPosition());
                    int pos;
                    for(int i=0; i<productList.size(); i++){
                        String prodId= productList.get(i).getProductId();
                        if(prodId.equalsIgnoreCase(linkId)){

                        }
                    }
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, linkId);
                    context.startActivity(intent);
                    break;
                case R.id.tv_option_avail:
                    Intent intOption= new Intent(context, OptionsActivity.class);
                    intOption.putExtra(Constants.PreferenceConstants.PRODUCTID,productList.get(getAdapterPosition()).getProductId());
                    intOption.putExtra(Constants.PreferenceConstants.TITLE, productList.get(getAdapterPosition()).getProductName());
                    context.startActivity(intOption);
                    break;
                case R.id.lladdtocart:
                    Log.e("Clicked",":2");
                    if(!SharedPreference.getUserid(context).isEmpty()) {
                        Log.e("Clicked",":LoggedIn");
                        String optId= productList.get(getAdapterPosition()).getOptionId();
                        String optValue= productList.get(getAdapterPosition()).getOptionValueId();
                        if(GlobalUtils.isNetworkAvailable((Activity)context)) {
                            String btnText= buttonList.get(getAdapterPosition()).getText().toString();
                            if(btnText.equalsIgnoreCase("Notify Me") && optId.equalsIgnoreCase("0")){
                                displayNotifyMeDialog(productList.get(getAdapterPosition()).getProductId(), optId, optValue);
                            }else if((btnText.equalsIgnoreCase("Notify Me") || btnText.equalsIgnoreCase("Add to cart")) && !optId.equalsIgnoreCase("0")){
                                Intent intent2= new Intent(context, OptionsActivity.class);
                                intent2.putExtra(Constants.PreferenceConstants.PRODUCTID,productList.get(getAdapterPosition()).getProductId());
                                intent2.putExtra(Constants.PreferenceConstants.TITLE, productList.get(getAdapterPosition()).getProductName());
                                context.startActivity(intent2);
                            }else if(btnText.equalsIgnoreCase("Add to cart") && optId.equalsIgnoreCase("0")) {
                                callAddtoCartApi(productList.get(getAdapterPosition()).getProductId(), selectedQty);
                            }
                        }else{
                            Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Log.e("Clicked",": LoggedOut");
                        Intent intLogin= new Intent(context, LoginActivity.class);
                        intLogin.putExtra(Constants.PreferenceConstants.BACKFROM,HomeActivity.class.getSimpleName());
                        ((Activity)context).startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                    }
                    break;
                case R.id.llproduct:

                    String imageUrl = globalUtils.IMAGE_BASE_URL + productList.get(getAdapterPosition()).getProductImage().replace(".jpg", "-300x400.jpg");
//                    Intent intent = new Intent(context, ProductDetailActivity.class);
//                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, productList.get(getAdapterPosition()).getProductId());
//                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, productList.get(getAdapterPosition()).getProductName());
//                    intent.putExtra(Constants.PreferenceConstants.PRODNETPRICE, priceList.get(getAdapterPosition()));
//                    intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, imageUrl);
//                    context.startActivity(intent);
                    clickListener.ListClickListener(productList.get(getAdapterPosition()).getProductId(),productList.get(getAdapterPosition()).getProductName(),priceList.get(getAdapterPosition()),imageUrl);
                    break;
            }
        }
    }


    private void callAddtoCartApi(String id, String selectedQty) {
        GlobalUtils.showdialog(context);
        HashMap<String, String> params = new HashMap<>();
        params.put("userid", SharedPreference.getUserid(context));
        params.put("productid", id);
        params.put("quantity", selectedQty);
        params.put("actiontype", "add");
        params.put("currencyCode", currency);
        params.put("currencyValue", String.valueOf(currencyValue));
        params.put("optionid",optionId);
        params.put("optionvalueid",optionValueId);
        RestInterface restInterface= RetrofitSinglton.getClient().create(RestInterface.class);
        Call<AddCartResponseDTO> call= restInterface.callAddToCartService(globalUtils.getKey(), globalUtils.getapidate(),params);
        call.enqueue(new Callback<AddCartResponseDTO>() {
            @Override
            public void onResponse(Call<AddCartResponseDTO> call, retrofit2.Response<AddCartResponseDTO> response) {
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
                Log.e("ExcAddCartWish",""+t);
            }
        });
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
                            callNotifySend(productId,strName, strEmail, strRemark,optId, optValue, notifyDialog);
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

    public interface ClickListener{
        void ListClickListener(String prodId, String prodName, String prodPrice, String imgUrl);
    }
}
