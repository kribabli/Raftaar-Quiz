package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AllWebViewActivity extends AppCompatActivity {
    WebView webView;
    String urlJobAlert = "https://adminapp.tech/raftarquiz/userapi/jobalert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_web_view);
        webView = findViewById(R.id.allWebView);
        getResponseJobAlert();
    }

    private void getResponseJobAlert() {
        RequestQueue queue = Volley.newRequestQueue(AllWebViewActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlJobAlert, response -> {
            try {
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String Type = jsonObject1.getString("Type");
                        String Url = jsonObject1.getString("Url");
                        //set the WebView.
                        webView.setWebViewClient(new WebViewClient());
                        webView.loadUrl(Url);
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("types", getIntent().getStringExtra("types"));
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}