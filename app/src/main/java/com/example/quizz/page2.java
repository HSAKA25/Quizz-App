package com.example.quizz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class page2 extends AppCompatActivity {

    private EditText t1, t2;
    private Button b3;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_page2);

        // Initialize views
        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        b3 = findViewById(R.id.b3);
        mAuth = FirebaseAuth.getInstance();

        // Register button click listener
        b3.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String userEmail = t1.getText().toString().trim();
        String userPass = t2.getText().toString().trim();

        // Input validation
        if (userEmail.isEmpty() || userPass.isEmpty()) {
            Toast.makeText(this, "Email or Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (userPass.length() < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button while processing
        b3.setEnabled(false);
        b3.setText("Creating Account...");

        // Firebase Authentication
        mAuth.createUserWithEmailAndPassword(userEmail, userPass)
                .addOnCompleteListener(task -> {
                    // Re-enable button
                    b3.setEnabled(true);
                    b3.setText("Create Account");

                    if (task.isSuccessful()) {
                        Toast.makeText(this, "✅ Registered Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), page3.class));
                        finish();
                    } else {
                        Toast.makeText(this, "❌ Registration Failed: " +
                                task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
