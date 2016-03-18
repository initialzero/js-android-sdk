package com.jaspersoft.android.sdk.network.entity.export;

import com.google.gson.annotations.Expose;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public class ExportComponentEntity {
    @Expose
    private String id;
    @Expose
    private String type;

    public ExportComponentEntity(String id, String type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
