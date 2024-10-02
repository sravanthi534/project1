package com.Revshop.revshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Revshop.revshop.model.Product;
import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.service.ProductService;
import com.Revshop.revshop.service.SellerLoginService;

import jakarta.servlet.http.HttpSession;
@Controller
public class ProductController {
	@Autowired
	private SellerLoginService sellerService;
	@Autowired
	private ProductService productService;
	@GetMapping("/add-product")
    public String showAddProductForm(Model model) {
        model.addAttribute("product", new Product()); 
        return "add-product"; 
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute("product") Product product, HttpSession session, Model model) {
        Seller loggedInSeller = (Seller) session.getAttribute("loggedInSeller");
        if (loggedInSeller != null) {
            product.setSeller(loggedInSeller); 
            System.out.println(loggedInSeller.getSellerId());
            productService.addProduct(product);  
            
            model.addAttribute("message", "Product added successfully!"); 
            return "seller-dashboard";  
        }
        model.addAttribute("loginError", "You must log in to add a product.");
        return "redirect:/seller-login";  
    }
    @GetMapping("/display-products")
    public String getProductsBySellerId(@RequestParam("sellerId") Long sellerId, Model model) {
        List<Product> productList = productService.getProductsBySellerId(sellerId);  
        model.addAttribute("products", productList);  
        
        return "display-products";  
    }

    
 // Method to display products on the homepage
    @GetMapping("/homepage")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products); // Add product list to the model
        return "homepage";  // Refers to a Thymeleaf template named "homepage.html"
    }
}
