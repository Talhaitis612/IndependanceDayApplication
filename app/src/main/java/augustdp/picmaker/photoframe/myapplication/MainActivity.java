package augustdp.picmaker.photoframe.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.ads.AdSize;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import augustdp.picmaker.photoframe.myapplication.photocommon.MenuActivityHelper;
import augustdp.picmaker.photoframe.myapplication.photocommon.PhotoConstant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static final int PERMISSION_REQUEST_CODE = 1;
    public static final int REQUEST_CODE_GALLERY = 1;
    public static final int REQUEST_CODE_IMAGE_EDITOR = 2;
    public static final int REQUEST_CODE_TAKE_PICTURE = 0;
    public static String TEMP_PHOTO_FILE_NAME = "temp_img.png";
    public static InterstitialAd mInterstitialAd;
    private LinearLayout adLayout;
    private AdView adview;
    EditText editText;
    private boolean isClick = false;
    ImageView ivCamera;
    ImageView ivGallery;
    ImageView ivMywork;
    ImageView ivmoreapps;
    ImageView ivrateus;
    private Uri mCurrentPhotoPath;
    ProgressDialog mProgressDialog;

    public void onClick(View view) {
    }

    /* access modifiers changed from: protected */
    @Override // com.pak.independence.profile.pic.dp.RunTimePermiss
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(R.layout.activity_main);
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId(getResources().getString(R.string.adMobInter));
//        requestNewInterstitial();
////        mInterstitialAd.setAdListener(new AdListener() {
//            /* class com.pak.independence.profile.pic.dp.MainActivity.AnonymousClass1 */
//
//            @Override // com.google.android.gms.ads.AdListener
//            public void onAdClosed() {
//                MainActivity.this.startActivity(new Intent(MainActivity.this, MySavedWork.class));
//                MainActivity.this.requestNewInterstitial();
//                super.onAdClosed();
//            }
//        });
//        this.adview = new AdView(this);
//        this.adview.setAdSize(AdSize.SMART_BANNER);
//        this.adview.setAdUnitId(getString(R.string.adMobId));
        this.adLayout = (LinearLayout) findViewById(R.id.adLayout);
        this.adLayout.addView(this.adview);
//        this.adview.loadAd(new AdRequest.Builder().build());
//        if (isOnline()) {
//            this.adLayout.setVisibility(0);
//        } else {
//            this.adLayout.setVisibility(8);
//        }
//        showPermission();
        this.ivCamera = (ImageView) findViewById(R.id.ivCamera);
        this.ivGallery = (ImageView) findViewById(R.id.ivGallery);
        this.ivMywork = (ImageView) findViewById(R.id.ivmywork);
        this.ivmoreapps = (ImageView) findViewById(R.id.ivmoreapp);
        this.ivrateus = (ImageView) findViewById(R.id.ivrateus);
        this.ivCamera.setOnClickListener(new View.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.MainActivity.AnonymousClass2 */

            public void onClick(View view) {
                MenuActivityHelper.deleteFolder(MainActivity.this);
                MainActivity.this.dispatchTakePictureIntent();
            }
        });
        this.ivGallery.setOnClickListener(new View.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.MainActivity.AnonymousClass3 */

            public void onClick(View view) {
                MenuActivityHelper.deleteFolder(MainActivity.this);
                MenuActivityHelper.callGalleryApp(MainActivity.this);
            }
        });
        this.ivMywork.setOnClickListener(new View.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.MainActivity.AnonymousClass4 */

            public void onClick(View view) {
//                if (MainActivity.mInterstitialAd.isLoaded()) {
//                    MainActivity.mInterstitialAd.show();
//                } else {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, MySavedWork.class));
                }

        });
        this.ivmoreapps.setOnClickListener(new View.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.MainActivity.AnonymousClass5 */

            public void onClick(View view) {
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Simple+Developerz+free+Photo+Frames+and+Dp")));
                } catch (ActivityNotFoundException unused) {
                }
            }
        });
        this.ivrateus.setOnClickListener(new View.OnClickListener() {
            /* class com.pak.independence.profile.pic.dp.MainActivity.AnonymousClass6 */

            public void onClick(View view) {
                try {
                    MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.pak.independence.profile.pic.dp")));
                } catch (ActivityNotFoundException unused) {
                }
            }
        });
        this.mProgressDialog = new ProgressDialog(this);
        this.mProgressDialog.setMessage("A message");
        this.mProgressDialog.setIndeterminate(true);
        this.mProgressDialog.setProgressStyle(1);
        this.mProgressDialog.setCancelable(true);
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
//    private void requestNewInterstitial() {
//        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//    }

