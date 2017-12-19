package bulgogi1216.gmail.photogenic.database;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by dbsrh on 2017-10-15.
 */

//This is child class of JSONProcessing, It preprocesses raw .json file in order to match on our data schema, CulturalProperty.
//The only thing when you want to use this class, just implement JSONProcess abstract method.
public class CPJSONProcess extends JSONProcessing{

    private static final String TAG = "CPJSONProcess";

    public CPJSONProcess(Context context, String... params) {
        super(context, params);
    }

    @Override
    public JSONObject JSONProcess(String filename, JSONObject jsonObject) {
        HashMap<String, Object> hm = new HashMap<>();
        JSONArray jsonArray = null;
        JSONObject obj = new JSONObject();
        try {
            jsonArray = jsonObject.getJSONObject("results")
                    .getJSONArray("bindings");
            for(int i=0; i<jsonArray.length(); i++){
                JSONObject object = jsonArray.getJSONObject(i);
                //ignore no key record
                if(!object.isNull(CulturalProperty.COL_ID)){
                    //check existence of the current object
                    if(hm.containsKey(object.getJSONObject(CulturalProperty.COL_ID).getString("value"))){
                        ((CulturalProperty)hm.get(object.getJSONObject(CulturalProperty.COL_ID).getString("value")))
                            .setDepiction(object.getJSONObject(CulturalProperty.COL_DEPICTION).getString("value"));
                    }else {
                        CulturalProperty culturalProperty = new CulturalProperty(context, object, filename);
                        hm.put(culturalProperty.get_id(), culturalProperty);
                    }
                }
            }
            jsonArray = new JSONArray();
            for(String key : hm.keySet()){
                jsonArray.put(((CulturalProperty)hm.get(key)).makeJSONObject());
            }
            obj.put("result", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
