/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.entitaet;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reisemittel_table")
public class Reisemittel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int kosten;
    private String reisemitteltyp;

    private int reiseID;

    public Reisemittel(int kosten, String reisemitteltyp, int reiseID) {
        this.kosten = kosten;
        this.reisemitteltyp = reisemitteltyp;
        this.reiseID = reiseID;
    }

    //Setter fuer Room
    public void setId(int id) {
        this.id = id;
    }

    public void setKosten(int kosten) {
        this.kosten = kosten;
    }

    public void setReisemitteltyp(String reisemitteltyp) {
        this.reisemitteltyp = reisemitteltyp;
    }

    public void setReiseID(int reiseID) {
        this.reiseID = reiseID;
    }


    public int getId() {
        return id;
    }

    public int getKosten() {
        return kosten;
    }

    public String getReisemitteltyp() {
        return reisemitteltyp;
    }

    public int getReiseID() {
        return reiseID;
    }
}
