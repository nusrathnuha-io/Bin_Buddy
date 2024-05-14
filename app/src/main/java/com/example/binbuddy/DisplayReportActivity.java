package com.example.binbuddy;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayReportActivity extends AppCompatActivity {

    TextView reportInfoTextView;
    DBHelper dbHelper;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_report);

        reportInfoTextView = findViewById(R.id.report_info_textview);
        dbHelper = new DBHelper(this);

        displayReportInformation();
    }

    private void displayReportInformation() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "report_id",  // Change "request_id" to "report_id"
                "name",
                "phone_number",
                "address",
                "complain"
        };
        Cursor cursor = db.query(
                "reports",
                projection,
                null,
                null,
                null,
                null,
                null
        );

        StringBuilder builder = new StringBuilder();
        while (cursor.moveToNext()) {
            int reportId = cursor.getInt(cursor.getColumnIndexOrThrow("report_id"));  // Change "request_id" to "report_id"
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow("phone_number"));
            String address = cursor.getString(cursor.getColumnIndexOrThrow("address"));
            String complain = cursor.getString(cursor.getColumnIndexOrThrow("complain"));

            builder.append("Report ID: ").append(reportId).append("\n")
                    .append("Name: ").append(name).append("\n")
                    .append("Phone Number: ").append(phoneNumber).append("\n")
                    .append("Address: ").append(address).append("\n")
                    .append("Complain: ").append(complain).append("\n\n");
        }
        cursor.close();

        if (builder.length() > 0) {
            reportInfoTextView.setText(builder.toString());
        } else {
            reportInfoTextView.setText("No report information found");
        }
    }
}
