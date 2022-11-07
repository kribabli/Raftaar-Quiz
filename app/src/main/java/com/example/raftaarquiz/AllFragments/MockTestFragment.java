package com.example.raftaarquiz.AllFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.raftaarquiz.R;
import com.example.raftaarquiz.Retrofit.ApiClient;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MockTestFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;

    public MockTestFragment() {
    }

    public static MockTestFragment newInstance(String param1, String param2) {
        MockTestFragment fragment = new MockTestFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllMockTest();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mock_test, container, false);
        swipeRefreshLayout = root.findViewById(R.id.swiper);
        recyclerView = root.findViewById(R.id.recycler);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllMockTest();
            }
        });
        return root;
    }

    private void getAllMockTest() {
        Call<Object> call = ApiClient
                .getInstance()
                .getApi()
                .getAllMockTest(getArguments().getString("id"));

        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Object scoreResponse = response.body();
                if (response.isSuccessful()) {
                    String message = new Gson().toJson(response.body());
                    try {
                        JSONObject jsonObject = new JSONObject(message);
                        JSONArray message1 = jsonObject.getJSONArray("message");
                        Log.d("TAG", "onResponse: " + message1);
                        for (int i = 0; i < message1.length(); i++) {
                            JSONObject jsonObject1 = message1.getJSONObject(i);
                            String id = jsonObject1.getString("id");
                            String title = jsonObject1.getString("title");
                            String availableTS = jsonObject1.getString("availablets");
                            String questionTime = jsonObject1.getString("quetime");
                            String totalQuestion = jsonObject1.getString("totalque");
                            String marks = jsonObject1.getString("marks");
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    } catch (JSONException e) {
                        swipeRefreshLayout.setRefreshing(false);
                        e.printStackTrace();
                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}