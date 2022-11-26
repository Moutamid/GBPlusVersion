package com.moutamid.gbplusversion.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.moutamid.gbplusversion.databinding.ActivityEmotionsBinding;
import com.moutamid.gbplusversion.fragment.AngryEmotFragment;

import java.util.ArrayList;
import java.util.List;

public class EmotionsActivity extends AppCompatActivity {
    ActivityEmotionsBinding binding;
    String[] tabs = new String[3];;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmotionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tabs[0] = "Angry";
        tabs[1] = "Happy";
        tabs[2] = "Other";

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        setupViewPager(binding.viewpager);

        binding.tabs.setupWithViewPager(binding.viewpager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager());

        adapter.addFragment(new AngryEmotFragment(), "Angry");
        /*adapter.addFragment(new HappyFragment(), "Happy");
        adapter.addFragment(new OtherFragment(), "Other");*/

        viewPager.setAdapter(adapter);
    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return this.mFragmentList.get(arg0);
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            this.mFragmentList.add(fragment);
            this.mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return this.mFragmentTitleList.get(position);
        }
    }
}