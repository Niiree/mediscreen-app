package com.microservice.diabete.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.microservice.diabete.model.Diabete;

@Service
public class DiabetesService {

	private static final List<String> DIABETES_TERMS = Arrays.asList(
			"hémoglobine A1C",
			"microalbumine",
			"taille",
			"poids",
			"fumeur",
			"anormal",
			"cholestérol",
			"vertige",
			"rechute",
			"réaction",
			"anticorps");

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);

	public int risk(Diabete patient) {
		int trigger = 0;
		List<String> notes = patient.getPatientNote();

		for (String note : notes) {
			for (String term : DIABETES_TERMS) {
				if (note.toLowerCase().contains(term.toLowerCase())) {
					trigger++;
				}
			}
		}
		return trigger;
	}

	public boolean noneCase(Diabete patient) {
		return risk(patient) == 0;
	}

	public boolean borderlineCase(Diabete patient) {
		int trigger = risk(patient);
		long age = getAge(patient.getPatientBirthdate());

		return trigger >= 2 && trigger < 6 && age > 30;
	}

	public boolean inDangerCase(Diabete patient) {
		int trigger = risk(patient);
		long age = getAge(patient.getPatientBirthdate());
		String gender = patient.getGender();

		return (trigger == 3 && age <= 30 && "M".equalsIgnoreCase(gender))
				|| (trigger == 4 && age <= 30 && "F".equalsIgnoreCase(gender))
				|| (trigger >= 6 && trigger < 8 && age > 30);
	}

	public boolean earlyOnsetCase(Diabete patient) {
		int trigger = risk(patient);
		long age = getAge(patient.getPatientBirthdate());
		String gender = patient.getGender();

		return (trigger == 5 && age <= 30 && "M".equalsIgnoreCase(gender))
				|| (trigger == 7 && age <= 30 && "F".equalsIgnoreCase(gender))
				|| (trigger >= 8 && age > 30);
	}

	private long getAge(String birthDateStr) {
		try {
			Date birthdate = DATE_FORMAT.parse(birthDateStr);
			Date now = new Date();

			return (now.getTime() - birthdate.getTime()) / (1000L * 60 * 60 * 24 * 365);
		} catch (ParseException e) {
			throw new IllegalArgumentException("Invalid birthdate format: " + birthDateStr, e);
		}
	}
}
