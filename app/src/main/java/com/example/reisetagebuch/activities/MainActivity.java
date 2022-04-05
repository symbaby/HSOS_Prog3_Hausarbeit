/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz
 Grundlage unseres Codes ist eine YouTube Tutorial-Reihe.
 Quelle: https://www.youtube.com/watch?v=ARpn-1FPNE4&list=PLrnPJCHvNZuDihTpkRs6SpZhqgBqPU118 */
package com.example.reisetagebuch.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.reisetagebuch.R;
import com.example.reisetagebuch.add.AddEditReiseActivity;
import com.example.reisetagebuch.adapter.ReiseAdapter;
import com.example.reisetagebuch.entitaet.Reise;
import com.example.reisetagebuch.viewmodel.AktivitaetViewModel;
import com.example.reisetagebuch.viewmodel.ReiseViewModel;
import com.example.reisetagebuch.viewmodel.ReisemittelViewModel;
import com.example.reisetagebuch.viewmodel.TagViewModel;
import com.example.reisetagebuch.viewmodel.UnterkunftViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    //Konstanten fuer REQUEST
    public static final int ADD_REISE_REQUEST = 1;
    public static final int EDIT_REISE_REQUEST = 2;

    //Key Konstanten
    public static final String EXTRA_REISE_ID = "com.example.reisetagebuch_EXTRA_REISE_ID";

    // Viewmodel instanziieren
    private ReiseViewModel reiseViewModel;

    //Hilfsvariablen
    private Bitmap angenommeneBitmap;
    private Uri uebergebenedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Titel setzen
        setTitle(getResources().getString(R.string.ReiseUebersicht));

        // FloatingActionButton fuer Hinzufuegen von Reisen
        FloatingActionButton buttonAddReise = findViewById(R.id.button_add_reise);
        buttonAddReise.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditReiseActivity.class);
            startActivityForResult(intent, ADD_REISE_REQUEST);
        });

        //RecyclerView fuer Reisen
        RecyclerView recyclerViewMainMenu = findViewById(R.id.recycler_view_main);
        recyclerViewMainMenu.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMainMenu.setHasFixedSize(true);

        //Adapter fuer Reisen
        final ReiseAdapter reiseAdapter = new ReiseAdapter();
        recyclerViewMainMenu.setAdapter(reiseAdapter);

        //ReiseViewModel initialisieren
        initViewModel();

        //Update Recyclerview von Reise
        updateReisen(reiseAdapter);

        //Swipe Funktion zum loeschen
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                reiseViewModel.delete(reiseAdapter.getReiseAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, getResources().getString(R.string.ReiseWurdeGeloescht), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewMainMenu);

        // ClickListener fuer Reise bearbeiten
        reiseAdapter.setOnReiseButtonClickListener(reise -> {
            Intent intent = new Intent(MainActivity.this, AddEditReiseActivity.class);
            intent.putExtra(AddEditReiseActivity.EXTRA_ID, reise.getId());
            intent.putExtra(AddEditReiseActivity.EXTRA_REISENAME, reise.getReiseName());
            intent.putExtra(AddEditReiseActivity.EXTRA_LAND, reise.getLand());
            intent.putExtra(AddEditReiseActivity.EXTRA_STADT, reise.getStadt());
            intent.putExtra(AddEditReiseActivity.EXTRA_PROVINZ, reise.getProvinz());
            intent.putExtra(AddEditReiseActivity.EXTRA_DAUER, reise.getReiseDauer());
            intent.putExtra(AddEditReiseActivity.EXTRA_BEWERTUNG, reise.getReiseBewertung());
            intent.putExtra(AddEditReiseActivity.EXTRA_ANZAHLREISENDE, reise.getAnzahlReisende());
            intent.putExtra(AddEditReiseActivity.EXTRA_KONTINENT, reise.getKontinent());
            intent.putExtra(AddEditReiseActivity.EXTRA_REISETYP, reise.getReisetyp());
            intent.putExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_TAG, reise.getAnreiseTag());
            intent.putExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_MONAT, reise.getAnreiseMonat());
            intent.putExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_JAHR, reise.getAnreiseJahr());
            intent.putExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_TAG, reise.getAbreiseTag());
            intent.putExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_MONAT, reise.getAbreiseMonat());
            intent.putExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_JAHR, reise.getAbreiseJahr());
            intent.putExtra(AddEditReiseActivity.EXTRA_VISUMKOSTEN, reise.getVisumKosten());

            uebergebenedUri = resolveBitmaptoUri(this, reise.getBild());
            intent.putExtra(AddEditReiseActivity.EXTRA_REISEBILD, uebergebenedUri.toString());

            startActivityForResult(intent, EDIT_REISE_REQUEST);
        });

        //BUTTON REISEMITTEL hinzufuegen
        reiseAdapter.setOnReisemittelButtonClickListener(reise -> {
            Intent intent = new Intent(MainActivity.this, ReisemittelActivity.class);
            // Reise ID mitgeben
            intent.putExtra(EXTRA_REISE_ID, reise.getId());
            startActivity(intent);
        });


        //BUTTON UNTERKUNFT hinzufuegen
        reiseAdapter.setOnUnterkunftButtonClickListener(reise -> {
            Intent intent = new Intent(MainActivity.this, UnterkunftActivity.class);
            // Reise ID mitgeben
            intent.putExtra(EXTRA_REISE_ID, reise.getId());
            startActivity(intent);
        });


        //BUTTON TAG hinzufuegen
        reiseAdapter.setOnTagButtonClickListener(reise -> {
            Intent intent = new Intent(MainActivity.this, TagActivity.class);
            // Reise ID mitgeben
            intent.putExtra(EXTRA_REISE_ID, reise.getId());
            startActivity(intent);
        });
    }

    //Erstellt eine Reise, wenn ein Result aus der AddEditReiseActivity zurueckkommt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // NEUE REISE HINZUFUEGEN
        if (requestCode == ADD_REISE_REQUEST && resultCode == RESULT_OK) {
            //Intent entpacken
            String reiseName = intent.getStringExtra(AddEditReiseActivity.EXTRA_REISENAME);
            String land = intent.getStringExtra(AddEditReiseActivity.EXTRA_LAND);
            String stadt = intent.getStringExtra(AddEditReiseActivity.EXTRA_STADT);
            String provinz = intent.getStringExtra(AddEditReiseActivity.EXTRA_PROVINZ);
            int dauer = intent.getIntExtra(AddEditReiseActivity.EXTRA_DAUER, 0);
            String kontinent = intent.getStringExtra(AddEditReiseActivity.EXTRA_KONTINENT);
            int anzahlReisende = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANZAHLREISENDE, 0);
            String reisetyp = intent.getStringExtra(AddEditReiseActivity.EXTRA_REISETYP);
            int bewertung = intent.getIntExtra(AddEditReiseActivity.EXTRA_BEWERTUNG, 0);
            int visumkosten = intent.getIntExtra(AddEditReiseActivity.EXTRA_VISUMKOSTEN, 0);

            String uriString = intent.getStringExtra(AddEditReiseActivity.EXTRA_REISEBILD);
            try {
                angenommeneBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(uriString));
            } catch (IOException e) {
                e.printStackTrace();
            }

            int anreisedatum_tag = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_TAG, 0);
            int anreisedatum_monat = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_MONAT, 0);
            int anreisedatum_jahr = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_JAHR, 0);

            int abreisedatum_tag = intent.getIntExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_TAG, 0);
            int abreisedatum_monat = intent.getIntExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_MONAT, 0);
            int abreisedatum_jahr = intent.getIntExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_JAHR, 0);

            Reise reise = new Reise.ReiseBuilder(reiseName, dauer)
                    .ortSetzen(kontinent, land, provinz, stadt)
                    .anzahlReisenderSetzen(anzahlReisende)
                    .bewertungSetzen(bewertung)
                    .reiseTypSetzen(reisetyp)
                    .visumKostenSetzen(visumkosten)
                    .anreiseDatumSetzen(anreisedatum_tag, anreisedatum_monat, anreisedatum_jahr)
                    .abreiseDatumSetzen(abreisedatum_tag, abreisedatum_monat, abreisedatum_jahr)
                    .bitmapSetzen(angenommeneBitmap)
                    .build();

            reiseViewModel.insert(reise);

        //BESTEHENDE REISE BEARBEITEN
        } else if (requestCode == EDIT_REISE_REQUEST && resultCode == RESULT_OK) {
            int id = intent.getIntExtra(AddEditReiseActivity.EXTRA_ID, -1);
            if (id == -1) {
                return;
            }
            String reiseName = intent.getStringExtra(AddEditReiseActivity.EXTRA_REISENAME);
            String land = intent.getStringExtra(AddEditReiseActivity.EXTRA_LAND);
            String stadt = intent.getStringExtra(AddEditReiseActivity.EXTRA_STADT);
            String provinz = intent.getStringExtra(AddEditReiseActivity.EXTRA_PROVINZ);
            int dauer = intent.getIntExtra(AddEditReiseActivity.EXTRA_DAUER, 0);
            String kontinent = intent.getStringExtra(AddEditReiseActivity.EXTRA_KONTINENT);
            int anzahlReisende = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANZAHLREISENDE, 0);
            String reisetyp = intent.getStringExtra(AddEditReiseActivity.EXTRA_REISETYP);
            int bewertung = intent.getIntExtra(AddEditReiseActivity.EXTRA_BEWERTUNG, 0);
            int visumkosten = intent.getIntExtra(AddEditReiseActivity.EXTRA_VISUMKOSTEN, 0);

            String uriString = intent.getStringExtra(AddEditReiseActivity.EXTRA_REISEBILD);
            try {
                angenommeneBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(uriString));
            } catch (IOException e) {
                e.printStackTrace();
            }

            int anreisedatum_tag = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_TAG, 0);
            int anreisedatum_monat = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_MONAT, 0);
            int anreisedatum_jahr = intent.getIntExtra(AddEditReiseActivity.EXTRA_ANREISEDATUM_JAHR, 0);

            int abreisedatum_tag = intent.getIntExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_TAG, 0);
            int abreisedatum_monat = intent.getIntExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_MONAT, 0);
            int abreisedatum_jahr = intent.getIntExtra(AddEditReiseActivity.EXTRA_ABREISEDATUM_JAHR, 0);

            Reise reise = new Reise.ReiseBuilder(reiseName, dauer)
                    .ortSetzen(kontinent, land, provinz, stadt)
                    .anzahlReisenderSetzen(anzahlReisende)
                    .bewertungSetzen(bewertung)
                    .reiseTypSetzen(reisetyp)
                    .visumKostenSetzen(visumkosten)
                    .anreiseDatumSetzen(anreisedatum_tag, anreisedatum_monat, anreisedatum_jahr)
                    .abreiseDatumSetzen(abreisedatum_tag, abreisedatum_monat, abreisedatum_jahr)
                    .bitmapSetzen(angenommeneBitmap)
                    .build();

            reise.setId(id);
            reiseViewModel.update(reise);
            Toast.makeText(this, getResources().getString(R.string.ReiseWurdeBearbeitet), Toast.LENGTH_SHORT).show();
        //REISE WURDE VERWORFEN
        } else {
            Toast.makeText(this, getResources().getString(R.string.ReiseWurdeVerworfen), Toast.LENGTH_SHORT).show();
        }
    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_reisen:
                reiseViewModel.deleteAllReisen();
                Toast.makeText(this, getResources().getString(R.string.AlleReisenWurdenGeloescht), Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sprache_aendern:
                sprachenAendern();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViewModel() {
        reiseViewModel = new ViewModelProvider(this).get(ReiseViewModel.class);
    }

    //Recyclerview der Reise wird geupdatet bei Veraenderung
    private void updateReisen(ReiseAdapter reiseAdapter) {
        reiseViewModel.getAllReisen().observe(this, reises -> {
            reiseAdapter.setReisen(reises);
        });
    }

    // Bitmap zu Uri konvertieren
    // Quelle: https://stackoverflow.com/questions/53913401/mediastore-images-media-insertimage-is-returning-null-when-trying-to-get-image-u
    public Uri resolveBitmaptoUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //Implementation der language change Funktion
    //QUELLE: https://www.youtube.com/watch?v=zILw5eV9QBQ
    private void sprachenAendern(){
        final String[] listSprachen={"Deutsch", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setSingleChoiceItems(listSprachen, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(i == 0){
                    setLocal("de");
                    recreate();
                }
                else if(i == 1){
                    setLocal("en");
                    recreate();
                }
                 dialog.dismiss();
            }
        });
        AlertDialog alertDialog = mBuilder.create();
        alertDialog.show();
    }
    //Quelle: https://www.youtube.com/watch?v=zILw5eV9QBQ
    private void setLocal(String s){
        Locale locale = new Locale(s);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale=locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.apply();
    }
}