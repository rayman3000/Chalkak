package bulgogi1216.gmail.photogenic.Sungmin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import bulgogi1216.gmail.photogenic.R;
import bulgogi1216.gmail.photogenic.database.DBManager;

/**
 * Created by Sungmin on 2017-10-29.
 */

public class Search extends AppCompatActivity {
    private AutoCompleteTextView mAutoComplete;
    private ImageButton mTextClearButton;
    private DBManager mDBManager;
    private DBManager.SelectCondition mSelectCondition = new DBManager.SelectCondition();
    private Cursor mCursor;
    private ListView mListView;
    private SimpleAdapter mSimpleAdapter;
    private ArrayList<HashMap<String, String>> mList;
    private HashMap<String, String> mMap;
    private Intent mFromPhotoDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sungmin_search_layout);

        mAutoComplete = (AutoCompleteTextView) findViewById(R.id.autocomplete);
        mTextClearButton = (ImageButton) findViewById(R.id.textclearbutton);
        mDBManager = new DBManager(getApplicationContext());
        mListView=(ListView) findViewById(R.id.listview);

        mTextClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAutoComplete.setText(" ");
            }
        });

        mFromPhotoDescription = getIntent();
        final String Province = mFromPhotoDescription.getExtras().getString("province");
        Log.i("province :", Province);

        mAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mList = new ArrayList<HashMap<String,String>>();
                Log.i("text", s.toString());
                mSelectCondition.setProjection("*");
                mSelectCondition.setSelection("title LIKE ? AND province = ?");
                mSelectCondition.setSelectionArgs("%" + mAutoComplete.getText().toString() + "%", Province);
                mCursor = mDBManager.select(mSelectCondition);

                if(mCursor != null){
                    if(mCursor.moveToFirst()){
                        do {
                            mMap = new HashMap<String,String>();
                            String title = mCursor.getString(mCursor.getColumnIndex("title"));
                            String address = mCursor.getString(mCursor.getColumnIndex("address"));
                            String id = mCursor.getString(mCursor.getColumnIndex("_id"));
                            mMap.put("title", title);
                            Log.i("title, address", title + " " + address);
                            mMap.put("address", address);
                            mMap.put("_id", id);
                            mList.add(mMap);
                        }while(mCursor.moveToNext());
                        mSimpleAdapter = new SimpleAdapter(getBaseContext(), mList, android.R.layout.simple_list_item_2, new String[]{"title", "address"}, new int[]{android.R.id.text1, android.R.id.text2});
                        mListView.setAdapter(mSimpleAdapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> item = (HashMap<String, String>) parent.getItemAtPosition(position);
                Intent GoToInformation = new Intent(Search.this, Information.class);
                GoToInformation.putExtra("title", item.get("title"));
                GoToInformation.putExtra("address", item.get("address"));
                GoToInformation.putExtra("_id", item.get("_id"));
                startActivity(GoToInformation);
            }
        });
    }
}


