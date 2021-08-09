package augustdp.picmaker.photoframe.myapplication;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.RelativeLayout;
import androidx.annotation.Keep;
import com.google.ads.interactivemedia.v3.api.Ad;
import com.google.ads.interactivemedia.v3.api.AdPodInfo;
import com.google.ads.interactivemedia.v3.api.CompanionAd;
import com.google.ads.interactivemedia.v3.api.UiElement;
import com.google.ads.interactivemedia.v3.api.UniversalAdId;
import com.google.android.gms.ads.AdListener;

import java.util.List;
import java.util.Set;

@Keep
public class AdView extends RelativeLayout implements Ad {
    public AdView(Context context) {
        super(context);
    }
//    private final AdViewApi mAdViewApi;
//    private final AdViewParentApi mAdViewParentApi = new AdViewParentApi() {
//        /* class com.facebook.ads.AdView.AnonymousClass1 */
//
//        @Override // com.facebook.ads.internal.api.AdViewParentApi
//        @Benchmark
//        public void onConfigurationChanged(Configuration configuration) {
//            AdView.super.onConfigurationChanged(configuration);
//        }
//    };

    @Override
    public double getDuration() {
        return 0;
    }

    @Override
    public double getSkipTimeOffset() {
        return 0;
    }

    @Override
    public int getVastMediaBitrate() {
        return 0;
    }

    @Override
    public int getVastMediaHeight() {
        return 0;
    }

    @Override
    public int getVastMediaWidth() {
        return 0;
    }

    @Override
    public AdPodInfo getAdPodInfo() {
        return null;
    }

    @Override
    public String getAdId() {
        return null;
    }

    @Override
    public String getAdSystem() {
        return null;
    }

    @Override
    public String getAdvertiserName() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public String getCreativeAdId() {
        return null;
    }

    @Override
    public String getCreativeId() {
        return null;
    }

    @Override
    public String getDealId() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public String getSurveyUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public String getTraffickingParameters() {
        return null;
    }

    @Override
    public String getUniversalAdIdRegistry() {
        return null;
    }

    @Override
    public String getUniversalAdIdValue() {
        return null;
    }

    @Override
    public List<CompanionAd> getCompanionAds() {
        return null;
    }

    @Override
    public Set<UiElement> getUiElements() {
        return null;
    }

    @Override
    public boolean isLinear() {
        return false;
    }

    @Override
    public boolean isSkippable() {
        return false;
    }

    @Override
    public boolean isUiDisabled() {
        return false;
    }

    @Override
    public UniversalAdId[] getUniversalAdIds() {
        return new UniversalAdId[0];
    }

    @Override
    public String[] getAdWrapperCreativeIds() {
        return new String[0];
    }

    @Override
    public String[] getAdWrapperIds() {
        return new String[0];
    }

    @Override
    public String[] getAdWrapperSystems() {
        return new String[0];
    }
//
//    @Keep
//    public interface AdViewLoadConfig extends Ad.LoadAdConfig {
//    }
//
//    @Keep
//    public interface AdViewLoadConfigBuilder extends Ad.LoadConfigBuilder {
//        @Override // com.facebook.ads.Ad.LoadConfigBuilder
//        @Benchmark(failAtMillis = 5, warnAtMillis = 1)
//        AdViewLoadConfig build();
//
//        @Benchmark(failAtMillis = 5, warnAtMillis = 1)
//        AdViewLoadConfigBuilder withAdListener(AdListener adListener);
//
//        @Override // com.facebook.ads.Ad.LoadConfigBuilder
//        @Benchmark(failAtMillis = 5, warnAtMillis = 1)
//        AdViewLoadConfigBuilder withBid(String str);
//    }
//
//    @Deprecated
//    public void disableAutoRefresh() {
//    }
//
//    @Benchmark
//    public AdView(Context context, String str, AdSize adSize) {
//        super(context);
//        this.mAdViewApi = DynamicLoaderFactory.makeLoader(context).createAdViewApi(context, str, adSize, this.mAdViewParentApi, this);
//    }
//
//    @Benchmark
//    public AdView(Context context, String str, String str2) throws Exception {
//        super(context);
//        this.mAdViewApi = DynamicLoaderFactory.makeLoader(context).createAdViewApi(context, str, str2, this.mAdViewParentApi, this);
//    }
//
//    @Benchmark
//    @Deprecated
//    public void setAdListener(AdListener adListener) {
//        this.mAdViewApi.setAdListener(adListener);
//    }
//
//    @Override // com.facebook.ads.Ad
//    @Benchmark
//    public void loadAd() {
//        this.mAdViewApi.loadAd();
//    }
//
//    @Benchmark
//    public void loadAd(AdViewLoadConfig adViewLoadConfig) {
//        this.mAdViewApi.loadAd(adViewLoadConfig);
//    }
//
//    @Override // com.facebook.ads.Ad
//    @Deprecated
//    public void loadAdFromBid(String str) {
//        this.mAdViewApi.loadAdFromBid(str);
//    }
//
//    @Override // com.facebook.ads.Ad
//    public boolean isAdInvalidated() {
//        return this.mAdViewApi.isAdInvalidated();
//    }
//
//    @Override // com.facebook.ads.Ad
//    @Deprecated
//    public void setExtraHints(ExtraHints extraHints) {
//        this.mAdViewApi.setExtraHints(extraHints);
//    }
//
//    @Override // com.facebook.ads.Ad
//    public void destroy() {
//        this.mAdViewApi.destroy();
//    }
//
//    @Override // com.facebook.ads.Ad
//    public String getPlacementId() {
//        return this.mAdViewApi.getPlacementId();
//    }
//
//    /* access modifiers changed from: protected */
//    public void onConfigurationChanged(Configuration configuration) {
//        this.mAdViewApi.onConfigurationChanged(configuration);
//    }
//
//    @Benchmark(failAtMillis = 5, warnAtMillis = 1)
//    public AdViewLoadConfigBuilder buildLoadAdConfig() {
//        return this.mAdViewApi.buildLoadAdConfig();
//    }
}
