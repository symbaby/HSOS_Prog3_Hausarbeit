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

import com.example.reisetagebuch.R;
import com.example.reisetagebuch.activities.TagActivity;

public class AddAktivitaetActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Keys Konstanten fuer Intent
    public static final String EXTRA_AKTIVITAET_TYP =
            "com.example.reisetagebuch.EXTRA_AKTIVITAET_TYP";

    public static final String EXTRA_AKTIVITAET_UHRZEIT =
            "com.example.reisetagebuch.EXTRA_AKTIVITAET_UHRZEIT";

    public static final String EXTRA_AKTIVITAET_BEWERTUNG =
            "com.example.reisetagebuch.EXTRA_AKTIVITAET_BEWERTUNG";

    public static final String EXTRA_AKTIVITAET_KOSTEN =
            "com.example.reisetagebuch.EXTRA_AKTIVITAET_KOSTEN";

    public static final String EXTRA_AKTIVITAET_BESCHREIBUNG =
            "com.example.reisetagebuch.EXTRA_AKTIVITAET_BESCHREIBUNG";


    //Deklaration von allen EditText, Spinnern, NumberPicker
    private Spinner spinnerAktivitaetTyp;
    private EditText editTextAktivitaetKosten;
    private EditText editTextAktivitaetBeschreibung;
    private NumberPicker numberPickerAktivitaetUhrzeit;
    private NumberPicker numberPickerAktivitaetBewertung;

    //Hilfsvariablen
    private String aktivitaetsTyp;
    private int t_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_aktivitaet);

        Intent intent = getIntent();
        auspackenFallsEdit(intent);

        setTitle(getResources().getString(R.string.AktivitaetHinzufuegen));
        initViews();
        initBewertung();
        initUhrzeit();
    }

    //Hilfsmethode fuer Intent auspacken
    private void auspackenFallsEdit(Intent intent) {
        t_id = intent.getIntExtra(TagActivity.EXTRA_TAG_ID, 0);
    }

    //Views werden mit XML Views verbunden
    private void initViews() {
        spinnerAktivitaetTyp = findViewById(R.id.spinner_aktivitaetstyp);
        editTextAktivitaetKosten = findViewById(R.id.edit_text_aktivitaet_kosten);
        editTextAktivitaetBeschreibung = findViewById(R.id.edit_text_aktivitaet_beschreibung);
        numberPickerAktivitaetUhrzeit = findViewById(R.id.numberpicker_aktivitaet_uhrzeit);
        numberPickerAktivitaetBewertung = findViewById(R.id.numberpicker_aktivitaet_bewertung);

        ArrayAdapter<CharSequence> aktivitaetTypAdapter = ArrayAdapter.createFromResource(this, R.array.aktivitaettyp, android.R.layout.simple_spinner_item);
        aktivitaetTypAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAktivitaetTyp.setAdapter(aktivitaetTypAdapter);
        spinnerAktivitaetTyp.setOnItemSelectedListener(this);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_aktivitaet_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_aktivitaet:
                saveAktivitaet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Intent wird gepackt und an Parent Activity zurueckgegeben
    private void saveAktivitaet() {
        int aktivitaetUhrzeit = numberPickerAktivitaetUhrzeit.getValue();
        int aktivitaetBewertung = numberPickerAktivitaetBewertung.getValue();
        int aktivitaetKosten = Integer.parseInt(editTextAktivitaetKosten.getText().toString());
        String aktivitaetBeschreibung = editTextAktivitaetBeschreibung.getText().toString();

        if (aktivitaetBeschreibung == null) {
            Toast.makeText(this, "Bitte kleine Beschreibung hinterlegen.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_AKTIVITAET_TYP, aktivitaetsTyp);
        data.putExtra(EXTRA_AKTIVITAET_KOSTEN, aktivitaetKosten);
        data.putExtra(EXTRA_AKTIVITAET_BESCHREIBUNG, aktivitaetBeschreibung);
        data.putExtra(EXTRA_AKTIVITAET_UHRZEIT, aktivitaetUhrzeit);
        data.putExtra(EXTRA_AKTIVITAET_BEWERTUNG, aktivitaetBewertung);
        data.putExtra(TagActivity.EXTRA_TAG_ID, t_id);

        setResult(RESULT_OK, data);
        finish();
    }

    //Numberpicker konfigurieren
    private void initUhrzeit() {
        numberPickerAktivitaetUhrzeit.setMinValue(0);
        numberPickerAktivitaetUhrzeit.setMaxValue(23);
    }

    //Numberpicker konfigurieren
    private void initBewertung() {
        numberPickerAktivitaetBewertung.setMinValue(1);
        numberPickerAktivitaetBewertung.setMaxValue(10);
    }


    //Noetig fuer die Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        aktivitaetsTyp = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}