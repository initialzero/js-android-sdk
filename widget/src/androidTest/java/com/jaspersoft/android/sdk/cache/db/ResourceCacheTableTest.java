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
import com.jaspersoft.android.sdk.utils.DbImporter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ResourceCacheTableTest {

    @Mock
    AuthorizedClient authorizedClient;

    @Mock
    Credentials credentials;

    private ResourceCacheDbHelper resourceCacheDbHelper;
    private ResourceCacheDatabase resourceCacheDatabase;

    @Before
    public void setUp() throws Exception {
        //TODO: change with  initMocks(this); after new dexmaker-mockito release
        System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getApplicationContext().getCacheDir().getPath());
        authorizedClient = Mockito.mock(AuthorizedClient.class);
        credentials = Mockito.mock(Credentials.class);

        resourceCacheDbHelper = new FakeResourceCacheDbHelper(getInstrumentation().getTargetContext());
        resourceCacheDatabase = new ResourceCacheDatabase(resourceCacheDbHelper);
    }

    @Test
    public void should_cache_resource() throws Exception {
        boolean added = resourceCacheDatabase.addResourceCache(2L,
                "http://test-url.com/report",
                551525125,
                3,
                "512t2fg");

        assertThat(added, is(true));
    }

    @Test
    public void should_cache_resource_without_filters() throws Exception {
        boolean added = resourceCacheDatabase.addResourceCache(3L,
                "http://test-url.com/report2",
                0,
                3,
                "512t2fg");

        assertThat(added, is(true));
    }

    @Test
    public void should_get_cached_resource() throws Exception {
        String cachedFile = resourceCacheDatabase.getCachedFileName(2L,
                "http://server-b.com/report2",
                538592935,
                1);

        assertThat(cachedFile, is("330f5118-89c7-4c81-9ce2-aeb747bb6c69"));
    }

    @Test
    public void should_get_cached_resource_without_filter_hash() throws Exception {
        String cachedFile = resourceCacheDatabase.getCachedFileName(2L,
                "http://server-b.com/report",
                0,
                44);

        assertThat(cachedFile, is("82d0c8fb-cdd2-46ae-a75f-a13f2a678a31"));
    }

    @Test
    public void should_not_get_cached_resource() throws Exception {
        String cachedFile = resourceCacheDatabase.getCachedFileName(10L,
                "http://WRONG.com/wrong",
                538592935,
                1);

        assertThat(cachedFile, is((String) null));
    }

    @Test
    public void should_remove_cached_resource() throws Exception {
        int removed = resourceCacheDatabase.deleteResourceCache(2L,
                "http://server-b.com/report2",
                538592935,
                1);

        assertThat(removed, is(1));
    }

    @Test
    public void should_remove_all_cached_page() throws Exception {
        int removed = resourceCacheDatabase.deleteResourceCache(1L,
                "http://server-a.com/report",
                634732353,
                null);

        assertThat(removed, is(2));
    }

    @Test
    public void should_not_remove_cached_resource() throws Exception {
        int removed = resourceCacheDatabase.deleteResourceCache(10L,
                "http://WRONG.com/wrong",
                538592935,
                1);

        assertThat(removed, is(0));
    }

    @After
    public void close() {
        resourceCacheDbHelper.close();
        ((FakeResourceCacheDbHelper) resourceCacheDbHelper).dropDb();
    }

    private final class FakeResourceCacheDbHelper extends ResourceCacheDbHelper {

        public FakeResourceCacheDbHelper(Context context) {
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