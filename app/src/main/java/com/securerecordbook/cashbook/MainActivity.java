package com.securerecordbook.cashbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.securerecordbook.cashbook.classes.CustomLoadingScreen;

public class MainActivity extends AppCompatActivity {

    public final String TAG = this.getClass().getName();
    public static SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor mEditor;
    public static final String PREFS_CB ="com.securerecordbook.cashbook";


    // Firebase Variables
    FirebaseRemoteConfig mFirebaseRemoteConfig;

    // Custom Loading Screen
    CustomLoadingScreen customLoadingScreen = CustomLoadingScreen.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);

        setContentView(R.layout.activity_main);

        //customLoadingScreen.showCustomLoadingScreen(this);

        mSharedPreferences = getSharedPreferences(PREFS_CB, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        // Status Bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.colorPrimaryVariant));


        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings mFirebaseRemoteConfigSettings = new FirebaseRemoteConfigSettings.Builder().setMinimumFetchIntervalInSeconds(0).build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(mFirebaseRemoteConfigSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.firebase_defaults);

        getFirebaseData();

        if (mSharedPreferences.getBoolean("FirstStart", true)) {
            replaceFragment(new PromotionFragment());
        } else if (!mSharedPreferences.getBoolean("loggedIn", false)) {
            replaceFragment(new AuthFragment());
        }

    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).disallowAddToBackStack().commitNow();
    }

    private void getFirebaseData() {
        mFirebaseRemoteConfig.fetchAndActivate().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mEditor.putBoolean("HavePromotion", mFirebaseRemoteConfig.getBoolean("HavePromotion"));
                Log.i(TAG, "onComplete: " + mFirebaseRemoteConfig.getBoolean("HavePromotion"));
                mEditor.commit();
            }else{
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}