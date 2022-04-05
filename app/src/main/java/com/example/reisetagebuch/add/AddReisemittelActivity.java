/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.add;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reisetagebuch.activities.MainActivity;
import com.example.reisetagebuch.R;

public class AddReisemittelActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Keys Konstanten fuer Intent
    public static final String EXTRA_REISEMITTELTYP =
            "com.example.reisetagebuch.EXTRA_REISEMITTELTYP";

    public static final String EXTRA_REISEMITTELKOSTEN =
            "com.example.reisetagebuch.EXTRA_REISEMITTELKOSTEN";


    //Deklaration von allen EditText, Spinnern, NumberPicker
    private Spinner spinnerReisemitteltyp;
    private EditText editTextReisemittelKosten;
    private String reiseMittelTyp;

    //Hilfsvariablen
    private int reise_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reisemittel);

        Intent intent = getIntent();

        auspackenFallsEdit(intent);

        setTitle(getResources().getString(R.string.ReisemittelHinzufuegen));
        initViews();
    }

    //Hilfsmethode fuer Intent auspacken
    private void auspackenFallsEdit(Intent intent) {
        reise_id = intent.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);
    }

    //EditText, Spinnern, NumberPicker werden mit XML Views verbunden
    private void initViews() {
        spinnerReisemitteltyp = findViewById(R.id.spinner_reisemittel_typ);
        editTextReisemittelKosten = findViewById(R.id.edit_text_reisemittel_kosten);

        ArrayAdapter<CharSequence> reisemittelTypAdapter = ArrayAdapter.createFromResource(this, R.array.reisemittel, android.R.layout.simple_spinner_item);
        reisemittelTypAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReisemitteltyp.setAdapter(reisemittelTypAdapter);
        spinnerReisemitteltyp.setOnItemSelectedListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_reisemittel_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_reisemittel:
                saveReiseMittel();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Intent wird gepackt und an Parent Activity zurueckgegeben
    private void saveReiseMittel() {
        int reisemittelKosten = Integer.parseInt(editTextReisemittelKosten.getText().toString());

        if (reisemittelKosten == 0) {
            Toast.makeText(this, "Bitte kosten eintragen", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_REISEMITTELTYP, reiseMittelTyp);
        data.putExtra(EXTRA_REISEMITTELKOSTEN, reisemittelKosten);
        data.putExtra(MainActivity.EXTRA_REISE_ID, reise_id);

        setResult(RESULT_OK, data);
        finish();

    }

    //Noetig fuer die Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        reiseMittelTyp = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}