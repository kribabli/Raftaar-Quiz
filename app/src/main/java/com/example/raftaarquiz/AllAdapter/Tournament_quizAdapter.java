package com.example.raftaarquiz.AllAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.Model.QuizCategories;
import com.example.raftaarquiz.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tournament_quizAdapter extends  RecyclerView.Adapter<Tournament_quizAdapter.ViewHolder>{
    public List<QuizCategories> list;
    public Context context;

    public Tournament_quizAdapter(List<QuizCategories> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public Tournament_quizAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_tournament_quiz, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Tournament_quizAdapter.ViewHolder holder, int position) {
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
        String openDate =dateString+" "+timeString;
        String closeDate =dateString+" "+timeString1;

        try {
            Date time1 = new SimpleDateFormat("yyyy-MM-dd h:mm").parse(openDate);
            Date time2 = new SimpleDateFormat("yyyy-MM-dd h:mm").parse(closeDate);
            Date d = new SimpleDateFormat("yyyy-MM-dd h:mm").parse(currentDate);

            if (time1.before(d) && time2.after(d)) {
                Log.d("Amit","Value  time is Between");
            }
            else{
                Log.d("Amit","Value after and before ");
            }



        } catch (ParseException e) {
            e.printStackTrace();
        }




        String strDateTime = "2022-10-17 12:20";





        if(currentDate.compareTo(strDateTime)>=0){
            holder.play_quiz.setVisibility(View.VISIBLE);
            Toast.makeText(context, "Time Is Not Started", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView title,play_quiz,Join_contests;
        LinearLayout liner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title_txt);
            liner = itemView.findViewById(R.id.liner);
            play_quiz = itemView.findViewById(R.id.play_quiz);
            Join_contests = itemView.findViewById(R.id.Join_contests);
        }
    }
}
