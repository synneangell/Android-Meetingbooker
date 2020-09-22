package com.example.s331153s333975mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_KID = "KID";
    static String KEY_NAVN = "Navn";
    static String KEY_TELEFON = "Telefon";

    static String TABLE_MOTER = "Møter";
    static String KEY_MID = "MID";
    static String KEY_TIDSPUNKT = "";
    static String KEY_STED = "Sted";
    static String KEY_TYPE = "Type";

    static String TABLE_DELTAGELSE = "Møtedeltagelse";
    static String KEY_DId = "DID";
    static String KEY_KId = "KID";
    static String KEY_MId = "MID";

    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "DB";

    public DBHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_KONTAKTER = "CREATE TABLE "+TABLE_KONTAKTER + "("+ KEY_KID + "INTEGER PRIMARY KEY, "+ KEY_NAVN + "TEXT,"
                + KEY_TELEFON + "TEXT" + ")";
        Log.d("Lag tabell Kontakter : ", LAG_KONTAKTER);

        String LAG_MOTER = "CREATE TABLE "+TABLE_MOTER + "("+ KEY_MID + "INTEGER PRIMARY KEY, "+ KEY_TIDSPUNKT + "TEXT,"
                + KEY_STED + "TEXT," + KEY_TYPE + "TEXT" + ")";
        Log.d("Lag tabell møter : ", LAG_MOTER);

        String LAG_DELTAGELSE = "CREATE TABLE "+TABLE_DELTAGELSE + "("+ KEY_DId + "INTEGER PRIMARY KEY, "+ KEY_KId + "TEXT,"
                + KEY_MId + "TEXT" + ")";
        Log.d("Lag deltagelse : ", LAG_DELTAGELSE);

        db.execSQL(LAG_KONTAKTER);
        db.execSQL(LAG_MOTER);
        db.execSQL(LAG_DELTAGELSE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELTAGELSE);
        onCreate(db);
    }

    public void leggTilKontakt(Kontakt kontakt){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAVN, kontakt.getNavn());
        values.put(KEY_TELEFON, kontakt.getTelefonnr());
        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }

    public void leggTilMoter(Møte møte){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TIDSPUNKT, møte.getTidspunkt());
        values.put(KEY_STED, møte.getSted());
        values.put(KEY_TYPE, møte.getType());
        db.insert(TABLE_MOTER, null, values);
        db.close();
    }

    public void finnAlleKontakter(){
        List<Kontakt> kontaktListe = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();

    }

    public void finnAlleMoter(){

    }

}
