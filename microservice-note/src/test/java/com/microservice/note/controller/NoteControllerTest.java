package com.microservice.note.controller;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.note.exception.NoteNotFoundException;
import com.microservice.note.model.Note;
import com.microservice.note.service.NoteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @BeforeEach
    public void setup() {
        // On inclut un handler local pour simuler le RestControllerAdvice global
        mockMvc = MockMvcBuilders
                .standaloneSetup(noteController)
                .setControllerAdvice(new TestExceptionHandler())
                .build();
    }

    @Test
    public void getAllNotesForPatient_shouldReturnList() throws Exception {
        Note noteTest = new Note(1, "My new patientNote", "noteId1");
        List<Note> list = new ArrayList<>();
        list.add(noteTest);

        when(noteService.getAllNotes(1)).thenReturn(list);

        mockMvc.perform(get("/patient/1/notes"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(list)));
    }

    @Test
    public void postNote_shouldCreateNote() throws Exception {
        Note incoming = new Note();
        incoming.setComment("Test User");
        incoming.setIdPatient(1);

        Note created = new Note(1, "Test User", "noteId2");
        when(noteService.create(1, incoming)).thenReturn(created);

        String payload = objectMapper.writeValueAsString(incoming);

        mockMvc.perform(post("/patient/1/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(created)));
    }

    @Test
    public void getNote_shouldReturnNote() throws Exception {
        Note note = new Note(1, "Some comment", "note123");
        when(noteService.get("note123")).thenReturn(note);

        mockMvc.perform(get("/notes/note123"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(note)));
    }

    @Test
    public void getNote_notFound_shouldReturn404() throws Exception {
        when(noteService.get("missing")).thenThrow(new NoteNotFoundException("Note with ID missing was not found"));

        mockMvc.perform(get("/notes/missing"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Note with ID missing was not found"));
    }

    @Test
    public void updateNote_shouldReturnUpdated() throws Exception {
        Note incoming = new Note();
        incoming.setComment("Updated comment");

        Note updated = new Note(1, "Updated comment", "noteUpd");
        when(noteService.update("noteUpd", incoming)).thenReturn(updated);

        String payload = objectMapper.writeValueAsString(incoming);

        mockMvc.perform(put("/notes/noteUpd")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(updated)));
    }

    @Test
    public void deleteNote_shouldReturnNoContent() throws Exception {
        // noteService.delete now void and throws if not found
        // on simule le succès : pas d'exception
        mockMvc.perform(delete("/notes/noteDel"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNote_notFound_shouldReturn404() throws Exception {
        doThrow(new NoteNotFoundException("Note with ID badId was not found"))
                .when(noteService).delete("badId");

        mockMvc.perform(delete("/notes/badId"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Note with ID badId was not found"));
    }
    // Handler minimal pour transformer NoteNotFoundException en 404 comme dans ton application réelle
    @RestControllerAdvice
    static class TestExceptionHandler {
        @ExceptionHandler(NoteNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String handleNotFound(NoteNotFoundException ex) {
            return ex.getMessage();
        }
    }
}
