package com.pocket.book.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by anil on 8/4/2016.
 */

public class AppSetting {


    public static final String MY_PREFS_NAME = "POCKET_BOOK";


    public static void setAppStartForFirstTime(Context context, boolean b){

        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean("boolean", b);
        editor.commit();

    }

    public static boolean isAppStartForFirstTime(Context context){

        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean("boolean",true);

    }

}
