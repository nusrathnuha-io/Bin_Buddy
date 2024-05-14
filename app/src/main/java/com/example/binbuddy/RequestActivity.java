package com.example.binbuddy;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RequestActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, bagCountEditText, addressEditText, locationEditText;
    private Button addButton, deleteButton, updateButton, readButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        // Initialize views
        nameEditText = findViewById(R.id.User_name);
        phoneEditText = findViewById(R.id.phone);
        bagCountEditText = findViewById(R.id.count_id);
        addressEditText = findViewById(R.id.address_id);
        locationEditText = findViewById(R.id.location_id);
        addButton = findViewById(R.id.create_button);
        deleteButton = findViewById(R.id.delete_button);
        updateButton = findViewById(R.id.update_button);
        readButton = findViewById(R.id.read_button);

        // Initialize database helper
        dbHelper = new DBHelper(this);

        // Set OnClickListener for Add button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRequest();
            }
        });

       deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRequest();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readReuquest();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRequest();
            }
        });


    }

    // Method to add a new request to the database
    private void addRequest() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String bagCountStr = bagCountEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || bagCountStr.isEmpty() || address.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int bagCount = Integer.parseInt(bagCountStr);

        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store data
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone_number", phone);
        values.put("bag_count", bagCount);
        values.put("address", address);
        values.put("location", location);


        // Insert data into the "requests" table
        long newRowId = db.insert("requests", null, values);

        // Check if the insertion was successful
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding request information", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Request information added successfully", Toast.LENGTH_SHORT).show();
        }

        // Close the database connection
        db.close();
    }

    private void readReuquest() {
        // Implement the logic to navigate to a new page for reading driver information
        Intent intent = new Intent(RequestActivity.this, DisplayRequestActivity.class);
        startActivity(intent);
    }
    private void deleteRequest() {
        String name = nameEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the name of the request to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "name = ?";
        String[] selectionArgs = {name};

        int deletedRows = db.delete("requests", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No request found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Request deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }



    private void updateRequest() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String bagCountStr = bagCountEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the name of the request to update", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.isEmpty() || bagCountStr.isEmpty() || address.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int bagCount = Integer.parseInt(bagCountStr);



        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("phone_number", phone);
        values.put("bag_count", bagCount);
        values.put("address", address);
        values.put("location", location);


        String selection = "name = ?";
        String[] selectionArgs = {name};

        int updatedRows = db.update("requests", values, selection, selectionArgs);
        if (updatedRows == 0) {
            Toast.makeText(this, "No request found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Request information updated successfully", Toast.LENGTH_SHORT).show();
        }
    }

}
