package com.moutamid.gbplusversion.activities;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.moutamid.gbplusversion.R;
import com.moutamid.gbplusversion.Utils;
import com.moutamid.gbplusversion.adapters.StatusAdapter;
import com.moutamid.gbplusversion.databinding.ActivityStatusBinding;
import com.moutamid.gbplusversion.databinding.ActivityTextEmojiBinding;
import com.moutamid.gbplusversion.models.StatusModel;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import java.io.File;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatusActivity extends AppCompatActivity implements StatusAdapter.OnCheckboxListener {
    ActivityStatusBinding binding;

    ArrayList<StatusModel> f = new ArrayList<>();
    StatusAdapter myAdapter;
    ArrayList<StatusModel> filesToDelete = new ArrayList<>();
    String type;
    File file, myFile;
    boolean fileIsThere = false;
    RelativeLayout loaderlay, emptyLay;
    private InterstitialAd finterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AudienceNetworkAds.initialize(this);

        loaderlay = findViewById(R.id.loaderLay);
        emptyLay = findViewById(R.id.emptyLay);

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

        try {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                type = extras.getString("type");
                if (type.equals("WA")) {
                    binding.typeSS.setText("Whats");
                } else if (type.equals("WAGB")) {
                    binding.typeSS.setText("Whats GB");
                } else if (type.equals("WABS")) {
                    binding.typeSS.setText("Whats Business");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        populateGrid();


        binding.deleteIV.setOnClickListener(view -> {

            if (!filesToDelete.isEmpty()) {
                new AlertDialog.Builder(StatusActivity.this)
                        .setMessage(getResources().getString(R.string.delete_alert))
                        .setCancelable(true)
                        .setNegativeButton(getResources().getString(R.string.yes), (dialogInterface, i) -> {
                            int success = -1;
                            ArrayList<StatusModel> deletedFiles = new ArrayList<>();

                            for (StatusModel details : filesToDelete) {
                                File file = new File(details.getFilePath());
                                if (file.exists()) {
                                    if (file.delete()) {
                                        deletedFiles.add(details);
                                        if (success == 0) {
                                            return;
                                        }
                                        success = 1;
                                    } else {
                                        success = 0;
                                    }
                                } else {
                                    success = 0;
                                }
                            }

                            filesToDelete.clear();
                            for (StatusModel deletedFile : deletedFiles) {
                                f.remove(deletedFile);
                            }
                            myAdapter.notifyDataSetChanged();
                            if (success == 0) {
                                Toast.makeText(StatusActivity.this, getResources().getString(R.string.delete_error), Toast.LENGTH_SHORT).show();
                            } else if (success == 1) {
                                Toast.makeText(StatusActivity.this, getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();
                            }
                            binding.actionLay.setVisibility(View.GONE);
                            binding.selectAll.setChecked(false);
                        })
                        .setPositiveButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
            }
        });

        binding.downloadIV.setOnClickListener(view -> {

            if (!filesToDelete.isEmpty()) {

                int success = -1;
                ArrayList<StatusModel> deletedFiles = new ArrayList<>();

                for (StatusModel details : filesToDelete) {
                    File file = new File(details.getFilePath());
                    if (file.exists()) {
                        if (Utils.download(StatusActivity.this, details.getFilePath())) {
                            deletedFiles.add(details);
                            if (success == 0) {
                                return;
                            }
                            success = 1;
                        } else {
                            success = 0;
                        }
                    } else {
                        success = 0;
                    }

                }

                filesToDelete.clear();
                for (StatusModel deletedFile : deletedFiles) {
                    f.contains(deletedFile.selected = false);
                }
                myAdapter.notifyDataSetChanged();
                if (success == 0) {
                    Toast.makeText(StatusActivity.this, getResources().getString(R.string.save_error), Toast.LENGTH_SHORT).show();
                } else if (success == 1) {
                    Toast.makeText(StatusActivity.this, getResources().getString(R.string.save_success), Toast.LENGTH_SHORT).show();
                }
                binding.actionLay.setVisibility(View.GONE);
                binding.selectAll.setChecked(false);
            }
        });

        binding.selectAll.setOnCheckedChangeListener((compoundButton, b) -> {

            if (!compoundButton.isPressed()) {
                return;
            }

            filesToDelete.clear();

            for (int i = 0; i < f.size(); i++) {
                if (!f.get(i).selected) {
                    b = true;
                    break;
                }
            }

            if (b) {
                for (int i = 0; i < f.size(); i++) {
                    f.get(i).selected = true;
                    filesToDelete.add(f.get(i));
                }
                binding.selectAll.setChecked(true);
            } else {
                for (int i = 0; i < f.size(); i++) {
                    f.get(i).selected = false;
                }
                binding.actionLay.setVisibility(View.GONE);
            }
            myAdapter.notifyDataSetChanged();
        });

    }

    public void populateGrid() {
        new loadDataAsync().execute();
    }

    class loadDataAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loaderlay.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getFromSdcard();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new Handler().postDelayed(() -> {
                myAdapter = new StatusAdapter(StatusActivity.this, f, StatusActivity.this);
                binding.recylerViewImage.setHasFixedSize(true);
                binding.recylerViewImage.setLayoutManager(new GridLayoutManager(StatusActivity.this, 2));
                binding.recylerViewImage.setItemAnimator(new DefaultItemAnimator());
                binding.recylerViewImage.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();


                loaderlay.setVisibility(View.GONE);

                if (f == null || f.size() == 0) {
                    emptyLay.setVisibility(View.VISIBLE);
                } else {
                    emptyLay.setVisibility(View.GONE);
                }
            }, 1000);
        }
    }

    public void getFromSdcard() {
        if (type.equals("WA")) {
            file = getWhatsupFolder();
        } else if (type.equals("WAGB")) {
            file = getWhatsupGFolder();
        } else if (type.equals("WABS")) {
            file = getWhatsupBFolder();
        }
        f = new ArrayList<>();
        if (file.isDirectory()) {
            File[] listFile = file.listFiles();
            if (listFile != null) {
                Arrays.sort(listFile, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                for (int i = 0; i < listFile.length; i++) {
                    File extStore = Environment.getExternalStorageDirectory();
                    if (isVideoFile(listFile[i].getAbsolutePath()))
                        myFile = new File(extStore.getAbsolutePath() + "/Download" + File.separator + getResources().getString(R.string.app_name) + "/Videos" + File.separator + listFile[i].getName());
                    else
                        myFile = new File(extStore.getAbsolutePath() + "/Download" + File.separator + getResources().getString(R.string.app_name) + "/Images" + File.separator + listFile[i].getName());
                    if (!listFile[i].getAbsolutePath().contains(".nomedia"))
                        f.add(new StatusModel(listFile[i].getAbsolutePath(), myFile.exists()));
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (filesToDelete.size() > 0) {
            filesToDelete.clear();
            binding.selectAll.setChecked(false);
            for (int i = 0; i < f.size(); i++) {
                f.get(i).selected = false;
            }
            binding.actionLay.setVisibility(View.GONE);
            myAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        myAdapter.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == 10) {
            myAdapter.notifyDataSetChanged();

            getFromSdcard();

            myAdapter = new StatusAdapter(StatusActivity.this, f, StatusActivity.this);
            binding.recylerViewImage.setHasFixedSize(true);
            binding.recylerViewImage.setLayoutManager(new GridLayoutManager(StatusActivity.this, 2));
            binding.recylerViewImage.setItemAnimator(new DefaultItemAnimator());
            binding.recylerViewImage.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();
            binding.actionLay.setVisibility(View.GONE);
            binding.selectAll.setChecked(false);
        }
    }


    public boolean isVideoFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("video");
    }

    public File getWhatsupFolder() {
        if (new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp/WhatsApp" + File.separator + "Media" + File.separator + ".Statuses").isDirectory()) {
            return new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp/WhatsApp" + File.separator + "Media" + File.separator + ".Statuses");
        } else {
            return new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp" + File.separator + "Media" + File.separator + ".Statuses");
        }
    }

    public File getWhatsupBFolder() {
        if (new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business" + File.separator + "Media" + File.separator + ".Statuses").isDirectory()) {
            return new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business" + File.separator + "Media" + File.separator + ".Statuses");
        } else {
            return new File(Environment.getExternalStorageDirectory() + File.separator + "WhatsApp Business" + File.separator + "Media" + File.separator + ".Statuses");
        }
    }

    public File getWhatsupGFolder() {
        if (new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp.w4b/GBWhatsApp" + File.separator + "Media" + File.separator + ".Statuses").isDirectory()) {
            return new File(Environment.getExternalStorageDirectory() + File.separator + "Android/media/com.whatsapp.w4b/GBWhatsApp" + File.separator + "Media" + File.separator + ".Statuses");
        } else {
            return new File(Environment.getExternalStorageDirectory() + File.separator + "GBWhatsApp" + File.separator + "Media" + File.separator + ".Statuses");
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
        }, 5000); // Show the ad after 5 minutes
    }

    @Override
    protected void onDestroy() {
        if (finterstitialAd != null) {
            finterstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onCheckboxListener(View view, List<StatusModel> list) {
        filesToDelete.clear();
        for (StatusModel details : list) {
            if (details.isSelected()) {
                filesToDelete.add(details);
            }
        }
        binding.selectAll.setChecked(filesToDelete.size() == f.size());
        if (!filesToDelete.isEmpty()) {
            binding.actionLay.setVisibility(View.VISIBLE);
            return;
        }

        binding.selectAll.setChecked(false);
        binding.actionLay.setVisibility(View.GONE);
    }

}