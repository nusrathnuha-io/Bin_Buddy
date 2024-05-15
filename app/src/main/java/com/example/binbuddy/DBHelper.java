package com.example.binbuddy;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "Sytem_BinBuddy.db";
    private static final int DB_VERSION = 1;

    // Table and column names
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USER_TYPE = "user_type";

    // User types
    public static final String USER_TYPE_ADMIN = "Admin";
    public static final String USER_TYPE_DRIVER = "Driver";
    public static final String USER_TYPE_USER = "User";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for users
        db.execSQL("CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_USER_TYPE + " TEXT)");

        // Create table for driver information
        db.execSQL("CREATE TABLE Driver_Information (" +
                "driver_name TEXT," +
                "driver_id TEXT PRIMARY KEY," +
                "vehicle_id TEXT," +
                "license_no TEXT UNIQUE," +
                "phone_number TEXT)");

        // Create table for requests
        db.execSQL("CREATE TABLE requests (" +
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
        db.execSQL("CREATE TABLE reports (" +
                "report_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "phone_number TEXT," +
                "address TEXT," +
                "complain TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        // Drop all tables if the database version is upgraded
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        MyDB.execSQL("DROP TABLE IF EXISTS Driver_Information");
        MyDB.execSQL("DROP TABLE IF EXISTS requests");
        MyDB.execSQL("DROP TABLE IF EXISTS reports");
        onCreate(MyDB);
    }

    // for the insert for User

    public Boolean insertData(String username, String password, String userType) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        contentValues.put(COLUMN_USER_TYPE, userType); // Add user type
        long result = MyDB.insert(TABLE_USERS, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public Boolean checkusername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else return false;
    }

    public String checkusernamepassword(String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String userType = cursor.getString(cursor.getColumnIndex(COLUMN_USER_TYPE));
            if (userType.equals(USER_TYPE_ADMIN)) {
                return USER_TYPE_ADMIN; // Admin
            } else if (userType.equals(USER_TYPE_DRIVER)) {
                return USER_TYPE_DRIVER; // Driver
            } else if (userType.equals(USER_TYPE_USER)) {
                return USER_TYPE_USER; // User
            }
        }
        cursor.close();
        return null; // Default to null if user not found or type not recognized
    }

}
