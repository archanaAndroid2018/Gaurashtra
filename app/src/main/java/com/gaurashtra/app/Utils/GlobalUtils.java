package com.gaurashtra.app.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gaurashtra.app.R;


public class GlobalUtils {
    public static String IMAGE_BASE_URL = "https://www.gaurashtra.com/image/cache/";
    public static String STATE_LIST_URL ="https://app.gaurashtra.com/api/getZoneList";
    public static String CATEGORY_LIST_URL = "https://app.gaurashtra.com/api/getProductListByCat";
    String formattedDate, conectstr, api_key;
    String keystr = "Gaurashtra";
    private static ProgressDialog progressDialog;
    static Dialog errorDialog;

    public String getKey() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        formattedDate = df.format(c.getTime());
        conectstr = keystr + formattedDate;
        api_key = convertomd5(conectstr);
        Log.e("APIKey",""+api_key);
        return api_key;
    }

    public String convertomd5(String in) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.reset();
            digest.update(in.getBytes());
            byte[] a = digest.digest();
            int len = a.length;
            StringBuilder sb = new StringBuilder(len << 1);
            for (int i = 0; i < len; i++) {
                sb.append(Character.forDigit((a[i] & 0xf0) >> 4, 16));
                sb.append(Character.forDigit(a[i] & 0x0f, 16));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getapidate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String apdate = df.format(c.getTime());
        return apdate;
    }
    public String getTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String time = df.format(c.getTime());
        return time;
    }
    public static boolean isNetworkAvailable(Activity activity) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public boolean isEmailValidate(String mal) {

        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(mal);
        return matcher.matches();

    }


    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showdialog(Context context){
         progressDialog = new ProgressDialog(context, R.style.StyledDialog);
        progressDialog.show();
    }

    public static void hidedialog(){
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public int randomNumber(){
        Random random = new Random();
        int raNum = random.nextInt(10000000);
        return raNum;
    }

  public static void networkCheckerDialog(Context context){
      new AlertDialog.Builder(context, R.style.CustomDialogTheme)
              .setTitle("Internet Checker")
              .setMessage("Please check your internet connection")
              .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                      Log.d("MainActivity", "Sending atomic bombs to Jupiter");
                  }
              }).show();
  }

  public String urlWithSuffix(String imgUrl){
        imgUrl = imgUrl.replace(".","-300x300.");
        return imgUrl;
    }


//    public static void showDialog(Activity activity, String msg, Boolean cancellable){
//        errorDialog = new Dialog(activity);
//        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        errorDialog.setCancelable(cancellable);
//        errorDialog.setContentView(R.layout.payment_method_error);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            errorDialog.getWindow().setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
//        }
//        TextView tvMsg = progressDialog.findViewById(R.id.tv_msg);
//
//        errorDialog.show();
//    }
//    public static void hideErrordialog(){
//        if(errorDialog != null) {
//            errorDialog.dismiss();
//        }
//    }
}
