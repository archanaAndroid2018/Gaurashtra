package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;

import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.model.bean.OrderBean.OrderListBean;
import com.gaurashtra.app.view.activity.OrderDetailActivity;
import com.gaurashtra.app.view.activity.OrdersActivity;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    ArrayList<OrderListBean.Result.OrderDatum> orderDataList;
    GlobalUtils globalUtils= new GlobalUtils();
    ReportLayoutInterface reportLayoutInterface;
    PrefClass prefClass;
    String currency="";
    float currencyValue= (float) 1.000;
    LinkedHashMap<String, String> orderStatusMap= new LinkedHashMap<>();

//    public OrderAdapter(Context context){
//        this.context = context;
//    }

    public OrderAdapter(OrdersActivity ordersActivity, List<OrderListBean.Result.OrderDatum> orderList, OrdersActivity activity) {
        this.context = ordersActivity;
        this.orderDataList= (ArrayList<OrderListBean.Result.OrderDatum>) orderList;
        this.reportLayoutInterface =activity;

        orderStatusMap.put("1","Pending");
        orderStatusMap.put("2","Processing");
        orderStatusMap.put("3","In Transit");
        orderStatusMap.put("4","Payment Failed");
        orderStatusMap.put("5","Delivered");
        orderStatusMap.put("6","Partially Refunded");
        orderStatusMap.put("7","Payment Awaited");
        orderStatusMap.put("8","Returned & Refunded");
        orderStatusMap.put("9","Damaged & Refunded");
        orderStatusMap.put("10","Voided");
        orderStatusMap.put("11","On Hold");
        orderStatusMap.put("12","Cancelled Reversal");
        orderStatusMap.put("13","Awaiting Response");
        orderStatusMap.put("14","Out For Delivery");
        orderStatusMap.put("15","Shipment Returned");
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem,parent,false);
        OrderViewHolder viewHolder = new OrderViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int i) {
        prefClass= new PrefClass(context);
        Type type=new TypeToken<List<GetCurrencyList.Result.CurrencyData>>(){}.getType();
        List<GetCurrencyList.Result.CurrencyData> currencyDataList=(List<GetCurrencyList.Result.CurrencyData>)(new Gson()).fromJson(prefClass.getCurrencyDataList(),type);
        for (GetCurrencyList.Result.CurrencyData currencyData:currencyDataList){
            if (prefClass.getSelectedCurrency().equalsIgnoreCase(currencyData.getCode())){
                currency=prefClass.getSelectedCurrency();
                String value= new DecimalFormat("0.000").format(Float.parseFloat(currencyData.getValue()));
                currencyValue= Float.parseFloat(value);
                Log.e("currencyValue",":"+currencyValue);
                currency= currencyData.getSymbol().trim();
            }
        }
        holder.tvOrderNo.setText(orderDataList.get(i).getOrderId());
        holder.tvOrderDate.setText(orderDataList.get(i).getOrderDate());
        Double amount= Double.parseDouble(orderDataList.get(i).getAmount());
        holder.tvOrderAmount.setText(currency+" "+new DecimalFormat("0.00").format(amount*currencyValue));
        holder.tvPaymentMethiod.setText(orderDataList.get(i).getPaymentMethod());
        String imageurl= GlobalUtils.IMAGE_BASE_URL+ orderDataList.get(i).getProductImage().replace(".jpg", "-300x400.jpg");
        Glide.with(context).load(imageurl).into(holder.prodImage);
        String orderStatus= orderDataList.get(i).getOrderStatus();
        holder.tvDeliveredDate.setText(orderStatusMap.get(orderStatus));
        try {
            if(!orderDataList.get(i).getDelivered().isEmpty()) {
                holder.tvDeliveredDate.setText(orderStatusMap.get(orderStatus)+"("+orderDataList.get(i).getDelivered()+")");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return orderDataList.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView order;
        LinearLayout deliveredDateLayout, reportBtn;
        ImageView prodImage;
        TextView tvOrderNo, tvOrderDate, tvOrderAmount, tvPaymentMethiod, tvDeliveredDate;
        public OrderViewHolder(View itemView){
            super(itemView);
            tvOrderNo= itemView.findViewById(R.id.tv_order_no);
            tvOrderDate= itemView.findViewById(R.id.tv_ordered_date);
            tvOrderAmount= itemView.findViewById(R.id.tv_amount_paid);
            tvPaymentMethiod= itemView.findViewById(R.id.tv_order_payment_method);
            tvDeliveredDate= itemView.findViewById(R.id.tv_delivered_date);
            deliveredDateLayout= itemView.findViewById(R.id.ll_delivered_date);
            reportBtn= itemView.findViewById(R.id.ll_report_btn);
            order= itemView.findViewById(R.id.cvOrderList);
            prodImage= itemView.findViewById(R.id.iv_order_prod_image);
            order.setOnClickListener(this);
            reportBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cvOrderList:
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra(Constants.PreferenceConstants.ORDERID, orderDataList.get(getAdapterPosition()).getOrderId());
                    context.startActivity(intent);
                    break;

                case R.id.ll_report_btn:
                    reportLayoutInterface.openReportPopup(orderDataList.get(getAdapterPosition()).getOrderId());
                    break;
            }
        }
    }
    public interface ReportLayoutInterface{
        public void openReportPopup(String orderId);
    }
}
