package com.jaspersoft.android.sdk.cache.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.jaspersoft.android.sdk.network.AuthorizedClient;
import com.jaspersoft.android.sdk.network.SpringCredentials;

import java.util.ArrayList;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class ResourceCacheDatabase {

    private final ResourceCacheDbHelper resourceCacheDbHelper;

    public ResourceCacheDatabase(ResourceCacheDbHelper resourceCacheDbHelper) {
        this.resourceCacheDbHelper = resourceCacheDbHelper;
    }

    public String getCachedFileName(long accountId, String resourceUri, int page, int filtersHash) {
        SQLiteDatabase db = resourceCacheDbHelper.getReadableDatabase();

        StringBuilder selection = new StringBuilder("");
        ArrayList<String> selectionArgs = new ArrayList<String>();

        //Add accountId to WHERE params
        selection.append(ResourceCachedContract.CacheResourcesEntry._ID + " =?");
        selectionArgs.add(String.valueOf(accountId));

        //Add resourceUri to WHERE params
        selection.append(" AND ");
        selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_RESOURCE_URI + " =?");
        selectionArgs.add(resourceUri);

        //Add page to WHERE params
        selection.append(" AND ");
        selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_PAGE + " =?");
        selectionArgs.add(String.valueOf(page));

        //Add filterHash to WHERE params
        selection.append(" AND ");
        selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILTERS_HASH + " =?");
        selectionArgs.add(String.valueOf(filtersHash));

        Cursor cacheCursor = db.query(
                ResourceCachedContract.CacheResourcesEntry.TABLE_NAME,
                new String[]{ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILEPATH},
                selection.toString(),
                selectionArgs.toArray(new String[selectionArgs.size()]),
                null,
                null,
                null
        );

        String filePath = null;
        if (cacheCursor != null) {
            if (cacheCursor.moveToFirst()) {
                filePath = cacheCursor.getString(cacheCursor.getColumnIndex(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILEPATH));
            }
            cacheCursor.close();
        }
        db.close();
        return filePath;
    }

    public boolean addResourceCache(long accountId, String resourceUri, int page, int filtersHash, String filepath) {
        SQLiteDatabase db = resourceCacheDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ResourceCachedContract.CacheResourcesEntry._ID, accountId);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_RESOURCE_URI, resourceUri);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_PAGE, page);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILTERS_HASH, filtersHash);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILEPATH, filepath);

        return db.insert(
                ResourceCachedContract.CacheResourcesEntry.TABLE_NAME,
                null,
                values) != -1;
    }
}
