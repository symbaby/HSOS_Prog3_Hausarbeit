/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.entitaet;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "unterkunft_table")
public class Unterkunft {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int bewertung;
    private String land;
    private String stadt;
    private String name;
    private int dauer;
    private int preis;
    private String unterkunftstyp;

    private int reiseID;

    public Unterkunft(int bewertung, String land, String stadt, String name, int dauer, int preis, String unterkunftstyp, int reiseID) {
        this.bewertung = bewertung;
        this.land = land;
        this.stadt = stadt;
        this.name = name;
        this.dauer = dauer;
        this.preis = preis;
        this.unterkunftstyp = unterkunftstyp;
        this.reiseID=reiseID;
    }

    @Ignore
    public Unterkunft(String land, String stadt, String name, int reiseID) {
        this.land = land;
        this.stadt = stadt;
        this.name = name;
        this.reiseID = reiseID;
    }

    @Ignore
    public Unterkunft() {
    }

    public static class UnterkunftsBuilder {
        private Unterkunft unterkunft;

        public UnterkunftsBuilder(String land, String stadt, String name, int reiseID) {
            this.unterkunft = new Unterkunft(land, stadt, name, reiseID);
        }

        public UnterkunftsBuilder typErgaenzen(String unterkunftstyp) {
            this.unterkunft.unterkunftstyp = unterkunftstyp;
            return this;
        }

        public UnterkunftsBuilder dauerSetzen(int dauer) {
            this.unterkunft.dauer = dauer;
            return this;
        }

        public UnterkunftsBuilder bewertungSetzen(int bewertung) {
            this.unterkunft.bewertung = bewertung;
            return this;
        }

        public UnterkunftsBuilder preisSetzen(int preis) {
            this.unterkunft.preis = preis;
            return this;
        }

        public Unterkunft build() {
            return this.unterkunft;
        }
    }

    //Setter fuer Room

    public void setBewertung(int bewertung) {
        this.bewertung = bewertung;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public void setStadt(String stadt) {
        this.stadt = stadt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDauer(int dauer) {
        this.dauer = dauer;
    }

    public void setPreis(int preis) {
        this.preis = preis;
    }

    public void setUnterkunftstyp(String unterkunftstyp) {
        this.unterkunftstyp = unterkunftstyp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setReiseID(int reiseID) {
        this.reiseID = reiseID;
    }

    public int getId() {
        return id;
    }

    public int getBewertung() {
        return bewertung;
    }

    public String getLand() {
        return land;
    }

    public String getStadt() {
        return stadt;
    }

    public String getName() {
        return name;
    }

    public int getDauer() {
        return dauer;
    }

    public int getPreis() {
        return preis;
    }

    public String getUnterkunftstyp() {
        return unterkunftstyp;
    }

    public int getReiseID() {
        return reiseID;
    }
}
