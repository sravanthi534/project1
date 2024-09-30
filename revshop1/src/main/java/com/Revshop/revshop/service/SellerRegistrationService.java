package com.Revshop.revshop.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.repository.SellerRepository;

@Service
public class SellerRegistrationService {

	@Autowired
	private SellerRepository sellerRepo;       

	public Seller registerSeller(Seller seller)
	{
		seller.setRegistrationDate(LocalDate.now());
		return sellerRepo.save(seller);
	}
	
	public boolean isSellerDataValid(Seller seller)
	{
        return seller.getFirstName() != null && !seller.getFirstName().isEmpty() &&
                seller.getLastName() != null && !seller.getLastName().isEmpty() &&
                seller.getEmail() != null && !seller.getEmail().isEmpty() &&
                seller.getPassword() != null && !seller.getPassword().isEmpty() &&
                seller.getPhoneNumber() != null && !seller.getPhoneNumber().isEmpty() &&
                seller.getCompanyName() != null && !seller.getCompanyName().isEmpty() &&
                seller.getCompanyAddress()!= null && !seller.getCompanyAddress().isEmpty() &&
                seller.getCompanyDescription()!= null && !seller.getCompanyDescription().isEmpty() &&
                seller.getState()!= null && !seller.getState().isEmpty() &&
                seller.getZipcode()!= null && !seller.getZipcode().isEmpty();
     
	}
}
