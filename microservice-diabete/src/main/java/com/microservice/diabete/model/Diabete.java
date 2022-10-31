package com.microservice.diabete.model;

import java.util.List;

public class Diabete {
		private List<String> patientNote;
		private String patientBirthdate;
		private String gender;
		
		public Diabete(){
			super();
		}

		public Diabete(List<String> patientNote, String patientBirthdate, String gender){
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

		public String getPatientBirthdate(){
			return patientBirthdate;
		}

		public void setPatientBirthdate(String patientBirthdate){
			this.patientBirthdate = patientBirthdate;
		}

		public String getGender(){
			return gender;
		}

		public void setGender(String gender){
			this.gender = gender;
		}
		
}
