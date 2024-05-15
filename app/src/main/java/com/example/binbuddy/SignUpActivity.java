package com.example.binbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    EditText username, password, repassword;
    Button signup;
    RadioGroup userTypeRadioGroup;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.btnsignup);
        userTypeRadioGroup = findViewById(R.id.user_type);
        DB = new DBHelper(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String repass = repassword.getText().toString().trim();
                String userType = ""; // Initialize user type

                // Get the selected user type
                int selectedId = userTypeRadioGroup.getCheckedRadioButtonId();
                if (selectedId == R.id.radio_admin) {
                    userType = "Admin";
                } else if (selectedId == R.id.radio_user) {
                    userType = "User";
                } else if (selectedId == R.id.radio_driver) {
                    userType = "Driver";
                }

                if(user.isEmpty() || pass.isEmpty() || repass.isEmpty())
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else {
                    if (pass.equals(repass)) {
                        Boolean checkuser = DB.checkusername(user);
                        if (!checkuser) {
                            Boolean insert = DB.insertData(user, pass, userType);
                            if (insert) {
                                Toast.makeText(SignUpActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                // Redirect to appropriate activity based on user type
                                Intent intent;
                                if (userType.equals("Admin")) {
                                    intent = new Intent(SignUpActivity.this, AdminActivity.class);
                                } else if (userType.equals("User")) {
                                    intent = new Intent(SignUpActivity.this, UserActivity.class);
                                } else { // Assuming Driver
                                    intent = new Intent(SignUpActivity.this, DriverActivity.class);
                                }
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "User already exists! Please sign in", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Passwords not matching", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}