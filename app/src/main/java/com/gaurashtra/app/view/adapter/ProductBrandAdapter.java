package com.gaurashtra.app.view.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.LoginActivity;
import com.gaurashtra.app.view.activity.OptionsActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.HomePagebean.BrandProductData;
import com.gaurashtra.app.view.activity.ProductByBrand;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

import org.json.JSONObject;

public class ProductBrandAdapter extends RecyclerView.Adapter<ProductBrandAdapter.MyViewHolder> {
    private GlobalUtils globalUtils;
    private List<BrandProductData.Result.BrandProductDatum> productData = new ArrayList<>();
    private Context context;
    AddToCartInterface addToCartInterface;
    String imagebaseurl = GlobalUtils.IMAGE_BASE_URL;
    private String bannerPos = " ", optionId = "", optionValueId = "";
    String selectedQty = "1";
    int totalQty = 1;
    ArrayList<TextView> buttonsList = new ArrayList<>();

    public ProductBrandAdapter(ProductByBrand productByBrand, List<BrandProductData.Result.BrandProductDatum> productList, ProductByBrand byBrand) {
        this.context = productByBrand;
        this.productData = productList;
        this.addToCartInterface = byBrand;
    }

    @Override
    public ProductBrandAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comonproductitem, viewGroup, false);
        MyViewHolder vh = new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ProductBrandAdapter.MyViewHolder myViewHolder, final int i) {
        ImageView view = myViewHolder.imageView;
        String imageURL = productData.get(i).getProductImage();
        totalQty = Integer.parseInt(productData.get(i).getProductQuantity());
        String prodInStock = productData.get(i).getProductInStock();

        if (Integer.parseInt(productData.get(i).getOptionId()) > 0) {
            myViewHolder.tvOptionAvail.setVisibility(View.VISIBLE);
        } else {
            myViewHolder.tvOptionAvail.setVisibility(View.GONE);
        }
        if (productData.get(i).getOptionId().equalsIgnoreCase("0")) {
            if (productData.get(i).getProductQuantity().equalsIgnoreCase("0")) {
                myViewHolder.cartAddButton.setBackgroundResource(R.drawable.reviewbox);
                myViewHolder.btnText.setTextColor(Color.GRAY);
                myViewHolder.btnText.setText("Notify Me");
            } else {
                myViewHolder.cartAddButton.setBackgroundResource(R.drawable.custom_button);
                myViewHolder.btnText.setTextColor(Color.WHITE);
                myViewHolder.btnText.setText("Add to cart");
            }
        } else {
            if (prodInStock.equalsIgnoreCase("1")) {
                myViewHolder.cartAddButton.setBackgroundResource(R.drawable.custom_button);
                myViewHolder.btnText.setTextColor(Color.WHITE);
                myViewHolder.btnText.setText("Add to cart");
            } else {
                myViewHolder.cartAddButton.setBackgroundResource(R.drawable.reviewbox);
                myViewHolder.btnText.setTextColor(Color.GRAY);
                myViewHolder.btnText.setText("Notify Me");
            }
        }
        Log.e("SellingURl", "" + imageURL);
        String addimage = imageURL.replace(".jpg", "-300x400.jpg");
        String image = imagebaseurl + addimage;
        Picasso.with(context).load(image).into(view);
        myViewHolder.textproduct.setText(productData.get(i).getProductName());
        String sprice = productData.get(i).getSpecialPrice();
        String pprice = productData.get(i).getProductPrice();
        Double dprice = Double.parseDouble(pprice);
        Double dsprice = Double.parseDouble(sprice);
        double taxPercent = 0.00;
        try {
            if (!productData.get(i).getTaxRate().isEmpty()) {
                taxPercent = Double.parseDouble(productData.get(i).getTaxRate());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dsprice == 0.00) {
            double taxAmount = dprice * (taxPercent / 100);
            myViewHolder.productprice.setText("\u20B9" + (new DecimalFormat("##.##").format(dprice + taxAmount)));
            myViewHolder.behind.setVisibility(View.GONE);
            myViewHolder.discountPercentage.setVisibility(View.GONE);
            try {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) myViewHolder.bottomlayout.getLayoutParams();
                params.setMargins(0, 30, 0, 0);
                myViewHolder.bottomlayout.setLayoutParams(params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (dsprice > 0.00) {
            double taxAmount = dprice * (taxPercent / 100);
            double new_taxAmount = dsprice * (taxPercent / 100);
            double forice = dprice - dsprice;
            double fprice = (forice / dprice) * 100;
            myViewHolder.prod_discount.setText(new DecimalFormat("##.##").format(dprice + taxAmount));
            myViewHolder.productprice.setText("\u20B9" + new DecimalFormat("##.##").format(dsprice + new_taxAmount));
            myViewHolder.disPer.setText(new DecimalFormat("##.##").format(fprice) + "% OFF");
        }
        myViewHolder.btnText.setId(i);
        buttonsList.add(myViewHolder.btnText);
        myViewHolder.cartAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!SharedPreference.getUserid(context).isEmpty()) {
                    if (GlobalUtils.isNetworkAvailable((Activity) context)) {
                        if (buttonsList.get(i).getText().toString().equalsIgnoreCase("Notify Me") &&
                                productData.get(i).getOptionId().equalsIgnoreCase("0")) {
                            displayNotifyMeDialog(productData.get(i).getProductId(), productData.get(i).getOptionId(), productData.get(i).getOptionValueId());
                        } else if ((buttonsList.get(i).getText().toString().equalsIgnoreCase("Notify Me") ||
                                buttonsList.get(i).getText().toString().equalsIgnoreCase("Add to cart"))
                                && !productData.get(i).getOptionId().equalsIgnoreCase("0")) {
                            Intent intOption2 = new Intent(context, OptionsActivity.class);
                            intOption2.putExtra(Constants.PreferenceConstants.PRODUCTID, productData.get(i).getProductId());
                            intOption2.putExtra(Constants.PreferenceConstants.TITLE, productData.get(i).getProductName());
                            context.startActivity(intOption2);
                        } else if (buttonsList.get(i).getText().toString().equalsIgnoreCase("Add to cart") &&
                                productData.get(i).getOptionId().equalsIgnoreCase("0")) {
                            optionId = productData.get(i).getOptionId();
                            optionValueId = productData.get(i).getOptionValueId();
                            addToCartInterface.addToCart(productData.get(i).getProductId(), selectedQty, "add", optionId, optionValueId);
                        }
                    } else {
                        Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("Clicked", ": LoggedOut");
                    Intent intLogin = new Intent(context, LoginActivity.class);
                    intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
                    ((Activity) context).startActivityForResult(intLogin, Constants.PreferenceConstants.BACKFROMPRODDETAILS);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        FrameLayout frameLayout;
        LinearLayout mainLayout;
        RelativeLayout ddLayout;
        AutoCompleteTextView actvQuantity;
        TextView textproduct, productprice, prod_discount, disPer, tvQuantity, btnText, tvOptionAvail;
        private LinearLayout cartAddButton, discountPercentage, behind, bottomlayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            frameLayout = itemView.findViewById(R.id.fldata);
            imageView = (ImageView) itemView.findViewById(R.id.ivProductImage);
            textproduct = (TextView) itemView.findViewById(R.id.tvProductName);
            productprice = (TextView) itemView.findViewById(R.id.Product_price);
            cartAddButton = (LinearLayout) itemView.findViewById(R.id.lladdtocart);
            cartAddButton.setOnClickListener(this);
            prod_discount = (TextView) itemView.findViewById(R.id.tvoldAmount);
            discountPercentage = itemView.findViewById(R.id.lldiscount);
            bottomlayout = itemView.findViewById(R.id.llbottom);
            disPer = itemView.findViewById(R.id.tvdis);
            behind = itemView.findViewById(R.id.llBehindLine);
            ddLayout = itemView.findViewById(R.id.clickLayout);
            actvQuantity = itemView.findViewById(R.id.actv_quantity);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            btnText = itemView.findViewById(R.id.tv_addToCart);
            tvOptionAvail = itemView.findViewById(R.id.tv_option_avail);
            tvOptionAvail.setVisibility(View.GONE);
            tvOptionAvail.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String imageURL = productData.get(pos).getProductImage();
                    String addimage = imageURL.replace(".jpg", "-300x400.jpg");
                    String image = imagebaseurl + addimage;
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, productData.get(pos).getProductId());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, productData.get(pos).getProductName());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, image);
                    context.startActivity(intent);
                }
            });

            final ArrayList<String> qtyList = new ArrayList<>();
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
//                    Toast.makeText(context, "act clicked!", Toast.LENGTH_SHORT).show();
                    actvQuantity.showDropDown();
                    if (i == 9) {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.enter_quantity_layout);
                        dialog.show();
                        final EditText etQuantity = dialog.findViewById(R.id.et_quantity);
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
                                    selectedQty = etQuantity.getText().toString();
                                    int limit = Integer.parseInt(productData.get(getAdapterPosition()).getProductQuantity());
                                    if (Integer.parseInt(selectedQty) <= limit) {
                                        tvQuantity.setText(selectedQty);
                                        dialog.dismiss();
                                    } else {
                                        Toast.makeText(context, "Only " + limit + " left in the stock. ", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(context, "Quantity should be at least 1", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        int qty = i+1;
                        int limit = Integer.parseInt(productData.get(getAdapterPosition()).getProductQuantity());
                        Log.e("qty & limit",":"+qty+" and "+limit);
                        if(qty > limit){
                            selectedQty = "1";
                            tvQuantity.setText("1");
                            if(limit==0){
                                Toast.makeText(context, "Product not available!", Toast.LENGTH_SHORT).show();
                            }else if(limit ==1){
                                Toast.makeText(context, "Only 1 item left in the stock!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Only " + limit + " left in the stock. ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            selectedQty = qtyList.get(i);
                            tvQuantity.setText(qtyList.get(i) + "");
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
            switch (view.getId()) {
                case R.id.tv_option_avail:
                    Intent intOption = new Intent(context, OptionsActivity.class);
                    intOption.putExtra(Constants.PreferenceConstants.PRODUCTID, productData.get(getAdapterPosition()).getProductId());
                    intOption.putExtra(Constants.PreferenceConstants.TITLE, productData.get(getAdapterPosition()).getProductName());
                    context.startActivity(intOption);
                    break;
                case R.id.lladdtocart:
//                        if(!SharedPreference.getUserid(context).isEmpty()) {
//                            Log.e("Clicked",": LoggedIn");
//                            if(GlobalUtils.isNetworkAvailable((Activity)context)) {
//                                if (productData.get(getAdapterPosition()).getOptionId().equalsIgnoreCase("0")) {
//                                    if (productData.get(getAdapterPosition()).getProductQuantity().equalsIgnoreCase("0")) {
//                                        displayNotifyMeDialog(productData.get(getAdapterPosition()).getProductId());
//                                    }else {
//                                        optionId = productData.get(getAdapterPosition()).getOptionId();
//                                        optionValueId = productData.get(getAdapterPosition()).getOptionValueId();
//                                        addToCartInterface.addToCart(productData.get(getAdapterPosition()).getProductId(), selectedQty, "add",
//                                                productData.get(getAdapterPosition()).getOptionId(), productData.get(getAdapterPosition()).getOptionValueId());
//                                    }
//                                } else {
//                                    if(productData.get(getAdapterPosition()).getProductInStock().equalsIgnoreCase("1")){
//                                    Intent intOption2= new Intent(context, OptionsActivity.class);
//                                    intOption2.putExtra(Constants.PreferenceConstants.PRODUCTID,productData.get(getAdapterPosition()).getProductId());
//                                    intOption2.putExtra(Constants.PreferenceConstants.TITLE, productData.get(getAdapterPosition()).getProductName());
//                                    context.startActivity(intOption2);
//                                    }else{
//                                        displayNotifyMeDialog(productData.get(getAdapterPosition()).getProductId()
//                                                ,productData.get(getAdapterPosition()).getOptionId(),
//                                                productData.get(getAdapterPosition()).getOptionValueId()));
//                                    }
//                                }
//                            }else{
//                                Toast.makeText(context, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
//                            }
//                        }else{
//                            Log.e("Clicked",": LoggedOut");
//                            Intent intLogin= new Intent(context, LoginActivity.class);
//                            intLogin.putExtra(Constants.PreferenceConstants.BACKFROM, HomeActivity.class.getSimpleName());
//                            ((Activity)context).startActivityForResult(intLogin,Constants.PreferenceConstants.BACKFROMPRODDETAILS);
//                        }
                    break;
            }
        }

    }
        public  interface AddToCartInterface{
            void addToCart(String productId, String selectedQty, String add, String optionId, String optionValue);
        }

    public void displayNotifyMeDialog(final String productId, final String optId, final String optValue) {
        final Dialog notifyDialog = new Dialog(context);
        notifyDialog.setContentView(R.layout.notify_me_dialog_layout);
        notifyDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        notifyDialog.show();
        final EditText etName = notifyDialog.findViewById(R.id.et_name);
        final EditText etEmail = notifyDialog.findViewById(R.id.et_email);
        final EditText etRemark = notifyDialog.findViewById(R.id.et_remark);
        Button btnNotify = notifyDialog.findViewById(R.id.btn_notify);
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
                String strName = etName.getText().toString();
                String strEmail = etEmail.getText().toString();
                String strRemark = etRemark.getText().toString();
                if (!strName.isEmpty() && !strEmail.isEmpty()) {
                    if (Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
                        if (GlobalUtils.isNetworkAvailable((Activity) context)) {
                            GlobalUtils.showdialog(context);
                            callNotifySend(productId, strName, strEmail, strRemark, optId, optValue, notifyDialog);
                        } else
                            Toast.makeText(context, "Please check your internet connection...", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(context, "Please Enter Valid Email Address", Toast.LENGTH_SHORT).show();
                    }
                } else if (strName.isEmpty()) {
                    Toast.makeText(context, "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                } else if (strEmail.isEmpty()) {
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
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<JSONObject> call = restInterface.setNotifyMe(globalUtils.getKey(), globalUtils.getapidate(), params);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                GlobalUtils.hidedialog();
                Log.e("NotifyResp", ":" + response);
                if (response != null) {
                    notifyDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                GlobalUtils.hidedialog();
                Log.e("NotifyExc", ":" + t);
            }
        });
    }
}
