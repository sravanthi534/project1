//package com.Revshop.revshop.Tests;
//
//import com.Revshop.revshop.controller.UserController;
//
//import com.Revshop.revshop.model.User;
//import com.Revshop.revshop.repository.UserRepository;
//import com.Revshop.revshop.service.UserService;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.ui.Model;
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class UserControllerTest {
//
//    private UserController userController;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private UserService userService;
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
//        userController = new UserController();
//        userController.userRepository = userRepository;
//        userController.serService = userService;
//    }
//
//    @Test
//    void testRegisterUser_Success() {
//        User user = new User();
//        user.setFirstName("John");
//        user.setLastName("Doe");
//        user.setEmail("john.doe@example.com");
//        user.setPhoneNumber("1234567890");
//
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        Map<String, String> result = userController.registerUser(user, session);
//
//        assertEquals("success", result.get("status"));
//        assertEquals("/profile", result.get("redirect"));
//
//        verify(session).setAttribute("userId", user.getUserId());
//        verify(session).removeAttribute("otp");
//        verify(session).removeAttribute("email");
//    }
//
//    @Test
//    void testRegisterUser_EmailVerificationRequired() {
//        User user = new User();
//        user.setFirstName("Jane");
//        user.setLastName("Doe");
//
//        when(session.getAttribute("email")).thenReturn(null);
//
//        Map<String, String> result = userController.registerUser(user, session);
//
//        assertEquals("error", result.get("status"));
//        assertEquals("Email verification required!", result.get("message"));
//    }
//
//    @Test
//    void testLoginUser_Success() {
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("otp", "123456");
//        when(session.getAttribute("otp")).thenReturn("123456");
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//
//        User user = new User();
//        user.setUserId(1L);
//        user.setEmail("john.doe@example.com");
//        user.setLastLoginAt(LocalDateTime.now());
//
//        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
//        Map<String, String> result = userController.loginUser(requestBody, session, model);
//
//        assertEquals("success", result.get("status"));
//        assertEquals("/home", result.get("redirect"));
//    }
//
//    @Test
//    void testLoginUser_InvalidOtp() {
//        Map<String, String> requestBody = new HashMap<>();
//        requestBody.put("otp", "654321");
//        when(session.getAttribute("otp")).thenReturn("123456");
//        when(session.getAttribute("email")).thenReturn("john.doe@example.com");
//
//        Map<String, String> result = userController.loginUser(requestBody, session, model);
//
//        assertEquals("error", result.get("status"));
//        assertEquals("Invalid OTP!", result.get("message"));
//    }
//
//    @Test
//    void testGetUserProfile_Success() {
//        when(session.getAttribute("userId")).thenReturn(1L);
//        User user = new User();
//        user.setUserId(1L);
//        user.setFirstName("John");
//        user.setLastName("Doe");
//
//        when(userService.getUserById(1L)).thenReturn(Optional.of(user));
//
//        String view = userController.getUserProfile(session, model);
//
//        assertEquals("user-profile", view);
//        verify(model).addAttribute("user", user);
//    }
//
//    @Test
//    void testGetUserProfile_UserNotFound() {
//        when(session.getAttribute("userId")).thenReturn(null);
//
//        String view = userController.getUserProfile(session, model);
//
//        assertEquals("redirect:/login", view);
//    }
//}
