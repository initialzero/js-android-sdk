package com.jaspersoft.android.sdk.cache.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public String getCachedFileName(@NotNull Long accountId, @NotNull String resourceUri, @NotNull Integer filtersHash, @Nullable Integer page) {
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

        if (filtersHash != 0) {
            //Add filterHash to WHERE params
            selection.append(" AND ");
            selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILTERS_HASH + " =?");
            selectionArgs.add(String.valueOf(filtersHash));
        }

        //Add page to WHERE params
        selection.append(" AND ");
        selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_PAGE + " =?");
        selectionArgs.add(String.valueOf(page));

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

    public boolean addResourceCache(@NotNull Long accountId, @NotNull String resourceUri, @NotNull Integer filtersHash, @Nullable Integer page, @NotNull String filepath) {
        SQLiteDatabase db = resourceCacheDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ResourceCachedContract.CacheResourcesEntry._ID, accountId);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_RESOURCE_URI, resourceUri);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_PAGE, page);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILTERS_HASH, filtersHash);
        values.put(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILEPATH, filepath);

        boolean inserted = db.insert(
                ResourceCachedContract.CacheResourcesEntry.TABLE_NAME,
                null,
                values) != -1;
        db.close();
        return inserted;
    }

    public int deleteResourceCache(@NotNull Long accountId, @NotNull String resourceUri, @NotNull Integer filtersHash, @Nullable Integer page) {
        SQLiteDatabase db = resourceCacheDbHelper.getWritableDatabase();

        StringBuilder selection = new StringBuilder("");
        ArrayList<String> selectionArgs = new ArrayList<String>();

        //Add accountId to WHERE params
        selection.append(ResourceCachedContract.CacheResourcesEntry._ID + " =?");
        selectionArgs.add(String.valueOf(accountId));

        //Add resourceUri to WHERE params
        selection.append(" AND ");
        selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_RESOURCE_URI + " =?");
        selectionArgs.add(resourceUri);

        //Add filterHash to WHERE params
        selection.append(" AND ");
        selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILTERS_HASH + " =?");
        selectionArgs.add(String.valueOf(filtersHash));

        if (page != null) {
            //Add page to WHERE params
            selection.append(" AND ");
            selection.append(ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_PAGE + " =?");
            selectionArgs.add(String.valueOf(page));
        }

        int deleted = db.delete(
                ResourceCachedContract.CacheResourcesEntry.TABLE_NAME,
                selection.toString(),
                selectionArgs.toArray(new String[selectionArgs.size()]));
        db.close();
        return deleted;
    }
}
