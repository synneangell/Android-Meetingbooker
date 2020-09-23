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
    static String KEY_KID = "_KID";
    static String KEY_KONTAKT_NAVN = "Navn";
    static String KEY_TELEFON = "Telefon";

    static String TABLE_MOTER = "Moter";
    static String KEY_MID = "_MID";
    static String KEY_MOTE_NAVN = "Navn";
    static String KEY_STED = "Sted";
    static String KEY_TIDSPUNKT = "Tidspunkt";

    static String TABLE_MOTEDELTAGELSER = "Motedeltagelser";
    static String KEY_MDID = "_MDID";
    static String KEY_FK_MID = "_MID";
    static String KEY_FK_KID = "_KID";

    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Motebooker";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String LAG_KONTAKTER = "CREATE TABLE " + TABLE_KONTAKTER + "(" + KEY_KID +
                " INTEGER PRIMARY KEY," + KEY_KONTAKT_NAVN + " TEXT," + KEY_TELEFON + " TEXT" + ")";
        Log.d("Lag tabell KONTAKTER", LAG_KONTAKTER);
        db.execSQL(LAG_KONTAKTER);

        String LAG_MOTER = "CREATE TABLE " + TABLE_MOTER + "(" + KEY_MID +
                " INTEGER PRIMARY KEY," + KEY_MOTE_NAVN + " TEXT," + KEY_STED + " TEXT," + KEY_TIDSPUNKT + "TEXT" + ")";
        Log.d("Lag tabell MOTER", LAG_MOTER);
        db.execSQL(LAG_MOTER);

        String LAG_MOTEDELTAGELSER = "CREATE TABLE " + TABLE_MOTEDELTAGELSER + "(" + KEY_MDID +
                " INTEGER PRIMARY KEY," + KEY_FK_KID + "INTEGER," + KEY_FK_MID + "INTEGER," +
                "FOREIGN KEY("+KEY_FK_KID+") REFERENCES "+DBHandler.TABLE_KONTAKTER+"("+KEY_KID+")"
            + ", FOREIGN KEY("+KEY_FK_MID+") REFERENCES "+DBHandler.TABLE_MOTER+"("+KEY_MID+")" + ")";
        Log.d("Lag tabell MOTEDEL", LAG_MOTEDELTAGELSER);
        db.execSQL(LAG_MOTEDELTAGELSER);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        onCreate(db);
    }

    //------Metoder for møter----
    public void leggTilMote(Mote mote) {
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
        db.delete(TABLE_MOTER, KEY_MID + " =? ",
                new String[]{Long.toString(inn_id)});
        db.close();
    }

    public int oppdaterMote(Mote mote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_NAVN, mote.getNavn());
        values.put(KEY_STED, mote.getSted());
        values.put(KEY_TIDSPUNKT, mote.getTidspunkt());
        int endret = db.update(TABLE_MOTER, values, KEY_MID + "= ?",
                new String[]{String.valueOf(mote.get_MID())});
        db.close();
        return endret;
    }

    public List<Mote> finnAlleMoter() {
        List<Mote> moteListe = new ArrayList<Mote>();
        String selectQuery = "SELECT * FROM " + TABLE_MOTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Mote mote = new Mote();
                mote.set_MID(cursor.getLong(0));
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

    //------Metoder for kontakter----
    public void leggTilKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_NAVN, kontakt.getNavn());
        values.put(KEY_TELEFON, kontakt.getTelefon());
        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }

    public List<Kontakt> finnAlleKontakter() {
        List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
        String selectQuery = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();
                kontakt.set_KID(cursor.getLong(0));
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
        db.delete(TABLE_KONTAKTER, KEY_KID + " =? ",
                new String[]{Long.toString(inn_id)});
        db.close();
    }

    public int oppdaterKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KONTAKT_NAVN, kontakt.getNavn());
        values.put(KEY_TELEFON, kontakt.getTelefon());
        int endret = db.update(TABLE_KONTAKTER, values, KEY_KID + "= ?",
                new String[]{String.valueOf(kontakt.get_KID())});
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

    public Kontakt finnKontakt(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KONTAKTER, new String[]{
                        KEY_KID, KEY_KONTAKT_NAVN, KEY_TELEFON}, KEY_KID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Kontakt kontakt = new
                Kontakt(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return kontakt;
    }

    //------Metoder for møtedeltakelse----
    public void leggTilMoteDeltakelse(MoteDeltagelse moteDeltagelse) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_KID, moteDeltagelse.get_KID());
        values.put(KEY_MID, moteDeltagelse.get_MID());
        db.insert(TABLE_MOTEDELTAGELSER, null, values);
        db.close();
    }

    public void slettMoteDeltakelse(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOTEDELTAGELSER, KEY_MDID + " =? ",
                new String[]{Long.toString(inn_id)});
        db.close();
    }

    public List<Kontakt> finnMoteDeltakelse(Long mote_id){
        List<Kontakt> deltakere = new ArrayList<Kontakt>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM" + TABLE_MOTEDELTAGELSER + "WHERE" + KEY_FK_MID + "=" + mote_id;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();
                kontakt.set_KID(cursor.getLong(0));
                kontakt.setNavn(cursor.getString(1));
                kontakt.setTelefon(cursor.getString(2));
                deltakere.add(kontakt);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return deltakere;

    }
}