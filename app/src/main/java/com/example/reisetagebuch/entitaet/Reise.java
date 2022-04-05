/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.entitaet;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "reise_table")
public class Reise {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String reiseName;
    private int anzahlReisende;
    private int reiseBewertung;
    private int reiseDauer;
    private int reiseKosten;

    private String land;
    private String stadt;
    private String provinz;
    private String kontinent;
    private String reisetyp;

    private int anreiseTag;
    private int anreiseMonat;
    private int anreiseJahr;

    private int abreiseTag;
    private int abreiseMonat;
    private int abreiseJahr;

    private int visumKosten;

    private Bitmap bild;

    //private Reisetyp reisetyp;
    // private Kontinent kontinent;


    //Default Konstruktor
    @Ignore
    public Reise() {
    }

    //Konstruktor fuer Room

    public Reise(String reiseName, int anzahlReisende, int reiseBewertung, int reiseDauer, int reiseKosten, String land, String stadt, String provinz, String kontinent, int anreiseTag, int anreiseMonat, int anreiseJahr, int abreiseTag, int abreiseMonat, int abreiseJahr, int visumKosten, String reisetyp) {
        this.reiseName = reiseName;
        this.anzahlReisende = anzahlReisende;
        this.reiseBewertung = reiseBewertung;
        this.reiseDauer = reiseDauer;
        this.reiseKosten = reiseKosten;
        this.land = land;
        this.stadt = stadt;
        this.provinz = provinz;
        this.kontinent = kontinent;
        this.anreiseTag = anreiseTag;
        this.anreiseMonat = anreiseMonat;
        this.anreiseJahr = anreiseJahr;
        this.abreiseTag = abreiseTag;
        this.abreiseMonat = abreiseMonat;
        this.abreiseJahr = abreiseJahr;
        this.visumKosten = visumKosten;
        this.reisetyp = reisetyp;
    }

    //Konstruktor für initial Builder
    @Ignore
    public Reise(String reiseName, int reiseDauer) {
        this.reiseName = reiseName;
        this.reiseDauer = reiseDauer;
    }


    public static class ReiseBuilder {

        private Reise reise;

        public ReiseBuilder(String reiseName, int reiseDauer) {
            this.reise = new Reise(reiseName, reiseDauer);
        }

        public ReiseBuilder anzahlReisenderSetzen(int anzahl) {
            this.reise.anzahlReisende = anzahl;
            return this;
        }

        public ReiseBuilder bewertungSetzen(int bewertung) {
            this.reise.reiseBewertung = bewertung;
            return this;
        }

        public ReiseBuilder kostenSetzen(int kosten) {
            this.reise.reiseKosten = kosten;
            return this;
        }

        public ReiseBuilder ortSetzen(String kontinent, String land, String provinz, String stadt) {
            this.reise.kontinent = kontinent;
            this.reise.land = land;
            this.reise.provinz = provinz;
            this.reise.stadt = stadt;
            return this;
        }

        public ReiseBuilder anreiseDatumSetzen(int tag, int monat, int jahr) {
            this.reise.anreiseTag = tag;
            this.reise.anreiseMonat = monat;
            this.reise.anreiseJahr = jahr;
            return this;
        }

        public ReiseBuilder abreiseDatumSetzen(int tag, int monat, int jahr) {
            this.reise.abreiseTag = tag;
            this.reise.abreiseMonat = monat;
            this.reise.abreiseJahr = jahr;
            return this;
        }

        public ReiseBuilder visumKostenSetzen(int kosten) {
            this.reise.visumKosten = kosten;
            return this;
        }


        public ReiseBuilder reiseTypSetzen(String typ) {
            this.reise.reisetyp = typ;
            return this;
        }


        public ReiseBuilder bitmapSetzen(Bitmap bitmap) {
            this.reise.bild = bitmap;
            return this;
        }

        public Reise build() {
            return this.reise;
        }
    }

    //Setter für Room fuer die ID
    public void setId(int id) {
        this.id = id;
    }

    public void setReiseName(String reiseName) {
        this.reiseName = reiseName;
    }

    public void setAnzahlReisende(int anzahlReisende) {
        this.anzahlReisende = anzahlReisende;
    }

    public void setReiseBewertung(int reiseBewertung) {
        this.reiseBewertung = reiseBewertung;
    }

    public void setReiseDauer(int reiseDauer) {
        this.reiseDauer = reiseDauer;
    }

    public void setReiseKosten(int reiseKosten) {
        this.reiseKosten = reiseKosten;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public void setProvinz(String provinz) {
        this.provinz = provinz;
    }


   /* public void setKontinent(Kontinent kontinent) {
        this.kontinent = kontinent;
    }*/

    public void setAnreiseTag(int anreiseTag) {
        this.anreiseTag = anreiseTag;
    }

    public void setAnreiseMonat(int anreiseMonat) {
        this.anreiseMonat = anreiseMonat;
    }

    public void setAnreiseJahr(int anreiseJahr) {
        this.anreiseJahr = anreiseJahr;
    }

    public void setAbreiseTag(int abreiseTag) {
        this.abreiseTag = abreiseTag;
    }

    public void setAbreiseMonat(int abreiseMonat) {
        this.abreiseMonat = abreiseMonat;
    }

    public void setAbreiseJahr(int abreiseJahr) {
        this.abreiseJahr = abreiseJahr;
    }

    public void setVisumKosten(int visumKosten) {
        this.visumKosten = visumKosten;
    }

    public void setKontinent(String kontinent) {
        this.kontinent = kontinent;
    }

    public void setReisetyp(String reisetyp) {
        this.reisetyp = reisetyp;
    }

    public void setBild(Bitmap bild) {
        this.bild = bild;
    }

    /*
    public void setReisetyp(Reisetyp reisetyp) {
        this.reisetyp = reisetyp;
    }*/

    public String getReiseName() {
        return reiseName;
    }

    public int getId() {
        return id;
    }

    public int getAnzahlReisende() {
        return anzahlReisende;
    }

    public int getReiseBewertung() {
        return reiseBewertung;
    }

    public int getReiseDauer() {
        return reiseDauer;
    }

    public int getReiseKosten() {
        return reiseKosten;
    }

    public String getLand() {
        return land;
    }

    public String getStadt() {
        return stadt;
    }

    public String getProvinz() {
        return provinz;
    }

    /*
    public Kontinent getKontinent() {
        return kontinent;
    }*/

    public String getKontinent() {
        return kontinent;
    }

    public String getReisetyp() {
        return reisetyp;
    }

    public int getAnreiseTag() {
        return anreiseTag;
    }

    public int getAnreiseMonat() {
        return anreiseMonat;
    }

    public int getAnreiseJahr() {
        return anreiseJahr;
    }

    public int getAbreiseTag() {
        return abreiseTag;
    }

    public int getAbreiseMonat() {
        return abreiseMonat;
    }

    public int getAbreiseJahr() {
        return abreiseJahr;
    }

    public int getVisumKosten() {
        return visumKosten;
    }


    public Bitmap getBild() {
        return bild;
    }

    /*
    public Reisetyp getReisetyp() {
        return reisetyp;
    }*/
}
