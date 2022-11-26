package com.moutamid.gbplusversion.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.moutamid.gbplusversion.R;
import com.moutamid.gbplusversion.adapters.CaptionAdapter;
import com.moutamid.gbplusversion.databinding.ActivityCaptionsBinding;

public class CaptionsActivity extends AppCompatActivity {
    ActivityCaptionsBinding binding;
    String[] captions;
    String name;
    int position;
    private InterstitialAd finterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = getIntent().getStringExtra("image");
        position = getIntent().getIntExtra("P", 0);

        binding.captionTitle.setText(name);

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