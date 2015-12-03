/*
 * Copyright © 2015 TIBCO Software, Inc. All rights reserved.
 * http://community.jaspersoft.com/project/jaspermobile-android
 *
 * Unless you have purchased a commercial license agreement from TIBCO Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of TIBCO Jaspersoft Mobile for Android.
 *
 * TIBCO Jaspersoft Mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * TIBCO Jaspersoft Mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with TIBCO Jaspersoft Mobile for Android. If not, see
 * <http://www.gnu.org/licenses/lgpl>.
 */

package com.jaspersoft.android.sdk.service;

import com.jaspersoft.android.sdk.service.auth.Credentials;
import com.jaspersoft.android.sdk.service.auth.SpringCredentials;
import com.jaspersoft.android.sdk.service.info.InMemoryInfoCache;
import com.jaspersoft.android.sdk.service.token.InMemoryTokenCache;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class RestClientTest {

    @Test
    public void testIntegration() {
        RestClient client = RestClient.builder()
                .serverUrl("http://localhost")
                .connectionTimeOut(5, TimeUnit.DAYS)
                .readTimeOut(5, TimeUnit.DAYS)
                .infoCache(new InMemoryInfoCache())
                .create();
        Credentials credentials = SpringCredentials.builder()
                .username("any")
                .password("any")
                .organization("any")
                .build();

        Session session = client.newSession(credentials)
                .tokenCache(new InMemoryTokenCache())
                .create();
        session.reportApi();
        session.repositoryApi();

        AnonymousSession anonymousSession = client.getAnonymousSession();
        anonymousSession.authApi();
        anonymousSession.infoApi();
    }
}