package augustdp.picmaker.photoframe.myapplication.myappgallery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import java.io.File;

import augustdp.picmaker.photoframe.myapplication.R;
import augustdp.picmaker.photoframe.myapplication.photocommon.BitmapScaler;
import augustdp.picmaker.photoframe.myapplication.photocommon.PhotoConstant;

public class ImageDetail extends Activity implements View.OnClickListener {
    public static boolean deleted = false;
    private LinearLayout adLayout;
//    private AdView adView;
//    private AdView adview;
    private ImageView deleteBtn;
    private String imgUrl = null;
//    private InterstitialAd mInterstitialAd;
    private ImageView mainImageView;
    private ImageView shareBtn;
    private ImageView whatsappBtn;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.image_detail);
        this.mainImageView = (ImageView) findViewById(R.id.mainImageView);
//        this.adview = new AdView(this);
//        this.adview.setAdSize(AdSize.SMART_BANNER);
//        this.adview.setAdUnitId(getString(R.string.adMobId));
//        this.adLayout = (LinearLayout) findViewById(R.id.adLayout);
//        this.adLayout.addView(this.adview);
//        this.adview.loadAd(new AdRequest.Builder().build());
//        if (isOnline()) {
//            this.adLayout.setVisibility(0);
//        } else {
//            this.adLayout.setVisibility(8);
//        }
//        this.mInterstitialAd = new InterstitialAd(this);
//        this.mInterstitialAd.setAdUnitId(getResources().getString(R.string.adMobInter));
//        requestNewInterstitial();
//        this.mInterstitialAd.setAdListener(new AdListener() {
//            /* class com.pak.independence.profile.pic.dp.myappgallery.ImageDetail.AnonymousClass1 */
//
//            @Override // com.google.android.gms.ads.AdListener
//            public void onAdClosed() {
//                ImageDetail.this.deleted();
//                ImageDetail.this.requestNewInterstitial();
//                super.onAdClosed();
//            }
//        });
        this.whatsappBtn = (ImageView) findViewById(R.id.whatsappbtn);
        this.shareBtn = (ImageView) findViewById(R.id.ivshareButton);
        this.deleteBtn = (ImageView) findViewById(R.id.delButton);
        this.imgUrl = getIntent().getStringExtra("ImgUrl");
        this.whatsappBtn.setOnClickListener(this);
        this.shareBtn.setOnClickListener(this);
        this.deleteBtn.setOnClickListener(this);
        ImageLoader.getInstance().loadImage(this.imgUrl, BitmapScaler.getDisplayImageOptions(true), new ImageLoadingListener() {
            /* class com.pak.independence.profile.pic.dp.myappgallery.ImageDetail.AnonymousClass2 */

            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
            public void onLoadingCancelled(String str, View view) {
            }

            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
            public void onLoadingStarted(String str, View view) {
            }

            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
            public void onLoadingFailed(String str, View view, FailReason failReason) {
                PhotoConstant.errorAlert(ImageDetail.this);
            }

            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
            public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                ImageDetail.this.mainImageView.setImageBitmap(bitmap);
            }
        });
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
//    private void requestNewInterstitial() {
//        this.mInterstitialAd.loadAd(new AdRequest.Builder().build());
//    }

    public void onClick(View view) {
        int id = view.getId();
        if (id != R.id.delButton) {
            if (id == R.id.ivshareButton) {
                Uri uriForFile = FileProvider.getUriForFile(this, "com.pak.independence.profile.pic.dp.provider", new File(Uri.parse(this.imgUrl).getPath()));
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("image/jpeg");
                intent.putExtra("android.intent.extra.STREAM", uriForFile);
                startActivity(Intent.createChooser(intent, "via"));
            } else if (id == R.id.whatsappbtn) {
                shareOnWhatsApp();
            }
        }
//        else if (this.mInterstitialAd.isLoaded()) {
//            this.mInterstitialAd.show();
//        }
        else {
            deleted();
        }
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void deleted() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.removeOpt));
        builder.setMessage(getResources().getString(R.string.removeTxt));
        builder.setPositiveButton(getResources().getString(R.string.yestxt), new DialogInterface.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.myappgallery.ImageDetail.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                if (new File(Uri.parse(ImageDetail.this.imgUrl).getPath()).delete()) {
                    ImageDetail.this.setResult(-1);
                    ImageDetail.this.finish();
                    return;
                }
                Toast.makeText(ImageDetail.this.getApplicationContext(), ImageDetail.this.getResources().getString(R.string.errorImg), 1).show();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.canceltxt), new DialogInterface.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.myappgallery.ImageDetail.AnonymousClass4 */

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private void shareOnWhatsApp() {
        if (!whatsappInstalledOrNot()) {
            Toast.makeText(this, getResources().getString(R.string.whatsappTxt), 1).show();
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en")));
            return;
        }
        Uri uriForFile = FileProvider.getUriForFile(this, "com.pak.independence.profile.pic.dp.provider", new File(Uri.parse(this.imgUrl).getPath()));
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/jpeg");
        intent.setPackage("com.whatsapp");
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.shareTxt)));
    }

    private boolean whatsappInstalledOrNot() {
        try {
            getPackageManager().getApplicationInfo("com.whatsapp", 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public void onPause() {
        super.onPause();
    }

    public void onResume() {
        super.onResume();
    }

    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
