package com.example.raftaarquiz.AllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.raftaarquiz.Model.QuizCategories;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestSeriesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swiper;
    TestSeriesAdapter testSeriesAdapter;
    String testSeriesUrl = "https://adminapp.tech/raftarquiz/userapi/testseries.php";
    ArrayList<QuizCategories> listItems = new ArrayList<>();
    TextView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series);
        initMethod();
        setAction();
        getAllTestSeriesList();
    }

    private void initMethod() {
        recyclerView = findViewById(R.id.recycler);
        swiper = findViewById(R.id.swiper);
        backPress = findViewById(R.id.backPress);
        Animation animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        recyclerView.startAnimation(animSlideDown);
    }

    private void setAction() {
        swiper.setOnRefreshListener(() -> {
            swiper.setRefreshing(false);
            getAllTestSeriesList();
            Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            recyclerView.startAnimation(animSlideDown);
        });

        backPress.setOnClickListener(view -> onBackPressed());
    }

    private void getAllTestSeriesList() {
        listItems.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, testSeriesUrl, response -> {
            try {
                listItems.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("message");
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String Name = jsonObject1.getString("Name");
                        String Image = jsonObject1.getString("Image");

                        QuizCategories quizCategories = new QuizCategories(id, Name, Image, "", "", "");
                        listItems.add(quizCategories);
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(this,2));
                    testSeriesAdapter = new TestSeriesAdapter(listItems);
                    recyclerView.setAdapter(testSeriesAdapter);
                    testSeriesAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    //TestSeriesAdapter for CategoryList
    public class TestSeriesAdapter extends RecyclerView.Adapter<TestSeriesAdapter.ViewHolder> {
        public List<QuizCategories> list;
        public Context context;

        public TestSeriesAdapter(ArrayList<QuizCategories> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public TestSeriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_series_exam_cat, parent, false);
            context = parent.getContext();

            return new TestSeriesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TestSeriesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.setIsRecyclable(false);
            holder.quizTitle.setText("" + list.get(position).getTitle());

            try {
                Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.logo).into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.liner.setOnClickListener(v -> {
                Intent intent = new Intent(context, TestSeriesQuestionActivity.class);
                intent.putExtra("id", list.get(position).getId());
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView imageView;
            TextView quizTitle;
            LinearLayout liner;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.quizImg);
                quizTitle = itemView.findViewById(R.id.quizTitle);
                liner = itemView.findViewById(R.id.LinearLayout2);
            }
        }
    }
}