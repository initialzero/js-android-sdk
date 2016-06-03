package com.jaspersoft.android.sdk.sample.cache;

import android.content.Context;

import com.jaspersoft.android.sdk.network.AuthorizedClient;
import com.jaspersoft.android.sdk.sample.cache.db.ResourceCacheDatabase;

import java.io.File;
import java.util.UUID;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ResourceCacheManager {

    private final ResourceCacheStorage resourceCacheStorage;
    private final ResourceCacheDatabase resourceCacheDatabase;
    private final ResourceCachePreferences resourceCachePreferences;
    private final Context context;

    public ResourceCacheManager(ResourceCacheStorage resourceCacheStorage, ResourceCacheDatabase resourceCacheDatabase, ResourceCachePreferences resourceCachePreferences, Context context) {
        this.resourceCacheStorage = resourceCacheStorage;
        this.resourceCacheDatabase = resourceCacheDatabase;
        this.resourceCachePreferences = resourceCachePreferences;
        this.context = context;
    }

    public boolean cacheResource(AuthorizedClient client, String resourceUri, int page, CacheWriter cacheWriter) {
        File resourceCacheDir = resourceCachePreferences.getResourceCacheDirectory(context);
        String cacheName = generateUniqueFileName();
        if (resourceCacheDir == null || cacheName == null) return false;

        File cacheFile = new File(resourceCacheDir, cacheName);
        boolean cached = false;
        cached = cacheWriter.write(cacheFile);
        if (cached) {

        }
        return cached;
    }

    public boolean deleteResourceCache(AuthorizedClient client, String resourceUri) {

    }

    private String generateUniqueFileName() {
        return UUID.randomUUID().toString();
    }
}
