package com.emma.watch_me_read_it;

import static com.emma.watch_me_read_it.ImageToTextActivity.EXTRA_MESSAGE;
import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AppActivity extends AppCompatActivity {


    protected void createNavigationBar(@IdRes int selectedItem) {

        // Variables
        TextView textView = (TextView) findViewById(R.id.detectedTextView);
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        // Set selected item
        navigationView.setSelectedItemId(selectedItem);

        // Navigation bar click listener
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (item.getItemId() == selectedItem) {
                    return true;
                }

                boolean handled = false;
                Intent intent = null;

                // Check selected item, create intent and launch activity
                switch (item.getItemId()) {
                    case R.id.homeStart:
                        intent = new Intent(AppActivity.this, HomeActivity.class);
                        handled = true;
                        break;

                    case R.id.imageToText:
                        intent = new Intent(AppActivity.this, ImageToTextActivity.class);
                        handled = true;
                        break;

                    case R.id.textToSpeech:
                        intent = new Intent(AppActivity.this, TextToSpeechActivity.class);

                        if (textView != null && !TextUtils.isEmpty(textView.getText().toString())) {
                            String inputText = textView.getText().toString();
                            intent.putExtra(EXTRA_MESSAGE,inputText);
                        }
                        handled = true;
                        break;
                }

                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return handled;
            }
        });
    }
}