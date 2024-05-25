package com.refugietransaction.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.DashboardDto;
import com.refugietransaction.repository.CampRepository;
import com.refugietransaction.repository.MenageRepository;
import com.refugietransaction.repository.SupplierRepository;
import com.refugietransaction.services.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	private final MenageRepository menageRepository;
	private final SupplierRepository supplierRepository;
	private final CampRepository campRepository;
	
	@Autowired
	public DashboardServiceImpl(MenageRepository menageRepository, SupplierRepository supplierRepository, CampRepository campRepository) {
		this.menageRepository = menageRepository;
		this.supplierRepository = supplierRepository;
		this.campRepository = campRepository;
	}
	
	@Override
	public DashboardDto getDashboard() {
		
		Long menageCount = menageRepository.count();
		Long supplierCount = supplierRepository.count();
		Long campCount = campRepository.count();
		
		DashboardDto dashboardDto = new DashboardDto();
		dashboardDto.setMenageCount(menageCount);
		dashboardDto.setSupplierCount(supplierCount);
		dashboardDto.setCampCount(campCount);
		
		return dashboardDto;
	}

}
