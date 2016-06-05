package com.jaspersoft.android.sdk.cache.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class AccountsDbHelper extends SQLiteOpenHelper {

    public AccountsDbHelper(Context context) {
        super(context, AccountsContract.AccountEntry.TABLE_NAME, null, AccountsContract.AccountEntry.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AccountsContract.SQL_CREATE_ACCOUNTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No update logic since it is first version
    }
}
