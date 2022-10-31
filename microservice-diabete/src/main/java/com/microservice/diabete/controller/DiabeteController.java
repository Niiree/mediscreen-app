package com.microservice.diabete.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.diabete.model.Diabete;
import com.microservice.diabete.service.DiabetesService;

@RestController
public class DiabeteController {
	
	@Autowired
	private DiabetesService service;
	
	@PostMapping("/patient/{id}/diabetes/getInfo")
	public String getCase(@PathVariable("id") Integer id, @RequestBody Diabete diabete)
	{
		String result = "le cas du patient ne correspond à aucun des 4 cas";
		
		if (service.noneCase(diabete))
		{
			result = "none";
		}
		
		else if (service.borderlineCase(diabete))
		{
			result = "borderline";
		}
		
		else if (service.inDangerCase(diabete))
		{
			result = "in danger";
		}
		
		else if (service.earlyOnsetCase(diabete))
		{
			result = "early on set";
		}
		return result;
	}

}
