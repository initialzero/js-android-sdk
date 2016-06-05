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

import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class AccountDbHelperTest {
    private AccountsDbHelper accountsDbHelper;

    @Before
    public void setUp() throws Exception {
        getInstrumentation().getTargetContext().deleteDatabase(AccountsContract.AccountEntry.TABLE_NAME);
        accountsDbHelper = new AccountsDbHelper(getInstrumentation().getTargetContext());
    }

    @After
    public void close() {
        accountsDbHelper.close();
    }

    @Test
    public void should_create_db() throws Exception {
        accountsDbHelper.getWritableDatabase();

        File database= getInstrumentation().getTargetContext().getApplicationContext().getDatabasePath(AccountsContract.AccountEntry.TABLE_NAME);
        assertThat(database.exists(), is(true));
    }
}