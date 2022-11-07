package com.example.raftaarquiz.AllActivity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSeriesQuestionActivity extends AppCompatActivity {
    ImageView heart_img;
    TextView question_txt, option_a_txt, option_b_txt, option_c_txt, option_d_txt, score_txt;
    String testSeriesQuestionsUrl = "https://adminapp.tech/raftarquiz/api/Note/notes/";
    String id;
    MutableLiveData<Integer> index = new MutableLiveData<>(0);
    MutableLiveData<Integer> rightCount = new MutableLiveData<>(0);
    MutableLiveData<Integer> wrongCount = new MutableLiveData<>(0);
    QuestionsList questionsList;
    TextView submitBtn;
    ArrayList<QuestionsList> listOfQ = new ArrayList<>();
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    QuestionNoAdapter questionNoAdapter;
    TextView timer;
    CountDownTimer countDownTimer;
    TextView btnA, btnB, btnC, btnD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_series_question);
        initMethod();
        setAction();
        getAllQuestionsList();
        //for countDownTimer
        countDownTimer();
    }

    private void initMethod() {
        heart_img = findViewById(R.id.heart_img);
        timer = findViewById(R.id.timer);
        score_txt = findViewById(R.id.score_txt);
        option_a_txt = findViewById(R.id.option_a_txt);
        option_b_txt = findViewById(R.id.option_b_txt);
        option_c_txt = findViewById(R.id.option_c_txt);
        option_d_txt = findViewById(R.id.option_d_txt);
        question_txt = findViewById(R.id.question_txt);

        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);

        submitBtn = findViewById(R.id.submitBtn);
        recyclerView = findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(this);
    }

    private void setAction() {
        id = getIntent().getStringExtra("id");
    }

    private void getAllQuestionsList() {
        progressDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, testSeriesQuestionsUrl + id, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                try {
                    if (jsonArray.length() > 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String category_id = jsonObject1.getString("category_id");
                            String pdf_url = jsonObject1.getString("pdf_url");
                            String type = jsonObject1.getString("type");
                            String question_img = jsonObject1.getString("question_img");

                            String ans_type = jsonObject1.getString("ans_type");
                            String correct_ans = jsonObject1.getString("correct_ans");

                            String ans1 = jsonObject1.getString("ans1");
                            String ans2 = jsonObject1.getString("ans2");
                            String ans3 = jsonObject1.getString("ans3");
                            String ans4 = jsonObject1.getString("ans4");
                            String date = jsonObject1.getString("date");

                            questionsList = new QuestionsList(title, ans1, ans2, ans3, ans4, correct_ans);
                            listOfQ.add(questionsList);
                        }
                        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                        questionNoAdapter = new QuestionNoAdapter(listOfQ);
                        recyclerView.setAdapter(questionNoAdapter);
                        questionNoAdapter.notifyDataSetChanged();

                        setAllQuestion(index.getValue());
                        progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                progressDialog.dismiss();
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

    private void countDownTimer() {
        countDownTimer = new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat("1");

                long hour = (millisUntilFinished / 3600000) % 24;

                long min = (millisUntilFinished / 60000) % 60;

                long sec = (millisUntilFinished / 1000) % 60;

                submitBtn.setClickable(false);
                submitBtn.setText("Wait for " + f.format(sec) + " seconds");
            }

            public void onFinish() {
                submitBtn.setClickable(true);
                submitBtn.setText("Next");
                submitBtn.setOnClickListener(view -> {
                    submitBtn.setClickable(false);
                    countDownTimer.cancel();
                    countDownTimer.start();
                    index.setValue(index.getValue() + 1);
                    setAllQuestion(index.getValue());
                    enableButton();
                    resetColor();
                });
            }
        };
        countDownTimer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownTimer();
    }

    public void setAllQuestion(Integer value) {
        if (listOfQ.size() > 0) {
            if (listOfQ.size() > index.getValue()) {
                question_txt.setText("[" + (index.getValue() + 1) + "/" + listOfQ.size() + "]" + " Q_ " + listOfQ.get(value).getQuestions());
                option_a_txt.setText(listOfQ.get(value).getoA());
                option_b_txt.setText(listOfQ.get(value).getoB());
                option_c_txt.setText(listOfQ.get(value).getoC());
                option_d_txt.setText(listOfQ.get(value).getoD());
            } else {
                //next and finish Dialog
//                showCustomDialog();
                Intent intent = new Intent(TestSeriesQuestionActivity.this, FullDialogActivity1.class);
                intent.putExtra("rightCount", String.valueOf(rightCount.getValue()));
                intent.putExtra("wrongCount", String.valueOf(wrongCount.getValue()));
                intent.putExtra("totalQuestion", String.valueOf(index.getValue()));
                startActivity(intent);
                finish();
            }
        }
    }

    private void showCustomDialog() {
        final Dialog dialog = new Dialog(TestSeriesQuestionActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_submit_test);

        TextView button = dialog.findViewById(R.id.play_btn);
        TextView button1 = dialog.findViewById(R.id.close);
        if (!isFinishing()) {
            dialog.show();// if fragment use getActivity().isFinishing() or isAdded() method
        }

        button1.setOnClickListener(view -> finish());

        button.setOnClickListener(view -> {
            index.setValue(0);
            rightCount.setValue(0);
            wrongCount.setValue(0);
            setAllQuestion(index.getValue());
            enableButton();
            resetColor();
            dialog.dismiss();
            score_txt.setText("Score : " + 0);
            //countDownTimer cancel and start new countDownTimer
            countDownTimer.cancel();
            countDownTimer();
        });
    }

    public void enableButton() {
        btnA.setClickable(true);
        btnB.setClickable(true);
        btnC.setClickable(true);
        btnD.setClickable(true);
    }

    public void disableButton() {
        btnA.setClickable(false);
        btnB.setClickable(false);
        btnC.setClickable(false);
        btnD.setClickable(false);
    }

    public void resetColor() {
        btnA.setBackgroundColor(getResources().getColor(R.color.white));
        btnB.setBackgroundColor(getResources().getColor(R.color.white));
        btnC.setBackgroundColor(getResources().getColor(R.color.white));
        btnD.setBackgroundColor(getResources().getColor(R.color.white));

        //1st question set no bg then click next the bg (By this code)
        btnA.setBackgroundResource(R.drawable.c_vc_border_rounded);
        btnB.setBackgroundResource(R.drawable.c_vc_border_rounded);
        btnC.setBackgroundResource(R.drawable.c_vc_border_rounded);
        btnD.setBackgroundResource(R.drawable.c_vc_border_rounded);
    }

    public void OptionAClick(View view) {
        disableButton();
        if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoA())) {
            btnA.setBackgroundResource(R.drawable.circle_green);
            rightCount.setValue(rightCount.getValue() + 1);
            score_txt.setText("Score : " + rightCount.getValue());
        } else if (listOfQ.get(index.getValue()).getAns() != listOfQ.get(index.getValue()).getoA()) {
            wrongCount.setValue(wrongCount.getValue() + 1);
            btnA.setBackgroundResource(R.drawable.circle);
            if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoC())) {
                btnC.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoB())) {
                btnB.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoD())) {
                btnD.setBackgroundResource(R.drawable.circle_green);
            }
        }
    }

    public void OptionBClick(View view) {
        disableButton();
        if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoB())) {
            btnB.setBackgroundResource(R.drawable.circle_green);
            rightCount.setValue(rightCount.getValue() + 1);
            score_txt.setText("Score : " + rightCount.getValue());
        } else if (listOfQ.get(index.getValue()).getAns() != listOfQ.get(index.getValue()).getoB()) {
            btnB.setBackgroundResource(R.drawable.circle);
            wrongCount.setValue(wrongCount.getValue() + 1);
            if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoA())) {
                btnA.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoC())) {
                btnC.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoD())) {
                btnD.setBackgroundResource(R.drawable.circle_green);
            }
        }
    }

    public void OptionCClick(View view) {
        disableButton();
        if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoC())) {
            btnC.setBackgroundResource(R.drawable.circle_green);
            rightCount.setValue(rightCount.getValue() + 1);
            score_txt.setText("Score : " + rightCount.getValue());
        } else if (listOfQ.get(index.getValue()).getAns() != listOfQ.get(index.getValue()).getoC()) {
            btnC.setBackgroundResource(R.drawable.circle);
            wrongCount.setValue(wrongCount.getValue() + 1);
            if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoA())) {
                btnA.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoB())) {
                btnB.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoD())) {
                btnD.setBackgroundResource(R.drawable.circle_green);
            }
        }
    }

    public void OptionDClick(View view) {
        disableButton();
        if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoD())) {
            btnD.setBackgroundResource(R.drawable.circle_green);
            rightCount.setValue(rightCount.getValue() + 1);
            score_txt.setText("Score : " + rightCount.getValue());
        } else if (listOfQ.get(index.getValue()).getAns() != listOfQ.get(index.getValue()).getoD()) {
            btnD.setBackgroundResource(R.drawable.circle);
            wrongCount.setValue(wrongCount.getValue() + 1);
            if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoA())) {
                btnA.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoB())) {
                btnB.setBackgroundResource(R.drawable.circle_green);
            } else if (listOfQ.get(index.getValue()).getAns().equals(listOfQ.get(index.getValue()).getoC())) {
                btnC.setBackgroundResource(R.drawable.circle_green);
            }
        }
    }

    //Adapter for No Of Question
    public class QuestionNoAdapter extends RecyclerView.Adapter<QuestionNoAdapter.ViewHolder> {
        public List<QuestionsList> list;
        public Context context;

        public QuestionNoAdapter(List<QuestionsList> list) {
            this.list = list;
        }

        @Override
        public QuestionNoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question_no, parent, false);
            context = parent.getContext();
            return new QuestionNoAdapter.ViewHolder(view);
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onBindViewHolder(final QuestionNoAdapter.ViewHolder holder, final int position) {
            holder.setIsRecyclable(false);
            holder.noOfQuestion.setText("" + (position + 1));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView noOfQuestion;
            private View mView;
            LinearLayout liner;

            public ViewHolder(View itemView) {
                super(itemView);
                mView = itemView;
                noOfQuestion = itemView.findViewById(R.id.no_txt);
                liner = itemView.findViewById(R.id.liner);
            }
        }
    }
}