/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.reisetagebuch.entitaet.Tag;

import java.util.List;

@Dao
public interface TagDao {

    @Insert
    void insert(Tag tag);

    @Delete
    void delete(Tag tag);

    @Update
    void update(Tag tag);

    @Query("DELETE FROM tag_table")
    void deleteAllTag();

    @Query("SELECT * FROM tag_table ORDER BY ID ASC")
    LiveData<List<Tag>> getAllTage();

    @Query("SELECT * FROM tag_table WHERE reiseID == :r_id ORDER BY datumTag ASC, datumMonat, datumJahr")
    LiveData<List<Tag>> getSpecificTag(int r_id);

    @Query("DELETE FROM tag_table WHERE reiseID == :r_id")
    void deleteSpecificTag(int r_id);

}
