package io.iaa.topon.library;

import com.anythink.core.api.ATAdInfo;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxReward;

public interface TopOnRewardedAdsListener {
    void onAdLoadShow();
    void onAdHidden(ATAdInfo adInfo);
    void onUserRewarded(ATAdInfo adInfo);
    void onAdClicked(ATAdInfo adInfo);
    void onAdLoadFailed(String s);
}
