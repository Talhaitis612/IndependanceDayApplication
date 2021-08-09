package augustdp.picmaker.photoframe.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Constant {
    public static String cardsCategoryIndex = "CardsCat";
    public static int coustom = 0;
    public static String date = "date";
    public static int[] faceflagsarray = {R.drawable.s_03, R.drawable.s_01, R.drawable.s_02, R.drawable.s_04, R.drawable.s_05, R.drawable.s_06, R.drawable.s_07, R.drawable.s_08, R.drawable.s_09, R.drawable.s_10, R.drawable.s_11, R.drawable.s_12, R.drawable.s_13, R.drawable.s_14, R.drawable.s_15, R.drawable.s_16, R.drawable.s_17, R.drawable.s_18, R.drawable.s_19, R.drawable.s_20, R.drawable.s_21, R.drawable.s_22, R.drawable.s_23, R.drawable.s_24, R.drawable.s_25, R.drawable.s_26, R.drawable.s_27, R.drawable.s_28, R.drawable.s_29, R.drawable.s_30, R.drawable.s_31, R.drawable.s_32, R.drawable.s_33, R.drawable.s_34, R.drawable.s_35, R.drawable.s_36, R.drawable.s_37, R.drawable.s_38};
    public static boolean intertialad = true;
    public static String location = "location";
    public static String message = "message";
    public static String partnername = "partnername";
    public static String time = "time";
    public static String yourname = "yourname";

    public static Bitmap createScaledBitmap(Bitmap bitmap, ScalingUtilities.ScalingLogic scalingLogic, int i, int i2) {
        Rect calculateSrcRect = calculateSrcRect(bitmap.getWidth(), bitmap.getHeight(), i, i2, scalingLogic);
        Rect calculateDstRect = calculateDstRect(bitmap.getWidth(), bitmap.getHeight(), i, i2, scalingLogic);
        Bitmap createBitmap = Bitmap.createBitmap(calculateDstRect.width(), calculateDstRect.height(), Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawBitmap(bitmap, calculateSrcRect, calculateDstRect, new Paint(2));
        return createBitmap;
    }

    public static Rect calculateSrcRect(int i, int i2, int i3, int i4, ScalingUtilities.ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingUtilities.ScalingLogic.CROP) {
            return new Rect(0, 0, i, i2);
        }
        float f = (float) i;
        float f2 = (float) i2;
        float f3 = ((float) i3) / ((float) i4);
        if (f / f2 > f3) {
            int i5 = (int) (f2 * f3);
            int i6 = (i - i5) / 2;
            return new Rect(i6, 0, i5 + i6, i2);
        }
        int i7 = (int) (f / f3);
        int i8 = (i2 - i7) / 2;
        return new Rect(0, i8, i, i7 + i8);
    }

    public static Rect calculateDstRect(int i, int i2, int i3, int i4, ScalingUtilities.ScalingLogic scalingLogic) {
        if (scalingLogic != ScalingUtilities.ScalingLogic.FIT) {
            return new Rect(0, 0, i3, i4);
        }
        float f = ((float) i) / ((float) i2);
        float f2 = (float) i3;
        float f3 = (float) i4;
        if (f > f2 / f3) {
            return new Rect(0, 0, i3, (int) (f2 / f));
        }
        return new Rect(0, 0, (int) (f3 * f), i4);
    }
}
