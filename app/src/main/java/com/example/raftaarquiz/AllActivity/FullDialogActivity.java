package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.raftaarquiz.R;

public class FullDialogActivity extends AppCompatActivity {
    ImageView sadOrHappyImg;
    TextView totalQuestion, next, rightAns, wrongAns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fulldialog_submit_test);

        sadOrHappyImg = findViewById(R.id.sadOrHappy);
        totalQuestion = findViewById(R.id.totalQuestion);
        rightAns = findViewById(R.id.rightAns);
        wrongAns = findViewById(R.id.wrongAns);
        next = findViewById(R.id.next);
        setAction();
    }

    private void setAction() {
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
}