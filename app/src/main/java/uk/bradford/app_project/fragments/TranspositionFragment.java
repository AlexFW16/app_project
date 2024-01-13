package uk.bradford.app_project.fragments;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.R;

public class TranspositionFragment extends CipherFragment {
    @Override
    public Cipher getCipher() {
        return new Cipher(R.layout.transposition, R.string.input_error_transposition, R.string.usage_transposition, R.string.description_transposition, Cipher.Type.TRANSPOSITION);
    }


}
