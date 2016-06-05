package com.jaspersoft.android.sdk.cache.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class ResourceCacheDbHelper extends SQLiteOpenHelper {

    public ResourceCacheDbHelper(Context context) {
        super(context, ResourceCachedContract.CacheResourcesEntry.TABLE_NAME, null, ResourceCachedContract.CacheResourcesEntry.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ResourceCachedContract.SQL_CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No update logic since it is first version
    }
}
