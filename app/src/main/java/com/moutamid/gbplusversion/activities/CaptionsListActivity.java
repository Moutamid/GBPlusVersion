package com.moutamid.gbplusversion.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.moutamid.gbplusversion.adapters.CaptionsListAdapter;
import com.moutamid.gbplusversion.databinding.ActivityCaptionsListBinding;

public class CaptionsListActivity extends AppCompatActivity {
    ActivityCaptionsListBinding binding;
    String[] logos = new String[]{"Best", "Clever", "Cool", "Cute", "Fitness", "Funny", "Life", "Love", "Motivation", "Sad", "Selfie", "Song"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptionsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.captionRC.setLayoutManager(new GridLayoutManager(this, 2));
        binding.captionRC.setHasFixedSize(false);
        binding.captionRC.setAdapter(new CaptionsListAdapter(this, logos));

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }
}