package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.R;

public class VigenereFragment extends Fragment {

    private Button encryptBtn;
    private Button decryptBtn;
    private EditText keyEditText;
    private EditText msgEditText;

    private TextView outputTextView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.vigenere, container, false);

        encryptBtn = rootView.findViewById(R.id.transposition_encrypt);
        decryptBtn = rootView.findViewById(R.id.transposition_decrypt);
        keyEditText = rootView.findViewById(R.id.xor_key);
        msgEditText= rootView.findViewById(R.id.xor_msg);
        outputTextView = rootView.findViewById(R.id.substitution_out);

        // Set the listeners

        encryptBtn.setOnClickListener(this::onEncryptButtonClick);
        decryptBtn.setOnClickListener(this::onDecryptButtonClick);
        keyEditText.setOnClickListener(this::onKeyEditTextClick);
        //keyEditText.setOnTouchListener(this::onKeyEditTextTouch);
        msgEditText.setOnClickListener(this::onMsgEditTextClick);

        return rootView;
    }


    //TODO implement
    private void onEncryptButtonClick(View v){

        try {
            String encryptedText = Crypto.encrypt(Cipher.VIGENERE, msgEditText.getText().toString().toUpperCase(), keyEditText.getText().toString().toUpperCase());
            outputTextView.setText(encryptedText);
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Message or Key contains letters different to \"a, b, c, ...\",", Toast.LENGTH_LONG);
            Log.e("IllegalArgumentException", e.getMessage());
        }


    }
    private void onDecryptButtonClick(View v){

        try {
            String encryptedText = Crypto.decrypt(Cipher.VIGENERE, msgEditText.getText().toString().toUpperCase(), keyEditText.getText().toString().toUpperCase());
            outputTextView.setText(encryptedText);
        }
        catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Message or Key contains letters different to \"a, b, c, ...\",", Toast.LENGTH_LONG);
            Log.e("IllegalArgumentException", e.getMessage());
        }   }

    private void onKeyEditTextClick(View v){

        //if (keyEditText.getText().toString().length() == getResources().getString(R.string.default_vigenere_key).length())
            keyEditText.setText("");
    }

    //public boolean onKeyEditTextTouch(View v, MotionEvent e) // could be used later to let default text disappear

    private void onMsgEditTextClick(View v){

    }
    // ...
}


