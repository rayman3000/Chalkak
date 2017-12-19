package bulgogi1216.gmail.photogenic.database;

import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dbsrh on 2017-10-15.
 */

/*
    This Class is able to load JSONObject from .json file in assets folder.
    Furthermore, it contains function to bind multiple json object to an object
 */
public abstract class JSONProcessing{
    protected ArrayList<String> files;
    protected JSONObject rootObj;
    protected Context context;
    protected final String extension = ".json";
    public abstract JSONObject JSONProcess(String filname, JSONObject jsonObject);


    public JSONProcessing(Context context, String... params){
        this.context = context;
        if(files == null)files = new ArrayList<>();
        Collections.addAll(files, params);
        rootObj = new JSONObject();
    }

    public void startProcessing(){
        for(String file : files){
            try {
                JSONObject jsonObject = new JSONObject(loadJSONfromAsset(file + extension, context));
                jsonObject = JSONProcess(file, jsonObject);
                rootObj.put(file, jsonObject);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected static String loadJSONfromAsset(String filename, Context context) throws IOException {
        AssetManager manager = context.getAssets();
        InputStream file = manager.open(filename);
        byte[] formArray = new byte[file.available()];
        file.read(formArray);
        file.close();
        return new String(formArray, "UTF-8");
    }

    public JSONObject getRootObj() {
        return rootObj;
    }

}
