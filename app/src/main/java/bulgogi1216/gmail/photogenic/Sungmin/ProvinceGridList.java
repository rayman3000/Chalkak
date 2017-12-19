package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.database.CPJSONProcess;
import bulgogi1216.gmail.photogenic.database.CulturalProperty;
import bulgogi1216.gmail.photogenic.database.DBManager;

/**
 * Created by Sungmin on 2017-11-02.
 */

public class ProvinceGridList extends AppCompatActivity{
    GridAdapter mGridAdapter;
    GridView mGridView;
    private DBManager dbManager;

    public static final String TAG = "MAIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_province_gridlist);

        dbManager = new DBManager(getApplicationContext());
        //InitializeDatabase(dbManager);

        DBManager.SelectCondition selectCondition = new DBManager.SelectCondition();
        selectCondition.setProjection("*");
        selectCondition.setLimit("200");
        Cursor cursor = dbManager.select(selectCondition);

        if(cursor != null){
            if(cursor.moveToFirst()){
                do {
                    String id = cursor.getString(cursor.getColumnIndex("_id"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String category = cursor.getString(cursor.getColumnIndex("category"));
                    Double latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
                    Double longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
                    String address = cursor.getString(cursor.getColumnIndex(CulturalProperty.COL_ADDRESS));
                    Log.d(TAG, id + " / " + title + " / " + category + " / " + latitude + " / " + longitude + " / " + address);
                }while(cursor.moveToNext());
            }
        }

        final String Provinces[] = {"서울특별시","부산광역시","인천광역시","대구광역시","대전광역시","광주광역시","울산광역시","제주도","경기도","강원도","충청북도","충청남도","전라북도","전라남도","경상북도","경상남도"};
        int ProvinceImage[] = {
            R.drawable.province_seoul, R.drawable.province_busan, R.drawable.province_incheon, R.drawable.province_daegu, R.drawable.province_daejeon, R.drawable.province_gwangju, R.drawable.province_ulsan,
                    R.drawable.province_jeju, R.drawable.province_gyeonggi, R.drawable.province_kangwon, R.drawable.province_chungbuk, R.drawable.province_chungnam, R.drawable.province_jeonbuk, R.drawable.province_jeonnam,
                    R.drawable.province_gyeongbuk, R.drawable.province_gyeongnam
        };
        mGridAdapter = new GridAdapter(
                getApplicationContext(),
                R.layout.sungmin_province_grid,
                Provinces,
                ProvinceImage
        );
        mGridView = (GridView)findViewById(R.id.province_gridlist);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent GoSearch = new Intent(ProvinceGridList.this, PhotoDescription.class);
                GoSearch.putExtra("province", Provinces[position]);
                startActivity(GoSearch);
            }
        });
    }

    public void InitializeDatabase(DBManager dbManager){
        String[] filenames = {"BuildingAndStructure", "HistoricalPlace","IndustrialAttraction","NaturalAttraction","NaturalResource","Observatory"};
        CPJSONProcess cpjsonProcess = new CPJSONProcess(getApplicationContext(), filenames);
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
}
class GridAdapter extends BaseAdapter{
    Context mContext;
    int mLayout;
    String mProvinces[];
    int mProvinceImage[];
    LayoutInflater mLayoutInflater;

    public GridAdapter(Context context, int layout, String provinces[], int provinceimage[])
    {
        this.mContext = context;
        this.mLayout = layout;
        this.mProvinces = provinces;
        this.mProvinceImage = provinceimage;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mProvinces.length;
    }

    @Override
    public Object getItem(int position) {
        return mProvinces[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
            convertView = mLayoutInflater.inflate(mLayout, null);
        TextView tv = (TextView) convertView.findViewById(R.id.province_text);
        ImageView iv = (ImageView) convertView.findViewById(R.id.province_image);
        tv.setText(mProvinces[position]);
        iv.setImageResource(mProvinceImage[position]);

        return convertView;
    }
}
