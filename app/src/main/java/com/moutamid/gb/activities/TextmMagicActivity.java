package com.moutamid.gb.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.moutamid.gb.R;
import com.moutamid.gb.databinding.ActivityTextMagicBinding;
import com.moutamid.gb.textmagic.Characters;
import com.moutamid.gb.textmagic.Font;
import com.moutamid.gb.textmagic.FontAdapter;

import java.util.ArrayList;

public class TextmMagicActivity extends AppCompatActivity {

    ActivityTextMagicBinding binding;
    ArrayList<Font> fonts;
    String font;
    private InterstitialAd finterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTextMagicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fonts = new ArrayList<>();

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

        binding.length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                makeStylishOf(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.fontRC.setHasFixedSize(false);
        binding.fontRC.setLayoutManager(new LinearLayoutManager(this));
    }

    public void makeStylishOf(CharSequence charSequence) {
        char[] charArray = charSequence.toString().toLowerCase().toCharArray();
        String[] strArr = new String[44];
        for (int i = 0; i < 44; i++) {
            strArr[i] = applyStyle(charArray, Characters.strings[i]);
        }
        styleTheFont(strArr);
    }

    private String applyStyle(char[] cArr, String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < cArr.length; i++) {
            if (cArr[i] - 'a' < 0 || cArr[i] - 'a' > 25) {
                stringBuffer.append(cArr[i]);
            } else {
                stringBuffer.append(strArr[cArr[i] - 'a']);
            }
        }
        return stringBuffer.toString();
    }

    private void styleTheFont(String[] strArr) {
        fonts.clear();
        font = binding.length.getText().toString().trim();
        if (!font.isEmpty()) {
            for (int i = 0; i < 44; i++) {
                Font font = new Font();
                font.fontText = strArr[i];
                fonts.add(font);
            }
            binding.fontRC.setAdapter(new FontAdapter(this, fonts, (adapterView, view, i, j) -> {}));
        }
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