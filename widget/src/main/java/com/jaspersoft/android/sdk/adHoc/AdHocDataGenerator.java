package com.jaspersoft.android.sdk.adHoc;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Tivodar
 * @since 2.5
 */
public class AdHocDataGenerator {

    public void getData(String adHocUri, DataObtainedCallback dataObtainedCallback) {
        AdHocData adHocData = new AdHocData(generateDummyData(), generateDummyGroups(), generateDummyMeasures());
        dataObtainedCallback.onDataObtained(adHocData);
    }

    private List<String> generateDummyGroups() {
        ArrayList<String> xValues = new ArrayList<>();
        xValues.add("Deluxe Supermarket, Canada");
        xValues.add("Deluxe Supermarket, Mexico");
        xValues.add("Deluxe Supermarket, USA");
        xValues.add("Gourmet Supermarket, Mexico");
        xValues.add("Gourmet Supermarket, USA");
        xValues.add("Mid-Size Grocery, Canada");
        xValues.add("Mid-Size Grocery, Mexico");
        xValues.add("Mid-Size Grocery, USA");
        xValues.add("Supermarket, Mexico");
        xValues.add("Supermarket, USA");
        return xValues;
    }

    private List<String> generateDummyMeasures() {
        ArrayList<String> groupsTitle = new ArrayList<>();
        groupsTitle.add("Store Sales 2013");
        groupsTitle.add("Store Cost 2013");
        groupsTitle.add("Per Sq Foot");
        return groupsTitle;
    }

    private List<List<Entry>> generateDummyData() {
        List<Entry> data1 = new ArrayList<>();
        List<Entry> data2 = new ArrayList<>();
        List<Entry> data3 = new ArrayList<>();

        BarEntry storeSalesEntry = new BarEntry(316.67f, 0);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(958.00f, 1);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(553.36f, 2);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(159.05f, 3);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(255.5f, 4);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(97.35f, 5);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(271.95f, 6);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(38.8f, 7);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(388.7f, 8);
        data1.add(storeSalesEntry);
        storeSalesEntry = new BarEntry(1227.10f, 9);
        data1.add(storeSalesEntry);

        BarEntry storeCostEntry = new BarEntry(130.6f, 0);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(374.19f, 1);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(223.87f, 2);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(68.16f, 3);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(99.83f, 4);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(36.78f, 5);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(105.04f, 6);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(14.37f, 7);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(158.13f, 8);
        data2.add(storeCostEntry);
        storeCostEntry = new BarEntry(495.93f, 9);
        data2.add(storeCostEntry);

        BarEntry perSqFootEntry = new BarEntry(0.86f, 0);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.89f, 1);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.63f, 2);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.84f, 3);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.83f, 4);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.57f, 5);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.52f, 6);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0f, 7);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.68f, 8);
        data3.add(perSqFootEntry);
        perSqFootEntry = new BarEntry(0.94f, 9);
        data3.add(perSqFootEntry);

        List<List<Entry>> result = new ArrayList<>();
        result.add(data1);
        result.add(data2);
        result.add(data3);
        return result;
    }

    public interface DataObtainedCallback {
        void onDataObtained(AdHocData adHocData);
    }
}
