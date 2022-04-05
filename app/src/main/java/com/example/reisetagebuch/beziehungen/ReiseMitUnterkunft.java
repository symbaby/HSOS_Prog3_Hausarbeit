/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.beziehungen;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.reisetagebuch.entitaet.Reise;
import com.example.reisetagebuch.entitaet.Unterkunft;

import java.util.List;

public class ReiseMitUnterkunft {

    @Embedded
    private Reise reise;

    @Relation(parentColumn = "id", entityColumn = "reiseID", entity = Unterkunft.class)
    private List<Unterkunft> unterkunfts;

}
