package com.dimsun.game.rodeur;


/**
 * Created by hudon on 03/06/2017.
 */

class Monstre {

    int vie;
    float degatPhy, degatMag, defPhy, defMag;
    boolean estVivant;

    public Monstre(int mobVie, float mobDegatPhy, float mobDegatMag, float mobDefPhy, float mobDefMag) {

    //Variables d'instances
        int vie, force, def, esq, mag, chan;
        boolean estVivant;

    }


    public int getVie() {
        return vie;
    }

    public int getDegatPhy() {
        int value = (int) degatPhy;
        return value;
    }

    public int getDegatMag() {
        int value = (int) degatMag;
        return value;
    }

    public int getDefPhy() {
        int value = (int) defPhy;
        return value;
    }

    public int getDefMag() {
        int value = (int) defMag;
        return value;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

}
