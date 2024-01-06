package uk.bradford.app_project.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.R;

public class VigenereFragment extends CipherFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.vigenere, container, false);

        encryptBtn = rootView.findViewById(R.id.encrypt);
        decryptBtn = rootView.findViewById(R.id.decrypt);
        keyEditText = rootView.findViewById(R.id.key);
        msgEditText = rootView.findViewById(R.id.msg);
        outputTextView = rootView.findViewById(R.id.out);

        // Set the listeners

        encryptBtn.setOnClickListener(this::onEncryptButtonClick);
        decryptBtn.setOnClickListener(this::onDecryptButtonClick);
        //keyEditText.setOnTouchListener(this::onKeyEditTextTouch);

        return rootView;
    }

    @Override
    protected Cipher getCipher() {
        return Cipher.VIGENERE;
    }

    private void onEncryptButtonClick(View v) {

        try {
            String encryptedText = Crypto.encrypt(Cipher.VIGENERE, msgEditText.getText().toString().toUpperCase(), keyEditText.getText().toString().toUpperCase());
            outputTextView.setText(encryptedText);
        } catch (IllegalArgumentException e) {

            printErrorMessage(R.string.input_error_vigenere);

            Toast.makeText(this.getActivity().getApplicationContext(), "Message or Key contains letters different \nto \"a, b, c, ...\"", Toast.LENGTH_LONG);
            Log.e("IllegalArgumentException", e.getMessage());
        }


    }

    private void onDecryptButtonClick(View v) {

        try {
            String encryptedText = Crypto.decrypt(Cipher.VIGENERE, msgEditText.getText().toString().toUpperCase(), keyEditText.getText().toString().toUpperCase());
            outputTextView.setText(encryptedText);
        } catch (IllegalArgumentException e) {
            printErrorMessage(R.string.input_error_vigenere);
            Toast.makeText(getContext(), "Message or Key contains letters different to \"a, b, c, ...\",", Toast.LENGTH_LONG);
            Log.e("IllegalArgumentException", e.getMessage());
        }
    }


}


