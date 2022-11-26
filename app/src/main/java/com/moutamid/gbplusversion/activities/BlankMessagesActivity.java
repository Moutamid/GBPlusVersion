package com.moutamid.gbplusversion.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.moutamid.gbplusversion.databinding.ActivityBlankMessagesBinding;

public class BlankMessagesActivity extends AppCompatActivity {
    ActivityBlankMessagesBinding binding;
    String space = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlankMessagesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.copyBtn.setOnClickListener(v -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            if (binding.blankText.getText().toString().isEmpty()){
                Toast.makeText(this, "Nothing To Copy", Toast.LENGTH_SHORT).show();
            } else {
                if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    clipboard.setText(binding.blankText.getText().toString());
                    Toast.makeText(this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Blank Message", binding.blankText.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.shareBtn.setOnClickListener(v -> {
            Intent whatsappIntent = new Intent("android.intent.action.SEND");
            whatsappIntent.setType("text/plain");
            whatsappIntent.putExtra("android.intent.extra.TEXT", binding.blankText.getText().toString());
            try {
                startActivity(whatsappIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(BlankMessagesActivity.this, "Some problems", Toast.LENGTH_SHORT).show();
            }
        });

        binding.deleteBtn.setOnClickListener(v -> {
            binding.length.setText("");
            binding.blankText.setText("");
            binding.blankText.setVisibility(View.GONE);
        });

        binding.length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int j = Integer.parseInt(s.toString());
                if (binding.newLineSwitch.isChecked()){
                    for (int i=0; i<j; i++){
                        space = space + "\u3164" + "\n";
                    }
                } else {
                    for (int i =0; i<j; i++){
                        space = space + "\u3164";
                    }
                }
                binding.blankText.setVisibility(View.VISIBLE);
                binding.blankText.setText(space);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}