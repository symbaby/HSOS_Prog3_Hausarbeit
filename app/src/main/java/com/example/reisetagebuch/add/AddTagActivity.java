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
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.reisetagebuch.activities.MainActivity;
import com.example.reisetagebuch.R;

public class AddTagActivity extends AppCompatActivity {

    // Keys Konstanten fuer Intent
    public static final String EXTRA_TAG_TAG =
            "com.example.reisetagebuch.EXTRA_TAG_TAG";

    public static final String EXTRA_TAG_MONAT =
            "com.example.reisetagebuch.EXTRA_TAG_MONAT";

    public static final String EXTRA_TAG_JAHR =
            "com.example.reisetagebuch.EXTRA_TAG_JAHR";

    //Deklaration von DatePicker
    private DatePicker datePickerTag;

    //Hilfsvariablen
    private int reise_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tag);

        Intent intent = getIntent();

        auspackenFallsEdit(intent);

        setTitle(getResources().getString(R.string.TagHinzufuegen));
        initViews();

    }

    //Hilfsmethode fuer Intent auspacken
    private void auspackenFallsEdit(Intent intent) {
        reise_id = intent.getIntExtra(MainActivity.EXTRA_REISE_ID, 0);
    }


    //Datepicker werden mit XML Views verbunden
    private void initViews() {
        datePickerTag = findViewById(R.id.date_picker_tag_datum);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_tag_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_tag:
                saveTag();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Intent wird gepackt und an Parent Activity zurueckgegeben
    private void saveTag() {
        int tagTag = datePickerTag.getDayOfMonth();
        int tagMonat = datePickerTag.getMonth() + 1;
        int tagJahr = datePickerTag.getYear();

        if (tagTag == 0 || tagMonat == 0 || tagJahr == 0) {
            Toast.makeText(this, "Bitte vollständiges Datum angeben", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TAG_TAG, tagTag);
        data.putExtra(EXTRA_TAG_MONAT, tagMonat);
        data.putExtra(EXTRA_TAG_JAHR, tagJahr);
        data.putExtra(MainActivity.EXTRA_REISE_ID, reise_id);

        setResult(RESULT_OK, data);
        finish();
    }
}