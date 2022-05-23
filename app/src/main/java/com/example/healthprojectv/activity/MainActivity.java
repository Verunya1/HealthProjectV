package com.example.healthprojectv.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.healthprojectv.Notification;
import com.example.healthprojectv.R;
import com.example.healthprojectv.SettingsFragment;
import com.example.healthprojectv.record.RecordMain;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity{

    BottomNavigationView bottomNavigationView;
    Notification notification = new Notification();
    RecordMain record = new RecordMain();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_navigation_bar);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, notification).commit();
        bottomNavigationView.setSelectedItemId(R.id.notifications);

//        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.notifications);
//        badgeDrawable.setVisible(true);
//        badgeDrawable.setNumber(8);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.notifications:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, notification).commit();
                        return true;

                    case R.id.record:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, record).commit();
//                        startActivity(new Intent(this, RecordChooseBottom.class));
                        return true;

                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, settingsFragment).commit();
                        return true;
                }
                return false;
            }
        });


    }








}