package com.example.demo.activities;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.example.demo.db.DatabaseHandler;
import com.example.demo.models.User;


public class ModifyProfileActivity extends AppCompatActivity {
    private EditText firstNameEditText, lastNameEditText, emailEditText;
    private Button submitButton;
    private DatabaseHandler db;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_profile);

        firstNameEditText = findViewById(R.id.firstname);
        lastNameEditText = findViewById(R.id.lastname);
        emailEditText = findViewById(R.id.email);
        submitButton = findViewById(R.id.submitButton);
        db = new DatabaseHandler(this);
        prefs = getSharedPreferences("SocialAppPrefs", MODE_PRIVATE);

        int userId = prefs.getInt("userId", -1);
        if (userId != -1) {
            User user = db.getUserById(userId);
            firstNameEditText.setText(user.getFirstName());
            lastNameEditText.setText(user.getLastName());
            emailEditText.setText(user.getEmail());
        }

        submitButton.setOnClickListener(v -> updateProfile());
    }

    private void updateProfile() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        int userId = prefs.getInt("userId", -1);
        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        User existingUser = db.getUserByEmail(email);
        if (existingUser != null && existingUser.getId() != userId) {
            Toast.makeText(this, "Email already in use", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = db.getUserById(userId);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        db.updateUser(user);
        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}