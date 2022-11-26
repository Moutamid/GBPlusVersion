package com.moutamid.gbplusversion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.moutamid.gbplusversion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.directChatBtn.setOnClickListener(v -> {
            startActivity(new Intent(this, DirectChatActivity.class));
        });

        binding.bubble.setOnClickListener(v -> {
            startActivity(new Intent(this, BlankMessagesActivity.class));
        });

        binding.textRepeat.setOnClickListener(v -> {
            startActivity(new Intent(this, TextRepeaterActivity.class));
        });

        binding.closedCaption.setOnClickListener(v -> {
            startActivity(new Intent(this, CaptionsListActivity.class));
        });
    }
}