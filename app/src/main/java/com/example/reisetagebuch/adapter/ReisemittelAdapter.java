/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reisetagebuch.R;
import com.example.reisetagebuch.entitaet.Reisemittel;
import com.example.reisetagebuch.entitaet.Unterkunft;

import java.util.ArrayList;
import java.util.List;


public class ReisemittelAdapter extends RecyclerView.Adapter<ReisemittelAdapter.ReisemittelHolder> {
    private List<Reisemittel> reisemittel = new ArrayList<>();

    @NonNull
    @Override
    public ReisemittelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reisemittel_item, parent, false);

        return new ReisemittelHolder(itemView);
    }

    // TextViews werden mit Werten befuellt
    @Override
    public void onBindViewHolder(@NonNull ReisemittelHolder holder, int position) {
        Reisemittel currentReisemittel = reisemittel.get(position);
        holder.textViewReisemitteltyp.setText(currentReisemittel.getReisemitteltyp());
        holder.textViewReisemittelKosten.setText(String.valueOf(currentReisemittel.getKosten()) + " €");
    }

    @Override
    public int getItemCount() {
        return reisemittel.size();
    }

    public void setReisemittel(List<Reisemittel> reisemittel) {
        this.reisemittel = reisemittel;
        notifyDataSetChanged();
    }

    //Position eines Items fuer die Swipefunktion
    public Reisemittel getReisemittelAt(int position) {
        return reisemittel.get(position);
    }

    //Weist die allen TextView die Views aus der XML zu
    class ReisemittelHolder extends RecyclerView.ViewHolder {
        private TextView textViewReisemitteltyp;
        private TextView textViewReisemittelKosten;

        public ReisemittelHolder(@NonNull View itemView) {
            super(itemView);

            textViewReisemitteltyp = itemView.findViewById(R.id.text_view_reisemittel_typ);
            textViewReisemittelKosten = itemView.findViewById(R.id.text_view_reisemittel_kosten);
        }
    }


}
