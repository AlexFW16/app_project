package uk.bradford.app_project.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import uk.bradford.app_project.Cipher;

public abstract class CipherFragment extends Fragment {
    protected Button encryptBtn, decryptBtn, toggleBinaryButton;

    protected EditText keyEditText, msgEditText;
    protected TextView outputTextView;

    protected abstract uk.bradford.app_project.Cipher getCipher();


    @Override
    public void onPause() {
        super.onPause();
        //TODO do I have to override in my own superclass? Or will it just get passed through if I don't? Test!
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getCipher().toString() + "key", keyEditText.getText().toString());
        editor.putString(getCipher().toString() + "msg", msgEditText.getText().toString());
        editor.putString(getCipher().toString() + "out", outputTextView.getText().toString());
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String key = prefs.getString(getCipher().toString() + "key", "");
        String msg = prefs.getString(getCipher().toString() + "msg", "");
        String out = prefs.getString(getCipher().toString() + "out", "");

        keyEditText.setText(key);
        msgEditText.setText(msg);
        outputTextView.setText(out);
    }

}
