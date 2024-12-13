package io.iaa.topon.ad;

import android.app.Activity;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.anythink.core.api.ATAdInfo;

import io.iaa.topon.library.TopOnAds;
import io.iaa.topon.library.TopOnEvent;
import io.iaa.topon.library.TopOnRewardedAdsListener;

public class MainActivity extends AppCompatActivity {

    //app_key: a5f0fff6f479ba52f2eaf3970fdb4be26
    //应用ID： h675ba24fea676
    //广告位ID：n675ba286f407f
    private Activity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        activity = this;
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_load).setOnClickListener((v)->{
            TopOnAds.loadRewardAd(activity, "n675ba286f407f", "", new TopOnRewardedAdsListener() {
                @Override
                public void onAdLoadShow() {
                    TopOnEvent.logEventADorFA(activity,"acloiyxa","ad_show");
                }
                @Override
                public void onAdHidden(ATAdInfo adInfo) {
                    TopOnEvent.logEventADorFA(activity,"acloiyxa","ad_hidden");
                }
                @Override
                public void onUserRewarded(ATAdInfo adInfo) {
                    TopOnEvent.logEventADorFA(activity,"acloiyxa","ad_reward");
                }
                @Override
                public void onAdClicked(ATAdInfo adInfo) {
                    TopOnEvent.logEventADorFA(activity,"acloiyxa","ads_click");
                }
                @Override
                public void onAdLoadFailed(String s) {
                    TopOnEvent.logEventADorFA(activity,"acloiyxa","ad_failed");
                }
            });
        });
    }
}