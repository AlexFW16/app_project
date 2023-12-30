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

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button loginButton, registerButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            updateUI(currentUser); // updates UI as if user had just signed in
        }
    }

    private void onLoginButtonClick(View v) {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            //debug ??
            Log.w("Register", "Email or password is empty");
            //TODO add working Toast message
            Toast.makeText(LoginActivity.this, "Email or password cannot be emtpy", Toast.LENGTH_LONG);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this::onCompleteFirebaseLogin);



    }
    private void onCompleteFirebaseLogin(@NonNull Task<AuthResult> task){
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d("FirebaseAuth", "signInWithEmail:success");
            FirebaseUser user = mAuth.getCurrentUser();
            updateUI(user);
        } else {
            // If sign in fails, display a message to the user.
            Log.w("FirebaseAuth", "signInWithEmail:failure", task.getException());
            Toast.makeText(LoginActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
           updateUI(null);
        }

    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            // Login failed
            //TODO
        }
        else { // Login successful
            Toast.makeText(LoginActivity.this, "You are logged in!",
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

    }


    private void onRegisterButtonClick(View v) {
        Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(intent);
        finish();
    }
}