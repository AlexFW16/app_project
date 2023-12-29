package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.R;
import uk.bradford.app_project.Util;

public class XORFragment extends Fragment {
    private Button encryptBtn;
    private Button decryptBtn;

    private Button toggleBinaryButton;
    private EditText keyEditText;
    private EditText msgEditText;

    private TextView outputTextView;

    private boolean isBinaryOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.xor, container, false);

        encryptBtn = rootView.findViewById(R.id.xor_encrypt);
        decryptBtn = rootView.findViewById(R.id.xor_decrypt);
        toggleBinaryButton = rootView.findViewById(R.id.xor_toggle_binary);
        keyEditText = rootView.findViewById(R.id.xor_key);
        msgEditText = rootView.findViewById(R.id.xor_msg);
        outputTextView = rootView.findViewById(R.id.xor_out);

        isBinaryOut = false;

        // Set the listeners

        encryptBtn.setOnClickListener(this::onEncryptButtonClick);
        decryptBtn.setOnClickListener(this::onDecryptButtonClick);
        toggleBinaryButton.setOnClickListener(this::onToggleBinaryButtonClick);
        keyEditText.setOnClickListener(this::onKeyEditTextClick);
        //keyEditText.setOnTouchListener(this::onKeyEditTextTouch);
        msgEditText.setOnClickListener(this::onMsgEditTextClick);

        return rootView;
    }


    //TODO implement
    private void onEncryptButtonClick(View v) {
        try {
            String encryptedText = Crypto.encrypt(Cipher.XOR, msgEditText.getText().toString(), keyEditText.getText().toString());
            outputTextView.setText(encryptedText);
        } catch (IllegalArgumentException e) {
            // TODO Toast
            Log.e("IllegalArgumentException", e.getMessage());
        }
    }

    private void onDecryptButtonClick(View v) {
        try {
            String decryptedText = Crypto.decrypt(Cipher.XOR, msgEditText.getText().toString(), keyEditText.getText().toString());
            outputTextView.setText(decryptedText);
        } catch (IllegalArgumentException e) {
            // TODO Toast
            Log.e("IllegalArgumentException", e.getMessage());
        }

    }
    private void onToggleBinaryButtonClick(View v) {

        if (isBinaryOut)
            outputTextView.setText(Util.fromBinaryStringToString(outputTextView.getText().toString()));
        else
            outputTextView.setText(Util.fromStringToBinaryString(outputTextView.getText().toString()));

        isBinaryOut = !isBinaryOut; // switch

    }

    private void onKeyEditTextClick(View v) {

        //if (keyEditText.getText().toString().length() == getResources().getString(R.string.default_vigenere_key).length())
        //keyEditText.setText("");
    }

    //public boolean onKeyEditTextTouch(View v, MotionEvent e) { // could be used later to let default text disappear

    private void onMsgEditTextClick(View v) {

    }

}