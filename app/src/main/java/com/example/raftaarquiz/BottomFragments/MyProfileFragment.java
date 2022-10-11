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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.Common.HelperData;
import com.example.raftaarquiz.LoginModule.LoginActivity;
import com.example.raftaarquiz.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileFragment extends Fragment {
    LinearLayout linearlayout7;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    HelperData helperData;
    TextView userName;
    CircleImageView profilePic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_profile, container, false);
        linearlayout7 = root.findViewById(R.id.linearlayout7);

        userName = root.findViewById(R.id.userName);
        profilePic = root.findViewById(R.id.profilePic);
        helperData=new HelperData(getContext());
        linearlayout7.setOnClickListener(view -> userLogout());
        userName.setText(""+helperData.getUserName());
        googleSignIn();
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
            Glide.with(getContext())
                    .load(photoUrl)
                    .into(profilePic);
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