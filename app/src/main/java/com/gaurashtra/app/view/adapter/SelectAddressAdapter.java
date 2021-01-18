package com.gaurashtra.app.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.addresslistbean.UserAddressDatum;
import com.gaurashtra.app.view.activity.AddressActivity;

public class SelectAddressAdapter extends RecyclerView.Adapter<SelectAddressAdapter.AddressViewHkolder> {
   private Context context;
   private List<UserAddressDatum> addressList = new ArrayList<>();
   DeleteEditAddressInterface deleteEditAddress;
    String USERId,Address_Id;
    private int lastCheckedPosition = -1;
    Boolean onBind= false, editMode= false;
    HashMap<Integer, Boolean> checkedMap;

    public SelectAddressAdapter(Context context, List<UserAddressDatum> addressList, AddressActivity addressActivity, Boolean isProceedToDelivery){
       this.context = context;
       this.addressList = addressList;
       this.deleteEditAddress=addressActivity;
   }

    @NonNull
    @Override
    public AddressViewHkolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
       View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.addselecteditem,parent,false);
       AddressViewHkolder viewHkolder = new AddressViewHkolder(view);
        return viewHkolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AddressViewHkolder holder, final int position) {
        onBind= true;
        final UserAddressDatum datum = addressList.get(position);
        String fname = datum.getFirstname();
        String lname = datum.getLastname();
        String address1 = datum.getAddress1();
        String address2 = datum.getAddress2();
        String city  = datum.getCity();
        String state = datum.getZoneName();
        String country = datum.getCountryName();
        String pin   = datum.getPostcode();
        String fulladdress = address1+", "+address2+", "+city+", "+state+", "+pin+", "+country;

        holder.selectedAddress.setId(position);
        holder.selectedAddress.setChecked(position == lastCheckedPosition);
        holder.address.setText(fulladdress);
        holder.fullName.setText(fname+" "+lname);
        holder.phone.setText(datum.getCustomField());

        if(datum.getAddressId().equalsIgnoreCase(datum.getDefaultAddressId())){
            holder.defaultAddressText.setVisibility(View.VISIBLE);
            holder.DeleteAddress.setVisibility(View.GONE);
        }

        holder.DeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode=true;
                USERId=datum.getCustomerId();
                Address_Id=datum.getAddressId();
                holder.selectedAddress.setChecked(true);
                deleteEditAddress.DeleteAdd(USERId,Address_Id);
                Log.e("AddressId",""+Address_Id);
            }
        });
        holder.EditAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMode=true;
                holder.selectedAddress.setChecked(true);
                Address_Id=datum.getAddressId();
                deleteEditAddress.EditAdd(position,datum);
            }
        });
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.selectedAddress.setChecked(true);
            }
        });
        onBind= false;
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class AddressViewHkolder extends RecyclerView.ViewHolder  {
        private TextView fullName,address,phone,DeleteAddress, EditAddress, defaultAddressText;
        RadioButton selectedAddress;
        LinearLayout mainLayout;
        CardView itemCard;
        public AddressViewHkolder(final View itemView){
            super(itemView);
            fullName = itemView.findViewById(R.id.tvFullName);
            address  = itemView.findViewById(R.id.tvAddress);
            phone    = itemView.findViewById(R.id.tvPhone);
            DeleteAddress=itemView.findViewById(R.id.Text_Delete);
            EditAddress= itemView.findViewById(R.id.tv_edit_address);
            selectedAddress= itemView.findViewById(R.id.radio_address);
            defaultAddressText= itemView.findViewById(R.id.tv_def_address_text);
            mainLayout= itemView.findViewById(R.id.main_ll);
            itemCard= itemView.findViewById(R.id.cardVideo);

            selectedAddress.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    Log.e("OnSelectAdd",""+buttonView.getId()+"\n "+isChecked);
                    if (!onBind && isChecked) {
                        int copyOfLastCheckedPosition = lastCheckedPosition;
                        lastCheckedPosition = getAdapterPosition();
                        notifyItemChanged(copyOfLastCheckedPosition);
                        notifyItemChanged(lastCheckedPosition);
                        checkedMap= new HashMap<>();
                        checkedMap.put(lastCheckedPosition, isChecked);
                        deleteEditAddress.selectedAddress(lastCheckedPosition, checkedMap);
                    }
                }
            });
        }
    }

    public interface DeleteEditAddressInterface {
       void selectedAddress(int position, HashMap<Integer, Boolean> map);
       void DeleteAdd(String uid, String addressid);
       void EditAdd(int pos,UserAddressDatum datum);
    }
}
