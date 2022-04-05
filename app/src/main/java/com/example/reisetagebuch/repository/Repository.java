/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.reisetagebuch.entitaet.Aktivitaet;
import com.example.reisetagebuch.entitaet.Reise;
import com.example.reisetagebuch.entitaet.Reisemittel;
import com.example.reisetagebuch.entitaet.Tag;
import com.example.reisetagebuch.entitaet.Unterkunft;
import com.example.reisetagebuch.dao.AktivitaetDao;
import com.example.reisetagebuch.dao.ReiseDao;
import com.example.reisetagebuch.dao.ReisemittelDao;
import com.example.reisetagebuch.dao.TagDao;
import com.example.reisetagebuch.dao.UnterkunftDao;
import com.example.reisetagebuch.datenbank.Datenbank;

import java.util.List;

public class Repository {
    private AktivitaetDao aktivitaetDao;
    private TagDao tagDao;
    private ReisemittelDao reisemittelDao;
    private UnterkunftDao unterkunftDao;
    private ReiseDao reiseDao;



    private LiveData<List<Aktivitaet>> allAktivitaeten;
    private LiveData<List<Tag>> allTage;
    private LiveData<List<Reisemittel>> allReisemittel;
    private LiveData<List<Unterkunft>> allUnterkuenfte;
    private LiveData<List<Reise>> allReise;


    public Repository(Application application) {

        Datenbank datenbank = Datenbank.getInstance(application);

        aktivitaetDao = datenbank.aktivitaetDao();
        tagDao = datenbank.tagDao();
        reisemittelDao = datenbank.reisemittelDao();
        unterkunftDao = datenbank.unterkunftDao();
        reiseDao = datenbank.reiseDao();

        allAktivitaeten = aktivitaetDao.getAllAktivitaeten();
        allTage = tagDao.getAllTage();
        allReisemittel = reisemittelDao.getAllReisemittel();
        allUnterkuenfte = unterkunftDao.getAllUnterkuenfte();
        allReise = reiseDao.getAllReisen();

    }


    // SQL AKTIVITÄT

    public void insert(Aktivitaet aktivitaet) {
        new InsertAktivitaetAsynchTask(aktivitaetDao).execute(aktivitaet);
    }

    public void update(Aktivitaet aktivitaet) {
        new UpdateAktivitaetAsynchTask(aktivitaetDao).execute(aktivitaet);
    }

    public void delete(Aktivitaet aktivitaet) {
        new DeleteAktivitaetAsynchTask(aktivitaetDao).execute(aktivitaet);
    }

    public void deleteAllAktivitaeten() {
        new DeleteAllAktivitaetenAsynchTask(aktivitaetDao).execute();
    }

    public LiveData<List<Aktivitaet>> getAllAktivitaeten() {
        return allAktivitaeten;
    }

    public LiveData<List<Aktivitaet>> getSpecificAktivitaeten(int r_id) {
        allAktivitaeten = aktivitaetDao.getSpecificAktivitaeten(r_id);
        return allAktivitaeten;
    }

    public void deleteSpecificAktivitaeten(int r_id) {
        new DeleteSpecificAktivitaetAsynchTask(aktivitaetDao, r_id).execute();
    }

    private static class DeleteSpecificAktivitaetAsynchTask extends AsyncTask<Aktivitaet,Void, Void> {
        private AktivitaetDao aktivitaetDao;
        private int r_id;

        private DeleteSpecificAktivitaetAsynchTask(AktivitaetDao aktivitaetDao, int r_id) {
            this.aktivitaetDao = aktivitaetDao;
            this.r_id=r_id;
        }

        @Override
        protected Void doInBackground(Aktivitaet... aktivitaets) {
            aktivitaetDao.deleteSpecificAktivitaeten(r_id);
            return null;
        }
    }

    private static class InsertAktivitaetAsynchTask extends AsyncTask<Aktivitaet, Void, Void> {
        private AktivitaetDao aktivitaetDao;

        private InsertAktivitaetAsynchTask(AktivitaetDao aktivitaetDao) {
            this.aktivitaetDao = aktivitaetDao;
        }

