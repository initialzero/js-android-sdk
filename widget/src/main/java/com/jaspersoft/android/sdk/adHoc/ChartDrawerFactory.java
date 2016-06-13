package com.jaspersoft.android.sdk.adHoc;

import android.content.Context;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ChartDrawerFactory {

    public ChartDrawer createChartDrawer(Context context, ChartType chartType) {
        if (chartType == null) {
            chartType = ChartType.COLUMN;
        }

        switch (chartType) {
            case COLUMN:
                return new ColumnChartDrawer(context);
            case LINE:
                return new LineChartDrawer(context);
            default:
                return new ColumnChartDrawer(context);
        }
    }
}
