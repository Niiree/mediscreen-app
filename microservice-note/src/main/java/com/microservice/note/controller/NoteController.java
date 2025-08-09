package com.microservice.note.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import com.microservice.note.model.Note;
import com.microservice.note.service.NoteService;

@RestController
@RequestMapping
@Validated
public class NoteController {

	private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

	private final NoteService noteService;

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	/**
	 * Récupère toutes les notes d'un patient.
	 * GET /patient/{patientId}/notes
	 */
	@GetMapping("/patient/{patientId}/notes")
	public ResponseEntity<List<Note>> getAllNotesForPatient(@PathVariable Integer patientId) {
		logger.info("GET /patient/{}/notes - Fetching all notes for patient", patientId);
		return ResponseEntity.ok(noteService.getAllNotes(patientId));
	}

	/**
	 * Crée une note pour un patient.
	 * POST /patient/{patientId}/notes
	 */
	@PostMapping("/patient/{patientId}/notes")
	public ResponseEntity<Note> addNote(@PathVariable Integer patientId, @Valid @RequestBody Note note) {
		logger.info("POST /patient/{}/notes - Creating note", patientId);
		Note created = noteService.create(patientId, note);
		return ResponseEntity.status(HttpStatus.CREATED).body(created);
	}

	/**
	 * Récupère une note par son ID.
	 * GET /notes/{noteId}
	 */
	@GetMapping("/notes/{noteId}")
	public ResponseEntity<Note> getNote(@PathVariable String noteId) {
		logger.info("GET /notes/{} - Fetching note", noteId);
		Note note = noteService.get(noteId);
		return ResponseEntity.ok(note);
	}

	/**
	 * Met à jour une note.
	 * PUT /notes/{noteId}
	 */
	@PutMapping("/notes/{noteId}")
	public ResponseEntity<Note> updateNote(@PathVariable String noteId, @Valid @RequestBody Note note) {
		logger.info("PUT /notes/{} - Updating note", noteId);
		Note updated = noteService.update(noteId, note);
		return ResponseEntity.ok(updated);
	}

	/**
	 * Supprime une note.
	 * DELETE /notes/{noteId}
	 */
	@DeleteMapping("/notes/{noteId}")
	public ResponseEntity<Void> deleteNote(@PathVariable String noteId) {
		logger.info("DELETE /notes/{} - Deleting note", noteId);
		noteService.delete(noteId);
		return ResponseEntity.noContent().build();
	}
}
