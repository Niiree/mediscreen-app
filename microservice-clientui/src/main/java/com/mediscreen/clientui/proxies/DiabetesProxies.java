package com.mediscreen.clientui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mediscreen.clientui.beans.DiabeteBean;

@FeignClient(name="mediscreen-diabetes", url="localhost:9003")
public interface DiabetesProxies {
	
	@PostMapping("/patient/{id}/diabetes/getInfo")
	public String getCase(@PathVariable("id") Integer id, @RequestBody DiabeteBean DiabetesBean);

}