package uk.bradford.app_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Enforces light mode, bc dark mode does not work yet
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.login_email_input);
        passwordEditText = findViewById(R.id.login_password_input);
        Button loginButton = findViewById(R.id.login_login_button);
        Button registerButton = findViewById(R.id.login_register_button);

        loginButton.setOnClickListener(this::onLoginButtonClick);
        registerButton.setOnClickListener(this::onRegisterButtonClick);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        login(mAuth.getCurrentUser());
    }

    private void onLoginButtonClick(View v) {
        String email = Objects.requireNonNull(emailEditText.getText()).toString();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();
        login(email, password);

    }

    private void onRegisterButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
        finish();
    }


    private void login(FirebaseUser user) {
        if (user != null) {
            updateUI(user); // updates UI as if user had just signed in
        }
    }

    private void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            Log.w("Login", "Email or password is empty");
            Toast.makeText(getApplicationContext(), R.string.login_empty_field, Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this::onCompleteFirebaseLogin);
    }

    private void onCompleteFirebaseLogin(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()) { // Login success, update UI with the signed-in user's information
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);

        } else { // Login failed
            Log.w(getClass().getName(), "signInWithEmail failed", task.getException());
            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            updateUI(null);
        }

    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
            Log.e(getClass().getName(), "Login failed because user==null");

        } else { // Login successful
            Toast.makeText(getApplicationContext(), "You are logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

    }

}