package com.jaspersoft.android.sdk.sample.cache;

import android.content.Context;
import android.webkit.WebView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class ResourceCacheStorage {

    private final ResourceCachePreferences resourceCachePreferences;
    private final Context context;

    public ResourceCacheStorage(ResourceCachePreferences resourceCachePreferences, Context context) {
        this.resourceCachePreferences = resourceCachePreferences;
        this.context = context;
        WebView webView;
    }

    public boolean cacheResource(String fileName, byte[] data) {
        File resourceCacheDir = resourceCachePreferences.getResourceCacheDirectory(context);
        try {
            File cachedResource = File.createTempFile(fileName, null, resourceCacheDir);
            FileOutputStream outputStream = new FileOutputStream(cachedResource);
            outputStream.write(data);
            outputStream.close();
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    public boolean removeResource(String fileName) {
        File resourceCacheDir = resourceCachePreferences.getResourceCacheDirectory(context);
        File cachedResource = new File(resourceCacheDir, fileName);
        return cachedResource.delete();
    }
}
