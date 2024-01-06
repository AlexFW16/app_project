package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;


import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.R;
import uk.bradford.app_project.Util;

public class XORFragment extends CipherFragment {

    private Switch toggleBinarySwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.xor, container, false);

        encryptBtn = rootView.findViewById(R.id.encrypt);
        decryptBtn = rootView.findViewById(R.id.decrypt);
        toggleBinarySwitch = rootView.findViewById(R.id.toggle_binary);
        keyEditText = rootView.findViewById(R.id.key);
        msgEditText = rootView.findViewById(R.id.msg);
        outputTextView = rootView.findViewById(R.id.out);


        // Set the listeners

        encryptBtn.setOnClickListener(this::onEncryptButtonClick);
        decryptBtn.setOnClickListener(this::onDecryptButtonClick);
        toggleBinarySwitch.setOnCheckedChangeListener(this::onBinaryToggle);

        return rootView;
    }

    @Override
    protected Cipher getCipher(){
       return Cipher.XOR;
    }

    private void onEncryptButtonClick(View v) {
        try {

            String encryptedText = Crypto.encrypt(Cipher.XOR, msgEditText.getText().toString(), keyEditText.getText().toString());
            if (toggleBinarySwitch.isChecked())
                encryptedText = Util.fromStringToBinaryString(encryptedText);

            outputTextView.setText(encryptedText);
        } catch (IllegalArgumentException e) {

            printErrorMessage(R.string.input_error_xor);
           // Toast.makeText(getActivity().getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("IllegalArgumentException", e.getMessage());
        }
    }

    private void onDecryptButtonClick(View v) {
        try {
            String decryptedText = Crypto.decrypt(Cipher.XOR, msgEditText.getText().toString(), keyEditText.getText().toString());

            if (toggleBinarySwitch.isChecked())
                decryptedText = Util.fromStringToBinaryString(decryptedText);
            outputTextView.setText(decryptedText);
        } catch (IllegalArgumentException e) {
            // TODO Toast
            printErrorMessage(R.string.input_error_xor);
            Log.e("IllegalArgumentException", e.getMessage());
        }

    }

    private void onBinaryToggle(CompoundButton buttonView, boolean isChecked) {

        if (!isChecked)
            outputTextView.setText(Util.fromBinaryStringToString(outputTextView.getText().toString()));
        else
            outputTextView.setText(Util.fromStringToBinaryString(outputTextView.getText().toString()));


    }


    //private void onKeyEditTextClick(View v) {

    //public boolean onKeyEditTextTouch(View v, MotionEvent e) { // could be used later to let default text disappear

    //private void onMsgEditTextClick(View v) {


}