package bulgogi1216.gmail.photogenic.communication;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import bulgogi1216.gmail.photogenic.database.CPJSONProcess;
import bulgogi1216.gmail.photogenic.database.CulturalProperty;
import bulgogi1216.gmail.photogenic.database.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by dbsrh on 2017-10-27.
 */

public class Tool {

    private static final String TAG = Tool.class.getSimpleName();

    public static void appendValue(StringBuilder stringBuilder, String key, String value, String char_set, boolean last) throws UnsupportedEncodingException {
        stringBuilder.append(key);
        stringBuilder.append("=");
        stringBuilder.append(URLEncoder.encode(value, char_set));
        if(!last)stringBuilder.append("&");
    }
    public static void InitializeDatabase(Context context, DBManager dbManager){
        String[] filenames = {"BuildingAndStructure", "HistoricalPlace","IndustrialAttraction","NaturalAttraction","NaturalResource","Observatory"};
        CPJSONProcess cpjsonProcess = new CPJSONProcess(context, filenames);
        cpjsonProcess.startProcessing();
        JSONObject obj = cpjsonProcess.getRootObj();
        try {
            for (String filename : filenames) {
                JSONArray jsonArray = obj.getJSONObject(filename).getJSONArray("result");
                Log.d(TAG, jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    dbManager.insert(new CulturalProperty(jsonArray.getJSONObject(i)));

                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Insert Finish");
    }

    public static String GetProvince(Context context ,double latitude, double longitude)
    {
        Geocoder geocoder = new Geocoder(context);
        List<Address> list = null;
        String RetVal = null;
        try {
            list = geocoder.getFromLocation(latitude, longitude, 1);
            RetVal = list.get(0).getAdminArea();
            if(RetVal == null)RetVal = list.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RetVal;
    }
}
