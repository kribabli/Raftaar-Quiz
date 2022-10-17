package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.raftaarquiz.R;

public class ViewCurrentAffairsActivity extends AppCompatActivity {
    TextView description, bookTitle;
    String getDescription, getId, getTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_affairs);
        description = findViewById(R.id.description);
        bookTitle = findViewById(R.id.bookTitle);

        if (getIntent().hasExtra("description") || getIntent().hasExtra("id") || getIntent().hasExtra("title")) {
            getDescription = getIntent().getStringExtra("description");
            getId = getIntent().getStringExtra("id");
            getTitle = getIntent().getStringExtra("title");

            bookTitle.setText(getTitle);
            description.setText(getDescription);
            bookTitle.setSelected(true);
        }
    }
}