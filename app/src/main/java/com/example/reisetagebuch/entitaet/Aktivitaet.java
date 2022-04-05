/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.entitaet;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "aktivitaet_table")
public class Aktivitaet{
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String aktivitaetsTyp;
    private String aktivitaetsBeschreibung;
    private int uhrzeitStunde;
    private int aktivitaetsKosten;
    private int aktivitaetsBewertung;
    private int tagID;

    //Bilder in BITMAP machen!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


    public Aktivitaet() {
    }

    @Ignore
    public Aktivitaet(String aktivitaetsTyp, String aktivitaetsBeschreibung,int uhrzeitStunde, int aktivitaetsKosten, int aktivitaetsBewertung, int tag_id) {
        this.aktivitaetsTyp = aktivitaetsTyp;
        this.aktivitaetsBeschreibung = aktivitaetsBeschreibung;
        this.uhrzeitStunde=uhrzeitStunde;
        this.aktivitaetsKosten = aktivitaetsKosten;
        this.aktivitaetsBewertung=aktivitaetsBewertung;
        this.tagID = tag_id;
    }

    public Aktivitaet(String aktivitaetsTyp, String aktivitaetsBeschreibung) {
        this.aktivitaetsTyp = aktivitaetsTyp;
        this.aktivitaetsBeschreibung = aktivitaetsBeschreibung;
    }


    public static class AktivitaetBuilder {

        private Aktivitaet aktivitaet;

        public AktivitaetBuilder(String aktivitaetsTyp, String aktivitaetsBeschreibung) {
            this.aktivitaet = new Aktivitaet(aktivitaetsTyp, aktivitaetsBeschreibung);
        }

        public Aktivitaet.AktivitaetBuilder uhrzeitSetzen(int uhrzeit) {
            this.aktivitaet.uhrzeitStunde = uhrzeit;
            return this;
        }

        public Aktivitaet.AktivitaetBuilder kostenSetzen(int kosten) {
            this.aktivitaet.aktivitaetsKosten = kosten;
            return this;
        }

        public Aktivitaet.AktivitaetBuilder bewertungSetzen(int bewertung) {
            this.aktivitaet.aktivitaetsBewertung = bewertung;
            return this;
        }

        public Aktivitaet.AktivitaetBuilder tagIdSetzen(int t_id) {
            this.aktivitaet.tagID = t_id;
            return this;
        }

        public Aktivitaet build() {
            return this.aktivitaet;
        }

    }


    //Setter fuer Room
    public void setId(int id) {
        this.id = id;
    }

    public void setAktivitaetsTyp(String aktivitaetsTyp) {
        this.aktivitaetsTyp = aktivitaetsTyp;
    }

    public void setAktivitaetsBeschreibung(String aktivitaetsBeschreibung) {
        this.aktivitaetsBeschreibung = aktivitaetsBeschreibung;
    }

    public void setUhrzeitStunde(int uhrzeitStunde) {
        this.uhrzeitStunde = uhrzeitStunde;
    }

    public void setAktivitaetsKosten(int aktivitaetsKosten) {
        this.aktivitaetsKosten = aktivitaetsKosten;
    }

    public void setAktivitaetsBewertung(int aktivitaetsBewertung) {
        this.aktivitaetsBewertung = aktivitaetsBewertung;
    }

    public void setTagID(int tagID) {
        this.tagID = tagID;
    }

    public int getId() {
        return id;
    }

    public String getAktivitaetsTyp() {
        return aktivitaetsTyp;
    }

    public String getAktivitaetsBeschreibung() {
        return aktivitaetsBeschreibung;
    }

    public int getUhrzeitStunde() {
        return uhrzeitStunde;
    }

    public int getAktivitaetsKosten() {
        return aktivitaetsKosten;
    }

    public int getAktivitaetsBewertung() {
        return aktivitaetsBewertung;
    }

    public int getTagID() {
        return tagID;
    }

};


