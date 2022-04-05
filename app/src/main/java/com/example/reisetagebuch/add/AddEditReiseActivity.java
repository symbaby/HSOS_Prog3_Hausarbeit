/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.add;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.reisetagebuch.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddEditReiseActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Keys Konstanten fuer Intent
    public static final String EXTRA_ID = "com.example.reisetagebuch.EXTRA_ID";
    public static final String EXTRA_REISENAME = "com.example.reisetagebuch.EXTRA_REISENAME";
    public static final String EXTRA_LAND = "com.example.reisetagebuch.EXTRA_LAND";
    public static final String EXTRA_STADT = "com.example.reisetagebuch.EXTRA_STADT";
    public static final String EXTRA_PROVINZ = "com.example.reisetagebuch.EXTRA_PROVINZ";
    public static final String EXTRA_DAUER = "com.example.reisetagebuch.EXTRA_DAUER";
    public static final String EXTRA_KONTINENT = "com.example.reisetagebuch.EXTRA_KONTINENT";
    public static final String EXTRA_ANZAHLREISENDE = "com.example.reisetagebuch.EXTRA_ANZAHLREISENDE";
    public static final String EXTRA_REISETYP = "com.example.reisetagebuch.EXTRA_REISETYP";
    public static final String EXTRA_BEWERTUNG = "com.example.reisetagebuch.EXTRA_BEWERTUNG";
    public static final String EXTRA_ANREISEDATUM_TAG = "com.example.reisetagebuch.EXTRA_ANREISEDATUM_TAG";
    public static final String EXTRA_ANREISEDATUM_MONAT = "com.example.reisetagebuch.EXTRA_ANREISEDATUM_MONAT";
    public static final String EXTRA_ANREISEDATUM_JAHR = "com.example.reisetagebuch.EXTRA_ANREISEDATUM_JAHR";
    public static final String EXTRA_ABREISEDATUM_TAG = "com.example.reisetagebuch.EXTRA_ABREISEDATUM_TAG";
    public static final String EXTRA_ABREISEDATUM_MONAT = "com.example.reisetagebuch.EXTRA_ABREISEDATUM_MONAT";
    public static final String EXTRA_ABREISEDATUM_JAHR = "com.example.reisetagebuch.EXTRA_ABREISEDATUM_JAHR";
    public static final String EXTRA_VISUMKOSTEN = "com.example.reisetagebuch.EXTRA_VISUMKOSTEN";
    public static final String EXTRA_REISEBILD = "com.example.reisetagebuch.EXTRA_REISEBILD";

    //Hilfsvariablen
    private ImageView reiseBild;
    private Uri resulturi;

    //Deklaration von allen EditText, Spinnern, NumberPicker
    private EditText editTextReisename;
    private EditText editTextLand;
    private EditText editTextStadt;
    private EditText editTextProvinz;
    private Spinner spinnerKontinent;
    private Spinner spinnerReisetyp;
    private NumberPicker numberPickerDauer;
    private NumberPicker numberPickerBewertung;
    private NumberPicker numberPickerAnzahlReisende;
    private NumberPicker numberPickerVisumKosten;
    private DatePicker datePickerAnreisedatum;
    private DatePicker datePickerAbreisedatum;

    private String kontinent;
    private String reisetyp;
    private Bitmap bitmap;

    //String fuer den Visumkostennumberpicker
    String[] visum = new String[]{
            "0€", "10€", "20€", "30€", "40€", "50€",
            "60€", "70€", "80€", "90€", "100€", "110€", "120€", "130€", "140€", "150€",
            "160€", "170€", "180€", "190€", "200€", "210€", "220€", "230€", "240€", "250€"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reise);

        // Initialisierung
        initViews();
        initDauer();
        initBewertung();
        initAnzahlReisende();
        initVisumkosten();
        initBild();

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();

        // Auspacken vom Edit
        if (intent.hasExtra(EXTRA_ID)) {
            auspackenFallsEdit(intent);
        } else {
            setTitle(getResources().getString(R.string.ReiseHinzufuegen));
        }
    }

    // Packen fuer die MainActitvity
    private void saveReise() {
        String reiseName = editTextReisename.getText().toString();
        String land = editTextLand.getText().toString();
        String stadt = editTextStadt.getText().toString();
        String provinz = editTextProvinz.getText().toString();

        int anzahlReisende = numberPickerAnzahlReisende.getValue();
        int dauer = numberPickerDauer.getValue();
        int bewertung = numberPickerBewertung.getValue();
        int visumkostenVal = numberPickerVisumKosten.getValue();

        int anreiseTag = datePickerAnreisedatum.getDayOfMonth();
        int anreiseMonat = datePickerAnreisedatum.getMonth() + 1;
        int anreiseJahr = datePickerAnreisedatum.getYear();

        int abreiseTag = datePickerAbreisedatum.getDayOfMonth();
        int abreiseMonat = datePickerAbreisedatum.getMonth() + 1;
        int abreiseJahr = datePickerAbreisedatum.getYear();


        if (reiseName.trim().isEmpty() || land.trim().isEmpty() || bitmap == null) {
            Toast.makeText(this, getResources().getString(R.string.Hinterlegen), Toast.LENGTH_SHORT).show();
            return;
        }

        // Intent bereitmachen fuer MainActivity
        Intent data = new Intent();
        data.putExtra(EXTRA_REISENAME, reiseName);
        data.putExtra(EXTRA_LAND, land);
        data.putExtra(EXTRA_STADT, stadt);
        data.putExtra(EXTRA_PROVINZ, provinz);
        data.putExtra(EXTRA_DAUER, dauer);
        data.putExtra(EXTRA_KONTINENT, kontinent);
        data.putExtra(EXTRA_REISETYP, reisetyp);
        data.putExtra(EXTRA_BEWERTUNG, bewertung);
        data.putExtra(EXTRA_ANZAHLREISENDE, anzahlReisende);
        data.putExtra(EXTRA_VISUMKOSTEN, (visumkostenVal - 1) * 10);
        data.putExtra(EXTRA_REISEBILD, resulturi.toString());
        data.putExtra(EXTRA_ANREISEDATUM_TAG, anreiseTag);
        data.putExtra(EXTRA_ANREISEDATUM_MONAT, anreiseMonat);
        data.putExtra(EXTRA_ANREISEDATUM_JAHR, anreiseJahr);
        data.putExtra(EXTRA_ABREISEDATUM_TAG, abreiseTag);
        data.putExtra(EXTRA_ABREISEDATUM_MONAT, abreiseMonat);
        data.putExtra(EXTRA_ABREISEDATUM_JAHR, abreiseJahr);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }

    private void initDauer() {
        numberPickerDauer.setMinValue(1);
        numberPickerDauer.setMaxValue(30);
    }

    private void initBewertung() {
        numberPickerBewertung.setMinValue(1);
        numberPickerBewertung.setMaxValue(10);
    }

    private void initAnzahlReisende() {
        numberPickerAnzahlReisende.setMinValue(1);
        numberPickerAnzahlReisende.setMaxValue(10);
    }

    private void initVisumkosten() {
        numberPickerVisumKosten.setMinValue(0);
        numberPickerVisumKosten.setMaxValue(25);
        numberPickerVisumKosten.setDisplayedValues(visum);
    }

    //EditText, Spinnern, NumberPicker werden mit XML Views verbunden
    private void initViews() {
        reiseBild = findViewById(R.id.image_view_reise_bild);
        editTextReisename = findViewById(R.id.edit_text_reisename);
        editTextLand = findViewById(R.id.edit_text_land);
        editTextStadt = findViewById(R.id.edit_text_stadt);
        editTextProvinz = findViewById(R.id.edit_text_provinz);
        numberPickerVisumKosten = findViewById(R.id.numberpicker_visumkosten);

        numberPickerDauer = findViewById(R.id.numberpicker_dauer);
        numberPickerBewertung = findViewById(R.id.numberpicker_bewertung);
        numberPickerAnzahlReisende = findViewById(R.id.numberpicker_anzahl_reisende);
        datePickerAnreisedatum = findViewById(R.id.date_picker_anreisedatum);
        datePickerAbreisedatum = findViewById(R.id.date_picker_abreisedatum);

        spinnerKontinent = findViewById(R.id.spinner_kontinent);
        ArrayAdapter<CharSequence> kontinentAdapter = ArrayAdapter.createFromResource(this, R.array.kontinente, android.R.layout.simple_spinner_item);
        kontinentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKontinent.setAdapter(kontinentAdapter);
        spinnerKontinent.setOnItemSelectedListener(this);

        spinnerReisetyp = findViewById(R.id.spinner_reisetyp);
        ArrayAdapter<CharSequence> reisetypAdapter = ArrayAdapter.createFromResource(this, R.array.reisetypen, android.R.layout.simple_spinner_item);
        reisetypAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReisetyp.setAdapter(reisetypAdapter);
        spinnerReisetyp.setOnItemSelectedListener(this);
    }

    //Die naechsten 6 Methoden sind alle fuer den ImagePicker + Crop Tool
    //Quelle: https://www.youtube.com/watch?v=HtS-qI54GKk
    private void initBild() {
        reiseBild.setOnClickListener(v -> {
            if (!checkCameraPermission()) {
                requestCameraPermission();
            }

            if (!checkStoragePermission()) {
                requestStoragePermission();
            } PickImage();
        });
    }

    //Quelle: https://www.youtube.com/watch?v=HtS-qI54GKk
    private void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    //Quelle: https://www.youtube.com/watch?v=HtS-qI54GKk
    private boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    //Quelle: https://www.youtube.com/watch?v=HtS-qI54GKk
    private void PickImage() {
        CropImage.activity().start(this);
    }

    //Quelle: https://www.youtube.com/watch?v=HtS-qI54GKk
    private void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    //Quelle: https://www.youtube.com/watch?v=HtS-qI54GKk
    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }
    //Setzt alle Views mit den erhaltenen Intent Werten
    private void auspackenFallsEdit(Intent intent) {
        setTitle(getResources().getString(R.string.ReiseBearbeiten));
        editTextReisename.setText(intent.getStringExtra(EXTRA_REISENAME));
        editTextLand.setText(intent.getStringExtra(EXTRA_LAND));
        editTextStadt.setText(intent.getStringExtra(EXTRA_STADT));
        editTextProvinz.setText(intent.getStringExtra(EXTRA_PROVINZ));
        numberPickerDauer.setValue(intent.getIntExtra(EXTRA_DAUER, 0));

        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(intent.getStringExtra(AddEditReiseActivity.EXTRA_REISEBILD)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        reiseBild.setImageBitmap(bitmap);
        kontinent = intent.getStringExtra(EXTRA_KONTINENT);
        reisetyp = intent.getStringExtra(EXTRA_REISETYP);
        numberPickerAnzahlReisende.setValue(intent.getIntExtra(EXTRA_ANZAHLREISENDE, 0));
        numberPickerBewertung.setValue(intent.getIntExtra(EXTRA_BEWERTUNG, 0));
        datePickerAnreisedatum.updateDate(intent.getIntExtra(EXTRA_ANREISEDATUM_JAHR, 0), intent.getIntExtra(EXTRA_ANREISEDATUM_MONAT, 0), intent.getIntExtra(EXTRA_ANREISEDATUM_TAG, 0));
        datePickerAbreisedatum.updateDate(intent.getIntExtra(EXTRA_ABREISEDATUM_JAHR, 0), intent.getIntExtra(EXTRA_ABREISEDATUM_MONAT, 0), intent.getIntExtra(EXTRA_ABREISEDATUM_TAG, 0));
        numberPickerVisumKosten.setValue((intent.getIntExtra(EXTRA_VISUMKOSTEN, 0)) / 10 + 1) ;
        spinnerKontinent.setSelection(resolveKontinent(kontinent));
        spinnerReisetyp.setSelection(resolveReisetyp(reisetyp));
        resulturi = resolveBitmaptoUri(this, bitmap);

    }

    // Bitmap zu Uri konvertieren
    // Quelle: https://stackoverflow.com/questions/53913401/mediastore-images-media-insertimage-is-returning-null-when-trying-to-get-image-u
    public Uri resolveBitmaptoUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    //Menueleiste
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_reise_menu, menu);
        return true;
    }

    //Elemente der Menueleiste
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_reise:
                saveReise();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Resolver fuer Spinner
    private int resolveKontinent(String s) {
        switch (s) {
            case "Afrika":
                return 0;
            case "Africa":
                return 0;
            case "Antarktis":
                return 1;
            case "Antarctica":
                return 1;
            case "Asien":
                return 2;
            case "Asia":
                return 2;
            case "Australien":
                return 3;
            case "Australia":
                return 3;
            case "Europa":
                return 4;
            case "Europe":
                return 4;
            case "Nordamerika":
                return 5;
            case "North America":
                return 5;
            case "Suedamerika":
                return 6;
            case "South America":
                return 6;
            default:
                return 6;
        }
    }

    //Resolver fuer Spinner
    private int resolveReisetyp(String r) {
        switch (r) {
            case "Aktivurlaub":
                return 0;
            case "active holiday":
                return 0;
            case "Kulturreise":
                return 1;
            case "cultural trip":
                return 1;
            case "Staedtereise":
                return 2;
            case "city trip":
                return 2;
            case "Strandurlaub":
                return 3;
            case "beach holiday":
                return 3;
            case "hiking trip":
                return 4;
            case "Wanderurlaub":
                return 4;
            case "Wellnesurlaub":
                return 5;
            case "wellness holiday":
                return 5;
            case "sonstige":
                return 6;
            case "other":
                return 6;
            default:
                return 6;
        }
    }

    // Kontinent Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getItemAtPosition(0).toString().equals("Afrika") || parent.getItemAtPosition(0).toString().equals("Africa")) {
            kontinent = parent.getItemAtPosition(position).toString();
        } else if (parent.getItemAtPosition(0).toString().equals("Aktivurlaub")||parent.getItemAtPosition(0).toString().equals("active holiday")) {
            reisetyp = parent.getItemAtPosition(position).toString();
        }
    }

    // Unimplementierte Methode fuer Kontinent Spinner
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    //QUELLE: https://www.youtube.com/watch?v=HtS-qI54GKk
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resulturi = result.getUri();
                try {
                    InputStream stream = getContentResolver().openInputStream(resulturi);
                    bitmap = BitmapFactory.decodeStream(stream);
                    reiseBild.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}