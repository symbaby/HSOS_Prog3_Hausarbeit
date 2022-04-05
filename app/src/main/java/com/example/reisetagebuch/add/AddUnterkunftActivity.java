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
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.reisetagebuch.activities.MainActivity;
import com.example.reisetagebuch.R;

public class AddUnterkunftActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Keys Konstanten fuer Intent
    public static final String EXTRA_UNTERKUNFTTYP =
            "com.example.reisetagebuch.EXTRA_UNTERKUNFTTYP";

    public static final String EXTRA_UNTERKUNFTPREIS =
            "com.example.reisetagebuch.EXTRA_UNTERKUNFTPREIS";

    public static final String EXTRA_UNTERKUNFTLAND =
            "com.example.reisetagebuch.EXTRA_UNTERKUNFTLAND";

    public static final String EXTRA_UNTERKUNFTSTADT =
            "com.example.reisetagebuch.EXTRA_UNTERKUNFTSTADT";

    public static final String EXTRA_UNTERKUNFTDAUER =
            "com.example.reisetagebuch.EXTRA_UNTERKUNFTDAUER";

    public static final String EXTRA_UNTERKUNFTBEWERTUNG =
            "com.example.reisetagebuch.EXTRA_UNTERKUNFTBEWERTUNG";

    public static final String EXTRA_UNTERKUNFTNAME =
            "com.example.reisetagebuch.EXTRA_UNTERKUNFTNAME";

    //Deklaration von allen EditText, Spinnern, NumberPicker
    private Spinner spinnerUnterkunftTyp;
    private EditText editTextUnterkunftName;
    private EditText editTextUnterkunftLand;
    private EditText editTextUnterkunftStadt;
    private EditText editTextUnterkunftPreis;
    private NumberPicker numberPickerUnterkunftBewertung;
    private NumberPicker numberPickerUnterkunftDauer;

    //Hilfsvariablen
    private String unterkunftTyp;
    private int reise_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_unterkunft);

        setTitle(getResources().getString(R.string.UnterkunftHinzufuegen));

        Intent intent = getIntent();

        auspackenFallsEdit(intent);

        initViews();
        initDauer();
        initBewertung();
    }

    //Hilfsmethode fuer Intent auspacken
    private void auspackenFallsEdit(Intent intent) {
        reise_id = intent.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);
    }

    //EditText, Spinnern, NumberPicker werden mit XML Views verbunden
    private void initViews() {
        spinnerUnterkunftTyp = findViewById(R.id.spinner_unterkunft_typ);
        editTextUnterkunftPreis = findViewById(R.id.edit_text_unterkunft_preis);
        editTextUnterkunftName = findViewById(R.id.edit_text_unterkunft_name);
        editTextUnterkunftLand = findViewById(R.id.edit_text_unterkunft_land);
        editTextUnterkunftStadt = findViewById(R.id.edit_text_unterkunft_stadt);
        numberPickerUnterkunftBewertung = findViewById(R.id.numberpicker_unterkunft_bewertung);
        numberPickerUnterkunftDauer = findViewById(R.id.numberpicker_unterkunft_dauer);

        ArrayAdapter<CharSequence> unterkunftTypAdapter = ArrayAdapter.createFromResource(this, R.array.unterkunfttyp, android.R.layout.simple_spinner_item);
        unterkunftTypAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUnterkunftTyp.setAdapter(unterkunftTypAdapter);
        spinnerUnterkunftTyp.setOnItemSelectedListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_unterkunft_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_unterkunft:
                saveUnterkunft();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Intent wird gepackt und an Parent Activity zurueckgegeben
    private void saveUnterkunft() {
        String unterkunftName = editTextUnterkunftName.getText().toString();
        String unterkunftLand = editTextUnterkunftLand.getText().toString();
        String unterkunftStadt = editTextUnterkunftStadt.getText().toString();
        int unterkunftDauer = numberPickerUnterkunftDauer.getValue();
        int unterkunftPreis = Integer.parseInt(editTextUnterkunftPreis.getText().toString());
        int unterkunftBewertung = numberPickerUnterkunftBewertung.getValue();

        if (unterkunftDauer == 0) {
            Toast.makeText(this, "Bitte Namen und Dauer eintragen", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(MainActivity.EXTRA_REISE_ID, reise_id);
        data.putExtra(EXTRA_UNTERKUNFTTYP, unterkunftTyp);
        data.putExtra(EXTRA_UNTERKUNFTNAME, unterkunftName);
        data.putExtra(EXTRA_UNTERKUNFTLAND, unterkunftLand);
        data.putExtra(EXTRA_UNTERKUNFTSTADT, unterkunftStadt);
        data.putExtra(EXTRA_UNTERKUNFTDAUER, unterkunftDauer);
        data.putExtra(EXTRA_UNTERKUNFTPREIS, unterkunftPreis);
        data.putExtra(EXTRA_UNTERKUNFTBEWERTUNG, unterkunftBewertung);

        setResult(RESULT_OK, data);
        finish();

    }

    //Numberpicker konfigurieren
    private void initDauer() {
        numberPickerUnterkunftDauer.setMinValue(1);
        numberPickerUnterkunftDauer.setMaxValue(30);
    }

    //Numberpicker konfigurieren
    private void initBewertung() {
        numberPickerUnterkunftBewertung.setMinValue(1);
        numberPickerUnterkunftBewertung.setMaxValue(10);

    }

    //Noetig fuer die Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        unterkunftTyp = parent.getItemAtPosition(position).toString();
    }

    // Unimplementierte Methode fuer Kontinent Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}