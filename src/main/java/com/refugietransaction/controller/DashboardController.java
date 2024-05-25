package com.refugietransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.DashboardApi;
import com.refugietransaction.dto.DashboardDto;
import com.refugietransaction.services.DashboardService;

@RestController
public class DashboardController implements DashboardApi {
	
	private final DashboardService dashboardService;
	
	@Autowired
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@Override
	public DashboardDto findDashboard() {
		
		return dashboardService.getDashboard();
	}

}
