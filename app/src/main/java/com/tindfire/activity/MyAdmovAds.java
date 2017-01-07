package com.tindfire.activity;

import android.content.Context;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by vcareall on 5/7/16.
 */
public class MyAdmovAds {
    final static String AD_UNIT_ID = "ca-app-pub-6977458640858756/6156426626";//"ca-app-pub-5148921223084059/1272533321";
    private static AdView adView;
    final static String AD_UNIT_ID_INTERTITISAL = "ca-app-pub-6977458640858756/5801879428";//"ca-app-pub-5148921223084059/2749266527";
    private static InterstitialAd interstitial;
//	private static InterstitialAd mInterstitialAd;

    public static AdView loadAdmodAd(Context context) {

        // Create an ad.
        adView = new AdView(context);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId(AD_UNIT_ID);

        AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
        // return ad view
        return adView;

    }
    public static InterstitialAd loadintertitisalAdmodAd(Context context) {
        // Create an ad.
        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(context);
        // Insert the Ad Unit ID

        interstitial.setAdUnitId(AD_UNIT_ID_INTERTITISAL);

        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        interstitial.loadAd(adRequest);
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });
        // return ad view
        return interstitial;
    }
    public static void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }


}
