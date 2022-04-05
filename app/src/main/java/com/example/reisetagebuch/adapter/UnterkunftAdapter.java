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
import com.example.reisetagebuch.entitaet.Reise;
import com.example.reisetagebuch.entitaet.Unterkunft;

import java.util.ArrayList;
import java.util.List;

public class UnterkunftAdapter extends RecyclerView.Adapter<UnterkunftAdapter.UnterkunftHolder> {
    private List<Unterkunft> unterkuenfte = new ArrayList<>();

    @NonNull
    @Override
    public UnterkunftHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unterkunft_item, parent, false);

        return new UnterkunftHolder(itemView);
    }

    // TextViews werden mit Werten befuellt
    @Override
    public void onBindViewHolder(@NonNull UnterkunftHolder holder, int position) {
        Unterkunft currentUnterkunft = unterkuenfte.get(position);
        holder.textViewUnterkunftTyp.setText(currentUnterkunft.getUnterkunftstyp());
        holder.textViewUnterkuftPreis.setText(String.valueOf(currentUnterkunft.getPreis()) + " €");
        holder.textViewUnterkuftBewertung.setText(String.valueOf(currentUnterkunft.getBewertung()) + "/10");
        holder.textViewUnterkuftLand.setText(String.valueOf(currentUnterkunft.getLand()));
        holder.textViewUnterkuftStadt.setText(String.valueOf(currentUnterkunft.getStadt()));
        holder.textViewUnterkuftName.setText(String.valueOf(currentUnterkunft.getName()));
        holder.textViewUnterkuftDauer.setText(String.valueOf(currentUnterkunft.getDauer()));
    }

    @Override
    public int getItemCount() {
        return unterkuenfte.size();
    }

    public void setUnterkuenfte(List<Unterkunft> unterkuenfte) {
        this.unterkuenfte = unterkuenfte;
        notifyDataSetChanged();
    }

    //Position eines Items fuer die Swipefunktion
    public Unterkunft getUnterkunftAt(int position) {
        return unterkuenfte.get(position);
    }

    public void setSpecificUnterkunft(List<Unterkunft> unterkuenfte) {
        this.unterkuenfte = unterkuenfte;
        notifyDataSetChanged();
    }

    //Weist die allen TextView die Views aus der XML zu
    class UnterkunftHolder extends RecyclerView.ViewHolder {
        private TextView textViewUnterkunftTyp;
        private TextView textViewUnterkuftPreis;
        private TextView textViewUnterkuftBewertung;
        private TextView textViewUnterkuftLand;
        private TextView textViewUnterkuftStadt;
        private TextView textViewUnterkuftName;
        private TextView textViewUnterkuftDauer;

        public UnterkunftHolder(@NonNull View itemView) {
            super(itemView);

            textViewUnterkunftTyp = itemView.findViewById(R.id.text_view_unterkunft_unterkufttyp);
            textViewUnterkuftPreis = itemView.findViewById(R.id.text_view_unterkunft_preis);
            textViewUnterkuftBewertung = itemView.findViewById(R.id.text_view_unterkunft_bewertung);
            textViewUnterkuftLand = itemView.findViewById(R.id.text_view_unterkunft_land);
            textViewUnterkuftStadt = itemView.findViewById(R.id.text_view_unterkunft_stadt);
            textViewUnterkuftName = itemView.findViewById(R.id.text_view_unterkunft_name);
            textViewUnterkuftDauer = itemView.findViewById(R.id.text_view_unterkunft_dauer);
        }
    }
}
