package com.moutamid.gbplusversion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.moutamid.gbplusversion.databinding.ActivityTextRepeaterBinding;

public class TextRepeaterActivity extends AppCompatActivity {

    ActivityTextRepeaterBinding binding;
    String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextRepeaterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.btnRep.setOnClickListener(v -> {
            if (binding.textRepeat.getText().toString().isEmpty() || binding.text.getText().toString().isEmpty()){
                Toast.makeText(this, "Please Enter The Required Data", Toast.LENGTH_SHORT).show();
            } else {
                if (binding.newLineSwitch.isChecked()) {
                    int t = Integer.parseInt(binding.textRepeat.getText().toString());
                    for (int i = 0; i < t; i++) {
                        s = s + binding.text.getText().toString() + "\n";
                    }
                    binding.resultTV.setText(s);
                } else {
                    int t = Integer.parseInt(binding.textRepeat.getText().toString());
                    for (int i = 0; i < t; i++) {
                        s = s + binding.text.getText().toString() + " ";
                    }
                    binding.resultTV.setText(s);
                }
            }
        });

        binding.copyBtn.setOnClickListener(v -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            if (binding.resultTV.getText().toString().isEmpty()){
                Toast.makeText(this, "Nothing To Copy", Toast.LENGTH_SHORT).show();
            } else {
                if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    clipboard.setText(binding.resultTV.getText().toString());
                    Toast.makeText(this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Blank Message", binding.resultTV.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.shareBtn.setOnClickListener(v -> {
            Intent whatsappIntent = new Intent("android.intent.action.SEND");
            whatsappIntent.setType("text/plain");
            whatsappIntent.putExtra("android.intent.extra.TEXT", binding.resultTV.getText().toString());
            try {
                startActivity(whatsappIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(TextRepeaterActivity.this, "Some problems", Toast.LENGTH_SHORT).show();
            }
        });

        binding.deleteBtn.setOnClickListener(v -> {
            binding.textRepeat.setText("");
            binding.text.setText("");
            binding.resultTV.setText("");
        });

    }
}