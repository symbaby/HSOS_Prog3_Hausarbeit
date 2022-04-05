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
import com.example.reisetagebuch.add.AddReisemittelActivity;
import com.example.reisetagebuch.adapter.ReisemittelAdapter;
import com.example.reisetagebuch.entitaet.Reisemittel;
import com.example.reisetagebuch.viewmodel.ReisemittelViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ReisemittelActivity extends AppCompatActivity {
    //Konstanten fuer REQUEST
    public static final int ADD_REISEMITTEL_REQUEST = 1;

    //Hilfsvariablen
    private int r_id;
    private ReisemittelViewModel reisemittelViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reisemittel);

        //Titel setzen
        setTitle(getResources().getString(R.string.ReisemittelUebersicht));

        //Intent auspacken
        Intent data = getIntent();
        r_id = data.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);

        // FloatingActionButton fuer Hinzufuegen von Reisemitteln
        FloatingActionButton buttonAddReisemittel = findViewById(R.id.button_add_reisemittel);
        buttonAddReisemittel.setOnClickListener(v -> {
            Intent intent = new Intent(ReisemittelActivity.this, AddReisemittelActivity.class);
            intent.putExtra(MainActivity.EXTRA_REISE_ID, r_id);

            startActivityForResult(intent, ADD_REISEMITTEL_REQUEST);
        });

        //RecyclerView fuer Reisemittel
        RecyclerView recyclerView = findViewById(R.id.recycler_view_reisemittel);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Adapter fuer Reisemittel
        final ReisemittelAdapter reisemittelAdapter = new ReisemittelAdapter();
        recyclerView.setAdapter(reisemittelAdapter);

        //ReisemittelViewModel initialisieren
        initReisemittelViewModel();

        //Update Recyclerview von Reisemittel
        updateSpecificReisemittel(reisemittelAdapter);

        //Swipe Funktion zum loeschen
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                reisemittelViewModel.delete(reisemittelAdapter.getReisemittelAt(viewHolder.getAdapterPosition()));
                Toast.makeText(ReisemittelActivity.this, getResources().getString(R.string.ReisemittelWurdeGeloescht), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initReisemittelViewModel() {
        reisemittelViewModel = new ViewModelProvider(this).get(ReisemittelViewModel.class);
    }

    //Recyclerview der Reisemittel wird geupdatet bei Veraenderung
    private void updateSpecificReisemittel(ReisemittelAdapter reisemittelAdapter) {
        reisemittelViewModel.getSpecificReisemittel(r_id).observe(this, reisemittels -> {
            reisemittelAdapter.setReisemittel(reisemittels);
        });
    }

    //Erstellt eine Reisemittel, wenn ein Result aus der AddReisemittelActivity zurueckkommt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_REISEMITTEL_REQUEST && resultCode == RESULT_OK) {
            int reisemittelKosten = data.getIntExtra(AddReisemittelActivity.EXTRA_REISEMITTELKOSTEN, 0);
            String reisemittelTyp = data.getStringExtra(AddReisemittelActivity.EXTRA_REISEMITTELTYP);
            int r_id = data.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);

            Reisemittel reisemittel = new Reisemittel(reisemittelKosten, reisemittelTyp, r_id);
            reisemittelViewModel.insert(reisemittel);

            Toast.makeText(this, getResources().getString(R.string.ReisemittelWurdeHinzugefügt), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.ReisemittelWurdeVerworfen), Toast.LENGTH_SHORT).show();
        }
    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.reisemittel_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_reisemittel:
                reisemittelViewModel.deleteSpecificReisemittel(r_id);
                Toast.makeText(this, getResources().getString(R.string.AlleReisemittelWurdenGeloescht), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}