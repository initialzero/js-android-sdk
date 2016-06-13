package com.jaspersoft.android.sdk.adHoc;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public interface ChartDrawer {
    Chart draw(List<List<Entry>> data, List<String> measures, List<String> groups);
}
