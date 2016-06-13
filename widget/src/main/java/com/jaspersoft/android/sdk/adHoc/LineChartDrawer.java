package com.jaspersoft.android.sdk.adHoc;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class LineChartDrawer implements ChartDrawer {

    private Context context;
    private List<Integer> barColors;

    public LineChartDrawer(Context context) {
        barColors = new ArrayList<>();
        barColors.add(Color.parseColor("#0d233a"));
        barColors.add(Color.parseColor("#2f7ed8"));
        barColors.add(Color.parseColor("#910000"));

        this.context = context;
    }

    @Override
    public Chart draw(List<List<Entry>> data, List<String> measures, List<String> groups) {
        LineChart lineChart = createBarChart(context);
        defineAxis(lineChart);
        setChartData(lineChart, data, measures, groups);
        return lineChart;
    }

    private LineChart createBarChart(Context context) {
        LineChart lineChart = new LineChart(context);
        lineChart.setDescription(null);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.animateY(500);
        lineChart.setKeepPositionOnRotation(true);

        Legend legend = lineChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setTextColor(Color.parseColor("#333333"));
        legend.setXEntrySpace(16f);
        legend.setYOffset(8f);

        return lineChart;
    }

    private void defineAxis(LineChart lineChart) {
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#606060"));
        leftAxis.setAxisLineColor(Color.parseColor("#D8D8D8"));
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setLabelCount(8 ,true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setChartData(LineChart barChart, List<List<Entry>> data, List<String> measures, List<String> groups) {
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            List<Entry> entries = data.get(i);

            LineDataSet lineDataSet = new LineDataSet(entries, measures.get(i));
            lineDataSet.setColor(barColors.get(i));
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            dataSets.add(lineDataSet);
        }

        LineData lineData = new LineData(groups, dataSets);
        lineData.setDrawValues(false);

        barChart.setData(lineData);
    }
}
