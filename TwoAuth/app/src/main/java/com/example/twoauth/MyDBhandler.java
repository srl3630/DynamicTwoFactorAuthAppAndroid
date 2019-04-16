package com.example.twoauth;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class MyDBhandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "creds.db";
    public static final String TABLE_CREDS = "creds";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER = "username";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_COUNTER = "counter";
    public static final String COLUMN_URL = "URL";


    public MyDBhandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "  + TABLE_CREDS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_URL + " TEXT, " +
                COLUMN_USER + "  TEXT, " +
                COLUMN_TOKEN + " TEXT, " +
                COLUMN_COUNTER + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CREDS);
        onCreate(db);
    }

    //add new row to the database
    public void addCreds(Creds cred){
        ContentValues values = new ContentValues();
        values.put(COLUMN_URL,cred.get_url());
        values.put(COLUMN_USER,cred.get_user());
        values.put(COLUMN_TOKEN,cred.get_token());
        values.put(COLUMN_COUNTER, 0);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_CREDS,null, values);
        db.close();
    }

    //Delete product from DB
    public void deleteCred(Integer credID){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CREDS + " WHERE "+ COLUMN_ID + "=\"" + credID + "\";");
    }

    public void incrementCount(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_CREDS + " SET "+ COLUMN_COUNTER + " = " + COLUMN_COUNTER + " + 1;");
    }

    public void dropAll(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CREDS );
        db.execSQL("VACUUM");
    }

    //Print DB
    public List dbtoStr(){
        List dblist = new ArrayList();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CREDS + " WHERE 1";

        //Cursor points to location in results
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("URL")) != null){
                dblist.add(new Creds(c.getInt(c.getColumnIndex("ID")),
                        c.getString(c.getColumnIndex("URL")),
                        c.getString(c.getColumnIndex("username")),
                        c.getString(c.getColumnIndex("token")),
                        c.getInt(c.getColumnIndex("counter"))));
            }
            c.move(1);
        }
        db.close();
        return dblist;
    }
}
