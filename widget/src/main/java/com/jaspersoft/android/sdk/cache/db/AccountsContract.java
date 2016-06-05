package com.jaspersoft.android.sdk.cache.db;

import android.provider.BaseColumns;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
class AccountsContract {
    public static final String SQL_CREATE_ACCOUNTS_TABLE =
            "CREATE TABLE " + AccountEntry.TABLE_NAME + " (" +
                    AccountEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    AccountEntry.COLUMN_NAME_USERNAME + " TEXT NOT NULL, " +
                    AccountEntry.COLUMN_NAME_ORGANIZATION + " TEXT NOT NULL, " +
                    AccountEntry.COLUMN_NAME_BASE_URL + " TEXT NOT NULL" +
                    " )";


    public AccountsContract() {
    }

    public abstract class AccountEntry implements BaseColumns {
        public static final int DATABASE_VERSION = 1;
        public static final String TABLE_NAME = "accounts";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_ORGANIZATION = "organization";
        public static final String COLUMN_NAME_BASE_URL = "base_url";
    }
}
