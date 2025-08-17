package com.mediscreen.clientui.proxies;

import com.mediscreen.clientui.beans.DiabeteBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
		name = "diabete",
		url = "${patients.base-url}/patient",      // ex: http://microservice-gateway:8080
		configuration = com.mediscreen.clientui.config.FeignConfig.class
)
public interface DiabetesProxies {

	@PostMapping("/{id}/diabetes/getInfo")
	String getCase(@PathVariable("id") Integer id, @RequestBody DiabeteBean diabetesBean);
}
