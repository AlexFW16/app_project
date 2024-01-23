package uk.bradford.app_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.registration_email_input);
        passwordEditText = findViewById(R.id.registration_password_input);
        Button registerButton = findViewById(R.id.registration_register_button);
        Button backToLoginButton = findViewById(R.id.registration_back_button);

        registerButton.setOnClickListener(this::onRegisterButtonClick);
        backToLoginButton.setOnClickListener(this::onClickBackButton);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            updateUI(currentUser); // updates UI as if user had just signed in
        }
    }

    private void onRegisterButtonClick(View v) {

        String email = Objects.requireNonNull(emailEditText.getText()).toString();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            //debug ??
            Log.w(getClass().getName(), "Email or password is empty");
            Toast.makeText(this, "Email or password cannot be emtpy", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this::onCompleteFirebaseRegister);

    }

    private void onCompleteFirebaseRegister(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Toast.makeText(this, "Account creation successful", Toast.LENGTH_LONG).show();

        } else {
            // If sign in fails, display a message to the user.
            Log.w(getClass().getName(), "createUserWithEmail failed", task.getException());
            Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(getApplicationContext(), R.string.login_failed, Toast.LENGTH_LONG).show();
            Log.e(getClass().getName(), "Login failed because user==null");

        } else { // Login successful
            Toast.makeText(RegistrationActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void onClickBackButton(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
