package io.iaa.topon.library;

import android.app.Activity;
import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATShowConfig;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAd;
import com.anythink.rewardvideo.api.ATRewardVideoListener;

public class TopOnAds {

    public static void loadRewardAd(Activity activity,String placement_id,String scenario_id,TopOnRewardedAdsListener listener){
        ATRewardVideoAd mRewardVideoAd = new ATRewardVideoAd(activity, placement_id);
        mRewardVideoAd.setAdRevenueListener(atAdInfo -> {
            Log.d("IAA","AD_ID： "+atAdInfo.getPlacementId()+", 收入："+atAdInfo.getPublisherRevenue());
            TopOnEvent.logRevenueEvent(activity,atAdInfo);
        });
        //设置广告监听
        mRewardVideoAd.setAdListener(new ATRewardVideoListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                if (mRewardVideoAd.isAdReady()) {
                    ATShowConfig showConfig = new ATShowConfig.Builder().scenarioId(scenario_id).build();
                    mRewardVideoAd.show(activity,showConfig);
                    if(null!=listener)listener.onAdLoadShow();
                } else {
                    mRewardVideoAd.load();
                }
            }
            @Override
            public void onRewardedVideoAdFailed(AdError adError) {
                //注意：禁止在此回调中执行广告的加载方法进行重试，否则会引起很多无用请求且可能会导致应用卡顿
                Log.d("IAA","onAdLoadFailed:"+topOnCodeByMsg(adError.getCode())+",maxError:"+adError.getCode());
                if(null!=listener)listener.onAdLoadFailed("onAdLoadFailed:"+topOnCodeByMsg(adError.getCode())+",maxError:"+adError.getCode());
            }
            @Override
            public void onRewardedVideoAdPlayStart(ATAdInfo adInfo) {
                //建议在此回调中调用load进行广告的加载，方便下一次广告的展示（不需要调用isAdReady()）
            }
            @Override
            public void onRewardedVideoAdPlayEnd(ATAdInfo atAdInfo) {}
            @Override
            public void onRewardedVideoAdPlayFailed(AdError adError, ATAdInfo atAdInfo) {
                Log.d("IAA","onAdLoadFailed:"+topOnCodeByMsg(adError.getCode())+",maxError:"+adError.getCode());
                if(null!=listener)listener.onAdLoadFailed("onAdLoadFailed:"+topOnCodeByMsg(adError.getCode())+",maxError:"+adError.getCode());
            }
            @Override
            public void onRewardedVideoAdClosed(ATAdInfo atAdInfo) {
                if(null!=listener)listener.onAdHidden(atAdInfo);
            }
            @Override
            public void onReward(ATAdInfo atAdInfo) {
                //建议在此回调中下发奖励
                if(null!=listener)listener.onUserRewarded(atAdInfo);
            }
            @Override
            public void onRewardedVideoAdPlayClicked(ATAdInfo atAdInfo) {
                if(null!=listener)listener.onAdClicked(atAdInfo);
            }
        });
        mRewardVideoAd.load();
    }


    private static String topOnCodeByMsg(String code){
        switch (code){
            case "10001": return "App ID或App Key错误，请检查初始化TopOn SDK时传入的App ID和App Key";
            case "10003": return "1. App ID错误，请检查初始化TopOn SDK时传入的App ID\n" +
                    "2. TopOn广告位ID与App ID不匹配，请检查代码中调用load方法时传入的Placement ID";
            case "10004": return "TopOn广告位ID错误，请检查调用load方法时传入的Placement ID";
            case "9999": return "1. 网络请求出现错误，检查网络状态是否正常\n" +
                    "2. 出现错误信息：chain validation failed，请检查是否有调整过测试设备的系统时间";
            case "9990": return "HTTP接口请求返回的状态错误，需要联系TopOn同事查看错误信息";
            case "9991": return "接口请求返回的业务代码错误，需要联系TopOn同事查看错误信息";
            case "9992": return "GDPR的等级设置过低，检查是否手动设置了FORBIDDEN等级";
            case "2001": return "广告加载超时，检查当前的测试的广告源是否是海外平台，手机网络是否已经翻墙\n";
            case "2002": return "TopOn的SDK包导入不全，缺失第三方广告厂商的Adapter包（anythink_network_*.aar），确认是否已经按照指引导入聚合的第三方需要的SDK包\n";
            case "2003": return "当前广告位的展示次数已经达到上限，需要确认TopOn的后台配置是否限制了该广告位的展示次数\n";
            case "2004": return "当前广告位处于非展示时间段，需要确认TopOn的后台配置是否限制了广告位的展示间隔\n";
            case "2005": return "该广告位处于加载阶段，同一个广告位发起请求后，在接收到加载成功或失败的回调之前，该广告位不能发起下一次的加载，请等待加载成功、失败的回调\n";
            case "2006": return "检查导入第三方广告平台的SDK包是否齐全，如果齐全则检查导入的版本是否与GitHub上指定的版本是否相符合，否则需要将第三方SDK包补充完整\n";
            case "2007": return "通常发生于，在加载失败的回调中立刻发起广告加载。禁止在加载失败的回调中立刻发起广告加载，距离上一次该广告位加载失败需满足一定时间间隔才可发起广告加载，请延迟调用广告加载的时间\n";
            case "2008": return "同一个广告位加载失败后禁止在加载失败的回调里立马调用load方法进行重试，请延迟10s以上再进行重试\n";
            case "2009": return "在一定时间间隔内广告位的加载次数达到上限\n";
            case "3001": return "策略获取错误\n" +
                    "1. 检查网络是否正常\n" +
                    "2. 检查使用的appid，appkey，placementid是否匹配\n" +
                    "3. 检查代码中appid，appkey，placementid是否正确并且匹配（不能包含空格）\n" +
                    "调用 ATSDK.setNetworkLogDebug(true); 在Logcat中过滤 anythink 可查看当前传入SDK的参数，请检查这些参数";
            case "3002": return "传入的appid,appkey，placementid其中有一个为空字符\n" +
                    "调用 ATSDK.setNetworkLogDebug(true); 在Logcat中过滤 anythink 可查看当前传入SDK的参数，请检查这些参数";
            case "3003": return "广告位与调用的API不匹配，例如：Banner的广告位调用了激励视频的API去加载广告\n";
            case "4001": return "通常发生于第三方广告平台返回错误导致没有广告填充，可通过AdError.getFullErrorInfo()获取完全的错误信息，通过platformCode及platformMsg查看广告平台的错误码及错误信息，请查看第三方广告平台错误码进行排查";
            case "4002": return "Context的上下文已经被销毁，需要重新创建相应的广告类型对象再重新发起广告加载\n";
            case "4003": return "该广告位的状态已经关闭，检查TopOn后台该广告位的状态开关是否开启\n";
            case "4004": return "该广告位没有在TopOn后台配置广告源的信息，需要到TopOn后台-聚合管理 为广告位添加第三方广告平台的广告源";
            case "4005": return "广告位下的所有广告源被过滤，可能的原因如下：\n" +
                    "1. 检查是否在TopOn后台设置了广告源的展示上限、展示间隔\n" +
                    "2. 如果只配置了头部竞价广告源，询价失败时，头部竞价广告源将被过滤";
            case "4006": return "视频播放失败，参照 4001错误码 进行排查";
            case "4007": return "广告源竞价失败，参照 4001错误码 进行排查\n";
            case "4008": return "因为开发者代码中的自定义过滤逻辑，导致广告源被过滤。如果过滤不符合预期，请排查自定义过滤逻辑";
            case "4009": return "调试模式下，该广告位没有配置广告源信息";
            default: return "未知错误："+code;
        }
    }




}
