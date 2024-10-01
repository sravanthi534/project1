package com.Revshop.revshop.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.repository.SellerRepository;

@Service
public class SellerLoginService {

	@Autowired
	private SellerRepository sellerRepo;
	
	public boolean isSellerRegistered(String email, String password)
	{
		Optional<Seller> seller = sellerRepo.findByEmailAndPassword(email, password);
		return seller.isPresent();
	}

	public Seller findByemail(String email) {
		Seller seller=sellerRepo.findByEmail(email);
		return seller;
	}
	
	
}
