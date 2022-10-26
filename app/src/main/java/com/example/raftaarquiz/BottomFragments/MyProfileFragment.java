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
import com.example.raftaarquiz.AllActivity.EditProfileActivity;
import com.example.raftaarquiz.Common.HelperData;
import com.example.raftaarquiz.LoginModule.LoginActivity;
import com.example.raftaarquiz.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileFragment extends Fragment {
    LinearLayout linearlayout7, linearlayout6, linearlayout1;
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
        linearlayout6 = root.findViewById(R.id.linearlayout6);
        linearlayout1 = root.findViewById(R.id.linearlayout1);

        userName = root.findViewById(R.id.userName);
        profilePic = root.findViewById(R.id.profilePic);
        helperData = new HelperData(getContext());

        setAction();
        googleSignIn();
        referAndEarn();
        return root;
    }

    private void setAction() {
        userName.setText("" + helperData.getUserName());
        linearlayout1.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), EditProfileActivity.class);
            startActivity(intent);
        });
        linearlayout7.setOnClickListener(view -> userLogout());
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
                    .placeholder(R.drawable.ic_profile_pic)
                    .into(profilePic);
            Log.d("TAG", "onCreate: " + id + "  " + userName + "  " + userEmail + "  " + photoUrl);
        } else {
            Glide.with(getContext())
                    .load(helperData.getSaveProfilePic())
                    .placeholder(R.drawable.ic_profile_pic)
                    .into(profilePic);
        }
    }

    private void referAndEarn() {
        helperData = new HelperData(getContext());
        //ReferAndEarn-----------------------------------------
        linearlayout6.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Raftaar Quiz");
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + androidx.multidex.BuildConfig.APPLICATION_ID + "";
                shareMessage = shareMessage + "\n1.Use my invite code " + helperData.getReferalCode() + "\n";
                shareMessage = shareMessage + "\nLet's play!";

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
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