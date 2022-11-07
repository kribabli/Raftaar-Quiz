package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raftaarquiz.Common.HelperData;
import com.example.raftaarquiz.Model.ScoreResponse;
import com.example.raftaarquiz.R;
import com.example.raftaarquiz.Retrofit.ApiClient;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullDialogActivity1 extends AppCompatActivity {
    ImageView sad_emoji, happy_emoji;
    TextView totalQuestion, next, rightAns, wrongAns;
    int totalQuestions = 0, score, totalRightAns;
    HelperData helperData;
    String id, scores;
    int totalRightAns1, totalWrongAns, totalWrongAns1, scores1;
    TextView yourScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_dialog1);

        yourScore = findViewById(R.id.yourScore);
        happy_emoji = findViewById(R.id.happy_emoji);
        sad_emoji = findViewById(R.id.sad_emoji);
        totalQuestion = findViewById(R.id.totalQuestion);
        rightAns = findViewById(R.id.rightAns);
        wrongAns = findViewById(R.id.wrongAns);
        next = findViewById(R.id.next);

        helperData = new HelperData(getApplicationContext());
        id = helperData.getUserId();
        scores = getIntent().getStringExtra("rightCount");
        setAction();
        sendScoreOnServer();
    }

    private void setAction() {
        //for happy_emoji or sad_emoji
        totalQuestions = Integer.parseInt(getIntent().getStringExtra("totalQuestion"));
        score = totalQuestions / 2;
        totalRightAns = Integer.parseInt(getIntent().getStringExtra("rightCount"));

        if (totalRightAns > score) {
            happy_emoji.setVisibility(View.VISIBLE);
        } else {
            sad_emoji.setVisibility(View.VISIBLE);
        }

        //your score..
        totalWrongAns = Integer.parseInt(getIntent().getStringExtra("wrongCount"));
        totalRightAns1 = (totalRightAns * 2);
        totalWrongAns1 = totalWrongAns * (-1);
        yourScore.setText("Your Score : " + ((totalRightAns1) + (totalWrongAns1)));
        scores1 = ((totalRightAns1) + (totalWrongAns1));

        rightAns.setText(getIntent().getStringExtra("rightCount"));
        wrongAns.setText(getIntent().getStringExtra("wrongCount"));
        totalQuestion.setText(getIntent().getStringExtra("totalQuestion"));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void sendScoreOnServer() {
        Call<ScoreResponse> call = ApiClient
                .getInstance()
                .getApi()
                .SendUserScoreOnServer(id, String.valueOf(scores1));

        call.enqueue(new Callback<ScoreResponse>() {
            @Override
            public void onResponse(Call<ScoreResponse> call, Response<ScoreResponse> response) {
                ScoreResponse scoreResponse = response.body();
                if (response.isSuccessful()) {
                    String status = new Gson().toJson(response.body());
                    if (scoreResponse.getMessage().equalsIgnoreCase(" Score Added Successfully")) {
                        Toast.makeText(FullDialogActivity1.this, "Your Score Update on LeaderBoard", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ScoreResponse> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
            }
        });
    }
}