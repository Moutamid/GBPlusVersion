package com.moutamid.gbplusversion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.moutamid.gbplusversion.databinding.ActivityBlankMessagesBinding;

public class BlankMessagesActivity extends AppCompatActivity {
    ActivityBlankMessagesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlankMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}