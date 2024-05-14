package com.example.binbuddy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DriverActivity extends AppCompatActivity {

    TextView assignedWorkTextView;
    EditText searchEditText,requestIDEditText;

    DBHelper dbHelper;
    Button completeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        assignedWorkTextView = findViewById(R.id.assigned_work_textview);
        searchEditText = findViewById(R.id.searchEditText);
        completeButton = findViewById(R.id.button);
        requestIDEditText=findViewById(R.id.request_id_edittext);
        dbHelper = new DBHelper(this);

        // Set up the search action for the EditText
        searchEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                    String driverId = searchEditText.getText().toString().trim();
                    if (!driverId.isEmpty()) {
                        filterAssignedWork(driverId);
                        return true;
                    } else {
                        Toast.makeText(DriverActivity.this, "Please enter a Driver ID", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        // Set up click listener for the Complete button
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the request ID from wherever you store it (e.g., an EditText)
                String requestIdStr = requestIDEditText.getText().toString().trim();
                if (!requestIdStr.isEmpty()) {
                    int requestId = Integer.parseInt(requestIdStr);
                    completeWork(requestId);
                } else {
                    Toast.makeText(DriverActivity.this, "Please enter the Request ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void filterAssignedWork(String driverId) {
        // Get a readable database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define the columns to retrieve
        String[] projection = {
                "request_id",
                "name",
                "phone_number",
                "bag_count",
                "address",
                "location"
        };

        // Define the selection criteria
        String selection = "driver_id=?";
        String[] selectionArgs = {driverId};

        // Query the "requests" table for assigned work for the driver
        Cursor cursor = db.query(
                "requests",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // Process the cursor to display assigned work
        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            int requestId = cursor.getInt(cursor.getColumnIndexOrThrow("request_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
            int bagCount = cursor.getInt(cursor.getColumnIndexOrThrow("bag_count"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

            // Append assigned work information to the StringBuilder
            builder.append("Request ID: ").append(requestId).append("\n")
                    .append("Name: ").append(name).append("\n")
                    .append("Phone Number: ").append(phoneNumber).append("\n")
                    .append("Bag Count: ").append(bagCount).append("\n")
                    .append("Address: ").append(address).append("\n")
                    .append("Location: ").append(location).append("\n\n");
        }
        cursor.close();

        // Display assigned work information in the TextView
        if (builder.length() > 0) {
            assignedWorkTextView.setText(builder.toString());
        } else {
            // If no assigned work found, display a message
            Toast.makeText(this, "No assigned work found for the driver", Toast.LENGTH_SHORT).show();
        }

        // Close the database connection
        db.close();
    }

    private void completeWork(int requestId) {
        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Update the status of the request to "Completed"
        ContentValues values = new ContentValues();
        values.put("status", "Completed");

        // Define the selection and selection arguments
        String selection = "request_id=?";
        String[] selectionArgs = {String.valueOf(requestId)};

        // Update the "requests" table
        int rowsAffected = db.update("requests", values, selection, selectionArgs);

        if (rowsAffected > 0) {
            Toast.makeText(this, "Work completed successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to complete work", Toast.LENGTH_SHORT).show();
        }

        // Close the database connection
        db.close();
    }

}