//    private void showPermission() {
//        requestAppPermissions(new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"}, R.string.msg, 1);
//    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            switch (i) {
                case 200:
                    if (i2 != 0) {
                        try {
                            if (this.mCurrentPhotoPath != null) {
                                Intent intent2 = new Intent(this, CropRotateActivity.class);
                                intent2.setData(this.mCurrentPhotoPath);
                                startActivityForResult(intent2, PhotoConstant.TRANSFORM_REQUEST);
                                break;
                            } else {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            Log.v("", e.toString());
                            break;
                        }
                    } else {
                        return;
                    }
                case PhotoConstant.GALLERY_REQUEST:
                    if (!(intent == null || intent.getData() == null)) {
                        intent.setClass(this, CropRotateActivity.class);
                        startActivityForResult(intent, PhotoConstant.TRANSFORM_REQUEST);
                        break;
                    }
                case PhotoConstant.TRANSFORM_REQUEST:
                    String cropImgPath = MenuActivityHelper.getCropImgPath();
                    String str = null;
                    if (cropImgPath != null) {
                        str = Uri.parse("file:///" + cropImgPath).toString();
                    }
                    if (str != null) {
                        ImageLoader.getInstance().loadImage(str, new ImageLoadingListener() {
                            /* class com.pak.independence.profile.pic.dp.MainActivity.AnonymousClass7 */

                            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                            public void onLoadingCancelled(String str, View view) {
                            }

                            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                            public void onLoadingStarted(String str, View view) {
                            }

                            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                            public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                                Intent intent = new Intent(MainActivity.this, ImageProcess.class);
                                intent.putExtra("cropped_img_uri", str);
                                MainActivity.this.startActivity(intent);
                            }

                            @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                            public void onLoadingFailed(String str, View view, FailReason failReason) {
                                PhotoConstant.errorAlert(MainActivity.this);
                            }
                        });
                        break;
                    }
                    break;
            }
            super.onActivityResult(i, i2, intent);
        }
    }


//    @Override // com.pak.independence.profile.pic.dp.RunTimePermiss
//    public void onPermissionsGranted(int i) {
//        if (this.isClick) {
//            startActivity(new Intent(this, MainActivity.class));
//        }
//    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void dispatchTakePictureIntent() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            File file = null;
            try {
                file = createImageFile();
            } catch (IOException unused) {
            }
            if (file != null) {
                try {
                    if (Build.VERSION.SDK_INT >= 24) {
                        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        this.mCurrentPhotoPath = FileProvider.getUriForFile(this, "com.pak.independence.profile.pic.dp.provider", file);
                        intent.putExtra("output", this.mCurrentPhotoPath);
                        startActivityForResult(intent, 200);
                        return;
                    }
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    this.mCurrentPhotoPath = Uri.fromFile(file);
                    intent.putExtra("output", this.mCurrentPhotoPath);
                    startActivityForResult(intent, 200);
                } catch (Exception e) {
                    e.toString();
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        File createTempFile = File.createTempFile(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), ".jpg", new File(UtilSaveImage.gettempFolderPath()));
        Log.d("MainActivity", "" + createTempFile);
        return createTempFile;
    }

    private boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
