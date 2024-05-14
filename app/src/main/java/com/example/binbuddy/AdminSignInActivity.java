package com.example.binbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminSignInActivity extends AppCompatActivity {
    EditText username, password;
    Button Btnsignin, BtnsignUp;
    DBHelper DB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminsignin);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        Btnsignin = findViewById(R.id.buttonSignIn);
        BtnsignUp = findViewById(R.id.btnsignup);
        DB = new DBHelper(this);

        Btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals(""))
                    Toast.makeText(AdminSignInActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    Boolean checkuserpass = DB.checkusernamepasswordAdmin(user, pass);
                    if (checkuserpass) {
                        Toast.makeText(AdminSignInActivity.this, "Sign in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(AdminSignInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        BtnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the sign-up activity
                Intent intent = new Intent(AdminSignInActivity.this, AdminSignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
