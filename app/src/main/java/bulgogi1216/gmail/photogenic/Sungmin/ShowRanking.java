package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.material.components.utils.Tools;
import com.material.components.widget.SpacingItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.communication.Photo.PhotoGET;
import bulgogi1216.gmail.photogenic.communication.RestfulAPI;
import bulgogi1216.gmail.photogenic.database.Photo;

/**
 * Created by Sungmin on 2017-12-14.
 */

public class ShowRanking extends AppCompatActivity{
    private Intent mGetQuery;
    private RecyclerView mRecyclerView;
    private AdapterGridImage mAdapterGridImage;
    private List<Photo> mItems = null;
    private String mJSONString = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_showranking);
        mRecyclerView = (RecyclerView) findViewById(R.id.showrankimageholder);
        mGetQuery = getIntent();
        String province = mGetQuery.getExtras().getString("province");
        String property = mGetQuery.getExtras().getString("property");
        String condition = mGetQuery.getExtras().getString("condition");
        Log.i("conditions", province + ", " + property + ", " + condition);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getApplicationContext(), 3), true));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        mJSONString = getRankImages(province, property, condition);
        mItems = MakePhoto();
        mAdapterGridImage = new AdapterGridImage(getApplicationContext(), mItems);
        mRecyclerView.setAdapter(mAdapterGridImage);

    }
    private String getRankImages(String province, String property, String condition)
    {
        String result="";
        RestfulAPI restfulAPI = new RestfulAPI(getString(R.string.end_point),"UTF-8");
        PhotoGET photoGET = new PhotoGET("http");
        //Conditions : province | cultural property | creativity, Background, person, raters, rating, likes

        //condition #1
        if(province.compareTo("none") != 1)
            photoGET.setProvince(province);
        //condition #2
        if(property.compareTo("none") != 1)
            photoGET.setCultural_property(property);
        //condition #3
        if(condition.compareTo("likes") == 1 || condition.compareTo("none") == 1)
            photoGET.setLike_asc(false);
        else if(condition.compareTo("rater") == 1)
            photoGET.setRater_asc(false);
        try {
            result = restfulAPI.GET(photoGET);
            Log.i("RESTresult", result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    private List<Photo> MakePhoto() {
        List<Photo> PhotoList = new ArrayList();
        try {
            JSONArray jsonArray = new JSONArray(mJSONString);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Photo photo = new Photo(jsonObject);
                PhotoList.add(photo);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return PhotoList;
    }
}
