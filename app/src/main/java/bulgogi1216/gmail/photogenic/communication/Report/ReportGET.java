package bulgogi1216.gmail.photogenic.communication.Report;

import android.net.Uri;

import bulgogi1216.gmail.photogenic.communication.GETMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;

/**
 * Created by dbsrh on 2017-10-27.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class ReportGET extends RestfulMethod implements GETMethod, ReportSetting {
    private String report_id = "";
    private String reported_photo = "";
    private String report_category = "";
    private String reporter = "";
    private int limit = 10;
    //사용 안함 : -1 , true : 1, false : 0
    private int time_asc = -1;
    private int processed = -1;


    public ReportGET(String scheme) {
        super(scheme);
        setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getReported_photo() {
        return reported_photo;
    }

    public void setReported_photo(String reported_photo) {
        this.reported_photo = reported_photo;
    }

    public String getReport_category() {
        return report_category;
    }

    public void setReport_category(String report_category) {
        this.report_category = report_category;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if(limit < 1){
            this.limit = 1;
        }else if(limit > 50){
            this.limit = 50;
        }else{
            this.limit = limit;
        }
    }

    public int getTime_asc() {
        return time_asc;
    }

    public void setTime_asc(Boolean time_asc) {
        this.time_asc = (time_asc)?1:0;
    }

    @Override
    public String[] getPath() {
        return path;
    }

    @Override
    public String buildUri(String end_point, String... paths) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .encodedAuthority(end_point);
        for(String path : paths){
            builder.appendPath(path);
        }
        if(!report_id.equals(""))builder.appendQueryParameter("report_id",report_id);
        if(!reported_photo.equals(""))builder.appendQueryParameter("reported_photo",reported_photo);
        if(!report_category.equals(""))builder.appendQueryParameter("report_category",report_category);
        if(!reporter.equals(""))builder.appendQueryParameter("reporter",reporter);
        if(processed != -1)builder.appendQueryParameter("processed", (processed==1)?"true":"false");
        builder.appendQueryParameter("limit", String.valueOf(limit));
        if(time_asc != -1)builder.appendQueryParameter("time_asc", (time_asc==1)?"true":"false");
        return builder.build().toString();
    }
}
