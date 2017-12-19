package bulgogi1216.gmail.photogenic.communication.Report;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.POSTMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;
import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-27.
 */
/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class ReportPOST extends RestfulMethod implements POSTMethod, ReportSetting {
    private String reported_photo;
    private String report_category;
    private String reporter;
    private String report_content;
    private String reported_time;

    public ReportPOST(String scheme, String reported_photo, String report_category, String reporter, String report_content, String reported_time){
        super(scheme);
        this.reported_photo = reported_photo;
        this.report_category = report_category;
        this.reporter = reporter;
        this.report_content = report_content;
        this.reported_time = reported_time;
        setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public String getReported_photo() {
        return reported_photo;
    }

    public String getReport_category() {
        return report_category;
    }

    public String getReporter() {
        return reporter;
    }

    public String getReport_content() {
        return report_content;
    }

    public String getReported_time() {
        return reported_time;
    }

    @Override
    public String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();
        Tool.appendValue(data, "reported_photo", reported_photo, char_set, false);
        Tool.appendValue(data, "report_category", report_category, char_set, false);
        Tool.appendValue(data, "reporter", reporter, char_set, false);
        Tool.appendValue(data, "report_content", report_content, char_set, false);
        Tool.appendValue(data, "reported_time", reported_time, char_set, true);
        return data.toString();
    }

    @Override
    public String buildUri(String end_point, String... paths) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .encodedAuthority(end_point);
        for(String path : paths){
            builder.appendPath(path);
        }
        return builder.build().toString();
    }

    @Override
    public String[] getPath() {
        return path;
    }

}
