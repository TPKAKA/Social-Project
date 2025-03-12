package com.example.demo.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.example.demo.db.DatabaseHandler;
import com.example.demo.models.User;


public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private DatabaseHandler db;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        db = new DatabaseHandler(this);
        prefs = getSharedPreferences("SocialAppPrefs", MODE_PRIVATE);

        loginButton.setOnClickListener(v -> login());

        TextView createAccount = findViewById(R.id.create_account);
        createAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
    }

    private void login() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = db.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("userId", user.getId());
            editor.apply();
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }
}