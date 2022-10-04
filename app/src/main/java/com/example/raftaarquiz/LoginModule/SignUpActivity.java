package com.example.raftaarquiz.LoginModule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.raftaarquiz.Model.RegistrationResponse;
import com.example.raftaarquiz.R;
import com.example.raftaarquiz.Retrofit.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    TextView login;
    Button SignUp;
    EditText userNumber, password, email, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initMethod();
        setAction();
    }

    private void initMethod() {
        login = findViewById(R.id.login);
        SignUp = findViewById(R.id.SignUp);

        userNumber = findViewById(R.id.number);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
    }

    private void setAction() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });
    }

    private boolean validation() {
        boolean isValid = true;
        try {
            if (userName.getText().toString().trim().length() == 0) {
                userName.setError("Please enter user name");
                userName.requestFocus();
                isValid = false;
            } else if (email.getText().toString().trim().length() == 0) {
                email.setError("Please enter email");
                email.requestFocus();
                isValid = false;
            } else if (password.getText().toString().trim().length() == 0) {
                password.setError("Please enter password");
                password.requestFocus();
                isValid = false;
            } else if (userNumber.getText().toString().trim().length() == 0) {
                userNumber.setError("Please enter number");
                userNumber.requestFocus();
                isValid = false;
            } else {
                sendUserData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    private void sendUserData() {
        Log.d("TAG", "sendUserData: " + userName.getText().toString() + email.getText().toString() + userNumber.getText().toString() + password.getText().toString());

        Call<RegistrationResponse> call = ApiClient.getInstance().getApi().
                SendUserDetails_server(userName.getText().toString(), email.getText().toString(), userNumber.getText().toString(), password.getText().toString(), "");
        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                RegistrationResponse registrationResponse = response.body();
                if (response.isSuccessful()) {
                    Log.d("TAG", "onResponse:1 " + response.toString());
                    if (registrationResponse.getResponse().equalsIgnoreCase("User Already exist")) {
                        showDialog("" + registrationResponse.getResponse(), false);
                        Log.d("TAG", "onResponse: " + response.body());
                    }
                    if (registrationResponse.getResponse().equalsIgnoreCase("Registration Successfully")) {
                        showDialog("User Register Successfully..", true);
                        Log.d("TAG", "onResponse:33 " + response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationResponse> call, Throwable t) {
            }
        });
    }

    public void showDialog(String message, Boolean isFinish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", (dialog, id) -> {
            dialog.dismiss();
            if (isFinish) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}