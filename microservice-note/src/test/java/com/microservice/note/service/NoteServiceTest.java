package com.microservice.note.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.microservice.note.model.Note;
import com.microservice.note.repository.NoteRepository;
import com.microservice.note.service.NoteService;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {
	
	@InjectMocks
	NoteService noteService;
	
	@Mock
	NoteRepository noteRepository;


	
	Note noteDtoTest = new Note(
			1, "My new patientNote", "idNote");
	
	
	@Test
	public void readAllNotesMethodTest()
	{
		List<Note> list = new ArrayList<>();
		Note note = new Note (1, "My comment");
		note.setId("id");
		list.add(note);
	
		when(noteRepository.findByIdPatient(1)).thenReturn(list);
		
		List<Note> noteList = noteService.getAllNotes(1);
		
		assertEquals(noteList.get(0).getComment(), note.getComment());
		
		
	}
	
	@Test
	public void createPatientMethodTest()
	{
		Note newNoteTest = noteService.create(1, noteDtoTest);
		assertEquals(1, newNoteTest.getIdPatient());
		assertEquals("My new patientNote", newNoteTest.getComment());
	
	}
	
	@Test
	public void updatePatientMethodTest()
	{
		Note newNoteTest = new Note();
		when(noteRepository.findById("id patientNote")).thenReturn(Optional.of(newNoteTest));
		
		noteService.update("id patientNote", noteDtoTest);
		
		assertEquals("My new patientNote", newNoteTest.getComment());
		
		
	}
	
	@Test
	public void readPatientMethodTest()
	{
		Note newNoteTest = new Note();
		newNoteTest.setId("this is the ID of the note");
		newNoteTest.setComment("This is the text of the note");
		
		when(noteRepository.findById("this is the ID of the note")).thenReturn(Optional.of(newNoteTest));
		
		Note readNote = noteService.get("this is the ID of the note");
		
		assertEquals("This is the text of the note", readNote.getComment());
		
	}
	
	@Test
	public void deletePatientMethodTest()
	{
		Note note = new Note (1, "My new patientNote");
		
		note.setId("id");
		noteService.delete("id");
		noteRepository.delete(note);
		verify(noteRepository).delete(note);
	}
	
	
}
