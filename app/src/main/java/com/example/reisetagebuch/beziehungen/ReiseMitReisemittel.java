/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.beziehungen;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.reisetagebuch.entitaet.Reise;
import com.example.reisetagebuch.entitaet.Reisemittel;

import java.util.List;


public class ReiseMitReisemittel {
    @Embedded
    private Reise reise;

    @Relation(parentColumn = "id", entityColumn = "reiseID", entity = Reisemittel.class)
    private List<Reisemittel> reisemittels;

    public Reise getReise() {
        return reise;
    }

    public List<Reisemittel> getReisemittels() {
        return reisemittels;
    }
}
