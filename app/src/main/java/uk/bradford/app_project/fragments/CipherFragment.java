package uk.bradford.app_project.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Locale;

import uk.bradford.app_project.Cipher;
import uk.bradford.app_project.R;
import uk.bradford.app_project.listener.DecryptionButtonListener;
import uk.bradford.app_project.listener.EncryptionButtonListener;
import uk.bradford.app_project.listener.SpeechRecognitionListener;
import uk.bradford.app_project.listener.MicButtonListener;

public abstract class CipherFragment extends Fragment {
    protected Button encryptBtn, decryptBtn;

    protected EditText keyEditText, msgEditText;
    protected TextView outputTextView;

    protected ImageView micView;
    private SpeechRecognizer speechRecognizer;

    private Intent speechIntent;


    public abstract Cipher getCipher();


    public TextView getOutputTextView() {
        return this.outputTextView;
    }

    public EditText getMsgEditText() {
        return msgEditText;
    }

    public EditText getKeyEditText() {
        return keyEditText;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(getCipher().getLayout(), container, false);


        encryptBtn = rootView.findViewById(R.id.encrypt);
        decryptBtn = rootView.findViewById(R.id.decrypt);
        keyEditText = rootView.findViewById(R.id.key);
        msgEditText = rootView.findViewById(R.id.msg);
        outputTextView = rootView.findViewById(R.id.out);

        micView = rootView.findViewById(R.id.mic);

        // Must be before the listeners are set but after elements are assigned
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(rootView.getContext());

        this.speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new SpeechRecognitionListener(speechRecognizer, msgEditText));

        // Set the listeners
        MicButtonListener micButtonListener = new MicButtonListener(getActivity(), speechRecognizer, speechIntent);
        EncryptionButtonListener encryptionButtonListener = new EncryptionButtonListener(this);
        DecryptionButtonListener decryptionButtonListener = new DecryptionButtonListener(this);

        micView.setOnTouchListener(micButtonListener::onMicTouch);
        encryptBtn.setOnClickListener(encryptionButtonListener);
        decryptBtn.setOnClickListener(decryptionButtonListener);


        return rootView;
    }


    @Override
    public void onPause() {
        super.onPause();
        // Saves the current in/outputs in the cipher fragment into the shared prefs

        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(getCipher().getType().toString() + "key", keyEditText.getText().toString());
        editor.putString(getCipher().getType().toString() + "msg", msgEditText.getText().toString());
        // Previous error msg should not be saved
        if (outputTextView.getText().toString().equals(getResources().getString(getCipher().getErrorMsg())))
            editor.putString(getCipher().getType().toString() + "out", "");
        else
            editor.putString(getCipher().getType().toString() + "out", outputTextView.getText().toString());

        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Loads the latest in/outputs into the current cipher
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String key = prefs.getString(getCipher().getType().toString() + "key", "");
        String msg = prefs.getString(getCipher().getType().toString() + "msg", "");
        String out = prefs.getString(getCipher().getType().toString() + "out", "");

        keyEditText.setText(key);
        msgEditText.setText(msg);
        outputTextView.setText(out);
    }

    @Override
    public void onDestroyView() {
        if (speechRecognizer != null) speechRecognizer.destroy();
        super.onDestroyView();

    }

    // Prints the error msg as red text on the output field
    public void printErrorMessage(Cipher cipher) {
        // Creates a string that is inherently red, so the text colour of the textView doesn't need to be changed
        String errorMsg = getResources().getString(cipher.getErrorMsg());
        SpannableString spannableError = new SpannableString(errorMsg);
        spannableError.setSpan(new ForegroundColorSpan(Color.RED), 0, errorMsg.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        outputTextView.setText(spannableError);

    }


}
