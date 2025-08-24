package com.microservice.note.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.microservice.note.model.Note;
import com.microservice.note.repository.NoteRepository;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

	@InjectMocks
	NoteService noteService;

	@Mock
	NoteRepository noteRepository;

	Note noteDtoTest = new Note(1, "My new patientNote", "idNote");

	@Test
	public void readAllNotesMethodTest() {
		List<Note> list = new ArrayList<>();
		Note note = new Note(1, "My comment");
		note.setId("id");
		list.add(note);

		when(noteRepository.findByIdPatient(1)).thenReturn(list);

		List<Note> noteList = noteService.getAllNotes(1);

		assertEquals(note.getComment(), noteList.get(0).getComment());
	}
	@Test
	public void createPatientMethodTest() {
		when(noteRepository.save(any(Note.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Note newNoteTest = noteService.create(1, noteDtoTest);

		assertEquals(1, newNoteTest.getIdPatient());
		assertEquals("My new patientNote", newNoteTest.getComment());
	}


	@Test
	public void updatePatientMethodTest() {
		Note existingNote = new Note();
		when(noteRepository.findById("idNote")).thenReturn(Optional.of(existingNote));
		when(noteRepository.save(existingNote)).thenReturn(existingNote); // Mock save si utilisé

		noteService.update("idNote", noteDtoTest);

		assertEquals("My new patientNote", existingNote.getComment());
	}

	@Test
	public void readPatientMethodTest() {
		Note existingNote = new Note();
		existingNote.setId("this is the ID of the note");
		existingNote.setComment("This is the text of the note");

		when(noteRepository.findById("this is the ID of the note")).thenReturn(Optional.of(existingNote));

		Note readNote = noteService.get("this is the ID of the note");

		assertEquals("This is the text of the note", readNote.getComment());
	}

	@Test
	public void deletePatientMethodTest() {
		String noteId = "id";

		// Mock existsById
		when(noteRepository.existsById(noteId)).thenReturn(true);

		boolean result = noteService.delete(noteId);

		assertTrue(result);
		verify(noteRepository).deleteById(noteId);
	}
}
