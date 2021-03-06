package com.avocarrot.adapters.sample.dfp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;

public class DFPBannerActivity extends AppCompatActivity {
    @NonNull
    private static final String EXTRA_AD_UNIT_ID = "ad_unit_id";

    @NonNull
    public static Intent buildIntent(@NonNull final Context context, @NonNull final String adUnitId) {
        return new Intent(context, DFPBannerActivity.class).putExtra(EXTRA_AD_UNIT_ID, adUnitId);
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String adUnitId = getIntent().getStringExtra(EXTRA_AD_UNIT_ID);
        if (TextUtils.isEmpty(adUnitId)) {
            finish();
            return;
        }
        final PublisherAdView adView = new PublisherAdView(this);
        final AdSize adSize = AdSize.BANNER;
        final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(adSize.getWidthInPixels(this), adSize.getHeightInPixels(this));
        layoutParams.gravity = Gravity.CENTER;
        adView.setLayoutParams(layoutParams);
        adView.setAdSizes(adSize);
        adView.setAdUnitId(adUnitId);
        setContentView(adView);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(final int reason) {
                Toast.makeText(DFPBannerActivity.this, "Failed to load add, reason [" + reason + "]", Toast.LENGTH_SHORT).show();
            }
        });
        adView.loadAd(new PublisherAdRequest.Builder().build());
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
