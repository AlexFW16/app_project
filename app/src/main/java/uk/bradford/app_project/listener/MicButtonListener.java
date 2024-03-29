package uk.bradford.app_project.listener;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MicButtonListener implements View.OnTouchListener {

    private final Activity activity;
    private final SpeechRecognizer speechRecognizer;

    private final Intent speechIntent;
    public static final Integer RecordAudioRequestCode = 1;

    public MicButtonListener(Activity activity, SpeechRecognizer speechRecognizer, Intent speechIntent) {
        this.activity = activity;
        this.speechRecognizer = speechRecognizer;
        this.speechIntent = speechIntent;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        view.performClick();
        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
            // Stop listening when the touch is released
            speechRecognizer.stopListening();
        }

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

            if (ContextCompat.checkSelfPermission(this.activity.getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                checkPermission();
            }
            // Start listening when the touch is pressed
            speechRecognizer.startListening(speechIntent);
        }
        return false;
    }

    private void checkPermission() {
        ActivityCompat.requestPermissions(this.activity, new String[]{Manifest.permission.RECORD_AUDIO}, RecordAudioRequestCode);
    }


}
