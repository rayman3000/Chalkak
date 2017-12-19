package bulgogi1216.gmail.photogenic.communication.Photo;

import android.net.Uri;

import java.io.UnsupportedEncodingException;

import bulgogi1216.gmail.photogenic.communication.POSTMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;
import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-25.
 */

/*
    Header Parameter
    Content-Type : application/x-www-form-urlencoded
 */
public class PhotoPOST extends RestfulMethod implements POSTMethod, PhotoSetting {
    private double longitude;
    private double latitude;
    private String uploader;
    private String province;
    private String city;
    private String gu;
    private String cultural_property;
    private String photo_url;
    private String filter_attribute = "";
    private String description = "";


    public PhotoPOST(String scheme, double longitude, double latitude, String uploader, String province, String city, String gu, String cultural_property, String photo_url){
        super(scheme);
        this.longitude = longitude;
        this.latitude = latitude;
        this.uploader = uploader;
        this.province = province;
        this.city = city;
        this.gu = gu;
        this.cultural_property = cultural_property;
        this.photo_url = photo_url;
        setHeader("Content-Type", "application/x-www-form-urlencoded");

    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getUploader() {
        return uploader;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getGu() {
        return gu;
    }

    public String getCultural_property() {
        return cultural_property;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getFilter_attribute() {
        return filter_attribute;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String[] getPath() {
        return path;
    }

    public void setFilter_attribute(String filter_attribute) {
        this.filter_attribute = filter_attribute;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getW3FormEncodedData(String char_set) throws UnsupportedEncodingException{
        StringBuilder data = new StringBuilder();
        Tool.appendValue(data, "latitude", String.valueOf(latitude), char_set, false);
        Tool.appendValue(data, "longitude", String.valueOf(longitude), char_set, false);
        Tool.appendValue(data, "uploader", uploader, char_set, false);
        Tool.appendValue(data, "province", province, char_set, false);
        Tool.appendValue(data, "city", city, char_set, false);
        Tool.appendValue(data, "gu", gu, char_set, false);
        Tool.appendValue(data, "cultural_property", cultural_property, char_set, false);
        if(!filter_attribute.equals(""))Tool.appendValue(data, "filter_attribute", filter_attribute, char_set, false);
        if(!description.equals(""))Tool.appendValue(data, "description", description, char_set, false);
        Tool.appendValue(data, "photo_url", photo_url, char_set, true);
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

}
