package com.example.raftaarquiz.AllAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.AllActivity.QuizQuestionsActivity;
import com.example.raftaarquiz.Model.QuizCategories;
import com.example.raftaarquiz.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.ViewHolder> {
    public List<QuizCategories> list;
    public Context context;

    public QuizAdapter(ArrayList<QuizCategories> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category, parent, false);
        context = parent.getContext();

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setIsRecyclable(false);
        holder.title.setText("" + list.get(position).getTitle());

        try {
            Glide.with(context).load(list.get(position).getImage()).placeholder(R.drawable.logo).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.liner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuizQuestionsActivity.class);
                intent.putExtra("id", list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imageView;
        TextView title;
        LinearLayout liner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title_txt);
            liner = itemView.findViewById(R.id.liner);
        }
    }
}
