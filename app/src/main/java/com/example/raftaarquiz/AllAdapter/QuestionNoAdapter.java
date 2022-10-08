package com.example.raftaarquiz.AllAdapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raftaarquiz.Model.QuestionsReq;

import java.util.ArrayList;

public class QuestionNoAdapter extends RecyclerView.Adapter<QuestionNoAdapter.ViewHolder> {

    public QuestionNoAdapter(ArrayList<QuestionsReq> list) {
    }

    @NonNull
    @Override
    public QuestionNoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionNoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