        @Override
        protected Void doInBackground(Aktivitaet... aktivitaets) {
            aktivitaetDao.insert(aktivitaets[0]);
            return null;
        }
    }

    private static class UpdateAktivitaetAsynchTask extends AsyncTask<Aktivitaet, Void, Void> {
        private AktivitaetDao aktivitaetDao;

        private UpdateAktivitaetAsynchTask(AktivitaetDao aktivitaetDao) {
            this.aktivitaetDao = aktivitaetDao;
        }

        @Override
        protected Void doInBackground(Aktivitaet... aktivitaets) {
            aktivitaetDao.update(aktivitaets[0]);
            return null;
        }
    }

    private static class DeleteAktivitaetAsynchTask extends AsyncTask<Aktivitaet, Void, Void> {
        private AktivitaetDao aktivitaetDao;

        private DeleteAktivitaetAsynchTask(AktivitaetDao aktivitaetDao) {
            this.aktivitaetDao = aktivitaetDao;
        }

        @Override
        protected Void doInBackground(Aktivitaet... aktivitaets) {
            aktivitaetDao.delete(aktivitaets[0]);
            return null;
        }
    }

    private static class DeleteAllAktivitaetenAsynchTask extends AsyncTask<Void, Void, Void> {
        private AktivitaetDao aktivitaetDao;

        private DeleteAllAktivitaetenAsynchTask(AktivitaetDao aktivitaetDao) {
            this.aktivitaetDao = aktivitaetDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            aktivitaetDao.deleteAllAktivitaeten();
            return null;
        }
    }


    // SQLTAG

    public void insert(Tag tag) {
        new InsertTagAsynchTask(tagDao).execute(tag);
    }

    public void update(Tag tag) {
        new UpdateTagAsynchTask(tagDao).execute(tag);
    }

    public void delete(Tag tag) {
        new DeleteTagAsynchTask(tagDao).execute(tag);
    }

    public void deleteAllTage() {
        new DeleteAllTagAsynchTask(tagDao).execute();
    }

    public LiveData<List<Tag>> getAllTage() {
        return allTage;
    }

    public LiveData<List<Tag>> getSpecificTag(int r_id) {
        allTage = tagDao.getSpecificTag(r_id);
        return allTage;
    }

    public void deleteSpecificTag(int r_id) {
        new DeleteSpecificTagAsynchTask(tagDao, r_id).execute();
    }

    private static class DeleteSpecificTagAsynchTask extends AsyncTask<Tag,Void, Void> {
        private TagDao tagDao;
        private int r_id;

        private DeleteSpecificTagAsynchTask(TagDao tagDao, int r_id) {
            this.tagDao = tagDao;
            this.r_id=r_id;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.deleteSpecificTag(r_id);
            return null;
        }
    }

