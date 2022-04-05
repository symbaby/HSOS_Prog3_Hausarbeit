/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.entitaet;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "tag_table")
public class Tag {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int reiseID;
    private int datumTag;
    private int datumMonat;
    private int datumJahr;

    public Tag() {
    }

    public Tag(int tag, int monat, int jahr, int reiseID){
        this.datumTag=tag;
        this.datumMonat=monat;
        this.datumJahr=jahr;
        this.reiseID=reiseID;
    }


    //Setter fuer Room
    public void setId(int id) {
        this.id = id;
    }

    public void setReiseID(int reiseID) {
        this.reiseID = reiseID;
    }

    public void setDatumTag(int datumTag) {
        this.datumTag = datumTag;
    }

    public void setDatumMonat(int datumMonat) {
        this.datumMonat = datumMonat;
    }

    public void setDatumJahr(int datumJahr) {
        this.datumJahr = datumJahr;
    }

    public int getId() {
        return id;
    }

    public int getReiseID() {
        return reiseID;
    }

    public int getDatumTag() {
        return datumTag;
    }

    public int getDatumMonat() {
        return datumMonat;
    }

    public int getDatumJahr() {
        return datumJahr;
    }
}
