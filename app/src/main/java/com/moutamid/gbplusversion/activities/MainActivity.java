package com.moutamid.gbplusversion.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.moutamid.gbplusversion.R;
import com.moutamid.gbplusversion.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private InterstitialAd finterstitialAd;
    private AdView faceBookBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AudienceNetworkAds.initialize(this);

        faceBookBanner = new AdView(this, getString(R.string.fb_ad_banner), AdSize.BANNER_HEIGHT_50);

        LinearLayout adContainer = (LinearLayout) findViewById(R.id.banner_container);

        adContainer.addView(faceBookBanner);

        faceBookBanner.loadAd();

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

        binding.magic.setOnClickListener(v -> {
            startActivity(new Intent(this, TextMagicActivity.class));
        });

        binding.emotions.setOnClickListener(v -> {
            startActivity(new Intent(this, EmotionsActivity.class));
        });

        binding.happy.setOnClickListener(v -> {
            startActivity(new Intent(this, TextEmojiActivity.class));
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