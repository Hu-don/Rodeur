package com.dimsun.game.rodeur;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.dimsun.game.rodeur.R.id.still_char;

public class activity_nouveau extends Activity {

    //une variable gardera en mémoire quelle classe sera choisis et une autre quel nom.
    String classeType, nom;
    String joueur_nom= "";
    Context ctx = this;
    final String TAG = activity_nouveau.class.getSimpleName();
    public final String PREFS_NAME = "RODEUR_PREFS";
    String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    TextView classe, vie, esq, force, chan, magie, def, display_nom;
    ImageView stillChar;
    EditText inputNom;
    boolean hasSound, hasClasseToValidate;
    MediaPlayer mp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nouveau);

        android.app.ActionBar actionBar = this.getActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences sharedPref, sharedSettingsPref;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean("KEY_SON", false);


        vie = (TextView) findViewById(R.id.dataVie);
        def = (TextView) findViewById(R.id.dataDef);
        force = (TextView) findViewById(R.id.dataForce);
        esq = (TextView) findViewById(R.id.dataEsq);
        chan = (TextView) findViewById(R.id.dataChan);
        magie = (TextView) findViewById(R.id.dataMagie);
        stillChar = (ImageView) findViewById(still_char);
        inputNom = (EditText) findViewById(R.id.joueur_nom);

        // ********** Guerrier *********** //

        Button selectGuerrier = (Button) findViewById(R.id.selectGuerrier);
        selectGuerrier.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View view) {
                tinySound();
                hasClasseToValidate = true;
                nom = inputNom.getText( ).toString( );
                stillChar.setImageResource(R.drawable.guerrier_selec);
                vie.setText(getString(R.string.guer_vie));
                def.setText(getString(R.string.guer_defense));
                force.setText(getString(R.string.guer_force));
                esq.setText(getString(R.string.guer_esquive));
                chan.setText(getString(R.string.guer_chance));
                magie.setText(getString(R.string.guer_magie));
                classeType = "Guerrier";
                Log.i(TAG, classeType);

                save (nom, 1, 5, 4, 4, 3, 2, 1);
            }
        });

        // ********** Sorcier *********** //

        Button selectSorcier = (Button) findViewById(R.id.selectSorcier);
        selectSorcier.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View view) {
                tinySound();
                hasClasseToValidate = true;
                nom = inputNom.getText( ).toString( );
                stillChar.setImageResource(R.drawable.mage_selec);
                vie.setText(getString(R.string.sorc_vie));
                def.setText(getString(R.string.sorc_defense));
                force.setText(getString(R.string.sorc_force));
                esq.setText(getString(R.string.sorc_esquive));
                chan.setText(getString(R.string.sorc_chance));
                magie.setText(getString(R.string.sorc_magie));
                classeType = "Sorcier";
                Log.i(TAG, classeType);
                save (nom, 2, 4, 2, 1, 3, 3, 4);

            }
        });

        // ********** Patate *********** //

        Button selectPatate = (Button) findViewById(R.id.selectPatate);
        selectPatate.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View view) {
                tinySound();
                hasClasseToValidate = true;
                nom = inputNom.getText( ).toString( );
                stillChar.setImageResource(R.drawable.patate_selec);
                vie.setText(getString(R.string.pata_vie));
                def.setText(getString(R.string.pata_defense));
                force.setText(getString(R.string.pata_force));
                esq.setText(getString(R.string.pata_esquive));
                chan.setText(getString(R.string.pata_chance));
                magie.setText(getString(R.string.pata_magie));
                classeType = "Patate";
                Log.i(TAG, classeType);
                save (nom, 3, 2, 2, 2, 2, 2, 2);
            }
        });

        // ********** Valider *********** //

        Button validerCreation = (Button) findViewById(R.id.valider);
        validerCreation.setOnClickListener(new View.OnClickListener( ) {

            @Override
            public void onClick(View view) {
                Log.i(TAG, "Valider");
                Log.i(TAG, joueur_nom);

                String joueur_nom = inputNom.getText( ).toString( );

                if (joueur_nom.isEmpty()){
                    Toast.makeText(ctx, "Donnez un nom à votre personnage.", Toast.LENGTH_SHORT).show();
                } else if (!hasClasseToValidate){
                    Toast.makeText(ctx, "Choissisez une classe pour votre personnage.", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(new Intent(activity_nouveau.this, activity_marche.class));
                }
            }
        });
    }

    private void save (String nom, int classe, int vie, int defense, int force, int esquive, int magie, int chance) {

        // ********** SharedPreferences *********** //
        Log.i(TAG, String.valueOf(classe));
        Log.i(TAG, joueur_nom);
        Log.i(TAG, String.valueOf(vie));
        Log.i(TAG, String.valueOf(defense));
        Log.i(TAG, String.valueOf(force));
        Log.i(TAG, String.valueOf(esquive));
        Log.i(TAG, String.valueOf(magie));
        Log.i(TAG, String.valueOf(chance));

        int step = 0;
        int point = 0;

        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.clear();

        editor.putString("KEY_NAME", nom);
        editor.putInt("KEY_CLASSE", classe);
        editor.putInt("KEY_NIVEAU", 1);
        editor.putInt("KEY_VIE", vie);
        editor.putInt("KEY_VIE_MAX", vie);
        editor.putInt("KEY_CHANCE", chance);
        editor.putInt("KEY_ESQUIVE", esquive);
        editor.putInt("KEY_MAGIE", magie);
        editor.putInt("KEY_DEFENSE", defense);
        editor.putInt("KEY_FORCE", force);
        editor.putInt("KEY_STEP", step);
        editor.putInt("KEY_NEXT", (step*100));
        editor.putInt("KEY_POINT", point);
        editor.putInt("KEY_OR", 10);
        editor.apply();
        Log.i(TAG, "•••••••••••••••• SHARED PREF EDITOR COMMITED  •••••••••••••••••");


    }

    private void tinySound() {
        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.tiny);
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

    @Override
    protected void onPause() {
        super.onPause( );
        mp.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy( );
        mp.stop();
    }
}
