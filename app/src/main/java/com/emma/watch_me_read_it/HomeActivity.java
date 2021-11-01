package com.emma.watch_me_read_it;

import android.os.Bundle;

public class HomeActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        createNavigationBar(R.id.about);

    }
}