package com.refugietransaction.controller.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.refugietransaction.dto.DashboardDto;
import com.refugietransaction.utils.Constants;

import io.swagger.annotations.Api;

@Api("dashboard")
public interface DashboardApi {
	
	@GetMapping(value = Constants.APP_ROOT + "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    DashboardDto findDashboard();
}
