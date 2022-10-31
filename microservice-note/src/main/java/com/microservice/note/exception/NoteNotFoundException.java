package com.microservice.note.exception;

public class NoteNotFoundException extends RuntimeException {

	  public NoteNotFoundException(String message) {
	    super(message);
	  }
}
