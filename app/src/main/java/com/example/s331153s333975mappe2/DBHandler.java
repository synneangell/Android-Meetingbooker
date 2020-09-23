package com.example.s331153s333975mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_KONTAKTER = "Kontakter";
    static String KEY_KONTAKT_ID = "_ID";
    static String KEY_KONTAKT_NAVN = "Navn";
    static String KEY_TELEFON = "Telefon";
    //static ArrayList<Kontakt> KEY_VENNER = "Venner";

    static String TABLE_MOTER = "Møter";
    static String KEY_MOTE_ID = "_ID";
    static String KEY_MOTE_NAVN = "Navn";
    static String KEY_STED = "Sted";
    static String KEY_TIDSPUNKT = "Tidspunkt";

    static String TABLE_MOTEDELTAGELSER = "Møtedeltagelser";
    static String KEY_MOTEDELTAGELSE_ID = "_ID";
    //Skal vi ta inn hele objektet møte eller bare id'en?
    static String KEY_FKMOTE = "MoteID"; //Hvordan få primærnøkkel fra en annen tabell?
    //static ArrayList<Kontakt> KEY_DELTAGERE = "Deltagere";   //Hva skal inn her?

    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Møtebooker";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_KONTAKTER = "CREATE TABLE " + TABLE_KONTAKTER + "(" + KEY_KONTAKT_ID +
                " INTEGER PRIMARY KEY," + KEY_KONTAKT_NAVN + " TEXT," + KEY_TELEFON + " TEXT" + ")";
        Log.d("Lag tabell KONTAKTER", LAG_KONTAKTER);
        db.execSQL(LAG_KONTAKTER);

        String LAG_MOTER = "CREATE TABLE " + TABLE_MOTER + "(" + KEY_MOTE_ID +
                " INTEGER PRIMARY KEY," + KEY_MOTE_NAVN + " TEXT," + KEY_STED + " TEXT," + KEY_TIDSPUNKT + "TEXT" + ")";
        Log.d("Lag tabell MOTER", LAG_MOTER);
        db.execSQL(LAG_MOTER);

        //Mangler arraylist med deltagere
        String LAG_MOTEDELTAGELSER = "CREATE TABLE " + TABLE_MOTEDELTAGELSER + "(" + KEY_MOTEDELTAGELSE_ID +
                " INTEGER PRIMARY KEY," + KEY_FKMOTE + "TEXT" + ")";
        Log.d("Lag tabell MOTEDTG", LAG_MOTEDELTAGELSER);
        db.execSQL(LAG_MOTEDELTAGELSER);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        onCreate(db);
    }

    public void leggTilMote(Model_Mote mote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_NAVN, mote.getNavn());
        values.put(KEY_STED, mote.getSted());
        values.put(KEY_TIDSPUNKT, mote.getTidspunkt());
        db.insert(TABLE_MOTER, null, values);
        db.close();
    }

    public void slettMote(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOTER, KEY_MOTE_ID + " =? ",
                new String[]{Long.toString(inn_id)});
        db.close();
    }

    public int oppdaterMote(Model_Mote mote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_NAVN, mote.getNavn());
        values.put(KEY_STED, mote.getSted());
        values.put(KEY_TIDSPUNKT, mote.getTidspunkt());
        int endret = db.update(TABLE_MOTER, values, KEY_MOTE_ID + "= ?",
                new String[]{String.valueOf(mote.get_ID())});
        db.close();
        return endret;
    }

    public List<Model_Mote> finnAlleMoter() {
        List<Model_Mote> moteListe = new ArrayList<Model_Mote>();
        String selectQuery = "SELECT * FROM " + TABLE_MOTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Model_Mote mote = new Model_Mote();
                mote.set_ID(cursor.getLong(0));
                mote.setNavn(cursor.getString(1));
                mote.setSted(cursor.getString(2));
                mote.setTidspunkt(cursor.getString(2));
                moteListe.add(mote);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return moteListe;
    }

    public void leggTilKontakt(Model_Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_NAVN, kontakt.getNavn());
        values.put(KEY_TELEFON, kontakt.getTelefon());
        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }

    public List<Model_Kontakt> finnAlleKontakter() {
        List<Model_Kontakt> kontaktListe = new ArrayList<Model_Kontakt>();
        String selectQuery = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Model_Kontakt kontakt = new Model_Kontakt();
                kontakt.set_ID(cursor.getLong(0));
                kontakt.setNavn(cursor.getString(1));
                kontakt.setTelefon(cursor.getString(2));
                kontaktListe.add(kontakt);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }
    public void slettKontakt(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KEY_KONTAKT_ID + " =? ",
                new String[]{Long.toString(inn_id)});
        db.close();
    }

    public int oppdaterKontakt(Model_Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_NAVN, kontakt.getNavn());
        values.put(KEY_TELEFON, kontakt.getTelefon());
        int endret = db.update(TABLE_KONTAKTER, values, KEY_KONTAKT_ID + "= ?",
                new String[]{String.valueOf(kontakt.get_ID())});
        db.close();
        return endret;
    }

    public int finnAntallKontakter() {
        String sql = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int antall = cursor.getCount();
        cursor.close();
        db.close();
        return antall;
    }

    public Model_Kontakt finnKontakt(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KONTAKTER, new String[]{
                        KEY_KONTAKT_ID, KEY_KONTAKT_NAVN, KEY_TELEFON}, KEY_KONTAKT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Model_Kontakt kontakt = new
                Model_Kontakt(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return kontakt;
    }
}