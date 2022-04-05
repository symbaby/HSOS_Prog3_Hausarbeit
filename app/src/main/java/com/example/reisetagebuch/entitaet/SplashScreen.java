/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.entitaet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.reisetagebuch.activities.MainActivity;
import com.example.reisetagebuch.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Sekunden bis zum Screen Wechsel
        int seconds = 3;


        // Ausblenden von der Actionbar
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(this::switchScreen, seconds * 1000);

    }

    private void switchScreen() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

}