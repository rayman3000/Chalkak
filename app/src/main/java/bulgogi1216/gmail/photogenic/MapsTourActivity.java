package bulgogi1216.gmail.photogenic;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import bulgogi1216.gmail.photogenic.database.CPJSONProcess;
import bulgogi1216.gmail.photogenic.database.CulturalProperty;
import bulgogi1216.gmail.photogenic.database.DBManager;
import bulgogi1216.gmail.photogenic.model.MyClusterItem;

public class MapsTourActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    private ClusterManager<MyClusterItem> mClusterManager;
    private GoogleMap mMap;

    //지오코더
    final Geocoder geocoder = new Geocoder(this);
    //관광디비
    DBManager dbManager;
    List<Address> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_tour);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //데이터 삽입
        dbManager = new DBManager(getApplicationContext());
        InitializeDatabase(dbManager);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 구글 맵 객체를 불러온다.
        mMap = googleMap;

        mClusterManager = new ClusterManager<MyClusterItem>(this, mMap);

        //처음 지도 맵 위치 설정
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(35.2345372,129.0886925), 5));

        mMap.setOnCameraIdleListener(mClusterManager);
        mMap.setOnMarkerClickListener(mClusterManager);

        //selection
        DBManager.SelectCondition selectCondition = new DBManager.SelectCondition();
        selectCondition.setProjection("title", "latitude", "longitude");
        selectCondition.setLimit("200");
        //       Log.d("A", selectCondition.getSelectionArgs().toString());
        Cursor cursor = dbManager.select(selectCondition);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    //지오코더 변환
                    try {
                        list = geocoder.getFromLocation(latitude, longitude, 1);
                        Log.e("geo", list.get(0).getAdminArea() + "나누기기"+ list.get(0).getLocality());
                        //       String snippet = list.get(0).getAdminArea() + list.get(0).getLocality();
                        if(list.get(0).getAdminArea()!=null && list.get(0).getLocality()!=null) {
                            String snippet = list.get(0).getAdminArea() + list.get(0).getLocality();
                            MyClusterItem offsetItem = new MyClusterItem(latitude, longitude,title,snippet);
                            mClusterManager.addItem(offsetItem);
                        } else if(list.get(0).getLocality()==null) {
                            String snippet = list.get(0).getAdminArea();
                            MyClusterItem offsetItem = new MyClusterItem(latitude, longitude,title,snippet);
                            mClusterManager.addItem(offsetItem);
                        }
                        else {
                            String snippet = list.get(0).getLocality();
                            MyClusterItem offsetItem = new MyClusterItem(latitude, longitude,title,snippet);
                            mClusterManager.addItem(offsetItem);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Log.d("B", title + " / " + latitude + " / " + longitude);
                } while (cursor.moveToNext());
            }
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    public void InitializeDatabase(DBManager dbManager) {
        String[] filenames = {"BuildingAndStructure", "HistoricalPlace", "IndustrialAttraction", "NaturalAttraction", "NaturalResource", "Observatory"};
        CPJSONProcess cpjsonProcess = new CPJSONProcess(getApplicationContext(), filenames);
        cpjsonProcess.startProcessing();
        JSONObject obj = cpjsonProcess.getRootObj();
        try {
            for (String filename : filenames) {
                JSONArray jsonArray = obj.getJSONObject(filename).getJSONArray("result");
                Log.d("C", jsonArray.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    dbManager.insert(new CulturalProperty(jsonArray.getJSONObject(i)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("D", "Insert Finish");
    }
}
