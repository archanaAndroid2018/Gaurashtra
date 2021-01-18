package com.gaurashtra.app.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gaurashtra.app.R;
import com.gaurashtra.app.Utils.Constants;
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.ContactUsResponse;
import com.gaurashtra.app.model.bean.ContactUsResult;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.LoginActivity;
import com.gaurashtra.app.view.fragment.leftmenufrag.ContactFrag;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends AppCompatActivity {
    TextView tvPhone, tvEmail, tvAttachmentLabel,textviewTitle;
    LinearLayout btnSubmit, phoneLayout;
    EditText etMessage;
    Spinner spinSubjects;
    ImageView ivAttachment;
    ImageButton ibtnDelete;
    String strPhone, strEmail, strReason, strMessage, phoneToDial="+91 9999 399 398";
    String[] subjects= {" Select Subject "," Query "," Feedback "," Complaint "};
    GlobalUtils globalUtils = new GlobalUtils();
    final int BROWSE_IMG =200;
    Bitmap currentBitmap;
    File imageFile;
    Uri fileUri;
    Toolbar toolbar;


    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(ContactUsActivity.this, HomeActivity.class);
        intent.putExtra(Constants.PreferenceConstants.BACKFROM, "CONTACT");
        startActivity(intent);
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        final ActionBar abar = getSupportActionBar();
        Drawable d = getResources().getDrawable(R.drawable.nav);
        abar.setBackgroundDrawable(d);
        abar.setHomeAsUpIndicator(R.drawable.ic_hamburger);
        View viewActionBar = getLayoutInflater().inflate(R.layout.layout_actionbar, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        toolbar = findViewById(R.id.toolbar);
        textviewTitle = viewActionBar.findViewById(R.id.actionbar_textview);
        textviewTitle.setText("Contact Us");
        abar.setCustomView(viewActionBar, params);
        abar.setDisplayShowCustomEnabled(true);
        abar.setDisplayShowTitleEnabled(false);
        abar.setDisplayHomeAsUpEnabled(true);

        tvPhone= findViewById(R.id.tv_contact_phone);
        tvEmail= findViewById(R.id.tv_contact_email);
        tvAttachmentLabel = findViewById(R.id.tv_attach_label);
        ibtnDelete = findViewById(R.id.ibtn_delete);
        spinSubjects= findViewById(R.id.sp_subject);
        ivAttachment = findViewById(R.id.iv_attachment);
        btnSubmit= findViewById(R.id.ll_btn_submit_query);
        phoneLayout = findViewById(R.id.phone_layout);
        etMessage= findViewById(R.id.et_message);
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(ContactUsActivity.this,android.R.layout.simple_spinner_dropdown_item,subjects);
        spinSubjects.setAdapter(spinnerAdapter);
        spinSubjects.setSelection(0);

        spinSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    strReason= subjects[position];
                }else{
                    strReason="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts(
                        "tel", phoneToDial, null));
                startActivity(phoneIntent);
            }
        });

        ivAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                strReason= etReason.getText().toString().trim();
                strMessage= etMessage.getText().toString().trim();
                try{
                    if(SharedPreference.getUserid(ContactUsActivity.this).isEmpty()){
                        Intent intent = new Intent(ContactUsActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }else {
                        if (strReason.isEmpty()) {
                            Toast.makeText(ContactUsActivity.this, "Please write a reason to contact us!", Toast.LENGTH_SHORT).show();
                        } else if (strMessage.isEmpty()) {
                            Toast.makeText(ContactUsActivity.this, "Please write some message!", Toast.LENGTH_SHORT).show();
                        } else {
                            GlobalUtils.showdialog(ContactUsActivity.this);
                            submitQuery();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        ibtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivAttachment.setImageBitmap(null);
                imageFile= null;
                ibtnDelete.setVisibility(View.GONE);
            }
        });
    }
    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ContactUsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            browseFromGallery();
        } else {
            ActivityCompat.requestPermissions( ContactUsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,}, 100);
        }
    }

    private void browseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        ContactUsActivity.this.startActivityForResult(intent, BROWSE_IMG);
