package com.refugietransaction.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.refugietransaction.model.ProductType;
import com.refugietransaction.repository.ProductTypeRepository;
import com.refugietransaction.services.ProductTypeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductTypeServiceImpl implements ProductTypeService {
	
	private final ProductTypeRepository productTypeRepository;
	
	@Autowired
	public ProductTypeServiceImpl(ProductTypeRepository productTypeRepository) {
		this.productTypeRepository = productTypeRepository;
	}

	@Override
	public void loadData() {
		//check if data already exists
		if(productTypeRepository.count() == 0) {
			//if no data exists, you can save some initial data
			ProductType productType1 = new ProductType();
			productType1.setNameProductTpe("Combustible solide");
			productTypeRepository.save(productType1);
		}
		
	}
	
	

}
