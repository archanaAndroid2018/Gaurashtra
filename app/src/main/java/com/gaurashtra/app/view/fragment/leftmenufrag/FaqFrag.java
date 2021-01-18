package com.gaurashtra.app.view.fragment.leftmenufrag;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaurashtra.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FaqFrag extends Fragment {


    public static final String TAG = FaqFrag.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faq, container, false);
    }
}
