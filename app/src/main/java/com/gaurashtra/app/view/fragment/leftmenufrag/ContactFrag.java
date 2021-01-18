package com.gaurashtra.app.view.fragment.leftmenufrag;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.gaurashtra.app.Utils.GlobalUtils;
import com.gaurashtra.app.Utils.SharedPreference;
import com.gaurashtra.app.model.api.RestInterface;
import com.gaurashtra.app.model.api.RetrofitSinglton;
import com.gaurashtra.app.model.bean.ContactUsResponse;
import com.gaurashtra.app.model.bean.ContactUsResult;
import com.gaurashtra.app.view.activity.HomeActivity;
import com.gaurashtra.app.view.activity.LoginActivity;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class ContactFrag extends Fragment {
    public static final String TAG = ContactFrag.class.getSimpleName();
    View view;
    TextView tvPhone, tvEmail, tvAttachmentLabel;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_contact, container, false);

        Activity activity = getActivity();
        if(activity instanceof HomeActivity){
            HomeActivity homeActivity = (HomeActivity) activity;
            homeActivity.hideBottomNav();
        }
        tvPhone= view.findViewById(R.id.tv_contact_phone);
        tvEmail= view.findViewById(R.id.tv_contact_email);
        tvAttachmentLabel = view.findViewById(R.id.tv_attach_label);
        ibtnDelete = view.findViewById(R.id.ibtn_delete);
        spinSubjects= view.findViewById(R.id.sp_subject);
        ivAttachment = view.findViewById(R.id.iv_attachment);
        btnSubmit= view.findViewById(R.id.ll_btn_submit_query);
        phoneLayout = view.findViewById(R.id.phone_layout);
        etMessage= view.findViewById(R.id.et_message);
        ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,subjects);
        spinSubjects.setAdapter(spinnerAdapter);
        spinSubjects.setSelection(0);

        spinSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    strReason= subjects[i];
                }else{
                    strReason="";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                    if(SharedPreference.getUserid(getActivity()).isEmpty()){
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }else {
                        if (strReason.isEmpty()) {
                            Toast.makeText(getActivity(), "Please write a reason to contact us!", Toast.LENGTH_SHORT).show();
                        } else if (strMessage.isEmpty()) {
                            Toast.makeText(getActivity(), "Please write some message!", Toast.LENGTH_SHORT).show();
                        } else {
                            GlobalUtils.showdialog(getActivity());
                            submitQuery();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            browseFromGallery();
        } else {
            ActivityCompat.requestPermissions( getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION,}, 100);
        }
    }

    private void browseFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        getActivity().startActivityForResult(intent, BROWSE_IMG);
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        Log.e("OnActContactUs", ":" + requestCode + "\n data:" + data + "\n resultcode: " + resultCode);
        if (requestCode == BROWSE_IMG) {
            Activity activity = getActivity();
            if (activity instanceof HomeActivity) {
                HomeActivity homeActivity = (HomeActivity) activity;
                homeActivity.loadFragment(new ContactFrag(), ContactFrag.class.getSimpleName());;
                try {
                    android.net.Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Log.w("filePathColoumn", "" + filePathColumn);
                    android.database.Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    Log.w("Image cursor", "" + cursor);
                    if (cursor == null) {
                        selectedImage = data.getData();
                        String path = selectedImage.getPath();
                        Log.w("ImagePath", "" + path);
                        imageFile = new File(path);
                        currentBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    } else {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        Log.w("filepath", "" + filePath);
                        cursor.close();
                        imageFile = new File(filePath);
                        Log.w("File of image", "" + imageFile);
                        currentBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                    }
                    ivAttachment.setImageBitmap(currentBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

     private void submitQuery() {
        String strUserName = SharedPreference.getFirstName(getActivity())+" "+SharedPreference.getLastName(getActivity());
        String strUserEmail="";
        if(!SharedPreference.getUserEmail(getActivity()).isEmpty()){
            strUserEmail = SharedPreference.getUserEmail(getActivity());
        }else if(!SharedPreference.getSocialEmail(getActivity()).isEmpty()){
            strUserEmail = SharedPreference.getSocialEmail(getActivity());
        }
        HashMap<String, RequestBody> params = new HashMap<>();
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), strUserEmail);
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), strMessage);
        RequestBody query = RequestBody.create(MediaType.parse("text/plain"), strUserName);
        RequestBody sub = RequestBody.create(MediaType.parse("text/plain"), strReason);

        MultipartBody.Part mpFile = null;
        if(imageFile!= null) {
            RequestBody reqPrescription = RequestBody.create(MediaType.parse("image/*"), imageFile);
            mpFile = MultipartBody.Part.createFormData("imageAttached", imageFile.getPath(), reqPrescription);
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

                        GlobalUtils.showToast(getActivity(),response.body().getMessage());
                        if(response.body().getSuccess()==1){
                            getActivity().finish();
                        }
                    }else{
                        GlobalUtils.showToast(getActivity(),getResources().getString(R.string.label_something_went_wrong));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ContactUsResponse> call, Throwable t) {
                GlobalUtils.hidedialog();
                GlobalUtils.showToast(getActivity(),getResources().getString(R.string.label_something_went_wrong));

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(GlobalUtils.isNetworkAvailable(getActivity())){
            getContent();
        }else{
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onPause() {
        super.onPause();
        Log.e("Fragment Cycle","PAUSE");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.e("Fragment Cycle","STOP");
    }
    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("Fragment Cycle","DETACH");
    }
}
