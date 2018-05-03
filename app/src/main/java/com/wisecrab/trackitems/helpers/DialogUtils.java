package com.wisecrab.trackitems.helpers;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

public class DialogUtils {
    public static ProgressDialog getLoadingDialog(Context context,String msg) {
        ProgressDialog pd = new ProgressDialog(context);
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        pd.setMessage(msg);
        pd.setTitle(null);
        return pd;
    }

    public static void dismiss(ProgressDialog pd) {
        if (pd!=null && pd.isShowing())
            pd.dismiss();
    }

    public static void showRationalDialog(final Context context, @StringRes int message) {
        new AlertDialog.Builder(context)
                .setTitle("Permission Required")
                .setMessage(message)
                .setPositiveButton("Settings", (dialog, which) -> {
                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                    i.setData(uri);
                    if (context instanceof Activity)
                        ((Activity)context).startActivityForResult(i,0);
                    else
                        context.startActivity(i);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    public static void showList(Context context,String title, String [] list,DialogInterface.OnClickListener onClickListener){
        new AlertDialog.Builder(context)
                .setItems(list, onClickListener)
                .setTitle(title)
                .show();
    }
}
