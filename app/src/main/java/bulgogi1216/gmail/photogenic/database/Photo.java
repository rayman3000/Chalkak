package bulgogi1216.gmail.photogenic.database;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by dbsrh on 2017-10-26.
 */

public class Photo {
    private String _id;
    private double longitude;
    private double latitude;
    private String province;
    private String city;
    private String gu;
    private String cultural_property;
    private String photo_url;
    private String uploader;
    private String upload_time;
    private int likes;
    private double rating;
    private int raters;
    private double atmosphere;
    private double person;
    private double background;
    private double creativity;
    private String filter_attribute;
    private String description;

    public static final String TABLE_NAME = "photos";
    public static final String COL_ID = "_id";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_PROVINCE = "province";
    public static final String COL_CITY = "city";
    public static final String COL_GU = "gu";
    public static final String COL_CULTURAL_PROPERTY = "cultural_property";
    public static final String COL_PHOTO_URL = "photo_url";
    public static final String COL_UPLOADER = "uploader";
    public static final String COL_UPLOAD_TIME = "upload_time";
    public static final String COL_LIKES = "likes";
    public static final String COL_RATING = "rating";
    public static final String COL_RATERS = "raters";
    public static final String COL_ATMOSPHERE = "atmosphere";
    public static final String COL_PERSON = "person";
    public static final String COL_BACKGROUND = "background";
    public static final String COL_CREATIVITY = "creativity";
    public static final String COL_FILTER_ATTRIBUTE = "filter_attribute";
    public static final String COL_DESCRIPTION = "description";

    public Photo(JSONObject jsonObject) throws JSONException{
        _id = (jsonObject.isNull(COL_ID))?"":jsonObject.getString(COL_ID);
        longitude = (jsonObject.isNull(COL_LONGITUDE))?0:jsonObject.getDouble(COL_LONGITUDE);
        latitude = (jsonObject.isNull(COL_LATITUDE))?0:jsonObject.getDouble(COL_LATITUDE);
        uploader = (jsonObject.isNull(COL_UPLOADER))?"":jsonObject.getString(COL_UPLOADER);
        province = (jsonObject.isNull(COL_PROVINCE))?"":jsonObject.getString(COL_PROVINCE);
        city = (jsonObject.isNull(COL_CITY))?"":jsonObject.getString(COL_CITY);
        gu = (jsonObject.isNull(COL_GU))?"":jsonObject.getString(COL_GU);
        photo_url = (jsonObject.isNull(COL_PHOTO_URL))?"":jsonObject.getString(COL_PHOTO_URL);
        cultural_property = (jsonObject.isNull(COL_CULTURAL_PROPERTY))?"":jsonObject.getString(COL_CULTURAL_PROPERTY);
        description = (jsonObject.isNull(COL_DESCRIPTION))?"":jsonObject.getString(COL_DESCRIPTION);
        filter_attribute = (jsonObject.isNull(COL_FILTER_ATTRIBUTE))?"":jsonObject.getString(COL_FILTER_ATTRIBUTE);
        creativity = (jsonObject.isNull(COL_CREATIVITY))?0:jsonObject.getInt(COL_CREATIVITY);
        background = (jsonObject.isNull(COL_BACKGROUND))?0:jsonObject.getInt(COL_BACKGROUND);
        person = (jsonObject.isNull(COL_PERSON))?0:jsonObject.getInt(COL_PERSON);
        atmosphere = (jsonObject.isNull(COL_ATMOSPHERE))?0:jsonObject.getInt(COL_ATMOSPHERE);
        raters = (jsonObject.isNull(COL_RATERS))?0:jsonObject.getInt(COL_RATERS);
        rating = (jsonObject.isNull(COL_RATING))?0:jsonObject.getDouble(COL_RATING);
        likes = (jsonObject.isNull(COL_LIKES))?0:jsonObject.getInt(COL_LIKES);
        upload_time = (jsonObject.isNull(COL_UPLOAD_TIME))?"":jsonObject.getString(COL_UPLOAD_TIME);
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

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUpload_time() {
        return upload_time;
    }

    public void setUpload_time(String upload_time) {
        this.upload_time = upload_time;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(int atmosphere) {
        this.atmosphere = roundDouble(atmosphere/raters, 2);
    }

    public double getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = roundDouble(person/raters, 2);
    }

    public double getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = roundDouble(background/raters,2);
    }

    public double getCreativity() {
        return creativity;
    }

    public void setCreativity(int creativity) {
        this.creativity = roundDouble(creativity/this.raters,2);
    }

    public String getFilter_attribute() {
        return filter_attribute;
    }

    public void setFilter_attribute(String filter_attribute) {
        this.filter_attribute = filter_attribute;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRaters() {
        return raters;
    }

    public void setRaters(int raters) {
        this.raters = raters;
    }

    private double roundDouble(double value, int decimal_place){
        int divider = (int)Math.pow(10,decimal_place);
        return Math.ceil(value*divider)/divider;
    }
}
