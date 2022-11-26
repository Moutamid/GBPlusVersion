package com.moutamid.gbplusversion.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.futuremind.recyclerviewfastscroll.Utils;
import com.moutamid.gbplusversion.databinding.ActivityDirectChatBinding;

public class DirectChatActivity extends AppCompatActivity {
    ActivityDirectChatBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDirectChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (!(ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NETWORK_STATE") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SET_WALLPAPER") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.INTERNET") == 0 && ContextCompat.checkSelfPermission(this, "android.permission.SYSTEM_ALERT_WINDOW") == 0) && Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.ACCESS_NETWORK_STATE", "android.permission.SET_WALLPAPER", "android.permission.INTERNET", "android.permission.SYSTEM_ALERT_WINDOW"}, 0);
        }

        binding.send.setOnClickListener(v -> {
            if (binding.phoneNumber.getText().toString().isEmpty()){
                Toast.makeText(this, "Please add number", Toast.LENGTH_SHORT).show();
            } else {
                String Num = binding.countryPicker.getSelectedCountryCode() + binding.phoneNumber.getText().toString();
                String messg = binding.message.getText().toString();
                openWhatsApp(Num, messg);
            }
        });

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });
    }

    private void openWhatsApp(String smsNumber, String text) {
        Log.d(TAG, "openWhatsApp: smsNumber: " + smsNumber);
        Log.d(TAG, "openWhatsApp: text: " + text);

        startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                        smsNumber, text))));
    }
}