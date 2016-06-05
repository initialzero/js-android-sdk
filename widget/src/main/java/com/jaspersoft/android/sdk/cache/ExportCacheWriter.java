package com.jaspersoft.android.sdk.cache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ExportCacheWriter implements CacheWriter {

    private final byte[] data;

    public ExportCacheWriter(byte[] data) {
        this.data = data;
    }

    @Override
    public boolean write(File cacheFile) {
        try {
            File cachedResource = File.createTempFile(cacheFile.getName(), null, cacheFile.getParentFile());
            FileOutputStream outputStream = new FileOutputStream(cachedResource);
            outputStream.write(data);
            outputStream.close();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}
