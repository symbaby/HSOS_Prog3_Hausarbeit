/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reisetagebuch.entitaet.Aktivitaet;
import com.example.reisetagebuch.R;
import com.example.reisetagebuch.entitaet.Unterkunft;

import java.util.ArrayList;
import java.util.List;

public class AktivitaetAdapter extends RecyclerView.Adapter<AktivitaetAdapter.AktivitaetHolder> {
    private List<Aktivitaet> aktivitaets = new ArrayList<>();

    @NonNull
    @Override
    public AktivitaetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.aktivitaet_item, parent, false);
        return new AktivitaetHolder(itemView);
    }

    // TextViews werden mit Werten befuellt
    @Override
    public void onBindViewHolder(@NonNull AktivitaetHolder holder, int position) {
        Aktivitaet currentAktivitaet = aktivitaets.get(position);
        holder.textViewAktivitaetTyp.setText(currentAktivitaet.getAktivitaetsTyp());
        holder.textViewAktivitaetUhrzeit.setText(String.valueOf(currentAktivitaet.getUhrzeitStunde()) + ":00");
        holder.textViewAktivitaetBewertung.setText(String.valueOf(currentAktivitaet.getAktivitaetsBewertung()) +"/10");
        holder.textViewAktivitaetKosten.setText(String.valueOf(currentAktivitaet.getAktivitaetsKosten()) + "€");
        holder.textViewAktivitaetBeschreibung.setText(currentAktivitaet.getAktivitaetsBeschreibung());
    }

    @Override
    public int getItemCount() {
        return aktivitaets.size();
    }

    public void setAktivitaeten(List<Aktivitaet> aktivitaeten) {
        this.aktivitaets = aktivitaeten;
        notifyDataSetChanged();
    }

    //Position eines Items fuer die Swipefunktion
    public Aktivitaet getAktivitaetAt(int position) {
        return aktivitaets.get(position);
    }

    //Weist die allen TextView die Views aus der XML zu
    class AktivitaetHolder extends RecyclerView.ViewHolder {
        private TextView textViewAktivitaetTyp;
        private TextView textViewAktivitaetUhrzeit;
        private TextView textViewAktivitaetBewertung;
        private TextView textViewAktivitaetKosten;
        private TextView textViewAktivitaetBeschreibung;

        public AktivitaetHolder(@NonNull View itemView) {
            super(itemView);
            textViewAktivitaetTyp = itemView.findViewById(R.id.text_view_aktivitaet_typ);
            textViewAktivitaetUhrzeit = itemView.findViewById(R.id.text_view_aktivitaet_uhrzeit);
            textViewAktivitaetBewertung = itemView.findViewById(R.id.text_view_aktivitaet_bewertung);
            textViewAktivitaetKosten = itemView.findViewById(R.id.text_view_aktivitaet_kosten);
            textViewAktivitaetBeschreibung = itemView.findViewById(R.id.text_view_aktivitaet_beschreibung);
        }
    }


}
