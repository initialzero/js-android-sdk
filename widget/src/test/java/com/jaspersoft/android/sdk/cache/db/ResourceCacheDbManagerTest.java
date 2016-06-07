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

import com.jaspersoft.android.sdk.network.AuthorizedClient;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ResourceCacheDbManagerTest {

    AuthorizedClient authorizedClient;

    @Mock
    AccountsTable accountsTable;
    @Mock
    ResourceCacheTable resourceCacheTable;
    private ResourceCacheDbManager resourceCacheDbManager;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        resourceCacheDbManager = new ResourceCacheDbManager(accountsTable, resourceCacheTable);
    }

    @Test
    public void should_get_cached_resource() throws Exception {
        when(accountsTable.getAccountId(authorizedClient)).thenReturn(2L);
        when(resourceCacheTable.getCachedFileName(2L, "http://server-b.com/report2", 538592935, 1)).thenReturn("330f5118-89c7-4c81-9ce2-aeb747bb6c69");

        String file = resourceCacheDbManager.getCachedFile(authorizedClient, "http://server-b.com/report2", 538592935, 1);
        assertThat(file, is("330f5118-89c7-4c81-9ce2-aeb747bb6c69"));
    }

    @Test
    public void should_add_cached_resource() throws Exception {
        when(accountsTable.getAccountId(authorizedClient)).thenReturn(-1L);
        when(accountsTable.addAccount(authorizedClient)).thenReturn(7L);
        when(resourceCacheTable.getCachedFileName(7L, "http://server-test.com/reportX", 293837953, 3)).thenReturn(null);
        when(resourceCacheTable.addResourceCache(7L, "http://server-test.com/reportX", 293837953, 3, "7dc846a6-d440-4c64-96aa-c95a81f02b6d")).thenReturn(true);

        boolean added = resourceCacheDbManager.addResourceCache(authorizedClient, "http://server-test.com/reportX", 293837953, 3, "7dc846a6-d440-4c64-96aa-c95a81f02b6d");

        assertThat(added, is(true));
    }

    @Test
    public void should_remove_cached_resource_for_client() throws Exception {
        when(accountsTable.removeAccount(authorizedClient)).thenReturn(true);

        boolean removed = resourceCacheDbManager.deleteResourceCache(authorizedClient);

        assertThat(removed, is(true));
    }

    @Test
    public void should_remove_cached_resource() throws Exception {
        when(accountsTable.getAccountId(authorizedClient)).thenReturn(2L);
        when(resourceCacheTable.deleteResourceCache(2L, "http://server-b.com/report2", 538592935, 1)).thenReturn(1);

        boolean removed = resourceCacheDbManager.deleteResourceCache(authorizedClient, "http://server-b.com/report2", 538592935, 1);

        assertThat(removed, is(true));
    }
}