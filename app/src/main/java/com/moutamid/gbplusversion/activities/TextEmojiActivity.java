package com.moutamid.gbplusversion.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.moutamid.gbplusversion.R;
import com.moutamid.gbplusversion.databinding.ActivityTextEmojiBinding;

import java.io.IOException;
import java.io.InputStream;

public class TextEmojiActivity extends AppCompatActivity {

    ActivityTextEmojiBinding binding;
    private InterstitialAd finterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextEmojiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AudienceNetworkAds.initialize(this);

        finterstitialAd = new InterstitialAd(this, getResources().getString(R.string.fb_ad_inters));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
                // Show the ad
                showAdWithDelay();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };
        finterstitialAd.loadAd(
                finterstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        binding.btnConv.setOnClickListener(v -> {
            if (binding.emoji.getText().toString().isEmpty() || binding.text.getText().toString().isEmpty()){
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();
            } else {
                char[] charArray = binding.text.getText().toString().toCharArray();
                binding.convertedEmojeeTxt.setText(".\n");
                for (char character : charArray) {
                    byte[] localObject3;
                    InputStream localObject2;
                    if (character == '?') {
                        try {
                            InputStream localObject1 = getBaseContext().getAssets().open("ques.txt");
                            localObject3 = new byte[localObject1.available()];
                            localObject1.read(localObject3);
                            localObject1.close();
                            binding.convertedEmojeeTxt.append(new String(localObject3).replaceAll("[*]", binding.emoji.getText().toString()) + "\n\n");
                        } catch (IOException ioe) {
                            ioe.printStackTrace();
                        }
                    } else if (character == ((char) (character & 95)) || Character.isDigit(character)) {
                        try {
                            localObject2 = getBaseContext().getAssets().open(character + ".txt");
                            localObject3 = new byte[localObject2.available()];
                            localObject2.read(localObject3);
                            localObject2.close();
                            binding.convertedEmojeeTxt.append(new String(localObject3).replaceAll("[*]", binding.emoji.getText().toString()) + "\n\n");
                        } catch (IOException ioe2) {
                            ioe2.printStackTrace();
                        }
                    } else {
                        try {
                            localObject2 = getBaseContext().getAssets().open("low" + character + ".txt");
                            localObject3 = new byte[localObject2.available()];
                            localObject2.read(localObject3);
                            localObject2.close();
                            binding.convertedEmojeeTxt.append(new String(localObject3).replaceAll("[*]", binding.emoji.getText().toString()) + "\n\n");
                        } catch (IOException ioe22) {
                            ioe22.printStackTrace();
                        }
                    }
                }
            }
        });

        binding.copyBtn.setOnClickListener(v -> {
            int sdk = android.os.Build.VERSION.SDK_INT;
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

            if (binding.convertedEmojeeTxt.getText().toString().isEmpty()){
                Toast.makeText(this, "Nothing To Copy", Toast.LENGTH_SHORT).show();
            } else {
                if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                    clipboard.setText(binding.convertedEmojeeTxt.getText().toString());
                    Toast.makeText(this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
                } else {
                    android.content.ClipData clip = android.content.ClipData.newPlainText("Blank Message", binding.convertedEmojeeTxt.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(this, "Copied To Clipboard", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.shareBtn.setOnClickListener(v -> {
            Intent whatsappIntent = new Intent("android.intent.action.SEND");
            whatsappIntent.setType("text/plain");
            whatsappIntent.putExtra("android.intent.extra.TEXT", binding.convertedEmojeeTxt.getText().toString());
            try {
                startActivity(whatsappIntent);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(TextEmojiActivity.this, "Some problems", Toast.LENGTH_SHORT).show();
            }
        });

        binding.deleteBtn.setOnClickListener(v -> {
            binding.convertedEmojeeTxt.setText("");
            binding.emoji.setText("");
            binding.text.setText("");
        });

    }
    private void showAdWithDelay() {
        // Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Check if interstitialAd has been loaded successfully
                if(finterstitialAd == null || !finterstitialAd.isAdLoaded()) {
                    return;
                }
                // Check if ad is already expired or invalidated, and do not show ad if that is the case. You will not get paid to show an invalidated ad.
                if(finterstitialAd.isAdInvalidated()) {
                    return;
                }
                // Show the ad
                finterstitialAd.show();
            }
        }, 3000); // Show the ad after 5 minutes
    }

    @Override
    protected void onDestroy() {
        if (finterstitialAd != null) {
            finterstitialAd.destroy();
        }
        super.onDestroy();
    }
}