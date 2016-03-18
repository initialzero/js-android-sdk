package com.jaspersoft.android.sdk.service.report;

import com.jaspersoft.android.sdk.service.data.report.ExportComponent;
import com.jaspersoft.android.sdk.service.data.report.PageRange;
import com.jaspersoft.android.sdk.service.exception.ServiceException;

import java.util.List;

/**
 * @author Tom Koptel
 * @since 2.0
 */
public class HtmlReportExport extends ReportExport {
    private final PageRange mPageRange;

    HtmlReportExport(
            ExportExecutionApi exportExecutionApi,
            List<ReportAttachment> attachments,
            String executionId,
            String exportId,
            PageRange pageRange
    ) {
        super(exportExecutionApi, attachments, executionId, exportId);
        mPageRange = pageRange;
    }

    /**
     * TODO javadoc
     */
    public List<ExportComponent> requestComponents() throws ServiceException {
        String page = (Integer.parseInt(mPageRange.toString()) - 1) + "";
        return mExportExecutionApi.requestComponents(mExecutionId, page);
    }
}
