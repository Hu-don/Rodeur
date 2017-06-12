package com.dimsun.game.rodeur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class activity_startcombat extends Activity {

    boolean hasSound;
    String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_combat);

        SharedPreferences  sharedSettingsPref;
        sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean("KEY_SON", false);

        Button seBattre = (Button) findViewById(R.id.goToCombat);
        seBattre.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toggleSound();
                startActivity(new Intent(activity_startcombat.this, ChoiceCombat_Activity.class));
                finish();
            }
        });

        Button retourMarche = (Button) findViewById(R.id.goToCombat);
        retourMarche.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                toggleSound();
                startActivity(new Intent(activity_startcombat.this, activity_marche.class));
                finish();
            }
        });
    }

    private void toggleSound() {
        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.toggle);
            mp.start( );
        }
    }
}
