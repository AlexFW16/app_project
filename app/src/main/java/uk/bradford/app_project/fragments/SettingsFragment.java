package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.bradford.app_project.MainActivity;
import uk.bradford.app_project.R;

public class SettingsFragment extends Fragment {


    private Button editEmailButton, editPasswordButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.settings, container, false);


        editEmailButton = rootView.findViewById(R.id.settings_change_email);
        editPasswordButton = rootView.findViewById(R.id.settings_change_password);


        editEmailButton.setOnClickListener(view -> changeEmail());
        editPasswordButton.setOnClickListener(view -> changePassword());

        // Firebase

        return rootView;
    }

    private void changeEmail() {
        //TODO

    }


    private void changePassword() {
// Create an AlertDialog TODO maybe change getActivity if not working
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Change Password");

// Set the layout for the dialog
        View view = getLayoutInflater().inflate(R.layout.change_password_dialog, null);
        builder.setView(view);

// Set up the input fields in the dialog
        EditText oldPasswordEditText = view.findViewById(R.id.oldPasswordEditText);
        EditText newPasswordEditText = view.findViewById(R.id.newPasswordEditText);

// Set up the buttons in the dialog
        builder.setPositiveButton("Change", (dialog, which) -> {
            String oldPassword = oldPasswordEditText.getText().toString();
            String newPassword = newPasswordEditText.getText().toString();

            // Validate and change password
            if (!oldPassword.isEmpty() && !newPassword.isEmpty()) {
                // Get the current user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // Example using the user's current password for re-authentication
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
                user.reauthenticate(credential).addOnCompleteListener(reAuthTask -> {
                    if (reAuthTask.isSuccessful()) {
                        // User successfully re-authenticated, proceed to change password
                        user.updatePassword(newPassword).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Password updated successfully
                                // You may want to inform the user or take additional actions
                            } else {
                                // Password update failed
                                // You can handle the error here
                            }
                        });
                    } else {
                        // Re-authentication failed, handle the error
                        Toast.makeText(getActivity(), "Wrong password, try again", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                // Handle empty fields
                Toast.makeText(getActivity(), "Please enter both old and new passwords", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // User clicked Cancel
        });

// Show the AlertDialog
        builder.create().show();

    }


}
