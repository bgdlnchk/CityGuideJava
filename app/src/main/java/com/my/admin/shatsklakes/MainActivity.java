package com.my.admin.shatsklakes;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.kobakei.ratethisapp.RateThisApp;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment mCurrentFragment;
    InterstitialAd mInterstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Integrate RateThisApp
        RateThisApp.onCreate(this);
        RateThisApp.showRateDialogIfNeeded(this);
        //Set the parameters when RateThisApp Window will be shown
        RateThisApp.Config config = new RateThisApp.Config(2, 3);
        RateThisApp.init(config);

        //Change AdMob App ID on your in res/values/strings
        MobileAds.initialize(this,getString(R.string.app_AdMob_ID));
        mInterstitial = new InterstitialAd(getApplicationContext());
        //Change Ad ID on your in a folder res/values/strings
        mInterstitial.setAdUnitId(getString(R.string.Interstitial_Ad_ID));
        mInterstitial.loadAd(new AdRequest.Builder().build());
        mInterstitial.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                mInterstitial.loadAd(new AdRequest.Builder().build());
            }
        });

        //Start application from HotelsFragment
        mCurrentFragment = new HotelsFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.mainFrame,mCurrentFragment);
        ft.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_share) {
            //Make ShareIntent here
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            //Paste your app's link here
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        } else if(id == R.id.action_rate) {
            //Make Intent with link on your app in market
            Intent mRateIntent = new Intent(android.content.Intent.ACTION_VIEW);
            //Paste your app's link here
            mRateIntent.setData(Uri.parse("https://play.google.com/store"));
            startActivity(mRateIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void showInterstitialAd(){
        //Show Interstitial Ad
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //ClickListener for NavigationDrawer's items
        if (id == R.id.nav_hotels) {
            mCurrentFragment = new HotelsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame,mCurrentFragment);
            ft.commit();
            showInterstitialAd();
        }else if(id == R.id.nav_shops){
            mCurrentFragment = new ShopsFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame,mCurrentFragment);
            ft.commit();
            showInterstitialAd();
        }else if(id == R.id.nav_cafes){
            mCurrentFragment = new CafesFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame,mCurrentFragment);
            ft.commit();
            showInterstitialAd();
        }else if(id == R.id.nav_pharmacy){
            mCurrentFragment = new PharmacyFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame,mCurrentFragment);
            ft.commit();
            showInterstitialAd();
        }else if(id == R.id.nav_tours){
            mCurrentFragment = new OtherFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainFrame,mCurrentFragment);
            ft.commit();
            showInterstitialAd();
        }else if(id == R.id.nav_share){
            //Make ShareIntent here
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            //Paste your app's link here
            shareIntent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store");
            shareIntent.setType("text/plain");
            startActivity(shareIntent);
        }else if (id == R.id.nav_rate){
            //Make Intent with link on your app in market
            Intent RateIntent = new Intent(android.content.Intent.ACTION_VIEW);
            //Paste your app's link here
            RateIntent.setData(Uri.parse("https://play.google.com/store"));
            startActivity(RateIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;

    }
}
