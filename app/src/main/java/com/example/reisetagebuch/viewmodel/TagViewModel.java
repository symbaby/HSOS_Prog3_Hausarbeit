/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reisetagebuch.entitaet.Tag;
import com.example.reisetagebuch.repository.Repository;

import java.util.List;

public class TagViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Tag>> allTage;

    public TagViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allTage = repository.getAllTage();
    }

    // Wrapper Methoden
    public void insert(Tag tag) {
        repository.insert(tag);
    }

    public void update(Tag tag) {
        repository.update(tag);
    }

    public void delete(Tag tag) {
        repository.delete(tag);
    }

    public void deleteAllTage() {
        repository.deleteAllTage();
    }

    public LiveData<List<Tag>> getAllTage() {
        return allTage;
    }

    public LiveData<List<Tag>> getSpecificTag(int r_id) {
        LiveData<List<Tag>> specificTag = repository.getSpecificTag(r_id);
        return specificTag;
    }

    public void deleteSpecificTag(int r_id) {
        repository.deleteSpecificTag(r_id);
    }
}
