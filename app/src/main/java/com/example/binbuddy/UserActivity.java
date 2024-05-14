package com.example.binbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        CardView requestCard = findViewById(R.id.RequestCard);
        CardView reportCard = findViewById(R.id.reportCard);
        CardView exitCard = findViewById(R.id.exitCard);

        requestCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open RequestActivity
                startActivity(new Intent(UserActivity.this, RequestActivity.class));
            }
        });

        reportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open ReportActivity
                startActivity(new Intent(UserActivity.this, ReportActivity.class));
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
