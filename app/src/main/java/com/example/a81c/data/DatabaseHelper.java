package com.example.a81c.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //define sql command with all advert elements
        String CREATE_TABLE = "CREATE TABLE " + Util.TABLE_NAME + "( " + Util.FULL_NAME + " TEXT PRIMARY KEY, "
                + Util.USERNAME + " TEXT," + Util.PASSWORD + " TEXT," + Util.PLAYLIST_ID + " TEXT )";

        //execute sql
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //define sql drop command
        String DROP_TABLE = "DROP TABLE IF EXISTS";

        //execute sql
        db.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});

        //call to recreate database
        onCreate(db);
    }

    public long insertUser(User user) {
        //obtain database and define values
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        //set each element into values
        contentValues.put(Util.FULL_NAME, user.getFullname());
        contentValues.put(Util.USERNAME, user.getUsername());
        contentValues.put(Util.PASSWORD, user.getPassword());
        contentValues.put(Util.PLAYLIST_ID, user.getPlaylistList());

        //insert values into table row
        long newRowId = db.insert(Util.TABLE_NAME, null, contentValues);
        db.close();

        //return table row
        return newRowId;
    }

    //attempt to return user from login
    public User loginCheck(String givenUsername, String GivenPassword) {
        //obtain database
        SQLiteDatabase db = this.getWritableDatabase();

        //check for username password match
        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.USERNAME + "  = '" + givenUsername + "' AND " + Util.PASSWORD + " = '" + GivenPassword + "'", null);

        if (cursor.moveToFirst()) {
            String fullname = cursor.getString(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String playlist = cursor.getString(3);

            User user = new User(fullname, username, password, playlist);

            return user;
        }
        else {
            return null;
        }
    }

    //check for duplicate values
    public boolean userDupCheck(String fullname) {
        //obtain database
        SQLiteDatabase db = this.getReadableDatabase();

        //sql where condition
        String DUPLICATE_CHECK = Util.FULL_NAME + " = '" + fullname + "'";

        //search for values
        Cursor cursor = db.query(Util.TABLE_NAME, null, DUPLICATE_CHECK, null, null, null, null);
        int numberOfRows = cursor.getCount();
        db.close();

        //return result
        if (numberOfRows == 0) {
            return false;
        }
        else {
            return true;
        }
    }

    //add url to playlist
    public void addToPlaylist(String fullname, String url) {
        //obtain database
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.FULL_NAME + "  = '" + fullname + "'", null);

        if (cursor.moveToFirst()) {
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String playlistList = cursor.getString(3);

            playlistList = playlistList + url + ", ";

            ContentValues contentValues = new ContentValues();

            //set each element into values
            contentValues.put(Util.FULL_NAME, fullname);
            contentValues.put(Util.USERNAME, username);
            contentValues.put(Util.PASSWORD, password);
            contentValues.put(Util.PLAYLIST_ID, playlistList);

            //update row
            db.update(Util.TABLE_NAME, contentValues, Util.FULL_NAME + " = '" + fullname + "'", null);
        }
        db.close();
    }

    //checks for empty playlist and converts playlist string to list
    public List<String> playlistToList(String fullname) {
        //obtain database
        SQLiteDatabase db = this.getWritableDatabase();

        List<String> playList = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Util.TABLE_NAME + " WHERE " + Util.FULL_NAME + "  = '" + fullname + "'", null);

        //
        if (cursor.moveToFirst()) {
            //get users playlist then close database
            String playlistList = cursor.getString(3);

            //split into list
            playList = Arrays.asList(playlistList.split("\\s*,\\s*"));
        }
        db.close();

        return playList;
    }

    public void delete(String name) {
        //obtain database
        SQLiteDatabase db = this.getWritableDatabase();

        //search for id and delete
        db.delete(Util.TABLE_NAME, Util.FULL_NAME + "= '" + name + "'", null);

        db.close();
    }
}
