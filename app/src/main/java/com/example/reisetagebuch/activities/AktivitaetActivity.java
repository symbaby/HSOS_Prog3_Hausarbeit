/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.reisetagebuch.R;
import com.example.reisetagebuch.add.AddAktivitaetActivity;
import com.example.reisetagebuch.adapter.AktivitaetAdapter;
import com.example.reisetagebuch.entitaet.Aktivitaet;
import com.example.reisetagebuch.viewmodel.AktivitaetViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AktivitaetActivity extends AppCompatActivity {

    //Konstante fuer REQUEST
    public static final int ADD_AKTIVITAET_REQUEST = 1;
    //Hilfvariable
    private int tag_id;
    //ViewModel fuer Aktivitaeten
    private AktivitaetViewModel aktivitaetViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitaet);


        //ID aus Intent holen
        Intent data = getIntent();
        tag_id = data.getIntExtra(TagActivity.EXTRA_TAG_ID, 0);

        //Menue Titel setzen
        setTitle(getResources().getString(R.string.AktivitaetUbersicht));

        //ClickListener für FloatingActionButton
        FloatingActionButton buttonAddAktivitaet = findViewById(R.id.button_add_aktivitaet);
        buttonAddAktivitaet.setOnClickListener(v -> {
            Intent intent = new Intent(AktivitaetActivity.this, AddAktivitaetActivity.class);

            intent.putExtra(TagActivity.EXTRA_TAG_ID, tag_id);

            startActivityForResult(intent, ADD_AKTIVITAET_REQUEST);
        });

        //RecyclerView intialisieren+Settings
        RecyclerView recyclerView = findViewById(R.id.recycler_view_aktivitaet);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Aktivitaet Adapter
        final AktivitaetAdapter aktivitaetAdapter = new AktivitaetAdapter();
        recyclerView.setAdapter(aktivitaetAdapter);

        //Aktivitaet ViewModel
        initAktivitaetViewModel();

        //Swipe Funktion
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                aktivitaetViewModel.delete(aktivitaetAdapter.getAktivitaetAt(viewHolder.getAdapterPosition()));
                Toast.makeText(AktivitaetActivity.this, getResources().getString(R.string.AktivitaetWurdeGeloescht), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        updateSpecificAktivitaet(aktivitaetAdapter);
    }

    private void initAktivitaetViewModel() {
        aktivitaetViewModel = new ViewModelProvider(this).get(AktivitaetViewModel.class);
    }

    //Updaten die RecyclerView falls etwas veraendert wird
    private void updateSpecificAktivitaet(AktivitaetAdapter aktivitaetAdapter) {
        aktivitaetViewModel.getSpecificAktivitaeten(tag_id).observe(this, aktivitaets -> {
            aktivitaetAdapter.setAktivitaeten(aktivitaets);
        });
    }

    //Erstellt eine Aktivitaet, wenn ein Result aus der AddAktivitaetActivity zurueckkommt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_AKTIVITAET_REQUEST && resultCode == RESULT_OK) {

            String aktivitaetTyp = data.getStringExtra(AddAktivitaetActivity.EXTRA_AKTIVITAET_TYP);
            int aktivitaetUhrzeit = data.getIntExtra(AddAktivitaetActivity.EXTRA_AKTIVITAET_UHRZEIT, 0);
            int aktivitaetBewertung = data.getIntExtra(AddAktivitaetActivity.EXTRA_AKTIVITAET_BEWERTUNG, 0);
            int aktivitaetKosten = data.getIntExtra(AddAktivitaetActivity.EXTRA_AKTIVITAET_KOSTEN, 0);
            String aktivitaetBeschreibung = data.getStringExtra(AddAktivitaetActivity.EXTRA_AKTIVITAET_BESCHREIBUNG);

            int r_id = data.getIntExtra(TagActivity.EXTRA_TAG_ID, 0);

            Aktivitaet aktivitaet = new Aktivitaet.AktivitaetBuilder(aktivitaetTyp, aktivitaetBeschreibung)
                    .bewertungSetzen(aktivitaetBewertung)
                    .kostenSetzen(aktivitaetKosten)
                    .uhrzeitSetzen(aktivitaetUhrzeit)
                    .tagIdSetzen(r_id)
                    .build();
            aktivitaetViewModel.insert(aktivitaet);

             Toast.makeText(this, getResources().getString(R.string.AktivitaetWurdeHinzugefuegt), Toast.LENGTH_SHORT).show();
        } else {
             Toast.makeText(this, getResources().getString(R.string.AktivitaetWurdeVerworfen), Toast.LENGTH_SHORT).show();
        }
    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.aktivitaet_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_aktivitaet:
                aktivitaetViewModel.deleteSpecificAktivitaeten(tag_id);
                Toast.makeText(this, getResources().getString(R.string.AlleAktivitaetenWurdeGeloescht), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}