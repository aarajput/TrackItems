package me.crosswall.photo.pick.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;


public class PermissionUtil {

    public static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    public static boolean checkPermission(Activity activity, String permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int storagePermission = ActivityCompat.checkSelfPermission(activity, permission);
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static void showPermissionDialog(final Activity activity,String permission) {
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity,permission)) {

            ActivityCompat.requestPermissions(activity, new String[]{permission},
                    PermissionUtil.REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }

        ActivityCompat.requestPermissions(activity,new String[]{permission},
                PermissionUtil.REQUEST_CODE_ASK_PERMISSIONS);

    }


    public static void showAppSettingDialog(final Activity activity){
        new AlertDialog.Builder(activity)
                .setMessage("In order to run on Android M, your authorization is needed")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Ok",null)
                .show();
    }

}
