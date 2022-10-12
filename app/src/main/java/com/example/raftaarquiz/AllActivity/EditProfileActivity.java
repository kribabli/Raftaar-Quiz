package com.example.raftaarquiz.AllActivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.raftaarquiz.Common.HelperData;
import com.example.raftaarquiz.Model.ProfileResponse;
import com.example.raftaarquiz.R;
import com.example.raftaarquiz.Retrofit.ApiClient;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.github.dhaval2404.imagepicker.util.FileUriUtils;
import com.google.gson.Gson;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {
    CircleImageView profilePic;
    EditText username_edt;
    EditText mobile_no;
    EditText email_edt, password;
    Button saveBtn;
    RadioButton rb_male, rb_female;
    HelperData helperData;
    ProgressBar progress_circular;
    String code = "";
    String profileImagePath = "";
    Bitmap bitmap;
    String gender = "NA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setHooks();
        handleActionMethod();
    }

    private void handleActionMethod() {
        profilePic.setOnClickListener(view -> {
            code = "profileImage";
            ImagePicker.with(EditProfileActivity.this)
                    .crop(15f, 9f)
                    .start();
        });

        saveBtn.setOnClickListener(view -> {
            validationFromBox();
        });
    }

    private void setHooks() {
        helperData = new HelperData(getApplicationContext());
        profilePic = findViewById(R.id.profilePic);
        username_edt = findViewById(R.id.username_edt);
        mobile_no = findViewById(R.id.mobile_no);
        email_edt = findViewById(R.id.email_edt);
        saveBtn = findViewById(R.id.saveBtn);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        password = findViewById(R.id.password);
        progress_circular = findViewById(R.id.progress_circular);

        username_edt.setText("" + helperData.getUserName());
        mobile_no.setText("" + helperData.getMobile());
        email_edt.setText("" + helperData.getUserEmail());
    }

    private boolean validationFromBox() {
        boolean isValid = true;
        if (username_edt.getText().toString().trim().length() == 0) {
            username_edt.setError("Please enter your username");
            username_edt.requestFocus();
            isValid = false;
        } else if (mobile_no.toString().trim().length() == 0 && mobile_no.toString().trim().length() == 10) {
            mobile_no.setError("Please enter your mobile");
            mobile_no.requestFocus();
            isValid = false;
        } else if (email_edt.toString().trim().length() == 0) {
            email_edt.setError("Please enter your email");
            email_edt.requestFocus();
            isValid = false;
        } else if (password.toString().trim().length() == 0) {
            password.setError("Please enter valid password");
            password.requestFocus();
            isValid = false;
        } else if (bitmap == null) {
            Toast.makeText(this, "Please select your Profile Image", Toast.LENGTH_SHORT).show();
            isValid = false;
        } else {
            updateProfileData();
        }
        return isValid;
    }

    private void updateProfileData() {
        progress_circular.setVisibility(View.VISIBLE);
        saveBtn.setVisibility(View.GONE);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), helperData.getUserId());
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), username_edt.getText().toString());
        RequestBody requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_no.getText().toString());
        RequestBody requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), email_edt.getText().toString());
        RequestBody requestBody6 = RequestBody.create(MediaType.parse("multipart/form-data"), password.getText().toString());
        RequestBody requestBody4 = RequestBody.create(MediaType.parse("multipart/form-data"), gender);

        MultipartBody.Part fileToUpload1 = null;
        File myFile1 = new File(profileImagePath);

        okhttp3.RequestBody requestBody5 = okhttp3.RequestBody.create(okhttp3.MediaType.parse("*/*"), myFile1);
        fileToUpload1 = MultipartBody.Part.createFormData("image_url", myFile1.getName(), requestBody5);

        Call<ProfileResponse> call = ApiClient.getInstance().getApi().UpdateProfileData(requestBody, requestBody1, requestBody2, requestBody3, requestBody6, fileToUpload1, requestBody4);

        call.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                ProfileResponse profileResponse = response.body();
                if (response.isSuccessful()) {
                    String categoryData = new Gson().toJson(response.body());
                    if (profileResponse.getStatus().equalsIgnoreCase("false")) {
                        progress_circular.setVisibility(View.GONE);
                        saveBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(EditProfileActivity.this, "Somethings went wrong..", Toast.LENGTH_SHORT).show();
                    } else if (profileResponse.getStatus().equalsIgnoreCase("true")) {
                        progress_circular.setVisibility(View.GONE);
                        saveBtn.setVisibility(View.VISIBLE);
                        Toast.makeText(EditProfileActivity.this, "" + profileResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                progress_circular.setVisibility(View.GONE);
                saveBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (code.equalsIgnoreCase("profileImage")) {
                Uri filePath = data.getData();
                try {
                    profileImagePath = FileUriUtils.INSTANCE.getRealPath(this, data.getData());
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                    profilePic.setImageURI(filePath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}