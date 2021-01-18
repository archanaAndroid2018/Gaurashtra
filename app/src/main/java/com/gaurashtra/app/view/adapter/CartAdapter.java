package com.gaurashtra.app.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gaurashtra.app.view.activity.OptionsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.GetCartData.GetCartProduct;
import com.gaurashtra.app.view.activity.CartActivity;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    List< GetCartProduct.Result.CartDetails> cartDataList;
    String prodPrice, oldPrice, spclPrice, offerPercent;
    AddRemoveInterface addRemoveInterface;
    String baseImgUrl="https://www.gaurashtra.com/image/cache/";
    private int selectedQuantity=1;
    PrefClass prefClass;
    String currency="",currencySymbol="\u20B9";
    float currencyValue= (float) 1.00;

    public CartAdapter(CartActivity cartActivity, List<GetCartProduct.Result.CartDetails> cartData, CartActivity activity) {
        this.context = cartActivity;
        this.cartDataList= cartData;
        this.addRemoveInterface=activity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
        CartViewHolder viewHolder = new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int i) {
        prefClass= new PrefClass(context);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                currency=prefClass.getSelectedCurrency();
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
            }
        }
        String imgPath=cartDataList.get(i).getProductImage().replace(".jpg","-300x400.jpg");
        holder.tvProductName.setText(cartDataList.get(i).getProductName());
        if(cartDataList.get(i).getProductName().contains("&amp;")) {
            holder.tvProductName.setText(cartDataList.get(i).getProductName().replace("&amp;", " "));
        }
        Glide.with(context).load(baseImgUrl+imgPath).skipMemoryCache(true).into(holder.ivProductImage);
        spclPrice= cartDataList.get(i).getSpecialPrice();
        if(cartDataList.get(i).getOptionId().equalsIgnoreCase("0")){
//            holder.tvOptionAvail.setVisibility(View.GONE);
            holder.optionNameLayout.setVisibility(View.GONE);
        }else{
//            holder.tvOptionAvail.setVisibility(View.VISIBLE);
            holder.optionNameLayout.setVisibility(View.VISIBLE);
            holder.tvOptionLabel.setText(cartDataList.get(i).getOptionType().replace("&amp;","&"));
            holder.tvOptionValue.setText(cartDataList.get(i).getOptionName().replace("&amp;","&"));
        }
        int prodQty= Integer.parseInt(cartDataList.get(i).getCartQuantity());
        if(Double.parseDouble(spclPrice)==0.0000) {
            prodPrice = String.valueOf(cartDataList.get(i).getCartProductPrice());
            double tax= Double.parseDouble(cartDataList.get(i).getTaxRate());
            double taxPrice= Double.parseDouble(prodPrice)*(tax/100);
            double netPrice= Double.parseDouble(prodPrice)+taxPrice;
            holder.oldPriceLayout.setVisibility(View.GONE);
            holder.tvOffer.setVisibility(View.GONE);
            holder.tvProductPrice.setText(currencySymbol+new DecimalFormat("0.00").format(netPrice*prodQty*currencyValue));
        }else {
            oldPrice = cartDataList.get(i).getProductPrice();
            prodPrice = cartDataList.get(i).getSpecialPrice();
            double tax= Double.parseDouble(cartDataList.get(i).getTaxRate());
            double taxPrice= Double.parseDouble(oldPrice)*(tax/100);
            double netPriceOld= Double.parseDouble(oldPrice)+taxPrice;

            double taxPriceSpcl= Double.parseDouble(prodPrice)*(tax/100);
            double netPriceSpcl= Double.parseDouble(prodPrice)+taxPriceSpcl;
            // offerPercent= String.valueOf(Double.parseDouble(oldPrice)-Double.parseDouble(prodPrice)*100/Double.parseDouble(oldPrice))+"% OFF";
            holder.oldPriceLayout.setVisibility(View.VISIBLE);
            holder.tvOffer.setVisibility(View.VISIBLE);
            holder.tvOffer.setText(cartDataList.get(i).getCartProductPriceText());
            holder.tvoldPrice.setText(currencySymbol +new DecimalFormat("0.00").format(netPriceOld*currencyValue));
            holder.tvProductPrice.setText(currencySymbol+new DecimalFormat("0.00").format(netPriceSpcl*prodQty*currencyValue));
        }
        holder.tvQuantity.setText(cartDataList.get(i).getCartQuantity());
        holder.btnSaveForLater.setId(i);
    }

    @Override
    public int getItemCount() {
        SharedPreference.setCartCount(context,String.valueOf(cartDataList.size()));
        return cartDataList.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Spinner spList;
        LinearLayout qtyLayout, optionNameLayout;
        TextView tvProductName,tvReview, tvProductPrice, tvOffer, tvoldPrice,btnSaveForLater,
                 tvOptionLabel, tvOptionValue,tvQuantity,tvOptionAvail;
        ImageView ivProductImage, star1,star2,star3,star4,star5, ivBtnremove, shine;
        FrameLayout oldPriceLayout,saveForLaterFrame;
        private ArrayList<String> quantityList = new ArrayList<>();

        private AutoCompleteTextView actvQuantity;

        public CartViewHolder(View itemView){
            super(itemView);
            spList = itemView.findViewById(R.id.spinner_quantity);
            tvProductName = itemView.findViewById(R.id.Text_productName);
            tvReview = itemView.findViewById(R.id.tv_review_count);
            tvReview.setVisibility(View.GONE);
            tvProductPrice = itemView.findViewById(R.id.tv_net_price);
            tvOffer= itemView.findViewById(R.id.Text_off);
            tvoldPrice= itemView.findViewById(R.id.tvoldAmount);
            ivProductImage= itemView.findViewById(R.id.wishImage);
            qtyLayout= itemView.findViewById(R.id.llqty);
            oldPriceLayout= itemView.findViewById(R.id.frame_old_amount);
            ivBtnremove= itemView.findViewById(R.id.iv_btn_remove);
            saveForLaterFrame=itemView.findViewById(R.id.tab_add_cart_wish);
            actvQuantity = itemView.findViewById(R.id.actv_quantity);
            optionNameLayout = itemView.findViewById(R.id.option_name_layout);
            tvOptionLabel = itemView.findViewById(R.id.tv_option_type);
            tvOptionValue = itemView.findViewById(R.id.tv_option_value);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            tvOptionAvail= itemView.findViewById(R.id.tv_option_avail);
            tvOptionAvail.setOnClickListener(this);

            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) saveForLaterFrame.getLayoutParams();
            lp.setMargins(60,0,0,0);
            btnSaveForLater= itemView.findViewById(R.id.btnAddToCartOrWish);
            btnSaveForLater.setGravity(Gravity.LEFT);
            btnSaveForLater.setPadding(0,0,0,0);
            btnSaveForLater.setText("Save for later");
            qtyLayout.setVisibility(View.VISIBLE);
            shine= itemView.findViewById(R.id.shine_img);
            shine.setVisibility(View.GONE);
            star1= itemView.findViewById(R.id.prod_wl_star1);
            star2= itemView.findViewById(R.id.prod_wl_star2);
            star3= itemView.findViewById(R.id.prod_wl_star3);
            star4= itemView.findViewById(R.id.prod_wl_star4);
            star5= itemView.findViewById(R.id.prod_wl_star5);
            quantityList.add("1");
            quantityList.add("2");
            quantityList.add("3");
            quantityList.add("4");
            quantityList.add("5");
            quantityList.add("6");
            quantityList.add("7");
            quantityList.add("8");
            quantityList.add("9");
            quantityList.add("More");

            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, quantityList);
            actvQuantity.setAdapter(dataAdapter);

            actvQuantity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    actvQuantity.showDropDown();
                    if (position==9){
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
                                    int limit= Integer.parseInt(cartDataList.get(getAdapterPosition()).getProductQuantity());
                                    if (Integer.parseInt(etQuantity.getText().toString())<=limit) {
                                        addRemoveInterface.updateQty(cartDataList.get(getAdapterPosition()).getCartProductId(), etQuantity.getText().toString(),cartDataList.get(getAdapterPosition()));
//                                        actvQuantity.setText(etQuantity.getText().toString());
                                        tvQuantity.setText(etQuantity.getText().toString());
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(context, "Only "+limit+" left in the stock. ", Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(context, "Quantity should be at least 1", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        int limit= Integer.parseInt(cartDataList.get(getAdapterPosition()).getProductQuantity());
                        int qty = position+1;
                        if(qty > limit){
                            selectedQuantity = 1;
                            tvQuantity.setText("1");
                            if(qty==0){
                                Toast.makeText(context, "Product not available!", Toast.LENGTH_SHORT).show();
                            }else if(limit ==1){
                                Toast.makeText(context, "Only 1 item left in the stock!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Only " + limit + " left in the stock. ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            selectedQuantity = position + 1;
                            addRemoveInterface.updateQty(cartDataList.get(getAdapterPosition()).getCartProductId(), selectedQuantity + "", cartDataList.get(getAdapterPosition()));
                            tvQuantity.setText(selectedQuantity + "");
                        }
                    }
                    actvQuantity.setAdapter(dataAdapter);
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


            spList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position==9){
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
                                    addRemoveInterface.updateQty(cartDataList.get(getAdapterPosition()).getCartProductId(), etQuantity.getText().toString(),cartDataList.get(getAdapterPosition()));
                                    spList.setSelection(getAdapterPosition());
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(context, "Quantity should be at least 1", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    } else {
                        selectedQuantity = position+1;
                        int limit = Integer.parseInt(cartDataList.get(getAdapterPosition()).getProductQuantity());
                        if(selectedQuantity > limit){
                            selectedQuantity = 1;
                            tvQuantity.setText("1");
                            if(limit==0){
                                Toast.makeText(context, "Product not available!", Toast.LENGTH_SHORT).show();
                            }else if(limit ==1){
                                Toast.makeText(context, "Only 1 item left in the stock!", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context, "Only " + limit + " left in the stock. ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            addRemoveInterface.updateQty(cartDataList.get(getAdapterPosition()).getCartProductId(), selectedQuantity + "",cartDataList.get(getAdapterPosition()));
                            spList.setSelection(getAdapterPosition());
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            ivBtnremove.setOnClickListener(this);
            saveForLaterFrame.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String imageURL = cartDataList.get(pos).getProductImage();
                    String addimage = imageURL.replace(".jpg", "-300x300.jpg");
                    String image = baseImgUrl + addimage;
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, cartDataList.get(pos).getCartProductId());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, cartDataList.get(pos).getProductName());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, image);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    if(!cartDataList.get(getAdapterPosition()).getOptionId().equalsIgnoreCase("0")) {
                        intent.putExtra(Constants.PreferenceConstants.OPTIONID, cartDataList.get(getAdapterPosition()).getOptionValueId());
                    }
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){

                case R.id.iv_btn_remove:
                     addRemoveInterface.removeProduct(cartDataList.get(getAdapterPosition()).getProductName(),cartDataList.get(getAdapterPosition()).getCartProductId(),"1",cartDataList.get(getAdapterPosition()));
                    break;
                case R.id.tab_add_cart_wish:
//                    if(!cartDataList.get(getAdapterPosition()).getOptionId().equalsIgnoreCase("0")){
//
//                        Intent intent=new Intent(context, OptionsActivity.class);
//                        intent.putExtra(Constants.PreferenceConstants.TITLE, cartDataList.get(getAdapterPosition()).getProductName());
//                        intent.putExtra(Constants.PreferenceConstants.PRODUCTID, cartDataList.get(getAdapterPosition()).getCartProductId());
//                        intent.putExtra(Constants.PreferenceConstants.BTNACTION, Constants.PreferenceConstants.ADDTOWISH );
//                        context.startActivity(intent);
//                    }else {
                        addRemoveInterface.moveToWishList(cartDataList.get(getAdapterPosition()).getCartProductId(), "1", "add", cartDataList.get(getAdapterPosition()));
//                    }
                    break;
                case R.id.tv_option_avail:
                    Intent intent=new Intent(context, OptionsActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.TITLE, cartDataList.get(getAdapterPosition()).getProductName());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, cartDataList.get(getAdapterPosition()).getCartProductId());
                    intent.putExtra(Constants.PreferenceConstants.BTNACTION, Constants.PreferenceConstants.ADDTOWISH );
                    context.startActivity(intent);

                    break;
            }

        }
    }
    public interface AddRemoveInterface{
        void updateQty(String pId, String qty,GetCartProduct.Result.CartDetails cartDetailsData);
        void removeProduct(String productName, String pId, String qty,GetCartProduct.Result.CartDetails cartDetailsData);
        void moveToWishList(String cartProductId, String qty, String action,GetCartProduct.Result.CartDetails cartDetailsData);
    }
}
