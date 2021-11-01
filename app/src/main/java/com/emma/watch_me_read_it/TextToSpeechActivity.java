package com.emma.watch_me_read_it;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.Locale;


public class TextToSpeechActivity extends AppActivity implements View.OnClickListener {

    private FloatingActionButton readTextButton;
    private EditText editText;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);

        Intent intent = getIntent();
        String text = intent.getStringExtra(ImageToTextActivity.EXTRA_MESSAGE);
        editText = (EditText) findViewById(R.id.editSpeechText);
        editText.setText(text);

        readTextButton = (FloatingActionButton) findViewById(R.id.readTextButton);
        readTextButton.setOnClickListener(this);

        createNavigationBar(R.id.textToSpeech);

    }

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

    @Override
    public void onClick(View v) {
        Toast.makeText(TextToSpeechActivity.this, "Click!",
                Toast.LENGTH_SHORT).show();

        String toRead = editText.getText().toString();
        Toast.makeText(TextToSpeechActivity.this, "Text: " + toRead,
                Toast.LENGTH_SHORT).show();

        textToSpeech.speak(toRead, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        textToSpeech.stop();
        textToSpeech.shutdown();
    }
}