package com.jaspersoft.android.sdk.adHoc;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class AdHocData {
    private List<List<Entry>> data;
    private List<String> groups;
    private List<String> measures;

    public AdHocData(List<List<Entry>> data, List<String> groups, List<String> measures) {
        this.data = data;
        this.groups = groups;
        this.measures = measures;
    }

    public List<List<Entry>> getData() {
        return data;
    }

    public List<String> getGroups() {
        return groups;
    }

    public List<String> getMeasures() {
        return measures;
    }
}
