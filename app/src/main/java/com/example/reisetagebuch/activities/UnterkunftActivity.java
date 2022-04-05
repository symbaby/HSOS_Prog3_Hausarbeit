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
import com.example.reisetagebuch.add.AddUnterkunftActivity;
import com.example.reisetagebuch.adapter.UnterkunftAdapter;
import com.example.reisetagebuch.entitaet.Unterkunft;
import com.example.reisetagebuch.viewmodel.UnterkunftViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UnterkunftActivity extends AppCompatActivity {

    //Konstanten fuer REQUEST
    public static final int ADD_UNTERKUNFT_REQUEST = 2;

    //Hilfsvariablen
    private int r_id;
    private UnterkunftViewModel unterkunftViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unterkunft);

        //Titel setzen
        setTitle(getResources().getString(R.string.UnterkunftUebersicht));

        //Intent auspacken
        Intent data = getIntent();
        r_id = data.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);

        // FloatingActionButton fuer Hinzufuegen von Unterkuenfte
        FloatingActionButton buttonAddReisemittel = findViewById(R.id.button_add_unterkunft);
        buttonAddReisemittel.setOnClickListener(v -> {
            Intent intent = new Intent(UnterkunftActivity.this, AddUnterkunftActivity.class);
            intent.putExtra(MainActivity.EXTRA_REISE_ID, r_id);

            startActivityForResult(intent, ADD_UNTERKUNFT_REQUEST);
        });

        //RecyclerView fuer Unterkunft
        RecyclerView recyclerView = findViewById(R.id.recycler_view_unterkunft);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Adapter fuer Unterkunft
        final UnterkunftAdapter unterkunftAdapter = new UnterkunftAdapter();
        recyclerView.setAdapter(unterkunftAdapter);

        //UnterkunftViewModel initialisieren
        initUnterkunftViewModel();

        //Update Recyclerview von Unterkunft
        updateSpecificUnterkunft(unterkunftAdapter);

        //Swipe Funktion zum loeschen
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                unterkunftViewModel.delete(unterkunftAdapter.getUnterkunftAt(viewHolder.getAdapterPosition()));
                Toast.makeText(UnterkunftActivity.this, getResources().getString(R.string.UnterkunftWurdeGeloescht) , Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initUnterkunftViewModel() {
        unterkunftViewModel = new ViewModelProvider(this).get(UnterkunftViewModel.class);
    }

    //Recyclerview der Unterkunft wird geupdatet bei Veraenderung
    private void updateSpecificUnterkunft(UnterkunftAdapter unterkunftAdapter) {
        unterkunftViewModel.getSpecificUnterkunft(r_id).observe(this, unterkunfts -> {
            unterkunftAdapter.setUnterkuenfte(unterkunfts);
        });
    }

    //Erstellt eine UNterkunft, wenn ein Result aus der AddUnterkunftActivity zurueckkommt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_UNTERKUNFT_REQUEST && resultCode == RESULT_OK) {
            int unterkunftPreis = data.getIntExtra(AddUnterkunftActivity.EXTRA_UNTERKUNFTPREIS, 0);
            String unterkunftLand = data.getStringExtra(AddUnterkunftActivity.EXTRA_UNTERKUNFTLAND);
            String unterkunftStadt = data.getStringExtra(AddUnterkunftActivity.EXTRA_UNTERKUNFTSTADT);
            String unterkunftName = data.getStringExtra(AddUnterkunftActivity.EXTRA_UNTERKUNFTNAME);
            int unterkunftDauer = data.getIntExtra(AddUnterkunftActivity.EXTRA_UNTERKUNFTDAUER, 0);
            int unterkunftBewertung = data.getIntExtra(AddUnterkunftActivity.EXTRA_UNTERKUNFTBEWERTUNG, 0);
            String unterkunftTyp = data.getStringExtra(AddUnterkunftActivity.EXTRA_UNTERKUNFTTYP);

            Unterkunft unterkunft = new Unterkunft.UnterkunftsBuilder(unterkunftLand, unterkunftStadt, unterkunftName, r_id)
                    .preisSetzen(unterkunftPreis)
                    .dauerSetzen(unterkunftDauer)
                    .bewertungSetzen(unterkunftBewertung)
                    .typErgaenzen(unterkunftTyp)
                    .build();

            unterkunftViewModel.insert(unterkunft);

            Toast.makeText(this, getResources().getString(R.string.UnterkunftWurdeHinzugefuegt), Toast.LENGTH_SHORT).show();
        } else {
             Toast.makeText(this, getResources().getString(R.string.UnterkunftWurdeGeloescht), Toast.LENGTH_SHORT).show();
        }
    }


    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.unterkuft_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_unterkunft:
                unterkunftViewModel.deleteSpecificUnterkunft(r_id);
                Toast.makeText(this, getResources().getString(R.string.AlleUnterkuenfteWurdenGeloescht), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}