package uk.bradford.app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button registerButton, backToLoginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.registration_email_input);
        passwordEditText = findViewById(R.id.registration_password_input);
        registerButton = findViewById(R.id.registration_register_button);
        backToLoginButton = findViewById(R.id.registration_back_button);

        registerButton.setOnClickListener(this::onRegisterButtonClick);
        backToLoginButton.setOnClickListener(this::onClickBackButton);
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

    private void onRegisterButtonClick(View v) {

        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            //debug ??
            Log.w("Register", "Email or password is empty");
            //TODO add working Toast message
            Toast.makeText(this, "Email or password cannot be emtpy", Toast.LENGTH_LONG);
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this::onCompleteFirebaseRegister);

    }

    private void onCompleteFirebaseRegister(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d("FirebaseAuth", "createUserWithEmail:success");
            Toast.makeText(RegistrationActivity.this, "Account creation successufl", Toast.LENGTH_LONG).show();


            //FirebaseUser user = mAuth.getCurrentUser();
            //updateUI(user);
        } else {
            // If sign in fails, display a message to the user.
            Log.w("FierbaseAuth", "createUserWithEmail:failure", task.getException());
            Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                    Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
    }

    private void updateUI(FirebaseUser user){
        if(user == null){
            // Login failed
            //TODO
        }
        else { // Login successful
            Toast.makeText(RegistrationActivity.this, "You are logged in!",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }

    }   private void onClickBackButton(View v){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
