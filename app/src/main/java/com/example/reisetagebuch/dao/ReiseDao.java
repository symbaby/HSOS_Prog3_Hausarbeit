/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reisetagebuch.entitaet.Reise;

import java.util.List;

@Dao
public interface ReiseDao {
    @Insert
    void insert(Reise reise);

    @Delete
    void delete(Reise reise);

    @Update
    void update(Reise reise);

    @Query("DELETE FROM reise_table")
    void deleteAllReisen();

    @Query("SELECT * FROM reise_table ORDER BY ID ASC")
    LiveData<List<Reise>> getAllReisen();

}
