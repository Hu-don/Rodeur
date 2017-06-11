package com.dimsun.game.rodeur;
/**
 * Created by hudon on 03/06/2017.
 */

class Joueur {

    //Variables d'instances
    private int vie, force, defense, esquive, magie, chance, vieMax;
    private boolean estVivant;

    public Joueur(int dataNiv, int dataVie, int dataVieMax, int dataDef, int dataEsq, int dataChan, int dataMagie, int dataOr, int dataForce) {

        this.vie = dataVie;
        this.vieMax = dataVieMax;
        this.force = dataForce;
        this.defense = dataDef;
        this.esquive = dataEsq;
        this.magie = dataMagie;
        this.chance = dataChan;
        this.force = dataForce;

    }

    public int getVie() {

        int value = (int) vie;
        return value;
    }

    public int getDegatPhy() {
        int degatPhy =  (int) (( this.force * 0.70) + ( this.chance * 0.30 ));
        return degatPhy;
    }

    public int getDegatMag() {
        int degatMag = (int) (( this.magie * 0.70 ) + ( this.chance * 0.30 ));
        return degatMag;
    }

    public int getDefPhy() {
        int defPhy = (int) (( this.vie * 0.20 ) + ( this.force * 0.20 ) + ( this.defense * 0.40 ) + (this.esquive * 0.20 )) ;
        return defPhy;
    }

    public int getDefMag() {
        int defMag = (int) (( this.vie * 0.20) + ( this.force * 0.20 ) + ( this.defense * 0.40 ) + ( this.chance * 0.20 ));
        return defMag;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }
}


