package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.R;

public class SubstitutionFragment extends CipherFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.substitution, container, false);

        encryptBtn = rootView.findViewById(R.id.encrypt);
        decryptBtn = rootView.findViewById(R.id.decrypt);
        keyEditText = rootView.findViewById(R.id.key);
        msgEditText = rootView.findViewById(R.id.msg);
        outputTextView = rootView.findViewById(R.id.out);

        // Set the listeners

        encryptBtn.setOnClickListener(this::onEncryptButtonClick);
        decryptBtn.setOnClickListener(this::onDecryptButtonClick);
        //keyEditText.setOnClickListener(this::onKeyEditTextClick);
        //keyEditText.setOnTouchListener(this::onKeyEditTextTouch);
        msgEditText.setOnClickListener(this::onMsgEditTextClick);

        return rootView;
    }

    @Override
    protected Cipher getCipher() {
        return Cipher.SUBSTITUTION;
    }

    //TODO implement
    private void onEncryptButtonClick(View v) {

        try {
            String encryptedText = Crypto.encrypt(Cipher.SUBSTITUTION, msgEditText.getText().toString(), keyEditText.getText().toString());
            outputTextView.setText(encryptedText);
        } catch (IllegalArgumentException e) {
            printErrorMessage(R.string.input_error_substitution);
            //Toast.makeText(getContext(), "Badly formatted input, see help", Toast.LENGTH_LONG);
            Log.e("IllegalArgumentException", e.getMessage());
        }

    }

    private void onDecryptButtonClick(View v) {
        try {
            String decryptedText = Crypto.decrypt(Cipher.SUBSTITUTION, msgEditText.getText().toString(), keyEditText.getText().toString());
            outputTextView.setText(decryptedText);
        } catch (IllegalArgumentException e) {

            printErrorMessage(R.string.input_error_substitution);
            //Toast.makeText(getContext(), "Badly formatted input, see help", Toast.LENGTH_LONG);
            Log.e("IllegalArgumentException", e.getMessage());

        }

    }

    //private void onKeyEditTextClick(View v) {}


    //public boolean onKeyEditTextTouch(View v, MotionEvent e) { }// could be used later to let default text disappear

    private void onMsgEditTextClick(View v) {

    }
}
