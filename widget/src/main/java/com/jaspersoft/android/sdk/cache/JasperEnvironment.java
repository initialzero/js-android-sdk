package com.jaspersoft.android.sdk.cache;

import android.content.Context;

import com.jaspersoft.android.sdk.network.AuthorizedClient;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class JasperEnvironment {

    private static JasperEnvironment instance;

    private JasperEnvironment() {

    }

    public static synchronized JasperEnvironment getInstance() {
        if (instance == null) {
            instance = new JasperEnvironment();
        }
        return instance;
    }

    public void clear(Context context, AuthorizedClient client) {
        ResourceCacheManager resourceCacheManager = new ResourceCacheManager(null, null, null, null);
    }

    public void clearAll(Context context) {
        ResourceCacheManager resourceCacheManager = new ResourceCacheManager(null, null, null, null);
    }
}
