package com.pocket.book;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.pocket.book.database.DBRecord;

/**
 * Created by anil on 8/3/2016.
 */

public class MyApplication extends Application{

    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;
        sInstance.initializeInstance();

    }

    private void initializeInstance() {
        //initialize global object which are used frequently in app

    }

    public static MyApplication getInstance(){

        return sInstance;
    }

    public static Context getAppContext(){

        return sInstance.getApplicationContext();
    }


}
