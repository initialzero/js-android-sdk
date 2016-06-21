package com.jaspersoft.android.sdk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jaspersoft.android.sdk.adHoc.table.TableAdHocView;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class AdHocTableActivity extends AppCompatActivity{

    TableAdHocView adHocView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_hoc_table);

        adHocView = (TableAdHocView) findViewById(R.id.adHoc);
        adHocView.run(null, null);
    }
}
