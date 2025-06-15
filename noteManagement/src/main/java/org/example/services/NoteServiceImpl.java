package org.example.services;

import org.example.data.models.Note;
import org.example.data.models.User;
import org.example.data.repository.NoteRepository;
import org.example.data.repository.UserRepository;
import org.example.dto.request.AddNoteRequest;
import org.example.dto.request.UpdateNoteRequest;
import org.example.dto.response.AddNoteResponse;
import org.example.dto.response.UpdateNoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public AddNoteResponse addNote(AddNoteRequest addNoteRequest) {
        User user = userRepository.findById(addNoteRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Note note = new Note();
        note.setTitle(addNoteRequest.getTitle());
        note.setContent(addNoteRequest.getContent());
        note.setUserId(addNoteRequest.getUserId());
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());

        Note savedNote = noteRepository.save(note);
        user.getNotes().add(savedNote);
        userRepository.save(user);

        return buildAddNoteResponse();
    }

    @Override
    public UpdateNoteResponse updateNote(UpdateNoteRequest updateNoteRequest) {
        System.out.println(updateNoteRequest.getUserId());
        System.out.println(updateNoteRequest.getNoteId());
        // First verify the user exists
        userRepository.findById(updateNoteRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Find the note by its ID (not user ID)
        Note note = noteRepository.findById(updateNoteRequest.getNoteId())
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Verify the note belongs to the user
        if (!note.getUserId().equals(updateNoteRequest.getUserId())) {
            throw new RuntimeException("Note does not belong to user");
        }

        note.setTitle(updateNoteRequest.getTitle());
        note.setContent(updateNoteRequest.getContent());
        note.setUpdatedAt(LocalDateTime.now());

        noteRepository.save(note);
        return buildUpdateNoteResponse();
    }

    @Override
    public List<Note> getNotesByUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return noteRepository.findByUserId(userId);
    }


    @Override
    public void deleteNote(String noteId, String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));
        if (!note.getUserId().equals(userId)) {
            throw new RuntimeException("Note does not belong to user");
        }
        noteRepository.deleteById(noteId);
    }

    private AddNoteResponse buildAddNoteResponse() {
        AddNoteResponse response = new AddNoteResponse();
        response.setMessage("Note added successfully");
        return response;
    }

    private UpdateNoteResponse buildUpdateNoteResponse() {
        UpdateNoteResponse response = new UpdateNoteResponse();
        response.setMessage("Note updated successfully");
        return response;
    }
}