package augustdp.picmaker.photoframe.myapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.AdView;

import java.util.HashMap;

public class FramesActivity extends Activity {
    //    public static InterstitialAd mInterstitialAd;
    public static int[] sticker = {R.drawable.f_03, R.drawable.f_01, R.drawable.f_02, R.drawable.f_04, R.drawable.f_05, R.drawable.f_06, R.drawable.f_07, R.drawable.f_08, R.drawable.f_09, R.drawable.f_10, R.drawable.f_11, R.drawable.f_12, R.drawable.f_13, R.drawable.f_14, R.drawable.f_15, R.drawable.f_16, R.drawable.f_17, R.drawable.f_18, R.drawable.f_19, R.drawable.f_20, R.drawable.f_21, R.drawable.f_22, R.drawable.f_23, R.drawable.f_24, R.drawable.f_25, R.drawable.f_26, R.drawable.f_27, R.drawable.f_28, R.drawable.f_29, R.drawable.f_30, R.drawable.f_31, R.drawable.f_32, R.drawable.f_33, R.drawable.f_34, R.drawable.f_35, R.drawable.f_36, R.drawable.f_37, R.drawable.f_38, R.drawable.f_39};
    private LinearLayout adLayout;
    private AdView adview;
    private HashMap<Integer, Bitmap> cache = new HashMap<>();
    private int cardsindex = 1;
    //    private ImageAdapter galleryAdapter;
    private GridView gridView;
    int position = 0;
    int screenHeight;
    private int screenWidth;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_frames);
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
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.adMobInter));
//        requestNewInterstitial();
//        mInterstitialAd.setAdListener(new AdListener() {
//            /* class com.pak.independence.profile.pic.dp.FramesActivity.AnonymousClass1 */
//
//            @Override // com.google.android.gms.ads.AdListener
//            public void onAdClosed() {
//                Intent intent = new Intent();
//                intent.putExtra("result", FramesActivity.this.position + "");
//                FramesActivity.this.setResult(-1, intent);
//                FramesActivity.this.finish();
//                FramesActivity.this.requestNewInterstitial();
//                super.onAdClosed();
//            }
//        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.cardsindex = extras.getInt("catagory");
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels;
        this.screenHeight = displayMetrics.heightPixels;
//        this.gridView = (GridView) findViewById(R.id.gvWishingCards);
//        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            /* class com.pak.independence.profile.pic.dp.FramesActivity.AnonymousClass2 */
//
////            @Override // android.widget.AdapterView.OnItemClickListener
////            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
////                if (FramesActivity.mInterstitialAd.isLoaded()) {
////                    FramesActivity.mInterstitialAd.show();
////                    FramesActivity.this.position = i;
////                    return;
////                }
////                Intent intent = new Intent();
////                intent.putExtra("result", i + "");
////                FramesActivity.this.setResult(-1, intent);
////                FramesActivity.this.finish();
////            }
////        });
//        this.galleryAdapter = new ImageAdapter(this, this.screenWidth, this.cardsindex);
//        this.gridView.setAdapter((ListAdapter) this.galleryAdapter);
//    }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
//    private void requestNewInterstitial() {
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//    }

        class ImageAdapter extends BaseAdapter {
            private Context context;
            int index = 1;
            private LayoutInflater layoutInflater;
            private int width;

            public Object getItem(int i) {
                return null;
            }

            public long getItemId(int i) {
                return 0;
            }

            public ImageAdapter(Context context2) {
                this.context = context2;
            }

            public ImageAdapter(Context context2, int i, int i2) {
                this.context = context2;
                this.width = i;
                this.index = i2;
            }

            public int getCount() {
                return FramesActivity.sticker.length;
            }

            public View getView(int i, View view, ViewGroup viewGroup) {
                this.layoutInflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
                View inflate = this.layoutInflater.inflate(R.layout.galleryadapter, (ViewGroup) null);
                RelativeLayout relativeLayout = (RelativeLayout) inflate.findViewById(R.id.galleryLayout);
                ImageView imageView = new ImageView(this.context);
                relativeLayout.addView(imageView, new RelativeLayout.LayoutParams(-1, this.width));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                relativeLayout.setLayoutParams(new AbsListView.LayoutParams(-1, this.width));
//            imageView.setImageBitmap(FramesActivity.this.cacheImage(Integer.valueOf(i), this.index));
                return inflate;
            }
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
//    private Bitmap cacheImage(Integer num, int i) {
//        Bitmap bitmap = null;
//        try {
//            if (this.cache.containsKey(num)) {
//                return this.cache.get(num);
//            }
//            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), sticker[num.intValue()], null);
//            try {
//                bitmap = Constant.createScaledBitmap(decodeResource, ScalingUtilities.ScalingLogic.FIT, this.screenWidth, this.screenHeight / 2);
//                this.cache.put(num, bitmap);
//            } catch (OutOfMemoryError unused) {
//                bitmap = decodeResource;
//                this.cache.clear();
//                return bitmap;
//            }
//            return bitmap;
//        } catch (OutOfMemoryError unused2) {
//            this.cache.clear();
//            return bitmap;
//        }
//    }

//        private boolean isOnline () {
//            NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
//            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
//        }
    }

}