package com.example.raftaarquiz.AllAdapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.raftaarquiz.AllFragments.MockTestFragment;

public class PageAdapter extends FragmentPagerAdapter {
    int tabCount;
    String id = "";
    String img = "";
    String title = "";

    public PageAdapter(@NonNull FragmentManager fm, int behavior, String id, String img, String title) {
        super(fm, behavior);
        tabCount = behavior;
        this.id = id;
        this.img = img;
        this.title = title;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                bundle.putString("img", img);
                bundle.putString("title", title);
                MockTestFragment mockTestFragment = new MockTestFragment();
                mockTestFragment.setArguments(bundle);
                return mockTestFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
