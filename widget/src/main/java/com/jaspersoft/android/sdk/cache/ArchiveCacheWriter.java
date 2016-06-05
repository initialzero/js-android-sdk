package com.jaspersoft.android.sdk.cache;

import android.webkit.WebView;

import java.io.File;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ArchiveCacheWriter implements CacheWriter {

    private final WebView webView;

    public ArchiveCacheWriter(WebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean write(File cacheFile) {
        webView.saveWebArchive(cacheFile.getPath());
        return true;
    }
}