//        requestNewInterstitial();
        super.onResume();
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context2) {
            this.context = context2;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Removed duplicated region for block: B:57:0x00b8 A[SYNTHETIC, Splitter:B:57:0x00b8] */
        /* JADX WARNING: Removed duplicated region for block: B:60:0x00bd A[Catch:{ IOException -> 0x00c0 }] */
        /* JADX WARNING: Removed duplicated region for block: B:63:0x00c2  */
        /* JADX WARNING: Removed duplicated region for block: B:67:0x00c9 A[SYNTHETIC, Splitter:B:67:0x00c9] */
        /* JADX WARNING: Removed duplicated region for block: B:70:0x00ce A[Catch:{ IOException -> 0x00d1 }] */
        /* JADX WARNING: Removed duplicated region for block: B:73:0x00d3  */
        public String doInBackground(String... strArr) {
            HttpURLConnection httpURLConnection;
            InputStream inputStream;
            Throwable th;
            Exception e;
            FileOutputStream fileOutputStream;
            FileOutputStream fileOutputStream2 = null;
            try {
                httpURLConnection = (HttpURLConnection) new URL(strArr[0]).openConnection();
                try {
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() != 200) {
                        String str = "Server returned HTTP " + httpURLConnection.getResponseCode() + " " + httpURLConnection.getResponseMessage();
                        if (httpURLConnection != null) {
                            httpURLConnection.disconnect();
                        }
                        return str;
                    }
                    int contentLength = httpURLConnection.getContentLength();
                    inputStream = httpURLConnection.getInputStream();
                    try {
                        fileOutputStream = new FileOutputStream("/sdcard/file_name.extension");
                    } catch (Exception e2) {
                        e = e2;
                        try {
                            String exc = e.toString();
                            if (fileOutputStream2 != null) {
                            }
                            if (inputStream != null) {
                            }
                            if (httpURLConnection != null) {
                            }
                            return exc;
                        } catch (Throwable th2) {
                            th = th2;
                            if (fileOutputStream2 != null) {
                                try {
                                    fileOutputStream2.close();
                                } catch (IOException unused) {
                                    if (httpURLConnection != null) {
                                    }
                                    throw th;
                                }
                            }
                            if (inputStream != null) {
                                inputStream.close();
                            }
                            if (httpURLConnection != null) {
                                httpURLConnection.disconnect();
                            }
                            throw th;
                        }
                    }
                    try {
                        byte[] bArr = new byte[4096];
                        long j = 0;
                        while (true) {
                            int read = inputStream.read(bArr);
                            if (read == -1) {
                                try {
                                    fileOutputStream.close();
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                } catch (IOException unused2) {
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                return null;
                            } else if (isCancelled()) {
                                inputStream.close();
                                try {
                                    fileOutputStream.close();
                                    if (inputStream != null) {
                                        inputStream.close();
                                    }
                                } catch (IOException unused3) {
                                }
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                }
                                return null;
                            } else {
                                j += (long) read;
                                if (contentLength > 0) {
                                    publishProgress(Integer.valueOf((int) ((100 * j) / ((long) contentLength))));
                                }
                                fileOutputStream.write(bArr, 0, read);
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        fileOutputStream2 = fileOutputStream;
                        String exc2 = e.toString();
                        if (fileOutputStream2 != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        return exc2;
                    } catch (Throwable th3) {
                        th = th3;
                        fileOutputStream2 = fileOutputStream;
                        if (fileOutputStream2 != null) {
                        }
                        if (inputStream != null) {
                        }
                        if (httpURLConnection != null) {
                        }
                        throw th;
                    }
                } catch (Exception e4) {
                    e = e4;
                    inputStream = null;
                    String exc22 = e.toString();
                    if (fileOutputStream2 != null) {
                        try {
                            fileOutputStream2.close();
                        } catch (IOException unused4) {
                            if (httpURLConnection != null) {
                            }
                            return exc22;
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    return exc22;
                } catch (Throwable th4) {
                    th = th4;
                    inputStream = null;
                    if (fileOutputStream2 != null) {
                    }
                    if (inputStream != null) {
                    }
                    if (httpURLConnection != null) {
                    }
                    throw th;
                }
            } catch (Exception e5) {
                e = e5;
                httpURLConnection = null;
                inputStream = null;
                String exc222 = e.toString();
                if (fileOutputStream2 != null) {
                }
                if (inputStream != null) {
                }
                if (httpURLConnection != null) {
                }
                return exc222;
            } catch (Throwable th5) {
                th = th5;
                httpURLConnection = null;
                inputStream = null;
                if (fileOutputStream2 != null) {
                }
                if (inputStream != null) {
                }
                if (httpURLConnection != null) {
                }
                try {
                    throw th;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            super.onPreExecute();
            this.mWakeLock = ((PowerManager) this.context.getSystemService(Context.POWER_SERVICE)).newWakeLock(1, getClass().getName());
            this.mWakeLock.acquire();
            MainActivity.this.mProgressDialog.show();
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(Integer... numArr) {
            super.onProgressUpdate(numArr);
            MainActivity.this.mProgressDialog.setIndeterminate(false);
            MainActivity.this.mProgressDialog.setMax(100);
            MainActivity.this.mProgressDialog.setProgress(numArr[0].intValue());
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(String str) {
            this.mWakeLock.release();
            MainActivity.this.mProgressDialog.dismiss();
            if (str != null) {
                Context context2 = this.context;
                Toast.makeText(context2, "Download error: " + str, Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this.context, "File downloaded", Toast.LENGTH_LONG).show();
        }
    }
}
