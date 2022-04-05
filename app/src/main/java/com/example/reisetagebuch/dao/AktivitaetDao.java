/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reisetagebuch.entitaet.Aktivitaet;


import java.util.List;

@Dao
public interface AktivitaetDao {
    @Insert
    void insert(Aktivitaet aktivitaet);

    @Delete
    void delete(Aktivitaet aktivitaet);

    @Update
    void update(Aktivitaet aktivitaet);

    @Query("DELETE FROM aktivitaet_table")
    void deleteAllAktivitaeten();

    @Query("SELECT * FROM aktivitaet_table ORDER BY ID ASC")
    LiveData<List<Aktivitaet>> getAllAktivitaeten();

    @Query("SELECT * FROM aktivitaet_table WHERE tagID == :r_id ORDER BY uhrzeitStunde ASC")
    LiveData<List<Aktivitaet>> getSpecificAktivitaeten(int r_id);

    @Query("DELETE FROM aktivitaet_table WHERE tagID == :r_id")
    void deleteSpecificAktivitaeten(int r_id);
}
