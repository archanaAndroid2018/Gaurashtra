package com.gaurashtra.app.view.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.model.bean.HomePagebean.CategoryDatum;
import com.gaurashtra.app.view.activity.BrandListActivity;
import com.gaurashtra.app.view.activity.HomeActivity;

public class SecondHorizontalHomeAdapter extends RecyclerView.Adapter<SecondHorizontalHomeAdapter.SecondViewHolder> {
   private Context context;
    private List<CategoryDatum> mcatlist=new ArrayList<>();
    int position;

    public SecondHorizontalHomeAdapter(HomeActivity homeActivity, List<CategoryDatum> category) {
       this.context=homeActivity;
       this.mcatlist=category;

    }

    @NonNull
    @Override
    public SecondViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.secondlistitems,parent,false);
       SecondViewHolder viewHolder = new SecondViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SecondViewHolder secondViewHolder, int i) {
       ImageView view=secondViewHolder.imageView;
       final String catId=mcatlist.get(i).getCategoryId();
       final String name=mcatlist.get(i).getName();
       String imageurl=mcatlist.get(i).getCatImage();
       secondViewHolder.textView.setText(mcatlist.get(i).getName());
       secondViewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(position==0){{
                   Intent i=new Intent(context, BrandListActivity.class);
                   i.putExtra(Constants.PreferenceConstants.CATEGORYID,""+catId);
                   i.putExtra(Constants.PreferenceConstants.CATEGORYNAME,""+name);
                   context.startActivity(i);
               }

               }

           }
       });
       if (imageurl.length() != 0){
           Glide.with(context).load(imageurl).diskCacheStrategy(DiskCacheStrategy.RESOURCE).thumbnail(0.1f).placeholder(R.drawable.img_icon).fitCenter().into(view);
//           Picasso.with(context).load(imageurl).placeholder(R.drawable.fooditems).into(view);
       }
    }

    @Override
    public int getItemCount() {
        return mcatlist.size();
    }


    public class SecondViewHolder extends RecyclerView.ViewHolder{

       TextView textView;
       LinearLayout linearLayout;
       ImageView imageView;

       public SecondViewHolder(View itemView){
           super(itemView);

           linearLayout=(LinearLayout)itemView.findViewById(R.id.Linera);
           textView=(TextView)itemView.findViewById(R.id.tvMerchant);
           imageView=(ImageView) itemView.findViewById(R.id.Image);
       }
    }
}
