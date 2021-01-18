package com.gaurashtra.app.view.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaurashtra.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private static String ARG_IMAGE_RESOURCE = "ARG_IMAGE_RESOURCE";

    public static HomeFragment newInstance(int imgres){
       HomeFragment homeFragment = new HomeFragment();
       Bundle bundle = new Bundle();
       bundle.putInt(ARG_IMAGE_RESOURCE,imgres);
       homeFragment.setArguments(bundle);
       return homeFragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
//        Intent intent = new Intent(getContext(), HomeActivity.class);
//        startActivity(intent);
        return view;
    }

}
