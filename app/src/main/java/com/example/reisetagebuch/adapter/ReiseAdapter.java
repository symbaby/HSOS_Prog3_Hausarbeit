/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reisetagebuch.R;
import com.example.reisetagebuch.entitaet.Reise;

import java.util.ArrayList;
import java.util.List;

public class ReiseAdapter extends RecyclerView.Adapter<ReiseAdapter.ReiseHolder> {
    private List<Reise> reisen = new ArrayList();
    private OnReiseClickListener listener;
    private Context context;


    private OnReiseButtonClickListener buttonEditClickListener;

    private OnReisemittelButtonClickListener buttonReisemittelClickListener;
    private OnUnterkunftButtonClickListener buttonUnterkunftClickListener;
    private OnTagButtonClickListener buttonTagClickListener;

    @NonNull
    @Override
    public ReiseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View reiseView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reise_item, parent, false);
        context = parent.getContext();
        return new ReiseHolder(reiseView);
    }

    // TextViews werden mit Werten befuellt
    @Override
    public void onBindViewHolder(@NonNull ReiseHolder holder, int position) {
        Reise currentReise = reisen.get(position);
        holder.imageViewReiseBild.setImageBitmap(currentReise.getBild());
        holder.textViewReise.setText(currentReise.getReiseName());
        holder.textViewKontinent.setText(currentReise.getKontinent());
        holder.textViewLand.setText(currentReise.getLand());
        holder.textViewDauer.setText(String.valueOf(currentReise.getReiseDauer())+" "+context.getString(R.string.Tage));
        holder.textViewReisetyp.setText(currentReise.getReisetyp());

        //Final String für Visumkosten
        if (currentReise.getVisumKosten() > 0 && currentReise.getVisumKosten() < 250) {
            holder.textViewVisumkosten.setText(currentReise.getVisumKosten() + 10 + " €"); // Funktion für alles zusammenzählen
        } else {
            holder.textViewVisumkosten.setText(0 + " €");
        }

        holder.textViewBewertung.setText(context.getString(R.string.Bewertung)+" " + currentReise.getReiseBewertung()+"/10");

        if (currentReise.getStadt() != null) {
            holder.textViewStadt.setText(currentReise.getStadt());
        } else {
            holder.textViewStadt.setText("Stadt: -");
        }

        //Final String für An- und Abreisedatum
        holder.textViewProvinz.setText(currentReise.getProvinz());
        holder.textViewAnreiseDatum.setText(currentReise.getAnreiseTag() + "."
                + currentReise.getAnreiseMonat() + "."
                + currentReise.getAnreiseJahr());

        holder.textViewAbreiseDatum.setText(currentReise.getAbreiseTag() + "."
                + currentReise.getAbreiseMonat() + "."
                + currentReise.getAbreiseJahr());
    }

    @Override
    public int getItemCount() {
        return reisen.size();
    }

    public void setReisen(List<Reise> reisen) {
        this.reisen = reisen;
        notifyDataSetChanged();
    }

    //Position eines Items fuer die Swipefunktion
    public Reise getReiseAt(int position) {
        return reisen.get(position);
    }

    //Weist die allen TextView die Views aus der XML zu + ClickListener
    class ReiseHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewReiseBild;
        private TextView textViewReise;
        private TextView textViewKontinent;
        private TextView textViewLand;
        private TextView textViewDauer;
        private TextView textViewStadt;
        private TextView textViewProvinz;
        private TextView textViewAnreiseDatum;
        private TextView textViewAbreiseDatum;

        // TEST
        private Button buttonEdit;
        private Button buttonReisemittel;
        private Button buttonUnterkunft;
        private Button buttonTag;

        // Bitmap private BitMap bitmapBild
        private TextView textViewReisetyp;
        private TextView textViewVisumkosten;
        private TextView textViewBewertung;


        public ReiseHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    buttonEditClickListener.onReiseButtonClick(reisen.get(position));
                }
            });

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onReiseClick(reisen.get(position));
                }
            });

            imageViewReiseBild = itemView.findViewById(R.id.image_view_reise_reiseBild);
            buttonEdit = itemView.findViewById(R.id.button_reise_item_edit);
            buttonReisemittel = itemView.findViewById(R.id.button_reise_item_reisemittel);
            buttonUnterkunft = itemView.findViewById(R.id.button_reise_item_unterkunft);
            buttonTag = itemView.findViewById(R.id.button_reise_item_tag);
            textViewReise = itemView.findViewById(R.id.text_view_reise);
            textViewKontinent = itemView.findViewById(R.id.text_view_reise_kontinent);
            textViewLand = itemView.findViewById(R.id.text_view_reise_land);
            textViewDauer = itemView.findViewById(R.id.text_view_reise_dauer);
            textViewReisetyp = itemView.findViewById(R.id.text_view_reise_reisetyp);
            textViewVisumkosten = itemView.findViewById(R.id.text_view_reise_visumkosten);
            textViewStadt = itemView.findViewById(R.id.text_view_reise_stadt);
            textViewProvinz = itemView.findViewById(R.id.text_view_reise_provinz);
            textViewAnreiseDatum = itemView.findViewById(R.id.text_view_reise_anreisedatum);
            textViewAbreiseDatum = itemView.findViewById(R.id.text_view_reise_abreisedatum);
            textViewBewertung = itemView.findViewById(R.id.text_view_reise_bewertung);

            // EDIT BUTTON Implementation
            buttonEdit.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (buttonEditClickListener != null && position != RecyclerView.NO_POSITION) {
                    buttonEditClickListener.onReiseButtonClick(reisen.get(position));
                }
            });

            // Reisemittel Button Implementation
            buttonReisemittel.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (buttonReisemittelClickListener != null && position != RecyclerView.NO_POSITION) {
                    buttonReisemittelClickListener.onReisemittelButtonClick(reisen.get(position));
                }
            });

            // Unterkunft Button Implementation
            buttonUnterkunft.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (buttonUnterkunftClickListener != null && position != RecyclerView.NO_POSITION) {
                    buttonUnterkunftClickListener.onUnterkunftButtonClick(reisen.get(position));
                }
            });

            // Tag Button Implementation
            buttonTag.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (buttonTagClickListener != null && position != RecyclerView.NO_POSITION) {
                    buttonTagClickListener.onTagButtonClick(reisen.get(position));
                }
            });
        }
    }

    //REISE EDIT
    public interface OnReiseButtonClickListener {
        void onReiseButtonClick(Reise reise);
    }

    public void setOnReiseButtonClickListener(OnReiseButtonClickListener onReiseButtonClickListener) {
        this.buttonEditClickListener = onReiseButtonClickListener;
    }

    //REISEMITTEL ADD
    public interface OnReisemittelButtonClickListener {
        void onReisemittelButtonClick(Reise reise);
    }

    public void setOnReisemittelButtonClickListener(OnReisemittelButtonClickListener onReisemittelButtonClickListener) {
        this.buttonReisemittelClickListener = onReisemittelButtonClickListener;
    }

    // UNTERKUNFT ADD
    public interface OnUnterkunftButtonClickListener {
        void onUnterkunftButtonClick(Reise reise);
    }

    public void setOnUnterkunftButtonClickListener(OnUnterkunftButtonClickListener onUnterkunftButtonClickListener) {
        this.buttonUnterkunftClickListener = onUnterkunftButtonClickListener;
    }

    // TAG ADD
    public interface OnTagButtonClickListener {
        void onTagButtonClick(Reise reise);
    }

    public void setOnTagButtonClickListener(OnTagButtonClickListener onTagButtonClickListener) {
        this.buttonTagClickListener = onTagButtonClickListener;
    }


    // TO BE DONE
    public interface OnReiseClickListener {
        void onReiseClick(Reise reise);
    }
}
