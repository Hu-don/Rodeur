package com.dimsun.game.rodeur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static com.dimsun.game.rodeur.Constantes.KEY_HAS_SOUND;
import static com.dimsun.game.rodeur.Constantes.SETTINGS_PREFS_NAME;

public class ChoiceCombatActivity extends AppCompatActivity {

    boolean hasSound;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_combat);

        SharedPreferences sharedSettingsPref;
        sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean(KEY_HAS_SOUND, false);

        Button goBackToMarche = (Button) findViewById(R.id.goToMarche);
        goBackToMarche.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                toggleSound();
                startActivity(new Intent(ChoiceCombatActivity.this, MarcheActivity.class));
                finish();
            }
        });

        Button goToCombat = (Button) findViewById(R.id.goToCombat);
        goToCombat.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                toggleSound();
                startActivity(new Intent(ChoiceCombatActivity.this, CombatActivity.class));
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
