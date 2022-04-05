/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reisetagebuch.entitaet.Reisemittel;

import java.util.List;

@Dao
public interface ReisemittelDao {

    @Insert
    void insert(Reisemittel reisemittel);

    @Delete
    void delete(Reisemittel reisemittel);

    @Update
    void update(Reisemittel reisemittel);

    @Query("DELETE FROM reisemittel_table")
    void deleteAllReisemittel();

    @Query("SELECT * FROM reisemittel_table ORDER BY ID ASC")
    LiveData<List<Reisemittel>> getAllReisemittel();

    @Query("SELECT * FROM reisemittel_table WHERE reiseID == :r_id")
    LiveData<List<Reisemittel>> getSpecifictReisemittel(int r_id);

    @Query("DELETE FROM reisemittel_table WHERE reiseID == :r_id")
    void deleteSpecificReisemittel(int r_id);


}
