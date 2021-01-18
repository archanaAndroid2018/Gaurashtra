package com.gaurashtra.app.view.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.ArrayList;

import com.gaurashtra.app.R;
import com.gaurashtra.app.model.bean.ProductDetails.Image;
import com.gaurashtra.app.view.activity.ProductDetailActivity;

public class ImageAdapter extends PagerAdapter {
    Context context;
    ArrayList<Image> slist ;
    private boolean doNotifyDataSetChangedOnce = false;
    private LayoutInflater inflater;
    int clickedPos=0;
    ProgressBar progressBar;
//    CategoryTopFocus categoryTopFocus;

    public ImageAdapter(ProductDetailActivity productDetailActivity, ArrayList<Image> sliderImageList, int i) {
        this.context = productDetailActivity;
        this.slist = sliderImageList;
        this.clickedPos= i;
        if(context!=null)
            inflater = LayoutInflater.from(context);
        Log.e("Inflatervalue",""+inflater);
    }

    @Override
    public int getCount() {
        if (doNotifyDataSetChangedOnce) {
            doNotifyDataSetChangedOnce = false;
            notifyDataSetChanged();
        }
        Log.e("AdapterListsize",":"+slist.size());
        return slist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View sliderView = inflater.inflate(R.layout.image_slide_layout,container,false);
        WebView webView = sliderView.findViewById(R.id.imageView);
        progressBar= sliderView.findViewById(R.id.progress_bar);

        try{
        final Image data = slist.get(position);
        String imgurl=" ";
        String baseurl= "https://www.gaurashtra.com/image/cache/";
        imgurl = data.getImageUrl().replace(".jpg", "-1100x1100.jpg");
        String img= baseurl+imgurl;
        Log.e("imageToset",":"+img);
        String htmlContent="<html><head><style>img {width: 100%; height: 100%; }</style></head><body><img src="+img+"></body><html>";
        webView.getSettings().setAppCacheEnabled(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//        webView.setInitialScale(1);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadDataWithBaseURL("",htmlContent,"text/html","UTF-8","");

        webView.setWebViewClient(new TumblrWebViewClient(progressBar));
//        Glide.with(context).asBitmap().load(img).into(imageView);
     //   notifyDataSetChanged();
        ((ViewPager) container).addView(sliderView, 0);
        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            e.printStackTrace();
        }
        return sliderView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        doNotifyDataSetChangedOnce = true;
        container.removeView((View) object);
    }

    private class TumblrWebViewClient extends WebViewClient {
        private ProgressBar progressBar;

        public TumblrWebViewClient(ProgressBar pb) {
            this.progressBar=pb;
            pb.setVisibility(View.VISIBLE);
        }
        @Override
        public void onPageCommitVisible (WebView view, String url){
            Log.e("ProgressBy","1:"+ view.getProgress());
//            progressBar.dismiss();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.e("ProgressBy","2:"+ view.getProgress());
            progressBar.setVisibility(View.GONE);
        }
    }
//    public interface CategoryTopFocus{
//        void setFocus(String pid, int position);
//    }

}
