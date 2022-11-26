package com.moutamid.gbplusversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.gbplusversion.databinding.ActivityDirectChatBinding;

public class DirectChatActivity extends AppCompatActivity {
    ActivityDirectChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }
}