package augustdp.picmaker.photoframe.myapplication.photocommon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MenuActivityHelper {
    private static final String TAG = "MenuActivityHelper";
    static String mCurrentPhotoPath;

    public static void callGalleryApp(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PhotoConstant.GALLERY_REQUEST);
    }

    public static String saveCropBit(Bitmap bitmap, Context context) {
        return saveCrop(bitmap, generateImageName(), context);
    }

    public static String generateImageName() {
        return "IMG_" + ((Object) DateFormat.format("yyyyMMdd_kkmmss", new Date())) + ".jpg";
    }

    public static String saveCrop(Bitmap bitmap, String str, Context context) {
        String str2 = getCropFolderPath() + "/" + str;
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(str2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str2;
    }

    private static File getCropFolderPath() {
        return checkCropPath(Environment.getExternalStorageDirectory(), PhotoConstant.CROP_TEMP);
    }

    private static File checkCropPath(File file, String str) {
        File file2 = new File(file, str);
        if (!file2.exists()) {
            file2.mkdir();
        }
        return file2;
    }

    public static void setCropImgPath(String str) {
        mCurrentPhotoPath = str;
    }

    public static String getCropImgPath() {
        return mCurrentPhotoPath;
    }

    public static void deleteFolder(Activity activity) {
        String[] list;
        try {
            File checkCropPath = checkCropPath(Environment.getExternalStorageDirectory(), PhotoConstant.CROP_TEMP);
            if (!checkCropPath.exists()) {
                checkCropPath.mkdirs();
            }
            String str = Uri.fromFile(checkCropPath).toString() + "/";
            if (checkCropPath.isDirectory() && (list = checkCropPath.list()) != null) {
                for (int i = 0; i < list.length; i++) {
                    new File(Uri.parse(str + list[i]).getPath()).delete();
                }
            }
        } catch (Exception unused) {
        }
    }
}
