package uk.bradford.app_project.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import uk.bradford.app_project.R;

public class SettingsFragment extends Fragment {


    private Button editEmailButton, editPasswordButton;

    private Switch darkModeSwitch;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.settings, container, false);


        editEmailButton = rootView.findViewById(R.id.settings_change_email);
        editPasswordButton = rootView.findViewById(R.id.settings_change_password);
        darkModeSwitch = rootView.findViewById(R.id.dark_mode_switch);


        editEmailButton.setOnClickListener(view -> changeEmail());
        editPasswordButton.setOnClickListener(view -> changePassword());
        darkModeSwitch.setOnCheckedChangeListener(((buttonView, isChecked) -> changeTheme(isChecked)));

        // Firebase

        return rootView;
    }

    private void changeTheme(boolean darkMode) {
        //TODO not working
        if (darkMode) {
            getActivity().setTheme(R.style.Base_Theme_App_project_Dark);
            getActivity().recreate();
            Log.e("theme", "dark");
        } else {

            Log.e("theme", "light");
            getActivity().setTheme(R.style.Base_Theme_App_project);
            getActivity().recreate();
        }


    }

    private void changeEmail() {
        //TODO do

    }


    private void changePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.change_pswd_title);

        // inflates the layout and sets the view
        View view = getLayoutInflater().inflate(R.layout.change_password_dialog, null);
        builder.setView(view);

        builder.setPositiveButton(R.string.change_pswd_accept_btn, (dialog, which) -> onPositiveButtonPasswordChangeDialog(dialog, which, view));
        // negative button is not set

        builder.create().show();
    }


    private void onPositiveButtonPasswordChangeDialog(DialogInterface dialog, int which, View view) {

        String oldPassword = ((EditText) view.findViewById(R.id.oldPasswordEditText)).getText().toString();
        String newPassword = ((EditText) view.findViewById(R.id.newPasswordEditText)).getText().toString();

        // Validate and change password
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(getActivity(), R.string.change_pswd_emtpy_input, Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current user
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Try to authenticate the user
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
        user.reauthenticate(credential).addOnCompleteListener(reAuthTask -> {

            //Authentication failed
            if (!reAuthTask.isSuccessful()) {
                Toast.makeText(getActivity(), R.string.change_pswd_wrong_pswd, Toast.LENGTH_SHORT).show();
                return;
            }
            // User successfully re-authenticated, proceed to change password
            user.updatePassword(newPassword).addOnCompleteListener(this::onUpdatePasswordComplete);

        });

    }

    private void onUpdatePasswordComplete(Task<Void> task) {
        if (task.isSuccessful())
            Toast.makeText(getActivity(), R.string.change_pswd_success, Toast.LENGTH_LONG).show();
        else Toast.makeText(getActivity(), R.string.change_pswd_update_error, Toast.LENGTH_LONG).show();


    }


}
