package com.dimsun.game.rodeur;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Random;

import static android.util.Log.i;


/*************************************************************
 * MARCHE
 *************************************************************/


public class MarcheActivity extends Activity implements SensorEventListener {

    final String TAG = MarcheActivity.class.getSimpleName( );
    final String PREFS_NAME = "RODEUR_PREFS";
    String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    boolean isDead, hasSound, hasDevMode;
    TextView showSteps;
    int dataVie, dataVieMax, pallier, dataCharDesign, stepsToBeAlive, dataNext, dataPoint, dataStep, dataNiv, stepRessurection;
    ImageView marcheStill;
    Vibrator vibrate;
    HashSet<Integer> meetSet;
    MediaPlayer mp;
    Sensor countSensor;
    AnimationDrawable marcheAnimation;
    SensorManager sManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marche);

        /*
        Animation vers les stats
         */

        getWindow( ).getAttributes( ).windowAnimations = R.style.Fade;


        showSteps = (TextView) findViewById(R.id.count);
        marcheStill = (ImageView) findViewById(R.id.imageMarche);

        /*
        Action Bar retour menu
         */

        ActionBar actionBar = this.getActionBar( );
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*
        Recuperation des datas
         */

        SharedPreferences getSharedPref, sharedSettingsPref;
        getSharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor;
        editor = getSharedPref.edit();

        String playNom = getSharedPref.getString(Constantes.KEY_NOM, null);
        dataNiv = getSharedPref.getInt(Constantes.KEY_NIVEAU, 0);
        dataStep = getSharedPref.getInt (Constantes.KEY_STEP, 0);
        dataVie = getSharedPref.getInt(Constantes.KEY_VIE, 0);
        dataVieMax = getSharedPref.getInt(Constantes.KEY_VIE_MAX, 0);
        dataPoint = getSharedPref.getInt(Constantes.KEY_POINT, 0);
        dataCharDesign = getSharedPref.getInt(Constantes.KEY_CLASSE, 0);

        sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean(Constantes.KEY_HAS_SOUND, false);
        hasDevMode = sharedSettingsPref.getBoolean(Constantes.KEY_HAS_DEV_MODE, false);

        editor.putInt(Constantes.KEY_RES, stepsToBeAlive);

          /*
        Initialisation animation de marche 1 : guerrier, 2 : mage, 3 : patate
         */

        animationMarche( );

        if (dataVie < 1) {
            isDead = true;
            ressurection();
            showSteps.setText("Resurection : " + ( stepsToBeAlive ) + " / " + stepRessurection);
        } else {
            isDead = false;
            initMarcheMode( );
        }


        //Sensor
        sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        i(TAG, "•••••••••••••••• Sensor init ••••••••••••••••");

        Button buttonToStats = (Button) findViewById(R.id.buttonToStats);
        buttonToStats.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                toggleSound( );
                startActivity(new Intent(MarcheActivity.this, CharActivity.class));
                finish( );
            }
        });
    }

    /*
    Sensor step
     */

    public void onSensorChanged(SensorEvent event) {

        vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

        marcheAnimation.start( );

        if (isDead) {

            ressurection( );

        } else {
            //Pas
            dataStep++;
            i(TAG, "•••••••••••••••• PAS : " + dataStep + " •••••••••••••••••");
            savedStepsToSharedPref(dataStep);
            rencontreListener( );
            initMarcheMode( );
            showSteps.setText("" + ( dataStep ) + " / " + ( pallier ));
        }
    }

    private void animationMarche() {

        if (dataCharDesign == 1) {
            marcheStill.setBackgroundResource(R.drawable.guerrier_animation);
        } else if (dataCharDesign == 2) {
            marcheStill.setBackgroundResource(R.drawable.mage_animation);
        } else if (dataCharDesign == 3) {
            marcheStill.setBackgroundResource(R.drawable.patate_animation);
        }
        marcheAnimation = (AnimationDrawable) marcheStill.getBackground( );
    }

    // region Initialisation de la marche

    private void ressurection() {

        if (hasDevMode) {
            stepRessurection = 10;
        } else {
            stepRessurection = 500;
        }

        stepsToBeAlive++;
        showSteps.setText("Resurection : " + ( stepsToBeAlive ) + " / " + stepRessurection);

        if (stepsToBeAlive == stepRessurection) {
            dataVie = dataVieMax;
            isDead = false;
            savedVieToSharedPref(dataVie);
            vibrate.vibrate(500);
            Toast.makeText(this, "Vous êtes de nouveau vivant !", Toast.LENGTH_LONG).show( );
        }
    }

    private void niveauListener() {

        /*
            Initialisation du pallier selon l'option devMode
         */

        if (dataStep == pallier) {
            Log.i(TAG, "•••••••••••••••• Pallier : " + pallier + "  •••••••••••••••••");
            i(TAG, "•••••••••••••••• LEVEL UP •••••••••••••••••");
            notification( );
            Toast.makeText(this, "Vous avez gagné un point de compétence !", Toast.LENGTH_LONG).show( );

            dataStep++;
            dataPoint++;
            dataNiv++;

            savedStepsToSharedPref(dataStep);
            savedNiveauToSharedPref(dataNiv);
            savedPointsToSharedPref(dataPoint);
            Log.i(TAG, "•••••••••••••••• POINTS DE COMP : " + dataPoint + "  •••••••••••••••••");
        }
    }

    private void initMarcheMode() {

        double coef;

        if (hasDevMode) {
            pallier = dataNiv * 10;
            niveauListener( );

        } else {

            if (dataNiv == 1) {
                double nextLevelSteps = dataNiv * 50;
                pallier = (int) nextLevelSteps;
                niveauListener( );


            } else if (dataNiv < 10 && dataNiv > 1) {
                coef = 1.5;
                double nextLevelSteps = ( ( dataNiv - 1 ) * 50 ) * coef;
                pallier = (int) nextLevelSteps;
                niveauListener( );

            } else if (dataNiv >= 10 && dataNiv < 40) {
                coef = 1.2;
                double nextLevelSteps = ( ( dataNiv - 1 ) * 50 ) * coef;
                pallier = (int) nextLevelSteps;
                niveauListener( );

            } else {
                coef = 1.15;
                double nextLevelSteps = ( ( dataNiv - 1 ) * 50 ) * coef;
                pallier = (int) nextLevelSteps;
                niveauListener( );
            }
        }
    }

    private void notification() {

        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.snare);
            mp.start( );
        }

        vibrate.vibrate(300);
    }

    public void rencontreListener() {


        Random r2 = new Random( );
        int rng;

        if (hasDevMode) {
            rng = 20;
        } else {
            rng = 80;
        }

        int randomMeetNum2 = Math.abs(r2.nextInt( )) % rng;

        i(TAG, "•••••••••••••••• Random Meet F/S " + randomMeetNum2 + " •••••••••••••••••");

        switch (randomMeetNum2) {
            case 1:
                notification( );
                i(TAG, "•••••••••••••••• MEET •••••••••••••••••");
                i(TAG, "•••••••••••••••• SHOP •••••••••••••••••");
                startActivity(new Intent(this, ShopActivity.class));
                finish( );
                break;

            case 10:
                notification( );
                i(TAG, "•••••••••••••••• MEET •••••••••••••••••");
                i(TAG, "•••••••••••••••• COMBAT •••••••••••••••••");
                startActivity(new Intent(this, ChoiceCombatActivity.class));
                finish( );
                break;
        }
    }

    //endregion

    //region Sauvegarde des datas

    private void savedStepsToSharedPref(int dataStep) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit( );
        editor.putInt(Constantes.KEY_STEP, dataStep);
        i(TAG, "•••••••••••••••• EDITOR STEP UPDATED •••••••••••••••••");
        editor.apply( );
    }

    private void savedPointsToSharedPref(int dataPoint) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit( );
        editor.putInt(Constantes.KEY_POINT, dataPoint);
        i(TAG, "•••••••••••••••• EDITOR POINT UPDATED ------- NOW " + dataPoint + "  •••••••••••••••••");
        editor.apply( );
    }

    private void savedNiveauToSharedPref(int dataNiv) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit( );
        editor.putInt(Constantes.KEY_NIVEAU, dataNiv);
        i(TAG, "•••••••••••••••• EDITOR NIV UPDATED ------- NOW " + dataNiv + "  •••••••••••••••••");
        editor.apply( );
    }

    private void savedVieToSharedPref(int dataVie) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit( );
        editor.putInt(Constantes.KEY_VIE, dataVie);
        i(TAG, "•••••••••••••••• EDITOR VIE UPDATED ------- NOW " + dataVie + "  •••••••••••••••••");
        editor.apply( );
    }

    //endregion

    //region Cycle de vie

    public void onResume() {
        super.onResume( );
        getWindow( ).getAttributes( ).windowAnimations = R.style.Fade;

        i(TAG, "•••••••••••••••• onResume Loading •••••••••••••••••");
        Sensor countSensor = sManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (countSensor != null) {

            sManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);

        } else {
            i(TAG, "•••••••••••••••• PAS DE CAPTEURS DE PAS •••••••••••••••••");
            Toast.makeText(this, "Y'a pas de capteur de pas dans ton tel !", Toast.LENGTH_LONG).show( );

        }
        i(TAG, "•••••••••••••••• onResume Finished Loading •••••••••••••••••");

    }

    public void onPause() {
        super.onPause( );
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        i(TAG, "•••••••••••••••• onPause •••••••••••••••••");
        SensorManager sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        i(TAG, "•••••••••••••••• isRunning FALSE •••••••••••••••••");
        sManager.unregisterListener(this);
    }

    public void onDestroy() {
        super.onDestroy( );
        i(TAG, "•••••••••••••••• onDestroy •••••••••••••••••");
        SensorManager sManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        i(TAG, "•••••••••••••••• isRunning FALSE •••••••••••••••••");
//        isRunning = false;
//        sManager.unregisterListener(this);

    }

    //endregion

    /*
    Sensor methode
     */

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        i(TAG, "•••••••••••••••• onAccuraryChanges •••••••••••••••••");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId( )) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void toggleSound() {
        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.toggle);
            mp.start( );
        }
    }

}