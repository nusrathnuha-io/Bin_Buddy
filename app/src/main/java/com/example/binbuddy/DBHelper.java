package com.example.binbuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "BinBuddy.db";
    private static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for users
        db.execSQL("CREATE TABLE  users(username TEXT PRIMARY KEY, password TEXT)");

        // Create table for admins
        db.execSQL("CREATE TABLE  admins(username TEXT PRIMARY KEY, password TEXT)");

        // Create table for drivers
        db.execSQL("CREATE TABLE  drivers(username TEXT PRIMARY KEY, password TEXT)");

        // Create table for driver information
        db.execSQL("CREATE TABLE  Driver_Information (" +
                "driver_name TEXT," +
                "driver_id TEXT PRIMARY KEY ," +
                "vehicle_id TEXT," +
                "license_no TEXT UNIQUE," +  // Added UNIQUE constraint to license_no
                "phone_number TEXT)");

        // Create table for requests
        db.execSQL("CREATE TABLE  requests (" +
                "request_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "phone_number TEXT," +
                "bag_count INTEGER," +
                "address TEXT," +
                "location TEXT," +
                "driver_id TEXT," +
                "status TEXT," +
                "FOREIGN KEY(driver_id) REFERENCES Driver_Information(driver_id) ON DELETE CASCADE)");


        // Create table for reports
        db.execSQL("CREATE TABLE  reports (" +
                "report_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "phone_number TEXT," +
                "address TEXT," +
                "complain TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        // Drop all tables if the database version is upgraded
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS admins");
        MyDB.execSQL("DROP TABLE IF EXISTS drivers");
        MyDB.execSQL("DROP TABLE IF EXISTS Driver_Information");
        MyDB.execSQL("DROP TABLE IF EXISTS requests");
        MyDB.execSQL("DROP TABLE IF EXISTS reports");
        onCreate(MyDB);
    }

    // for the insert for User
    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


// for the Admin
public Boolean insertAdminData(String username, String password){
    SQLiteDatabase MyDB = this.getWritableDatabase();
    ContentValues contentValues= new ContentValues();
    contentValues.put("username", username);
    contentValues.put("password", password);
    long result = MyDB.insert("admins", null, contentValues);
    if(result==-1) return false;
    else
        return true;
}

    public Boolean checkusernameAdmin(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admins where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepasswordAdmin(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admins where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }


// for the driver
public Boolean insertDriverData(String username, String password){
    SQLiteDatabase MyDB = this.getWritableDatabase();
    ContentValues contentValues= new ContentValues();
    contentValues.put("username", username);
    contentValues.put("password", password);
    long result = MyDB.insert("drivers", null, contentValues);
    if(result==-1) return false;
    else
        return true;
}

    public Boolean checkusernamedriver(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from drivers where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean checkusernamepassworddriver(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from drivers where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
}
