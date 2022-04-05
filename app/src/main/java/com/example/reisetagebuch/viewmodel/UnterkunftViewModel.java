/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.reisetagebuch.entitaet.Unterkunft;
import com.example.reisetagebuch.repository.Repository;

import java.util.List;

public class UnterkunftViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Unterkunft>> allUnterkuenfte;

    public UnterkunftViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allUnterkuenfte = repository.getAllUnterkuenfte();
    }

    // Wrapper Methoden
    public void insert(Unterkunft unterkunft) {
        repository.insert(unterkunft);
    }

    public void update(Unterkunft unterkunft) {
        repository.update(unterkunft);
    }

    public void delete(Unterkunft unterkunft) {
        repository.delete(unterkunft);
    }

    public void deleteAllUnterkuenfte() {
        repository.deleteAllUnterkuenfte();
    }

    public LiveData<List<Unterkunft>> getAllUnterkuenfte() {
        return allUnterkuenfte;
    }

    public LiveData<List<Unterkunft>> getSpecificUnterkunft(int r_id) {
        LiveData<List<Unterkunft>> specificUnterkunft = repository.getSpecificUnterkunft(r_id);
        return specificUnterkunft;
    }

    public void deleteSpecificUnterkunft(int r_id) {
        repository.deleteSpecificUnterkunft(r_id);
    }
}
