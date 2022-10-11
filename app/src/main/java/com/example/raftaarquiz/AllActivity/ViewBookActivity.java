package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.R;

public class ViewBookActivity extends AppCompatActivity {
    TextView description, bookTitle;
    String getDescription, getBookImg, getBookTitle;
    ImageView bookImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);
        description = findViewById(R.id.description);
        bookTitle = findViewById(R.id.bookTitle);
        bookImg = findViewById(R.id.bookImg);

        getDescription = getIntent().getStringExtra("bookDescription");
        getBookImg = getIntent().getStringExtra("image");
        getBookTitle = getIntent().getStringExtra("bookTitle");

        Glide.with(this)
                .load(getBookImg)
                .into(bookImg);
        bookTitle.setText(getBookTitle);
        description.setText(getDescription);

    }
}