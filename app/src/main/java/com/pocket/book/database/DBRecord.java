package com.pocket.book.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by anil on 8/3/2016.
 */

public class DBRecord extends SQLiteOpenHelper{

    private static final String TAG = "DBRecord";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "pocketBook.db";

    public static final String TABLE_RECORD = "record";

    // Contacts Table Columns names
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE_PATH = "imagePath";
    public static final String KEY_CREATED_DATE = "createDate";
    public static final String KEY_MODIFIED_DATE = "modifiedDate";

    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_RECORD +"("+
             KEY_ID +" integer primary key autoincrement,"+
             KEY_TITLE + " text,"+
             KEY_DESCRIPTION + " text,"+
             KEY_IMAGE_PATH + " text,"+
             KEY_CREATED_DATE + " text," +
             KEY_MODIFIED_DATE + " text);";


    public DBRecord(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "creating database" + DATABASE_CREATE);

        db.execSQL(DATABASE_CREATE);

        Log.e(TAG, "database created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);

        // Create tables again
        onCreate(db);
    }



}
