package com.Revshop.revshop.repository;


import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.Revshop.revshop.model.Product;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
//	 List<Product> findBySeller_SelleridAndNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategorynameContainingIgnoreCase(
//	            Long sellerId, String name, String description, String categoryname);
	 List<Product> findBySeller_SellerId(Long sellerId);

}
