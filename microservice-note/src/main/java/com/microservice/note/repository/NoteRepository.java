package com.microservice.note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.microservice.note.model.Note;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
	public List<Note> findByIdPatient(Integer id);

}
