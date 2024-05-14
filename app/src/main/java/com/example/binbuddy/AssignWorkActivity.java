package com.example.binbuddy;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AssignWorkActivity extends AppCompatActivity {

    TextView requestInfoTextView;
    EditText driverIdEditText,requestIDEditText;
    Button assignButton;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignwork);

        requestInfoTextView = findViewById(R.id.request_info_textview);
        driverIdEditText = findViewById(R.id.driver_id_edittext);
        requestIDEditText = findViewById(R.id.request_id_edittext);
        assignButton = findViewById(R.id.assign_button);
        dbHelper = new DBHelper(this);

        displayRequestInformation();

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignWorkToDriver();
            }
        });
    }

    private void displayRequestInformation() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "request_id",
                "name",
                "phone_number",
                "bag_count",
                "address",
                "location"
        };
        Cursor cursor = db.query(
                "requests",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            int requestId = cursor.getInt(cursor.getColumnIndexOrThrow("request_id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
            int bagCount = cursor.getInt(cursor.getColumnIndexOrThrow("bag_count"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

            builder.append("Request ID: ").append(requestId).append("\n")
                    .append("Name: ").append(name).append("\n")
                    .append("Phone Number: ").append(phoneNumber).append("\n")
                    .append("Bag Count: ").append(bagCount).append("\n")
                    .append("Address: ").append(address).append("\n")
                    .append("Location: ").append(location).append("\n\n");
        }
        cursor.close();

        if (builder.length() > 0) {
            requestInfoTextView.setText(builder.toString());
        } else {
            requestInfoTextView.setText("No request information found");
        }
    }

    private void assignWorkToDriver() {
        String driverId = driverIdEditText.getText().toString().trim();
        if (driverId.isEmpty()) {
            Toast.makeText(this, "Please enter the Driver ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the request ID from the user input
        String requestIdStr = requestIDEditText.getText().toString().trim();
        if (requestIdStr.isEmpty()) {
            Toast.makeText(this, "Please enter the Request ID", Toast.LENGTH_SHORT).show();
            return;
        }

        int requestId = Integer.parseInt(requestIdStr);

        // Get a readable database to check if the driver ID exists
        SQLiteDatabase dbRead = dbHelper.getReadableDatabase();

        // Define the selection and selection arguments to check if the driver ID exists
        String driverSelection = "driver_id=?";
        String[] driverSelectionArgs = {driverId};

        // Query the "Driver_Information" table to check if the driver ID exists
        Cursor driverCursor = dbRead.query(
                "Driver_Information",
                null,
                driverSelection,
                driverSelectionArgs,
                null,
                null,
                null
        );

        // Check if the driver ID exists
        if (driverCursor.getCount() == 0) {
            Toast.makeText(this, "Driver ID does not exist", Toast.LENGTH_SHORT).show();
            driverCursor.close();
            dbRead.close();
            return;
        }

        driverCursor.close();
        dbRead.close();

        // Get a writable database
        SQLiteDatabase dbWrite = dbHelper.getWritableDatabase();

        // Update the "requests" table to assign work to the driver
        ContentValues values = new ContentValues();
        values.put("driver_id", driverId);

        // Define the selection and selection arguments
        String requestSelection = "request_id=?";
        String[] requestSelectionArgs = {String.valueOf(requestId)};

        // Update the "requests" table
        int updatedRows = dbWrite.update("requests", values, requestSelection, requestSelectionArgs);

        // Check if the update was successful
        if (updatedRows > 0) {
            Toast.makeText(this, "Work assigned to driver successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to assign work to driver", Toast.LENGTH_SHORT).show();
        }

        // Close the database connection
        dbWrite.close();
    }


}
