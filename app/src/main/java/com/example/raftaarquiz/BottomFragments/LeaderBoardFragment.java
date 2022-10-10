package com.example.raftaarquiz.BottomFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.raftaarquiz.Model.LeaderBoard;
import com.example.raftaarquiz.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoardFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    String leaderBoardUrl = "https://adminapp.tech/raftarquiz/api/users/leaderboard";
    ArrayList<LeaderBoard> listItems = new ArrayList<>();
    RecyclerView recyclerView;
    ProgressDialog progressDialog;

    public LeaderBoardFragment() {
        // Required empty public constructor
    }

    public static LeaderBoardFragment newInstance(String param1, String param2) {
        LeaderBoardFragment fragment = new LeaderBoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllLeaderBoardData();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    LeaderBoardAdapter leaderBoardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading..");
        return root;
    }

    private void getAllLeaderBoardData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, leaderBoardUrl, response -> {
            try {
                progressDialog.show();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String image_url = jsonObject.getString("image_url");
                    String score = jsonObject.getString("score");
                    String rank = String.valueOf((i + 1));

                    LeaderBoard leaderBoard = new LeaderBoard(id, name, image_url, score, rank);
                    listItems.add(leaderBoard);
                }
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1, LinearLayoutManager.HORIZONTAL, false));
                leaderBoardAdapter = new LeaderBoardAdapter(listItems);
                recyclerView.setAdapter(leaderBoardAdapter);
                recyclerView.setHasFixedSize(true);
                leaderBoardAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
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
    
    //LeaderBoardAdapter
    public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.ViewHolder> {
        public List<LeaderBoard> list;
        public Context context;

        public LeaderBoardAdapter(List<LeaderBoard> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public LeaderBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_leaderboard1, parent, false);
            context = parent.getContext();
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull LeaderBoardAdapter.ViewHolder holder, int position) {
            if (list.size() > 0) {
                holder.playerName.setText(list.get(position).getName());
                holder.score.setText("Score : " + list.get(position).getScore());
                holder.rank.setText("Rank #" + list.get(position).getRank());
                Glide.with(context)
                        .load(list.get(position).getImage_url())
                        .placeholder(R.drawable.avatar)
                        .into(holder.profile_img);
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView playerName, score, rank;
            ImageView profile_img;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                playerName = itemView.findViewById(R.id.playerName);
                score = itemView.findViewById(R.id.score);
                rank = itemView.findViewById(R.id.rank);
                profile_img = itemView.findViewById(R.id.profilePic);
            }
        }
    }
}