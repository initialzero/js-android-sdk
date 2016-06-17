package com.jaspersoft.android.sdk.adHoc;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.jaspersoft.android.sdk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class ColumnChartDrawer implements ChartDrawer {

    private Context context;
    private List<Integer> barColors;
    private BarChart barChart;

    public ColumnChartDrawer(Context context) {
        barColors = new ArrayList<>();
        barColors.add(Color.parseColor("#0d233a"));
        barColors.add(Color.parseColor("#2f7ed8"));
        barColors.add(Color.parseColor("#910000"));

        this.context = context;
    }

    @Override
    public Chart draw(List<List<Entry>> data, List<String> measures, List<String> groups) {
        barChart = createBarChart(context);
        defineAxis(barChart);
        setChartData(barChart, data, measures, groups);
        return barChart;
    }

    private BarChart createBarChart(Context context) {
        BarChart barChart = new BarChart(context);
        barChart.setDescription(null);
        barChart.setPinchZoom(true);
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.animateY(500);
        barChart.setKeepPositionOnRotation(true);

        barChart.setMarkerView(new PopupView(context));

        Legend legend = barChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setTextColor(Color.parseColor("#333333"));
        legend.setXEntrySpace(16f);
        legend.setYOffset(8f);

        return barChart;
    }

    private void defineAxis(BarChart barChart) {
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setTextColor(Color.parseColor("#606060"));
        leftAxis.setAxisLineColor(Color.parseColor("#D8D8D8"));
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setLabelCount(8 ,true);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(false);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setChartData(BarChart barChart, List<List<Entry>> data, List<String> measures, List<String> groups) {
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            List<Entry> entries = data.get(i);

            BarDataSet barDataSet = new BarDataSet(castTo(entries), measures.get(i));
            barDataSet.setColor(barColors.get(i));
            barDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            dataSets.add(barDataSet);
        }

        BarData barData = new BarData(groups, dataSets);
        barData.setDrawValues(false);
        barData.setGroupSpace(200);

        barChart.setData(barData);
    }

    private List<BarEntry> castTo(List<Entry> entries) {
        List<BarEntry> barEntries = new ArrayList<>();
        for (Entry entry : entries) {
            barEntries.add((BarEntry) entry);
        }
        return barEntries;
    }

    private class PopupView extends MarkerView {

        private TextView tvContent;

        public PopupView(Context context) {
            super(context, R.layout.view_popup);
            tvContent = (TextView) findViewById(R.id.popupText);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {
            tvContent.setText(barChart.getXValue(highlight.getXIndex()) + "\n" + barChart.getData().getDataSetByIndex(highlight.getDataSetIndex()).getLabel() + ": " + e.getVal());
        }

        @Override
        public int getXOffset(float xpos) {
            return -(getWidth() / 2);
        }

        @Override
        public int getYOffset(float ypos) {
            return -getHeight();
        }
    }
}
