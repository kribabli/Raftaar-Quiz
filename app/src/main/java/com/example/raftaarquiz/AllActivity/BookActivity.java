package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raftaarquiz.AllAdapter.BookListAdapter;
import com.example.raftaarquiz.Model.BookListData;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout lyt_not_found;
    ArrayList<BookListData> mListItem;
    String BookListUrl = "https://adminapp.tech/raftarquiz/userapi/book.php";
    BookListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setInit();
    }

    private void setInit() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        lyt_not_found = findViewById(R.id.lyt_not_found);
        mListItem = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getAllBookListData();
    }

    private void getAllBookListData() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(BookActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, BookListUrl, response -> {
            try {
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("message");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String description = jsonObject1.getString("description");
                            String authorname = jsonObject1.getString("authorname");
                            String image = jsonObject1.getString("image");
                            BookListData bookListData = new BookListData(id, title, description, authorname, image);
                            mListItem.add(bookListData);
                        }
                        adapter = new BookListAdapter(mListItem, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        progressBar.setVisibility(View.GONE);
                    } else {
                        lyt_not_found.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                lyt_not_found.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, error -> {
            lyt_not_found.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(stringRequest);
    }
}