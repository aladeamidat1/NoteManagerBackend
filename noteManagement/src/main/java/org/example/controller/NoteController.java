package org.example.controller;

import org.example.data.models.Note;
import org.example.dto.request.AddNoteRequest;
import org.example.dto.request.UpdateNoteRequest;
import org.example.dto.response.UpdateNoteResponse;
import org.example.services.NoteService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteController {
    @Autowired
    NoteService noteService;
    @Autowired
    UserService userService;

    @PostMapping("/addNotes")
    public ResponseEntity<?> addNote(@RequestBody AddNoteRequest  addNoteRequest) {
        try{
            return new ResponseEntity<>(noteService.addNote(addNoteRequest), HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UpdateNoteResponse> updateNote(@PathVariable("id") String noteId, @RequestBody UpdateNoteRequest updateNoteRequest) {
        updateNoteRequest.setNoteId(noteId);
        UpdateNoteResponse response = noteService.updateNote(updateNoteRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{userId}/notes")
    public ResponseEntity<List<Note>> getNotesByUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(noteService.getNotesByUser(userId));
    }



    @DeleteMapping("/{noteId}")
    public ResponseEntity<String> deleteNote(@PathVariable String noteId, @RequestHeader("userId") String userId) {
        noteService.deleteNote(noteId, userId);
        return ResponseEntity.ok("Note deleted successfully");
    }

}
