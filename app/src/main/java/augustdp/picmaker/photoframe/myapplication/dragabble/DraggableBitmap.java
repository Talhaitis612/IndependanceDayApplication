package augustdp.picmaker.photoframe.myapplication.dragabble;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class DraggableBitmap {
    private boolean activated;
    private Matrix currentMatrix = null;
    public Bitmap mBitmap;
    private int mId = -1;
    private Matrix marginMatrix;
    private Matrix savedMatrix = null;
    private boolean touched;

    public DraggableBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
        this.activated = false;
    }

    public void setCurrentMatrix(Matrix matrix) {
        this.currentMatrix = null;
        this.currentMatrix = new Matrix(matrix);
    }

    public void setSavedMatrix(Matrix matrix) {
        this.savedMatrix = null;
        this.savedMatrix = new Matrix(matrix);
    }

    public Matrix getCurrentMatrix() {
        return this.currentMatrix;
    }

    public Matrix getSavedMatrix() {
        return this.savedMatrix;
    }

    public void activate() {
        this.activated = true;
    }

    public void deActivate() {
        this.activated = false;
    }

    public boolean isActivate() {
        return this.activated;
    }

    public boolean isTouched() {
        return this.touched;
    }

    public void setTouched(boolean z) {
        this.touched = z;
    }

    public Matrix getMarginMatrix() {
        return this.marginMatrix;
    }

    public void setMarginMatrix(Matrix matrix) {
        this.marginMatrix = null;
        this.marginMatrix = new Matrix(matrix);
    }

    public int getmId() {
        return this.mId;
    }

    public void setmId(int i) {
        this.mId = i;
    }
}
