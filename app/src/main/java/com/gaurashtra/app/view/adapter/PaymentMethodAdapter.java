package com.gaurashtra.app.view.adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.CartInfoBean.CartInfoDTO;
import com.gaurashtra.app.view.activity.PaymentMethodActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.PaymentViewHolder> {
    Context context;
    ArrayList<CartInfoDTO.Result.PaymentGateway> paymentGateways;
    PaymentSelectorInterface paymentSelection;
    Boolean isCOD=false;
    LinkedHashMap<Integer, ImageView> radioImage= new LinkedHashMap<>();

    public PaymentMethodAdapter(PaymentMethodActivity activity, ArrayList<CartInfoDTO.Result.PaymentGateway> cartInfo, PaymentMethodActivity selectioninterface, Boolean COD) {
        this.context= activity;
        this.paymentGateways=cartInfo;
        this.paymentSelection=selectioninterface;
        this.isCOD=COD;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_gateway_row,parent,false);
        PaymentViewHolder viewHolder = new PaymentViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, final int position) {
        try {
            holder.mainlayout.setVisibility(View.VISIBLE);
            holder.tvPaymentTitle.setText(paymentGateways.get(position).getDescription());
            holder.tvPaymentName.setText(paymentGateways.get(position).getTitle());
            if(paymentGateways.get(position).getName().equalsIgnoreCase("Cash on delivery") && !isCOD){
                holder.mainlayout.setVisibility(View.GONE);
            }
            radioImage.put(position, holder.ivSelected);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return paymentGateways.size();
    }

    public class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvPaymentName, tvPaymentTitle;
        ImageView ivSelected;
        LinearLayout mainlayout;
        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPaymentName= itemView.findViewById(R.id.tv_payment_name);
            ivSelected= itemView.findViewById(R.id.iv_sel_icon);
            tvPaymentTitle= itemView.findViewById(R.id.tv_payment_title);
            mainlayout= itemView.findViewById(R.id.ll_payment);
            mainlayout.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            paymentSelection.paymentMethodType(paymentGateways.get(getAdapterPosition()).getName());
            List<Integer> idList= new ArrayList<>(radioImage.keySet());
            for(int i=0; i<idList.size(); i++){
                if(idList.get(i)==getAdapterPosition()){
                    radioImage.get(i).setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_tick));
                }else{
                    radioImage.get(i).setImageDrawable(context.getResources().getDrawable(R.drawable.ic_circle_ring));
                }
            }
        }
    }

    public interface PaymentSelectorInterface{
        void paymentMethodType(String type);
    }
}
