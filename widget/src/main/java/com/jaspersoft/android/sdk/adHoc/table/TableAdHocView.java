package com.jaspersoft.android.sdk.adHoc.table;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jaspersoft.android.sdk.R;
import com.jaspersoft.android.sdk.network.AuthorizedClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class TableAdHocView extends LinearLayout {
    private RecyclerView headerList;
    private RecyclerView dataList;

    public TableAdHocView(Context context) {
        super(context);
        init();
    }

    public TableAdHocView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TableAdHocView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        setOrientation(VERTICAL);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_ad_hoc_table, this);
        headerList = (RecyclerView) view.findViewById(R.id.headersList);
        dataList = (RecyclerView) view.findViewById(R.id.dataList);
       // headerList.setPivotY(0);
       // dataList.setPivotY(0);
        ((ZoomableRelativeLayout) view.findViewById(R.id.zoomableContainer)).setZoomListener(new ZoomableRelativeLayout.ZoomListener() {
            float headerHeight;

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onZoom(float zoom) {
                if (headerHeight == 0) {
                    headerHeight = headerList.getHeight();
                }
                headerList.setScaleX(zoom);
                headerList.setScaleY(zoom);
                headerList.setY((int) ((int) (headerHeight * zoom) - headerHeight) / 2);
                ((ViewGroup)dataList.getParent()).setTranslationY((int) (headerHeight * zoom - headerHeight));
            }

            @Override
            public void onMove(float x, float y) {
                headerList.setX(x);
            }
        });
    }

    public void run(AuthorizedClient client, String adHocUri) {
        final List<String> headers = generateHeaders();
        RecyclerView.Adapter headersAdapter = new HeadersAdapter(headers);
        headerList.setLayoutManager(new GridLayoutManager(getContext(), headers.size()));
        headerList.setAdapter(headersAdapter);

        final List<List<String>> data = generateData();
        RecyclerView.Adapter dataAdapter = new TableAdapter(data);
        dataList.setLayoutManager(new GridLayoutManager(getContext(), data.get(0).size()));
        dataList.setAdapter(dataAdapter);
    }

    private List<String> generateHeaders() {
        List<String> headers = new ArrayList<>();
        headers.add("City");
        headers.add("Open Date");
        headers.add("Store Sales 1998");
        headers.add("Store Cost 1998");
        headers.add("Unit Sales 1998");
        return headers;
    }

    private List<List<String>> generateData() {
        List<List<String>> data = new ArrayList<>();
        List<String> row = new ArrayList<>();
        row.add("Vancouver");
        row.add("Mar 27, 1977");
        row.add("11.04");
        row.add("4.75");
        row.add("4.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Acapulco");
        row.add("Jan 9, 1982");
        row.add("8.28");
        row.add("2.73");
        row.add("3.00");
        data.add(row);

        row = new ArrayList<>();
        row.add("Orizaba");
        row.add("Apr 13, 1979");
        row.add("13.20");
        row.add("5.93");
        row.add("5.00");
        data.add(row);
        return data;
    }
}
