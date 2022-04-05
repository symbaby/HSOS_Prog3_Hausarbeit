/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reisetagebuch.entitaet.Reisemittel;
import com.example.reisetagebuch.repository.Repository;

import java.util.List;

public class ReisemittelViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Reisemittel>> allReisemittel;
    // private LiveData<List<Reisemittel>> specificReisemittel;

    public ReisemittelViewModel(@NonNull Application application) {
        super(application);

        repository = new Repository(application);
        allReisemittel = repository.getAllReisemittel();
    }

    // Wrapper Methoden
    public void insert(Reisemittel reisemittel) {
        repository.insert(reisemittel);
    }

    public void update(Reisemittel reisemittel) {
        repository.update(reisemittel);
    }

    public void delete(Reisemittel reisemittel) {
        repository.delete(reisemittel);
    }

    public void deleteAllReisemittel() {
        repository.deleteAllReisemittel();
    }

    public LiveData<List<Reisemittel>> getAllReisemittel() {

        return allReisemittel;
    }

    public LiveData<List<Reisemittel>> getSpecificReisemittel(int r_id) {
        LiveData<List<Reisemittel>> specificReisemittel = repository.getSpecificReisemittel(r_id);
        return specificReisemittel;
    }

    public void deleteSpecificReisemittel(int r_id) {
        repository.deleteSpecificReisemittel(r_id);
    }
}
