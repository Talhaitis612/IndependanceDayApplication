package augustdp.picmaker.photoframe.myapplication.photocommon;

import android.graphics.Bitmap;

public class CWRotater extends BaseRotater {
    /* access modifiers changed from: package-private */
    @Override // com.appintex.photocommon.BaseRotater
    public void setmDegree() {
        this.mDegree = 90;
    }

    @Override // com.appintex.photocommon.BaseRotater
    public Bitmap rotate(Bitmap bitmap) {
        setmDegree();
        return super.rotate(bitmap);
    }
}
