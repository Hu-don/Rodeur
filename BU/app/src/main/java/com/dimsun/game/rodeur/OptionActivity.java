package com.dimsun.game.rodeur;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class OptionActivity extends AppCompatActivity {

    boolean hasSound, hasDevMode;
    TextView stateSwitchSon, stateSwitchDevMode;
    String PREFS_NAME = "RODEUR_PREFS";
    String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    Button validerOptions, effacerData;
    MediaPlayer mp;
    int dataPas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences  sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        stateSwitchDevMode = (TextView) findViewById(R.id.stateSwitchDevMode);
        stateSwitchSon = (TextView) findViewById(R.id.stateSwitchSon);
        hasDevMode = sharedSettingsPref.getBoolean("KEY_DEV_MODE", false);
        hasSound = sharedSettingsPref.getBoolean("KEY_SON", false);
        dataPas = sharedPreferences.getInt("KEY_STEP", 0);

        //Ne pas déclarer les Switch avant le onCreate
        Switch switchDevMode, switchSon;
        switchDevMode = (Switch) findViewById(R.id.switchDevMode);
        switchSon = (Switch) findViewById(R.id.switchSon);

        if(hasSound) {
            switchSon.setChecked(hasSound);
            stateSwitchSon.setText("Activé");
        } else {
            stateSwitchSon.setText("Désactivé");
        }

        if(hasDevMode) {
            switchDevMode.setChecked(hasDevMode);
            stateSwitchDevMode.setText("Activé");
        } else {
            stateSwitchDevMode.setText("Désactivé");
        }

        switchDevMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                   hasDevMode = true;
                   stateSwitchDevMode.setText("Activé");
                   dataPas = 0;
                   validerOptions();

               } else {
                   hasDevMode = false;
                   stateSwitchDevMode.setText("Désactivé");
                    validerOptions();
               }
            }
        });

        switchSon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {
                    hasSound = true;
                    stateSwitchSon.setText("Activé");
                    validerOptions();
                } else {
                    hasSound = false;
                    stateSwitchSon.setText("Désactivé");
                    validerOptions();
                }
            }
        });


        if(switchDevMode.isChecked()) {
            stateSwitchDevMode.setText("Activé");
        } else {
            stateSwitchDevMode.setText("Désactivé");
        }


        if(switchSon.isChecked()) {
            stateSwitchSon.setText("Activé");
        } else {
            stateSwitchSon.setText("Désactivé");
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


    private void tinySound() {
        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.tiny);
            mp.start();
        }
    }

    public void validerOptions() {
        SharedPreferences sharedSettingsPref = getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedSettingsPref.edit();

        editor.putBoolean("KEY_SON", hasSound);
        editor.putBoolean("KEY_DEV_MODE", hasDevMode);
        editor.apply();

        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editorShared = sharedPreferences.edit();
        editorShared.putInt("KEY_STEP", dataPas);
        editorShared.apply();

        tinySound();
    }

}
