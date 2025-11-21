package com.example.quizz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    EditText t1, t2;
    Button b1, b2, b4;
    FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;

    private static final String PREF_NAME = "UserPrefs";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialization
        b1 = findViewById(R.id.b1);  // Login
        b2 = findViewById(R.id.b2);  // Register
        b4 = findViewById(R.id.b4);  // Forgot Password
        t1 = findViewById(R.id.t1);  // Email
        t2 = findViewById(R.id.t2);  // Password
        tv1 = findViewById(R.id.tv1);  // Welcome
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load saved email & password
        loadUserData();

        // Login button click
        b1.setOnClickListener(v -> loginUser());

        // Register button click
        b2.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), page2.class);
            startActivity(intent);
        });

        // Forgot password button click
        b4.setOnClickListener(v -> {
            String eml = t1.getText().toString().trim();
            if (!eml.isEmpty()) {
                mAuth.sendPasswordResetEmail(eml)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Reset link sent to your email", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void loginUser() {
        String userEmail = t1.getText().toString().trim();
        String userPass = t2.getText().toString().trim();

        if (userEmail.isEmpty() || userPass.isEmpty()) {
            Toast.makeText(this, "Email or Password can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                        // Save email & password for future
                        saveUserData(userEmail, userPass);

                        Intent intent = new Intent(getApplicationContext(), page3.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Login Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    void saveUserData(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }

    void loadUserData() {
        String savedEmail = sharedPreferences.getString(KEY_EMAIL, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        if (!savedEmail.isEmpty()) {
            t1.setText(savedEmail);
        }
        if (!savedPassword.isEmpty()) {
            t2.setText(savedPassword);
        }
    }
}
