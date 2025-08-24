package com.microservice.diabete.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.microservice.diabete.model.Diabete;
import com.microservice.diabete.service.DiabetesService;

@RestController
@RequestMapping("/diabetes")
public class DiabeteController {

	private static final Logger logger = LoggerFactory.getLogger(DiabeteController.class);

	private final DiabetesService service;

	@Autowired
	public DiabeteController(DiabetesService service) {
		this.service = service;
	}

	@PostMapping("/patient/{patientId}/risk")
	public ResponseEntity<DiabetesRiskResponse> assessRisk(
			@PathVariable("patientId") Integer patientId,
			@Valid @RequestBody Diabete diabete) {

		logger.info("Assessing diabetes risk for patientId={}", patientId);

		RiskLevel level;
		String message;

		if (service.earlyOnsetCase(diabete)) {
			level = RiskLevel.EARLY_ONSET;
			message = "Early onset";
		} else if (service.inDangerCase(diabete)) {
			level = RiskLevel.IN_DANGER;
			message = "In danger";
		} else if (service.borderlineCase(diabete)) {
			level = RiskLevel.BORDERLINE;
			message = "Borderline";
		} else if (service.noneCase(diabete)) {
			level = RiskLevel.NONE;
			message = "No risk detected";
		} else {
			level = RiskLevel.UNKNOWN;
			message = "The case does not match known categories";
		}

		DiabetesRiskResponse response = new DiabetesRiskResponse(level, message);
		return ResponseEntity.ok(response);
	}

	public enum RiskLevel {
		NONE,
		BORDERLINE,
		IN_DANGER,
		EARLY_ONSET,
		UNKNOWN
	}

	public static class DiabetesRiskResponse {
		private RiskLevel riskLevel;
		private String message;

		public DiabetesRiskResponse() {
		}

		public DiabetesRiskResponse(RiskLevel riskLevel, String message) {
			this.riskLevel = riskLevel;
			this.message = message;
		}

		public RiskLevel getRiskLevel() {
			return riskLevel;
		}

		public void setRiskLevel(RiskLevel riskLevel) {
			this.riskLevel = riskLevel;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
