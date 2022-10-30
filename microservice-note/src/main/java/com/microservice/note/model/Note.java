package com.microservice.note.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notes")
public class Note {
	
	@Id
	private String id;
	private Integer idPatient;
	private String comment;
	
	public Note(String comment){
		super();
		this.comment = comment;
	}

	public Note(Integer idPatient, String comment){
		super();
		this.idPatient = idPatient;
		this.comment = comment;
	}
	
	public Note(Integer idPatient, String comment,String idNote){
		super();
		this.idPatient = idPatient;
		this.comment = comment;
		this.id = idNote;
	}

	public Integer getIdPatient(){
		return idPatient;
	}

	public void setIdPatient(Integer idPatient){
		this.idPatient = idPatient;
	}

	public Note(){
		super();
	}

	public String getComment(){
		return comment;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}
	
}
