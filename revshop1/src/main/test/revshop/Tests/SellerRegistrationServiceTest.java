package com.Revshop.revshop.Tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.repository.SellerRepository;
import com.Revshop.revshop.service.SellerRegistrationService;

class SellerRegistrationServiceTest {

    @InjectMocks
    private SellerRegistrationService sellerRegistrationService;

    @Mock
    private SellerRepository sellerRepo;

    private Seller seller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a sample Seller object for testing
        seller = new Seller();
        seller.setFirstName("John");
        seller.setLastName("Doe");
        seller.setEmail("john@example.com");
        seller.setPassword("password123");
        seller.setPhoneNumber("1234567890");
        seller.setCompanyName("Doe Industries");
        seller.setCompanyAddress("1234 Street");
        seller.setCompanyDescription("A leading company.");
        seller.setState("CA");
        seller.setZipcode("90001");
    }

    @Test
    void testRegisterSeller() {
        when(sellerRepo.save(seller)).thenReturn(seller);

        Seller registeredSeller = sellerRegistrationService.registerSeller(seller);

        assertNotNull(registeredSeller);
        assertEquals(LocalDate.now(), registeredSeller.getRegistrationDate());
        verify(sellerRepo, times(1)).save(seller);
    }

    @Test
    void testIsSellerDataValid_ValidData() {
        boolean isValid = sellerRegistrationService.isSellerDataValid(seller);
        assertTrue(isValid);
    }

    @Test
    void testIsSellerDataValid_InvalidData() {
        // Set some fields to invalid or empty
        seller.setFirstName("");
        boolean isValid = sellerRegistrationService.isSellerDataValid(seller);
        assertFalse(isValid);
    }
}

