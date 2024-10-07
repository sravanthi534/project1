package com.Revshop.revshop.Tests;



import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.Revshop.revshop.controller.SellerRegistrationController;
import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.service.SellerRegistrationService;

class SellerRegistrationControllerTest {

    @Mock
    private SellerRegistrationService sellerRegistrationService;

    @InjectMocks
    private SellerRegistrationController sellerRegistrationController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerRegistrationController).build();
    }

    @Test
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/sellerRegistration"))
               .andExpect(status().isOk())
               .andExpect(view().name("seller-registration"))
               .andExpect(model().attributeExists("seller"));
    }

    @Test
    void testRegisterSeller_ValidData() throws Exception {
        Seller validSeller = new Seller();
        when(sellerRegistrationService.isSellerDataValid(any(Seller.class))).thenReturn(true);

        mockMvc.perform(post("/sellerRegistration")
               .flashAttr("seller", validSeller))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/sellerLogin"));

        verify(sellerRegistrationService, times(1)).registerSeller(any(Seller.class));
    }

    @Test
    void testRegisterSeller_InvalidData() throws Exception {
        Seller invalidSeller = new Seller();
        when(sellerRegistrationService.isSellerDataValid(any(Seller.class))).thenReturn(false);

        mockMvc.perform(post("/sellerRegistration")
               .flashAttr("seller", invalidSeller))
               .andExpect(status().isOk())
               .andExpect(view().name("seller-registration"))
               .andExpect(model().attributeExists("errorMessage"));

        verify(sellerRegistrationService, never()).registerSeller(any(Seller.class));
    }
}

