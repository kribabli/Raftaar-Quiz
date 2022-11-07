package com.example.raftaarquiz.AllActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.AllAdapter.PageAdapter;
import com.example.raftaarquiz.BuildConfig;
import com.example.raftaarquiz.R;
import com.google.android.material.tabs.TabLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class MockTestActivity extends AppCompatActivity {
    TextView backPress, share, mockTestName;
    CircleImageView mockTestImg;
    PageAdapter pageAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_test);
        initMethod();
        setAction();

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), getIntent().getStringExtra("id"), getIntent().getStringExtra("img"), getIntent().getStringExtra("title"));
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void initMethod() {
        backPress = findViewById(R.id.backPress);
        share = findViewById(R.id.share);
        mockTestImg = findViewById(R.id.mockTestImg);
        mockTestName = findViewById(R.id.mockTestName);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
    }

    private void setAction() {
        backPress.setOnClickListener(view -> onBackPressed());

        if (getIntent().hasExtra("img")) {
            try {
                Glide.with(this).load(getIntent().getStringExtra("img")).placeholder(R.drawable.logo).into(mockTestImg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (getIntent().hasExtra("title")) {
            mockTestName.setText(getIntent().getStringExtra("title"));
        }

        share.setOnClickListener(view -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Total Test");
                String shareMessage = getIntent().getStringExtra("title") + "\n" + "Online Platform for Education" + "\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}