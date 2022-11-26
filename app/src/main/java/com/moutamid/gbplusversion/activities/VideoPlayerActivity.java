package com.moutamid.gbplusversion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.MediaController;

import com.moutamid.gbplusversion.R;
import com.moutamid.gbplusversion.Utils;
import com.moutamid.gbplusversion.databinding.ActivityVideoPlayerBinding;

public class VideoPlayerActivity extends AppCompatActivity {

    ActivityVideoPlayerBinding binding;
    String videoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        videoPath = Utils.mPath;

        binding.displayVV.setVideoPath(videoPath);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(binding.displayVV);

        binding.displayVV.setMediaController(mediaController);

        binding.displayVV.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
        binding.displayVV.setVideoPath(videoPath);
        binding.displayVV.start();
    }

}