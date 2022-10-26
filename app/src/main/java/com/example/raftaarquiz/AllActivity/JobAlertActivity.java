package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.raftaarquiz.R;

public class JobAlertActivity extends AppCompatActivity {
    LinearLayout latest_job_liner, admission_liner, all_india_liner;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView backPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_alert);
        Animation animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        backPress = findViewById(R.id.backPress);

        latest_job_liner = findViewById(R.id.latest_job_liner);
        admission_liner = findViewById(R.id.admission_liner);
        all_india_liner = findViewById(R.id.all_india_liner);

        latest_job_liner.startAnimation(animSlideDown);
        admission_liner.startAnimation(animSlideDown);
        all_india_liner.startAnimation(animSlideDown);

        latest_job_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobAlertActivity.this, AllWebViewActivity.class);
                intent.putExtra("types", "1");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        admission_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobAlertActivity.this, AllWebViewActivity.class);
                intent.putExtra("types", "2");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        all_india_liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(JobAlertActivity.this, AllWebViewActivity.class);
                intent.putExtra("types", "3");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                latest_job_liner.startAnimation(animSlideDown);
                admission_liner.startAnimation(animSlideDown);
                all_india_liner.startAnimation(animSlideDown);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}