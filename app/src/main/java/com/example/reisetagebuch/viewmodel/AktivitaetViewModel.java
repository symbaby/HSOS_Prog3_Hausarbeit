/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.reisetagebuch.entitaet.Aktivitaet;
import com.example.reisetagebuch.repository.Repository;

import java.util.List;

public class AktivitaetViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Aktivitaet>> allAktivitaeten;


    public AktivitaetViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allAktivitaeten = repository.getAllAktivitaeten();
    }

    // Wrapper Methoden
    public void insert(Aktivitaet aktivitaet){
        repository.insert(aktivitaet);
    }

    public void update(Aktivitaet aktivitaet){
        repository.update(aktivitaet);
    }

    public void delete(Aktivitaet aktivitaet){
        repository.delete(aktivitaet);
    }

    public void deleteAllAktivitaeten(){
        repository.deleteAllAktivitaeten();
    }

    public LiveData<List<Aktivitaet>> getAllAktivitaeten() {
        return allAktivitaeten;
    }

    public LiveData<List<Aktivitaet>> getSpecificAktivitaeten(int r_id) {
        LiveData<List<Aktivitaet>> specificAktivitaet = repository.getSpecificAktivitaeten(r_id);
        return specificAktivitaet;
    }

    public void deleteSpecificAktivitaeten(int r_id) {
        repository.deleteSpecificAktivitaeten(r_id);
    }
}
