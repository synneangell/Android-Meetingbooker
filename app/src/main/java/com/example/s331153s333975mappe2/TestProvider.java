package com.example.s331153s333975mappe2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class TestProvider extends ContentProvider {
    private static final String _KID = "_KID";
    private static final String KONTAKT_NAVN = "Navn";
    private static final String TELEFON = "Telefon";
    private static final String DB_NAVN = "Motebooker.db";
    private static final int DB_VERSJON = 1;
    private final static String TABELL = "Kontakter";
    public final static String PROVIDER = "com.example.s331153s333975mappe2";
    private static final int KONTAKT_ID = 1;
    private static final int KONTAKTER = 2;
    DBHandler DBhelper;
    SQLiteDatabase db;

    public static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER + "/kontakt");
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "kontakt", KONTAKTER);
        uriMatcher.addURI(PROVIDER, "kontakt/#", KONTAKT_ID);
    }

    @Override
    public boolean onCreate() {
        DBhelper = new DBHandler(getContext());
        db = DBhelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        projection = new String[] { _KID, KONTAKT_NAVN, TELEFON };

        Cursor cur = null;

        if (uriMatcher.match(uri) == KONTAKT_ID) {
            cur = db.query(TABELL, projection, _KID + "=" + uri.getPathSegments().get(1), selectionArgs, null, null, sortOrder);
            return cur;
        }
        else if (uriMatcher.match(uri) == KONTAKTER){
            cur = db.query(TABELL, projection, null, null, null, null, sortOrder);
            return cur;
        }
        else {
            cur = db.query(TABELL, null, null, null, null, null, null);
            return cur;
        }
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case KONTAKTER:
                return "vnd.android.cursor.dir/vnd.example.kontakt";
            case KONTAKT_ID:
                return "vnd.android.cursor.item/vnd.example.kontakt";
            default:
                throw new
                        IllegalArgumentException("Ugyldig URI"+uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = DBhelper.getWritableDatabase();
        db.insert(TABELL, null, values);
        Cursor c = db.query(TABELL, null, null, null, null, null, null);
        c.moveToLast();
        long minid = c.getLong(0); getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, minid);
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uriMatcher.match(uri) == KONTAKTER) {
            db.update(TABELL, values, _KID + " = " + uri.getPathSegments().get(1), null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 1;
        }
        else if (uriMatcher.match(uri) == KONTAKT_ID) {
            db.update(TABELL, values, null, null);
            getContext().getContentResolver().notifyChange(uri, null);
            return 2;

        }
        return 0; }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

}

