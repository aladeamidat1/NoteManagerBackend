package org.example.services;

import org.example.data.models.Note;
import org.example.dto.request.AddNoteRequest;
import org.example.dto.request.UpdateNoteRequest;
import org.example.dto.response.AddNoteResponse;
import org.example.dto.response.UpdateNoteResponse;

import java.util.List;

public interface NoteService {


    AddNoteResponse addNote(AddNoteRequest addNoteRequest);

    UpdateNoteResponse updateNote(UpdateNoteRequest updateNoteRequest);

    List<Note> getNotesByUser(String userId);

    void deleteNote(String noteId, String userId);
}
