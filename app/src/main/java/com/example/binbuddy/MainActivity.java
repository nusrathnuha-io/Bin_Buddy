package com.example.binbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdmin = findViewById(R.id.btnAdmin);
        Button btnUser = findViewById(R.id.btnUser);
        Button btnDriver = findViewById(R.id.btnDriver);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start AdminSignInActivity
                startActivity(new Intent(MainActivity.this, AdminSignInActivity.class));
            }
        });

        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start UserSignInActivity
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });

        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start DriverSignInActivity
                startActivity(new Intent(MainActivity.this, DriverSigninActivity.class));
            }
        });
    }
}
