package com.Revshop.revshop.Tests;



import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Revshop.revshop.model.Product;
import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.repository.ProductRepository;
import com.Revshop.revshop.repository.SellerRepository;
import com.Revshop.revshop.service.ProductService;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerRepository sellerRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;
    private Seller seller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        seller = new Seller();
        seller.setSellerId(1L);
        
        product = new Product();
        product.setSeller(seller);
        product.setProductAddedAt(LocalDate.now());
    }

    @Test
    public void testAddProduct() {
        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));

        productService.addProduct(product);

        verify(productRepository, times(1)).save(product);
        verify(sellerRepository, times(1)).findById(1L);
        assertNotNull(product.getProductAddedAt());
    }

//    @Test
//    public void testUpdateProduct() {
//        when(sellerRepository.findById(1L)).thenReturn(Optional.of(seller));
//        productService.updateProduct(product);
//
//        verify(productRepository, times(1)).save(product);
//        assertEquals(LocalDate.now(), product.getProductAddedAt());
//    }
    
    

    @Test
    public void testDeleteProduct() {
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testGetProductById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Product foundProduct = productService.getProductById(1L);

        assertNotNull(foundProduct);
        assertEquals(product, foundProduct);
    }

    @Test
    public void testGetAllProducts() {
        productService.getAllProducts();
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductsBySellerId() {
        productService.getProductsBySellerId(1L);
        verify(productRepository, times(1)).findBySeller_SellerId(1L);
    }
}



