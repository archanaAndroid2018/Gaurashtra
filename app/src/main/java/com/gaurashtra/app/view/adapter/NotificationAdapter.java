package com.gaurashtra.app.view.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.NotificationBean.NotificationDTO;
import com.gaurashtra.app.view.activity.NotificationActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    Context context;
    ArrayList<NotificationDTO.Result.NotificationDatum> notifList;
    NotificationInterface notificationInterface;

    public NotificationAdapter(Activity activity, ArrayList<NotificationDTO.Result.NotificationDatum> notificationList, NotificationActivity notifInterface) {
        this.context= activity;
        this.notifList= notificationList;
        this.notificationInterface = notifInterface;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_notification,parent,false);
        NotificationViewHolder vh= new NotificationViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.tvTitle.setText(notifList.get(position).getNotificationTitle());
        holder.tvContent.setText(notifList.get(position).getNotificationContent());
        holder.tvDate.setText(notifList.get(position).getNotificationDate());

    }

    @Override
    public int getItemCount() {
        return notifList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent, tvDate;
        ImageButton btnDelete;
        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tv_title);
            tvContent= itemView.findViewById(R.id.tv_content);
            tvDate= itemView.findViewById(R.id.tv_date);
            btnDelete = itemView.findViewById(R.id.ibtn_delete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 notificationInterface.deleteNofication(notifList.get(getAdapterPosition()).getNotificationId());
                }
            });
        }
    }
    public interface NotificationInterface{
       void deleteNofication(String notifId);
    }
}
