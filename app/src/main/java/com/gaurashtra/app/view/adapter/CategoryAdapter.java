package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.categoryListbean.CategoryDatum;
import com.gaurashtra.app.view.activity.BrandListActivity;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryDataViewHolder> {
    private Context context;
    private List<CategoryDatum> catList = new ArrayList<>();

    public CategoryAdapter(Context context, List<CategoryDatum> catList){
        this.context = context;
        this.catList = catList;
    }

    @NonNull
    @Override
    public CategoryDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.branditems,parent,false);
        CategoryDataViewHolder viewHolder = new CategoryDataViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryDataViewHolder holder,final int position) {
      if (position%2 != 0){
         holder.items.setBackgroundColor(Color.WHITE);
      }

      holder.category.setText(catList.get(position).getName());

      holder.items.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(context, BrandListActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
              intent.putExtra(Constants.PreferenceConstants.CATEGORYNAME,catList.get(position).getName());
              intent.putExtra(Constants.PreferenceConstants.CATEGORYID,catList.get(position).getCategoryId());
              context.startActivity(intent);
          }
      });
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }


    public class CategoryDataViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout items;
        private TextView category;
        public CategoryDataViewHolder(View itemView){
            super(itemView);
            items = itemView.findViewById(R.id.llitems);
            category = itemView.findViewById(R.id.tvCatName);
        }
    }
}
