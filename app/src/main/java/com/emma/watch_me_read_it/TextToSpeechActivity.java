package com.emma.watch_me_read_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class TextToSpeechActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_to_speech);


        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.textToSpeech) {

                    Intent imageIntent = new Intent(TextToSpeechActivity.this, MainActivity.class);
                    imageIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(imageIntent);
                    finish();

                    return true;
                }
                return false;
            }
        });









    }
}