package com.refugietransaction.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.refugietransaction.model.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
	
	private Long id;
	private String nomProduit;
	private BigDecimal price;
	private String poids;
	@JsonIgnore
	private ProductTypeDto productType;
	
	private SupplierDto supplier;
	
	@JsonIgnore List<MouvementStockDto> mouvementStocks;
	
	public static ProductDto fromEntity(Product product) {
		if(product == null) {
			return null;
			//TODO throw an exception
		}
		
		return ProductDto.builder()
				.id(product.getId())
				.nomProduit(product.getNomProduit())
				.price(product.getPrice())
				.poids(product.getPoids())
				.productType(ProductTypeDto.fromEntity(product.getProductType()))
				.supplier(SupplierDto.fromEntity(product.getSupplier()))
				.build();
	}
	
	public static Product toEntity(ProductDto productDto) {
		if(productDto == null) {
			return null;
			//TODO throw an exception
		}
		
		Product product = new Product();
		product.setId(productDto.getId());
		product.setNomProduit(productDto.getNomProduit());
		product.setPrice(productDto.getPrice());
		product.setPoids(productDto.getPoids());
		product.setProductType(ProductTypeDto.toEntity(productDto.getProductType()));
		product.setSupplier(SupplierDto.toEntity(productDto.getSupplier()));
		
		return product;
		
	}
}
