package com.jaspersoft.android.sdk.cache.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jaspersoft.android.sdk.network.AuthorizedClient;
import com.jaspersoft.android.sdk.network.SpringCredentials;

import java.util.ArrayList;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class AccountsDatabase {

    private final AccountsDbHelper accountsDbHelper;

    public AccountsDatabase(AccountsDbHelper accountsDbHelper) {
        this.accountsDbHelper = accountsDbHelper;
    }

    public long getAccountId(AuthorizedClient client) {
        if (!(client.getCredentials() instanceof SpringCredentials)) return -1;
        SpringCredentials credentials = (SpringCredentials) client.getCredentials();

        SQLiteDatabase db = accountsDbHelper.getReadableDatabase();

        StringBuilder selection = new StringBuilder("");
        ArrayList<String> selectionArgs = new ArrayList<String>();

        //Add username to WHERE params
        selection.append(AccountsContract.AccountEntry.COLUMN_NAME_USERNAME + " =?");
        selectionArgs.add(credentials.getUsername());

        //Add organization to WHERE params
        selection.append(" AND ");
        selection.append(AccountsContract.AccountEntry.COLUMN_NAME_ORGANIZATION + " =?");
        selectionArgs.add(credentials.getOrganization());

        //Add baseUrl to WHERE params
        selection.append(" AND ");
        selection.append(AccountsContract.AccountEntry.COLUMN_NAME_BASE_URL + " =?");
        selectionArgs.add(client.getBaseUrl());

        Cursor accountCursor = db.query(
                AccountsContract.AccountEntry.TABLE_NAME,
                new String[]{AccountsContract.AccountEntry._ID},
                selection.toString(),
                selectionArgs.toArray(new String[selectionArgs.size()]),
                null,
                null,
                null
        );

        long accountId = -1;
        if (accountCursor != null) {
            if (accountCursor.moveToFirst()) {
                accountId = accountCursor.getLong(accountCursor.getColumnIndex(AccountsContract.AccountEntry._ID));
            }
            accountCursor.close();
        }
        db.close();
        return accountId;
    }

    public long addAccount(AuthorizedClient client) {
        if (!(client.getCredentials() instanceof SpringCredentials)) return -1;
        SpringCredentials credentials = (SpringCredentials) client.getCredentials();

        SQLiteDatabase db = accountsDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AccountsContract.AccountEntry.COLUMN_NAME_USERNAME, credentials.getUsername());
        values.put(AccountsContract.AccountEntry.COLUMN_NAME_ORGANIZATION, credentials.getOrganization());
        values.put(AccountsContract.AccountEntry.COLUMN_NAME_BASE_URL, client.getBaseUrl());

        return db.insert(
                AccountsContract.AccountEntry.TABLE_NAME,
                null,
                values);
    }
}
