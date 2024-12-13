package io.iaa.topon.ad;

import android.app.Application;

import io.iaa.topon.library.TopOnManager;

public class TopOnApplication extends Application {
    //app_key: a5f0fff6f479ba52f2eaf3970fdb4be26
    //应用ID： h675ba24fea676
    //广告位ID：n675ba286f407f
    @Override
    public void onCreate() {
        super.onCreate();
        TopOnManager.getInstance(this)
                .setEnabledLog(true)
                .setEnvironment(true)
                .initAdjust("1f4ixw19lt0g")
                .initTopOn("h675ba24fea676","a5f0fff6f479ba52f2eaf3970fdb4be26");
    }



}
