package com.gaurashtra.app.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.WishListBean.WishlistDatum;
import com.gaurashtra.app.view.activity.ProductDetailActivity;
import com.gaurashtra.app.view.activity.WishListActivity;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishDataViewHolder> {
    private Context mContext;
    private List<WishlistDatum> wishdata=new ArrayList<>();
    ListActionInterface alterationInterface;
    String prodId;
    ArrayList<String> ddQtyList;
    String baseUrl="https://www.gaurashtra.com/image/cache/";
    PrefClass prefClass;
    String currency="", currencySymbol="\u20B9";
    float currencyValue= (float) 1.000;
    Float actualCurrValue;
    String selectedQty="1";


    public WishListAdapter(WishListActivity wishListActivity, List<WishlistDatum> wishlistData, WishListActivity listActivity) {
        this.mContext=wishListActivity;
        this.wishdata=wishlistData;
        this.alterationInterface= listActivity;
    }


    @Override
    public WishDataViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem,parent,false);
        WishDataViewHolder viewHolder = new WishDataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final WishDataViewHolder wishDataViewHolder, final int i) {
        prefClass= new PrefClass(mContext);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                currency=prefClass.getSelectedCurrency();
                actualCurrValue= Float.parseFloat(currencyData.getValue());
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currencySymbol= currencyData.getSymbol().trim();
            }
        }
        String imageUrl=wishdata.get(i).getProductImage();
        String addimage=imageUrl.replace(".jpg","-300x300.jpg");
        String TopImageList=baseUrl+addimage;
        Picasso.with(mContext).load(TopImageList).into(wishDataViewHolder.productImage);
        wishDataViewHolder.productname.setText(wishdata.get(i).getProductName());
        if(wishdata.get(i).getProductName().contains("&amp;")) {
            wishDataViewHolder.productname.setText(wishdata.get(i).getProductName().replace("&amp;", " "));
        }
        Double tax= Double.parseDouble(wishdata.get(i).getTaxRate());
        Double spclPrice= Double.parseDouble(wishdata.get(i).getSpecialPrice());
        Double prodPrice= Double.parseDouble(wishdata.get(i).getProductPrice());
        if(spclPrice==0.00){
            Double netPrice= prodPrice + (prodPrice*(tax/100.00));
            wishDataViewHolder.product_off.setVisibility(View.GONE);
            wishDataViewHolder.oldAmountFrame.setVisibility(View.GONE);
            wishDataViewHolder.product_price.setText(currencySymbol+new DecimalFormat("0.00").format(netPrice* currencyValue));
        }else{
            Double netPrice= spclPrice + (prodPrice*(tax/100.00));
            Double netoldPrice= prodPrice + (prodPrice*(tax/100.00));
            wishDataViewHolder.oldAmountFrame.setVisibility(View.VISIBLE);
            wishDataViewHolder.product_off.setVisibility(View.VISIBLE);
            Double offerPercent= ((netoldPrice - netPrice)/netoldPrice)*actualCurrValue;
            String offerP= new DecimalFormat("0").format(offerPercent);
            wishDataViewHolder.product_off.setText(offerP+"% OFF");
            if(Float.parseFloat(offerP)>0) {
                wishDataViewHolder.product_off.setText(offerP+"% OFF");
            }else if (Float.parseFloat(offerP)==0) {
                double offerAmount = netoldPrice - netPrice;
                if (offerAmount > 0.00) {
                    wishDataViewHolder.product_off.setText(currencySymbol+ new DecimalFormat("0.0").format(offerAmount * actualCurrValue) + " OFF");
                }
            }
            wishDataViewHolder.product_OldPrice.setText(currencySymbol+new DecimalFormat("0.00").format(netoldPrice * currencyValue));
            wishDataViewHolder.product_price.setText(currencySymbol+new DecimalFormat("0.00").format(netPrice* currencyValue));
        }
        Log.e("GetProductPrice","\n"+wishdata.get(i).getProductPrice());


        Float count= Float.parseFloat(wishdata.get(i).getTotalRating().toString());
        setRatingStar(wishDataViewHolder,count);
            Animation animation = new TranslateAnimation(0, 320, 0, 0);
            animation.setDuration(850);
            animation.setRepeatMode(Animation.RESTART);
            animation.setFillAfter(false);
            animation.setRepeatCount(1000);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            wishDataViewHolder.shine.startAnimation(animation);

    }

    private void setRatingStar(WishDataViewHolder holder, Float count) {
        if (count == 1) {
            holder.star1.setVisibility(View.VISIBLE);
        } else if (count == 2) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
        } else if (count == 3) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
        } else if (count == 4) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.VISIBLE);
        } else if (count == 5) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.VISIBLE);
            holder.star5.setVisibility(View.VISIBLE);
        } else if (count > 0 && count < 1) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star1.setImageResource(R.drawable.halfstar);
        } else if (count > 1 && count < 2) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star2.setImageResource(R.drawable.halfstar);
        } else if (count > 2 && count < 3) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star3.setImageResource(R.drawable.halfstar);
        } else if (count > 3 && count < 4) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.VISIBLE);
            holder.star4.setImageResource(R.drawable.halfstar);
        } else if (count > 4 && count < 5) {
            holder.star1.setVisibility(View.VISIBLE);
            holder.star2.setVisibility(View.VISIBLE);
            holder.star3.setVisibility(View.VISIBLE);
            holder.star4.setVisibility(View.VISIBLE);
            holder.star5.setVisibility(View.VISIBLE);
            holder.star5.setImageResource(R.drawable.halfstar);
        }
    }

    @Override
    public int getItemCount() {
        return wishdata.size();
    }


    public class WishDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout qty;
        private ImageView productImage, shine;
        AutoCompleteTextView actvQuantity;
        TextView tvQuantity;
        private TextView productname,product_price,product_off, product_OldPrice, reviewCount;
        LinearLayout bottomLayout;
        FrameLayout tabAddToCart, oldAmountFrame;
        ImageView deleteImgBtn,star1, star2, star3, star4, star5;
