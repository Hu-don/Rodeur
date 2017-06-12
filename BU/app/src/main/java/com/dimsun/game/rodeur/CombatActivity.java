package com.dimsun.game.rodeur;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import static android.util.Log.i;

public class CombatActivity extends AppCompatActivity {


    final String PREFS_NAME = "RODEUR_PREFS";
    String SETTINGS_PREFS_NAME = "SETTINGS_PREFS";
    boolean isWinner, hasSound;
    int dataNiv, dataForce, dataDef, dataMagie, dataVie, dataEsq, dataOr, dataVieMax, dataChan;
    int mobVie, mobDegatPhy, mobDegatMag, mobDefPhy, mobDefMag;
    float degatPhy, degatMag, defPhy, defMag;
    Monstre mob;
    Joueur joueur;
    final String TAG = CombatActivity.class.getSimpleName( );
    TextView infos;
    Context context = this;
    ImageView monstreImage;
    ImageButton roll;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_combat);

        roll = (ImageButton) findViewById(R.id.roll);
        infos = (TextView) findViewById(R.id.combatInfo);
        monstreImage = (ImageView) findViewById(R.id.imageMonstre);

        Context context = getApplicationContext();
        String PREFS_NAME = "RODEUR_PREFS";
        SharedPreferences getSharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        dataNiv = getSharedPref.getInt("KEY_NIVEAU", 0);
        dataVie = getSharedPref.getInt("KEY_VIE", 0);
        dataForce = getSharedPref.getInt("KEY_FORCE", 0);
        dataDef = getSharedPref.getInt("KEY_DEFENSE", 0);
        dataEsq = getSharedPref.getInt("KEY_ESQUIVE", 0);
        dataChan = getSharedPref.getInt("KEY_CHANCE", 0);
        dataMagie = getSharedPref.getInt("KEY_MAGIE", 0);
        dataOr = getSharedPref.getInt("KEY_OR", 0);
        dataVieMax = getSharedPref.getInt("KEY_VIE_MAX", 0);

        SharedPreferences sharedSettingsPref = getApplicationContext( ).getSharedPreferences(SETTINGS_PREFS_NAME, Context.MODE_PRIVATE);
        hasSound = sharedSettingsPref.getBoolean("KEY_SON", false);

        joueur = new Joueur(dataNiv, dataVie, dataVieMax, dataDef, dataEsq, dataChan, dataMagie, dataOr, dataForce);

        i(TAG, "•••••••••••••••• Joueur Vie : " +joueur.getVie()+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Joueur Degat Phy : " +joueur.getDegatPhy()+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Joueur Degat Mag : " +joueur.getDegatMag()+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Joueur Def Phy : " +joueur.getDefPhy()+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Joueur Def Mag : " +joueur.getDefMag()+ " •••••••••••••••••");

        randomMob();


        roll.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                i(TAG, "•••••••••••••••• COMBAT•••••••••••••••••");

                if (mobVie > 0 && dataVie > 0) {
                    combat( );
                } else {
                    i(TAG, "•••••••••••••••• Personne n'est vivant •••••••••••••••••");
                }
            }
        });

    }

    private void randomMob() {

        Random ranMob = new Random( );
        int rMob = Math.abs(ranMob.nextInt( )) % 3;
        i(TAG, "•••••••••••••••• " + rMob+ " •••••••••••••••••");

        switch (rMob) {

            case 0:
                i(TAG, "•••••••••••••••• ARAIGNEE •••••••••••••••••");

                mobVie = (int) (dataVieMax * 1);
                mobDegatPhy = (int) ( joueur.getDegatPhy( ) *1);
                mobDegatMag = (int) (joueur.getDegatMag() * 1);
                mobDefPhy = (int) (joueur.getDefPhy() * 1);
                mobDefMag = (int) (joueur.getDefMag() * 1);

                mob = new Monstre (mobVie, mobDegatPhy, mobDegatMag, mobDefPhy, mobDefMag);
                monstreImage.setImageResource(R.drawable.monstre1);
                savedEncounterToSharedPref(rMob);
                break;

            case 1:
                i(TAG, "•••••••••••••••• SQUELETTE •••••••••••••••••");

                mobVie = (int) (dataVieMax * 1);
                mobDegatPhy = (int) ( joueur.getDegatPhy( ) *1);
                mobDegatMag = (int) (joueur.getDegatMag() * 1);
                mobDefPhy = (int) (joueur.getDefPhy() * 1);
                mobDefMag = (int) (joueur.getDefMag() * 1);

                mob = new Monstre (mobVie, mobDegatPhy, mobDegatMag, mobDefPhy, mobDefMag);
                monstreImage.setImageResource(R.drawable.monstre2);
                savedEncounterToSharedPref(rMob);
                break;

            case 2:
                i(TAG, "•••••••••••••••• SORCIER •••••••••••••••••");

                mobVie = (int) (dataVieMax * 1);
                mobDegatPhy = (int) ( joueur.getDegatPhy( ) * 1);
                mobDegatMag = (int) (joueur.getDegatMag() *  1);
                mobDefPhy = (int) (joueur.getDefPhy() * 1);
                mobDefMag = (int) (joueur.getDefMag() * 1);

                mob = new Monstre (mobVie, mobDegatPhy, mobDegatMag, mobDefPhy, mobDefMag);
                monstreImage.setImageResource(R.drawable.monstre3);
                savedEncounterToSharedPref(rMob);
                break;
        }

        i(TAG, "•••••••••••••••• Mob Vie : " +mobVie+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Mob Degat Phy : " +mobDegatPhy+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Mob Degat Mag : " +mobDegatMag+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Mob Def Phy : " +mobDefPhy+ " •••••••••••••••••");
        i(TAG, "•••••••••••••••• Mob Def Mag : " +mob.defMag+ " •••••••••••••••••");
    }

    /*************************************************************
     * Combat
     *************************************************************/

    private void combat() {

        rollSound();

        Random r2 = new Random( );
        //% 2 = 0 ou 1
        int randomActionCombat = Math.abs(r2.nextInt( )) % 6;

        i(TAG, "•••••••••••••••• Random Attaque Number " + randomActionCombat + " •••••••••••••••••");

        switch (randomActionCombat) {

            case 0:
                i(TAG, "•••••••••••••••• Joueur Attaque Magie •••••••••••••••••");
                mobVie = (mobVie - joueur.getDegatPhy());
                infos.setText("Vous lancez un sort");
                i(TAG, "•••••••••••••••• Degat Infligés : " +joueur.getDegatPhy()+ " •••••••••••••••••");
                i(TAG, "•••••••••••••••• Vie restante au mob: " +mobVie+ " •••••••••••••••••");
                vieCheck();
                break;
            case 1:
                i(TAG, "•••••••••••••••• Joueur Attaque Physique •••••••••••••••••");
                mobVie = (mobVie - joueur.getDegatMag());
                infos.setText("Vous lancez une attaque physique");
                i(TAG, "•••••••••••••••• Degat Infligés : " +joueur.getDegatPhy()+ " •••••••••••••••••");
                i(TAG, "•••••••••••••••• Vie restante au mob: " +mobVie+ " •••••••••••••••••");
                vieCheck();
                break;
            case 2:
                i(TAG, "•••••••••••••••• Mob Attaque Magique •••••••••••••••••");
                joueur.setVie(joueur.getVie() - mobDegatMag);
                infos.setText("Vous subissez des dégats magiques");
                i(TAG, "•••••••••••••••• Degat Infligés : " +mobDegatMag+ " •••••••••••••••••");
                i(TAG, "•••••••••••••••• Vie restante au joueur : " +joueur.getVie()+ " •••••••••••••••••");
                vieCheck();
                break;
            case 3:
                i(TAG, "•••••••••••••••• Mob Attaque Physique •••••••••••••••••");
                joueur.setVie(joueur.getVie() - mobDegatPhy);
                infos.setText("Vous subissez des dégats physiques");
                i(TAG, "•••••••••••••••• Degat Infligés : " +mobDegatPhy+ " •••••••••••••••••");
                i(TAG, "•••••••••••••••• Vie restante au joueur : " +joueur.getVie()+ " •••••••••••••••••");
                vieCheck();
                break;
            case 4:
                i(TAG, "•••••••••••••••• Joueur tente une attaque... c'est un echec ! •••••••••••••••••");
                infos.setText("Vous tentez une attaque... c'est un echec !");
                break;
            case 5:
                i(TAG, "•••••••••••••••• Mob Tente une attaque... c'est un echec ! •••••••••••••••••");
                infos.setText("Le monstre tente une attaque... c'est un echec !");
                break;
        }
    }

    private void vieCheck() {

        i(TAG, "•••••••••••••••• Vie Check •••••••••••••••••");

        if (mobVie < 1 ) {
            i(TAG, "•••••••••••••••• Le monstre est vaincu •••••••••••••••••");
            dataVie = joueur.getVie();
            if (joueur.getVie() < dataVieMax){
                dataVie ++;
            }

            int newOr = (int) ((dataNiv * 0.60) + (dataVie* 0.40));
            dataOr = dataOr + newOr;

            savedLifeToSharedPref(dataVie);
            savedOrToSharedPref(dataOr);

            startActivity(new Intent(CombatActivity.this, ScoreActivity.class));
            finish();

        }

        if (joueur.getVie() < 1) {

            i(TAG, "•••••••••••••••• Vous êtes mort •••••••••••••••••");
            dataVie = 0;
            savedLifeToSharedPref(dataVie);
            startActivity(new Intent(CombatActivity.this, MortActivity.class));
            finish();
        }
    }

    private void savedLifeToSharedPref(int dataVie) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("KEY_VIE", dataVie);
        i(TAG, "•••••••••••••••• Vie du joueur " + dataVie + " •••••••••••••••••");
        editor.apply();
    }

    private void savedOrToSharedPref(int dataOr) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("KEY_OR", dataOr);
        i(TAG, "•••••••••••••••• Or du joueur " + dataOr + " •••••••••••••••••");
        editor.apply();
    }

    private void savedEncounterToSharedPref(int rMob) {
        SharedPreferences sharedPref;
        SharedPreferences.Editor editor;
        sharedPref = getApplicationContext( ).getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putInt("KEY_ENCOUNTER", rMob);
        editor.apply();
    }

    private void rollSound() {
        if (hasSound) {
            mp = new MediaPlayer( );
            mp = MediaPlayer.create(this, R.raw.roll);
            mp.start( );
        }
    }
}