    private static class InsertTagAsynchTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private InsertTagAsynchTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.insert(tags[0]);
            return null;
        }
    }

    private static class UpdateTagAsynchTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private UpdateTagAsynchTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.update(tags[0]);
            return null;
        }
    }

    private static class DeleteTagAsynchTask extends AsyncTask<Tag, Void, Void> {
        private TagDao tagDao;

        private DeleteTagAsynchTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Tag... tags) {
            tagDao.delete(tags[0]);
            return null;
        }
    }

    private static class DeleteAllTagAsynchTask extends AsyncTask<Void, Void, Void> {
        private TagDao tagDao;

        private DeleteAllTagAsynchTask(TagDao tagDao) {
            this.tagDao = tagDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            tagDao.deleteAllTag();
            return null;
        }
    }


    // SQL REISEMITTEL

    public void insert(Reisemittel reisemittel) {
        new InsertReisemittelAsynchTask(reisemittelDao).execute(reisemittel);
    }

    public void update(Reisemittel reisemittel) {
        new UpdateReisemittelAsynchTask(reisemittelDao).execute(reisemittel);
    }

    public void delete(Reisemittel reisemittel) {
        new DeleteReisemittelAsynchTask(reisemittelDao).execute(reisemittel);
    }

    public void deleteAllReisemittel() {
        new DeleteAllReisemittelAsynchTask(reisemittelDao).execute();
    }

    public LiveData<List<Reisemittel>> getAllReisemittel() {
        return allReisemittel;
    }

    // Hier zum testen erstmal ne ID uebergeben, falls whack dann rausnhemen

    public LiveData<List<Reisemittel>> getSpecificReisemittel(int r_id) {
        allReisemittel = reisemittelDao.getSpecifictReisemittel(r_id);
        return allReisemittel;
    }

    public void deleteSpecificReisemittel(int r_id) {
        new DeleteSpecificReisemittelAsynchTask(reisemittelDao, r_id).execute();
    }

    private static class DeleteSpecificReisemittelAsynchTask extends AsyncTask<Reisemittel,Void, Void> {
        private ReisemittelDao reisemittelDao;
        private int r_id;

        private DeleteSpecificReisemittelAsynchTask(ReisemittelDao reisemittelDao, int r_id) {
            this.reisemittelDao = reisemittelDao;
            this.r_id=r_id;
        }

        @Override
        protected Void doInBackground(Reisemittel... reisemittels) {
            reisemittelDao.deleteSpecificReisemittel(r_id);
            return null;
        }
    }

    private static class InsertReisemittelAsynchTask extends AsyncTask<Reisemittel, Void, Void> {
        private ReisemittelDao reisemittelDao;

        private InsertReisemittelAsynchTask(ReisemittelDao reisemittelDao) {
            this.reisemittelDao = reisemittelDao;
        }

        @Override
        protected Void doInBackground(Reisemittel... reisemittels) {
            reisemittelDao.insert(reisemittels[0]);
            return null;
        }
    }

    private static class UpdateReisemittelAsynchTask extends AsyncTask<Reisemittel, Void, Void> {
        private ReisemittelDao reisemittelDao;

        private UpdateReisemittelAsynchTask(ReisemittelDao reisemittelDao) {
            this.reisemittelDao = reisemittelDao;
        }

        @Override
        protected Void doInBackground(Reisemittel... reisemittels) {
            reisemittelDao.update(reisemittels[0]);
            return null;
        }
    }

    private static class DeleteReisemittelAsynchTask extends AsyncTask<Reisemittel, Void, Void> {
        private ReisemittelDao reisemittelDao;

        private DeleteReisemittelAsynchTask(ReisemittelDao reisemittelDao) {
            this.reisemittelDao = reisemittelDao;
        }

        @Override
        protected Void doInBackground(Reisemittel... reisemittels) {
            reisemittelDao.delete(reisemittels[0]);
            return null;
        }
    }

    private static class DeleteAllReisemittelAsynchTask extends AsyncTask<Void, Void, Void> {
        private ReisemittelDao reisemittelDao;

        private DeleteAllReisemittelAsynchTask(ReisemittelDao reisemittelDao) {
            this.reisemittelDao = reisemittelDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            reisemittelDao.deleteAllReisemittel();
            return null;
        }
    }


    // SQL UNTERKUNFT

    public void insert(Unterkunft unterkunft) {
        new InsertUnterkunftAsynchTask(unterkunftDao).execute(unterkunft);
    }

    public void update(Unterkunft unterkunft) {
        new UpdateUnterkunftAsynchTask(unterkunftDao).execute(unterkunft);
    }

    public void delete(Unterkunft unterkunft) {
        new DeleteUnterkunftAsynchTask(unterkunftDao).execute(unterkunft);
    }

    public void deleteAllUnterkuenfte() {
        new DeleteAllUnterkuenfteAsynchTask(unterkunftDao).execute();
    }

    public LiveData<List<Unterkunft>> getSpecificUnterkunft(int r_id) {
        allUnterkuenfte = unterkunftDao.getSpecificUnterkunft(r_id);
        return allUnterkuenfte;
    }

    public LiveData<List<Unterkunft>> getAllUnterkuenfte() {
        return allUnterkuenfte;
    }

    public void deleteSpecificUnterkunft(int r_id) {
        new DeleteSpecificUnterkunftAsynchTask(unterkunftDao, r_id).execute();
    }

    private static class DeleteSpecificUnterkunftAsynchTask extends AsyncTask<Unterkunft,Void, Void> {
        private UnterkunftDao unterkunftDao;
        private int r_id;

        private DeleteSpecificUnterkunftAsynchTask(UnterkunftDao unterkunftDao, int r_id) {
            this.unterkunftDao = unterkunftDao;
            this.r_id=r_id;
        }

        @Override
        protected Void doInBackground(Unterkunft... unterkunfts) {
            unterkunftDao.deleteSpecificUnterkunft(r_id);
            return null;
        }
    }

    private static class InsertUnterkunftAsynchTask extends AsyncTask<Unterkunft, Void, Void> {
        private UnterkunftDao unterkunftDao;

        private InsertUnterkunftAsynchTask(UnterkunftDao unterkunftDao) {
            this.unterkunftDao = unterkunftDao;
        }

        @Override
        protected Void doInBackground(Unterkunft... unterkunfts) {
            unterkunftDao.insert(unterkunfts[0]);
            return null;
        }
    }

    private static class UpdateUnterkunftAsynchTask extends AsyncTask<Unterkunft, Void, Void> {
        private UnterkunftDao unterkunftDao;

        private UpdateUnterkunftAsynchTask(UnterkunftDao unterkunftDao) {
            this.unterkunftDao = unterkunftDao;
        }

        @Override
        protected Void doInBackground(Unterkunft... unterkunfts) {
            unterkunftDao.update(unterkunfts[0]);
            return null;
        }
    }

    private static class DeleteUnterkunftAsynchTask extends AsyncTask<Unterkunft, Void, Void> {
        private UnterkunftDao unterkunftDao;

        private DeleteUnterkunftAsynchTask(UnterkunftDao unterkunftDao) {
            this.unterkunftDao = unterkunftDao;
        }

        @Override
        protected Void doInBackground(Unterkunft... unterkunfts) {
            unterkunftDao.delete(unterkunfts[0]);
            return null;
        }
    }

    private static class DeleteAllUnterkuenfteAsynchTask extends AsyncTask<Void, Void, Void> {
        private UnterkunftDao unterkunftDao;

        private DeleteAllUnterkuenfteAsynchTask(UnterkunftDao unterkunftDao) {
            this.unterkunftDao = unterkunftDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            unterkunftDao.deleteAllUnterkuenfte();
            return null;
        }
    }


    //SQL Reise

    public void insert(Reise reise) {
        new InsertReiseAsynchTask(reiseDao).execute(reise);
    }

    public void update(Reise reise) {
        new UpdateReiseAsynchTask(reiseDao).execute(reise);
    }

    public void delete(Reise reise) {
        new DeleteReiseAsynchTask(reiseDao).execute(reise);
    }

    public void deleteAllReisen() {
        new DeleteAllReisenAsynchTask(reiseDao).execute();
    }


    public LiveData<List<Reise>> getAllReise() {
        return allReise;
    }

    private static class InsertReiseAsynchTask extends AsyncTask<Reise, Void, Void> {
        private ReiseDao reiseDao;

        private InsertReiseAsynchTask(ReiseDao reiseDao) {
            this.reiseDao = reiseDao;
        }

        @Override
        protected Void doInBackground(Reise... reises) {
            reiseDao.insert(reises[0]);
            return null;
        }
    }

    private static class UpdateReiseAsynchTask extends AsyncTask<Reise, Void, Void> {
        private ReiseDao reiseDao;

        private UpdateReiseAsynchTask(ReiseDao reiseDao) {
            this.reiseDao = reiseDao;
        }

        @Override
        protected Void doInBackground(Reise... reises) {
            reiseDao.update(reises[0]);
            return null;
        }
    }

    private static class DeleteReiseAsynchTask extends AsyncTask<Reise, Void, Void> {
        private ReiseDao reiseDao;

        private DeleteReiseAsynchTask(ReiseDao reiseDao) {
            this.reiseDao = reiseDao;
        }

        @Override
        protected Void doInBackground(Reise... reises) {
            reiseDao.delete(reises[0]);
            return null;
        }
    }

    private static class DeleteAllReisenAsynchTask extends AsyncTask<Void, Void, Void> {
        private ReiseDao reiseDao;

        private DeleteAllReisenAsynchTask(ReiseDao reiseDao) {
            this.reiseDao = reiseDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            reiseDao.deleteAllReisen();
            return null;
        }
    }


}
