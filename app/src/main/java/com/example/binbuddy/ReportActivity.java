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

public class ReportActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText, complainEditText;
    private Button addButton, deleteButton, updateButton, readButton;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        // Initialize views
        nameEditText = findViewById(R.id.User_name);
        phoneEditText = findViewById(R.id.phone);
        addressEditText = findViewById(R.id.address_id);
        complainEditText = findViewById(R.id.complain_id);
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
                addReport();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteReport();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readReports();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateReport();
            }
        });
    }

    // Method to add a new report to the database
    private void addReport() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String complain = complainEditText.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || address.isEmpty() || complain.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get a writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store data
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("phone_number", phone);
        values.put("address", address);
        values.put("complain", complain);

        // Insert data into the "reports" table
        long newRowId = db.insert("reports", null, values);

        // Check if the insertion was successful
        if (newRowId == -1) {
            Toast.makeText(this, "Error adding report information", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Report information added successfully", Toast.LENGTH_SHORT).show();
        }

        // Close the database connection
        db.close();
    }

    private void readReports() {
        // Implement the logic to navigate to a new page for reading report information
        Intent intent = new Intent(ReportActivity.this, DisplayReportActivity.class);
        startActivity(intent);
    }

    private void deleteReport() {
        String name = nameEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the name of the report to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "name = ?";
        String[] selectionArgs = {name};

        int deletedRows = db.delete("reports", selection, selectionArgs);
        if (deletedRows == 0) {
            Toast.makeText(this, "No report found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Report deleted successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateReport() {
        String name = nameEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();
        String complain = complainEditText.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter the name of the report to update", Toast.LENGTH_SHORT).show();
            return;
        }

        if (phone.isEmpty() || address.isEmpty() || complain.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("phone_number", phone);
        values.put("address", address);
        values.put("complain", complain);

        String selection = "name = ?";
        String[] selectionArgs = {name};

        int updatedRows = db.update("reports", values, selection, selectionArgs);
        if (updatedRows == 0) {
            Toast.makeText(this, "No report found with the given name", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Report information updated successfully", Toast.LENGTH_SHORT).show();
        }
    }
}
