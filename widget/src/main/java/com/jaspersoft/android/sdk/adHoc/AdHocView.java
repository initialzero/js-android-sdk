package com.jaspersoft.android.sdk.adHoc;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet;
import com.jaspersoft.android.sdk.network.AuthorizedClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class AdHocView extends RelativeLayout {
    private ChartType chartType;
    private Chart chart;
    private AdHocDataGenerator adHocDataGenerator;
    private ChartDrawerFactory chartDrawerFactory;
    private AdHocData adHocData;

    public AdHocView(Context context) {
        super(context);
        init();
    }

    public AdHocView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AdHocView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AdHocView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ChartType getChartType() {
        return chartType;
    }

    public void setChartType(ChartType chartType) {
        this.chartType = chartType;
        drawChart(adHocData);
    }

    public void run(AuthorizedClient authorizedClient, String adHocUri) {
        adHocDataGenerator.getData(adHocUri, new AdHocDataGenerator.DataObtainedCallback() {
            @Override
            public void onDataObtained(AdHocData adHocData) {
                AdHocView.this.adHocData = adHocData;
                drawChart(adHocData);
            }
        });
    }

    public List<Legend> getLegend() {
        return generateLegends();
    }

    public void setLegend(List<Legend> legends) {
        for (int i = 0; i < legends.size(); i++) {
            ((BarLineScatterCandleBubbleDataSet) chart.getData().getDataSets().get(i)).setVisible(legends.get(i).isEnabled());
            ((BarLineScatterCandleBubbleDataSet) chart.getData().getDataSets().get(i)).setHighlightEnabled(legends.get(i).isEnabled());
        }
        chart.animateY(500);
    }

    private void init() {
        adHocDataGenerator = new AdHocDataGenerator();
        chartDrawerFactory = new ChartDrawerFactory();
    }

    private List<Legend> generateLegends() {
        List<Legend> legends = new ArrayList<>();
        for (int i = 0; i < chart.getData().getColors().length; i++) {
            Legend legend = new Legend(adHocData.getMeasures().get(i), chart.getData().getColors()[i], ((BarLineScatterCandleBubbleDataSet) chart.getData().getDataSets().get(i)).isVisible());
            legends.add(legend);
        }
        return legends;
    }

    private void drawChart(AdHocData adHocData) {
        ChartDrawer chartDrawer = chartDrawerFactory.createChartDrawer(getContext(), chartType);
        chart = chartDrawer.draw(adHocData.getData(), adHocData.getMeasures(), adHocData.getGroups());
        removeAllViews();
        addView(chart, new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }
}
