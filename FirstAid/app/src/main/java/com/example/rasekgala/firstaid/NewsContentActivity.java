package com.example.rasekgala.firstaid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class NewsContentActivity extends Activity {

    private InterstitialAd interstitial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_content);

        this.setTitle("News Content");

        // Prepare the Interstitial Ad
        interstitial = new InterstitialAd(this);

        // Insert the Ad Unit ID
        interstitial.setAdUnitId("ca-app-pub-7098222151161497/3396407168");

        //Locate the Banner Ad in activity_main.xml
        AdView adView = (AdView) this.findViewById(R.id.adView);

        // Request for Ads
        AdRequest adRequest = new AdRequest.Builder()
                // Add a test device to show Test Ads
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("DB9E254456AEEC7A4D2E8213B1FC6DA6")
                .build();

        // Load ads into Banner Ads
        adView.loadAd(adRequest);

        // Load ads into Interstitial Ads
        interstitial.loadAd(adRequest);

        // Prepare an Interstitial Ad Listener
        interstitial.setAdListener(new AdListener() {
            public void onAdLoaded() {
                // Call displayInterstitial() function
                displayInterstitial();
            }
        });

        //get the intent send here
        Intent in = getIntent();
        //get the title and body from the intent
        News news =(News) in.getSerializableExtra("news");


        String newsContent = news.getTitle().toUpperCase()+"\n========================\n"+news.getBody();

        //set the text to the TextView
        TextView tv = (TextView)findViewById(R.id.textViewBody);

        tv.setText(newsContent);
    }

    public void displayInterstitial() {
        // If Ads are loaded, show Interstitial else show nothing.
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    @Override
    public void onBackPressed() {
        //change the behaviour of the back button
        startActivity(new Intent(this, NewsActivity.class));
        this.finish();
    }
}
