package bulgogi1216.gmail.photogenic.communication.Weather;

import android.content.Context;
import android.net.Uri;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.communication.GETMethod;
import bulgogi1216.gmail.photogenic.communication.RestfulMethod;

/**
 * Created by dbsrh on 2017-10-29.
 */

/*
    Header Parameter
    appKey : string.xml value
    Content-Type : application/json
 */

public class WeatherGET extends RestfulMethod implements GETMethod, WeatherSetting {
    private int version = 1;
    private String lat = "";
    private String lon;
    private String city = "";
    private String county;
    private String village;


    public WeatherGET(String scheme, Context context) {
        super(scheme);
        setHeader("Content-Type", "application/json");
        setHeader("appKey", context.getString(R.string.sk_apikey));
    }


    public WeatherGET(String scheme, Context context, int version) {
        super(scheme);
        this.version = version;
        setHeader("Content-Type", "application/json");
        setHeader("appKey", context.getString(R.string.sk_apikey));
    }

    public void setCoordinate(double latitude, double longitude) {
        this.lat = String.valueOf(latitude);
        this.lon = String.valueOf(longitude);
    }

    /**
     * Description of setCoordinate method {@code setAddress}
     *
     * @param city    - 특별시, 광역시, 도
     * @param county  - 시, 군, 구
     * @param village - 읍, 면, 동
     */
    public void setAddress(String city, String county, String village) {
        this.city = city;
        this.county = county;
        this.village = village;
    }

    @Override
    public String buildUri(String end_point, String... paths) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(scheme)
                .encodedAuthority(end_point);
        for(String path : paths){
            builder.appendPath(path);
        }
        builder.appendQueryParameter("version", String.valueOf(version));
        if (lat.equals("")) {
            builder.appendQueryParameter("city", city);
            builder.appendQueryParameter("county", county);
            builder.appendQueryParameter("village", village);
        }
        if (city.equals("")) {
            builder.appendQueryParameter("lat", lat);
            builder.appendQueryParameter("lon", lon);
        }
        return builder.build().toString();
    }

    @Override
    public String[] getPath() {
        return path;
    }
}
