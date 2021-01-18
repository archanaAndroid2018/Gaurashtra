package com.gaurashtra.app.view.base;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.gaurashtra.app.R;

public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
    }
}
