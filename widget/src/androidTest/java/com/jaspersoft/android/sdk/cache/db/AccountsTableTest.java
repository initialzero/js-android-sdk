/*
 * Copyright (C) 2016 TIBCO Jaspersoft Corporation. All rights reserved.
 * http://community.jaspersoft.com/project/mobile-sdk-android
 *
 * Unless you have purchased a commercial license agreement from TIBCO Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of TIBCO Jaspersoft Mobile SDK for Android.
 *
 * TIBCO Jaspersoft Mobile SDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TIBCO Jaspersoft Mobile SDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TIBCO Jaspersoft Mobile SDK for Android. If not, see
 * <http://www.gnu.org/licenses/lgpl>.
 */

package com.jaspersoft.android.sdk.cache.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.jaspersoft.android.sdk.network.AuthorizedClient;
import com.jaspersoft.android.sdk.network.SpringCredentials;
import com.jaspersoft.android.sdk.utils.DbImporter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AccountsTableTest {

    private AuthorizedClient authorizedClient;
    private AccountsTable accountsTable;
    private AccountsDbHelper accountsDbHelper;

    @Before
    public void setUp() throws Exception {
        //TODO: change with  initMocks(this); after new dexmaker-mockito release
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getApplicationContext().getCacheDir().getPath());
        authorizedClient = Mockito.mock(AuthorizedClient.class);

        accountsDbHelper = new FakeAccountsDbHelper(getTargetContext());
        accountsTable = new AccountsTable(accountsDbHelper);
    }

    @Test
    public void should_add_account() throws Exception {
        SpringCredentials springCredentials = SpringCredentials.builder()
                .withUsername("testUserName")
                .withOrganization("testOrganization")
                .withPassword("empty")
                .build();

        when(authorizedClient.getCredentials()).thenReturn(springCredentials);
        when(authorizedClient.getBaseUrl()).thenReturn("http://test-url.com/test");

        long accountId = accountsTable.addAccount(authorizedClient);

        assertThat(accountId, is(4L));
    }

    @Test
    public void should_get_account() throws Exception {
        SpringCredentials springCredentials = SpringCredentials.builder()
                .withUsername("test_user_b")
                .withOrganization("test_org_b")
                .withPassword("empty")
                .build();

        when(authorizedClient.getCredentials()).thenReturn(springCredentials);
        when(authorizedClient.getBaseUrl()).thenReturn("http://test-url-b.com/test");

        long accountId = accountsTable.getAccountId(authorizedClient);

        assertThat(accountId, is(2L));
    }

    @Test
    public void should_not_get_account() throws Exception {
        SpringCredentials springCredentials = SpringCredentials.builder()
                .withUsername("wrong")
                .withOrganization("wrong")
                .withPassword("empty")
                .build();

        when(authorizedClient.getCredentials()).thenReturn(springCredentials);
        when(authorizedClient.getBaseUrl()).thenReturn("http://wrong.com/test");

        long accountId = accountsTable.getAccountId(authorizedClient);

        assertThat(accountId, is(-1L));
    }

    @Test
    public void should_remove_account() throws Exception {
        SpringCredentials springCredentials = SpringCredentials.builder()
                .withUsername("test_user_b")
                .withOrganization("test_org_b")
                .withPassword("empty")
                .build();

        when(authorizedClient.getCredentials()).thenReturn(springCredentials);
        when(authorizedClient.getBaseUrl()).thenReturn("http://test-url-b.com/test");

        boolean removeAccount = accountsTable.removeAccount(authorizedClient);

        assertThat(removeAccount, is(true));
    }

    @Test
    public void should_remove_account_cascading() throws Exception {
        SpringCredentials springCredentials = SpringCredentials.builder()
                .withUsername("test_user_b")
                .withOrganization("test_org_b")
                .withPassword("empty")
                .build();

        when(authorizedClient.getCredentials()).thenReturn(springCredentials);
        when(authorizedClient.getBaseUrl()).thenReturn("http://test-url-b.com/test");

        accountsTable.removeAccount(authorizedClient);

        int count = getAccountCachedResourcesCount(2L, accountsDbHelper.getReadableDatabase());
        assertThat(count, is(0));
    }

    @Test
    public void should_not_remove_account() throws Exception {
        SpringCredentials springCredentials = SpringCredentials.builder()
                .withUsername("wrong")
                .withOrganization("wrong")
                .withPassword("empty")
                .build();

        when(authorizedClient.getCredentials()).thenReturn(springCredentials);
        when(authorizedClient.getBaseUrl()).thenReturn("http://test-url-b.com/wrong");

        boolean removeAccount = accountsTable.removeAccount(authorizedClient);

        assertThat(removeAccount, is(false));
    }

    @After
    public void close() {
        ((FakeAccountsDbHelper) accountsDbHelper).dropDb();
    }

    private final class FakeAccountsDbHelper extends AccountsDbHelper {

        private SQLiteDatabase sqLiteDatabase;

        public FakeAccountsDbHelper(Context context) {
            super(context);

            File fakeDbFile = DbImporter.importDb("accounts", getInstrumentation().getTargetContext().getExternalCacheDir(), "accounts.sqlite");
            sqLiteDatabase = SQLiteDatabase.openDatabase(fakeDbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
            // Enable foreign key constraints
            sqLiteDatabase.execSQL("PRAGMA foreign_keys=ON;");
        }

        @Override
        public SQLiteDatabase getReadableDatabase() {
            return sqLiteDatabase;
        }

        @Override
        public SQLiteDatabase getWritableDatabase() {
            return sqLiteDatabase;
        }

        public void dropDb() {
            accountsDbHelper.close();

            File[] cacheFiles = getTargetContext().getExternalCacheDir().listFiles();
            for (File file : cacheFiles) {
                file.delete();
            }
        }
    }

    private int getAccountCachedResourcesCount(Long accountId, SQLiteDatabase db) {
        return db.query(
                ResourceCachedContract.CacheResourcesEntry.TABLE_NAME,
                new String[]{ResourceCachedContract.CacheResourcesEntry.COLUMN_NAME_FILEPATH},
                ResourceCachedContract.CacheResourcesEntry.COLUMN_ACCOUNT_ID + " =?",
                new String[]{String.valueOf(accountId)},
                null,
                null,
                null
        ).getCount();
    }
}