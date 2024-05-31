package com.govno228.pon;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CheckLocationAccess {

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private static final String[] locationPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    public static boolean checkPermissions(Activity activity) {
        return hasPermissions(activity, locationPermissions);
    }

    public static void RequestPermissions(Activity activity) {
        requestPermissions(activity, locationPermissions);
    }

    public static boolean checkAndRequestPermissions(Activity activity) {
        if (!hasPermissions(activity, locationPermissions)) {
            requestPermissions(activity, locationPermissions);
            return false;
        } else {
            return true;
        }
    }

    private static boolean hasPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private static void requestPermissions(Activity activity, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, PERMISSIONS_REQUEST_CODE);
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            // Check granted results for each location permission
            // Do something appropriate based on the results
        }
    }
}