//        Spinner spinnerQty;
        public WishDataViewHolder(View itemView){
            super(itemView);
            productImage=itemView.findViewById(R.id.wishImage);
            productname=itemView.findViewById(R.id.Text_productName);
            product_price=itemView.findViewById(R.id.tv_net_price);
            product_OldPrice =itemView.findViewById(R.id.tvoldAmount);
            product_off=itemView.findViewById(R.id.Text_off);
            deleteImgBtn= itemView.findViewById(R.id.iv_btn_remove);
            reviewCount= itemView.findViewById(R.id.tv_review_count);
            oldAmountFrame= itemView.findViewById(R.id.frame_old_amount);
            star1= itemView.findViewById(R.id.prod_wl_star1);
            star2= itemView.findViewById(R.id.prod_wl_star2);
            star3= itemView.findViewById(R.id.prod_wl_star3);
            star4= itemView.findViewById(R.id.prod_wl_star4);
            star5= itemView.findViewById(R.id.prod_wl_star5);
            tabAddToCart= itemView.findViewById(R.id.tab_add_cart_wish);
            bottomLayout= itemView.findViewById(R.id.llpack);
            shine= itemView.findViewById(R.id.shine_img);
            qty = itemView.findViewById(R.id.llqty);
            deleteImgBtn.setOnClickListener(this);
            tabAddToCart.setOnClickListener(this);
            ddQtyList= new ArrayList<>();
            ddQtyList.add("Pack of 1");
            ddQtyList.add("Pack of 2");
            ddQtyList.add("Pack of 3");
            ddQtyList.add("Pack of 4");

            actvQuantity = itemView.findViewById(R.id.actv_quantity);
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
            final ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, qtykList);
            actvQuantity.setAdapter(dataAdapter);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    String imageURL = wishdata.get(pos).getProductImage();
                    String addimage = imageURL.replace(".jpg", "-300x300.jpg");
                    String image = baseUrl + addimage;
                    Intent intent = new Intent(mContext, ProductDetailActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTID, wishdata.get(pos).getProductId());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTNAME, wishdata.get(pos).getProductName());
                    intent.putExtra(Constants.PreferenceConstants.PRODUCTIMG, image);
                    mContext.startActivity(intent);
                }
            });

            actvQuantity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    actvQuantity.showDropDown();
                    if(i== 9){
                        final Dialog dialog=new Dialog(mContext);
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
                                    int limit= Integer.parseInt(wishdata.get(getAdapterPosition()).getProductQuantity());
                                    if (Integer.parseInt(selectedQty)<=limit) {
                                        tvQuantity.setText(selectedQty);
                                        dialog.dismiss();
                                    }else {
                                        Toast.makeText(mContext, "Only "+limit+" left in the stock. ", Toast.LENGTH_LONG).show();
                                    }
                                }else {
                                    Toast.makeText(mContext, "Quantity should be at least 1", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }else{
                        selectedQty= qtykList.get(i);
                        tvQuantity.setText(qtykList.get(i)+"");
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
        public void onClick(View v) {
            String action=" ";
            switch (v.getId()) {
                case (R.id.iv_btn_remove):
                    action= "delete";
                    prodId= wishdata.get(getLayoutPosition()).getProductId();
                    Log.e("DeleteProdId",":"+prodId);
                    alterationInterface.deleteItemFromWishList(wishdata.get(getLayoutPosition()).getProductName(),prodId, selectedQty, action);
                    break;
                case (R.id.tab_add_cart_wish):
                    action= "add";
                    prodId= wishdata.get(getLayoutPosition()).getProductId();
                    alterationInterface.addToCart(prodId, selectedQty, action,
                            wishdata.get(getLayoutPosition()).getOptionId(),wishdata.get(getLayoutPosition()).getOptionValueId());
                    break;
            }
        }
    }
    public interface ListActionInterface {
        void deleteItemFromWishList(String prodName, String prodId, String qty, String action);
        void addToCart(String prodId, String qty, String action, String optionId, String optionValue);
    }
}
