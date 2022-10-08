package com.example.raftaarquiz.AllAdapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.raftaarquiz.Model.QuestionsReq;
import com.example.raftaarquiz.R;

import java.util.List;

public class QuestionNoAdapter extends RecyclerView.Adapter<QuestionNoAdapter.ViewHolder> {

    public List<QuestionsReq> list;
    public Context context;

    public QuestionNoAdapter(List<QuestionsReq> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_question_no, parent, false);

        context = parent.getContext();
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        holder.no_txt.setText("" + (position + 1));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView no_txt;
        private View mView;
        LinearLayout liner;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            no_txt = itemView.findViewById(R.id.no_txt);
            liner = itemView.findViewById(R.id.liner);
        }
    }
}