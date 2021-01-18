package com.gaurashtra.app.view.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gaurashtra.app.model.bean.ProductBasicDetailsResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.ProductDetails.StaticContentDatum;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {
    private Context context;
    private List<ProductBasicDetailsResult.Result.StaticContentDatum> list;

    public ListAdapter(ProductDetailActivity productDetailActivity, List<ProductBasicDetailsResult.Result.StaticContentDatum> staticData) {
        this.context=productDetailActivity;
        this.list=staticData;
    }

    @Override
    public ListAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detailslist,viewGroup,false);
        MyViewHolder vh=new MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ListAdapter.MyViewHolder myViewHolder, int i) {
        String URL=list.get(i).getImage();
        ImageView view=myViewHolder.image;
        myViewHolder.textheading.setText(list.get(i).getTitle().replace("&amp;","&"));
        myViewHolder.textcontent.setText(list.get(i).getContent().replace("&amp;","&"));
        Picasso.with(context).load(URL).into(view);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textheading,textcontent;
        ImageView image;
        public MyViewHolder( View itemView) {
            super(itemView);

            image=(ImageView)itemView.findViewById(R.id.Image);
            textheading=(TextView)itemView.findViewById(R.id.Text_heading);
            textcontent=(TextView)itemView.findViewById(R.id.Text_content);

        }
    }
}
