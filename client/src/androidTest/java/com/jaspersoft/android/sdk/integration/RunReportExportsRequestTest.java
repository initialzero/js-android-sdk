package com.jaspersoft.android.sdk.integration;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.LargeTest;

import com.jaspersoft.android.sdk.client.JsRestClient;
import com.jaspersoft.android.sdk.client.async.request.RunReportExecutionRequest;
import com.jaspersoft.android.sdk.client.async.request.RunReportExportOutputRequest;
import com.jaspersoft.android.sdk.client.async.request.RunReportExportsRequest;
import com.jaspersoft.android.sdk.client.oxm.report.ExportExecution;
import com.jaspersoft.android.sdk.client.oxm.report.ExportsRequest;
import com.jaspersoft.android.sdk.client.oxm.report.ReportDataResponse;
import com.jaspersoft.android.sdk.client.oxm.report.ReportExecutionRequest;
import com.jaspersoft.android.sdk.client.oxm.report.ReportExecutionResponse;
import com.jaspersoft.android.sdk.integration.utils.FactoryGirl;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * @author Tom Koptel
 * @since 1.9
 */
@LargeTest
public class RunReportExportsRequestTest extends AndroidTestCase {
    private RunReportExecutionRequest runReportExecutionRequest;
    private JsRestClient jsRestClient;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        FactoryGirl factoryGirl = FactoryGirl.newInstance();
        jsRestClient = factoryGirl.createJsRestClient();
        ReportExecutionRequest reportExecutionRequest = factoryGirl.createExecutionData(jsRestClient);
        runReportExecutionRequest = new RunReportExecutionRequest(jsRestClient, reportExecutionRequest);
    }

    public void test_requestForExportsOnReport() throws Exception {
        ReportExecutionResponse runReportExecutionResponse = runReportExecutionRequest.loadDataFromNetwork();
        String requestId = runReportExecutionResponse.getRequestId();

        ExportsRequest exr = new ExportsRequest();
        exr.setMarkupType(ReportExecutionRequest.MARKUP_TYPE_EMBEDDABLE);
        exr.setAllowInlineScripts(false);
        exr.setOutputFormat("html");
        exr.setPages("1");

        RunReportExportsRequest runReportExportsRequest = new RunReportExportsRequest(jsRestClient, exr, requestId);
        ExportExecution runReportExportsResponse = runReportExportsRequest.loadDataFromNetwork();
        assertThat(runReportExportsResponse, notNullValue());

        String executionId = runReportExportsResponse.getId();
        RunReportExportOutputRequest runReportExportOutputRequest
                = new RunReportExportOutputRequest(jsRestClient, requestId, executionId);
        ReportDataResponse response = runReportExportOutputRequest.loadDataFromNetwork();
        assertThat(response, notNullValue());
    }
}
