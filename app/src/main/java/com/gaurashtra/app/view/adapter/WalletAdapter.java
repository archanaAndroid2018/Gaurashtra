package com.gaurashtra.app.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.PrefClass;
import com.gaurashtra.app.model.bean.GetCurrencyList;
import com.gaurashtra.app.model.bean.WalletResponseModel;
import com.gaurashtra.app.view.activity.WalletActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder> {
    Context context;
    ArrayList<WalletResponseModel.Result.WalletDatum> walletList;
    PrefClass prefClass;
    String currency="", currencySymbol="\u20B9";
    float currencyValue= (float) 1.000;
    Float actualCurrValue;

    public WalletAdapter(Activity walletActivity, ArrayList<WalletResponseModel.Result.WalletDatum> list) {
        this.context = walletActivity;
        this.walletList = list;
    }


    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_row_layout,parent,false);
        WalletViewHolder vh = new WalletViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        prefClass= new PrefClass(context);
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

                holder.tvCurrencySymbol.setText(currencySymbol);
                Double walletAmount= Double.parseDouble(walletList.get(position).getAmount())* currencyValue;
                holder.tvAmount.setText(String.valueOf(walletAmount));
                holder.tvDesc.setText(walletList.get(position).getDescription());
                String unFormatDate, formattedDate = " ";
                Date oldDate=null;
                unFormatDate = walletList.get(position).getDateAdded();
                SimpleDateFormat oldSdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat newSdf = new SimpleDateFormat("MMM dd yyyy");
                try {
                    oldDate = oldSdf.parse(unFormatDate);
                    formattedDate = newSdf.format(oldDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                holder.tvDate.setText(formattedDate);
            }
        }
    }

    @Override
    public int getItemCount() {
        return walletList.size();
    }

    public class WalletViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNo, tvCurrencySymbol, tvAmount, tvDate, tvDesc;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNo = itemView.findViewById(R.id.tv_order_no);
            tvCurrencySymbol = itemView.findViewById(R.id.tv_currency_symbol);
            tvAmount = itemView.findViewById(R.id.tv_wallet_amount);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDesc = itemView.findViewById(R.id.tv_wallet_desc);
        }
    }
}
