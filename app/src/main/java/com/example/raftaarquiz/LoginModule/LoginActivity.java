package com.example.raftaarquiz.LoginModule;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raftaarquiz.MainActivity;
import com.example.raftaarquiz.Common.HelperData;
import com.example.raftaarquiz.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    TextView signUp;
    ImageView googleLogo;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    String url = "https://adminapp.tech/raftarquiz/all_apis.php?func=google_login";
    String userName, userEmail;
    ProgressBar progressBar;
    String Login_url = "https://adminapp.tech/raftarquiz/userapi/login.php";
    Button loginBtn;
    EditText email, password1;
    HelperData helperData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initMethod();
        setAction();
    }

    private void initMethod() {
        signUp = findViewById(R.id.signUp);
        progressBar = findViewById(R.id.progressBar);
        googleLogo = findViewById(R.id.googleLogo);
        loginBtn = findViewById(R.id.loginBtn);
        password1 = findViewById(R.id.password1);
        email = findViewById(R.id.email);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        helperData = new HelperData(getApplicationContext());
    }

    private void setAction() {
        googleLogo.setOnClickListener(v -> googleSignIn());

        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> validation());
    }

    private boolean validation() {
        boolean isValid = true;
        if (email.getText().toString().trim().length() == 0) {
            email.setError("Please enter user name");
            email.requestFocus();
            isValid = false;
        } else if (password1.getText().toString().trim().length() == 0) {
            password1.setError("Please enter email");
            password1.requestFocus();
            isValid = false;
        } else {
            loginUserDeatil();
        }
        return isValid;
    }

    private void loginUserDeatil() {
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);
        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Login_url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("message").equalsIgnoreCase("Login Sucessfully")) {
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    helperData.saveLogin(jsonObject1.getString("id"), jsonObject1.getString("Name"), jsonObject1.getString("Email"), jsonObject1.getString("Mobile"));
                    progressBar.setVisibility(View.GONE);
                    loginBtn.setVisibility(View.VISIBLE);
                    helperData.saveIsLogin(true);
                    Toast.makeText(LoginActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                loginBtn.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Somethings went wrongs..", Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
            Toast.makeText(LoginActivity.this, "Somethings went wrongs..", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email.getText().toString());
                params.put("password", password1.getText().toString());
                return params;
            }
        };
        queue.add(stringRequest);

    }

    private void googleSignIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                googleSignInVerification();
            } catch (Exception e) {
                Toast.makeText(this, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void googleSignInVerification() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            userName = googleSignInAccount.getDisplayName();
            userEmail = googleSignInAccount.getEmail();
            Uri uri = googleSignInAccount.getPhotoUrl();
        }

        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getString("status").equalsIgnoreCase("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        String id = jsonObject1.getString("id");
                        String name = jsonObject1.getString("name");
                        String contact_number = jsonObject1.getString("contact_number");
                        String email = jsonObject1.getString("email");
                        helperData.saveLogin(id, name, email, contact_number);
                        helperData.saveIsLogin(true);
                    }
                    Toast.makeText(LoginActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (jsonObject.getString("status").equalsIgnoreCase("False")) {
                    if (jsonObject.getString("message").equalsIgnoreCase("Invalid user ")) {
                        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                        startActivity(intent);
                        finish();
                        gsc.signOut();
                        Toast.makeText(LoginActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(LoginActivity.this, "Somethings Went Wrong....", Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", userEmail);
                return params;
            }
        };
        queue.add(stringRequest);
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