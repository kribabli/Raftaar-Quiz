package com.example.raftaarquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.raftaarquiz.BottomFragments.HomeFragment;
import com.example.raftaarquiz.BottomFragments.LeaderBoardFragment;
import com.example.raftaarquiz.BottomFragments.MyDownloadFragment;
import com.example.raftaarquiz.BottomFragments.MyProfileFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMethod();
        setAction();
        googleSignIn();

        fragmentManager = getSupportFragmentManager();
        HomeFragment homeFragment = new HomeFragment();
        loadFragment(homeFragment, fragmentManager);

        new Thread(this::mBottomNavigationBar).start();

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    private void initMethod() {
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.NavigationView);
    }

    private void setAction() {
    }

    private void loadFragment(Fragment f1, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, f1);
        ft.commit();
    }

    @SuppressLint("NonConstantResourceId")
    private void mBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean bool = false;
                if (bottomNavigationView.getSelectedItemId() != item.getItemId()) {
                    switch (item.getItemId()) {
                        case R.id.home:
                            bool = true;
                            HomeFragment homeFragment = new HomeFragment();
                            loadFragment(homeFragment, fragmentManager);
                            break;
                        case R.id.leaderboard:
                            bool = true;
                            LeaderBoardFragment leaderBoardFragment = new LeaderBoardFragment();
                            loadFragment(leaderBoardFragment, fragmentManager);
                            break;
                        case R.id.download:
                            bool = true;
                            MyDownloadFragment myDownloadFragment = new MyDownloadFragment();
                            loadFragment(myDownloadFragment, fragmentManager);
                            break;
                        case R.id.profile:
                            bool = true;
                            MyProfileFragment myProfileFragment = new MyProfileFragment();
                            loadFragment(myProfileFragment, fragmentManager);
                            break;
                    }
                }
                return bool;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    private void googleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            String userName = googleSignInAccount.getDisplayName();
            String userEmail = googleSignInAccount.getEmail();
            Uri photoUrl = googleSignInAccount.getPhotoUrl();
            String id = googleSignInAccount.getId();
            Log.d("TAG", "onCreate: " + id + "  " + userName + "  " + userEmail + "  " + photoUrl);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }
}