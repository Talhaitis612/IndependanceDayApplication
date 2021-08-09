package augustdp.picmaker.photoframe.myapplication.photocommon;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public abstract class BaseRotater {
    protected int mDegree;

    public void destroy() {
    }

    /* access modifiers changed from: package-private */
    public abstract void setmDegree();

    public Bitmap rotate(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float) this.mDegree, ((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap == createBitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return createBitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return bitmap;
        }
    }
}
