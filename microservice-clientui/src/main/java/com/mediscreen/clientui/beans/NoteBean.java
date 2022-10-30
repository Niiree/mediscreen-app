package com.mediscreen.clientui.beans;

import javax.validation.constraints.NotEmpty;

public class NoteBean {

	private String id;
	private Integer idPatient;
	@NotEmpty(message = "Comment is mandatory")
	private String comment;

	public NoteBean(){
		super();
	}

	public NoteBean( Integer idPatient, @NotEmpty(message = "Note is mandatory")String comment){
		super();
		this.idPatient = idPatient;
		this.comment = comment;
	}

	public NoteBean(String id, Integer idPatient, @NotEmpty(message = "Comment is mandatory") String comment)	{
		super();
		this.id = id;
		this.idPatient = idPatient;
		this.comment = comment;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){
		this.id = id;
	}
		
	public Integer getIdPatient(){
		return idPatient;
	}

	public void setIdPatient(Integer idPatient){
		this.idPatient = idPatient;
	}

	public String getComment(){
		return comment;
	}

	public void setComment(String comment){
		this.comment = comment;
	}
	
}
