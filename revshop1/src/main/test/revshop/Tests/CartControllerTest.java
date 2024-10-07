//package com.Revshop.revshop.Tests;
//
//
//
//
//
//import com.Revshop.revshop.controller.BuyerOrderController;
//import com.Revshop.revshop.controller.CartController;
//import com.Revshop.revshop.model.CartItem;
//import com.Revshop.revshop.model.Product;
//import com.Revshop.revshop.model.User;
//import com.Revshop.revshop.service.CartService;
//import com.Revshop.revshop.service.ProductService;
//import com.Revshop.revshop.repository.UserRepository;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.ui.Model;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class CartControllerTest {
//
//    private CartController cartController;
//
//    @Mock
//    private CartService cartService;
//
//    @Mock
//    private ProductService productService;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private HttpSession session;
//
//    @Mock
//    private Model model;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        cartController = new CartController();
//        cartController.cartService = cartService;
//        cartController.productService = productService;
//        cartController.userRepository = userRepository;
//    }
//
//    @Test
//    void testAddToCart_UserNotLoggedIn() {
//        when(session.getAttribute("email")).thenReturn(null);
//
//        String view = BuyerOrderController.addToCart(1L, 2, session);
//
//        assertEquals("redirect:/login", view);
//    }
//
//    @Test
//    void testAddToCart_UserLoggedIn() {
//        User user = new User();
//        user.setUserId(1L);
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
//        Product product = new Product();
//        product.setId(1L);
//        when(productService.getProductById(1L)).thenReturn(product);
//
//        String view = cartController.addToCart(1L, 2, session);
//
//        assertEquals("redirect:/cart", view);
//        verify(cartService).addProductToCart(user, product, 2);
//    }
//
//    @Test
//    void testShowCart_UserNotLoggedIn() {
//        when(session.getAttribute("email")).thenReturn(null);
//
//        String view = cartController.showCart(session, model);
//
//        assertEquals("redirect:/login", view);
//    }
//
//    @Test
//    void testShowCart_UserLoggedIn() {
//        User user = new User();
//        user.setUserId(1L);
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
//        List<CartItem> cartItems = new ArrayList<>();
//        when(cartService.getCartItemsByUser(user)).thenReturn(cartItems);
//        when(cartService.calculateTotalPrice(cartItems)).thenReturn(100.0);
//        when(cartService.calculateDiscount(cartItems)).thenReturn(10.0);
//        when(cartService.calculateDeliveryCharges(cartItems)).thenReturn(5.0);
//        when(cartService.calculateFinalAmount(cartItems)).thenReturn(95.0);
//
//        String view = cartController.showCart(session, model);
//
//        assertEquals("cart", view);
//        verify(model).addAttribute("cartItems", cartItems);
//        verify(model).addAttribute("totalPrice", 100.0);
//        verify(model).addAttribute("discount", 10.0);
//        verify(model).addAttribute("deliveryCharges", 5.0);
//        verify(model).addAttribute("totalAmount", 95.0);
//    }
//
//    @Test
//    void testUpdateCartItemQuantity_UserNotLoggedIn() {
//        when(session.getAttribute("email")).thenReturn(null);
//
//        String view = cartController.updateCartItemQuantity(1L, true, session);
//
//        assertEquals("redirect:/login", view);
//    }
//
//    @Test
//    void testUpdateCartItemQuantity_UserLoggedIn() {
//        User user = new User();
//        user.setUserId(1L);
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
//
//        String view = cartController.updateCartItemQuantity(1L, true, session);
//
//        assertEquals("redirect:/cart", view);
//        verify(cartService).updateCartItemQuantity(user, 1L, true);
//    }
//
//    @Test
//    void testRemoveFromCart_UserNotLoggedIn() {
//        when(session.getAttribute("email")).thenReturn(null);
//
//        String view = cartController.removeFromCart(1L, session);
//
//        assertEquals("redirect:/login", view);
//    }
//
//    @Test
//    void testRemoveFromCart_UserLoggedIn() {
//        User user = new User();
//        user.setUserId(1L);
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
//
//        String view = cartController.removeFromCart(1L, session);
//
//        assertEquals("redirect:/cart", view);
//        verify(cartService).removeCartItem(user, 1L);
//    }
//
//    @Test
//    void testSaveForLater_UserNotLoggedIn() {
//        when(session.getAttribute("email")).thenReturn(null);
//
//        String view = cartController.saveForLater(1L, session);
//
//        assertEquals("redirect:/login", view);
//    }
//
//    @Test
//    void testSaveForLater_UserLoggedIn() {
//        User user = new User();
//        user.setUserId(1L);
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
//
//        String view = cartController.saveForLater(1L, session);
//
//        assertEquals("redirect:/cart", view);
//        verify(cartService).saveForLater(user, 1L);
//    }
//
//    @Test
//    void testMoveToCart_UserNotLoggedIn() {
//        when(session.getAttribute("email")).thenReturn(null);
//
//        String view = cartController.moveToCart(1L, session);
//
//        assertEquals("redirect:/login", view);
//    }
//
//    @Test
//    void testMoveToCart_UserLoggedIn() {
//        User user = new User();
//        user.setUserId(1L);
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
//
//        String view = BuyerOrderController.moveToCart(1L, session);
//
//        assertEquals("redirect:/cart", view);
//        verify(cartService).moveToCart(user, 1L);
//    }
//}
