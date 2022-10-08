package com.example.raftaarquiz.BottomFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.raftaarquiz.AllActivity.BookActivity;
import com.example.raftaarquiz.AllActivity.CurrentAffairsActivity;
import com.example.raftaarquiz.AllActivity.JobAlertActivity;
import com.example.raftaarquiz.AllActivity.QuizActivity;
import com.example.raftaarquiz.AllActivity.SetActivity;
import com.example.raftaarquiz.AllActivity.TestSeriesActivity;
import com.example.raftaarquiz.R;

public class HomeFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    LinearLayout quiz_liner, set_liner, book_liner, job_alert_liner, currentAffairs_liner, testSeries_liner;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        quiz_liner = root.findViewById(R.id.quiz_liner);
        set_liner = root.findViewById(R.id.set_liner);
        book_liner = root.findViewById(R.id.book_liner);
        job_alert_liner = root.findViewById(R.id.job_alert_liner);
        currentAffairs_liner = root.findViewById(R.id.currentAffairs_liner);
        testSeries_liner = root.findViewById(R.id.testSeries_liner);

        quiz_liner.setOnClickListener(v -> startActivity(new Intent(getContext(), QuizActivity.class)));

        set_liner.setOnClickListener(v -> startActivity(new Intent(getContext(), SetActivity.class)));

        book_liner.setOnClickListener(v -> startActivity(new Intent(getContext(), BookActivity.class)));

        job_alert_liner.setOnClickListener(v -> startActivity(new Intent(getContext(), JobAlertActivity.class)));

        currentAffairs_liner.setOnClickListener(v -> startActivity(new Intent(getContext(), CurrentAffairsActivity.class)));

        testSeries_liner.setOnClickListener(v -> startActivity(new Intent(getContext(), TestSeriesActivity.class)));

        return root;
    }
}