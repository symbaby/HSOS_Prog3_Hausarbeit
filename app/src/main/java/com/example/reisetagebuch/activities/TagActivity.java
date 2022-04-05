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
import com.example.reisetagebuch.add.AddTagActivity;
import com.example.reisetagebuch.adapter.TagAdapter;
import com.example.reisetagebuch.entitaet.Tag;
import com.example.reisetagebuch.viewmodel.TagViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TagActivity extends AppCompatActivity {
    //Konstanten fuer REQUEST
    public static final int ADD_TAG_REQUEST = 1;

    //Key fuer Intent
    public static final String EXTRA_TAG_ID =
            "com.example.reisetagebuch.EXTRA_TAG_ID";

    //Hilfsvariablen
    private int r_id;
    private TagViewModel tagViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        //Titel setzen
        setTitle(getResources().getString(R.string.TagUebersicht));

        //Intent auspacken
        Intent data = getIntent();
        r_id = data.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);

        // FloatingActionButton fuer Hinzufuegen von Tagen
        FloatingActionButton buttonAddTag = findViewById(R.id.button_add_tag);
        buttonAddTag.setOnClickListener(tags -> {
            Intent intent = new Intent(TagActivity.this, AddTagActivity.class);
            intent.putExtra(TagActivity.EXTRA_TAG_ID, tags.getId());
            intent.putExtra(MainActivity.EXTRA_REISE_ID, r_id);

            startActivityForResult(intent, ADD_TAG_REQUEST);
        });

        //RecyclerView fuer Tage
        RecyclerView recyclerView = findViewById(R.id.recycler_view_tag);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Adapter fuer Tage
        final TagAdapter tagAdapter = new TagAdapter();
        recyclerView.setAdapter(tagAdapter);

        //TageViewModel initialisieren
        initTagViewModel();

        //Update Recyclerview von Tagen
        updateSpecificTag(tagAdapter);

        //Swipe Funktion zum loeschen
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                tagViewModel.delete(tagAdapter.getTagAt(viewHolder.getAdapterPosition()));
                Toast.makeText(TagActivity.this, getResources().getString(R.string.TagWurdeGeloescht), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //ClickListener fuer Aktivitaet des Tages oeffnen
        tagAdapter.setOnAktivitaetButtonClickListener(tags -> {
            Intent intent = new Intent(TagActivity.this, AktivitaetActivity.class);

            // Tag ID mitgeben
            intent.putExtra(TagActivity.EXTRA_TAG_ID, tags.getId());

            startActivity(intent);
        });
    }

    private void initTagViewModel() {
        tagViewModel = new ViewModelProvider(this).get(TagViewModel.class);
    }

    //Recyclerview der Tage wird geupdatet bei Veraenderung
    private void updateSpecificTag(TagAdapter tagAdapter) {
        tagViewModel.getSpecificTag(r_id).observe(this, tags -> {
            tagAdapter.setTage(tags);
        });
    }

    //Erstellt eine Tages, wenn ein Result aus der AddTagActivity zurueckkommt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TAG_REQUEST && resultCode == RESULT_OK) {
            int tagTag = data.getIntExtra(AddTagActivity.EXTRA_TAG_TAG, 0);
            int tagMonat = data.getIntExtra(AddTagActivity.EXTRA_TAG_MONAT, 0);
            int tagJahr = data.getIntExtra(AddTagActivity.EXTRA_TAG_JAHR, 0);
            int r_id = data.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);

            Tag tag = new Tag(tagTag, tagMonat, tagJahr, r_id);
            tagViewModel.insert(tag);

            Toast.makeText(this, getResources().getString(R.string.TagWurdeHinzugefuegt), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.TagWurdeGeloescht), Toast.LENGTH_SHORT).show();
        }
    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.tag_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_tag:
                tagViewModel.deleteSpecificTag(r_id);
                Toast.makeText(this, getResources().getString(R.string.AlleTageWurdenGeloescht), Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}