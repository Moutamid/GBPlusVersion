package com.moutamid.gbplusversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.gbplusversion.databinding.ActivityTextRepeaterBinding;

public class TextRepeaterActivity extends AppCompatActivity {

    ActivityTextRepeaterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextRepeaterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }
}