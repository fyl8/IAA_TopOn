package io.iaa.topon.library;

import android.content.Context;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.LogLevel;
import com.anythink.core.api.ATSDK;

public class TopOnManager {


    private final Context context;
    private static boolean log_level = true;
    private static boolean isEnvironment = true;
    public static boolean isInitAdjust = false;

    private static TopOnManager topManager = null;

    public static TopOnManager getInstance(Context context) {
        if (topManager == null) {
            topManager = new TopOnManager(context);
        }
        return topManager;
    }

    public TopOnManager(Context context){
        this.context = context;
    }

    /**
     * 是否开启日志
     */
    public TopOnManager setEnabledLog(boolean isLog){
        log_level = isLog;
        return topManager;
    }

    /***
     * 是否开启测试环境
     */
    public TopOnManager setEnvironment(boolean environment){
        isEnvironment = environment;
        return topManager;
    }

    /**初始化Adjust*/
    public TopOnManager initAdjust(String app_token) {
//        String appToken = (TextUtils.isEmpty(app_token))?MaxConstant.ADJUST_APP_TOKEN:app_token;
        String environment = isEnvironment? AdjustConfig.ENVIRONMENT_SANDBOX:AdjustConfig.ENVIRONMENT_PRODUCTION;//测试环境   ENVIRONMENT_PRODUCTION//正式环境
        AdjustConfig config = new AdjustConfig(context, app_token, environment);
        config.setLogLevel(log_level? LogLevel.VERBOSE:LogLevel.SUPPRESS); //LogLevel.VERBOSE	启用所有日志记录  LogLevel.SUPPRESS	禁止所有日志记录
        Adjust.initSdk(config);
        isInitAdjust = true;
        return topManager;
    }

    public void initTopOn(String app_id,String app_key){
        ATSDK.init(context, app_id, app_key);
    }

}
