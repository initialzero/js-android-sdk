package com.jaspersoft.android.sdk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.jaspersoft.android.sdk.adHoc.AdHocView;
import com.jaspersoft.android.sdk.adHoc.ChartType;
import com.jaspersoft.android.sdk.adHoc.Legend;

import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class AdHocActivity extends AppCompatActivity implements LegendBottomDialogFragment.LegendUpdateListener {

    AdHocView adHocView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_hoc);

        adHocView = (AdHocView) findViewById(R.id.adHoc);
        adHocView.run(null, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_adhoc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.legends:
                LegendBottomDialogFragment bottomSheetDialogFragment = new LegendBottomDialogFragment();
                bottomSheetDialogFragment.setLegends(adHocView.getLegend());
                bottomSheetDialogFragment.setLegendUpdateListener(this);
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                return true;
            case R.id.chartType:
                ChartType chartType = adHocView.getChartType();
                if (chartType == ChartType.COLUMN) {
                    adHocView.setChartType(ChartType.LINE);
                } else {
                    adHocView.setChartType(ChartType.COLUMN);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLegendsChanged(List<Legend> legends) {
        adHocView.setLegend(legends);
    }
}
