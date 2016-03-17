package com.jaspersoft.android.sdk.network.entity.export;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public class ExportComponentEntityList {
    @Expose
    private List<ExportComponentEntity> components;

    public List<ExportComponentEntity> getComponents() {
        return components;
    }
}
