package com.emma.watch_me_read_it;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Locale;


public class TextToSpeechActivity extends AppActivity implements View.OnClickListener {

    // Variables
    private FloatingActionButton readTextButton;
    private EditText editText;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        // Bottom navigation bar
        createNavigationBar(R.id.textToSpeech);

        // Get input text from intent and set to editText
        Intent intent = getIntent();
        String text = intent.getStringExtra(ImageToTextActivity.EXTRA_MESSAGE);
        editText = (EditText) findViewById(R.id.editSpeechText);
        editText.setText(text);

        // Action button and listener
        readTextButton = (FloatingActionButton) findViewById(R.id.readTextButton);
        readTextButton.setOnClickListener(this);
    }


    // Start new TextToSpeech service on start
    @Override
    protected void onStart() {
        super.onStart();

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                try {
                    textToSpeech.setLanguage(new Locale("sv", "SE"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.wtf("WTF", "onInit: ", e);
                }
            }
        });
    }


    // Update selected activity item in navigation bar
    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.textToSpeech);
    }


    // Get text and speak it out on click
    @Override
    public void onClick(View v) {
        String toRead = editText.getText().toString();
        textToSpeech.speak(toRead, TextToSpeech.QUEUE_FLUSH, null, null);
    }


    // Stop TextToSpeech service
    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }
}