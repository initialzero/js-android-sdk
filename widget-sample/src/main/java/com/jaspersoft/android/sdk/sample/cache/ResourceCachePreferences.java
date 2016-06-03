package com.jaspersoft.android.sdk.sample.cache;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class ResourceCachePreferences {

    private static final String RESOURCE_CACHE_DIR_NAME = "jasperResources";
    private static final String WRITE_TO_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

    public File getResourceCacheDirectory(Context context) {
        File cacheDirectory = getCacheDirectory(context);
        if (cacheDirectory == null || !cacheDirectory.exists()) return null;

        return new File(cacheDirectory, RESOURCE_CACHE_DIR_NAME);
    }

    private File getCacheDirectory(Context context) {
        String extStorageState = getExternalStorageState();
        boolean writeToExternalCacheDir = writeToExternalCacheDir(context, extStorageState);

        return writeToExternalCacheDir ? context.getExternalCacheDir() : context.getCacheDir();
    }

    private String getExternalStorageState() {
        try {
            return Environment.getExternalStorageState();
        } catch (NullPointerException | IncompatibleClassChangeError e) {
            return Environment.MEDIA_REMOVED;
        }
    }

    private boolean writeToExternalCacheDir(Context context, String extStorageState) {
        return extStorageState.equals(Environment.MEDIA_MOUNTED) && hasStorageWritePermission(context);
    }

    private boolean hasStorageWritePermission(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) return true;

        int permission = context.checkCallingOrSelfPermission(WRITE_TO_STORAGE_PERMISSION);
        return permission == PackageManager.PERMISSION_GRANTED;
    }
}
