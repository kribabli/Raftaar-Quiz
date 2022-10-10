package com.example.raftaarquiz.BottomFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.raftaarquiz.Common.HelperData;
import com.example.raftaarquiz.LoginModule.LoginActivity;
import com.example.raftaarquiz.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MyProfileFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    LinearLayout linearlayout7;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    HelperData helperData;

    public MyProfileFragment() {
        // Required empty public constructor
    }

    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        googleSignIn();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        linearlayout7 = root.findViewById(R.id.linearlayout7);

        linearlayout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogout();
            }
        });
        return root;

    }

    private void googleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(getActivity(), gso);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getContext());
        if (googleSignInAccount != null) {
            String userName = googleSignInAccount.getDisplayName();
            String userEmail = googleSignInAccount.getEmail();
            Uri photoUrl = googleSignInAccount.getPhotoUrl();
            String id = googleSignInAccount.getId();
            Log.d("TAG", "onCreate: " + id + "  " + userName + "  " + userEmail + "  " + photoUrl);
        }
    }

    private void userLogout() {
        helperData = new HelperData(getContext());
        Intent intent = new Intent(getContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        gsc.signOut();
        helperData.Logout();
        Toast.makeText(getContext(), "Logout Successfully", Toast.LENGTH_SHORT).show();
        getActivity().finish();
    }
}