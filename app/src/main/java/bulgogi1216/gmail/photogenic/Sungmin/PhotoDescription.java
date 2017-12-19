package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
 * Created by dbsrh on 2017-10-23.
 */

public class PhotoDescription extends FragmentActivity {
    //FragmentManager fragmentManager;
    private ImageView mImage;
    private AdapterGridImage mAdapterGridImage;
    private TextView mProvince;
    private AutoCompleteTextView AutoComplete;
    private Intent mFromProvinceList;
    private RecyclerView mRecyclerView;
    private String mJSONString;
    private List<Photo> mItems = null;

    Intent GoSearchPage;


    private static final String TAG = PhotoDescription.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_ttour_property_description);
        mRecyclerView = (RecyclerView) findViewById(R.id.rankimageholder);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        gridLayoutManager.setAutoMeasureEnabled(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getApplicationContext(), 3), true));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);


        mImage = (ImageView) findViewById(R.id.property_image);
        mProvince = (TextView) findViewById(R.id.property_province);
        mFromProvinceList = getIntent();
        final String Province = mFromProvinceList.getExtras().getString("province");

        mJSONString = getRankImages(Province);
        mItems = MakePhoto();
        mAdapterGridImage = new AdapterGridImage(getApplicationContext(), mItems);
        mRecyclerView.setAdapter(mAdapterGridImage);

        mProvince.setText(Province);
        if (Province.equals("서울특별시")) {
            mImage.setImageResource(R.drawable.landmark_seoul);
        } else if (Province.equals("인천광역시")) {
            mImage.setImageResource(R.drawable.landmark_incheon);
        } else if (Province.equals("부산광역시")) {
            mImage.setImageResource(R.drawable.landmark_busan);
        } else if (Province.equals("대구광역시")) {
            mImage.setImageResource(R.drawable.landmark_daegu);
        } else if (Province.equals("대전광역시")) {
            mImage.setImageResource(R.drawable.landmark_daejeon);
        } else if (Province.equals("광주광역시")) {
            mImage.setImageResource(R.drawable.landmark_gwangju);
        } else if (Province.equals("울산광역시")) {
            mImage.setImageResource(R.drawable.landmark_ulsan);
        } else if (Province.equals("제주도")) {
            mImage.setImageResource(R.drawable.landmark_jeju);
        } else if (Province.equals("경기도")) {
            mImage.setImageResource(R.drawable.landmark_gyeonggi);
        } else if (Province.equals("강원도")) {
            mImage.setImageResource(R.drawable.landmark_kangwon);
        } else if (Province.equals("충청북도")) {
            mImage.setImageResource(R.drawable.landmark_chungbuk);
        } else if (Province.equals("충청남도")) {
            mImage.setImageResource(R.drawable.landmark_chungnam);
        } else if (Province.equals("전라북도")) {
            mImage.setImageResource(R.drawable.landmark_jeonbuk);
        } else if (Province.equals("전라남도")) {
            mImage.setImageResource(R.drawable.landmark_jeonnam);
        } else if (Province.equals("경상북도")) {
            mImage.setImageResource(R.drawable.landmark_gyeongbuk);
        } else if (Province.equals("경상남도")) {
            mImage.setImageResource(R.drawable.landmark_gyeongnam);
        }

        AutoComplete = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        AutoComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoSearchPage = new Intent(PhotoDescription.this, Search.class);
                GoSearchPage.putExtra("province", Province);
                startActivity(GoSearchPage);
            }
        });

        initToolbar();

        /*fragmentManager = getSupportFragmentManager();
        Fragment image_header = fragmentManager.findFragmentByTag(ImageSlideHeaderAuto.TAG);
        if(image_header == null){
            fragmentManager.beginTransaction()
                    .add(R.id.container_head, ImageSlideHeaderAuto.getInstance(), ImageSlideHeaderAuto.TAG)
                    .commit();
        }
        Fragment image_content = fragmentManager.findFragmentByTag(ImageGridTwoLine.TAG);
        if(image_content == null){
            fragmentManager.beginTransaction()
                    .add(R.id.container_content, ImageGridTwoLine.getInstance(), ImageGridTwoLine.TAG)
                    .commit();
        }*/


    }

    private void initToolbar() {
        /*Tools.displayImageOriginal(this, (ImageView) findViewById(R.id.image_1), com.material.components.R.drawable.image_8);
        Tools.displayImageOriginal(this, (ImageView) findViewById(com.material.components.R.id.image_2), com.material.components.R.drawable.image_9);
        Tools.displayImageOriginal(this, (ImageView) findViewById(com.material.components.R.id.image_3), com.material.components.R.drawable.image_15);
        Tools.displayImageOriginal(this, (ImageView) findViewById(com.material.components.R.id.image_4), com.material.components.R.drawable.image_14);
        Tools.displayImageOriginal(this, (ImageView) findViewById(com.material.components.R.id.image_5), com.material.components.R.drawable.image_12);
        Tools.displayImageOriginal(this, (ImageView) findViewById(com.material.components.R.id.image_6), com.material.components.R.drawable.image_2);
        Tools.displayImageOriginal(this, (ImageView) findViewById(com.material.components.R.id.image_7), com.material.components.R.drawable.image_5);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onDestroy() {
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(InformationDepiction.TAG);
        if(fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(InformationDepiction.TAG))
                    .commit();
        }
        fragment = fragmentManager.findFragmentByTag(ImageGridTwoLine.TAG);
        if(fragment != null){
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(ImageGridTwoLine.TAG))
                    .commit();
        }
        super.onDestroy();
    }*/
    private String getRankImages(String Province)
    {
        String result="";
        RestfulAPI restfulAPI = new RestfulAPI(getString(R.string.end_point),"UTF-8");
        PhotoGET photoGET = new PhotoGET("http");
        photoGET.setLike_asc(false);
        photoGET.setProvince(Province);
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
