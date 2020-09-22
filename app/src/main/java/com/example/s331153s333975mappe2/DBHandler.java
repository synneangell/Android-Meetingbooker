package com.example.s331153s333975mappe2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_KID = "KID";
    static String KEY_NAVN = "Navn";
    static String KEY_TELEFON = "Telefon";

    static String TABLE_MØTER = "Møter";
    static String KEY_MID = "MID";
    static String KEY_TIDSPUNKT = "";
    static String KEY_STED = "Sted";
    static String KEY_TYPE = "Type";

    static String TABLE_DELTAGELSE = "Møtedeltagelse";
    static String KEY_KId = "KID";
    static String KEY_MId = "MID";

    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "DB";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_TABELL = "CREATE TABLE "+TABLE_KONTAKTER
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
