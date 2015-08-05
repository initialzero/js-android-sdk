package com.jaspersoft.android.sdk.data.server;
/*
 * Copyright � 2015 TIBCO Software, Inc. All rights reserved.
 * http://community.jaspersoft.com/project/jaspermobile-android
 *
 * Unless you have purchased a commercial license agreement from Jaspersoft,
 * the following license terms apply:
 *
 * This program is part of Jaspersoft Mobile for Android.
 *
 * Jaspersoft Mobile is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Jaspersoft Mobile is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Jaspersoft Mobile for Android. If not, see
 * <http://www.gnu.org/licenses/lgpl>.
 */

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jaspersoft.android.sdk.data.type.GsonFactory;
import com.jaspersoft.android.sdk.data.type.XmlSerializerFactory;
import com.jaspersoft.android.sdk.test.resource.ResourceFile;
import com.jaspersoft.android.sdk.test.resource.TestResource;
import com.jaspersoft.android.sdk.test.resource.inject.TestResourceInjector;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;

import java.io.ByteArrayOutputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public class ServerInfoResponseTest {
    @ResourceFile("json/default_server_info.json")
    TestResource mJsonResource;
    @ResourceFile("xml/default_server_info.xml")
    TestResource mXmlResource;

    Gson mGson = GsonFactory.create();
    Serializer mSerializer = XmlSerializerFactory.create();

    @Before
    public void setup() {
        TestResourceInjector.inject(this);
    }

    @Test
    public void shouldParseDefaultJsonResponse() {
        ServerInfoResponse response = mGson.fromJson(mJsonResource.asString(), ServerInfoResponse.class);
        assertThat(response, is(notNullValue()));
        assertThat(response.getVersion(), is(ServerVersion.AMBER_MR2));
    }

    @Test
    public void shouldSerializeServerVersionToJson() {
        String initialJson = mJsonResource.asString();
        ServerInfoResponse response = mGson.fromJson(initialJson, ServerInfoResponse.class);
        JsonElement element = mGson.toJsonTree(response, ServerInfoResponse.class);
        String serializedVersion = element.getAsJsonObject().get("version").getAsString();
        assertThat(serializedVersion, is("6.1"));
    }

    @Test
    public void shouldParseDefaultXmlResponse() throws Exception {
        ServerInfoResponse response = mSerializer.read(ServerInfoResponse.class, mXmlResource.asString());
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldSerializeServerVersionToXml() throws Exception {
        String initialXml = mXmlResource.asString();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ServerInfoResponse serverInfoResponse1 = mSerializer.read(ServerInfoResponse.class, initialXml);
        mSerializer.write(serverInfoResponse1, outputStream);

        String xml = new String(outputStream.toByteArray());
        ServerInfoResponse serverInfoResponse2 = mSerializer.read(ServerInfoResponse.class, xml);
        assertThat(serverInfoResponse1.getVersion(), is(serverInfoResponse2.getVersion()));
    }
}
