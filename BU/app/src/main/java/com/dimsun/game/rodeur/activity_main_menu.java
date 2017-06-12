package com.dimsun.game.rodeur;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class activity_main_menu extends Activity {

    Button continuer, nouveau, options, aPropos;
    final String PREFS_NAME = "RODEUR_PREFS";
    String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    int classe;
    String TAG;
    ImageView lastPlayed;
    boolean hasSound;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_start);

        ActionBar actionBar = this.getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Initialisation du TAG pour les logCat
        TAG = activity_main_menu.class.getSimpleName();

        continuer = (Button) findViewById(R.id.continuer);
        lastPlayed = (ImageView) findViewById(R.id.last_played_char);
        nouveau = (Button) findViewById(R.id.nouveau);
        options = (Button) findViewById(R.id.options);


        /**
         * Recuperation de la donnée qui définie la classe.
         * classe : Si classe = 0 on affiche pas continuer car aucune partie n'est démarrée
         * hasSound : joue ou non les sons selon les reglages des options
         */

        SharedPreferences sharedP = this.getSharedPreferences("RODEUR_PREFS", Context.MODE_PRIVATE);
        SharedPreferences  sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        classe = sharedP.getInt("KEY_CLASSE",0);
        hasSound = sharedSettingsPref.getBoolean("KEY_SON", false);

        if (classe < 1) {continuer.setVisibility(View.GONE);}
        else {continuer.setVisibility(View.VISIBLE);}

            if (classe == 1) {
                lastPlayed.setImageResource(R.drawable.guerrier_selec);
            } else if (classe == 2) {
                lastPlayed.setImageResource(R.drawable.mage_selec);
            } else if (classe == 3){
                lastPlayed.setImageResource(R.drawable.patate_selec);
            }

        continuer.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                toggleSound();
                Log.i(TAG, "Continuer");
                startActivity(new Intent(activity_main_menu.this, activity_marche.class));
            }
        });

        nouveau.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                toggleSound();
                Log.i(TAG, "Nouveau");
                startActivity(new Intent(activity_main_menu.this, activity_nouveau.class));
            }
        });

        options.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                toggleSound();
                Log.i(TAG, "Options");
                startActivity(new Intent(activity_main_menu.this, OptionActivity.class));
            }
        });

        aPropos = (Button) findViewById(R.id.apropos);
        aPropos.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                toggleSound();
                Log.i(TAG, "A Propos");
                Uri uriUrl = Uri.parse("http://rodeurs.dimsun.fr");
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                startActivity(launchBrowser);
            }
        });
    }

    private void toggleSound() {
        if(hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.toggle);
            mp.start( );
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}


