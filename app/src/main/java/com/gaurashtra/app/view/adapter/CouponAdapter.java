package com.gaurashtra.app.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.GetCartData.GetCartProduct;
import com.gaurashtra.app.view.activity.CartActivity;
import com.gaurashtra.app.view.activity.ConfirmOrderActivity;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    Context context;
    ArrayList<GetCartProduct.Result.CouponDatum> couponList;
    SelectedCouponInterface couponInterface;
    HashMap<Integer, ImageView> btnRadioMap= new HashMap<>();

    public CouponAdapter(ConfirmOrderActivity cartActivity, ArrayList<GetCartProduct.Result.CouponDatum> couponList, ConfirmOrderActivity activity) {
        this.context= cartActivity;
        this.couponList= couponList;
        this.couponInterface= activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.coupon_items,viewGroup,false);
        ViewHolder vh= new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.radioIcon.setId(i);
        holder.tvCouponCode.setText(couponList.get(i).getCouponCode());
        holder.tvCouponContent.setText(couponList.get(i).getCouponContent());
        if(couponList.size()==1){
            holder.line.setVisibility(View.GONE);
        }else{
            holder.line.setVisibility(View.VISIBLE);
        }
        btnRadioMap.put(i, holder.radioIcon);
    }

    @Override
    public int getItemCount() {
        return couponList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCouponCode, tvCouponContent, btnApply;
        ImageView radioIcon;
        LinearLayout line, cardBg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCouponCode = itemView.findViewById(R.id.tv_coupon_code);
            tvCouponContent = itemView.findViewById(R.id.tv_coupon_content);
            line = itemView.findViewById(R.id.line);
            cardBg = itemView.findViewById(R.id.container);
//            cardBg.setOnClickListener(clickListener);
            radioIcon = itemView.findViewById(R.id.iv_sel_icon);
            btnApply = itemView.findViewById(R.id.tv_btn_apply);
            btnApply.setOnClickListener(clickListener);
        }

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponInterface.selectedCoupons(getAdapterPosition());
                List<Integer> idList= new ArrayList<>(btnRadioMap.keySet());
                for(int i=0; i<idList.size(); i++){
                    if(idList.get(i)==getAdapterPosition()){
                        btnRadioMap.get(i).setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                    }else{
                        btnRadioMap.get(i).setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_ring));
                    }
                }
            }
        };
    }

    public interface SelectedCouponInterface{
        void selectedCoupons(int position);
    }
}
