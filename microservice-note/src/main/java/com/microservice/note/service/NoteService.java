package com.microservice.note.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.note.exception.NoteNotFoundException;
import com.microservice.note.model.Note;
import com.microservice.note.repository.NoteRepository;

@Service
public class NoteService {

	private static final Logger logger = LogManager.getLogger(NoteService.class);

	private final NoteRepository noteRepository;

	@Autowired
	public NoteService(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	/**
	 * Récupère toutes les notes d'un patient.
	 */
	public List<Note> getAllNotes(Integer patientId) {
		logger.info("Fetching all notes for patientId={}", patientId);
		return noteRepository.findByIdPatient(patientId);
	}

	/**
	 * Crée une nouvelle note pour un patient.
	 */
	public Note create(Integer patientId, Note note) {
		Note toSave = new Note(note.getComment());
		toSave.setIdPatient(patientId);
		Note saved = noteRepository.save(toSave);
		logger.info("Created new note with id={} for patientId={}", saved.getId(), patientId);
		return saved;
	}

	/**
	 * Récupère une note par son ID, ou lève si introuvable.
	 */
	public Note get(String id) {
		Note note = noteRepository.findById(id)
				.orElseThrow(() -> new NoteNotFoundException("Note with ID " + id + " was not found"));
		logger.info("Fetched note with id={}", id);
		return note;
	}

	/**
	 * Met à jour une note existante.
	 */
	@Transactional
	public Note update(String id, Note incoming) {
		Note existing = noteRepository.findById(id)
				.orElseThrow(() -> new NoteNotFoundException("Note with ID " + id + " was not found"));

		existing.setComment(incoming.getComment());
		// Si d'autres champs existent, les mettre à jour ici

		Note updated = noteRepository.save(existing); // explicite même si transactionnel
		logger.info("Updated note with id={}", id);
		return updated;
	}

	/**
	 * Supprime une note par son ID. Lève une exception si introuvable.
	 */
	public void delete(String id) {
		if (!noteRepository.existsById(id)) {
			logger.warn("Attempt to delete nonexistent note with id={}", id);
			throw new NoteNotFoundException("Note with ID " + id + " was not found");
		}
		noteRepository.deleteById(id);
		logger.info("Deleted note with id={}", id);
	}
}
