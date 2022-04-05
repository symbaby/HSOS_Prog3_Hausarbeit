/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.beziehungen;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.reisetagebuch.entitaet.Aktivitaet;
import com.example.reisetagebuch.entitaet.Tag;

import java.util.List;

public class TagMitAktivitaet {
    @Embedded
    private Tag tag;

    @Relation(parentColumn = "id", entityColumn = "tagID", entity = Aktivitaet.class)
    private List<Aktivitaet> aktivitatens;

}
