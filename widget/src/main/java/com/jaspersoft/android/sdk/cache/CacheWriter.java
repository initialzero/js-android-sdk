package com.jaspersoft.android.sdk.cache;

import java.io.File;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public interface CacheWriter {
    boolean write(File cacheFile);
}
