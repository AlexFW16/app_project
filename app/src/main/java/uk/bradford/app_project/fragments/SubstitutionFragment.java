package uk.bradford.app_project.fragments;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.R;

public class SubstitutionFragment extends CipherFragment {

    @Override
    public Cipher getCipher() {
        return new Cipher(R.layout.substitution, R.string.input_error_substitution, R.string.usage_substitution, R.string.description_substitution, Cipher.Type.SUBSTITUTION);
    }


}
