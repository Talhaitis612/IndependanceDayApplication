package augustdp.picmaker.photoframe.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
//import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

import augustdp.picmaker.photoframe.myapplication.dragabble.DraggableBitmap;
import augustdp.picmaker.photoframe.myapplication.dragabble.DraggableImageView;

public class ImageProcess extends Activity implements View.OnClickListener {
    private static final int CHOOSE_FRAME = 100;
    public static HashMap<Integer, Bitmap> cache2 = new HashMap<>();
    public static HorizontalListView hlvflags;
    public static InterstitialAd mInterstitialAd;
    private static HashMap<Integer, Integer> replaceMap = new HashMap<>();
    private LinearLayout adLayout;
    private AdView adview;
    private Bitmap bitmapResulted;
    private boolean btnfaceflageclick = false;
    Uri croppedImageUri;
    DraggableImageView dragimageview;
    int index = 0;
    boolean isSaved = false;
    ImageView ivDelete;
    ImageView ivReceivedImage;
    ImageView ivSaveImg;
    ImageView ivfaceflags;
    ImageView ivflagsImage;
    ImageView ivframes;
    RelativeLayout rlImage4Save;
    private int screenHeight;
    private int screenWidth;
    private Uri shareimageUri;
    RelativeLayout viewlayout;

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.imageprocess);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenWidth = displayMetrics.widthPixels / 2;
        this.screenHeight = displayMetrics.heightPixels / 2;
        hlvflags = (HorizontalListView) findViewById(R.id.myhlvfalgs);
        this.dragimageview = (DraggableImageView) findViewById(R.id.dragimageview);
        this.dragimageview.setImageBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.mediaempty), displayMetrics.widthPixels, displayMetrics.heightPixels, false));
        this.index = 0;
