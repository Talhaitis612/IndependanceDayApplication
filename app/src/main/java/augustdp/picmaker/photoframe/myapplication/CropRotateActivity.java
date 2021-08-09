package augustdp.picmaker.photoframe.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import augustdp.picmaker.photoframe.myapplication.photocommon.BaseRotater;
import augustdp.picmaker.photoframe.myapplication.photocommon.BitmapScaler;
import augustdp.picmaker.photoframe.myapplication.photocommon.CCWRotater;
import augustdp.picmaker.photoframe.myapplication.photocommon.CWRotater;
import augustdp.picmaker.photoframe.myapplication.photocommon.CropImageView;
import augustdp.picmaker.photoframe.myapplication.photocommon.MenuActivityHelper;
import augustdp.picmaker.photoframe.myapplication.photocommon.PhotoConstant;

public class CropRotateActivity extends Activity {
    private final String TAG = CropRotateActivity.class.getSimpleName();
    private Bitmap mBitmapIn;
    private Bitmap mBitmapOut;
    private ImageView mClipBtn;
    private Button mCropCancel;
    private CropImageView mCropDisplayView;
    private RelativeLayout mCropListView;
    private Button mCropOK;
    private ImageView mDoneBtn;
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        /* class com.pak.independence.profile.pic.dp.CropRotateActivity.AnonymousClass2 */

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.clip /*{ENCODED_INT: 2131230778}*/:
                    CropRotateActivity.this.showCropView();
                    return;
                case R.id.crop_cancel /*{ENCODED_INT: 2131230786}*/:
                    CropRotateActivity.this.cleanEditView();
                    return;
                case R.id.crop_ok /*{ENCODED_INT: 2131230789}*/:
                    CropRotateActivity.this.mBitmapOut = CropRotateActivity.this.mCropDisplayView.getCropBitmap();
                    CropRotateActivity.this.mBitmapIn = CropRotateActivity.this.mBitmapOut.copy(CropRotateActivity.this.mBitmapOut.getConfig(), true);
                    CropRotateActivity.this.mOriginalDisplayView.setImageBitmap(CropRotateActivity.this.mBitmapOut);
                    CropRotateActivity.this.mOriginalDisplayView.invalidate();
                    CropRotateActivity.this.cleanEditView();
                    return;
                case R.id.done /*{ENCODED_INT: 2131230803}*/:
                    try {
                        CropRotateActivity.this.savedImagePath = MenuActivityHelper.saveCropBit(CropRotateActivity.this.mBitmapOut, CropRotateActivity.this);
                        MenuActivityHelper.setCropImgPath(CropRotateActivity.this.savedImagePath);
                        CropRotateActivity.this.setResult(-1);
                        CropRotateActivity.this.finish();
                        return;
                    } catch (Exception e) {
                        Toast.makeText(CropRotateActivity.this, e.toString(), 1).show();
                        return;
                    }
                case R.id.rotate /*{ENCODED_INT: 2131230896}*/:
                    CropRotateActivity.this.showRotateView();
                    return;
                case R.id.rotate_ccw /*{ENCODED_INT: 2131230897}*/:
                    Log.i(CropRotateActivity.this.TAG, RotaterName.CCW_ROTATER.toString());
                    CropRotateActivity.this.executeRotater(RotaterName.CCW_ROTATER);
                    return;
                case R.id.rotate_cw /*{ENCODED_INT: 2131230898}*/:
                    Log.i(CropRotateActivity.this.TAG, RotaterName.CW_ROTATER.toString());
                    CropRotateActivity.this.executeRotater(RotaterName.CW_ROTATER);
                    return;
                default:
                    return;
            }
        }
    };
    private ImageView mOriginalDisplayView;
    private ImageView mRotateBtn;
    private Button mRotateCCW;
    private Button mRotateCW;
    private RelativeLayout mRotateListView;
    private BaseRotater mRotater;
    private String savedImagePath;

    /* access modifiers changed from: private */
    public enum RotaterName {
        CW_ROTATER("CW Rotater"),
        CCW_ROTATER("CCW Rotater");
        
        private final String name;

        private RotaterName(String str) {
            this.name = str;
        }

        public String toString() {
            return this.name;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_crop_rotate);
        this.mOriginalDisplayView = (ImageView) findViewById(R.id.original_display);
        this.mCropDisplayView = (CropImageView) findViewById(R.id.crop_display);
        this.mCropListView = (RelativeLayout) findViewById(R.id.crop_list);
        this.mCropCancel = (Button) findViewById(R.id.crop_cancel);
        this.mCropOK = (Button) findViewById(R.id.crop_ok);
        this.mRotateListView = (RelativeLayout) findViewById(R.id.rotate_list);
        this.mRotateCW = (Button) findViewById(R.id.rotate_cw);
        this.mRotateCCW = (Button) findViewById(R.id.rotate_ccw);
        this.mClipBtn = (ImageView) findViewById(R.id.clip);
        this.mRotateBtn = (ImageView) findViewById(R.id.rotate);
        this.mDoneBtn = (ImageView) findViewById(R.id.done);
        this.mCropCancel.setOnClickListener(this.mOnClickListener);
        this.mCropOK.setOnClickListener(this.mOnClickListener);
        this.mRotateCW.setOnClickListener(this.mOnClickListener);
        this.mRotateCCW.setOnClickListener(this.mOnClickListener);
        this.mClipBtn.setOnClickListener(this.mOnClickListener);
        this.mRotateBtn.setOnClickListener(this.mOnClickListener);
        this.mDoneBtn.setOnClickListener(this.mOnClickListener);
        Uri data = getIntent().getData();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ImageSize imageSize = new ImageSize(displayMetrics.widthPixels / 2, displayMetrics.heightPixels / 2);
        if (data != null) {
            ImageLoader.getInstance().loadImage(data.toString(), imageSize, BitmapScaler.getDisplayImageOptions(false), new ImageLoadingListener() {
                /* class com.pak.independence.profile.pic.dp.CropRotateActivity.AnonymousClass1 */

                @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                public void onLoadingCancelled(String str, View view) {
                }

                @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                public void onLoadingStarted(String str, View view) {
                }

                @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                public void onLoadingFailed(String str, View view, FailReason failReason) {
                    PhotoConstant.errorAlert(CropRotateActivity.this);
                }

                @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                    CropRotateActivity.this.mBitmapIn = bitmap;
                    CropRotateActivity.this.mBitmapOut = CropRotateActivity.this.mBitmapIn.copy(CropRotateActivity.this.mBitmapIn.getConfig(), true);
                    CropRotateActivity.this.mOriginalDisplayView.setImageBitmap(CropRotateActivity.this.mBitmapOut);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void executeRotater(RotaterName rotaterName) {
        if (this.mRotater != null) {
            this.mRotater.destroy();
        }
        switch (rotaterName) {
            case CW_ROTATER:
                this.mRotater = new CWRotater();
                break;
            case CCW_ROTATER:
                this.mRotater = new CCWRotater();
                break;
        }
        this.mBitmapOut = this.mRotater.rotate(this.mBitmapOut);
        this.mBitmapIn = this.mRotater.rotate(this.mBitmapIn);
        updateDisplay();
    }

    private void updateDisplay() {
        if (this.mRotateListView.isShown()) {
            this.mOriginalDisplayView.setImageBitmap(this.mBitmapOut);
            this.mOriginalDisplayView.invalidate();
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showCropView() {
        if (!this.mCropListView.isShown()) {
            this.mCropListView.setVisibility(View.VISIBLE);
            this.mCropDisplayView.setBitmap(this.mBitmapOut);
            this.mCropDisplayView.reset();
            this.mCropDisplayView.setVisibility(View.VISIBLE);
            this.mOriginalDisplayView.setVisibility(View.VISIBLE);
        } else {
            this.mCropListView.setVisibility(View.GONE);
            this.mCropDisplayView.setVisibility(View.GONE);
            this.mOriginalDisplayView.setVisibility(View.GONE);
        }
        if (this.mRotateListView.isShown()) {
            this.mRotateListView.setVisibility(View.GONE);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void showRotateView() {
        if (!this.mOriginalDisplayView.isShown()) {
            this.mCropDisplayView.setVisibility(View.GONE);
            this.mOriginalDisplayView.setVisibility(View.VISIBLE);
        }
        if (this.mCropListView.isShown()) {
            this.mCropListView.setVisibility(View.GONE);
        }
        if (!this.mRotateListView.isShown()) {
            this.mRotateListView.setVisibility(View.VISIBLE);
        } else {
            this.mRotateListView.setVisibility(8);
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void cleanEditView() {
        if (!this.mOriginalDisplayView.isShown()) {
            this.mCropDisplayView.setVisibility(View.GONE);
            this.mOriginalDisplayView.setVisibility(View.VISIBLE);
        }
        if (this.mCropListView.isShown()) {
            this.mCropListView.setVisibility(View.GONE);
        }
        if (this.mRotateListView.isShown()) {
            this.mRotateListView.setVisibility(View.GONE);
        }
    }
}
