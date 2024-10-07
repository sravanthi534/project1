package com.Revshop.revshop.Tests;

//public class SellerLoginServiceTest {
//
//}

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.repository.SellerRepository;
import com.Revshop.revshop.service.SellerLoginService;

public class SellerLoginServiceTest {

    @Mock
    private SellerRepository sellerRepo;

    @InjectMocks
    private SellerLoginService sellerLoginService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsSellerRegistered_Success() {
        // Setup: Mock the repository to return a valid seller
        Seller seller = new Seller();
        when(sellerRepo.findByEmailAndPassword("test@example.com", "password"))
            .thenReturn(Optional.of(seller));

        // Act & Assert: Should return true for valid credentials
        assertTrue(sellerLoginService.isSellerRegistered("test@example.com", "password"));
    }

    @Test
    public void testIsSellerRegistered_Failure() {
        // Setup: Mock the repository to return an empty result
        when(sellerRepo.findByEmailAndPassword("wrong@example.com", "wrongpassword"))
            .thenReturn(Optional.empty());

        // Act & Assert: Should return false for invalid credentials
        assertFalse(sellerLoginService.isSellerRegistered("wrong@example.com", "wrongpassword"));
    }

    @Test
    public void testIsSellerRegistered_NullInputs() {
        // Setup: Mock the repository to handle null values
        when(sellerRepo.findByEmailAndPassword(null, null)).thenReturn(Optional.empty());

        // Act & Assert: Ensure that null inputs return false
        assertFalse(sellerLoginService.isSellerRegistered(null, null));
    }
}

