package com.Revshop.revshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.service.SellerLoginService;

@Controller
public class SellerLoginController {
	
	@Autowired
	private SellerLoginService sellerLoginService;
	
	@GetMapping("/sellerLogin")
	public String showLoginForm(Model model) {
	    model.addAttribute("seller", new Seller());
	    return "seller-login";
	}

	@PostMapping("/sellerLogin")
	public String loginSeller(@ModelAttribute("seller") Seller seller, Model model) {
	    if(sellerLoginService.isSellerRegistered(seller.getEmail(), seller.getPassword())) {
	        return "redirect:/sellerDashboard";  // Redirect after successful login
	    } else {
	        model.addAttribute("errorMessage", "Invalid Email or Password");
	        return "seller-login";
	    }
	}

}
