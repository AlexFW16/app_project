package uk.bradford.app_project.listener;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;


public class SpeechRecognitionListener implements RecognitionListener {


    protected SpeechRecognizer speechRecognizer;
    private TextView outTextView;


    public SpeechRecognitionListener(SpeechRecognizer speechRecognizer, TextView outTextView) {
        this.speechRecognizer = speechRecognizer;
        this.outTextView = outTextView;
    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        // Called when the speech recognizer is ready for speech input
    }

    @Override
    public void onBeginningOfSpeech() {
        //TODO Implement "is listening dialog

        // Called when speech input has started
        // Show a listening dialog


            /*
            ViewGroup viewGroup = findViewById(android.R.id.content);
            View dialogView = LayoutInflater.from(MainActivity.this).inflate(R.layout.alertcus, viewGroup, false);

            alertSpeechDialog = new AlertDialog.Builder(MainActivity.this);
            alertSpeechDialog.setMessage("Listening.....");
            alertSpeechDialog.setView(dialogView);
            alertDialog = alertSpeechDialog.create();
            alertDialog.show();

             */
    }

    @Override
    public void onRmsChanged(float v) {
        // Called when the RMS (Root Mean Square) value of the audio changes
    }

    @Override
    public void onBufferReceived(byte[] bytes) {
        // Called when the audio buffer is received
    }

    @Override
    public void onEndOfSpeech() {
        // Called when speech input has ended
    }

    @Override
    public void onError(int i) {
        // Called when an error occurs during speech recognition
        Log.e("SpeechRecognitionListener", "Error occurred while recognizing speech, code: " + i);
    }

    // Called when speech recognition results are available
    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> arrayList = bundle.getStringArrayList(speechRecognizer.RESULTS_RECOGNITION);
        outTextView.setText(arrayList.get(0));
    }

    @Override
    public void onPartialResults(Bundle bundle) {
        // Called when partial recognition results are available
    }

    @Override
    public void onEvent(int i, Bundle bundle) {
        // Called when an event related to speech recognition occurs
    }
}
