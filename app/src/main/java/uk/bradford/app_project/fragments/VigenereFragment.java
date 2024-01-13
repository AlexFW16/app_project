package uk.bradford.app_project.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.Crypto;
import uk.bradford.app_project.R;

public class VigenereFragment extends CipherFragment {
    @Override
    public Cipher getCipher() {
        return new Cipher(R.layout.vigenere, R.string.input_error_vigenere, R.string.usage_vigenere, R.string.description_vigenere, Cipher.Type.VIGENERE);
    }

}


