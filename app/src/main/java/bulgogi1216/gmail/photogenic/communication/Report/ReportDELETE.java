package bulgogi1216.gmail.photogenic.communication.Report;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.DELETEMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;
import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-27.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class ReportDELETE extends RestfulMethod implements DELETEMethod, ReportSetting{

    private String report_id;

    public ReportDELETE(String scheme, String report_id){
        super(scheme);
        this.report_id = report_id;
        setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    @Override
    public String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException {
        StringBuilder data = new StringBuilder();
        Tool.appendValue(data, "report_id", report_id, char_set, true);
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
