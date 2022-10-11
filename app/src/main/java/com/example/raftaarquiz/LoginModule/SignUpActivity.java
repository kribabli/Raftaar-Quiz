package com.example.raftaarquiz.LoginModule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raftaarquiz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    TextView login;
    Button SignUp;
    EditText userNumber, password, email, userName;
    String url = "https://adminapp.tech/raftarquiz/userapi/register.php";
    ProgressBar progressBar;

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
        progressBar = findViewById(R.id.progressBar);

        userNumber = findViewById(R.id.number);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);
        userName = findViewById(R.id.userName);
        if (getIntent().hasExtra("userEmail") && getIntent().hasExtra("userName")) {
            email.setText("" + getIntent().getStringExtra("userEmail"));
            userName.setText("" + getIntent().getStringExtra("userName"));
        }
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
        progressBar.setVisibility(View.VISIBLE);
        SignUp.setVisibility(View.GONE);

        RequestQueue queue = Volley.newRequestQueue(SignUpActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("message").equalsIgnoreCase("Register Successfully")) {
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(SignUpActivity.this, "" + jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        SignUp.setVisibility(View.VISIBLE);
                    } else {
                        showDialog(jsonObject.getString("message"), false);
                        progressBar.setVisibility(View.GONE);
                        SignUp.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressBar.setVisibility(View.GONE);
                    SignUp.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                SignUp.setVisibility(View.VISIBLE);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", userName.getText().toString());
                params.put("email", email.getText().toString());
                params.put("contact_number", userNumber.getText().toString());
                params.put("password", password.getText().toString());
                params.put("gender", "NA");
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