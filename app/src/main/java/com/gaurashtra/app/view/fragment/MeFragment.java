package com.gaurashtra.app.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.view.activity.MyReviewsActivity;
import androidx.fragment.app.Fragment;
import androidx.cardview.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaurashtra.app.R;
import com.gaurashtra.app.view.activity.AddressActivity;
import com.gaurashtra.app.view.activity.ChangePassActivity;
import com.gaurashtra.app.view.activity.OrdersActivity;
import com.gaurashtra.app.view.activity.ProfileActivity;
import com.gaurashtra.app.view.activity.WalletActivity;
import com.gaurashtra.app.view.activity.WishListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment implements View.OnClickListener {
    private static String ARG_IMAGE_RESOURCE = "ARG_IMAGE_RESOURCE";
    LinearLayout profile,address, orders, wishList, changePassword, Reviewlist, walletLayout;
    TextView tvUsername;
    String firstName = "", lastName = "", username = "";

    public static MeFragment newInstance(int imgres) {
        MeFragment meFragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_IMAGE_RESOURCE, imgres);
        meFragment.setArguments(bundle);
        return meFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        walletLayout = view.findViewById(R.id.wallet_layout);
        profile = view.findViewById(R.id.cvProfile);
        address  = view.findViewById(R.id.cvAddress);
        orders   = view.findViewById(R.id.cvOrders);
        wishList = view.findViewById(R.id.cvWishList);
        changePassword = view.findViewById(R.id.cvChangePass);
        Reviewlist = view.findViewById(R.id.cvReview_list);
        tvUsername= view.findViewById(R.id.tv_username);
        try {
            if (!SharedPreference.getFirstName(getActivity()).isEmpty()) {
                firstName = SharedPreference.getFirstName(getActivity());
                username = firstName.toUpperCase().charAt(0) + firstName.substring(1, firstName.length());
                if (!SharedPreference.getLastName(getActivity()).isEmpty()) {
                    lastName = SharedPreference.getLastName(getActivity());
                    username = firstName.toUpperCase().charAt(0) + firstName.substring(1, firstName.length()) + " " +
                    lastName.charAt(0) + lastName.substring(1, lastName.length());
                }
                tvUsername.setText(username);
            } else {
                tvUsername.setVisibility(View.GONE);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        profile.setOnClickListener(this);
        address.setOnClickListener(this);
        orders.setOnClickListener(this);
        wishList.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        Reviewlist.setOnClickListener(this);
        walletLayout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cvProfile:
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.cvAddress:
                Intent intent1 = new Intent(getContext(), AddressActivity.class);
                startActivity(intent1);
                break;
            case R.id.cvOrders:
                Intent intent2 = new Intent(getContext(), OrdersActivity.class);
                startActivity(intent2);
                break;
            case R.id.cvWishList:
                Intent intent3 = new Intent(getContext(), WishListActivity.class);
                startActivity(intent3);
                break;
            case R.id.cvChangePass:
                Intent intent4 = new Intent(getContext(), ChangePassActivity.class);
                startActivity(intent4);
                break;
            case R.id.cvReview_list:
                Intent intent5 = new Intent(getContext(), MyReviewsActivity.class);
                startActivity(intent5);
                break;
            case R.id.wallet_layout:
                Intent intent6 = new Intent(getContext(), WalletActivity.class);
                startActivity(intent6);
                break;
        }
    }
}

