package bulgogi1216.gmail.photogenic.communication;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by dbsrh on 2017-10-29.
 */

public class RestfulMethod {
    protected String scheme;
    private ArrayList<String> header_key = null;
    private ArrayList<String> header_value = null;

    public RestfulMethod(String scheme){
        this.scheme = scheme;
    }

    public void setHeader(String key, String value){
        if(header_key == null)header_key = new ArrayList<String>();
        if(header_value == null)header_value = new ArrayList<String>();
        if(key != null && value != null){
            header_key.add(key);
            header_value.add(value);
        }
    }

    public String getHeader_key() {
        if(header_key == null){
            return "";
        }else{
            return new JSONArray(header_key).toString();
        }
    }

    public String getHeader_value() {
        if(header_value == null){
            return "";
        }else{
            return new JSONArray(header_value).toString();
        }
    }
}
