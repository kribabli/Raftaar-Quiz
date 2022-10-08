package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.AllAdapter.QuestionNoAdapter;
import com.example.raftaarquiz.Model.QuestionsReq;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionsActivity extends AppCompatActivity {
    ImageView heart_img;
    TextView question_txt, option_a_txt, option_b_txt, option_c_txt, option_d_txt, score_txt;
    ImageView image_option_a, image_option_b, image_option_c, image_option_d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        heart_img = findViewById(R.id.heart_img);
        score_txt = findViewById(R.id.score_txt);
        option_a_txt = findViewById(R.id.option_a_txt);
        option_b_txt = findViewById(R.id.option_b_txt);
        option_c_txt = findViewById(R.id.option_c_txt);
        option_d_txt = findViewById(R.id.option_d_txt);
        question_txt = findViewById(R.id.question_txt);

        image_option_a = findViewById(R.id.image_option_a);
        image_option_b = findViewById(R.id.image_option_b);
        image_option_c = findViewById(R.id.image_option_c);
        image_option_d = findViewById(R.id.image_option_d);

    }

}