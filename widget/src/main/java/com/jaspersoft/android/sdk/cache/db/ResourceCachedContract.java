package com.jaspersoft.android.sdk.cache.db;

import android.provider.BaseColumns;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class ResourceCachedContract {
    public static final String SQL_CREATE_ACCOUNTS_TABLE =
            "CREATE TABLE " + CacheResourcesEntry.TABLE_NAME + " (" +
                    CacheResourcesEntry._ID + " INTEGER NOT NULL, " +
                    CacheResourcesEntry.COLUMN_NAME_RESOURCE_URI + " TEXT NOT NULL, " +
                    CacheResourcesEntry.COLUMN_NAME_PAGE + " INTEGER NOT NULL, " +
                    CacheResourcesEntry.COLUMN_NAME_FILTERS_HASH + " INTEGER NOT NULL, " +
                    CacheResourcesEntry.COLUMN_NAME_FILEPATH + " TEXT NOT NULL" +
                    " )";


    public ResourceCachedContract() {
    }

    public abstract class CacheResourcesEntry implements BaseColumns {
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_NAME = "resource_cache";
        public static final String COLUMN_NAME_RESOURCE_URI = "resource_uri";
        public static final String COLUMN_NAME_PAGE = "page";
        public static final String COLUMN_NAME_FILTERS_HASH = "filters_hash";
        public static final String COLUMN_NAME_FILEPATH = "file_path";
    }
}
