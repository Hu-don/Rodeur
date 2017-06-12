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
import static com.dimsun.game.rodeur.Constantes.KEY_OR;
import static com.dimsun.game.rodeur.Constantes.KEY_VIE;
import static com.dimsun.game.rodeur.Constantes.KEY_VIE_MAX;
import static com.dimsun.game.rodeur.Constantes.PREFS_NAME;
import static com.dimsun.game.rodeur.Constantes.SETTINGS_PREFS_NAME;

public class MortActivity extends AppCompatActivity {

    boolean hasSound;
    MediaPlayer mp;

    //ToDo : rajouter une illu sombre

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mort);

        SharedPreferences getSharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        int dataVie = getSharedPref.getInt(KEY_VIE, 0);
        int dataOr = getSharedPref.getInt(KEY_OR, 0);
        int dataVieMax = getSharedPref.getInt(KEY_VIE_MAX, 0);

        SharedPreferences sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean(KEY_HAS_SOUND, false);

        Button goToMarche = (Button) findViewById(R.id.goFromScoreToMarche);
        goToMarche.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                toggleSound();
                startActivity(new Intent(MortActivity.this, MarcheActivity.class));
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
