package bulgogi1216.gmail.photogenic.communication.Photo;

import android.net.Uri;

import bulgogi1216.gmail.photogenic.database.Photo;

import bulgogi1216.gmail.photogenic.communication.GETMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;

/**
 * Created by dbsrh on 2017-10-25.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class PhotoGET extends RestfulMethod implements GETMethod, PhotoSetting{
    private String _id = "";
    private Double longitude = 0.0;
    private Double latitude  = 0.0;
    private String uploader = "";
    //도(경기도, 경상북도..), 광역시, 특별시
    private String province = "";
    //도시명(광역시 특별시는 province 값이랑 똑같이 적어주면 됨)
    private String city = "";
    private String gu = "";
    private String cultural_property = "";

    //-1 : 사용 안함 , 0 : false, 1 : true
    private int time_asc = -1;
    private int like_asc = -1;
    private int rater_asc = -1;

    //default : 10
    private int limit = 10;

    public PhotoGET(String scheme){
        super(scheme);
        setHeader("Content-Type", "application/x-www-form-urlencoded");
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setCoordinate(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGu() {
        return gu;
    }

    public void setGu(String gu) {
        this.gu = gu;
    }

    public String getCultural_property() {
        return cultural_property;
    }

    public void setCultural_property(String cultural_property) {
        this.cultural_property = cultural_property;
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

    public int getLike_asc() {
        return like_asc;
    }

    public void setLike_asc(Boolean like_asc) {
        this.like_asc = (like_asc)?1:0;
    }

    public int getRater_asc() {
        return rater_asc;
    }

    public void setRater_asc(Boolean rater_asc) {
        this.rater_asc = (rater_asc)?1:0;
    }

    @Override
    public String[] getPath() {
        return path;
    }

    public String buildUri(String end_point, String... paths){
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .encodedAuthority(end_point);
        for(String path : paths){
            builder.appendPath(path);
        }
        if(!_id.equals(""))builder.appendQueryParameter(Photo.COL_ID, _id);
        if(longitude != 0)builder.appendQueryParameter(Photo.COL_LONGITUDE, String.valueOf(longitude)).appendQueryParameter(Photo.COL_LATITUDE, String.valueOf(latitude));
        if(!uploader.equals(""))builder.appendQueryParameter(Photo.COL_UPLOADER, uploader);
        if(!province.equals("")){
            builder.appendQueryParameter(Photo.COL_PROVINCE, province);
            if(!city.equals("")){
                builder.appendQueryParameter(Photo.COL_CITY, city);
                if(!gu.equals("")){
                    builder.appendQueryParameter(Photo.COL_GU, gu);
                }
            }
        }
        if(!cultural_property.equals(""))builder.appendQueryParameter(Photo.COL_CULTURAL_PROPERTY, cultural_property);
        if(time_asc != -1)builder.appendQueryParameter("time_asc",(time_asc==1)?"true":"false");
        if(like_asc != -1)builder.appendQueryParameter("like_asc",(like_asc==1)?"true":"false");
        if(rater_asc != -1)builder.appendQueryParameter("rater_asc",(rater_asc==1)?"true":"false");
        builder.appendQueryParameter("limit", String.valueOf(limit));
        return builder.build().toString();
    }
}
