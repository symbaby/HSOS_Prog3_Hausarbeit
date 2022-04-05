/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reisetagebuch.entitaet.Reise;
import com.example.reisetagebuch.repository.Repository;

import java.util.List;

public class ReiseViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Reise>> allReise;

    public ReiseViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        allReise = repository.getAllReise();
    }

    // Wrapper Methoden
    public void insert(Reise reise) {
        repository.insert(reise);
    }

    public void update(Reise reise) {
        repository.update(reise);
    }

    public void delete(Reise reise) {
        repository.delete(reise);
    }

    public void deleteAllReisen() {
        repository.deleteAllReisen();
    }


    public LiveData<List<Reise>> getAllReisen() {
        return allReise;
    }

}
