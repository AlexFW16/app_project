package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.appcompat.widget.SwitchCompat;

import java.util.Objects;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.R;
import uk.bradford.app_project.Util;

public class XORFragment extends CipherFragment {

    private SwitchCompat toggleBinarySwitch;

    @Override
    public Cipher getCipher() {
        return new Cipher(R.layout.xor, R.string.input_error_xor, R.string.usage_xor, R.string.description_xor, Cipher.Type.XOR);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        assert rootView != null;
        toggleBinarySwitch = rootView.findViewById(R.id.toggle_binary);

        super.encryptBtn.setOnClickListener(this::onEncryptButtonClick);
        super.decryptBtn.setOnClickListener(this::onDecryptButtonClick);
        toggleBinarySwitch.setOnCheckedChangeListener(this::onBinaryToggle);

        return rootView;
    }

    private void onEncryptButtonClick(View v) {
        try {

            String encryptedText = Crypto.encrypt(getCipher().getType(), msgEditText.getText().toString(), keyEditText.getText().toString());
            if (toggleBinarySwitch.isChecked())
                encryptedText = Util.fromStringToBinaryString(encryptedText);

            outputTextView.setText(encryptedText);
        } catch (IllegalArgumentException e) {
            printErrorMessage(getCipher());
            Log.e("IllegalArgumentException", Objects.requireNonNull(e.getMessage()));
        }
    }

    private void onDecryptButtonClick(View v) {
        try {
            String decryptedText = Crypto.decrypt(getCipher().getType(), msgEditText.getText().toString(), keyEditText.getText().toString());

            if (toggleBinarySwitch.isChecked())
                decryptedText = Util.fromStringToBinaryString(decryptedText);
            outputTextView.setText(decryptedText);
        } catch (IllegalArgumentException e) {
            printErrorMessage(getCipher());
            Log.e("IllegalArgumentException", Objects.requireNonNull(e.getMessage()));
        }

    }

    private void onBinaryToggle(CompoundButton buttonView, boolean isChecked) {
        if (!isChecked)
            outputTextView.setText(Util.fromBinaryStringToString(outputTextView.getText().toString()));
        else
            outputTextView.setText(Util.fromStringToBinaryString(outputTextView.getText().toString()));

    }

    @Override
    public void onPause() {
        toggleBinarySwitch.setChecked(false);
        super.onPause();
    }


}


