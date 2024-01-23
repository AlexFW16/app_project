package uk.bradford.app_project.listener;

import android.util.Log;
import android.view.View;

import java.util.Objects;

import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.fragments.CipherFragment;

public class DecryptionButtonListener implements View.OnClickListener {

    private final CipherFragment fragment;

    public DecryptionButtonListener(CipherFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        try {
            String decryptedText = Crypto.decrypt(fragment.getCipher().getType(), fragment.getMsgEditText().getText().toString(), fragment.getKeyEditText().getText().toString());
            fragment.getOutputTextView().setText(decryptedText);

        } catch (IllegalArgumentException e) {
            fragment.printErrorMessage(fragment.getCipher());
            Log.e("IllegalArgumentException", Objects.requireNonNull(e.getMessage()));
        }
    }
}
