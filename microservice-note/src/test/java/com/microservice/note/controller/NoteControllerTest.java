package com.microservice.note.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.microservice.note.exception.NoteNotFoundException;
import com.microservice.note.model.Note;
import com.microservice.note.repository.NoteRepository;
import com.microservice.note.service.NoteService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NoteControllerTest {
	
	@Autowired
	private WebApplicationContext context;
		
	@InjectMocks
	NoteController noteController;
	
	@MockBean
	NoteService noteService;

	@Mock
	NoteRepository noteRepository;
	
	private MockMvc mockMvc;
	private Note noteTest;
	
	@BeforeEach
    public void setupMockmvc() {
		Note noteTest = new Note(1, "My new patientNote", "idNote");
		String response = "test";
		
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void getAllNotesByPatient() throws Exception {
        List <Note> listNote = new ArrayList<>();
        listNote.add(noteTest);
        when(noteService.getAllNotes(1)).thenReturn(listNote);

        MvcResult result = mockMvc.perform(get("/patient/1/notes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
    @Test
    public void postNote() throws Exception {
    	
        MvcResult result = mockMvc.perform(post("/patient/1/notes/add")
        		.content("{\"idPatient\" : 1 ,\"comment\" : \"Test User\"}")
        		.contentType(MediaType.APPLICATION_JSON))
        		.andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
        
        assertEquals(result.getResponse().getStatus(),201);
    }
    @Test
    public void getUpdateNote() throws Exception {
        List <Note> listNote = new ArrayList<>();
        listNote.add(noteTest);
        when(noteService.getAllNotes(1)).thenReturn(listNote);

        MvcResult result = mockMvc.perform(get("/update/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
   
    }
    @Test
    public void getUpdateNoteError() throws Exception {
    	when(noteService.get("1")).thenThrow(NoteNotFoundException.class);
    	MvcResult result = mockMvc.perform(get("/update/1"))
    			.andDo(print())
    			.andExpect(status().isNotFound())
    			.andReturn();
    }

    
    @Test
    public void getDelete() throws Exception {
        String response = "Deletion successful !";
        when(noteService.delete("1")).thenReturn(true);
        MvcResult result = mockMvc.perform(get("/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }
    
    @Test
    public void getDeleteError() throws Exception {
        String response = "Note was not found.";
        when(noteService.delete("1")).thenReturn(false);
        MvcResult result = mockMvc.perform(get("/delete/1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(response));
    }
    
	
	
}
