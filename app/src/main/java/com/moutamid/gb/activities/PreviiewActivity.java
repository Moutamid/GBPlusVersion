package com.moutamid.gb.activities;

import static android.content.ContentValues.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.moutamid.gb.R;
import com.moutamid.gb.SharedPrefs;
import com.moutamid.gb.Utils;
import com.moutamid.gb.adapters.PreviewAdapter;
import com.moutamid.gb.databinding.ActivityPreviewBinding;
import com.moutamid.gb.models.StatusModel;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;

public class PreviiewActivity extends AppCompatActivity {
    private InterstitialAd finterstitialAd;
    ArrayList<StatusModel> imageList;
    int position;
    PreviewAdapter previewAdapter;
    boolean statusDownload;

    ActivityPreviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPreviewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        Utils.setLanguage(PreviiewActivity.this, SharedPrefs.getLang(PreviiewActivity.this));

        imageList = getIntent().getParcelableArrayListExtra("images");
        position = getIntent().getIntExtra("position", 0);
        statusDownload = getIntent().getBooleanExtra("statusdownload", false);

        binding.backbtn.setOnClickListener(v -> {
            onBackPressed();
            finish();
        });

        previewAdapter = new PreviewAdapter(PreviiewActivity.this, imageList);
        binding.viewPager.setAdapter(previewAdapter);
        binding.viewPager.setCurrentItem(position);

        if (statusDownload) {
            binding.downloadIV.setVisibility(View.GONE);
        } else {
            binding.downloadIV.setVisibility(View.VISIBLE);
        }

        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                File myFile, file;
                file = new File(imageList.get(binding.viewPager.getCurrentItem()).getFilePath());
                File extStore = Environment.getExternalStorageDirectory();
                if (isVideoFile(imageList.get(binding.viewPager.getCurrentItem()).getFilePath()))
                    myFile = new File(extStore.getAbsolutePath() + "/Download" + File.separator + getResources().getString(R.string.app_name) + "/Videos" + File.separator + file.getName());
                else
                    myFile = new File(extStore.getAbsolutePath() + "/Download" + File.separator + getResources().getString(R.string.app_name) + "/Images" + File.separator + file.getName());


                if (myFile.exists()) {
                    binding.downloadIV.setVisibility(View.GONE);
                } else {
                    binding.downloadIV.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        binding.downloadIV.setOnClickListener(clickListener);
        binding.shareIV.setOnClickListener(clickListener);
        binding.deleteIV.setOnClickListener(clickListener);
        binding.backbtn.setOnClickListener(clickListener);


    }

    private ViewPager.OnPageChangeListener mPageLitsener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            File myFile, file;
            file = new File(imageList.get(binding.viewPager.getCurrentItem()).getFilePath());
            File extStore = Environment.getExternalStorageDirectory();
            if (isVideoFile(imageList.get(binding.viewPager.getCurrentItem()).getFilePath()))
                myFile = new File(extStore.getAbsolutePath() + "/Download" + File.separator + getResources().getString(R.string.app_name) + "/Videos" + File.separator + file.getName());
            else
                myFile = new File(extStore.getAbsolutePath() + "/Download" + File.separator + getResources().getString(R.string.app_name) + "/Images" + File.separator + file.getName());


            if (myFile.exists()) {
                binding.downloadIV.setVisibility(View.GONE);
            } else {
                binding.downloadIV.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.backbtn:
                    finish();
                    break;

                case R.id.downloadIV:
                    if (imageList.size() > 0) {


                        try {
                            binding.downloadIV.setVisibility(View.GONE);
                            Utils.download(PreviiewActivity.this, imageList.get(binding.viewPager.getCurrentItem()).getFilePath());
                            Toast.makeText(PreviiewActivity.this, getResources().getString(R.string.saved_success), Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(PreviiewActivity.this, "Sorry we can't move file.try with other file.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        finish();
                    }
                    break;

                case R.id.shareIV:
                    if (imageList.size() > 0) {
                        if (isImageFile(imageList.get(binding.viewPager.getCurrentItem()).getFilePath())) {
                            File imageFileToShare = new File(imageList.get(binding.viewPager.getCurrentItem()).getFilePath());

                            Intent share = new Intent(Intent.ACTION_SEND);
                            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            share.setType("image/*");
                            Uri photoURI = FileProvider.getUriForFile(
                                    getApplicationContext(), getApplicationContext()
                                            .getPackageName() + ".provider", imageFileToShare);
                            share.putExtra(Intent.EXTRA_STREAM,
                                    photoURI);
                            startActivity(Intent.createChooser(share, "Share via"));

                        } else if (isVideoFile(imageList.get(binding.viewPager.getCurrentItem()).getFilePath())) {

                            Uri videoURI = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext()
                                    .getPackageName() + ".provider", new File(imageList.get(binding.viewPager.getCurrentItem()).getFilePath()));
                            Intent videoshare = new Intent(Intent.ACTION_SEND);
                            videoshare.setType("*/*");
                            videoshare.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            videoshare.putExtra(Intent.EXTRA_STREAM, videoURI);

                            startActivity(videoshare);
                        }
                    } else {
                        finish();
                    }
                    break;

                case R.id.deleteIV:
                    if (imageList.size() > 0) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PreviiewActivity.this);
                        alertDialog.setTitle(R.string.confirm);
                        alertDialog.setMessage(R.string.del_status);
                        alertDialog.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                int currentItem = 0;

                                File file = new File(imageList.get(binding.viewPager.getCurrentItem()).getFilePath());
                                if (file.exists()) {
                                    boolean del = file.delete();
                                    if (imageList.size() > 0 && binding.viewPager.getCurrentItem() < imageList.size()) {
                                        currentItem = binding.viewPager.getCurrentItem();
                                    }
                                    imageList.remove(binding.viewPager.getCurrentItem());
                                    previewAdapter = new PreviewAdapter(PreviiewActivity.this, imageList);
                                    binding.viewPager.setAdapter(previewAdapter);

                                    Intent intent = new Intent();
                                    setResult(10, intent);

                                    if (imageList.size() > 0) {
                                        binding.viewPager.setCurrentItem(currentItem);
                                    } else {
                                        finish();
                                    }
                                }
                            }
                        });
                        alertDialog.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    } else {
                        finish();
                    }
                    break;

                default:
                    break;
            }
        }
    };

    private void showAdWithDelay() {
        /**
         * Here is an example for displaying the ad with delay;
         * Please do not copy the Handler into your project
         */
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
        }, 5000); // Show the ad after 5 minutes
    }

    @Override
    protected void onDestroy() {
        if (finterstitialAd != null) {
            finterstitialAd.destroy();
        }
        super.onDestroy();
    }

    public boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }
}