package com.jaspersoft.android.sdk.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class AdHocChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_ad_hoc);

        findViewById(R.id.adHocChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdHocChooseActivity.this, AdHocChartActivity.class));
            }
        });

        findViewById(R.id.adHocTable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdHocChooseActivity.this, AdHocTableActivity.class));
            }
        });
    }

}
