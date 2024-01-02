package uk.bradford.app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.bradford.app_project.model.User;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;

    private FirebaseAuth mAuth;

    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        emailEditText = findViewById(R.id.login_email_input);
        passwordEditText = findViewById(R.id.login_password_input);
        loginButton = findViewById(R.id.login_login_button);
        registerButton = findViewById(R.id.login_register_button);

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
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        login(email, password);

    }

    private void onRegisterButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
        finish();
    }


    // Login is always called before a user enters the main app -> ensure the right user data is there
    private void login(FirebaseUser user) {
        updateUI(user);
        if (user != null) {
            updateUI(user); // updates UI as if user had just signed in
        }

        //TODO restore user data to prefs

        //restoreUserData(mAuth.getCurrentUser());
    }

    private void login(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            //debug ??
            Log.w("Register", "Email or password is empty");
            Toast.makeText(LoginActivity.this, "Email or password cannot be emtpy", Toast.LENGTH_LONG);
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this::onCompleteFirebaseLogin);

        //TODO restore user data to prefs
        //restoreUserDataToPrefs(mAuth.getCurrentUser());
    }

    private void onCompleteFirebaseLogin(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d("FirebaseAuth", "signInWithEmail:success");
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        } else {
            // If sign in fails, display a message to the user.
            Log.w("FirebaseAuth", "signInWithEmail:failure", task.getException());
            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
            updateUI(null);
        }

    }

    private void updateUI(FirebaseUser user) {
        if (user == null) {
            // Login failed
            //TODO
        } else { // Login successful
            Toast.makeText(LoginActivity.this, "You are logged in!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

}