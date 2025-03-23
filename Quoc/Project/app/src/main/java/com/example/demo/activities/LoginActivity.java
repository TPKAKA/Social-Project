package com.example.demo.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.example.demo.db.DatabaseHandler;
import com.example.demo.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private ImageView googleBtn;
    private DatabaseHandler db;
    private SharedPreferences prefs;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginbtn);
        googleBtn = findViewById(R.id.google_btn);
        db = new DatabaseHandler(this);
        prefs = getSharedPreferences("SocialAppPrefs", MODE_PRIVATE);

        // Initialize Google Sign-In
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        // Check if user is already signed in
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            navigateToHomeActivity();
        }

        // Email login functionality
        loginButton.setOnClickListener(v -> login());

        // Google Sign-In functionality
        googleBtn.setOnClickListener(v -> signInWithGoogle());


        TextView createAccount = findViewById(R.id.forgotpass);
        createAccount.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, MainActivity.class)));
    }

    // Email/Password Login
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

    // Google Sign-In functionality
    private void signInWithGoogle() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToHomeActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Navigate to Home Activity after successful login
    private void navigateToHomeActivity() {
        finish();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
