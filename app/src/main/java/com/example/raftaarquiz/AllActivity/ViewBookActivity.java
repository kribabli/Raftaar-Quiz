package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.raftaarquiz.R;

public class ViewBookActivity extends AppCompatActivity {
    TextView description, bookTitle;
    String getDescription, getBookImg, getBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        description = findViewById(R.id.description);
        bookTitle = findViewById(R.id.bookTitle);

        if (getIntent().hasExtra("bookDescription") || getIntent().hasExtra("image") || getIntent().hasExtra("bookTitle")) {
            getDescription = getIntent().getStringExtra("bookDescription");
            getBookImg = getIntent().getStringExtra("image");
            getBookTitle = getIntent().getStringExtra("bookTitle");

            bookTitle.setText(getBookTitle);
            description.setText(getDescription);
            bookTitle.setSelected(true);
        }
    }
}