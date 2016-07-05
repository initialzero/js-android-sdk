package com.jaspersoft.android.sdk.widget.report.renderer.event.rest;

import com.jaspersoft.android.sdk.widget.report.renderer.event.Event;

/**
 * @author Andrew Tivodar
 * @since 2.6
 */
public class PageExportedEvent implements Event {
    private final String reportPage;

    PageExportedEvent(String reportPage) {
        this.reportPage = reportPage;
    }

    public String getReportPage() {
        return reportPage;
    }
}
