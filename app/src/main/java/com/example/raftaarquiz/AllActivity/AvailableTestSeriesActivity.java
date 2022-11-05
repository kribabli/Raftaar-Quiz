package com.example.raftaarquiz.AllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.Model.AvailableTestSeries;
import com.example.raftaarquiz.R;
import com.example.raftaarquiz.Retrofit.ApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableTestSeriesActivity extends AppCompatActivity {
    TextView backPress, examCatName;
    String id = "", examName;
    ArrayList<AvailableTestSeries> list = new ArrayList<>();
    AvailableTestSeriesAdapter availableTestSeriesAdapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_test_series);
        initMethod();
        setAction();
        getAvailableTestSeriesList();
    }

    private void initMethod() {
        backPress = findViewById(R.id.backPress);
        examCatName = findViewById(R.id.eXAMCATEGORIESNAME);
        recyclerView = findViewById(R.id.recycler);
        swipeRefreshLayout = findViewById(R.id.swiper);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }
        examName = getIntent().getStringExtra("examName");
        examCatName.setText(examName);
    }

    private void setAction() {
        backPress.setOnClickListener(view -> onBackPressed());

        swipeRefreshLayout.setOnRefreshListener(() -> getAvailableTestSeriesList());
    }

    private void getAvailableTestSeriesList() {
        list.clear();
        Call<Object> call = ApiClient
                .getInstance()
                .getApi()
                .getAvailableTestSeries(id);

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Object scoreResponse = response.body();
                if (response.isSuccessful()) {
                    list.clear();
                    String message = new Gson().toJson(response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(message);
                        JSONArray message1 = jsonObject.getJSONArray("message");
                        for (int i = 0; i < message1.length(); i++) {
                            JSONObject jsonObject1 = message1.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String category = jsonObject1.getString("category");
                            String Image = jsonObject1.getString("Image");
                            String total = jsonObject1.getString("total");

                            AvailableTestSeries availableTestSeries = new AvailableTestSeries(id, title, category, Image, total);
                            list.add(availableTestSeries);
                        }
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        availableTestSeriesAdapter = new AvailableTestSeriesAdapter(list, getApplicationContext());
                        recyclerView.setAdapter(availableTestSeriesAdapter);
                        availableTestSeriesAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        swipeRefreshLayout.setRefreshing(false);
                        e.printStackTrace();
                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Log.d("TAG", "onFailure: " + t);
            }
        });
    }

    //Adapter for AvailableTestSeries List
    public class AvailableTestSeriesAdapter extends RecyclerView.Adapter<AvailableTestSeriesAdapter.ViewHolder> {
        public List<AvailableTestSeries> list;
        public Context context;

        public AvailableTestSeriesAdapter(ArrayList<AvailableTestSeries> list, Context context) {
            this.list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public AvailableTestSeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_test_series_list, parent, false);
            context = parent.getContext();
            return new AvailableTestSeriesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AvailableTestSeriesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.setIsRecyclable(false);
            holder.testSeriesTitle.setText("" + list.get(position).getTitle());
            holder.noOfTest.setText("" + list.get(position).getTotal() + " Total tests");

            try {
                Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.logo).into(holder.circleImageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MockTestActivity.class);
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("img", list.get(position).getImage());
                    intent.putExtra("title", list.get(position).getTitle());
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView circleImageView;
            TextView testSeriesTitle, noOfTest;
            RelativeLayout relativeLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                circleImageView = itemView.findViewById(R.id.testSeriesImg);
                testSeriesTitle = itemView.findViewById(R.id.testSeriesTitle);
                noOfTest = itemView.findViewById(R.id.noOfTest);
                relativeLayout = itemView.findViewById(R.id.parentRelative);
            }
        }
    }
}