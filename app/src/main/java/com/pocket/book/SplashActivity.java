package com.pocket.book;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pocket.book.database.DBRecord;
import com.pocket.book.sharedPreferences.AppSetting;

/**
 * Created by anil on 8/3/2016.
 */

public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * create the database when app start for first time
         * and set to the value false.
         */

        if (AppSetting.isAppStartForFirstTime(this)){

            new DBRecord(this);

            //create the database

            AppSetting.setAppStartForFirstTime(this, false);
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
