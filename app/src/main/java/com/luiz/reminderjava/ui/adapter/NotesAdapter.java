package com.luiz.reminderjava.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.luiz.reminderjava.R;
import com.luiz.reminderjava.api.models.Notes;
import com.luiz.reminderjava.databinding.ItemsNoteBinding;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Notes> notes;

    public void setNotes(List<Notes> newList) {
        this.notes = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsNoteBinding itemsNoteBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.items_note, parent, false);
        return new NotesViewHolder(itemsNoteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Notes itemAtual = notes.get(position);
        holder.view.setNote(itemAtual);
    }

    @Override
    public int getItemCount() {
        if(notes != null) {
            return notes.size();
        } else {
            return 0;
        }
    }

    static class NotesViewHolder extends RecyclerView.ViewHolder {

        public ItemsNoteBinding view;

        public NotesViewHolder(@NonNull ItemsNoteBinding itemView) {
            super(itemView.getRoot());

            this.view = itemView;
        }
    }
}