//        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        // ******** code for crop image
//        i.putExtra("crop", "true");
//        i.putExtra("aspectX", 100);
//        i.putExtra("aspectY", 100);
//        i.putExtra("outputX", 256);
//        i.putExtra("outputY", 356);
//
//        try {
//
//            i.putExtra("return-data", true);
//            startActivityForResult(
//                    Intent.createChooser(i, "Select Picture"), 0);
//        }catch (ActivityNotFoundException ex){
//            ex.printStackTrace();
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("OnActContactUs", ":" + requestCode + "\n data:" + data + "\n resultcode: " + resultCode);
        if (requestCode == BROWSE_IMG && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Log.w("filePathColoumn", "" + filePathColumn);
            Cursor cursor = ContactUsActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            Log.w("Image cursor", "" + cursor);
            if (cursor == null) {
                selectedImage = data.getData();
                String path = selectedImage.getPath();
                Log.w("ImagePath", "" + path);
                imageFile = new File(path);
                int compressionRatio = 4; //1 == originalImage, 2 = 50% compression, 4=25% compress
                try {
                    currentBitmap = BitmapFactory.decodeFile (imageFile.getPath ());
                    currentBitmap.compress (Bitmap.CompressFormat.JPEG, compressionRatio, new FileOutputStream(imageFile));
                }
                catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file1." + t.toString ());
                    t.printStackTrace ();
                }
            } else {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                Log.w("filepath", "" + filePath);
                cursor.close();
                imageFile = new File(filePath);
                Log.w("File of image", "" + imageFile);
                int compressionRatio = 90; //1 == originalImage, 2 = 50% compression, 4=25% compress
                try {
                    currentBitmap = MediaStore.Images.Media.getBitmap(ContactUsActivity.this.getContentResolver(), data.getData());
                    currentBitmap.compress (Bitmap.CompressFormat.JPEG, compressionRatio, new FileOutputStream(imageFile));
                }
                catch (Throwable t) {
                    Log.e("ERROR", "Error compressing file2." + t.toString ());
                    t.printStackTrace ();
                }
            }
            ibtnDelete.setVisibility(View.VISIBLE);
            ivAttachment.setImageBitmap(currentBitmap);
        }
    }

    private void submitQuery() {
        String strUserName = SharedPreference.getFirstName(ContactUsActivity.this)+" "+SharedPreference.getLastName(ContactUsActivity.this);
        String strUserEmail="";
        if(!SharedPreference.getUserEmail(ContactUsActivity.this).isEmpty()){
            strUserEmail = SharedPreference.getUserEmail(ContactUsActivity.this);
        }else if(!SharedPreference.getSocialEmail(ContactUsActivity.this).isEmpty()){
            strUserEmail = SharedPreference.getSocialEmail(ContactUsActivity.this);
        }
        HashMap<String, RequestBody> params = new HashMap<>();
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), strUserEmail);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), strUserName);
        RequestBody query = RequestBody.create(MediaType.parse("text/plain"), strMessage);
        RequestBody sub = RequestBody.create(MediaType.parse("text/plain"), strReason);

        MultipartBody.Part mpFile = null;
        if(imageFile!= null) {
            RequestBody reqPrescription = RequestBody.create(MediaType.parse("image/*"), imageFile);
            mpFile = MultipartBody.Part.createFormData("imageAttached", imageFile.getPath(), reqPrescription);
            Log.e("FileToReq",":"+reqPrescription+"/n multipart: "+mpFile);
        }
        params.put("email",email);
        params.put("username", name);
        params.put("query", query);
        params.put("subject", sub);
//        params.put("imageAttached", mpFile);
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<ContactUsResponse> call = restInterface.callSubmitQuery(globalUtils.getKey(),globalUtils.getapidate(), params,mpFile);
        call.enqueue(new Callback<ContactUsResponse>() {
            @Override
            public void onResponse(Call<ContactUsResponse> call, Response<ContactUsResponse> response) {
                Log.e("ContResp",":"+new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                GlobalUtils.hidedialog();
                try{
                    if(response != null){
                        GlobalUtils.showToast(ContactUsActivity.this,response.body().getMessage());
                        if(response.body().getSuccess()==1){
                            ContactUsActivity.this.finish();
                        }
                    }else{
                        GlobalUtils.showToast(ContactUsActivity.this,getResources().getString(R.string.label_something_went_wrong));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                Log.e("ExcQuerySubmit",":"+t);
                GlobalUtils.hidedialog();
                GlobalUtils.showToast(ContactUsActivity.this,getResources().getString(R.string.label_something_went_wrong));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ContactUsActivity.this, HomeActivity.class);
        intent.putExtra(Constants.PreferenceConstants.BACKFROM, "CONTACT");
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(GlobalUtils.isNetworkAvailable(ContactUsActivity.this)){
            getContent();
        }else{
            Toast.makeText(ContactUsActivity.this, getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
        }
    }

    private void getContent() {
        RestInterface restInterface = RetrofitSinglton.getClient().create(RestInterface.class);
        Call<ContactUsResult> call = restInterface.getContactUsContent(globalUtils.getKey(), globalUtils.getapidate());
        call.enqueue(new Callback<ContactUsResult>() {
            @Override
            public void onResponse(Call<ContactUsResult> call, Response<ContactUsResult> response) {
                try {
                    Log.e("ContactUsRes", ":" + new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                    if(response.body().getSuccess()==1){

                        strPhone = response.body().getResult().getContactUsContent().getMobile();
                        strEmail =response.body().getResult().getContactUsContent().getEmail();
                        if(strPhone.contains("(")){
                            String[] ph = strPhone.split("\\(");
                            phoneToDial =ph[0];
                        }
                        tvPhone.setText(strPhone);
                        tvEmail.setText(strEmail);
                    }else{}
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ContactUsResult> call, Throwable t) {
                Log.e("ExcContact",":"+t);
            }
        });
    }

}