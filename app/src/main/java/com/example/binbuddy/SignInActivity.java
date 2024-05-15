package com.example.binbuddy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    EditText usernameInput, passwordInput;
    Button signInButton, signUpButton;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        usernameInput = findViewById(R.id.editTextUsername);
        passwordInput = findViewById(R.id.editTextPassword);
        signInButton = findViewById(R.id.buttonSignIn);
        signUpButton = findViewById(R.id.btnsignup);
        DB = new DBHelper(this);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = usernameInput.getText().toString().trim();
                String pass = passwordInput.getText().toString().trim();

                if (user.isEmpty() || pass.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform login authentication using DBHelper
                    String userType = DB.checkusernamepassword(user, pass);

                    if (userType != null) {
                        // Login successful, check user type and direct to respective activity
                        if (userType.equals(DBHelper.USER_TYPE_ADMIN)) {
                            // Admin, direct to AdminActivity
                            Toast.makeText(SignInActivity.this, "Login successful ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, AdminActivity.class);
                            intent.putExtra("USER_NAME", user);
                            startActivity(intent);
                        } else if (userType.equals(DBHelper.USER_TYPE_DRIVER)) {
                            // Driver, direct to DriverActivity
                            Toast.makeText(SignInActivity.this, "Login successful ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, DriverActivity.class);
                            intent.putExtra("USER_NAME", user);
                            startActivity(intent);
                        } else if (userType.equals(DBHelper.USER_TYPE_USER)) {
                            // User, direct to UserActivity
                            Toast.makeText(SignInActivity.this, "Login successful ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInActivity.this, UserActivity.class);
                            intent.putExtra("USER_NAME", user);
                            startActivity(intent);
                        }
                    } else {
                        // Invalid credentials
                        Toast.makeText(SignInActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the sign-up activity
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
