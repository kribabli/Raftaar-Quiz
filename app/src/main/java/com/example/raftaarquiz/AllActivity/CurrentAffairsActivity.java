package com.example.raftaarquiz.AllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raftaarquiz.Common.DataBaseHelper;
import com.example.raftaarquiz.Model.BookListData;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentAffairsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressBar progressBar;
    LinearLayout lyt_not_found;
    ArrayList<BookListData> list;
    String currentAffairsUrl = "https://adminapp.tech/raftarquiz/api/Current_affairs/";
    CurrentAffairsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_affairs);
        initMethod();
        getAllBookListData();
    }

    private void initMethod() {
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        lyt_not_found = findViewById(R.id.lyt_not_found);
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getAllBookListData() {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(CurrentAffairsActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, currentAffairsUrl, response -> {
            try {
                if (response != null) {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String description = jsonObject1.getString("description");
                            String date = jsonObject1.getString("date");

                            BookListData bookListData = new BookListData(id, title, description, date, "");
                            list.add(bookListData);
                        }
                        adapter = new CurrentAffairsAdapter(list, getApplicationContext());
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

    //Adapter for CurrentAffairs
    public class CurrentAffairsAdapter extends RecyclerView.Adapter<CurrentAffairsAdapter.MyViewHolder> {
        List<BookListData> list;
        Context context;
        DataBaseHelper dataBaseHelper;

        public CurrentAffairsAdapter(ArrayList<BookListData> list, Context context) {
            this.list = list;
            this.context = context;
            dataBaseHelper = new DataBaseHelper(context);
        }

        @NonNull
        @Override
        public CurrentAffairsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_current_affairs, parent, false);
            return new CurrentAffairsAdapter.MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CurrentAffairsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.positionTV.setText("" + (position + 1));
            holder.titleTV.setText(list.get(position).getTitle());

            holder.relativeLayout.setOnClickListener(view -> {
                Intent intent = new Intent(context, ViewCurrentAffairsActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("description", list.get(position).getDescription());
                intent.putExtra("title", list.get(position).getTitle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView titleTV, positionTV;
            LinearLayout relativeLayout;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                positionTV = itemView.findViewById(R.id.positionTV);
                titleTV = itemView.findViewById(R.id.titleTV);
                relativeLayout = itemView.findViewById(R.id.relativeLayout);
            }
        }
    }
}