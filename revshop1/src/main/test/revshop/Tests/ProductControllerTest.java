package com.Revshop.revshop.Tests;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import com.Revshop.revshop.controller.ProductController;
import com.Revshop.revshop.model.Product;
import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.service.ProductService;

public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private HttpSession session;

    @Mock
    private Model model;

    private Seller seller;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Seller();
        seller.setSellerId(1L);
        product = new Product();
        product.setName("Test Product");
        product.setSeller(seller);
    }

    @Test
    public void testAddProduct_WhenLoggedIn() {
        when(session.getAttribute("loggedInSeller")).thenReturn(seller);
        
        String result = productController.addProduct(product, session, model);
        
        verify(productService, times(1)).addProduct(product);
        verify(model, times(1)).addAttribute("message", "Product added successfully!");
        assertEquals("seller-dashboard", result);
    }

    @Test
    public void testAddProduct_WhenNotLoggedIn() {
        when(session.getAttribute("loggedInSeller")).thenReturn(null);

        String result = productController.addProduct(product, session, model);

        verify(productService, never()).addProduct(any(Product.class));
        verify(model, times(1)).addAttribute("loginError", "You must log in to add a product.");
        assertEquals("redirect:/seller-login", result);
    }

    @Test
    public void testGetProductsBySellerId() {
        Long sellerId = 1L;
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productService.getProductsBySellerId(sellerId)).thenReturn(products);

        String result = productController.getProductsBySellerId(sellerId, model);

        verify(productService, times(1)).getProductsBySellerId(sellerId);
        verify(model, times(1)).addAttribute("products", products);
        assertEquals("display-products", result);
    }

    @Test
    public void testShowAddProductForm() {
        // Call the method under test
        String result = productController.showAddProductForm(model);

        // Verify the model's interaction by checking for specific properties
        verify(model, times(1)).addAttribute(eq("product"), any(Product.class));
        assertEquals("add-product", result);
    }

    @Test
    public void testShowProductsOnHomepage() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productService.getAllProducts()).thenReturn(products);

        String result = productController.showProducts(model);

        verify(productService, times(1)).getAllProducts();
        verify(model, times(1)).addAttribute("products", products);
        assertEquals("homepage", result);
    }
}


