package com.refugietransaction.services.impl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.ByCampStockDto;
import com.refugietransaction.dto.CampDto;
import com.refugietransaction.dto.CampStockDto;
import com.refugietransaction.dto.MvtStkSupplierDto;
import com.refugietransaction.dto.ProductCampStockDto;
import com.refugietransaction.dto.ProductDto;
import com.refugietransaction.dto.ProductStockQuantityDto;
import com.refugietransaction.dto.ProductTypeDto;
import com.refugietransaction.dto.StockQuantityDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.exceptions.InvalidOperationException;
import com.refugietransaction.model.Camp;
import com.refugietransaction.model.MvtStkMenage;
import com.refugietransaction.model.MvtStkSupplier;
import com.refugietransaction.model.Product;
import com.refugietransaction.model.ProductType;
import com.refugietransaction.model.SalesUnit;
import com.refugietransaction.model.Supplier;
import com.refugietransaction.model.TypeMvtStkSupplier;
import com.refugietransaction.projections.ByCampStockProjection;
import com.refugietransaction.repository.CampRepository;
import com.refugietransaction.repository.MvtStkSupplierRepository;
import com.refugietransaction.repository.ProductRepository;
import com.refugietransaction.repository.SupplierRepository;
import com.refugietransaction.services.MvtStkSupplierService;
import com.refugietransaction.validator.MvtStkSupplierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MvtStkSupplierServiceImpl implements MvtStkSupplierService {
	
	private final MvtStkSupplierRepository mvtStkSupplierRepository;
	private final CampRepository campRepository;
	private final SupplierRepository supplierRepository;
	private final ProductRepository productRepository;
	
	@Autowired
	public MvtStkSupplierServiceImpl(MvtStkSupplierRepository mvtStkSupplierRepository, CampRepository campRepository, SupplierRepository supplierRepository, ProductRepository productRepository) {
		this.mvtStkSupplierRepository = mvtStkSupplierRepository;
		this.campRepository = campRepository;
		this.supplierRepository = supplierRepository;
		this.productRepository = productRepository;
	}

	@Override
	public MvtStkSupplierDto save(MvtStkSupplierDto dto) {
		List<String> errors = MvtStkSupplierValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Entries is not valid {}", dto);
			throw new InvalidEntityException("Le mouvement stock du fournisseur n'est pas valide", ErrorCodes.MVTSTK_SUPPLIER_NOT_VALID, errors);
		}
		
		dto.setDateMouvement(LocalDate.now());
		dto.setTypeMouvement(TypeMvtStkSupplier.ENTREE);
		
		return MvtStkSupplierDto.fromEntity(
				mvtStkSupplierRepository.save(MvtStkSupplierDto.toEntity(dto))
		);
	}

	@Override
	public Page<MvtStkSupplierDto> findCampSupplierEntriesByProductNameSupplierNameLike(Long idCamp, Long idSupplier, String search, Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdCampAndIdSupplierEntriesByNameLike(idCamp, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findBySupplierIdCampIdEntries(idSupplier, idCamp, pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public MvtStkSupplierDto sortie(MvtStkSupplierDto dto) {
		List<String> errors = MvtStkSupplierValidator.validate(dto);
		if(!errors.isEmpty()) {
			log.error("Entries is not valid {}", dto);
			throw new InvalidEntityException("Le mouvement stock du fournisseur n'est pas valide", ErrorCodes.MVTSTK_SUPPLIER_NOT_VALID, errors);
		}
		
		dto.setDateMouvement(LocalDate.now());
		dto.setQuantite(BigDecimal.valueOf(Math.abs(dto.getQuantite().doubleValue()) * -1) );
		dto.setTypeMouvement(TypeMvtStkSupplier.SORTIE);
		
		return MvtStkSupplierDto.fromEntity(
				mvtStkSupplierRepository.save(MvtStkSupplierDto.toEntity(dto))
		);
	}

	@Override
	public List<MvtStkSupplierDto> findMvtStkSupplierBySupplierAndCamp(Long idSupplier, Long idCamp) {
		
		return mvtStkSupplierRepository.findMvtStkSupplierBySupplierAndCamp(idSupplier, idCamp).stream()
				.map(MvtStkSupplierDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public List<MvtStkSupplierDto> findMvtStkSupplierByProductId(Long idProduit) {
		
		return mvtStkSupplierRepository.findMvtStkSupplierByProductId(idProduit).stream()
				.map(MvtStkSupplierDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public Page<MvtStkSupplierDto> findAllByProductSupplierLike(String search, Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findAllByProductSupplierLike(search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findAllMvtStkSuppliers(pageable);
		}
		
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	

	@Override
	public Page<MvtStkSupplierDto> findCampSupplierSortiesByProductNameSupplierNameLike(Long idCamp, Long idSupplier,
			String search, Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdCampAndIdSupplierSortiesByNameLike(idCamp, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findBySupplierIdCampIdSorties(idSupplier, idCamp, pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public List<CampStockDto> findStockQuantityByCamp(Long supplierId) {
		List<Camp> camps =mvtStkSupplierRepository.findDistinctCampsBySupplierId(supplierId);

		List<CampStockDto> campStockDTOS=new ArrayList<>();
		for (Camp camp : camps) {
			//System.out.println("Camp: " + camp.getName());
			List<Object[]> results = mvtStkSupplierRepository.findStockQuantityByCamp(supplierId,camp);
			List<StockQuantityDto> stockQuantities=new ArrayList<>();
			for (Object[] result : results) {
				Product product = (Product) result[0];
				BigDecimal quantity = (BigDecimal) result[1];
				
				StockQuantityDto stockQuantityDTO = new StockQuantityDto();
				stockQuantityDTO.setProductName(product.getNomProduit());
				stockQuantityDTO.setQuantity(quantity);
				stockQuantityDTO.setSalesName(product.getProductType().getSalesUnit().getName());
				stockQuantities.add(stockQuantityDTO);


				//System.out.println("\tProduct: " + product.getName() + ", Quantity: " + quantity);
			}

			CampStockDto campStockDTO = new CampStockDto();
			campStockDTO.setCampName(camp.getNomCamp());
			campStockDTO.setStockQuantities(stockQuantities);
			campStockDTOS.add(campStockDTO);
		}
		return campStockDTOS;
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierEntriesByProductNameSupplierNameLike(Long idSupplier, String search,
			Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdSupplierEntriesByNameLike(idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findBySupplierIdEntries(idSupplier, pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierSortiesByProductNameSupplierNameLike(Long idSupplier, String search,
			Pageable pageable) {
		Page<MvtStkSupplier> mvtStkSupplier;
		if(search != null) {
			mvtStkSupplier = mvtStkSupplierRepository.findByIdSupplierSortiesByNameLike(idSupplier, search, pageable);
		} else {
			mvtStkSupplier = mvtStkSupplierRepository.findBySupplierIdSorties(idSupplier, pageable);
		}
		return mvtStkSupplier.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierAndCampEntries(LocalDate startDate, LocalDate endDate, Long supplierId,
			Long campId, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findBySupplierIdCampIdEntries(supplierId, campId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdCampAndIdSupplierEntriesByNameLike(campId, supplierId, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndSupplierIdCampId(startDate, endDate, supplierId, campId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndSearchAndSupplierIdCampId(startDate, endDate, supplierId, campId, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierEntries(LocalDate startDate, LocalDate endDate, Long supplierId,
			String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findBySupplierIdEntries(supplierId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdSupplierEntriesByNameLike(supplierId, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndSupplierId(startDate, endDate, supplierId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndSearchAndSupplierId(startDate, endDate, supplierId, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierAndCampSorties(LocalDate startDate, LocalDate endDate, Long supplierId,
			Long campId, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findBySupplierIdCampIdSorties(supplierId, campId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdCampAndIdSupplierSortiesByNameLike(campId, supplierId, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndSupplierIdCampId(startDate, endDate, supplierId, campId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndSearchAndSupplierIdCampId(startDate, endDate, supplierId, campId, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findSupplierSorties(LocalDate startDate, LocalDate endDate, Long supplierId,
			String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findBySupplierIdSorties(supplierId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdSupplierSortiesByNameLike(supplierId, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndSupplierId(startDate, endDate, supplierId, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndSearchAndSupplierId(startDate, endDate, supplierId, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductCampSupplierEntriesByProductNameSupplierNameLike(Long idProduct,
			Long idCamp, Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdCampAndIdSupplierEntriesByNameLike(idProduct, idCamp, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdCampIdEntries(idProduct, idSupplier, idCamp, pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductCampSupplierEntries(LocalDate startDate, LocalDate endDate,
			Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdCampIdEntries(idProduct, idSupplier, idCamp, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdCampAndIdSupplierEntriesByNameLike(idProduct, idCamp, idSupplier, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndProductIdSupplierIdCampId(startDate, endDate, idProduct, idSupplier, idCamp, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndSearchAndProductIdSupplierIdCampId(startDate, endDate, idProduct, idSupplier, idCamp, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductSupplierEntriesByProductNameSupplierNameLike(Long idProduct,
			Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdSupplierEntriesByNameLike(idProduct, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdEntries(idProduct, idSupplier, pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductSupplierEntries(LocalDate startDate, LocalDate endDate, Long idProduct,
			Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdEntries(idProduct, idSupplier, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdSupplierEntriesByNameLike(idProduct, idSupplier, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndProductIdSupplierId(startDate, endDate, idProduct, idSupplier, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findEntriesByStartDateAndEndDateAndSearchAndProductIdSupplierId(startDate, endDate, idProduct, idSupplier, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductCampSupplierSortiesByProductNameSupplierNameLike(Long idProduct,
			Long idCamp, Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdCampAndIdSupplierSortiesByNameLike(idProduct, idCamp, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdCampIdSorties(idProduct, idSupplier, idCamp, pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductCampSupplierSorties(LocalDate startDate, LocalDate endDate,
			Long idProduct, Long idCamp, Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdCampIdSorties(idProduct, idSupplier, idCamp, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdCampAndIdSupplierSortiesByNameLike(idProduct, idCamp, idSupplier, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndProductIdSupplierIdCampId(startDate, endDate, idProduct, idSupplier, idCamp, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndSearchAndProductIdSupplierIdCampId(startDate, endDate, idProduct, idSupplier, idCamp, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductSupplierSortiesByProductNameSupplierNameLike(Long idProduit,
			Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers;
		if(search != null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdSupplierSortiesByNameLike(idProduit, idSupplier, search, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdSorties(idProduit, idSupplier, pageable);
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> findProductSupplierSorties(LocalDate startDate, LocalDate endDate, Long idProduct,
			Long idSupplier, String search, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			//if both startDate end endDate are null, return all records without date filtering
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findByProductIdSupplierIdSorties(idProduct, idSupplier, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findByIdProductIdSupplierSortiesByNameLike(idProduct, idSupplier, search, pageable);
			}
		}
		else {
			if(search == null || search.isEmpty()) {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndProductIdSupplierId(startDate, endDate, idProduct, idSupplier, pageable);
			} else {
				mvtStkSuppliers = mvtStkSupplierRepository.findSortiesByStartDateAndEndDateAndSearchAndProductIdSupplierId(startDate, endDate, idProduct, idSupplier, search, pageable);
			}
		}
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public List<ProductStockQuantityDto> findProductStockQuantityByCamp(Long productId) {
		
			List<Object[]> results = mvtStkSupplierRepository.findProductStockQuantityByCamp(productId);
			
			
		return results.stream().map(result->{
			String camp = (String) result[0];
			Product product = (Product) result[1];
			BigDecimal quantity = (BigDecimal) result[2];
			
			ProductStockQuantityDto stockQuantityDto = new ProductStockQuantityDto();
			stockQuantityDto.setCampName(camp);
			stockQuantityDto.setQuantity(quantity);
			stockQuantityDto.setSalesName(product.getProductType().getSalesUnit().getName());
			
			return stockQuantityDto;
		}).collect(Collectors.toList());
	}

	@Override
	public Page<ByCampStockDto> getProductsWithQuantityByIdCampIdSupplier(Long idCamp, Long idSupplier, String search,
			Pageable pageable) {
		
		Page<ByCampStockProjection> byCampStockProjections;
		if(search != null) {
			byCampStockProjections = mvtStkSupplierRepository.findTotalQuantityByIdCampIdSupplierAndNameProductLike(idCamp, idSupplier, search, pageable);
		} else {
			byCampStockProjections = mvtStkSupplierRepository.findTotalQuantityByIdCampIdSupplier(idCamp, idSupplier, pageable);
		}
		
		return byCampStockProjections.map(ByCampStockDto::fromEntity);
	}

	@Override
	public Page<MvtStkSupplierDto> getProductMvtStkBySupplierCamp(LocalDate startDate, LocalDate endDate,
			Long idProduct, Long idSupplier, Long idCamp, Pageable pageable) {
		
		Page<MvtStkSupplier> mvtStkSuppliers=null;
		if(startDate == null || endDate == null) {
			mvtStkSuppliers = mvtStkSupplierRepository.findProductMvtStkBySupplierCamp(idProduct, idSupplier, idCamp, pageable);
		} else {
			mvtStkSuppliers = mvtStkSupplierRepository.findProductMvtStkBySupplierCampWithDate(startDate, endDate, idProduct, idSupplier, idCamp, pageable);
		}
		
		return mvtStkSuppliers.map(MvtStkSupplierDto::fromEntity);
	}

	@Override
	public void delete(Long id) {
		
		if(id == null) {
			log.error("Mvt Stk Supplier ID is null");
		}
		
		mvtStkSupplierRepository.deleteById(id);
		
	}


}
