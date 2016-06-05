package com.jaspersoft.android.sdk.cache.db;

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
public class ResourceCacheDatabase {

    private final AccountsDbHelper accountsDbHelper;

    public ResourceCacheDatabase(AccountsDbHelper accountsDbHelper) {
        this.accountsDbHelper = accountsDbHelper;
    }

    public String getCachedFileName(AuthorizedClient client, String resourceUri, int page, int filtersHash) {
        if (!(client.getCredentials() instanceof SpringCredentials)) return null;
        SpringCredentials credentials = (SpringCredentials) client.getCredentials();

        SQLiteDatabase db = accountsDbHelper.getReadableDatabase();

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(AccountsContract.AccountEntry.TABLE_NAME + " LEFT OUTER JOIN " +
                CachedResourcesContract.CacheResourcesEntry.TABLE_NAME + " ON (" +
                AccountsContract.AccountEntry.TABLE_NAME + "." + AccountsContract.AccountEntry._ID +
                " = " +
                CachedResourcesContract.CacheResourcesEntry.TABLE_NAME + "." + CachedResourcesContract.CacheResourcesEntry._ID +
                ")");

        StringBuilder selection = new StringBuilder("");
        ArrayList<String> selectionArgs = new ArrayList<String>();

        //Add username to WHERE params
        selection.append(AccountsContract.AccountEntry.TABLE_NAME + "." + AccountsContract.AccountEntry.COLUMN_NAME_USERNAME + " =?");
        selectionArgs.add(credentials.getUsername());

        //Add organization to WHERE params
        selection.append(" AND ");
        selection.append(AccountsContract.AccountEntry.TABLE_NAME + "." + AccountsContract.AccountEntry.COLUMN_NAME_ORGANIZATION + " =?");
        selectionArgs.add(credentials.getOrganization());

        //Add baseUrl to WHERE params
        selection.append(" AND ");
        selection.append(AccountsContract.AccountEntry.TABLE_NAME + "." + AccountsContract.AccountEntry.COLUMN_NAME_BASE_URL + " =?");
        selectionArgs.add(client.getBaseUrl());

        Cursor accountCursor = sqLiteQueryBuilder.query(
                db,
                new String[]{CachedResourcesContract.CacheResourcesEntry.COLUMN_NAME_FILEPATH},
                selection.toString(),
                selectionArgs.toArray(new String[selectionArgs.size()]),
                null,
                null,
                null
        );

        String fileName = null;
        if (accountCursor != null) {
            if (accountCursor.moveToFirst()) {
                fileName = accountCursor.getString(accountCursor.getColumnIndex(CachedResourcesContract.CacheResourcesEntry.COLUMN_NAME_FILEPATH));
            }
            accountCursor.close();
        }
        db.close();
        return fileName;
    }
}
