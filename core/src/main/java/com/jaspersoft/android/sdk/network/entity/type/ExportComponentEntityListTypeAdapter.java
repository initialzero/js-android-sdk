package com.jaspersoft.android.sdk.network.entity.type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jaspersoft.android.sdk.network.entity.export.ExportComponentEntityList;

import java.util.Map;
import java.util.Set;

/**
 * @author Tom Koptel
 * @since 2.3
 */
class ExportComponentEntityListTypeAdapter extends CustomizedTypeAdapterFactory<ExportComponentEntityList> {
    public ExportComponentEntityListTypeAdapter() {
        super(ExportComponentEntityList.class);
    }

    protected JsonElement afterRead(JsonElement raw) {
        JsonObject main = raw.getAsJsonObject();
        JsonArray array = new JsonArray();
        Set<Map.Entry<String, JsonElement>> entries = main.entrySet();

        for (Map.Entry<String, JsonElement> entry : entries) {
            array.add(entry.getValue());
        }

        JsonObject result = new JsonObject();
        result.add("components", array);
        return result;
    }
}
