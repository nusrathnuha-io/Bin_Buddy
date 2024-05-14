package com.example.binbuddy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class DisplayDriverInformationActivity extends AppCompatActivity {

    TextView driverInfoTextView;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_driver_information);

        driverInfoTextView = findViewById(R.id.driver_info_textview);
        dbHelper = new DBHelper(this);

        displayDriverInformation();
    }

    private void displayDriverInformation() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "driver_id",
                "driver_name",
                "vehicle_id",
                "license_no",
                "phone_number"
        };
        Cursor cursor = db.query(
                "Driver_Information",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            String driverId = cursor.getString(cursor.getColumnIndexOrThrow("driver_id"));
            String driverName = cursor.getString(cursor.getColumnIndexOrThrow("driver_name"));
            String vehicleId = cursor.getString(cursor.getColumnIndexOrThrow("vehicle_id"));
            String licenseNo = cursor.getString(cursor.getColumnIndexOrThrow("license_no"));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));

            builder.append("Driver ID: ").append(driverId).append("\n")
                    .append("Driver Name: ").append(driverName).append("\n")
                    .append("Vehicle ID: ").append(vehicleId).append("\n")
                    .append("License No: ").append(licenseNo).append("\n")
                    .append("Phone Number: ").append(phoneNumber).append("\n\n");
        }
        cursor.close();

        if (builder.length() > 0) {
            driverInfoTextView.setText(builder.toString());
        } else {
            driverInfoTextView.setText("No driver information found");
        }
    }
}
