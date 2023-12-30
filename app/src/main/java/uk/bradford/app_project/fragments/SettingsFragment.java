package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;

import uk.bradford.app_project.R;

public class SettingsFragment extends Fragment {

    private FirebaseUser user;

    private EditText email, password;
    public SettingsFragment(FirebaseUser user) {

        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.settings, container, false);


        email = rootView.findViewById(R.id.settings_user_email);
        password = rootView.findViewById(R.id.settings_user_password);

        email.setText(user.getEmail().toString());



        return rootView;
    }

}
