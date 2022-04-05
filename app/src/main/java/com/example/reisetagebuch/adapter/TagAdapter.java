/*Die Datei ist in Pair-Programming entstanden.
 Authoren des Codes: Johannes Belaschow, Berkan Yildiz */
package com.example.reisetagebuch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reisetagebuch.R;
import com.example.reisetagebuch.entitaet.Tag;
import com.example.reisetagebuch.entitaet.Unterkunft;

import java.util.ArrayList;
import java.util.List;


public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagHolder> {
    private List<Tag> tags = new ArrayList<>();

    private Context context;

    private Button buttonAktivitaet;
    private OnAktivitaetButtonClickListener buttonAktivitaetClickListener;

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tag_item, parent, false);
        context=parent.getContext();
        return new TagHolder(itemView);
    }

    // TextViews werden mit Werten befuellt
    @Override
    public void onBindViewHolder(@NonNull TagHolder holder, int position) {
        Tag currentTag = tags.get(position);
        holder.textViewTagTag.setText(String.valueOf(currentTag.getDatumTag()));
        holder.textViewTagMonat.setText(resolveMonat(currentTag.getDatumMonat()));
        holder.textViewTagJahr.setText(String.valueOf(currentTag.getDatumJahr()));
    }
    //Um die Monatszahl in einen String zu bekommen
    private String resolveMonat(int m) {
        switch (m) {
            case 1:
                return context.getString(R.string.Januar);
            case 2:
                return context.getString(R.string.Februar);
            case 3:
                return context.getString(R.string.Maerz);
            case 4:
                return context.getString(R.string.April);
            case 5:
                return context.getString(R.string.Mai);
            case 6:
                return context.getString(R.string.Juni);
            case 7:
                return context.getString(R.string.Juli);
            case 8:
                return context.getString(R.string.August);
            case 9:
                return context.getString(R.string.September);
            case 10:
                return context.getString(R.string.Oktober);
            case 11:
                return context.getString(R.string.November);
            case 12:
                return context.getString(R.string.Dezember);
            default:
                return context.getString(R.string.Dezember);
        }
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public void setTage(List<Tag> tags) {
        this.tags = tags;
        notifyDataSetChanged();
    }

    //Position eines Items fuer die Swipefunktion
    public Tag getTagAt(int position) {
        return tags.get(position);
    }

    //Weist die allen TextView die Views aus der XML zu
    class TagHolder extends RecyclerView.ViewHolder {
        private TextView textViewTagTag;
        private TextView textViewTagMonat;
        private TextView textViewTagJahr;

        public TagHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    buttonAktivitaetClickListener.onAktivitaetButtonClick(tags.get(position));
                }
            });

            buttonAktivitaet = itemView.findViewById(R.id.button_tag_item_aktivitaet_hinzufuegen);
            textViewTagTag = itemView.findViewById(R.id.text_view_tag_tag);
            textViewTagMonat = itemView.findViewById(R.id.text_view_tag_monat);
            textViewTagJahr = itemView.findViewById(R.id.text_view_tag_jahr);

            buttonAktivitaet.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (buttonAktivitaetClickListener != null && position != RecyclerView.NO_POSITION) {

                    buttonAktivitaetClickListener.onAktivitaetButtonClick(tags.get(position));
                }
            });
        }
    }

    //ADD AKTIVITAETEN
    public interface OnAktivitaetButtonClickListener {
        void onAktivitaetButtonClick(Tag tag);
    }

    public void setOnAktivitaetButtonClickListener(TagAdapter.OnAktivitaetButtonClickListener buttonAktivitaetClickListener) {
        this.buttonAktivitaetClickListener = buttonAktivitaetClickListener;
    }
}


