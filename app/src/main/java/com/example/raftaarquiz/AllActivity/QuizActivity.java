package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raftaarquiz.AllAdapter.QuizAdapter;
import com.example.raftaarquiz.Model.QuizCategories;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QuizActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    SwipeRefreshLayout swiper;
    QuizAdapter quizAdapter;
    String quizUrl = "https://adminapp.tech/raftarquiz/api/exam/categories";
    ArrayList<QuizCategories> listItems = new ArrayList<>();
    TextView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        getAllQuizList();
        initMethod();
        setAction();
    }

    private void initMethod() {
        recyclerView = findViewById(R.id.recycler);
        swiper = findViewById(R.id.swiper);
        backPress = findViewById(R.id.backPress);
        Animation animSlideDown = AnimationUtils.loadAnimation(this,R.anim.slide_down);
        recyclerView.startAnimation(animSlideDown);
    }

    private void setAction() {
        swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swiper.setRefreshing(false);
                getAllQuizList();
                Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_down);
                recyclerView.startAnimation(animSlideDown);


            }
        });

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void getAllQuizList() {
        listItems.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, quizUrl, response -> {
            try {
                listItems.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String title = jsonObject1.getString("title");
                        String image = jsonObject1.getString("image");
                        QuizCategories quizCategories = new QuizCategories(id, title, image);
                        listItems.add(quizCategories);
                    }
                    recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
                    quizAdapter = new QuizAdapter(listItems);
                    recyclerView.setAdapter(quizAdapter);
                    quizAdapter.notifyDataSetChanged();
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
}