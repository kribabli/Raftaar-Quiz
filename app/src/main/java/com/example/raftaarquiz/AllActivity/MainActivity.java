package com.example.raftaarquiz.AllActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.raftaarquiz.BottomFragments.HomeFragment;
import com.example.raftaarquiz.BottomFragments.LeaderBoardFragment;
import com.example.raftaarquiz.BottomFragments.MyDownloadFragment;
import com.example.raftaarquiz.BottomFragments.MyProfileFragment;
import com.example.raftaarquiz.BuildConfig;
import com.example.raftaarquiz.LoginModule.LoginActivity;
import com.example.raftaarquiz.Common.HelperData;
import com.example.raftaarquiz.R;
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
    Toolbar toolbar_main;
    boolean doubleBackToExitPressedOnce = false;
    HelperData helperData;
    TextView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMethod();
        setAction();
        googleSignIn();

        setSupportActionBar(toolbar_main);

        new Thread(this::mBottomNavigationBar).start();
        helperData = new HelperData(getApplicationContext());
        fragmentManager = getSupportFragmentManager();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_main, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar_main.setNavigationIcon(R.drawable.ic_baseline_person_24);

        NavigationView navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.privacyPolicy:
                    startActivity(new Intent(this, AboutUsActivity.class));
                    return true;
                case R.id.aboutUs:
                    startActivity(new Intent(this, AboutUsActivity.class));
                    return true;
                case R.id.share:
                    startActivity(new Intent(this, AboutUsActivity.class));
                    return true;
                case R.id.logout:
                    userLogout();
                    return true;
                default:
                    return true;
            }
        });
        HomeFragment homeFragment = new HomeFragment();
        loadFragment(homeFragment, "Home", fragmentManager);
    }

    private void initMethod() {
        toolbar_main = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.NavigationView);
    }

    private void setAction() {
        setSupportActionBar(toolbar_main);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar_main, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        toolbar_main.setNavigationIcon(R.drawable.ic_baseline_home_24);
    }

    private void userLogout() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        gsc.signOut();
        helperData.Logout();
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
    }

    public void loadFragment(Fragment f1, String name, FragmentManager fm) {
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, f1, name);
        ft.commit();
        setToolbarTitle(name);
    }

    public void setToolbarTitle(String Title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(Title);
        }
    }

    private void mBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            boolean bool = false;
            if (bottomNavigationView.getSelectedItemId() != item.getItemId()) {
                switch (item.getItemId()) {
                    case R.id.home:
                        bool = true;
                        HomeFragment homeFragment = new HomeFragment();
                        loadFragment(homeFragment, "Home", fragmentManager);
                        break;
                    case R.id.leaderboard:
                        LeaderBoardFragment leaderBoardFragment = new LeaderBoardFragment();
                        loadFragment(leaderBoardFragment, "LeaderBoard", fragmentManager);
                        bool = true;
                        break;
                    case R.id.download:
                        MyDownloadFragment myDownloadFragment = new MyDownloadFragment();
                        loadFragment(myDownloadFragment, "My Download", fragmentManager);
                        bool = true;
                        break;
                    case R.id.Profile:
                        MyProfileFragment myProfileFragment = new MyProfileFragment();
                        loadFragment(myProfileFragment, "My Profile", fragmentManager);
                        bool = true;
                        break;
                }
            }
            return bool;
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
        return false;
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

    private void shareApp() {
        //App Share-----------------------------------------
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Raftaar Quiz");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (fragmentManager.getBackStackEntryCount() != 0) {
            String tag = fragmentManager.getFragments().get(fragmentManager.getBackStackEntryCount() - 1).getTag();
            setToolbarTitle(tag);
            super.onBackPressed();
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.back_key), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 1000);
        }
    }
}