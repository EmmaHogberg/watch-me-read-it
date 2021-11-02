package com.emma.watch_me_read_it;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Bottom navigation bar
        createNavigationBar(R.id.homeStart);
    }


    // Update selected activity item in navigation bar
    @Override
    protected void onResume() {
        super.onResume();
        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.homeStart);
    }
}