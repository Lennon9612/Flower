package com.catchmind.FlowerClassificationApplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class PermissionControl {
    public static final String PERMISSON_ARRAY[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean checkPermission(Activity activity, int rq_permission) {
        boolean permissionCheck = true;
        for(String permission : PERMISSON_ARRAY){
            if(activity.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                permissionCheck = false;
                break;
            }
        }
        if(!permissionCheck){
            activity.requestPermissions(PERMISSON_ARRAY, rq_permission);
            return false;
        }else{
            return true;
        }
    }

    public static boolean onCheckResult(int[] grantResults){
        boolean checkResult = true;
        for(int result : grantResults){
            if(result != PackageManager.PERMISSION_GRANTED){
                checkResult = false;
                break;
            }
        }
        return checkResult;
    }

}
