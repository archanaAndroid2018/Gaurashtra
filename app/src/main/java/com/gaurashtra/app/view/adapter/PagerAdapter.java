package com.gaurashtra.app.view.adapter;

import com.gaurashtra.app.view.activity.HomeActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

//import com.gaurashtra.app.view.fragment.catFragment.DantmanjanFrag;
//import com.gaurashtra.app.view.fragment.catFragment.DentalCareFrag;
//import com.gaurashtra.app.view.fragment.catFragment.DeshiCowGheeFrag;
//import com.gaurashtra.app.view.fragment.catFragment.DhoopAgarbattiFrag;
//import com.gaurashtra.app.view.fragment.catFragment.HairFragement;
//import com.gaurashtra.app.view.fragment.catFragment.HairwashSoapFrag;
//import com.gaurashtra.app.view.fragment.catFragment.HavankandeFrag;
//import com.gaurashtra.app.view.fragment.catFragment.HealthCareFragment;
//import com.gaurashtra.app.view.fragment.catFragment.HoneyFrag;
//import com.gaurashtra.app.view.fragment.catFragment.MosquitoFrag;
//import com.gaurashtra.app.view.fragment.catFragment.PoojaSamagriFrag;
//import com.gaurashtra.app.view.fragment.catFragment.ShampooFrag;
//import com.gaurashtra.app.view.fragment.catFragment.SuplimentFrag;
//import com.gaurashtra.app.view.fragment.catFragment.TonicFrag;
//import com.gaurashtra.app.view.fragment.catFragment.ToothpestFrag;
//import com.gaurashtra.app.view.fragment.catFragment.WeightlossFrag;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;
    public PagerAdapter(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {
//        switch (i){
//            case 0:
//                HairFragement hairFragement = new HairFragement();
//                return hairFragement;
//            case 1:
//                HealthCareFragment healthCareFragment = new HealthCareFragment();
//                return healthCareFragment;
//            case 2:
//                PoojaSamagriFrag poojaSamagriFrag = new PoojaSamagriFrag();
//                return poojaSamagriFrag;
//            case 3:
//                DentalCareFrag dentalCareFrag = new DentalCareFrag();
//                return dentalCareFrag;
//            case 4:
//                ToothpestFrag toothpestFrag = new ToothpestFrag();
//                return toothpestFrag;
//            case 5:
//                DantmanjanFrag dantmanjanFrag = new DantmanjanFrag();
//                return dantmanjanFrag;
//            case 6:
//                ShampooFrag shampooFrag = new ShampooFrag();
//                return shampooFrag;
//            case 7:
//                HairwashSoapFrag hairwashSoapFrag = new HairwashSoapFrag();
//                return hairwashSoapFrag;
//            case 8:
//                TonicFrag tonicFrag = new TonicFrag();
//                return tonicFrag;
//            case 9:
//                DeshiCowGheeFrag deshiCowGheeFrag = new DeshiCowGheeFrag();
//                return deshiCowGheeFrag;
//            case 10:
//                HoneyFrag honeyFrag = new HoneyFrag();
//                return honeyFrag;
//            case 11:
//                DhoopAgarbattiFrag dhoopAgarbattiFrag = new DhoopAgarbattiFrag();
//                return dhoopAgarbattiFrag;
//            case 12:
//                MosquitoFrag mosquitoFrag = new MosquitoFrag();
//                return mosquitoFrag;
//            case 13:
//                HavankandeFrag havankandeFrag = new HavankandeFrag();
//                return havankandeFrag;
//            case 14:
//                WeightlossFrag weightlossFrag = new WeightlossFrag();
//                return weightlossFrag;
//            case 15:
//                SuplimentFrag suplimentFrag = new SuplimentFrag();
//                return suplimentFrag;
//            default:
                return null;
//        }
    }


    @Override
    public int getCount() {
        return 16;
    }
}
