package augustdp.picmaker.photoframe.myapplication.photocommon;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.File;
import java.io.FileInputStream;

import augustdp.picmaker.photoframe.myapplication.R;

public class BitmapScaler {
    public static Bitmap scaleToFitWidth(Bitmap bitmap, int i) {
        return Bitmap.createScaledBitmap(bitmap, i, (int) (((float) bitmap.getHeight()) * (((float) i) / ((float) bitmap.getWidth()))), true);
    }

    public static Bitmap scaleToFitHeight(Bitmap bitmap, int i) {
        return Bitmap.createScaledBitmap(bitmap, (int) (((float) bitmap.getWidth()) * (((float) i) / ((float) bitmap.getHeight()))), i, true);
    }

    public static Bitmap makeTransparent(Bitmap bitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, new Paint(2));
        return createBitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bitmap, int i, int i2) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) i2) / ((float) width), ((float) i) / ((float) height));
        return makeTransparent(Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true));
    }

    public static Bitmap decodeFile(File file, int i, int i2) {
        int i3;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            int i4 = options.outWidth;
            int i5 = options.outHeight;
            if (i4 <= i2) {
                if (i5 <= i) {
                    i3 = 1;
                    BitmapFactory.Options options2 = new BitmapFactory.Options();
                    options2.inSampleSize = i3;
                    options2.inPurgeable = true;
                    options2.inScaled = false;
                    return BitmapFactory.decodeFile(file.getAbsolutePath(), options2);
                }
            }
            i3 = Math.round(((float) i5) / ((float) i));
            int round = Math.round(((float) i4) / ((float) i2));
            if (i3 >= round) {
                i3 = round;
            }
            BitmapFactory.Options options22 = new BitmapFactory.Options();
            options22.inSampleSize = i3;
            options22.inPurgeable = true;
            options22.inScaled = false;
            return BitmapFactory.decodeFile(file.getAbsolutePath(), options22);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DisplayImageOptions getDisplayImageOptions(boolean z) {
        return new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.ic_empty).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_empty).cacheInMemory(z).cacheOnDisk(z).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).build();
    }
}
