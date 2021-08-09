package augustdp.picmaker.photoframe.myapplication.dragabble;

import android.graphics.Matrix;

public class BitmapOperationMap {
    DraggableBitmap mBitmap;
    Matrix mOperationMtx;
    OPERATION mOpt;

    public enum OPERATION {
        NEW,
        ADD,
        DELETE
    }

    public BitmapOperationMap(DraggableBitmap draggableBitmap, Matrix matrix, OPERATION operation) {
        this.mBitmap = draggableBitmap;
        this.mOperationMtx = matrix;
        this.mOpt = operation;
    }

    public DraggableBitmap getDraggableBitmap() {
        return this.mBitmap;
    }

    public Matrix getOperationMatrix() {
        return this.mOperationMtx;
    }

    public OPERATION getOption() {
        return this.mOpt;
    }
}
