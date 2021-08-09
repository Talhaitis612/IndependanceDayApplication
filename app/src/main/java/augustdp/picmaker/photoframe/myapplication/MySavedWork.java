package augustdp.picmaker.photoframe.myapplication;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.google.android.gms.ads.AdView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import augustdp.picmaker.photoframe.myapplication.myappgallery.ImageDetail;
import augustdp.picmaker.photoframe.myapplication.photocommon.BitmapScaler;

public class MySavedWork extends Activity {
    private LinearLayout adLayout;
    private AdView adView;
    private AdView adview;
    private GridView gridView;
    private int height;
    private String[] imageFileList;
    private DisplayImageOptions options;
    private ArrayList<String> photo = new ArrayList<>();
    private int width;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.gallery);
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
        this.options = BitmapScaler.getDisplayImageOptions(true);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.width = (displayMetrics.widthPixels / 3) - 30;
        this.height = displayMetrics.heightPixels / 4;
        this.gridView = (GridView) findViewById(R.id.gridView);
        this.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* class com.pak.independence.profile.pic.dp.myappgallery.MySavedWork.AnonymousClass1 */

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (!((String) MySavedWork.this.photo.get(i)).equalsIgnoreCase("empty.png")) {
                    Intent intent = new Intent(MySavedWork.this, ImageDetail.class);
                    intent.putExtra("ImgUrl", (String) MySavedWork.this.photo.get(i));
                    MySavedWork.this.startActivityForResult(intent, 45);
                }
            }
        });
        new MyGalleryAsy().execute();
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1) {
            this.photo.clear();
            new MyGalleryAsy().execute();
        }
    }

    public class MyGalleryAsy extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public MyGalleryAsy() {
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            this.dialog = ProgressDialog.show(MySavedWork.this, "", "Loading ...", true);
            this.dialog.show();
        }

        /* access modifiers changed from: protected */
        public Void doInBackground(Void... voidArr) {
            MySavedWork.this.readImage();
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void r4) {
            int i;
            this.dialog.dismiss();
            if (MySavedWork.this.photo.size() == 0) {
                i = 2;
            } else if ((MySavedWork.this.photo.size() - 1) % 3 == 0) {
                i = 1;
            } else {
                i = (MySavedWork.this.photo.size() - 2) % 3 == 0 ? 0 : -1;
            }
            for (int i2 = 0; i2 <= i; i2++) {
                MySavedWork.this.photo.add("empty.png");
            }
            MySavedWork.this.gridView.setAdapter((ListAdapter) new WorkAdapter(MySavedWork.this));
        }
    }

    /* access modifiers changed from: private */
    /* access modifiers changed from: public */
    private void readImage() {
        try {
            File realFolderFile = UtilSaveImage.getRealFolderFile();
            if (!realFolderFile.exists()) {
                realFolderFile.mkdirs();
            }
            String str = Uri.fromFile(realFolderFile).toString() + "/";
            if (realFolderFile.isDirectory()) {
                this.imageFileList = realFolderFile.list();
                if (this.imageFileList != null) {
                    for (int i = 0; i < this.imageFileList.length; i++) {
                        this.photo.add(str + this.imageFileList[i]);
                    }
                }
            }
        } catch (Exception unused) {
        }
    }

    public class WorkAdapter extends BaseAdapter {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private LayoutInflater inflater;

        public long getItemId(int i) {
            return (long) i;
        }

        WorkAdapter(Context context) {
            this.inflater = LayoutInflater.from(context);
            new HashMap();
        }

        public int getCount() {
            return MySavedWork.this.photo.size();
        }

        public Object getItem(int i) {
            return MySavedWork.this.photo.get(i);
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder viewHolder;
            if (view == null) {
                view = this.inflater.inflate(R.layout.galleryadapter, viewGroup, false);
                viewHolder = new ViewHolder();
                viewHolder.imglesson = (ImageView) view.findViewById(R.id.imglesson);
                viewHolder.bookLayout = (RelativeLayout) view.findViewById(R.id.bookLayout);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(MySavedWork.this.width, MySavedWork.this.height);
            layoutParams.addRule(1);
            if (i % 3 == 0) {
                layoutParams.addRule(11);
                layoutParams.setMargins(20, 0, 0, 0);
            } else if ((i - 1) % 3 == 0) {
                layoutParams.addRule(14);
                layoutParams.setMargins(12, 0, 12, 0);
            } else if ((i - 2) % 3 == 0) {
                layoutParams.addRule(9);
                layoutParams.setMargins(0, 0, 24, 0);
            }
            if (((String) MySavedWork.this.photo.get(i)).equalsIgnoreCase("empty.png")) {
                viewHolder.imglesson.setBackgroundResource(R.drawable.mediaempty);
            } else {
                ImageLoader.getInstance().loadImage((String) MySavedWork.this.photo.get(i), new ImageSize(150, 150), MySavedWork.this.options, new ImageLoadingListener() {
                    /* class com.pak.independence.profile.pic.dp.myappgallery.MySavedWork.WorkAdapter.AnonymousClass1 */

                    @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                    public void onLoadingCancelled(String str, View view) {
                    }

                    @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                    public void onLoadingStarted(String str, View view) {
                    }

                    @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                    public void onLoadingFailed(String str, View view, FailReason failReason) {
                        Toast.makeText(MySavedWork.this.getApplicationContext(), failReason.toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingListener
                    public void onLoadingComplete(String str, View view, Bitmap bitmap) {
                        viewHolder.imglesson.setImageBitmap(bitmap);
                    }
                }, new ImageLoadingProgressListener() {
                    /* class com.pak.independence.profile.pic.dp.myappgallery.MySavedWork.WorkAdapter.AnonymousClass2 */

                    @Override // com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener
                    public void onProgressUpdate(String str, View view, int i, int i2) {
                    }
                });
            }
            viewHolder.bookLayout.setLayoutParams(layoutParams);
            return view;
        }
    }

    static class ViewHolder {
        RelativeLayout bookLayout;
        ImageView imglesson;

        ViewHolder() {
        }
    }

    public void onPause() {
        if (this.adView != null) {
            this.adView.pause();
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (this.adView != null) {
            this.adView.resume();
        }
    }

    public void onDestroy() {
        if (this.adView != null) {
            this.adView.destroy();
        }
        super.onDestroy();
        System.gc();
    }

    public boolean isOnline() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
