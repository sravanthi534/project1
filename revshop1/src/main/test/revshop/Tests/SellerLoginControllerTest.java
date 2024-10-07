package com.Revshop.revshop.Tests;

//public class SellerLoginControllerTest {
//
//}

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.Revshop.revshop.controller.SellerLoginController;
import com.Revshop.revshop.model.Seller;
import com.Revshop.revshop.service.SellerLoginService;

public class SellerLoginControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SellerLoginService sellerLoginService;

    @InjectMocks
    private SellerLoginController sellerLoginController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sellerLoginController).build();
    }

    @Test
    public void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/sellerLogin"))
                .andExpect(status().isOk())
                .andExpect(view().name("seller-login"))
                .andExpect(model().attributeExists("seller"));
    }

    @Test
    public void testLoginSeller_Success() throws Exception {
        when(sellerLoginService.isSellerRegistered("test@example.com", "password")).thenReturn(true);

        mockMvc.perform(post("/sellerLogin")
                .param("email", "test@example.com")
                .param("password", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/sellerDashboard"));
    }

    @Test
    public void testLoginSeller_Failure() throws Exception {
        when(sellerLoginService.isSellerRegistered("wrong@example.com", "wrongpassword")).thenReturn(false);

        mockMvc.perform(post("/sellerLogin")
                .param("email", "wrong@example.com")
                .param("password", "wrongpassword"))
                .andExpect(status().isOk())
                .andExpect(view().name("seller-login"))
                .andExpect(model().attribute("errorMessage", "Invalid Email or Password"));
    }
}

