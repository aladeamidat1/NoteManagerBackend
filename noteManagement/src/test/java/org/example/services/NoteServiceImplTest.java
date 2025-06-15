package org.example.services;

import org.example.data.models.Note;
import org.example.data.models.User;
import org.example.data.repository.NoteRepository;
import org.example.data.repository.UserRepository;
import org.example.dto.request.AddNoteRequest;
import org.example.dto.request.LoginRequest;
import org.example.dto.request.UpdateNoteRequest;
import org.example.dto.response.AddNoteResponse;
import org.example.dto.response.LoginResponse;
import org.example.dto.response.UpdateNoteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class NoteServiceImplTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    NoteService noteService;
    @Autowired
    NoteRepository noteRepository;

    @Test
    public void addNote() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse);

        User user = userRepository.findByUsername("username1");
        assertNotNull(user, "User not found");
        String userId = user.getId();

        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle("Boy are sucm");
        addNoteRequest.setContent("i am soo tired");
        addNoteRequest.setUserId(userId);

        AddNoteResponse response = noteService.addNote(addNoteRequest);
        assertEquals("Note added successfully", response.getMessage());
    }
    @Test
    public void updateNote(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse);

        User user = userRepository.findByUsername("username1");
        assertNotNull(user, "User not found");
        String userId = user.getId();

        // Find a note for the user
        Note note = noteRepository.findByUserId(userId).stream().findFirst().orElse(null);
        assertNotNull(note, "Note not found");
        String noteId = note.getId();


        UpdateNoteRequest updateNoteRequest = new UpdateNoteRequest();
        updateNoteRequest.setTitle("sweet boy");
        updateNoteRequest.setContent("my shayala must be a bad boii😂😂😂");
        updateNoteRequest.setUserId(userId);
        updateNoteRequest.setNoteId(noteId);

        UpdateNoteResponse updateNoteResponse = noteService.updateNote(updateNoteRequest);
        assertEquals("Note updated successfully", updateNoteResponse.getMessage());
    }
    @Test
    public void getNotesByUser() {
        // Login and get user
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse);

        // Get user from repository
        User user = userRepository.findByUsername("username1");
        assertNotNull(user, "User not found");
        String userId = user.getId();

        // Get notes for user
        List<Note> notes = noteService.getNotesByUser(userId);
        assertNotNull(notes);
        System.out.println(notes);
    }
    @Test
    public void deleteNote() {
        // Login and get user
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");
        LoginResponse loginResponse = userService.login(loginRequest);
        assertNotNull(loginResponse);

        // Get user from repository
        User user = userRepository.findByUsername("username1");
        assertNotNull(user, "User not found");
        String userId = user.getId();

        // Find a note for the user
        Note note = noteRepository.findByUserId(userId).stream().findFirst().orElse(null);
        assertNotNull(note, "Note not found");
        String noteId = note.getId();

        // Delete note
        noteService.deleteNote(noteId, userId);

        // Verify note is deleted
        assertFalse(noteRepository.existsById(noteId));
    }


}