package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.raftaarquiz.R;

public class ViewBookActivity extends AppCompatActivity {
    TextView description;
    String getDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        description = findViewById(R.id.description);
        getDescription = getIntent().getStringExtra("bookDescription");
        description.setText(getDescription);
    }
}