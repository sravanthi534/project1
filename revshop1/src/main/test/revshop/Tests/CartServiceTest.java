package com.Revshop.revshop.Tests;



import com.Revshop.revshop.model.CartItem;
import com.Revshop.revshop.model.Product;
import com.Revshop.revshop.model.User;
import com.Revshop.revshop.repository.CartItemRepository;
import com.Revshop.revshop.repository.ProductRepository;
import com.Revshop.revshop.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CartServiceTest {

    private CartService cartService;

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(cartItemRepository, productRepository, userRepository);
    }

    @Test
    void testAddProductToCart() {
        User user = new User();
        user.setUserId(1L);
        Product product = new Product();
        product.setId(1L);
        int quantity = 2;

        cartService.addProductToCart(user, product, quantity);

        ArgumentCaptor<CartItem> captor = ArgumentCaptor.forClass(CartItem.class);
        verify(cartItemRepository).save(captor.capture());
        CartItem cartItem = captor.getValue();

        assertEquals(user.getUserId(), cartItem.getUser().getUserId());
        assertEquals(product.getId(), cartItem.getProduct().getId());
        assertEquals(quantity, cartItem.getQuantity());
    }

    @Test
    void testGetCartItemsByUser() {
        User user = new User();
        user.setUserId(1L);
        List<CartItem> expectedCartItems = new ArrayList<>();
        when(cartItemRepository.findByUser(user)).thenReturn(expectedCartItems);

        List<CartItem> actualCartItems = cartService.getCartItemsByUser(user);

        assertEquals(expectedCartItems, actualCartItems);
    }

    @Test
    void testRemoveCartItem() {
        User user = new User();
        user.setUserId(1L);
        Long productId = 1L;

        cartService.removeCartItem(user, productId);

        verify(cartItemRepository).deleteByUserAndProductId(user, productId);
    }

    @Test
    void testUpdateCartItemQuantity() {
        User user = new User();
        user.setUserId(1L);
        Long productId = 1L;
        boolean increase = true;

        cartService.updateCartItemQuantity(user, productId, increase);

        verify(cartItemRepository).updateQuantity(user, productId, increase);
    }

    @Test
    void testSaveForLater() {
        User user = new User();
        user.setUserId(1L);
        Long productId = 1L;

        cartService.saveForLater(user, productId);

        verify(cartItemRepository).saveForLater(user, productId);
    }

    @Test
    void testMoveToCart() {
        User user = new User();
        user.setUserId(1L);
        Long productId = 1L;

        cartService.moveToCart(user, productId);

        verify(cartItemRepository).moveToCart(user, productId);
    }
}