//        this.adview = new AdView(this);
//        this.adview.setAdSize(AdSize.SMART_BANNER);
//        this.adview.setAdUnitId(getString(R.string.adMobId));
//        this.adLayout = (LinearLayout) findViewById(R.id.adLayout);
//        this.adLayout.addView(this.adview);
//        this.adview.loadAd(new AdRequest.Builder().build());
//        if (isOnline()) {
//            this.adLayout.setVisibility(View.GONE);
//        } else {
//            this.adLayout.setVisibility(8);
//        }
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.adMobInter));
//        requestNewInterstitial();
//        mInterstitialAd.setAdListener(new AdListener() {
//            /* class com.pak.independence.profile.pic.dp.ImageProcess.AnonymousClass1 */
//
//            @Override // com.google.android.gms.ads.AdListener
//            public void onAdClosed() {
//                ImageProcess.this.rlImage4Save.setDrawingCacheEnabled(true);
//                Bitmap createBitmap = Bitmap.createBitmap(ImageProcess.this.rlImage4Save.getDrawingCache());
//                ImageProcess.this.rlImage4Save.setDrawingCacheEnabled(false);
//                UtilSaveImage.saveReal(createBitmap, ImageProcess.this);
//                Toast.makeText(ImageProcess.this.getApplicationContext(), "Successfully Saved...", 1).show();
//                ImageProcess.this.requestNewInterstitial();
//                super.onAdClosed();
//            }
//        });
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
        }
        this.ivReceivedImage = (ImageView) findViewById(R.id.ivReceivedImage);
        this.ivflagsImage = (ImageView) findViewById(R.id.ivflags);
        this.ivSaveImg = (ImageView) findViewById(R.id.ivSaveImg);
        this.ivfaceflags = (ImageView) findViewById(R.id.ivfacesticker);
        this.ivDelete = (ImageView) findViewById(R.id.ivdelete);
        this.ivframes = (ImageView) findViewById(R.id.ivfrmes);
        this.croppedImageUri = Uri.parse(getIntent().getStringExtra("cropped_img_uri"));
        Glide.with((Activity) this).load(this.croppedImageUri).into(this.ivReceivedImage);
        this.ivReceivedImage.bringToFront();
        this.ivflagsImage.bringToFront();
        sethlvfaceflags();
        this.rlImage4Save = (RelativeLayout) findViewById(R.id.rlImage4Save);
        this.ivframes.setOnClickListener(this);
        this.ivSaveImg.setOnClickListener(this);
        this.ivfaceflags.setOnClickListener(this);
        this.ivDelete.setOnClickListener(this);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 100 && intent != null) {
            this.ivflagsImage.setBackgroundResource(FramesActivity.sticker[Integer.valueOf(intent.getStringExtra("result")).intValue()]);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
//    private void requestNewInterstitial() {
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        replaceMap.clear();
        super.onDestroy();
    }

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    private boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0045 A[SYNTHETIC, Splitter:B:29:0x0045] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x004f A[SYNTHETIC, Splitter:B:34:0x004f] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x005f A[SYNTHETIC, Splitter:B:41:0x005f] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0069 A[SYNTHETIC, Splitter:B:46:0x0069] */
    /* JADX WARNING: Removed duplicated region for block: B:53:? A[RETURN, SYNTHETIC] */
    public Bitmap loadBitmap(String str) throws Throwable {
        Throwable th;
        InputStream inputStream;
        InputStream inputStream2;
        Exception e;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        try {
            URLConnection openConnection = new URL(str).openConnection();
            openConnection.connect();
            inputStream2 = openConnection.getInputStream();
            try {
                bufferedInputStream = new BufferedInputStream(inputStream2, 8192);
                try {
                    Bitmap decodeStream = BitmapFactory.decodeStream(bufferedInputStream);
                    try {
                        bufferedInputStream.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    if (inputStream2 != null) {
                        try {
                            inputStream2.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    return decodeStream;
                } catch (Exception e4) {
                    e = e4;
                    try {
                        e.printStackTrace();
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e5) {
                                e5.printStackTrace();
                            }
                        }
                        if (inputStream2 == null) {
                            return null;
                        }
                        try {
                            inputStream2.close();
                            return null;
                        } catch (IOException e6) {
                            e6.printStackTrace();
                            return null;
                        }
                    } catch (Throwable th2) {
                        inputStream = inputStream2;
                        th = th2;
                        bufferedInputStream2 = bufferedInputStream;
                        if (bufferedInputStream2 != null) {
                        }
                        if (inputStream != null) {
                        }
                        throw th;
                    }
                }
            } catch (Exception e7) {
                e = e7;
                bufferedInputStream = null;
                e.printStackTrace();
                if (bufferedInputStream != null) {
                }
                if (inputStream2 == null) {
                }
            } catch (Throwable th3) {
                inputStream = inputStream2;
                th = th3;
                if (bufferedInputStream2 != null) {
                    try {
                        bufferedInputStream2.close();
                    } catch (IOException e8) {
                        e8.printStackTrace();
                    }
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e9) {
                        e9.printStackTrace();
                    }
                }
                throw th;
            }
        } catch (Exception e10) {
            e = e10;
            inputStream2 = null;
            bufferedInputStream = null;
            e.printStackTrace();
            if (bufferedInputStream != null) {
            }
            if (inputStream2 == null) {
            }
        } catch (Throwable th4) {
            th = th4;
            inputStream = null;
            if (bufferedInputStream2 != null) {
            }
            if (inputStream != null) {
            }
            throw th;
        }
        return null;
    }

    private void sethlvfaceflags() {
        CustomAdapterLockets customAdapterLockets = new CustomAdapterLockets(this);
        hlvflags.setVisibility(View.VISIBLE);
        hlvflags.setAdapter((ListAdapter) customAdapterLockets);
        hlvflags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.pak.independence.profile.pic.dp.ImageProcess.AnonymousClass2 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ImageProcess.this.setDraggableImgs(BitmapFactory.decodeResource(ImageProcess.this.getResources(), Constant.faceflagsarray[i]), "caps");
                Log.i("frame no", "" + i);
                ImageProcess.hlvflags.setVisibility(View.GONE);
                ImageProcess.this.btnfaceflageclick = false;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSaveImg /*{ENCODED_INT: 2131230839}*/:
//                if (mInterstitialAd.isLoaded()) {
//                    mInterstitialAd.show();
//                    return;
//                }
                this.rlImage4Save.setDrawingCacheEnabled(true);
                Bitmap createBitmap = Bitmap.createBitmap(this.rlImage4Save.getDrawingCache());
                this.rlImage4Save.setDrawingCacheEnabled(false);
                UtilSaveImage.saveReal(createBitmap, this);
                Toast.makeText(getApplicationContext(), "Successfully Saved...", Toast.LENGTH_LONG).show();
                return;
            case R.id.ivdelete /*{ENCODED_INT: 2131230840}*/:
                if (this.dragimageview.ActiveBitmap() == 0) {
                    removeImage();
                    return;
                } else {
                    Toast.makeText(getApplicationContext(), "First select image", 1).show();
                    return;
                }
            case R.id.ivfacesticker /*{ENCODED_INT: 2131230841}*/:
                if (!this.btnfaceflageclick) {
                    hlvflags.setVisibility(View.GONE);
                    hlvflags.bringToFront();
                    this.btnfaceflageclick = true;
                    return;
                }
                hlvflags.setVisibility(View.GONE);
                this.btnfaceflageclick = false;
                return;
            case R.id.ivflags /*{ENCODED_INT: 2131230842}*/:
            default:
                return;
            case R.id.ivfrmes /*{ENCODED_INT: 2131230843}*/:
                startActivityForResult(new Intent(this, FramesActivity.class), 100);
        }
    }

    private void removeImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove Change!");
        builder.setMessage("Are you sure to want to remove selected image?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.ImageProcess.AnonymousClass3 */

            public void onClick(DialogInterface dialogInterface, int i) {
                ImageProcess.this.dragimageview.deleteActiveBitmap();
                ImageProcess.replaceMap.remove(1);
                if (!ImageProcess.replaceMap.containsKey(1)) {
                    ImageProcess.this.dragimageview.setActiveBitmap();
                }
                ImageProcess.this.dragimageview.invalidate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.ImageProcess.AnonymousClass4 */

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void setDraggableImgs(Bitmap bitmap, String str) {
        DraggableBitmap draggableBitmap = new DraggableBitmap(bitmap);
        if (!replaceMap.containsKey(Integer.valueOf(this.index))) {
            replaceMap.put(Integer.valueOf(this.index), Integer.valueOf(this.dragimageview.addOverlayBitmap(draggableBitmap)));
            this.dragimageview.bringToFront();
            this.index++;
        } else {
            this.dragimageview.replaceOverlayBitmap(draggableBitmap, replaceMap.get(Integer.valueOf(this.index)).intValue());
            this.dragimageview.bringToFront();
            this.index++;
        }
        this.dragimageview.invalidate();
    }

    public class CustomAdapterLockets extends BaseAdapter {
        public Context cntxt;
        LayoutInflater layoutInflater;

        public long getItemId(int i) {
            return (long) i;
        }

        public CustomAdapterLockets(Context context) {
            this.cntxt = context;
        }

        public int getCount() {
            return Constant.faceflagsarray.length;
        }

        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = ((LayoutInflater) ImageProcess.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_data_view, viewGroup, false);
            }
//            ((ImageView) view.findViewById(R.id.textView)).setImageBitmap(ImageProcess.this.cacheImage2(Integer.valueOf(i)));
            return view;
        }
    }

    public Bitmap cacheImage2(Integer num) {
        Bitmap bitmap = null;
        try {
            if (cache2.containsKey(num)) {
                return cache2.get(num);
            }
            Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), Constant.faceflagsarray[num.intValue()], null);
            try {
                bitmap = Constant.createScaledBitmap(decodeResource, ScalingUtilities.ScalingLogic.FIT, this.screenWidth, this.screenHeight);
                cache2.put(num, bitmap);
            } catch (OutOfMemoryError unused) {
                bitmap = decodeResource;
                cache2.clear();
                return bitmap;
            }
            return bitmap;
        } catch (OutOfMemoryError unused2) {
            cache2.clear();
            return bitmap;
        }
    }
}
