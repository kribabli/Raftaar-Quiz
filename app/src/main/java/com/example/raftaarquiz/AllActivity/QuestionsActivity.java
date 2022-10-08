package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.raftaarquiz.Model.QuestionsList;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QuestionsActivity extends AppCompatActivity {
    ImageView heart_img;
    TextView question_txt, option_a_txt, option_b_txt, option_c_txt, option_d_txt, score_txt;
    ImageView image_option_a, image_option_b, image_option_c, image_option_d;
    String questionsUrl = "https://adminapp.tech/raftarquiz/api/exam/questions/";
    String id;
    int index = 0;
    int correctCount = 0;
    int wrongCount = 0;
    QuestionsList questionsList;
    TextView submitBtn;
    ArrayList<QuestionsList> list = new ArrayList<>();
    ArrayList<QuestionsList> listOfQ = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        initMethod();
        setAction();
        getAllQuestionsList();
    }

    private void initMethod() {
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

        submitBtn = findViewById(R.id.submitBtn);
    }

    private void setAction() {
        id = getIntent().getStringExtra("id");
    }

    private void getAllQuestionsList() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, questionsUrl + id, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String type = jsonObject1.getString("type");
                        String category = jsonObject1.getString("category");
                        String question = jsonObject1.getString("question");
                        String question_image = jsonObject1.getString("question_image");
                        String ans_type = jsonObject1.getString("ans_type");
                        String correct_ans = jsonObject1.getString("correct_ans");

                        String ans1 = jsonObject1.getString("ans1");
                        String ans2 = jsonObject1.getString("ans2");
                        String ans3 = jsonObject1.getString("ans3");
                        String ans4 = jsonObject1.getString("ans4");
                        questionsList = new QuestionsList(question, ans1, ans2, ans3, ans4, correct_ans);
                        listOfQ.add(questionsList);
                    }
                    setAllQuestion();
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

    public void setAllQuestion() {
        question_txt.setText(questionsList.getQuestions());
        option_a_txt.setText(questionsList.getoA());
        option_b_txt.setText(questionsList.getoB());
        option_c_txt.setText(questionsList.getoC());
        option_d_txt.setText(questionsList.getoD());
    }

    public void Correct(TextView option_a_txt) {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                correctCount++;
                index++;
                questionsList = list.get(index);
                setAllQuestion();
            }
        });
    }

    public void Wrong(TextView option_a_txt) {
        option_a_txt.setBackgroundColor(getResources().getColor(R.color.red));
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrongCount++;
                if (index < list.size() - 1) {
                    index++;
                    questionsList = list.get(index);
                    setAllQuestion();
                    resetColor();
                } else {
                    GameWon();
                }
            }
        });
    }

    private void GameWon() {
        Intent intent = new Intent(QuestionsActivity.this, WonActivity.class);
        intent.putExtra("correctCount", correctCount);
        intent.putExtra("wrongCount", wrongCount);
        startActivity(intent);
    }

    public void enableButton() {
        option_a_txt.setClickable(true);
        option_b_txt.setClickable(true);
        option_c_txt.setClickable(true);
        option_d_txt.setClickable(true);
    }

    public void disableButton() {
        option_a_txt.setClickable(false);
        option_b_txt.setClickable(false);
        option_c_txt.setClickable(false);
        option_d_txt.setClickable(false);
    }

    public void resetColor() {
        option_a_txt.setBackgroundColor(getResources().getColor(R.color.white));
        option_b_txt.setBackgroundColor(getResources().getColor(R.color.white));
        option_c_txt.setBackgroundColor(getResources().getColor(R.color.white));
        option_d_txt.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void OptionAClick(View view) {
        disableButton();
        if (questionsList.getoA().equals(questionsList.getAns())) {
            option_a_txt.setBackgroundColor(getResources().getColor(R.color.Green_Apple));

            if (index < list.size() - 1) {
//                index++;
//                questionsList = list.get(index);
//                setAllQuestion();
//                resetColor();
                Correct(option_a_txt);
            } else {
                GameWon();
            }
        } else {
            Wrong(option_a_txt);
        }
    }

    public void OptionBClick(View view) {
        disableButton();
        if (questionsList.getoB().equals(questionsList.getAns())) {
            option_b_txt.setBackgroundColor(getResources().getColor(R.color.Green_Apple));

            if (index < list.size() - 1) {
                Correct(option_b_txt);
            } else {
                GameWon();
            }
        } else {
            Wrong(option_b_txt);
        }
    }

    public void OptionCClick(View view) {
        disableButton();
        if (questionsList.getoC().equals(questionsList.getAns())) {
            option_c_txt.setBackgroundColor(getResources().getColor(R.color.Green_Apple));

            if (index < list.size() - 1) {
                Correct(option_c_txt);
            } else {
                GameWon();
            }
        } else {
            Wrong(option_c_txt);
        }
    }

    public void OptionDClick(View view) {
        disableButton();
        if (questionsList.getoD().equals(questionsList.getAns())) {
            option_d_txt.setBackgroundColor(getResources().getColor(R.color.Green_Apple));

            if (index < list.size() - 1) {
                Correct(option_d_txt);
            } else {
                GameWon();
            }
        } else {
            Wrong(option_d_txt);
        }
    }
}