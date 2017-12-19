package bulgogi1216.gmail.photogenic.database;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bulgogi1216.gmail.photogenic.communication.Tool;

/**
 * Created by dbsrh on 2017-10-13.
 */

/*
    Table Schema

    Description for Column
    - depiction's format is json array string , hence you must add data via set method.
 */
public class CulturalProperty {
    //Primary Key - URL
    private String _id = "";
    private String title = "";
    private String category = "";
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String address = "";
    private String description = "";
    private String closed_for_the_day = "";
    private String time_available = "";
    private String baby_equipment_rental = "";
    private String pet_available = "";
    private String parking = "";
    //Format JSONArray
    private String depiction = "";
    private String province ;

    private Context context;

    //Database Table String
    public static final String TABLE_NAME = "Properties";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_CATEGORY = "category";
    public static final String COL_LATITUDE = "latitude";
    public static final String COL_LONGITUDE = "longitude";
    public static final String COL_ADDRESS = "address";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_CLOSED_FOR_THE_DAY = "closed_for_the_day";
    public static final String COL_TIME_AVAILABLE = "time_available";
    public static final String COL_BABY_EQUIPMENT_RENTAL = "baby_equipment_rental";
    public static final String COL_PET_AVAILABLE = "pet_available";
    public static final String COL_PARKING = "parking";
    public static final String COL_DEPICTION = "depiction";
    public static final String COL_PROVINCE = "province";

    public CulturalProperty(){}

    public CulturalProperty(Context context, JSONObject jsonObject, String filename) throws JSONException {
        this.context = context;
        if(!jsonObject.isNull("_id"))_id = jsonObject.getJSONObject("_id").getString("value");
        if(!jsonObject.isNull("title"))title = jsonObject.getJSONObject("title").getString("value");
        category = filename;
        if(!jsonObject.isNull("latitude"))latitude = jsonObject.getJSONObject("latitude").getDouble("value");
        if(!jsonObject.isNull("longitude"))longitude = jsonObject.getJSONObject("longitude").getDouble("value");
        if(!jsonObject.isNull("address"))address = jsonObject.getJSONObject("address").getString("value");
        if(!jsonObject.isNull("description"))description = jsonObject.getJSONObject("description").getString("value");
        if(!jsonObject.isNull("closed_for_the_day"))closed_for_the_day = jsonObject.getJSONObject("closed_for_the_day").getString("value");
        if(!jsonObject.isNull("time_available"))time_available = jsonObject.getJSONObject("time_available").getString("value");
        if(!jsonObject.isNull("baby_equipment_rental"))baby_equipment_rental = jsonObject.getJSONObject("baby_equipment_rental").getString("value");
        if(!jsonObject.isNull("pet_available"))pet_available = jsonObject.getJSONObject("pet_available").getString("value");
        if(!jsonObject.isNull("parking"))parking = jsonObject.getJSONObject("parking").getString("value");
        if(!jsonObject.isNull("depiction")){
            setDepiction(jsonObject.getJSONObject("depiction").getString("value"));
        }
        if(latitude != 0.0 && longitude != 0.0)province = Tool.GetProvince(this.context, latitude, longitude);
    }

    public CulturalProperty(JSONObject jsonObject) throws JSONException{
        if(!jsonObject.isNull("_id"))_id = jsonObject.getString("_id");
        if(!jsonObject.isNull("title"))title = jsonObject.getString("title");
        if(!jsonObject.isNull("category"))category = jsonObject.getString("category");
        if(!jsonObject.isNull("latitude"))latitude = jsonObject.getDouble("latitude");
        if(!jsonObject.isNull("longitude"))longitude = jsonObject.getDouble("longitude");
        if(!jsonObject.isNull("address"))address = jsonObject.getString("address");
        if(!jsonObject.isNull("description"))description = jsonObject.getString("description");
        if(!jsonObject.isNull("closed_for_the_day"))closed_for_the_day = jsonObject.getString("closed_for_the_day");
        if(!jsonObject.isNull("time_available"))time_available = jsonObject.getString("time_available");
        if(!jsonObject.isNull("baby_equipment_rental"))baby_equipment_rental = jsonObject.getString("baby_equipment_rental");
        if(!jsonObject.isNull("pet_available"))pet_available = jsonObject.getString("pet_available");
        if(!jsonObject.isNull("parking"))parking = jsonObject.getString("parking");
        if(!jsonObject.isNull("depiction")){
            setDepiction(jsonObject.getJSONArray("depiction").toString());
        }
        if(!jsonObject.isNull("province"))province = jsonObject.getString("province");
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClosed_for_the_day() {
        return closed_for_the_day;
    }

    public void setClosed_for_the_day(String closed_for_the_day) {
        this.closed_for_the_day = closed_for_the_day;
    }

    public String getTime_available() {
        return time_available;
    }

    public void setTime_available(String time_available) {
        this.time_available = time_available;
    }

    public String getBaby_equipment_rental() {
        return baby_equipment_rental;
    }

    public void setBaby_equipment_rental(String baby_equipment_rental) {
        this.baby_equipment_rental = baby_equipment_rental;
    }

    public String getPet_available() {
        return pet_available;
    }

    public void setPet_available(String pet_available) {
        this.pet_available = pet_available;
    }

    public String getParking() {
        return parking;
    }

    public void setParking(String parking) {
        this.parking = parking;
    }

    public String getDepiction() {
        return depiction;
    }

    public void setProvince(String province) {
        this.province = province.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setDepiction(String depiction) throws JSONException{
        if(this.depiction.equals("")){
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(depiction);
            this.depiction = jsonArray.toString();
        }else{
            JSONArray jsonArray = new JSONArray(this.depiction);
            jsonArray.put(depiction);
            this.depiction = jsonArray.toString();
        }
    }

    public JSONObject makeJSONObject(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(COL_ID, _id);
            jsonObject.put(COL_TITLE, title);
            jsonObject.put(COL_CATEGORY, category);
            jsonObject.put(COL_LATITUDE, latitude);
            jsonObject.put(COL_LONGITUDE, longitude);
            jsonObject.put(COL_ADDRESS, address);
            jsonObject.put(COL_DESCRIPTION, description);
            jsonObject.put(COL_CLOSED_FOR_THE_DAY, closed_for_the_day);
            jsonObject.put(COL_TIME_AVAILABLE, time_available);
            jsonObject.put(COL_BABY_EQUIPMENT_RENTAL, baby_equipment_rental);
            jsonObject.put(COL_PET_AVAILABLE, pet_available);
            jsonObject.put(COL_PARKING,parking);
            jsonObject.put(COL_PROVINCE, province);
            if(depiction.equals("")){
                jsonObject.put(COL_DEPICTION, depiction);
            }else{
                JSONArray jsonArray = new JSONArray(depiction);
                jsonObject.put(COL_DEPICTION, jsonArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}
