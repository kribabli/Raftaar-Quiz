package com.example.raftaarquiz.AllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.raftaarquiz.AllAdapter.QuizAdapter;
import com.example.raftaarquiz.Model.QuizCategories;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuizActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView tournament_Quiz;
    SwipeRefreshLayout swiper;
    QuizAdapter quizAdapter;
    String quizUrl = "https://adminapp.tech/raftarquiz/api/exam/categories";
    String tournamentUrl = "https://adminapp.tech/raftarquiz/userapi/turnamentcategories.php";
    ArrayList<QuizCategories> listItems = new ArrayList<>();
    TextView backPress;
    TournamentQuizAdapter tournamentQuizAdapter;
    ArrayList<QuizCategories> tournament_listItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        initMethod();
        setAction();
        getAllQuizList();
        getAllTournamentList();
    }

    private void initMethod() {
        recyclerView = findViewById(R.id.recycler);
        tournament_Quiz = findViewById(R.id.tournament_Quiz);
        swiper = findViewById(R.id.swiper);
        backPress = findViewById(R.id.backPress);
        Animation animSlideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down);
        recyclerView.startAnimation(animSlideDown);
        tournament_Quiz.startAnimation(animSlideDown);
    }

    private void setAction() {
        swiper.setOnRefreshListener(() -> {
            swiper.setRefreshing(false);
            getAllQuizList();
            getAllTournamentList();
            Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
            recyclerView.startAnimation(animSlideDown);
            tournament_Quiz.startAnimation(animSlideDown);
        });

        backPress.setOnClickListener(view -> onBackPressed());
    }

    private void getAllQuizList() {
        listItems.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, quizUrl, response -> {
            try {
                listItems.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String title = jsonObject1.getString("title");
                        String image = jsonObject1.getString("image");
                        QuizCategories quizCategories = new QuizCategories(id, title, image, "", "", "");
                        listItems.add(quizCategories);
                    }
                    quizAdapter = new QuizAdapter(listItems);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(quizAdapter);
                    quizAdapter.notifyDataSetChanged();

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
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void getAllTournamentList() {
        tournament_listItems.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, tournamentUrl, response -> {
            try {
                tournament_listItems.clear();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("message");
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String id = jsonObject1.getString("id");
                        String Name = jsonObject1.getString("Name");
                        String Image = jsonObject1.getString("Image");
                        String Open_Time = jsonObject1.getString("Open Time");
                        String Close_Time = jsonObject1.getString("Close Time");
                        String Date = jsonObject1.getString("Date");

                        QuizCategories quizCategories = new QuizCategories(id, Name, Image, Open_Time, Close_Time, Date);
                        tournament_listItems.add(quizCategories);
                    }
                    tournament_Quiz.setLayoutManager(new LinearLayoutManager(this));
                    tournamentQuizAdapter = new TournamentQuizAdapter(tournament_listItems);
                    tournament_Quiz.setAdapter(tournamentQuizAdapter);
                    tournamentQuizAdapter.notifyDataSetChanged();
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
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);
    }

    //TournamentQuizAdapter for Category
    public class TournamentQuizAdapter extends RecyclerView.Adapter<TournamentQuizAdapter.ViewHolder> {
        public List<QuizCategories> list;
        public Context context;

        public TournamentQuizAdapter(List<QuizCategories> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public TournamentQuizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_tournament_quiz, parent, false);
            context = parent.getContext();
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TournamentQuizAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.setIsRecyclable(false);

            holder.title.setText("" + list.get(position).getTitle());
            try {
                Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.logo).into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Calendar calendar1 = Calendar.getInstance();
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd h:mm");
            String currentDate = formatter1.format(calendar1.getTime());

            final String dateString = list.get(position).getQuiz_date();
            final String timeString = list.get(position).getQuiz_start_time();
            final String timeString1 = list.get(position).getQuiz_close_time();
            String openDate = dateString + " " + timeString;
            String closeDate = dateString + " " + timeString1;

            //for date time.(Show)
            String openDateOnly = openDate.split("\\ ")[0].split("\\ ")[0];
            String openTimeOnly = openDate.split("\\ ")[1].split("\\ ")[0];
            String closeTimeOnly = closeDate.split("\\ ")[1].split("\\ ")[0];
            holder.dateTime.setVisibility(View.VISIBLE);
            holder.dateTime.setText(openDateOnly + "   " + openTimeOnly + "-" + closeTimeOnly);

            try {
                Date time1 = new SimpleDateFormat("yyyy-MM-dd h:mm").parse(openDate);
                Date time2 = new SimpleDateFormat("yyyy-MM-dd h:mm").parse(closeDate);
                Date d = new SimpleDateFormat("yyyy-MM-dd h:mm").parse(currentDate);

                Log.d("TAG", "onBindViewHolder:time1 " + time1);
                Log.d("TAG", "onBindViewHolder:time2 " + time2);
                Log.d("TAG", "onBindViewHolder:d " + d);

                if (time1.after(d) && time2.before(d)) {
                    holder.play_quiz.setVisibility(View.VISIBLE);
                    holder.linearLayout.setOnClickListener(v -> {
                        Intent intent = new Intent(context, TournamentQuestionActivity.class);
                        intent.putExtra("id", list.get(position).getId());
                        context.startActivity(intent);
                    });
                } else {
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            CircleImageView imageView;
            TextView title, play_quiz, dateTime;
            LinearLayout linearLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.quizImg);
                dateTime = itemView.findViewById(R.id.dateTime);
                title = itemView.findViewById(R.id.quizTitle);
                linearLayout = itemView.findViewById(R.id.LinearLayout);
//                play_quiz = itemView.findViewById(R.id.play_quiz);
            }
        }
    }
}