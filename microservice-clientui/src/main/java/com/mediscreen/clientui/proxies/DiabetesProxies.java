package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.DiabeteBean;
import com.mediscreen.clientui.beans.DiabetesRiskResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
		name = "diabetes-service",
		url = "${diabetes.base-url}", // == http://microservice-gateway:8080 (profil docker)
		configuration = com.mediscreen.clientui.config.FeignConfig.class
)
public interface DiabetesProxies {

	// Correspond EXACTEMENT au backend: POST /patient/{patientId}/diabetes/risk
	@PostMapping("/patient/{id}/risk")
	DiabetesRiskResponse assessRisk(@PathVariable("id") Integer id,
									@RequestBody DiabeteBean diabetesBean);
}
