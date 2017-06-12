package com.dimsun.game.rodeur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class ShopActivity extends AppCompatActivity {

    int prixPointDeNiveau, dataNiv, dataPoint, dataVie, dataOr, dataVieMax;
    final String TAG = ShopActivity.class.getSimpleName();
    final String PREFS_NAME = "RODEUR_PREFS";
    final String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    Context context = this;
    TextView orActuelView;
    Button buyVie;
    Button buyNiveau;
    Button goBackToMarche;
    boolean hasSound;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);


        buyVie = (Button) findViewById(R.id.buy_vie);
        buyNiveau = (Button) findViewById(R.id.buy_niveau);
        goBackToMarche = (Button) findViewById(R.id.buy_continuer);

        SharedPreferences getSharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        dataNiv = getSharedPref.getInt("KEY_NIVEAU", 55);
        dataVie = getSharedPref.getInt("KEY_VIE", 55);
        dataPoint = getSharedPref.getInt("KEY_POINT", 55);
        dataOr = getSharedPref.getInt("KEY_OR", 55);
        dataVieMax = getSharedPref.getInt("KEY_VIE_MAX", 55);

        SharedPreferences sharedSettingsPref = getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean("KEY_SON", false);


        orActuelView = (TextView) findViewById(R.id.disOr);
        orActuelView.setText(""+dataOr);

        prixPointDeNiveau = dataNiv *5;
        buyNiveau.setText("Acheter un point pour " +prixPointDeNiveau + " Or");


        buyNiveau.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                toggleSound();

                // Acheter un point de niveau
                if (dataOr >= prixPointDeNiveau) {
                    //Achat possible
                    dataPoint ++;
                    dataOr = dataOr - prixPointDeNiveau;
                    buyNiveau.setText("Acheter un point pour " +prixPointDeNiveau + " Or");
                    orActuelView.setText(""+dataOr);
                    Toast.makeText(context, "Vous avez achetez un point !", Toast.LENGTH_SHORT).show();


                    //Save
                    savedPointsToSharedPref();
                    savedOrToSharedPref();

                    //or restant = tt or - prix
                } else {
                    Toast.makeText(context, "Vous n'avez pas assez d'or", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "•••••••••••••••• PAS ASSEZ D'OR •••••••••••••••••");
                }

            }
        });

        buyVie.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {

                toggleSound();

                if (dataOr >= (dataVieMax - dataVie) && dataVie < dataVieMax) {
                    //Achat possible
                    dataVie = dataVieMax;
                    dataOr = dataOr - (dataVieMax - dataVie);
                    //Save
                    savedVieToSharedPref();
                    savedOrToSharedPref();
                    Toast.makeText(context, "Votre vie est désormais au max.", Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "•••••••••••••••• VIE RECUPEREE •••••••••••••••••");

                } else if (dataVie == dataVieMax){

                    Toast.makeText(context, "Vous avez déjà toute votre vie", Toast.LENGTH_SHORT).show( );
                    Log.i(TAG, "•••••••••••••••• VIE DEJA FULL •••••••••••••••••");

                } else {

                    Toast.makeText(context, "Vous n'avez pas assez d'or ! ", Toast.LENGTH_SHORT).show( );
                    Log.i(TAG, "•••••••••••••••• PAS ASSEZ D'OR •••••••••••••••••");
                }

            }
        });

        goBackToMarche.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                toggleSound();
                startActivity(new Intent(ShopActivity.this, activity_marche.class));
                finish();
            }
        });

    }

    private void savedPointsToSharedPref() {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("KEY_POINT", dataPoint);
        Log.i(TAG, "•••••••••••••••• EDITOR POINT UPDATED ------- NOW " +dataPoint+ "  •••••••••••••••••");
        editor.apply();
    }

    private void savedVieToSharedPref() {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("KEY_VIE", dataVie);
        Log.i(TAG, "•••••••••••••••• EDITOR POINT UPDATED ------- NOW " +dataVie+ "  •••••••••••••••••");
        editor.apply();
    }

    private void savedOrToSharedPref() {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("KEY_OR", dataOr);
        Log.i(TAG, "•••••••••••••••• EDITOR POINT UPDATED ------- NOW " +dataOr+ "  •••••••••••••••••");
        editor.apply();
    }

    private void toggleSound() {
        if (hasSound)
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.toggle);
            mp.start( );

    }
}
