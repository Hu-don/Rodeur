package com.dimsun.game.rodeur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class ScoreActivity extends AppCompatActivity {

    MediaPlayer mp;
    int lastEncounter, dataVie, dataOr, dataVieMax;
    ImageView monstreImage;
    boolean hasSound;
    final String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    final String PREFS_NAME = "RODEUR_PREFS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        SharedPreferences getSharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        lastEncounter = getSharedPref.getInt("KEY_ENCOUNTER", 0);
        dataVie = getSharedPref.getInt("KEY_VIE", 0);
        dataOr = getSharedPref.getInt("KEY_OR", 0);
        dataVieMax = getSharedPref.getInt("KEY_VIE_MAX", 0);

        SharedPreferences sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean("KEY_SON", false);

        initImageDeadMonster();
        soundVictory();

        TextView displayVie = (TextView) findViewById(R.id.disVie);
        displayVie.setText("Vie restante : "+dataVie+" / " +dataVieMax);
        TextView displayOr = (TextView) findViewById(R.id.disOr);
        displayOr.setText("Vous avez désormais : "+dataOr+" or !");

        Button goToMarche = (Button) findViewById(R.id.goFromScoreToMarche);
        goToMarche.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                toggleSound();
                startActivity(new Intent(ScoreActivity.this, activity_marche.class));
                finish();
            }
        });

    }

    private void soundVictory() {
        mp = new MediaPlayer();
        mp = MediaPlayer.create(this, R.raw.victory);
        mp.start();
    }

    private void initImageDeadMonster() {

        monstreImage = ( ImageView) findViewById(R.id.imageMonstre);

        switch (lastEncounter) {
            case 0:
                monstreImage.setImageResource(R.drawable.monstre1mort);
                break;
            case 1:
                monstreImage.setImageResource(R.drawable.monstre2mort);
                break;
            case 2:
                monstreImage.setImageResource(R.drawable.monstre3mort);
                break;
        }
    }

    private void toggleSound() {
        if (hasSound) {
        mp = new MediaPlayer( );
        mp = MediaPlayer.create(this, R.raw.toggle);
        mp.start( );
      }
    }
}
