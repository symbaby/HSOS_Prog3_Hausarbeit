/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.datenbank;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.reisetagebuch.converter.BildConverter;
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

@Database(entities = {Aktivitaet.class, Tag.class, Reisemittel.class, Unterkunft.class, Reise.class}, version = 18)
@TypeConverters(BildConverter.class)
public abstract class Datenbank extends RoomDatabase {

    private static Datenbank instanz;

    public abstract AktivitaetDao aktivitaetDao();

    public abstract TagDao tagDao();

    public abstract ReisemittelDao reisemittelDao();

    public abstract UnterkunftDao unterkunftDao();

    public abstract ReiseDao reiseDao();

    public static synchronized Datenbank getInstance(Context context) {
        if (instanz == null) {
            instanz = Room.databaseBuilder(context.getApplicationContext(),
                    Datenbank.class, "projekt_datenbank")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instanz;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instanz).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void,Void>{
        private final AktivitaetDao aktivitaetDao;
        private final TagDao tagDao;
        private final ReisemittelDao reisemittelDao;
        private final UnterkunftDao unterkunftDao;
        private final ReiseDao reiseDao;

        private PopulateDBAsyncTask(Datenbank db) {
            aktivitaetDao = db.aktivitaetDao();
            tagDao = db.tagDao();
            reisemittelDao = db.reisemittelDao();
            unterkunftDao = db.unterkunftDao();
            reiseDao = db.reiseDao();
        }


        //PRESET
        @Override
        protected Void doInBackground(Void... voids) {


            return null;
        }
    }

}
