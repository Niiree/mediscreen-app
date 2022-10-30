package com.mediscreen.clientui.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiabeteBean {

		private List<String> patientNote = new ArrayList<>();
		private Date patientBirthdate;
		private String gender;
		
		public DiabeteBean(){
			super();
		}

		public DiabeteBean(List<String> patientNote, Date patientBirthdate, String gender){
			super();
			this.patientNote = patientNote;
			this.patientBirthdate = patientBirthdate;
			this.gender = gender;
		}
		
		

		public List<String> getPatientNote(){
			return patientNote;
		}

		public void setPatientNote(List<String> patientNote){
			this.patientNote = patientNote;
		}

		public Date getPatientBirthdate(){
			return patientBirthdate;
		}

		public void setPatientBirthdate(Date patientBirthdate){
			this.patientBirthdate = patientBirthdate;
		}

		public String getgender(){
			return gender;
		}

		public void setPatientGender(String gender){
			this.gender = gender;
		}
	}