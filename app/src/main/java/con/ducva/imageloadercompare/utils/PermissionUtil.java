package con.ducva.imageloadercompare.utils;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by HAL Team on 10/6/16.
 * Copyright © 2016 GMO Media Inc. All rights reserved.
 */

public class PermissionUtil {

    public static boolean checkPermission(final AppCompatActivity activity, @NonNull final String permission, String explainText, @NonNull final int requestCode) {
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    permission)) {
                DialogUtils.showConfirmDialog(activity, null, explainText, "OK", "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(activity, permission, requestCode);
                    }
                }, null, false, null);
            } else {
                requestPermission(activity, permission, requestCode);
            }
            return false;
        }
    }

    private static void requestPermission(AppCompatActivity activity, @NonNull String permission, @NonNull int requestCode) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
    }

}
