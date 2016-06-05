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
import com.jaspersoft.android.sdk.network.Credentials;
import com.jaspersoft.android.sdk.network.SpringCredentials;
import com.jaspersoft.android.sdk.utils.DbImporter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;
import java.io.InputStream;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class AccountsTableTest {

    @Mock
    AuthorizedClient authorizedClient;

    @Mock
    Credentials credentials;

    private AccountsDatabase accountsDatabase;
    private AccountsDbHelper accountsDbHelper;

    @Before
    public void setUp() throws Exception {
        //TODO: change with  initMocks(this); after new dexmaker-mockito release
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getApplicationContext().getCacheDir().getPath());
        authorizedClient = Mockito.mock(AuthorizedClient.class);
        credentials = Mockito.mock(Credentials.class);

        accountsDbHelper = new FakeAccountsDbHelper(getInstrumentation().getTargetContext());
        accountsDatabase = new AccountsDatabase(accountsDbHelper);
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

        long accountId = accountsDatabase.addAccount(authorizedClient);

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

        long accountId = accountsDatabase.getAccountId(authorizedClient);

        assertThat(accountId, is(2L));
    }

    @After
    public void close() {
        accountsDbHelper.close();
        ((FakeAccountsDbHelper) accountsDbHelper).dropDb();
    }

    private final class FakeAccountsDbHelper extends AccountsDbHelper {

        public FakeAccountsDbHelper(Context context) {
            super(context);
        }

        @Override
        public SQLiteDatabase getReadableDatabase() {
            return getFakeBd();
        }

        @Override
        public SQLiteDatabase getWritableDatabase() {
            return getFakeBd();
        }

        private SQLiteDatabase getFakeBd() {
            File fakeDbFile = DbImporter.importDb("accounts", getInstrumentation().getTargetContext().getExternalCacheDir(), "accounts.sqlite");
            return SQLiteDatabase.openDatabase(fakeDbFile.getAbsolutePath(), null, SQLiteDatabase.OPEN_READWRITE);
        }

        public void dropDb() {
            File[] cacheFiles = getInstrumentation().getTargetContext().getExternalCacheDir().listFiles();
            for (File file : cacheFiles) {
                file.delete();
            }
        }
    }
}