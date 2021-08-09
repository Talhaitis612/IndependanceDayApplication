package augustdp.picmaker.photoframe.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.format.DateFormat;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class UtilSaveImage {
    private static File checkPath(File file, String str) {
        File file2 = new File(file, str);
        if (!file2.exists()) {
            file2.mkdir();
        }
        return file2;
    }

    public static String generateImageName() {
        return "IMG_" + ((Object) DateFormat.format("yyyyMMdd_kkmmss", new Date())) + ".jpg";
    }

    public static String saveReal(Bitmap bitmap, Context context) {
        return saveReal(bitmap, generateImageName(), context);
    }

    public static String saveReal(Bitmap bitmap, String str, Context context) {
        String str2 = getRealFolderPath() + "/" + str;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }

    public static String getRealFolderPath() {
        return checkPath(Environment.getExternalStorageDirectory(), "AugustProfilePicDP").getPath();
    }

    public static String gettempFolderPath() {
        return checkPath(Environment.getExternalStorageDirectory(), "August").getPath();
    }

    public static File getRealFolderFile() {
        return checkPath(Environment.getExternalStorageDirectory(), "AugustProfilePicDP");
    }
}
