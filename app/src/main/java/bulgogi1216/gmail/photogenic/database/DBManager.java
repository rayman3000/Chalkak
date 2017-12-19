package bulgogi1216.gmail.photogenic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by dbsrh on 2017-10-13.
 */

//Main Class to manage Database - CRUD function is implemeneted
public class DBManager extends SQLiteOpenHelper {

    public static final String TAG_DB_MANAGER = DBManager.class.getSimpleName();
    public static final String DATABASE_NAME = "CulturalProperty.db";
    public static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS " + CulturalProperty.TABLE_NAME + "(" +
            CulturalProperty.COL_ID + " TEXT PRIMARY KEY," +
            CulturalProperty.COL_TITLE + " TEXT," +
            CulturalProperty.COL_LATITUDE + " REAL," +
            CulturalProperty.COL_CATEGORY + " TEXT," +
            CulturalProperty.COL_LONGITUDE + " REAL," +
            CulturalProperty.COL_ADDRESS + " TEXT," +
            CulturalProperty.COL_PROVINCE + " TEXT," +
            CulturalProperty.COL_DESCRIPTION + " TEXT," +
            CulturalProperty.COL_CLOSED_FOR_THE_DAY + " TEXT," +
            CulturalProperty.COL_TIME_AVAILABLE + " TEXT," +
            CulturalProperty.COL_BABY_EQUIPMENT_RENTAL + " INTEGER," +
            CulturalProperty.COL_PET_AVAILABLE + " INTEGER," +
            CulturalProperty.COL_PARKING + " TEXT," +
            CulturalProperty.COL_DEPICTION + " TEXT" +
            " )";
    private final String DROP_TABLE_QUERY = "DROP TABLE IF EXISTS " + CulturalProperty.TABLE_NAME;

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //at first time when database create
        db.execSQL(DROP_TABLE_QUERY);
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //This method is only a cache for online data;
    }

    //return value row ID - to verify success of insertion
    public long insert(CulturalProperty culturalProperty){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = getContentValues(culturalProperty);
        return db.insert(CulturalProperty.TABLE_NAME, null, contentValues);
    }

    //When you execute update query, you should separate key, and value to update.
    public int update(String _id, CulturalProperty culturalProperty){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = getContentValues(culturalProperty);

        String selection = CulturalProperty.COL_ID + " LIKE ?";
        String[] selectionArgs = {_id};

        return db.update(
                CulturalProperty.TABLE_NAME,
                contentValues,
                selection,
                selectionArgs
        );
    }

    // return value : 0 - fail , 1 - success
    public int delete(String _id){
        SQLiteDatabase db = getWritableDatabase();
        String selection = CulturalProperty.COL_ID + " LIKE ?";
        String[] selectionArgs = {_id};
        return db.delete(CulturalProperty.TABLE_NAME, selection, selectionArgs);
    }

    public void dropTable(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + CulturalProperty.TABLE_NAME);
    }

    //Return cursor
    public Cursor select(SelectCondition selectCondition){
        SQLiteDatabase db = getReadableDatabase();
        return db.query(CulturalProperty.TABLE_NAME,
                selectCondition.getProjection(),
                selectCondition.getSelection(),
                selectCondition.getSelectionArgs(),
                selectCondition.getGroupBy(),
                null,
                selectCondition.getOrderBy(),
                selectCondition.getLimit());
    }
    //Make ContentValues to execute Sqlite query
    private ContentValues getContentValues(CulturalProperty culturalProperty){
        ContentValues contentValues = new ContentValues();
        //All values are optional, if statements check value whether assigned or not
        if(!culturalProperty.get_id().equals(""))
        contentValues.put(CulturalProperty.COL_ID, culturalProperty.get_id());
        if(!culturalProperty.getTitle().equals(""))
            contentValues.put(CulturalProperty.COL_TITLE, culturalProperty.getTitle());
        if(!culturalProperty.getTitle().equals(""))
            contentValues.put(CulturalProperty.COL_CATEGORY, culturalProperty.getCategory());
        if(culturalProperty.getLatitude() != 0)
            contentValues.put(CulturalProperty.COL_LATITUDE, culturalProperty.getLatitude());
        if(culturalProperty.getLongitude() != 0)
            contentValues.put(CulturalProperty.COL_LONGITUDE, culturalProperty.getLongitude());
        if(!culturalProperty.getAddress().equals(""))
            contentValues.put(CulturalProperty.COL_ADDRESS, culturalProperty.getAddress());
        if(!culturalProperty.getDescription().equals(""))
            contentValues.put(CulturalProperty.COL_DESCRIPTION, culturalProperty.getDescription());
        if(!culturalProperty.getClosed_for_the_day().equals(""))
            contentValues.put(CulturalProperty.COL_CLOSED_FOR_THE_DAY, culturalProperty.getClosed_for_the_day());
        if(!culturalProperty.getTime_available().equals(""))
            contentValues.put(CulturalProperty.COL_TIME_AVAILABLE, culturalProperty.getTime_available());
        if(!culturalProperty.getParking().equals(""))
            contentValues.put(CulturalProperty.COL_PARKING, culturalProperty.getParking());
        if(!culturalProperty.getBaby_equipment_rental().equals(""))
            contentValues.put(CulturalProperty.COL_BABY_EQUIPMENT_RENTAL, culturalProperty.getBaby_equipment_rental());
        if(!culturalProperty.getPet_available().equals(""))
            contentValues.put(CulturalProperty.COL_PET_AVAILABLE, culturalProperty.getPet_available());
        if(culturalProperty.getDepiction() != null)
            contentValues.put(CulturalProperty.COL_DEPICTION, culturalProperty.getDepiction());
        if(culturalProperty.getProvince() != null)
            contentValues.put(CulturalProperty.COL_PROVINCE, culturalProperty.getProvince());
        return contentValues;
    }

    //Select Condition Class - You should build this class up to execute select query
    public static class SelectCondition{
        //return columns
        String[] projection = null;
        //Filter condition
        //example : CATEGORY LIKE ?
        String selection = null;
        //Filter arguments
        //example : value...
        String[] selectionArgs = null;
        //Group by
        String groupBy = null;
        //Order by
        //example : CATEGORY DESC
        String orderBy = null;
        //Limit
        //example : 50
        String limit = "10";

        @Nullable
        public String[] getProjection() {
            return projection;
        }

        public void setProjection(String... columns) {
            projection = columns;
        }
        @Nullable
        public String getSelection() {
            return selection;
        }

        public void setSelection(String selection) {
            this.selection = selection;
        }
        @Nullable
        public String[] getSelectionArgs() {
            return selectionArgs;
        }

        public void setSelectionArgs(String... values) {
            String[] str_arr = new String[values.length];
            System.arraycopy(values, 0, str_arr, 0, values.length);
            this.selectionArgs = str_arr;
            for(int i=0;i<str_arr.length; i++)
            Log.i("selectionargs", str_arr[i]);
        }

        @Nullable
        public String getGroupBy() {
            return groupBy;
        }

        public void setGroupBy(String groupBy) {
            this.groupBy = groupBy;
        }
        @Nullable
        public String getOrderBy() {
            return orderBy;
        }

        public void setOrderBy(String column, String order) {
            this.orderBy = column + " " + order;
        }
        @Nullable
        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }
    }
}
