package com.luiz.reminderjava.api.responses;

import com.luiz.reminderjava.api.models.Notes;

import java.util.List;

public class NotesResponse {
    private List<Notes> notes;

    public List<Notes> getNotes() {
        return notes;
    }
}
