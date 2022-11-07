package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.BuildConfig;
import com.example.raftaarquiz.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MockTestActivity extends AppCompatActivity {
    TextView backPress, share, mockTestName;
    CircleImageView mockTestImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);
        initMethod();
        setAction();
    }

    private void initMethod() {
        backPress = findViewById(R.id.backPress);
        share = findViewById(R.id.share);
        mockTestImg = findViewById(R.id.mockTestImg);
        mockTestName = findViewById(R.id.mockTestName);
    }

    private void setAction() {
        backPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (getIntent().hasExtra("img")) {
            try {
                Glide.with(this).load(getIntent().getStringExtra("img")).placeholder(R.drawable.logo).into(mockTestImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (getIntent().hasExtra("title")) {
            mockTestName.setText(getIntent().getStringExtra("title"));
        }

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Total Test");
                    String shareMessage = getIntent().getStringExtra("title") + "\n" + "Online Platform for Education" + "\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}