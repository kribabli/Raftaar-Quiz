package com.example.raftaarquiz.Common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "sajilo.db";


    public static final String TABLE_FAVOURITE = "FAVOURITE";


    public static final String FAVOURITE_ID = "id";
    public static final String FAVOURITE_NAME = "Favourite_book_Name";
    public static final String FAVOURITE_IMAGE = "Favourite_image";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + TABLE_FAVOURITE + "("
                + FAVOURITE_ID + " INTEGER,"
                + FAVOURITE_NAME + " TEXT,"
                + FAVOURITE_IMAGE + " TEXT"
                + ")";

        db.execSQL(CREATE_FAVOURITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
        onCreate(db);

    }

    public void addFavourite(String TableName, ContentValues contentvalues, String s1) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TableName, s1, contentvalues);
    }


    public void removeFavouriteById(String postId, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + tableName + " WHERE id = " + postId);
        db.close();
    }


    public boolean getFavouriteById(String postId, String tableName) {
        boolean count = false;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = new String[]{postId};
        Cursor cursor = db.rawQuery("SELECT id FROM " + tableName + " WHERE id=? ", args);
        if (cursor.moveToFirst()) {
            count = true;
        }
        cursor.close();
        db.close();
        return count;
    }


    public ArrayList<String> getFavouriteId() {
        ArrayList<String> movieList = new ArrayList<>();
        String selectQuery = "SELECT *  FROM "
                + TABLE_FAVOURITE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                movieList.add(String.valueOf(cursor.getColumnIndexOrThrow(FAVOURITE_ID)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movieList;
    }

}
