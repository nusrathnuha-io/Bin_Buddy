package com.example.binbuddy;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;


public class AdminDriverActivity extends AppCompatActivity {

    TextInputEditText driverNameEditText, driverIdEditText, vehicleIdEditText, licenseNoEditText, phoneNumberEditText;
    Button createButton, readButton, deleteButton, updateButton;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admindriver);

        driverNameEditText = findViewById(R.id.driver_name);
        driverIdEditText = findViewById(R.id.driver_id);
        vehicleIdEditText = findViewById(R.id.vehicle_id);
        licenseNoEditText = findViewById(R.id.license_no);
        phoneNumberEditText = findViewById(R.id.phone_number);

        createButton = findViewById(R.id.create_button);
        readButton = findViewById(R.id.read_button);
        deleteButton = findViewById(R.id.delete_button);
        updateButton = findViewById(R.id.update_button);

        dbHelper = new DBHelper(this);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDriverInformation();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readDriverInformation();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDriverInformation();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDriverInformation();
            }
        });
    }

    private void addDriverInformation() {
        String driverName = driverNameEditText.getText().toString().trim();
        String driverId = driverIdEditText.getText().toString().trim();
        String vehicleId = vehicleIdEditText.getText().toString().trim();
        String licenseNo = licenseNoEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        if (driverName.isEmpty() || driverId.isEmpty() || vehicleId.isEmpty() || licenseNo.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("driver_name", driverName);
        values.put("driver_id", driverId);
        values.put("vehicle_id", vehicleId);
        values.put("license_no", licenseNo);
        values.put("phone_number", phoneNumber);

        long newRowId = db.insert("Driver_Information", null, values);
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding driver information", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Driver information added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void readDriverInformation() {
        // Implement the logic to navigate to a new page for reading driver information
        Intent intent = new Intent(AdminDriverActivity.this, DisplayDriverInformationActivity.class);
        startActivity(intent);
    }
    private void updateDriverInformation() {
        String driverId = driverIdEditText.getText().toString().trim();
        String driverName = driverNameEditText.getText().toString().trim();
        String vehicleId = vehicleIdEditText.getText().toString().trim();
        String licenseNo = licenseNoEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();

        if (driverId.isEmpty() || driverName.isEmpty() || vehicleId.isEmpty() || licenseNo.isEmpty() || phoneNumber.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("driver_name", driverName);
        values.put("vehicle_id", vehicleId);
        values.put("license_no", licenseNo);
        values.put("phone_number", phoneNumber);

        String selection = "driver_id = ?";
        String[] selectionArgs = {driverId};

        int count = db.update("Driver_Information", values, selection, selectionArgs);
        if (count == 0) {
            Toast.makeText(this, "No driver information found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Driver information updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteDriverInformation() {
        String driverId = driverIdEditText.getText().toString().trim();

        if (driverId.isEmpty()) {
            Toast.makeText(this, "Please enter the Driver ID", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "driver_id = ?";
        String[] selectionArgs = {driverId};

        int deletedRows = db.delete("Driver_Information", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No driver information found with the given ID", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Driver information deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }



}
