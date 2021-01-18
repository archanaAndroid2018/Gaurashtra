package com.gaurashtra.app.view.activity;


import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.UserUpdate;
import com.gaurashtra.app.view.fragment.MyReviewFragment;
import com.gaurashtra.app.view.fragment.ToBeReviewFragment;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MyReviewsActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reviews);

        final ActionBar abar = getSupportActionBar();
        Drawable d=getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        TextView textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Reviews");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        UserUpdate userUpdate= new UserUpdate(MyReviewsActivity.this);
        if(!userUpdate.isUserAvailable){
            Toast.makeText(MyReviewsActivity.this, "Your account is not available!", Toast.LENGTH_SHORT).show();
            Intent intentIogout= new Intent(MyReviewsActivity.this,HomeActivity.class);
            startActivity(intentIogout);
        }
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("To be reviewed");
        tabLayout.getTabAt(1).setText("My reviews");
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFragment(getFragment(1), "ToBeReview");
        adapter.addFragment(getFragment(2), "Review");
        viewPager.setAdapter(adapter);
    }

    public Fragment getFragment(int fragmentNo)
    {
        if (fragmentNo==1) {
            Fragment fragment=new ToBeReviewFragment();
            return fragment;
        }else if (fragmentNo==2){
            Fragment fragment=new MyReviewFragment();
            return fragment;
        }
        return null;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
//            tabLayout.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.review_box));
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent= new Intent(MyReviewsActivity.this,HomeActivity.class);
        intent.putExtra("BACKFROM","ME");
        startActivity(intent);

//        new HomeActivity().setMeFragment();
//        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}

