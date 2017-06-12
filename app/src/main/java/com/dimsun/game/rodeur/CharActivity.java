package com.dimsun.game.rodeur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.dimsun.game.rodeur.Constantes.KEY_CHANCE;
import static com.dimsun.game.rodeur.Constantes.KEY_DEFENSE;
import static com.dimsun.game.rodeur.Constantes.KEY_ESQUIVE;
import static com.dimsun.game.rodeur.Constantes.KEY_FORCE;
import static com.dimsun.game.rodeur.Constantes.KEY_HAS_SOUND;
import static com.dimsun.game.rodeur.Constantes.KEY_MAGIE;
import static com.dimsun.game.rodeur.Constantes.KEY_NIVEAU;
import static com.dimsun.game.rodeur.Constantes.KEY_NOM;
import static com.dimsun.game.rodeur.Constantes.KEY_OR;
import static com.dimsun.game.rodeur.Constantes.KEY_POINT;
import static com.dimsun.game.rodeur.Constantes.KEY_STEP;
import static com.dimsun.game.rodeur.Constantes.KEY_VIE;
import static com.dimsun.game.rodeur.Constantes.KEY_VIE_MAX;
import static com.dimsun.game.rodeur.Constantes.PREFS_NAME;
import static com.dimsun.game.rodeur.Constantes.SETTINGS_PREFS_NAME;

//ToDo: quand depense Point faite et point == 0 = cacher. + interdire point en negatif

public class CharActivity extends AppCompatActivity {

    int dataNiv, dataStep, dataVie, dataForce, dataDef, dataEsq, dataChan, dataMagie, dataPoint, dataOr, dataVieMax;
    final String TAG = MarcheActivity.class.getSimpleName( );
    TextView nom, niv, pas, vie, force, defense, esquive, chance, magie, or, point;
    Context ctx = this;
    String dataNom;
    Button buttonToMarche, addVie, addForce, addDef, addMagie, addEsq, addChance;
    boolean hasSound;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.Fade;
        setContentView(R.layout.activity_char);

        Context context = getApplicationContext();

        //region SharedPref

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences getSharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        dataNom = getSharedPref.getString(KEY_NOM, "nom");
        dataNiv = getSharedPref.getInt(KEY_NIVEAU, 1);
        dataVie = getSharedPref.getInt(KEY_VIE, 1);
        dataForce = getSharedPref.getInt(KEY_FORCE, 1);
        dataDef = getSharedPref.getInt(KEY_DEFENSE, 1);
        dataEsq = getSharedPref.getInt(KEY_ESQUIVE, 1);
        dataChan = getSharedPref.getInt(KEY_CHANCE, 1);
        dataMagie = getSharedPref.getInt(KEY_MAGIE, 1);
        dataStep = getSharedPref.getInt(KEY_STEP, 1);
        dataPoint = getSharedPref.getInt(KEY_POINT, 0);
        dataOr = getSharedPref.getInt(KEY_OR, 55);
        dataVieMax = getSharedPref.getInt(KEY_VIE_MAX, 55);
        Log.i(TAG, "•••••••••••••••• POINT : " + dataPoint + " •••••••••••••••••");

        SharedPreferences  sharedSettingsPref;
        sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean(KEY_HAS_SOUND, false);

        //endregion

        //region findViewById + setText

        nom = (TextView) findViewById(R.id.dataNom);
        nom.setText(dataNom);
        niv = (TextView) findViewById(R.id.dataNiv);
        niv.setText(""+dataNiv);
        pas = (TextView) findViewById(R.id.dataStep);
        pas.setText(""+dataStep);
        vie = (TextView) findViewById(R.id.dataVie);
        vie.setText(""+dataVie+" / "+dataVieMax);
        force = (TextView) findViewById(R.id.dataForce);
        force.setText(""+dataForce);
        defense = (TextView) findViewById(R.id.dataDef);
        defense.setText(""+dataDef);
        esquive = (TextView) findViewById(R.id.dataEsq);
        esquive.setText(""+dataEsq);
        chance = (TextView) findViewById(R.id.dataChan);
        chance.setText(""+dataChan);
        magie = (TextView) findViewById(R.id.dataMagie);
        magie.setText(""+dataMagie);
        or = (TextView) findViewById(R.id.dataOr);
        or.setText(""+dataOr);
        point = (TextView) findViewById(R.id.dataPoint);
        point.setText(""+dataPoint);

