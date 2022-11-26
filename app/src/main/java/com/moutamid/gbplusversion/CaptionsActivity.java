package com.moutamid.gbplusversion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ListView;

import com.moutamid.gbplusversion.adapters.CaptionAdapter;
import com.moutamid.gbplusversion.databinding.ActivityCaptionsBinding;

public class CaptionsActivity extends AppCompatActivity {
    ActivityCaptionsBinding binding;
    String[] captions;
    String name;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = getIntent().getStringExtra("image");
        position = getIntent().getIntExtra("P", 0);

        binding.captionTitle.setText(name);

        if (position == 0) {
            captions = getResources().getStringArray(R.array.s_best);
        } else if (position == 1) {
            captions = getResources().getStringArray(R.array.s_clever);
        } else if (position == 2) {
            captions = getResources().getStringArray(R.array.s_cool);
        } else if (position == 3) {
            captions = getResources().getStringArray(R.array.s_cute);
        } else if (position == 4) {
            captions = getResources().getStringArray(R.array.s_fitness);
        } else if (position == 5) {
            captions = getResources().getStringArray(R.array.s_funny);
        } else if (position == 6) {
            captions = getResources().getStringArray(R.array.s_life);
        } else if (position == 7) {
            captions = getResources().getStringArray(R.array.s_love);
        } else if (position == 8) {
            captions = getResources().getStringArray(R.array.s_motivation);
        } else if (position == 9) {
            captions = getResources().getStringArray(R.array.s_sad);
        } else if (position == 10) {
            captions = getResources().getStringArray(R.array.s_selfie);
        } else if (position == 11) {
            captions = getResources().getStringArray(R.array.s_song);
        }

        binding.captionRC.setLayoutManager(new LinearLayoutManager(this));
        binding.captionRC.setHasFixedSize(false);

        binding.captionRC.setAdapter(new CaptionAdapter(this, captions));

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

    }
}