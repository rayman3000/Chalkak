package bulgogi1216.gmail.photogenic.communication;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by dbsrh on 2017-10-25.
 */

/*
    RestFul API 사용방법

    Example - GET Method, Photo 에 대해서 get방식으로 받아올 때 (다른 방식도 유사함)
    RestfulAPI restfulAPI = new RestfulAPI(end_point);

    * API Document 를 보고 원하는 쿼리를 완성합니다. 각 객체는 Method 별 사용가능한 parameter 를 멤버변수로 가지고 있습니다.
    PhotoGET photoGET = new PhotoGET();
    photoGET.setLimit(100);

    String result = restfulAPI.GET(photoGET);

    -----------------------------------------

 */

public class RestfulAPI {
    private String end_point;
    private String encoding_;
    private final String TAG = RestfulAPI.class.getSimpleName();
    private final int HTTP_RESPONSE_SUCCESS = 200;

    private final int ConnectTimeOut = 3000;
    private final int ReadTimeOut = 3000;


    public RestfulAPI(String end_point, String encoding_){
        this.end_point = end_point;
        this.encoding_ = encoding_;
    }

   public String GET(GETMethod obj) throws ExecutionException, InterruptedException {
       return new HttpCommunication("GET",obj.buildUri(end_point, obj.getPath()))
               .execute(obj.getHeader_key(),
                       obj.getHeader_value()).get();
    }
    public String POST(POSTMethod obj) throws UnsupportedEncodingException, ExecutionException, InterruptedException {
        return new HttpCommunication("POST",obj.buildUri(end_point,obj.getPath()))
                .execute(obj.getHeader_key(),
                        obj.getHeader_value(),
                        obj.getW3FormEncodedData(encoding_)).get();
    }
    public String PUT(PUTMethod obj) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        return new HttpCommunication("PUT", obj.buildUri(end_point,obj.getPath()))
                .execute(obj.getHeader_key(),
                        obj.getHeader_value(),
                        obj.getW3FormEncodedData(encoding_)).get();
    }
    public String DELETE(DELETEMethod obj) throws ExecutionException, InterruptedException, UnsupportedEncodingException {
        return new HttpCommunication("DELETE", obj.buildUri(end_point,obj.getPath()))
                .execute(obj.getHeader_key(),
                        obj.getHeader_value(),
                        obj.getW3FormEncodedData(encoding_)).get();
    }

    /*
        Parameter Order
            1. Header Key
            2. Header Data
            3. Data(Optional)
     */
    private class HttpCommunication extends AsyncTask<String, Void, String>{
        private String method;
        private String url;

        private HttpCommunication(String method, String url){
            this.method = method;
            this.url = url;
        }

        @Override
        protected String doInBackground(String... params) {
            int data;
            String response = "";
            //Enable Writing flag
            boolean output_flag = false;

            try {
                URL url = new URL(this.url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setConnectTimeout(ConnectTimeOut);
                httpURLConnection.setReadTimeout(ReadTimeOut);
                if(!params[0].equals("")){
                    JSONArray key_array = new JSONArray(params[0]);
                    JSONArray value_array = new JSONArray(params[1]);
                    for(int i=0; i<key_array.length(); i++){
                        httpURLConnection.setRequestProperty(key_array.getString(i),value_array.getString(i));
                    }
                }
                if(!(this.method).equals("GET")){
                    output_flag=true;
                }
                httpURLConnection.setRequestMethod(this.method);
                httpURLConnection.setDoOutput(output_flag);
                //Send data
                if(output_flag){
                    //Unknown data size
                    httpURLConnection.setChunkedStreamingMode(0);
                    ByteArrayInputStream in = new ByteArrayInputStream(params[2].getBytes());
                    BufferedOutputStream out = new BufferedOutputStream(httpURLConnection.getOutputStream());
                    while((data=in.read()) != -1){
                        out.write(data);
                    }
                    in.close();
                    out.flush();
                    out.close();
                }
                //Success
                if(httpURLConnection.getResponseCode() == HTTP_RESPONSE_SUCCESS) {
                    BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    while ((data = in.read()) != -1) {
                        out.write(data);
                    }
                    response = out.toString();
                    in.close();
                    out.close();
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

}
