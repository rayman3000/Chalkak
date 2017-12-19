package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.database.DBManager;

/**
 * Created by Sungmin on 2017-10-29.
 */

public class Information extends FragmentActivity{
    private FragmentManager fragmentManager;
    private Intent mGetExtras;
    private DBManager mDBManager;
    private DBManager.SelectCondition mSelectCondition = new DBManager.SelectCondition();
    private Cursor mCursor;
    private JSONObject mInformation;
    private String ID;

    private static final String TAG = PhotoDescription.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_information);
        mGetExtras = getIntent();
        String Title = mGetExtras.getExtras().getString("title");
        String Address = mGetExtras.getExtras().getString("address");
        ID = mGetExtras.getExtras().getString("_id");
        ArrayList<String> arrayList;

        mDBManager = new DBManager(getApplicationContext());
        mSelectCondition.setProjection("*");
        mSelectCondition.setSelection("title = ? AND address = ? AND _id = ?");
        mSelectCondition.setSelectionArgs(Title, Address, ID);
        mCursor = mDBManager.select(mSelectCondition);
        if(mCursor != null){
            if(mCursor.moveToFirst()){
                do {
                    mInformation = CursorToJSON(mCursor);
                    arrayList = GetDepiction();
                }while(mCursor.moveToNext());
            }
        }

        initToolbar();

        fragmentManager = getSupportFragmentManager();
        Fragment image_header = fragmentManager.findFragmentByTag(InformationDepiction.TAG);
        if(image_header == null){
            fragmentManager.beginTransaction()
                    .add(R.id.depictions, InformationDepiction.getInstance(), InformationDepiction.TAG)
                    .commit();
        }
        Fragment image_content = fragmentManager.findFragmentByTag(InformationDescription.TAG);
        if(image_content == null){
            fragmentManager.beginTransaction()
                    .add(R.id.descriptions, InformationDescription.getInstance(), InformationDescription.TAG)
                    .commit();
        }
        Fragment image_usercontent = fragmentManager.findFragmentByTag(GridImage.TAG);
        if (image_usercontent == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.usercontents, GridImage.getInstance(), GridImage.TAG)
                    .commit();
        }


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

    @Override
    protected void onDestroy() {
        fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(InformationDepiction.TAG);
        if(fragment != null) {
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(InformationDepiction.TAG))
                    //.commit();
                    .commitAllowingStateLoss();
        }
        fragment = fragmentManager.findFragmentByTag(InformationDescription.TAG);
        if(fragment != null){
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(InformationDescription.TAG))
                    //.commit();
                    .commitAllowingStateLoss();
        }
        fragment = fragmentManager.findFragmentByTag(GridImage.TAG);
        if(fragment!=null){
            fragmentManager.beginTransaction()
                    .remove(fragmentManager.findFragmentByTag(GridImage.TAG))
                    .commitAllowingStateLoss();
        }
        super.onDestroy();
    }

    public JSONObject CursorToJSON(Cursor c) {
        JSONObject retVal = new JSONObject();
        for(int i=0; i<c.getColumnCount(); i++) {
            String cName = c.getColumnName(i);
            try {
                switch (c.getType(i)) {
                    case Cursor.FIELD_TYPE_INTEGER:
                        retVal.put(cName, c.getInt(i));
                        break;
                    case Cursor.FIELD_TYPE_FLOAT:
                        retVal.put(cName, c.getFloat(i));
                        break;
                    case Cursor.FIELD_TYPE_STRING:
                        retVal.put(cName, c.getString(i));
                        break;
                }
            }
            catch(Exception ex) {
            }
        }
        return retVal;
    }


    public ArrayList<String> GetDepiction()
    {
        //Log.i("Depictions", mInformation.toString());
        ArrayList<String> Depictions = new ArrayList<String>();
        try {
            JSONArray JArray = new JSONArray(mInformation.getString("depiction"));
            JSONArray JArray2 = new JSONArray(JArray.getString(0));
            for(int i=0; i<JArray2.length(); i++)
            {
                //Log.i("Depictions", JArray2.getString(i));
                Depictions.add(JArray2.getString(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Depictions;
    }
    public JSONObject getJSONObject()
    {
        return mInformation;
    }

    public String GetCulturalPropertyID()
    {
        return ID;
    }
}
