package com.refugietransaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.context.ConfigurableApplicationContext;

import com.refugietransaction.services.impl.ProductTypeServiceImpl;

@SpringBootApplication
@EnableJpaAuditing
public class RefugTransactionApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RefugTransactionApplication.class, args);
		
		//Retrieve the ProductTypeService bean from the application context
		ProductTypeServiceImpl myDataService = context.getBean(ProductTypeServiceImpl.class);
		
		//Call loadData method
		myDataService.loadData();
		
		//You can continue with other application logic here if needed
		
		//Close the application context
		context.close();
	}

}
