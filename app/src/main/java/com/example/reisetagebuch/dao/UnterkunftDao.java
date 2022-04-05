/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reisetagebuch.entitaet.Unterkunft;

import java.util.List;

@Dao
public interface UnterkunftDao {

    @Insert
    void insert(Unterkunft unterkunft);

    @Delete
    void delete(Unterkunft unterkunft);

    @Update
    void update(Unterkunft unterkunft);

    @Query("DELETE FROM unterkunft_table")
    void deleteAllUnterkuenfte();

    @Query("SELECT * FROM unterkunft_table ORDER BY ID ASC")
    LiveData<List<Unterkunft>> getAllUnterkuenfte();

    @Query("SELECT * FROM unterkunft_table WHERE reiseID == :r_id")
    LiveData<List<Unterkunft>> getSpecificUnterkunft(int r_id);

    @Query("DELETE FROM unterkunft_table WHERE reiseID == :r_id")
    void deleteSpecificUnterkunft(int r_id);
}
