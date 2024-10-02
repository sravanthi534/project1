package com.Revshop.revshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.Revshop.revshop.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;
import com.Revshop.revshop.model.User;
import org.springframework.ui.Model;
import java.time.LocalDateTime;
import java.util.*;


@Controller 
public class UserController {
	
	@Autowired
    private UserRepository userRepository;

    // Get mapping to show the registration page
    @GetMapping("/registration")
    public String showRegistrationForm() {
    
        return "registration";  
    }

    @PostMapping("/api/register-user")
    @ResponseBody
    public String registerUser(@RequestBody User user, HttpSession session) {
        // Retrieve the verified email from session
        String verifiedEmail = (String) session.getAttribute("email");

        if (verifiedEmail != null) {
            // Set the verified email in the user object
            user.setEmail(verifiedEmail);
            user.setEmailVerified(true);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            // Save the user to the repository (database)
            userRepository.save(user);

            // Clear session after registration
            session.removeAttribute("otp");
            session.removeAttribute("email");

            // Redirect to index page after successful registration
            return "redirect:/index"; // Ensure index.html exists in templates
        } else {
            return "Email verification required!";
        }
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";  
    }
   
    @PostMapping("/api/login-user")
    @ResponseBody
    public String loginUser(@RequestBody Map<String, String> requestBody, HttpSession session) {
        String savedOtp = (String) session.getAttribute("otp");
        String verifiedEmail = (String) session.getAttribute("email");

        String enteredOtp = requestBody.get("otp");

        if (verifiedEmail != null && savedOtp != null) {
            if (savedOtp.equals(enteredOtp)) {
                Optional<User> existingUser = userRepository.findByEmail(verifiedEmail);

                if (existingUser.isPresent()) {
                    User user = existingUser.get();
                    user.setLastLoginAt(LocalDateTime.now());
                    userRepository.save(user);

                    // Set the email in the session for further use
                    session.setAttribute("email", verifiedEmail);

                    // Redirect the user to the profile page
                    return "redirect:/homepage";  
                } else {
                    return "User does not exist. Please register.";
                }
            } else {
                return "Invalid OTP!";
            }
        } else {
            return "Email verification required!";
        }
    }

    
    
    @GetMapping("/profile")
    public String getUserProfile(HttpSession session, Model model) {
        String email = (String) session.getAttribute("email");

        if (email != null) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                model.addAttribute("user", user.get());
                return "profile";
            }
        }
        return "redirect:/login";  // Redirect to login if email is not found
    }


    @PostMapping("/update-profile")
    @ResponseBody
    public String updateProfile(@RequestBody Map<String, String> updates, HttpSession session) {
        String email = (String) session.getAttribute("email");

        if (email != null) {
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                User user = existingUser.get();

                // Update user details based on incoming data
                if (updates.containsKey("firstName")) {
                    user.setFirstName(updates.get("firstName"));
                }
                if (updates.containsKey("lastName")) {
                    user.setLastName(updates.get("lastName"));
                }
                if (updates.containsKey("email")) {
                    user.setEmail(updates.get("email"));
                }
                if (updates.containsKey("phoneNumber")) {
                    user.setPhoneNumber(updates.get("phoneNumber"));
                }

                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
                return "Profile updated successfully";
            }
        }
        return "User not found";
    }

    @PostMapping("/update-address")
    @ResponseBody
    public String updateAddress(@RequestBody Map<String, String> addressData, HttpSession session) {
        String email = (String) session.getAttribute("email");

        if (email != null) {
            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isPresent()) {
                User user = existingUser.get();

                // Update address-related fields
                user.setAddress(addressData.get("address"));
                user.setLocality(addressData.get("locality"));
                user.setCity(addressData.get("city"));
                user.setState(addressData.get("state"));
                user.setPincode(Integer.parseInt(addressData.get("pincode")));
                user.setAddressType(addressData.get("addressType"));

                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
                return "Address updated successfully";
            }
        }
        return "User not found";
    }


    
    
//other features of myprofile yet to be impelemented 
    /*
@PostMapping("/wishlist/add")
public String addToWishlist(@RequestParam("productId") Long productId, HttpSession session) {
    String email = (String) session.getAttribute("email");

    if (email != null) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Product product = productRepository.findById(productId).orElse(null); // Assuming productRepository exists
            if (product != null) {
                user.get().getWishlist().add(product);
                userRepository.save(user.get());
                return "redirect:/wishlist";
            }
        }
    }
    return "redirect:/login";
}

@PostMapping("/wishlist/remove")
public String removeFromWishlist(@RequestParam("productId") Long productId, HttpSession session) {
    String email = (String) session.getAttribute("email");

    if (email != null) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            Product product = productRepository.findById(productId).orElse(null); // Assuming productRepository exists
            if (product != null) {
                user.get().getWishlist().remove(product);
                userRepository.save(user.get());
                return "redirect:/wishlist";
            }
        }
    }
    return "redirect:/login";
}

@GetMapping("/orders")
public String getOrderHistory(HttpSession session, Model model) {
    String email = (String) session.getAttribute("email");

    if (email != null) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            List<Order> orders = orderRepository.findByUser(user.get()); // Assuming orderRepository exists
            model.addAttribute("orders", orders);
            return "order-history";  // Assuming there's a Thymeleaf template for order history
        }
    }
    return "redirect:/login";
}


     */
}