        addVie = (Button) findViewById(R.id.addvie);
        addForce = (Button) findViewById(R.id.addforce);
        addDef = (Button) findViewById(R.id.adddefense);
        addEsq = (Button) findViewById(R.id.addesquive);
        addChance = (Button) findViewById(R.id.addchance);
        addMagie = (Button) findViewById(R.id.addmagie);
        buttonToMarche = (Button) findViewById(R.id.buttonToMarche);

        //endregion

        //region onClickListener

        buttonToMarche.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                toggleSound();
                startActivity(new Intent(CharActivity.this, MarcheActivity.class));
                finish();
            }
        });



        addVie.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                tinySound();

                if (dataPoint > 0) {
                    dataVieMax++;
                    dataPoint--;
                    vie.setText(""+dataVie+" / "+dataVieMax);
                    point.setText(""+dataPoint);
                    save("Vie");
                } else {
                    noPointToast();
                }
            }
        });

        addForce.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                tinySound();

                if (dataPoint > 0) {
                    toggleSound();
                    dataForce++;
                    dataPoint--;
                    force.setText(""+dataForce);
                    point.setText(""+dataPoint);
                    save("Force");
                } else {
                    noPointToast();
                }
            }
        });

        addDef.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                tinySound();

                if (dataPoint > 0) {
                    dataDef++;
                    dataPoint--;
                    defense.setText(""+dataDef);
                    point.setText(""+dataPoint);
                    save("Defense");
                } else {
                    noPointToast();
                }
            }
        });

        addEsq.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                tinySound();

                if (dataPoint > 0) {
                    dataEsq++;
                    dataPoint--;
                    esquive.setText(""+dataEsq);
                    point.setText(""+dataPoint);
                    save("Esquive");
                } else {
                    noPointToast();
                }
            }
        });

        addMagie.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                tinySound();

                if (dataPoint > 0) {
                    toggleSound();
                    dataMagie++;
                    dataPoint--;
                    magie.setText(""+dataMagie);
                    point.setText(""+dataPoint);
                    save("Magie");
                } else {
                    noPointToast();
                }
            }
        });

        addChance.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                tinySound();

                if (dataPoint > 0) {
                    dataChan++;
                    dataPoint--;
                    chance.setText(""+dataChan);
                    point.setText(""+dataPoint);
                    save("Chance");
                } else {
                    noPointToast();
                }
            }


        });

        //endregion
    }

    private void noPointToast() {
        Toast.makeText(ctx, "Pas de points de competences.", Toast.LENGTH_SHORT).show();
    }

    public void onPause(Bundle savedInstanceState){
        super.onResume();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

    }

    private void save (String toSave) {

        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;

        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        switch (toSave){
            case "Vie": editor.putInt(KEY_VIE_MAX, dataVieMax);
                editor.putInt(KEY_POINT, dataPoint);
                break;
            case "Force" : editor.putInt(KEY_FORCE, dataForce);
                editor.putInt(KEY_POINT, dataPoint);
                break;
            case "Defense" : editor.putInt(KEY_DEFENSE, dataDef);
                editor.putInt(KEY_POINT, dataPoint);
                break;
            case "Esquive" : editor.putInt(KEY_ESQUIVE, dataEsq);
                editor.putInt(KEY_POINT, dataPoint);
                break;
            case "Chance" : editor.putInt(KEY_CHANCE, dataChan);
                editor.putInt(KEY_POINT, dataPoint);
                break;
            case "Magie" : editor.putInt(KEY_MAGIE, dataEsq);
                editor.putInt(KEY_POINT, dataPoint);
                break;
        }

        editor.apply();

        Log.i(TAG, "•••••••••••••••• SHARED PREF EDITOR COMMITED  •••••••••••••••••");


    }

    private void toggleSound() {
        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.toggle);
            mp.start( );
        }
    }

    private void tinySound() {
        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.tiny);
            mp.start( );
        }
    }

}
