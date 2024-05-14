package com.example.binbuddy;



import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayRequestActivity extends AppCompatActivity {

    TextView requestInfoTextView;
    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_request);

        requestInfoTextView = findViewById(R.id.request_info_textview);
        dbHelper = new DBHelper(this);

        displayRequestInformation();
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
}

