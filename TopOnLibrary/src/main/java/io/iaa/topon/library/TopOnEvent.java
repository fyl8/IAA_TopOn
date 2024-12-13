package io.iaa.topon.library;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustAdRevenue;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.anythink.core.api.ATAdInfo;
import com.applovin.mediation.MaxAd;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;

public class TopOnEvent {

    public static void logRevenueEvent(Context context, ATAdInfo atAdInfo){
        if(TopOnManager.isInitAdjust){
            AdjustAdRevenue adjustAdRevenue = new AdjustAdRevenue("topon_sdk");
            adjustAdRevenue.setRevenue(atAdInfo.getPublisherRevenue(), "USD");
            adjustAdRevenue.setAdRevenueNetwork(atAdInfo.getAdNetworkType());
            adjustAdRevenue.setAdRevenueUnit(atAdInfo.getPlacementId());
            Adjust.trackAdRevenue(adjustAdRevenue);
        }
        Bundle bundle = new Bundle();
        bundle.putDouble("revenue",atAdInfo.getPublisherRevenue());
        bundle.putString("networkName",atAdInfo.getAdNetworkType()+"");
        bundle.putString("adUnitId",atAdInfo.getPlacementId()+"");
        FirebaseAnalytics.getInstance(context).logEvent("ad_impression",bundle);
    }

    public static void logEventADorFA(Context context,String ad_key,String fa_name){
        if(!TextUtils.isEmpty(ad_key)){
            AdjustEvent event = new AdjustEvent(ad_key);
            Adjust.trackEvent(event);
        }
        FirebaseAnalytics.getInstance(context).logEvent(fa_name,new Bundle());
    }

    public static void logEventADorFA(Context context, String ad_key,String fa_key, Map<String,String> maps){
        Bundle bundle = new Bundle();
        AdjustEvent event = null;
        if(!TextUtils.isEmpty(ad_key)){
            event = new AdjustEvent(ad_key);
        }
        for (String key : maps.keySet()) {
            bundle.putString(key,String.valueOf(maps.get(key)));
            if(null!=event)event.addCallbackParameter(key,String.valueOf(maps.get(key)));
        }
        if(null!=event)Adjust.trackEvent(event);
        FirebaseAnalytics.getInstance(context).logEvent(fa_key,new Bundle());
    }



}
