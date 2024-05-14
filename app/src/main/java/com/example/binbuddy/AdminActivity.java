package com.example.binbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        CardView driverCard = findViewById(R.id.card_driver);
        CardView requestCard = findViewById(R.id.card_request);
        CardView reportCard = findViewById(R.id.card_report);
        CardView exitCard = findViewById(R.id.card_exit);

        // Set click listeners for each CardView
        driverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AdminDriverActivity when the driver card is clicked
                Intent intent = new Intent(AdminActivity.this, AdminDriverActivity.class);
                startActivity(intent);
            }
        });

        requestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AssignWorkActivity when the request card is clicked
                Intent intent = new Intent(AdminActivity.this, AssignWorkActivity.class);
                startActivity(intent);
            }
        });

        reportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start DisplayReportActivity when the report card is clicked
                Intent intent = new Intent(AdminActivity.this, DisplayReportActivity.class);
                startActivity(intent);
            }
        });

        exitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Exit the application
                finishAffinity();
                System.exit(0);
            }
        });
    }
}
