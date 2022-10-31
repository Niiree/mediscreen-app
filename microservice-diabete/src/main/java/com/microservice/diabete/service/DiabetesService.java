package com.microservice.diabete.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.microservice.diabete.model.Diabete;

@Service
public class DiabetesService {
		List<String> diabetesList = new ArrayList<>();
	
	public void listInit(){
		
		diabetesList.addAll(Arrays.asList(
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
				"anticorps"));
	}
	
	
	public int risk(Diabete patient){
		diabetesList.clear();
		
		listInit();
		
		int trigger = 0;
		
		for (String split : patient.getPatientNote())
		{
			for (String string : diabetesList)
			{
				
				if(split.contains(string))
				{
					trigger++;
				}
				
			}
		}
		return trigger;
		
	}
	
	public boolean noneCase(Diabete patient){
		
		int trigger = risk(patient);
		
		if (trigger == 0)
		{
			return true;
		}
		
		
		return false;
		
	}




	public boolean borderlineCase(Diabete patient){
		
		try{
			
			int trigger = risk(patient);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
			Date birthdate = sdf.parse(patient.getPatientBirthdate());
			Date now = new Date();

			long age = (now.getTime() - birthdate.getTime()) / 86400000 / 365;

			if (trigger == 2 && trigger < 6 && age > 30)		{
				return true;
			}
			
		} 
		
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
		
	}

	public boolean inDangerCase(Diabete patient){
		
		try{
			
			int trigger = risk(patient);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
			Date birthdate = sdf.parse(patient.getPatientBirthdate());
			Date now = new Date();

			long age = (now.getTime() - birthdate.getTime()) / 86400000 / 365;

			
			
			if (trigger == 3 && age <= 30 && patient.getGender().equals("M"))		{
				return true;
			}
			
			else if (trigger == 4 && age <= 30 && patient.getGender().equals("F"))		{
				return true;
			}
			
			else if (trigger >= 6 && trigger < 8 && age > 30)		{
				return true;
			}
			
			
		} 
		
		catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
		
	}


	public boolean earlyOnsetCase(Diabete patient){
		
		try{
			
			int trigger = risk(patient);
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);
			Date birthdate = sdf.parse(patient.getPatientBirthdate());
			Date now = new Date();

			long age = (now.getTime() - birthdate.getTime()) / 86400000 / 365;

			
			
			if (trigger == 5 && age <= 30 && patient.getGender().equals("M"))		{
				return true;
			}
			
			else if (trigger == 7 && age <= 30 && patient.getGender().equals("F"))		{
				return true;
			}
			
			else if (trigger >= 8 && age > 30)		{
				return true;
			}
			
			
		} 
		
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
		
	}
	
}
