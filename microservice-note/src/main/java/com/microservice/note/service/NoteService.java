package com.microservice.note.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.note.exception.NoteNotFoundException;
import com.microservice.note.model.Note;
import com.microservice.note.repository.NoteRepository;

@Service
public class NoteService{
	
	private static final Logger logger = LogManager.getLogger(NoteService.class);
	
	@Autowired
	private NoteRepository noteRepository;

	public List<Note> getAllNotes(Integer idPatient){
		List<Note> listNotes = noteRepository.findByIdPatient(idPatient);
		return listNotes;
	}

	public Note create(Integer idPatient, Note note){
		Note newNote = new Note (note.getComment());
		newNote.setIdPatient(idPatient);
		noteRepository.save(newNote);
		logger.info("New note create");
		return newNote;
	}
	
		public Note get(String id){
		Optional<Note> note = noteRepository.findById(id);
		if(note.isPresent()) {
			return note.get();
		}
		throw new NoteNotFoundException("Note not found");
	}

	public Note update(String id, Note note){
		Optional<Note> noteUpdate = noteRepository.findById(id);
		
		if(noteUpdate.isPresent()) {
			noteUpdate.get().setComment(note.getComment());
			noteRepository.save(note);
			logger.info("UPDATE :  note id : "+id);
			return note;
		}
		throw new NoteNotFoundException("Note not found");
	}
	
	public boolean delete(String id){
		if(noteRepository.findById(id).isPresent()) {
			logger.info("get note id : "+id);
			noteRepository.deleteById(id);
			return true;
		}else {
			logger.warn("Deletion abort because note was not found...");
			return false;
		}
		
		
	}

}
